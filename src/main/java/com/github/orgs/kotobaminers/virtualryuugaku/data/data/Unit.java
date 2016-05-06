package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.GUIIcon;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerData;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class Unit {
	public static final int MAX_SENTENCE = 8;
	protected String name = "";
	public static final String TALK_FREELY = "Talk freely";
	protected List<List<HolographicSentence>> helperSentences = new ArrayList<List<HolographicSentence>>();
	protected List<List<HolographicSentence>> playerSentences = new ArrayList<List<HolographicSentence>>();
	protected List<Integer> playerNPCIds = new ArrayList<Integer>();
	protected List<String> playerQuestions = new ArrayList<String>();

	public enum UnitType {
		NORMAL(Arrays.asList(GUIIcon.FREE_UP, GUIIcon.RESPAWN)),
		ONLINE_PLAYER(Arrays.asList(GUIIcon.RESPAWN)),;
		private List<GUIIcon> options = new ArrayList<>();
		private UnitType(List<GUIIcon> options) {
			this.options = options;
		}
		public List<GUIIcon> getOptions() {
			return options;
		}
	}

	private UnitType type = UnitType.NORMAL;
	private Unit() {
	}
	public static Unit create(String name, UnitType type) {
		Unit unit = new Unit();
		unit.name = name;
		unit.type = type;
		return unit;
	}

	public List<List<HolographicSentence>> getHelperSentences() {
		return helperSentences;
	}
	public void setHelperSentences(List<List<HolographicSentence>> helperSentences) {
		this.helperSentences = helperSentences;
	}
	public List<List<HolographicSentence>> getPlayerSentences() {
		return playerSentences;
	}
	public List<List<PlayerSentence>> getCastedPlayerSentences() {
		return playerSentences.stream().map(ls -> ls.stream().map(s -> (PlayerSentence) s).collect(Collectors.toList())).collect(Collectors.toList());
	}
	public void setPlayerSentences(List<List<HolographicSentence>> playerSentences) {
		this.playerSentences = playerSentences;
	}
	public List<Integer> getPlayerNPCIds() {
		return playerNPCIds;
	}
	public void setPlayerNPCIds(List<Integer> playerNPCIds) {
		this.playerNPCIds = playerNPCIds;
	}
	public List<String> getPlayerQuestions() {
		return playerQuestions;
	}
	public void setPlayerQuestions(List<String> playerQuestions) {
		this.playerQuestions = playerQuestions;
	}
	public Optional<List<HolographicSentence>> findDisplayedLS(NPC npc) {
		Optional<List<HolographicSentence>> helpers = findHelperLS(npc.getId());
		if (helpers.isPresent()) {
			return helpers;
		}
		Optional<List<HolographicSentence>> players = NPCUtility.findSkinUUID(npc).flatMap(skin -> findPlayerLS(skin));
		if (players.isPresent()) {
			return players;
		}
		return Optional.empty();
	}

	public Optional<List<HolographicSentence>> findHelperLS(Integer id) {
		return getHelperSentences().stream().filter(ls -> predicateLS.test(ls, id)).findFirst();
	}
	public Optional<List<HolographicSentence>> findPlayerLS(UUID uuid) {
		return getPlayerSentences().stream().filter(ls -> ls.stream().anyMatch(s -> ((PlayerSentence) s).getUniqueId().equals(uuid))).findFirst();
	}

	public void addHelperNPC(Integer id, CommandSender sender) {
		name = getName();
		if(UnitStorage.getAllIds().contains(id)) {
			Message.ERROR_1.print(Arrays.asList("The NPC is in use: " + id), sender);
			return;
		}
		Optional<NPC> npc = NPCUtility.findNPC(id);
		if (!npc.isPresent()) {
			Message.ERROR_1.print(Arrays.asList("The NPC doesn't exist: " + name), sender);
			return;
		}

		List<HolographicSentence> ls = new ArrayList<>();
		ls.add((HolographicSentence) HelperSentence.createEmpty(id));
		getHelperSentences().add(ls);
		Message.VRG_1.print(Arrays.asList("A helper NPC was added: " + name + " " + id), sender);
		if (sender instanceof Player) {
			Utility.teleportToNPC((Player) sender, npc.get());
		}
	}
	public void addPlayerNPC(Integer id, CommandSender sender) {
		name = getName();
		if(UnitStorage.getAllIds().contains(id)) {
			Message.ERROR_1.print(Arrays.asList("The NPC is in use: " + id), sender);
			return;
		}
		Optional<NPC> npc = NPCUtility.findNPC(id);
		if (!npc.isPresent()) {
			Message.ERROR_1.print(Arrays.asList("The NPC doesn't exist: " + name), sender);
			return;
		}

		getPlayerNPCIds().add(id);
		NPCUtility.changeNPCAsEmpty(npc.get());
		Message.VRG_1.print(Arrays.asList("A player NPC was added: " + name + " " + id), sender);
		if (sender instanceof Player) {
			Utility.teleportToNPC((Player) sender, npc.get());
		}
	}

	public void addPlayerQuestion(Integer position, String question, Player player) {
		Message.EDITOR_1.print(Arrays.asList("Editing the player question"), player);
		player.playSound(player.getLocation(), Sound.ANVIL_USE, 1f, 1f);
		playerQuestions.add(position, question);
		resizePlayerQuestions();
	}
	public void removePlayerQuestion(Integer position, Player player) {
		if (position < playerQuestions.size()) {
			Message.EDITOR_1.print(Arrays.asList("Editing the player question"), player);
			player.playSound(player.getLocation(), Sound.ANVIL_USE, 1f, 1f);
			playerQuestions.remove(playerQuestions.get(position));
			resizePlayerQuestions();
		} else {
			Message.ERROR_1.print(Arrays.asList(""), player);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
		}
	}
	public void editPlayerQuestion(Integer position, String question, Player player) {
		if (position < playerQuestions.size()) {
			Message.EDITOR_1.print(Arrays.asList("Editing the player question"), player);
			player.playSound(player.getLocation(), Sound.ANVIL_USE, 1f, 1f);
			playerQuestions.add(position, question);
			playerQuestions.remove(playerQuestions.get(position + 1));
		} else {
			Message.ERROR_1.print(Arrays.asList(""), player);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
		}
	}

	public void resizePlayerQuestions() {
		List<String> buff = new ArrayList<>();
		for(int i = 0; i < MAX_SENTENCE; i++) {
			if (i < playerQuestions.size()) {
				buff.add(playerQuestions.get(i));
			} else {
				buff.add(TALK_FREELY);
			}
		}
		playerQuestions = buff;
	}

	public long calculateAchievementRate(PlayerData data) {
		int all = 0;
		long done = 0;
		List<Integer> questions = getHelperSentences().stream().flatMap(ls -> ls.stream().filter(s -> s instanceof QuestionSentence))
			.map(s -> s.getId())
			.collect(Collectors.toList());
		done += data.scores.getOrDefault(name.toUpperCase(), new UnitScore("")).done.stream()
			.filter(d -> questions.contains(d))
			.count();
		all += questions.size();
		long rate = 0;
		if (all == 0) {
			rate = 100;
		} else {
			rate = 100 * done / all;
		}
		return rate;
	}
	public void printAchievementRate(Player player) {
		long rate = calculateAchievementRate(PlayerDataStorage.getPlayerData(player));
		long done = rate / 10;
		String indicator = ChatColor.YELLOW + "Achievement: " + ChatColor.GREEN + ChatColor.BOLD;
		for (int i = 0; i < done; i++) {
			indicator += "*";
		}
		indicator += ChatColor.DARK_GREEN;
		for (int i = 0; i < 10 - done; i++) {
			indicator += "-";
		}
		indicator += ChatColor.GREEN + " (" + rate + "%)";
		Message.VRG_1.print(Arrays.asList(indicator), player);
	}

	public BiPredicate<List<HolographicSentence>, Integer> predicateLS = (ls, id) ->
	ls.stream().anyMatch(s -> s.getId().equals(id));
	public BiPredicate<List<List<HolographicSentence>>, Integer> predicateLLS = (lls, id) ->
	lls.stream().anyMatch(ls -> ls.stream().map(s -> s.getId().equals(id)).anyMatch(b -> b == true));

	public String getName() {
		return name;
	}
	public UnitType getType() {
		return type;
	}

	public void addEmptyPlayerSentencesIfAbsent(Player player) {
		addEmptyPlayerSentencesIfAbsent(player.getName(), player.getUniqueId());
	}
	public void addEmptyPlayerSentencesIfAbsent(NPC npc) {
		NPCUtility.findSkinName(npc).ifPresent(name -> NPCUtility.findSkinUUID(npc)
			.ifPresent(uuid -> addEmptyPlayerSentencesIfAbsent(name, uuid)));
	}

	private void addEmptyPlayerSentencesIfAbsent(String name, UUID uuid) {
		Stream<PlayerSentence> sentences = playerSentences.stream().flatMap(ls -> ls.stream().map(s -> (PlayerSentence) s));
		if (!sentences.anyMatch(s -> s.getUniqueId().equals(uuid))) {
			List<HolographicSentence> list = new ArrayList<>();
			for (int i = 0; i < MAX_SENTENCE; i++) {
				list.add(PlayerSentence.createEmpty(uuid, name));
			}
			playerSentences.add(list);
		}
	}
}

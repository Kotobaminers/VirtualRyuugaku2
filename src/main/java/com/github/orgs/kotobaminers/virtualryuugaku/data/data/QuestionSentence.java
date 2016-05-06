package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.SentenceHologram;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.UnitYamlConverter.UnitPath;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.GUIIcon;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class QuestionSentence extends HolographicSentence{
	public static final String NOT_REGISTERED = "" + ChatColor.RED + ChatColor.BOLD + "No question is registered";
	private static final String EMPTY = "Enter question";

	private String question = "";
	private List<String> answers = new ArrayList<String>();
	private UUID owner = null;

	private QuestionSentence() {
	}

	public static Optional<QuestionSentence> create(ConfigurationSection section, List<Integer> index) {
		if(section.isList(UnitPath.Q.toString()) && section.isList(UnitPath.A.toString())) {
			QuestionSentence instance = new QuestionSentence();
			Optional.ofNullable(section.getStringList(UnitPath.Q.name()))
				.filter(q -> 0 < q.size())
				.ifPresent(q -> instance.question = q.get(0));
			Optional.ofNullable(section.getStringList(UnitPath.A.name()))
				.ifPresent(a -> instance.answers = a);
			instance.id = index.get(index.size() - 1);
			return Optional.of(instance);
		}
		return Optional.empty();
	}
	public static QuestionSentence createEmpty(Integer id) {
		QuestionSentence question = new QuestionSentence();
		question.question = EMPTY;
		question.setId(id);
		return question;
	}

	public void validate(String answer, Player player) {
		if (answers.stream().anyMatch(a -> a.equalsIgnoreCase(answer))) {
			Message.COMMON_CORRECT_0.print(null, player);
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
			PlayerDataStorage.getPlayerData(player).addHelperQuestionDone(getUnitName(), id);
		} else {
			Message.COMMON_WRONG_0.print(null, player);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
		}
	}

	@Override
	public void playEffect(Player player, Location location) {
		Utility.lookAt(player, location);
		player.getWorld().spigot().playEffect(location.add(0, 2, 0), Effect.NOTE, 25, 10, 0, 0, 0, 0, 1, 10);
		player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1F, 1F);
	}

	public String getQuestion() {
		return question;
	}
	public List<String> getAnswers() {
		return answers;
	}

	@Override
	public void update(String line, SpellType spell) {
		this.question = line;
	}

	@Override
	public List<String> getHolographicLines(List<SpellType> spells) {
		return Arrays.asList("" + ChatColor.GREEN + ChatColor.BOLD + "[Question]", ChatColor.BOLD + getQuestion());
	}

	@Override
	public Optional<List<ItemStack>> giveIcons() {
		ItemStack item = GUIIcon.HELPER_QUESTION.createItem();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(getQuestion());
		meta.setLore(Arrays.asList(answers.size() + " answers"));
		item.setItemMeta(meta);
		return Optional.of(Arrays.asList(item));
	}

	@Override
	public Optional<List<ItemStack>> giveSentenceIcons() {
		return Optional.empty();
	}

	@Override
	public Optional<List<ItemStack>> giveEmptyIcons() {
		ItemStack item = GUIIcon.HELPER_QUESTION.createItem();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(NOT_REGISTERED);
		meta.setLore(Arrays.asList(answers.size() + " answers"));
		item.setItemMeta(meta);
		return Optional.of(Arrays.asList(item));
	}

	@Override
	public void registerHologram(SentenceHologram hologram, NPC npc, List<HolographicSentence> sentences) {
		HologramStorage.holograms.put(sentences.stream().map(s -> s.getId()).collect(Collectors.toList()), hologram);
	}

	@Override
	public Location getTalkerLocation(NPC npc) {
		return NPCUtility.findNPC(id).get().getStoredLocation();
	}

	public UUID getOwner() {
		return owner;
	}
}


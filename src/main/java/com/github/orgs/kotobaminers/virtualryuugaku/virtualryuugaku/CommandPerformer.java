package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Commands;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.PlayerRequest;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.Unit;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.QuestionSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceEditor;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.UnitStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.UnitSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicCommandGame;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicEmptyGame;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicEventGame;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public class CommandPerformer {
	public CommandSender sender;
	public ConsoleCommandSender console = null;
	public Player player = null;

	public Commands command;
	public List<String> params;

	public CommandPerformer(CommandSender sender, Commands command, String label, String[] args) {
		this.sender = sender;

		if (sender instanceof Player) {
			this.player = (Player) sender;
		} else if (sender instanceof ConsoleCommandSender) {
			this.console = (ConsoleCommandSender) sender;
		}
		this.command = command;

		List<String> list = new LinkedList<String>();
		list.add(label);
		list.addAll(Arrays.asList(args));
		int i = 0;
		while(i < command.getPath().size()) {
			list.remove(0);
			i++;
		}
		this.params = list;
	}

	public boolean performCommand() {
		boolean success = false;
		switch (command) {
		case VIRTUALRYUUGAKU_DEBUG:
		case VIRTUALRYUUGAKU_OP:
		case VIRTUALRYUUGAKU:
		case ANSWER:
		case DEBUG_MODE:
			success = commandDebugMode();
			break;
		case DEBUG_INTERVAL:
			success = commandDebugInterval();
			break;
		case DEBUG_DEBUG:
			success = commandDebugDebug();
			break;
		case DEBUG_EFFECT:
			success = commandDebugEffect();
			break;
		case DEBUG_SOUND:
			success = commandDebugSound();
			break;

		case SAVE:
			success = commandOpSave();
			break;
		case RELOAD:
			success = commandOpReload();
			break;
		case EMPTY:
			success = commandOpEmpty();
			break;
		case UNIT:
			break;
		case UNIT_CREATE:
			success = commandUnitCreate();
			break;
		case UNIT_DELETE:
			success = commandUnitDelete();
			break;
		case UNIT_ADD_HELPER:
			success = commandUnitAddHelper();
			break;
		case UNIT_ADD_PLAYER:
			success = commandUnitAddPlayer();
			break;
		case UNIT_ADD_PLAYER_QUESTION:
		case UNIT_EDIT_PLAYER_QUESTION:
		case UNIT_REMOVE_PLAYER_QUESTION:
			success = commandUnitUpdatePlayerQuestion(command);
			break;
		case REQUEST_TELEPORT_NPC:
			success = commandRequestTeleportNPC();
			break;
		case REQUEST_TELEPORT_PLAYER:
			success = commandRequestTeleportPlayer();
			break;
		case REQUEST_ACCEPT:
			success = commandAcceptRequest();
			break;

		case DICTIONARY:
			success = commandDictionary();
			break;
		case DICTIONARY_BROADCAST:
			success = commandDictionaryBroadcast();
			break;

		case EDIT:
			success = commandEdit();
			break;
		case ADD_ANSWER:
			success = commandAddAnswer();
			break;
		case REMOVE_ANSWER:
			success = commandRemoveAnswer();
			break;
		case DELETE_QUESTION:
			success = commandDeleteQuestion();
			break;

		case LIST:
			success = commandList();
			break;

		case ANSWER_HELPER:
			success = commandAnswerHelper();
			break;
		default:
			break;
		}
		return  success;
	}

	private boolean commandAcceptRequest() {
		if (!hasValidPlayer()) return true;
		PlayerDataStorage.getPlayerData(player).request.ifPresent(r -> r.execute());
		return true;
	}

	private boolean commandRequestTeleportPlayer() {
		if(!hasValidPlayer()) return true;
		if (params.size() < 1) return false;
		Bukkit.getOnlinePlayers().stream()
			.filter(p -> p.getName().equalsIgnoreCase(params.get(0)))
			.findFirst()
			.ifPresent(p -> PlayerRequest.createTeleportToPlayerRequest(player).sendRequest(p));
		return true;
	}

	private boolean commandRequestTeleportNPC() {
		if(!hasValidPlayer()) return true;
		if (params.size() < 2) return false;
		try {
			Bukkit.getOnlinePlayers().stream()
				.filter(p -> p.getName().equalsIgnoreCase(params.get(0)))
				.findFirst()
				.ifPresent(p -> PlayerRequest.createTeleportToNPCRequest(player, Integer.parseInt(params.get(1))).sendRequest(p));
		} catch(NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean commandUnitUpdatePlayerQuestion(Commands command) {
		if (!hasValidPlayer()) return true;
		if (params.size() < 2) return false;
		Optional<Unit> unit = UnitStorage.findUnit(params.get(0).toUpperCase());
		if (!unit.isPresent()) return false;
		Integer position = 0;
		switch(command) {
		case UNIT_ADD_PLAYER_QUESTION:
			try {
				position = Integer.parseInt(params.get(1));
			} catch(NumberFormatException e) {
				e.printStackTrace();
				Message.ERROR_1.print(Arrays.asList(""), sender);
			}
			if (params.size() < 3) return false;
			List<String> list = new ArrayList<String>();
			for(int i = 2; i < params.size(); i++) {
				list.add(params.get(i));
			}
			unit.get().addPlayerQuestion(position, String.join(" ", list), player);
			break;
		case UNIT_EDIT_PLAYER_QUESTION:
			try {
				position = Integer.parseInt(params.get(1));
			} catch(NumberFormatException e) {
				e.printStackTrace();
				Message.ERROR_1.print(Arrays.asList(""), sender);
			}
			if (params.size() < 3) return false;
			List<String> list2 = new ArrayList<String>();
			for(int i = 2; i < params.size(); i++) {
				list2.add(params.get(i));
			}
			unit.get().editPlayerQuestion(position, String.join(" ", list2), player);
			break;
		case UNIT_REMOVE_PLAYER_QUESTION:
			try {
				position = Integer.parseInt(params.get(1));
			} catch(NumberFormatException e) {
				e.printStackTrace();
				Message.ERROR_1.print(Arrays.asList(""), sender);
			}
			unit.get().removePlayerQuestion(position, player);
			break;
		default:
			break;
		}

		Message.EDITOR_1.print(Arrays.asList(String.join(", ", unit.get().getPlayerQuestions())), player);
		return true;
	}

	private boolean commandUnitAddPlayer() {
		if(params.size() < 2) {
			return false;
		}
		String name = params.get(0).toUpperCase();
		try {
			Integer id = Integer.parseInt(params.get(1));
			UnitStorage.findUnit(name).ifPresent(unit -> unit.addPlayerNPC(id, sender));
		} catch(NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean commandUnitDelete() {
		if (0 < params.size()) {
			UnitStorage.unregisterUnit(params.get(0), sender);
			return true;
		}
		return false;
	}

	private boolean commandDeleteQuestion() {
		if (!hasValidPlayer()) {
			return true;
		}
		Optional<SentenceEditor> editor = PlayerDataStorage.getPlayerData(player).editor;
		if (editor.isPresent()) {
			editor.get().deleteQuestion(player);
		} else {
			Message.INVALID_1.print(Arrays.asList("Please select an ediable question"), player);
		}
		return true;
	}

	private boolean commandRemoveAnswer() {
		if (params.size() < 1) {
			return false;
		}
		if (!hasValidPlayer()) {
			return true;
		}
		String answer = String.join(" ", params);
		Optional<SentenceEditor> editor = PlayerDataStorage.getPlayerData(player).editor;
		if (editor.isPresent()) {
			editor.get().removeAnswer(answer, player);
		} else {
			Message.INVALID_1.print(Arrays.asList("Please select an ediable question"), player);
		}
		PlayerDataStorage.closeEditor(player);
		return true;
	}

	private boolean commandAddAnswer() {
		if (params.size() < 1) {
			return false;
		}
		if (!hasValidPlayer()) {
			return true;
		}
		String answer = String.join(" ", params);
		Optional<SentenceEditor> editor = PlayerDataStorage.getPlayerData(player).editor;
		if (editor.isPresent()) {
			editor.get().addAnswer(answer, player);
		} else {
			Message.INVALID_1.print(Arrays.asList("Please select an ediable question"), player);
		}
		PlayerDataStorage.closeEditor(player);
		return true;
	}

	private boolean commandUnitAddHelper() {
		if(params.size() < 2) {
			return false;
		}
		String name = params.get(0).toUpperCase();
		try {
			Integer id = Integer.parseInt(params.get(1));
			UnitStorage.findUnit(name).ifPresent(unit -> unit.addHelperNPC(id, sender));
		} catch(NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean commandUnitCreate() {
		if(params.size() < 2) {
			return false;
		}
		String unit = params.get(0).toUpperCase();
		Integer id = 0;
		try {
			id =  Integer.parseInt(params.get(1));
		} catch(NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		UnitStorage.addUnit(unit, id, sender);
		return true;
	}

	private boolean commandOpEmpty() {
		int id = Integer.parseInt(params.get(0));
		NPCUtility.findNPC(id).ifPresent(npc -> NPCUtility.changeNPCAsEmpty(npc));
		return true;
	}

	private boolean commandDebugInterval() {
		if (0 < params.size()) {
			try {
				Integer interval = Integer.parseInt(params.get(0));
				PublicCommandGame.interval = 20L * interval;
				PublicEventGame.interval = 20L * interval;
				PublicEmptyGame.interval = 20L * interval;
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			PublicCommandGame.interval = 20L * 30;
			PublicEventGame.interval = 20L * 20;
			PublicEmptyGame.interval = 20L * 30;
		}
		return true;
	}

	private boolean commandDebugMode() {
		if(!Settings.debugMessage) {
			Settings.debugMessage = true;
			Debug.printDebugMessage("[VRG Debug] Message = " + Settings.debugMessage + ", BC = " + Settings.debugMessageBroadcast, new Exception());
		} else {
			if(!Settings.debugMessageBroadcast) {
				Settings.debugMessageBroadcast = true;
				Debug.printDebugMessage("[VRG Debug] Message = " + Settings.debugMessage + ", BC = " + Settings.debugMessageBroadcast, new Exception());
			} else {
				Settings.debugMessage = false;
				Settings.debugMessageBroadcast = false;
				Debug.printDebugMessage("[VRG Debug] Message = " + Settings.debugMessage + ", BC = " + Settings.debugMessageBroadcast, new Exception());
			}
		}
		return true;
	}

	private boolean commandDictionaryBroadcast() {
		if (0 < params.size()) {
			Message.COMMAND_DICTIONARY_1.broadcast(Arrays.asList(String.join("+", params)));
		} else {
			Message.COMMAND_DICTIONARY_0.broadcast(null);
		}
		return true;
	}

	private boolean commandDictionary() {
		if (0 < params.size()) {
			String jisho = String.join("%20", params);
			String weblio = String.join("+", params);
			String alc = weblio;
			Message.COMMAND_DICTIONARY_1.print(Arrays.asList(jisho, weblio, alc), sender);
		} else {
			Message.COMMAND_DICTIONARY_0.print(null, sender);
		}
		return true;
	}

	public void printInfo() {
		command.printInfo(sender);
	}

	public boolean canPerform() {
		if (command.canPerform(sender)) {
			return true;
		}
		Message.COMMAND_NO_PERMISSION_1.print(Arrays.asList(""), sender);
		return false;
	}

	private boolean hasValidPlayer() {
		if (player == null) {
			return false;
		}
		return true;
	}

	public void printInvalidParams() {
		Message.COMMAND_INVALID_PARAMS_1.print(Arrays.asList(String.join(", ", params)), sender);
		command.printUsage(player);
	}

	private boolean commandAnswerHelper() {
		if (!hasValidPlayer()) {
			return true;
		}
		if (0 < params.size()) {
			Integer select = PlayerDataStorage.getPlayerData(player).getSelectId();
			Optional<Unit> unit = UnitStorage.findUnit(select);
			Optional<List<HolographicSentence>> sentences = unit.flatMap(u -> u.findHelperLS(select));
			if (sentences.isPresent()) {
				sentences.flatMap(ls -> ls.stream().filter(s -> s instanceof QuestionSentence).findFirst())
					.map(s -> (QuestionSentence) s)
					.ifPresent(q-> q.validate(String.join(" ", params), player));
				unit.ifPresent(u -> u.printAchievementRate(player));
				return true;
			}
			return true;
		}
		return false;
	}

	private boolean commandOpSave() {
		Message.OP_SAVE_.print(null, sender);
		VRGManager.savePlugin();
		return true;
	}

	private boolean commandOpReload() {
		Message.OP_RELOAD_.print(null, sender);
		VRGManager.loadPlugin();
		return true;
	}

	private boolean commandList() {
		if (!hasValidPlayer()) {
			return true;
		}
		player.openInventory(new UnitSelector(player).createInventory());
		return true;
	}

	private boolean commandDebugEffect() {
		Optional<Effect> effect = Stream.of(Effect.values())
			.filter(e -> e.name().equalsIgnoreCase(params.get(0)))
			.findFirst();
		int data = 0;
		float offsetX = 0;
		float offsetY = 0;
		float offsetZ = 0;
		float speed = 0;
		int count = 1;
		if (6< params.size()) {
			data = Integer.parseInt(params.get(1));
			offsetX = Float.valueOf(params.get(2));
			offsetY = Float.valueOf(params.get(3));
			offsetZ = Float.valueOf(params.get(4));
			speed = Float.valueOf(params.get(5));
			count = Integer.parseInt(params.get(6));
		}
		if(effect.isPresent()) {
			Integer id = Arrays.asList(Effect.values()).indexOf(effect.get());
			player.getWorld().spigot().playEffect(player.getLocation().clone().add(2,0,0), effect.get(), id, data, offsetX, offsetY, offsetZ, speed, count, 10);
			player.sendMessage(effect.get().name() + " data" + data + " X" + offsetX + " Y" + offsetY + " Z" + offsetZ + " speed" + speed + " count" + count);
		} else {
			List<Effect> effects = Arrays.asList(Effect.values()).stream().sorted().collect(Collectors.toList());
			Integer id = Integer.parseInt(params.get(0));
			Effect playing = effects.get(id);
			player.getWorld().spigot().playEffect(player.getLocation().clone().add(2,0,0), playing, id, data, offsetX, offsetY, offsetZ, speed, count, 10);
			player.sendMessage(playing.name());
		}
		return true;
	}

	private boolean commandDebugSound() {
		Optional<Sound> sound = Stream.of(Sound.values())
			.filter(s -> s.name().equalsIgnoreCase(params.get(0)))
			.findFirst();
		if (sound.isPresent()) {
			player.playSound(player.getLocation(), sound.get(), Float.valueOf(params.get(1)), Float.valueOf(params.get(2)));
		} else {
			List<Sound> sounds = Arrays.asList(Sound.values()).stream().sorted().collect(Collectors.toList());
			Integer id = Integer.valueOf(params.get(0));
			Sound playing = sounds.get(id);
			player.playSound(player.getLocation(), playing, Float.valueOf(params.get(1)), Float.valueOf(params.get(2)));
			player.sendMessage(playing.name());
		}
		return true;
	}

	private boolean commandEdit() {
		if (0 < params.size()) {
			Optional<SentenceEditor> optional = PlayerDataStorage.getPlayerData(player).editor;
			if (optional.isPresent()) {
				optional.get().edit(String.join(" ", params), player);
				PlayerDataStorage.closeEditor(player);
			} else {
				Message.INVALID_1.print(Arrays.asList("Please select an editable sentence"), player);
				player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
			}
		}
		return true;
	}

	private boolean commandDebugDebug() {
		return true;
	}

}



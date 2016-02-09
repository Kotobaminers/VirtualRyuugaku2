package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Commands;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Stage;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.StageStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HologramStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.QuestionSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceEditor;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;
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
		case UNIT_ADD_HELPER:
			success = commandUnitAddHelper();
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

		case INFO:
			success = commandInfo();
			break;
		case LIST:
			success = commandList();
			break;

		case ANSWER_GAME:
			success = commandAnswerGame();
			break;
		case ANSWER_CONVERSATION:
			success = commandAnswerHelper();
			break;
		default:
			break;
		}
		return  success;
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
		PlayerDataStorage.closeEditor(player);
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
		String unit = params.get(0).toUpperCase();
		Integer id = 0;
		try {
			id =  Integer.parseInt(params.get(1));
		} catch(NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		SentenceStorage.addHelperNPC(unit, id, sender);
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
		SentenceStorage.addHelperUnit(unit, id, sender);
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
		String[] opts = {""};
		Message.COMMAND_NO_PERMISSION_1.print(sender, opts);
		return false;
	}

	private boolean hasValidPlayer() {
		if (player == null) {
			String[] opts = {"Player == null"};
			Message.COMMON_INVALID_1.print(sender, opts);
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
			Optional<List<HolographicSentence>> sentences = SentenceStorage.findLS(PlayerDataStorage.getPlayerData(player).getSelectId());
			if (sentences.isPresent()) {
				sentences.flatMap(ls -> ls.stream().filter(s -> s instanceof QuestionSentence).findFirst())
					.map(s -> (QuestionSentence) s)
					.ifPresent(q-> q.validate(String.join(" ", params), player));
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
		List<String> names = new ArrayList<String>();
		for (Stage stage : StageStorage.getStages()) {
			names.add(stage.name);
		}
		Message.STAGE_LIST_1.print(Arrays.asList(String.join(" ", names)), sender);
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
			player.getWorld().spigot().playEffect(player.getLocation().clone().add(2,0,0), effect.get(), id, data, offsetX, offsetY, offsetZ, speed, count, 10);
			player.sendMessage(playing.name());
		}
		return true;
	}

	private boolean commandDebugSound() {
		Optional<Sound> sound = Stream.of(Sound.values())
			.filter(s -> s.name().equalsIgnoreCase(params.get(0)))
			.findFirst();
		if (sound.isPresent()) {
			player.playSound(player.getLocation(), sound.get(), 1f, 1f);
		} else {
			List<Sound> sounds = Arrays.asList(Sound.values()).stream().sorted().collect(Collectors.toList());
			Integer id = Integer.valueOf(params.get(0));
			Sound playing = sounds.get(id);
			player.playSound(player.getLocation(), playing, 1f, 1f);
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
		HologramStorage.updateHologram(NPCUtility.findNPC(Integer.parseInt(params.get(0))).get(), SentenceStorage.findHelperLS(Integer.parseInt(params.get(0))).get(), player);
		return true;
	}

	private boolean commandInfo() {
//		if (!hasValidPlayer()) {
//			return true;
//		}
//		if(0 < params.size()) {
//			String name = params.get(0).toUpperCase();
//			Stage stage;
//			try {
//				stage = Stage.createStage(name);
//			} catch (Exception e) {
//				e.printStackTrace();
//				return false;
//			}
//			if (0 < stage.getConversations().size()) {
//				DataPlayer data = DataManagerPlayer.getDataPlayer(player);
//
//				String npcs = stage.getNPCsTotal().toString();
//				String question = stage.getQuestionDoneByMax(data);
//				String conversation = stage.getConversationDoneByMax(data);
//				String keySentence = stage.getKeySentenceTotal().toString();
//				String score = stage.getScoreDoneByMax(data);
//				String[] opts = {name};
//				Message.STAGE_INFO_TITLE_1.print(sender, opts);
//				String[] opts2 = {npcs};

		//				Message.STAGE_INFO_NPC_1.print(sender, opts2);
//				String[] opts3 = {conversation};
//				Message.STAGE_INFO_CONVERSATION_1.print(sender, opts3);
//				String[] opts4 = {question};
//				Message.STAGE_INFO_QUESTION_1.print(sender, opts4);
//				String[] opts5 = {keySentence};
//				Message.STAGE_INFO_KEY_SENTENCE_1.print(sender, opts5);
//				String[] opts6 = {score};
//				Message.STAGE_INFO_SCORE_1.print(sender, opts6);
//				return true;
//			}
//		}
		return false;
	}
	private boolean commandAnswerGame() {
//		if (!hasValidPlayer()) {
//			return true;
//		}
//		String answer = "";
//		if (0 < params.size()) {
//			answer = UtilitiesGeneral.joinStrings(params, " ");
//			if(ControllerGameGlobal.isValidGame()) {
//				ControllerGameGlobal.validataAnswer(player, answer);
//				ControllerGameGlobal.updataScoreboard(player);
//			} else {
//				Message.GAME_PLEASE_LOAD_0.print(sender, null);
//			}
//			return true;
//		}
		return false;
	}
}



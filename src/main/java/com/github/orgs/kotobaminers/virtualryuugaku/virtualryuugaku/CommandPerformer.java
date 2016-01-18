package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Commands;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerVRG.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.LearnerNPCBook;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Stage;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.StageStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.VRGSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerData;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicCommandGame;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicEmptyGame;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicEventGame;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGameController;
import com.github.orgs.kotobaminers.virtualryuugaku.publictour.publictour.PublicTourController;
import com.github.orgs.kotobaminers.virtualryuugaku.publictour.publictour.PublicTourController0;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

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
		case MYSELF:
			break;
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

		case SAVE:
			success = commandSave();
			break;
		case RELOAD:
			success = commandReload();
			break;

		case EN:
			success = commandEN();
			break;
		case ROMAJI:
			success = commandRomaji();
			break;
		case KANA:
			success = commandKana();
			break;
		case KANJI:
			success = commandKanji();
			break;
		case DICTIONARY:
			success = commandDictionary();
			break;
		case DICTIONARY_BROADCAST:
			success = commandDictionaryBroadcast();
			break;

		case TP:
			success = commandTP();
			break;
		case INFO:
			success = commandInfo();
			break;
		case LIST:
			success = commandList();
			break;

		case GAME:
			break;
		case GAME_START:
			success = commandGameStart();
			break;
		case GAME_FINISH:
			success = commandGameFinish();
			break;
		case GAME_NEXT:
			success = commandGameNext();
			break;
		case GAME_RULE:
			success = commandGameRule();
			break;
		case GAME_JOIN:
			success = commandGameJoin();
			break;
		case GAME_REPEAT:
			success = commandGameRepeat();
			break;

		case TOUR_JOIN:
//			success = commandTourJoin();
			break;
		case TOUR_START:
//			success = commandTourStart();
			break;
		case TOUR_NEXT:
			success = commandTourNext();
			break;
		case TOUR_PREVIOUS:
			success = commandTourPrevious();
			break;

		case ANSWER_GAME:
			success = commandAnswerGame();
			break;
		case ANSWER_CONVERSATION:
			success = commandAnswerConversation();
			break;
		case ANSWER_PUBLIC_GAME:
			success = commandAnswerPublicGame();
			break;

		case MYSELF_BOOK:
			success = commandMyselfBook();
			break;
		case MYSELF_UPDATE:
//			success = commandMyselfUpdate();
			break;
//		case MYSELF_RELOAD:
//			success = commandMyselfReload();
//			break;

		default:
			break;
		}
		return  success;
	}

//	private boolean commandTourJoin() {
//		if (!hasValidPlayer()) {
//			return true;
//		}
//		PublicTourController.join(player);
//		return true;
//	}

//	private boolean commandTourStart() {
//		if (!hasValidPlayer()) {
//			return true;
//		}
//		if (0 < params.size()) {
//			PublicTourController.loadTour(params.get(0));
//			PublicTourController.join(player);
//			return true;
//		} else {
//			commandList();
//		}
//		return false;
//	}

	private boolean commandTourNext() {
		if (!hasValidPlayer()) {
			return true;
		}
		PublicTourController.continueNext(player);
		return true;
	}
	private boolean commandTourPrevious() {
		if (!hasValidPlayer()) {
			return true;
		}
		PublicTourController0.returnToPrevious(player);
		return true;
	}

	private boolean commandGameRepeat() {
		if (0 < PublicGameController.join.size()) {
			Message.GAME_STARTED_0.print(sender, null);
		} else {
			PublicGameController.loadLastStage();
			String[] opts = {PublicGameController.lastStage};
			Message.GAME_REPEATED_1.print(sender, opts);
			commandGameJoin();
		}
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

	private boolean commandGameJoin() {
		if (!hasValidPlayer()) {
			return true;
		}
		for (List<Integer> index : PublicGameController.stage.npcConversations.keySet()) {
			if (0 < index.size()) {
				try {
					Location location = NPCHandler.getNPC(index.get(0)).getStoredLocation();
					player.teleport(location.add(0, 1, 0));
					PublicGameController.joinPlayer(player);
					String[] opts = {player.getName(), PublicGameController.stage.name};
					Message.GAME_JOIN_TP_2.broadcast(opts);
					PublicGameController.game.giveCurrentQuestion(player);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
		}
		return true;
	}

	private boolean commandGameStart() {
		if (!hasValidPlayer()) {
			return true;
		}
		if (0 < params.size()) {
			for (String name : StageStorage.getStageNames()) {
				if (name.equalsIgnoreCase(params.get(0))) {
					PublicGameController.loadStage(name);
					PublicGameController.joinPlayer(player);
					return true;
				}
			}
		}
		commandList();
		return false;
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

	private boolean commandAnswerPublicGame() {
		if (!hasValidPlayer()) {
			return true;
		}
		String answer = "";
		if (0 < params.size()) {
			answer = Utility.joinStrings(params, " ");
			PublicGameController.game.validateAnswer(player, answer);
			return true;
		}
		return false;
	}

	private boolean commandDictionaryBroadcast() {
		if (0 < params.size()) {
			String[] opts = {Utility.joinStrings(params, "+")};
			Message.COMMAND_DICTIONARY_1.broadcast(opts);
		} else {
			Message.COMMAND_DICTIONARY_0.broadcast(null);
		}
		return true;
	}

	private boolean commandDictionary() {
		if (0 < params.size()) {
			String jisho = Utility.joinStrings(params, "%20");
			String weblio = Utility.joinStrings(params, "+");
			String alc = weblio;
			String[] opts = {jisho, weblio, alc};
			Message.COMMAND_DICTIONARY_1.print(sender, opts);
		} else {
			Message.COMMAND_DICTIONARY_0.print(sender, null);
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
		String[] opts = {Utility.joinStrings(params, ", ")};
		Message.COMMAND_INVALID_PARAMS_1.print(sender, opts);
		command.printUsage(player);
	}

	private boolean commandTP() {
		if (!hasValidPlayer()) {
			return true;
		}
		if(0 < params.size()) {
			String name = params.get(0).toUpperCase();
			for (Stage stage  : StageStorage.getStages()) {
				for (List<Integer> index : stage.npcConversations.keySet()) {
					if (0 < index.size()) {
						try {
							Location location = NPCHandler.getNPC(index.get(0)).getStoredLocation();
							player.teleport(location);
							String[] opts = {name};
							Message.COMMON_TP_1.print(player, opts);
							return true;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return false;
	}

	private boolean commandAnswerConversation() {
		if (!hasValidPlayer()) {
			return true;
		}
		String answer = "";
		if (0 < params.size()) {
			answer = Utility.joinStrings(params, " ");
			PlayerDataStorage.getDataPlayer(player).question.validateQuestion(player, answer);
			return true;
		}
		return false;
	}

	private boolean commandEN() {
		return commandToggleExpression(SpellType.EN);
	}
	private boolean commandRomaji() {
		return commandToggleExpression(SpellType.ROMAJI);
	}
	private boolean commandKana() {
		return commandToggleExpression(SpellType.KANA);
	}
	private boolean commandKanji() {
		return commandToggleExpression(SpellType.KANJI);
	}

	private boolean commandToggleExpression(SpellType expression) {
		if (!hasValidPlayer()) {
			return true;
		}

		PlayerData data = PlayerDataStorage.getDataPlayer(player);
		PlayerDataStorage.toggleExpression(data, expression);
		List<String> expressions = new ArrayList<String>();
		for(SpellType search : data.expressions) {
			expressions.add(search.toString());
		}
		Collections.sort(expressions);

		if(0 < expressions.size()) {
			String[] opts = {Utility.joinStrings(expressions, ", ")};
			Message.EXPRESSIONS_1.print(sender, opts);
		} else {
			Message.EXPRESSIONS_OFF_0.print(sender, null);
		}
		return true;
	}

	private boolean commandSave() {
		Message.OP_SAVE_.print(sender, null);
		VirtualRyuugakuManager.savePlugin();
		return true;
	}

	private boolean commandReload() {
		Message.OP_RELOAD_.print(sender, null);
		VirtualRyuugakuManager.loadPlugin();
		return true;
	}

	private boolean commandList() {
		List<String> names = new ArrayList<String>();
		for (Stage stage : StageStorage.getStages()) {
			names.add(stage.name);
		}
		String[] opts = {Utility.joinStrings(names, ", ")};
		Message.STAGE_LIST_1.print(sender, opts);
		return true;
	}

	private boolean commandMyselfBook() {
		if (!hasValidPlayer()) {
			return true;
		}
		if (0 < params.size()) {
			LearnerNPCBook.giveBlankBookItem(player, params.get(0));
			return true;
		}
		return false;
	}

	private boolean commandDebugEffect() {
		List<Effect> effects = Arrays.asList(Effect.values()).stream().sorted().collect(Collectors.toList());
		Integer id = Integer.valueOf(params.get(0));
		Effect playing = effects.get(id);
		Location location = NPCHandler.findNPC(10001).get().getStoredLocation();
		Utility.lookAt(player, location);
		player.getWorld().spigot().playEffect(location.add(0, 2, 0), playing, id, 10, 0, 0, 0, 0, 1, 10);
		player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);

//		player.getWorld().spigot().playEffect(location, effect, id, data, offsetX, offsetY, offsetZ, speed, particleCount, radius););
		player.sendMessage(playing.name());
		return true;
	}

	private boolean commandDebugDebug() {
		Utility.sendTitle(player, "Stage TMP", "aaa");
		return true;
	}

	public static Function<String, Optional<VRGSentence>> translatePageToSentence =
		(page) -> {
			List<String> lines = Arrays.asList(page.replaceAll("§0", "").split("\\n"));
			VRGSentence sentence = null;
			if (2 < lines.size()) {
				String kanji = "";
				if (3 < lines.size()) {
					kanji = lines.get(3);
				}
				sentence = new VRGSentence();
				sentence.description = Description.create(kanji, lines.get(2), lines.get(1), new ArrayList<String>());
			}
			return Optional.ofNullable(sentence);
		};

	private boolean commandGameFinish() {
//		if(ControllerGameGlobal.isValidGame()) {
//			ControllerGameGlobal.finishGame();
//		} else {
//			Message.GAME_PLEASE_LOAD_0.print(sender, null);;
//		}
		return true;
	}

	private boolean commandGameNext() {
//		if(ControllerGameGlobal.isValidGame()) {
//			ControllerGameGlobal.giveNextQuestion(player);
//		} else {
//			Message.GAME_PLEASE_LOAD_0.print(sender, null);;
//		}
		return true;
	}

	private boolean commandGameRule() {
//		ControllerGameGlobal.printRule(sender);
//		Commands.ANSWER_GAME.printUsage(sender);
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
	private boolean commandMyselfReload() {
//		if (1 < params.size()) {
//			String stageName = params.get(0).toUpperCase();
//			CheckState check = CheckState.lookup(params.get(1));
//			if (!check.equals(CheckState.NOT_EXISTS)) {
//				try {
//					StageMyself myself = Stage.createStageMyself(stageName);
//					myself.changeNPCs(check);
//					String[] opts = {stageName};
//					Message.NPC_CHANGE_1.print(sender, opts);
//					return true;
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
		return false;
	}

	private boolean commandMyselfUpdate() {
//		if (!hasValidPlayer()) {
//			return true;
//		}
//		ControllerConversation.importBook(player);
		return true;
	}

}



package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation.CheckState;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Stage;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.game.game.ControllerGameGlobal;
import com.github.orgs.kotobaminers.virtualryuugaku.myself.myself.ConversationBook;
import com.github.orgs.kotobaminers.virtualryuugaku.myself.myself.StageMyself;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Commands;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Expression;

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
		case VIRTUALRYUUGAKU_OP:
		case VIRTUALRYUUGAKU_TEACHER:
		case GAME:
		case VIRTUALRYUUGAKU:
		case ANSWER:
		case MYSELF:
			break;
		case SAVE:
			success = commandSave();
			break;
		case RELOAD:
			success = commandReload();
			break;

		case KEY:
			success = commandKey();
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

		case MYSELF_BOOK:
			success = commandMyselfBook();
			break;
		case MYSELF_UPDATE:
			success = commandMyselfUpdate();
			break;
		case MYSELF_RELOAD:
			success = commandMyselfReload();
			break;

		case ANSWER_GAME:
			success = commandAnswerGame();
			break;
		case ANSWER_CONVERSATION:
			success = commandAnswerConversation();
			break;
		default:
			break;
		}
		return  success;
	}



	private boolean commandDictionaryBroadcast() {
		if (0 < params.size()) {
			String[] opts = {UtilitiesGeneral.joinStrings(params, "+")};
			Message.COMMAND_DICTIONARY_1.broadcast(opts);
		} else {
			Message.COMMAND_DICTIONARY_0.broadcast(null);
		}
		return true;
	}

	private boolean commandDictionary() {
		if (0 < params.size()) {
			String jisho = UtilitiesGeneral.joinStrings(params, "%20");
			String weblio = UtilitiesGeneral.joinStrings(params, "+");
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
		String[] opts = {command.permission.toString()};
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
		String[] opts = {UtilitiesGeneral.joinStrings(params, ", ")};
		Message.COMMAND_INVALID_PARAMS_1.print(sender, opts);
		command.printUsage(player);
	}

	private boolean commandTP() {
		if (!hasValidPlayer()) {
			return true;
		}
		if(0 < params.size()) {
			String stage = params.get(0).toUpperCase();
			try {
				List<Conversation> conversations = ControllerConversation.getConversations(stage);
				List<Integer> ids = new ArrayList<Integer>();
				for (Conversation conversation : conversations) {
					if (0 < conversation.listTalk.size()) {
						ids.addAll(conversation.getIDSorted());
					}
				}
				if (0 < ids.size()) {
					Collections.sort(ids);
					System.out.println(ids);
					Integer id = ids.get(0);
					NPC npc = NPCHandler.getNPC(id);
					Location location = npc.getStoredLocation();
					player.teleport(location);
					String[] opts = {stage};
					Message.STAGE_TP_1.print(player, opts);
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private boolean commandGameStart() {
		if (0 < params.size()) {
			String stage = params.get(0);
			ControllerGameGlobal.loadGame(stage);
			if (ControllerGameGlobal.isValidGame()) {
				String[] opts = {stage};
				Message.GAME_JOIN_TP_1.broadcast(opts);
				ControllerGameGlobal.giveNextQuestion(sender);
				return true;
			}
		}
		return false;
	}

	private boolean commandGameFinish() {
		if(ControllerGameGlobal.isValidGame()) {
			ControllerGameGlobal.finishGame();
		} else {
			Message.GAME_PLEASE_LOAD_0.print(sender, null);;
		}
		return true;
	}

	private boolean commandGameNext() {
		if(ControllerGameGlobal.isValidGame()) {
			ControllerGameGlobal.giveNextQuestion(player);
		} else {
			Message.GAME_PLEASE_LOAD_0.print(sender, null);;
		}
		return true;
	}

	private boolean commandGameRule() {
		ControllerGameGlobal.printRule(sender);
		Commands.ANSWER_GAME.printUsage(sender);
		return true;
	}

	private boolean commandInfo() {
		if (!hasValidPlayer()) {
			return true;
		}
		if(0 < params.size()) {
			String name = params.get(0).toUpperCase();
			Stage stage;
			try {
				stage = Stage.createStage(name);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			if (0 < stage.getConversations().size()) {
				DataPlayer data = DataManagerPlayer.getDataPlayer(player);

				String npcs = stage.getNPCsTotal().toString();
				String question = stage.getQuestionDoneByMax(data);
				String conversation = stage.getConversationDoneByMax(data);
				String keySentence = stage.getKeySentenceTotal().toString();
				String score = stage.getScoreDoneByMax(data);
				String[] opts = {name};
				Message.STAGE_INFO_TITLE_1.print(sender, opts);
				String[] opts2 = {npcs};
				Message.STAGE_INFO_NPC_1.print(sender, opts2);
				String[] opts3 = {conversation};
				Message.STAGE_INFO_CONVERSATION_1.print(sender, opts3);
				String[] opts4 = {question};
				Message.STAGE_INFO_QUESTION_1.print(sender, opts4);
				String[] opts5 = {keySentence};
				Message.STAGE_INFO_KEY_SENTENCE_1.print(sender, opts5);
				String[] opts6 = {score};
				Message.STAGE_INFO_SCORE_1.print(sender, opts6);
				return true;
			}
		}
		return false;
	}

	private boolean commandList() {
		String course = UtilitiesGeneral.joinStrings(ControllerConversation.getStagesCourse(), ", ");
		String myself = UtilitiesGeneral.joinStrings(ControllerConversation.getStagesMyself(), ", ");
		String[] opts = {course, myself};
		Message.STAGE_LIST_2.print(sender, opts);
		return true;
	}

	private boolean commandMyselfBook() {
		if (!hasValidPlayer()) {
			return true;
		}
		if (0 < params.size()) {
			String stage = params.get(0);
			if (ControllerConversation.existsMyselfStage(stage)) {
				ConversationBook.giveConversationBookEmpty(player, stage);
				return true;
			}
		}
		return false;
	}

	private boolean commandMyselfUpdate() {
		if (!hasValidPlayer()) {
			return true;
		}
		ControllerConversation.importBook(player);
		return true;
	}

	private boolean commandAnswerGame() {
		if (!hasValidPlayer()) {
			return true;
		}
		String answer = "";
		if (0 < params.size()) {
			answer = UtilitiesGeneral.joinStrings(params, " ");
			if(ControllerGameGlobal.isValidGame()) {
				ControllerGameGlobal.validataAnswer(player, answer);
				ControllerGameGlobal.updataScoreboard(player);
			} else {
				Message.GAME_PLEASE_LOAD_0.print(sender, null);
			}
			return true;
		}
		return false;
	}
	private boolean commandAnswerConversation() {
		if (!hasValidPlayer()) {
			return true;
		}
		String answer = "";
		if (0 < params.size()) {
			answer = UtilitiesGeneral.joinStrings(params, " ");
			DataManagerPlayer.getDataPlayer(player).question.validateQuestion(player, answer);
			return true;
		}
		return false;
	}

	private boolean commandMyselfReload() {
		if (1 < params.size()) {
			String stageName = params.get(0).toUpperCase();
			CheckState check = CheckState.lookup(params.get(1));
			if (!check.equals(CheckState.NOT_EXISTS)) {
				try {
					StageMyself myself = Stage.createStageMyself(stageName);
					myself.changeNPCs(check);
					String[] opts = {stageName};
					Message.NPC_CHANGE_1.print(sender, opts);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	private boolean commandEN() {
		return commandToggleExpression(Expression.EN);
	}
	private boolean commandRomaji() {
		return commandToggleExpression(Expression.ROMAJI);
	}
	private boolean commandKana() {
		return commandToggleExpression(Expression.KANA);
	}
	private boolean commandKanji() {
		return commandToggleExpression(Expression.KANJI);
	}

	private boolean commandToggleExpression(Expression expression) {
		if (!hasValidPlayer()) {
			return true;
		}

		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		DataManagerPlayer.toggleExpression(data, expression);
		List<String> expressions = new ArrayList<String>();
		for(Expression search : data.expressions) {
			expressions.add(search.toString());
		}
		Collections.sort(expressions);

		if(0 < expressions.size()) {
			String[] opts = {UtilitiesGeneral.joinStrings(expressions, ", ")};
			Message.EXPRESSIONS_1.print(sender, opts);
		} else {
			Message.EXPRESSIONS_OFF_0.print(sender, null);
		}
		return true;
	}

	private boolean commandKey() {
		if (!hasValidPlayer()) {
			return true;
		}

		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		try {
			Talk talk = DataManagerPlayer.getTalk(data);
			talk.key = !talk.key;
			String[] opts = {"" + talk.key};
			Message.KEY_TOGGLE_1.print(player, opts);
		} catch (Exception e) {
			e.printStackTrace();
			Message.COMMON_NOT_SELECT_CONVERSATION_0.print(player, null);
		}
		return true;
	}

	private boolean commandSave() {
		Message.OP_SAVE_.print(sender, null);
		DataManagerPlugin.savePlugin();
		return true;
	}

	private boolean commandReload() {
		Message.OP_RELOAD_.print(sender, null);
		DataManagerPlugin.loadPlugin();
		return true;
	}
}



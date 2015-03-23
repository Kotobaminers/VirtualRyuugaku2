package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.comment.DataComment;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class MessengerGeneral {
	public static final String MARK_JP = ChatColor.RED +"*JP* " + ChatColor.RESET;
	public static final String MARK_EN = ChatColor.AQUA +"*EN* " + ChatColor.RESET;
	private static final String MESSENGER_PREFIX = "" + ChatColor.GOLD + ChatColor.BOLD +"[" + ChatColor.YELLOW + "VRG" + ChatColor.GOLD + ChatColor.BOLD + "] " + ChatColor.RESET;
	private static final String getPartition(String mark1, String mark2, ChatColor color1, ChatColor color2) {
		String partition = "  ";
		for(int i = 0; i < 8; i++) {
			partition += color1 + mark1 + " " + color2 + mark2 + " ";
		}
		partition += color1 + mark1;
		return partition;
	}
	public static final String getPartitionDefault() {
		return getPartition("=", "*", ChatColor.DARK_GREEN, ChatColor.GREEN);
	}

	public enum Message {
		CANT_EDIT_TALKER_0,
		EDITED_TALKER_0, SET_LANGUAGE_1,
		OWN_TALKER_0,
		ALREADY_OWNED_0,
		SELECT_TALKER_1,
		NO_EXPRESSION_LINE_LANG_2,
		NO_QUESTION_LANG_1,
		NO_SENTENCE_0,
		GOT_NEW_MESSAGE_1,
		SHOW_COMMENT_2,
		INVALID_TALKER_1,
		NEW_COMMENT_4,
		SENT_COMMENT_0,
		NO_NEW_COMMENT_0,
		NO_COMMENT_0,
		DONE_COMMENT_1,
		CONVERSATION_SPEAK_2,
		CONVERSATION_QUESTION_1,
		CONVERSATION_TALK_START_1,
		CONVERSATION_TALK_FINISH_0,
		CONVERSATION_INFO_LABEL_1,
		CONVERSATION_INFO_DATA_3,
		CONVERSATION_INFO_SENTENCE_3,
		CONVERSATION_KEY_SENTENCE_EN_1,
		CONVERSATION_KEY_SENTENCE_JP_1,
		CONVERSATION_KEY_SENTENCE_1,
		STAGE_INVALID_1,
		STAGE_RUNNING_0,
		STAGE_NOT_RUNNING_0,
		STAGE_CORRECT_1,
		STAGE_WRONG_0,
		STAGE_FINISH_1,
		TELEPORT_0,
		STAGE_QUESTION_2,
		FIND_PEOPLE_MISSION_0,
		FIND_PEOPLE_QUEST_1,
		FIND_PEOPLE_CORRECT_0,
		FIND_PEOPLE_WRONG_0,

		FIND_PEOPLE_FINISH_2,
		FIND_PEOPLE_REMOVE_1,

		COMMAND_VRG_JAPANESE_1,
		COMMAND_VRG_LANGUAGE_1,
		COMMAND_VRG_EXPRESSIONS_1,
		COMMAND_VRG_EXPRESSIONS_OFF_0,
		DESCRIPTION_EN_2,
		DESCRIPTION_JP_2,
		;
	}

	public static String getMessage(Message key, String[] opts) {
		String message = MESSENGER_PREFIX;
		switch(key) {
		case CANT_EDIT_TALKER_0: message += "You can't edit this talker."; break;
		case EDITED_TALKER_0: message += "This talker was edited."; break;
		case SET_LANGUAGE_1: message += "You set your language in " + opts[0] + "."; break;
		case OWN_TALKER_0: message += "You own this talker."; break;
		case ALREADY_OWNED_0: message += "You have already owned a talker in this stage."; break;
		case SELECT_TALKER_1: message += "You selected " + opts[0] + ChatColor.RESET + "."; break;
		case NO_EXPRESSION_LINE_LANG_2: message += "No expression in line " + opts[0] + " in " + opts[1] + "."; break;
		case NO_QUESTION_LANG_1: message += "No question in " + opts[0] + "."; break;
		case NO_SENTENCE_0: message += "The talker has no sentence."; break;
		case GOT_NEW_MESSAGE_1: message += "You got " + opts[0] + " new messages. /talker comment new"; break;
		case SHOW_COMMENT_2: message += "Showing comments for " + opts[0] + ChatColor.RESET + "(ID: " + opts[1] + ")."; break;
		case INVALID_TALKER_1: message += "Invalid talker(ID: " + opts[0] + ")."; break;
		case NEW_COMMENT_4: message += opts[0] + " new comments with " +opts[1] + ChatColor.RESET + "(ID: " + opts[2] + "). From: " + opts[3] + ")."; break;
		case SENT_COMMENT_0: message += "You sent a comment."; break;
		case NO_NEW_COMMENT_0: message += "You don't have any new comments."; break;
		case NO_COMMENT_0: message += "The selected talker doesn't have any comments."; break;
		case DONE_COMMENT_1: message += "The comment from " + opts[0] + "'s state was switched to " + DataComment.CommentState.DONE.toString() + "."; break;
		case CONVERSATION_SPEAK_2: message = opts[0] + ChatColor.GREEN + " \"" + ChatColor.RESET + opts[1] + ChatColor.GREEN + "\"" + ChatColor.RESET; break;
		case CONVERSATION_QUESTION_1: message += "[Question] " + opts[0]; break;
		case CONVERSATION_TALK_START_1: message += "The conversation has started."; break;
		case CONVERSATION_TALK_FINISH_0: message += ChatColor.RED + "The conversation is finished." + ChatColor.RESET; break;//Expression from ALC.
		case CONVERSATION_INFO_LABEL_1: message = ChatColor.GOLD +  "[Talker] " + ChatColor.RESET + opts[0] + ChatColor.RESET; break;//Without prefix
		case CONVERSATION_INFO_DATA_3: message = " ID: " + opts[0] + ", EDITOR: " + opts[1] + ", STAGE: " + opts[2]; break;//Without prefix
		case CONVERSATION_INFO_SENTENCE_3: message = " SENT(" + opts[0] + ") " + opts[1] + ": " + opts[2]; break;//Without prefix
		case CONVERSATION_KEY_SENTENCE_1: message += opts[0] + ChatColor.RESET + "'s Key Sentence."; break;
		case CONVERSATION_KEY_SENTENCE_EN_1: message = " EN: " + ChatColor.GOLD + " \"" + ChatColor.RESET + opts[0] + ChatColor.GOLD + "\"" + ChatColor.RESET; break;//Without prefix
		case CONVERSATION_KEY_SENTENCE_JP_1: message = " JP: " + ChatColor.GOLD + " \"" + ChatColor.RESET + opts[0] + ChatColor.GOLD + "\"" + ChatColor.RESET; break;//Without prefix
		case STAGE_INVALID_1: message += "Invalid stage name(" + opts[0] + ")"; break;
		case STAGE_RUNNING_0: message += "A game is already running right now. Try later."; break;
		case STAGE_NOT_RUNNING_0: message += "No game is running for now."; break;
		case STAGE_QUESTION_2: message = " [Q] What is \"" + opts[0] + "\" in " + opts[1] + "?"; break;
		case STAGE_CORRECT_1: message += ChatColor.GREEN + "Correct Answer!" + ChatColor.RESET + " (Answers: " + opts[0] + ")"; break;
		case STAGE_WRONG_0: message += ChatColor.DARK_RED + "Wrong Answer!"; break;
		case STAGE_FINISH_1: message += ChatColor.GREEN + opts[0] + " successfully completed the stage!"; break;
		case TELEPORT_0: message += "Teleporting..."; break;
		case FIND_PEOPLE_MISSION_0: message += ChatColor.LIGHT_PURPLE + "Find the Person!"; break;
		case FIND_PEOPLE_QUEST_1: message = ChatColor.GOLD + "???" + ChatColor.RESET + ": " + opts[0] ; break;
		case FIND_PEOPLE_CORRECT_0: message += ChatColor.GREEN + "Correct!"; break;
		case FIND_PEOPLE_WRONG_0: message += ChatColor.DARK_RED + "Wrong!"; break;
		case FIND_PEOPLE_FINISH_2: message += ChatColor.GREEN + opts[0] + " successufully finished the game!" + ChatColor.RESET + " (FindPpl: " + opts[1] + ")"; break;

		case FIND_PEOPLE_REMOVE_1: message = " [FindPpl: " + opts[0] + "] Removed the game!"; break;
		case COMMAND_VRG_JAPANESE_1: message += "Your Japanese mode is set as " + opts[0] + "."; break;
		case COMMAND_VRG_LANGUAGE_1: message += "Your learning language is set as " + opts[0] + "."; break;
		case COMMAND_VRG_EXPRESSIONS_1: message += "Your current languages are " + opts[0] + "."; break;
		case COMMAND_VRG_EXPRESSIONS_OFF_0: message += "Your current VRG output is OFF."; break;
		case DESCRIPTION_EN_2: message = opts[0] + ChatColor.RESET + ": " + opts[1] + " " + MARK_EN; break;
		case DESCRIPTION_JP_2: message = opts[0] + ChatColor.RESET + ": " + opts[1] + " " + MARK_JP; break;
		default: break;
		}
		return message;
	}

	@Deprecated
	public static void print(Player player, Message key, String[] opts) {
		String message = MESSENGER_PREFIX;
		switch(key) {
		case CANT_EDIT_TALKER_0: message += "You can't edit this talker."; break;
		case EDITED_TALKER_0: message += "This talker was edited."; break;
		case SET_LANGUAGE_1: message += "You set your language in " + opts[0] + "."; break;
		case OWN_TALKER_0: message += "You own this talker."; break;
		case ALREADY_OWNED_0: message += "You have already owned a talker in this stage."; break;
		case SELECT_TALKER_1: message += "You selected " + opts[0] + ChatColor.RESET + "."; break;
		case NO_EXPRESSION_LINE_LANG_2: message += "No expression in line " + opts[0] + " in " + opts[1] + "."; break;
		case NO_QUESTION_LANG_1: message += "No question in " + opts[0] + "."; break;
		case NO_SENTENCE_0: message += "The talker has no sentence."; break;
		case GOT_NEW_MESSAGE_1: message += "You got " + opts[0] + " new messages. /talker comment new"; break;
		case SHOW_COMMENT_2: message += "Showing comments for " + opts[0] + ChatColor.RESET + "(ID: " + opts[1] + ")."; break;
		case INVALID_TALKER_1: message += "Invalid talker(ID: " + opts[0] + ")."; break;
		case NEW_COMMENT_4: message += opts[0] + " new comments with " +opts[1] + ChatColor.RESET + "(ID: " + opts[2] + "). From: " + opts[3] + ")."; break;
		case SENT_COMMENT_0: message += "You sent a comment."; break;
		case NO_NEW_COMMENT_0: message += "You don't have any new comments."; break;
		case NO_COMMENT_0: message += "The selected talker doesn't have any comments."; break;
		case DONE_COMMENT_1: message += "The comment from " + opts[0] + "'s state was switched to " + DataComment.CommentState.DONE.toString() + "."; break;
		case CONVERSATION_SPEAK_2: message = opts[0] + ChatColor.GREEN + " \"" + ChatColor.RESET + opts[1] + ChatColor.GREEN + "\"" + ChatColor.RESET; break;
		case CONVERSATION_QUESTION_1: message += "[Question] " + opts[0]; break;
		case CONVERSATION_TALK_START_1: message += "The conversation has started."; break;
		case CONVERSATION_TALK_FINISH_0: message += ChatColor.RED + "The conversation is finished." + ChatColor.RESET; break;//Expression  from ALC.
		case CONVERSATION_INFO_LABEL_1: message = ChatColor.GOLD +  "[Talker] " + ChatColor.RESET + opts[0] + ChatColor.RESET; break;//Without prefix
		case CONVERSATION_INFO_DATA_3: message = " ID: " + opts[0] + ", EDITOR: " + opts[1] + ", STAGE: " + opts[2]; break;//Without prefix
		case CONVERSATION_INFO_SENTENCE_3: message = " SENT(" + opts[0] + ") " + opts[1] + ": " + opts[2]; break;//Without prefix
		case CONVERSATION_KEY_SENTENCE_1: message += opts[0] + ChatColor.RESET + "'s Key Sentence."; break;
		case CONVERSATION_KEY_SENTENCE_EN_1: message = " EN: " + ChatColor.GOLD + " \"" + ChatColor.RESET + opts[0] + ChatColor.GOLD + "\"" + ChatColor.RESET; break;//Without prefix
		case CONVERSATION_KEY_SENTENCE_JP_1: message = " JP: " + ChatColor.GOLD + " \"" + ChatColor.RESET + opts[0] + ChatColor.GOLD + "\"" + ChatColor.RESET; break;//Without prefix
		case STAGE_INVALID_1: message += "Invalid stage name(" + opts[0] + ")"; break;
		case STAGE_RUNNING_0: message += "A game is already running right now. Try later."; break;
		case STAGE_NOT_RUNNING_0: message += "No game is running for now."; break;
		case TELEPORT_0: message += "Teleporting..."; break;
		default: break;
		}
		player.sendMessage(message);
	}

	public static void print(List<Player> listPlayer, String message) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(Player player : listPlayer) {
			print(player, message);
		}
	}
	public static void print(Player player, String message) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(player.isOnline()) {
			player.sendMessage(message);
		}
	}
	public static void broadcast(String message) {
		UtilitiesGeneral.sendMessageAll(message);
	}

	public static void printReadComment(Player player, DataComment data, Integer line) {
		String info = "[Comment: " + line + "] FROM: " + data.sender + ", STATE: " + data.state;
		String expression = "  " + data.expression;
		player.sendMessage(info);
		player.sendMessage(expression);
	}

}
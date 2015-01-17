package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.talker.comment.DataComment;

public class MessengerGeneral {
	private static final String MESSENGER_PREFIX = "" + ChatColor.GOLD + ChatColor.BOLD +"[" + ChatColor.YELLOW + "VRG" + ChatColor.GOLD + ChatColor.BOLD + "] " + ChatColor.RESET;
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
		DONE_COMMENT_1
		;
	}
	public static void print(Player player, Message key, String[] opts) {
		String message = MESSENGER_PREFIX;
		switch(key) {
		case CANT_EDIT_TALKER_0: message += "You can't edit this talker."; break;
		case EDITED_TALKER_0: message += "This talker was edited."; break;
		case SET_LANGUAGE_1: message += "You set your language in " + opts[0] + "."; break;
		case OWN_TALKER_0: message += "You own this talker."; break;
		case ALREADY_OWNED_0: message += "You have already owned a talker in this stage."; break;
		case SELECT_TALKER_1: message += "You selected " + opts[0] + "."; break;
		case NO_EXPRESSION_LINE_LANG_2: message += "No expression in line " + opts[0] + " in " + opts[1] + "."; break;
		case NO_QUESTION_LANG_1: message += "No question in " + opts[0] + "."; break;
		case NO_SENTENCE_0: message += "The talker has no sentence."; break;
		case GOT_NEW_MESSAGE_1: message += "You got " + opts[0] + " new messages. /talker comment new"; break;
		case SHOW_COMMENT_2: message += "Showing comments for " + opts[0] + "(ID: " + opts[1] + ")."; break;
		case INVALID_TALKER_1: message += "Invalid talker(ID: " + opts[0] + ")."; break;
		case NEW_COMMENT_4: message += opts[0] + " new comments with " +opts[1] + "(ID: " + opts[2] + "). From: " + opts[3] + ")."; break;
		case SENT_COMMENT_0: message += "You sent a comment."; break;
		case NO_NEW_COMMENT_0: message += "You don't have any new comments."; break;
		case NO_COMMENT_0: message += "The selected talker doesn't have any comments."; break;
		case DONE_COMMENT_1: message += "The comment from " + opts[0] + "'s state was switched to " + DataComment.CommentState.DONE.toString() + "."; break;
		default: break;
		}
		player.sendMessage(message);
	}

	public static void printReadComment(Player player, DataComment data, Integer line) {
		String info = "[Comment: " + line + "] FROM: " + data.sender + ", STATE: " + data.state;
		String expression = "  " + data.expression;
		player.sendMessage(info);
		player.sendMessage(expression);
	}
}
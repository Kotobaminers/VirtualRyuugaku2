package com.github.kotobaminers.virtualryuugaku.common.common;

import org.bukkit.entity.Player;

public class Messenger {
	private static final String MESSENGER_PREFIX = "[VRG] ";
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
		case GOT_NEW_MESSAGE_1: message += "You got " + opts[0] + " new messages. /talker comment read"; break;
		case SHOW_COMMENT_2: message += "Comments for the selected talker(NAME: " + opts[0] + ", ID: " + opts[1] + ") are shown."; break;
		default: break;
		}
		player.sendMessage(message);
	}
}
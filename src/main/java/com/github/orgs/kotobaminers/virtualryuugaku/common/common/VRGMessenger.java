package com.github.orgs.kotobaminers.virtualryuugaku.common.common;


import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VRGMessenger {
	public static final String MARK_KEY = ChatColor.YELLOW +"*KEY* " + ChatColor.RESET;
	private static final String getPartition(String mark1, String mark2, ChatColor color1, ChatColor color2) {
		String partition = "  ";
		for(int i = 0; i < 8; i++) {
			partition += color1 + mark1 + " " + color2 + mark2 + " ";
		}
		partition += color1 + mark1;
		return partition;
	}
	private static final String getPartition(String mark1, String mark2, ChatColor color1, ChatColor color2, Integer num) {
		String partition = "  ";
		int count = 8;
		if(0 < num) {
			count = num;
		}
		for(int i = 0; i < count; i++) {
			partition += color1 + mark1 + " " + color2 + mark2 + " ";
		}
		partition += color1 + mark1;
		return partition;
	}
	public static final String getPartitionGreen() {
		return getPartition("=", "*", ChatColor.DARK_GREEN, ChatColor.GREEN);
	}
	public static final String getPartitionPurple() {
		return getPartition("=", "*", ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE);
	}
	public static final String getPartitionGray() {
		return getPartition("=", "*", ChatColor.DARK_GRAY, ChatColor.GRAY);
	}

	public enum Prefix {
		VRG("" + ChatColor.GOLD + ChatColor.BOLD +"[" + ChatColor.YELLOW + "VRG" + ChatColor.GOLD + ChatColor.BOLD + "] " + ChatColor.RESET),
		NONE(""),
		MINI_GAME("" + ChatColor.GOLD + ChatColor.BOLD +"[" + ChatColor.YELLOW + "MiniGame" + ChatColor.GOLD + ChatColor.BOLD + "] " + ChatColor.RESET),
		ERROR("" + ChatColor.DARK_RED + ChatColor.BOLD + "[VRG Error] " + ChatColor.RESET),
		INVALID("" + ChatColor.RED + ChatColor.BOLD + "[VRG Invalid] " + ChatColor.RESET),
		EDITOR("" + ChatColor.DARK_GREEN + ChatColor.BOLD +"[" + ChatColor.GREEN + "VRG Editor" + ChatColor.DARK_GREEN + ChatColor.BOLD + "] " + ChatColor.RESET),
		REQUEST("" + ChatColor.DARK_GREEN + ChatColor.BOLD +"[" + ChatColor.GREEN + "VRG Request" + ChatColor.DARK_GREEN + ChatColor.BOLD + "] " + ChatColor.RESET),
			;

		private String prefix = "";
		private Prefix(String prefix) {
			this.prefix = prefix;
		}
		public String getPrefix() {
			return prefix;
		}
	}

	public enum Message {
		NONE_1(Arrays.asList(""), Prefix.NONE),

		OP_SAVE_(Arrays.asList("Saving VirtualRyuugaku2..."), Prefix.VRG),
		OP_RELOAD_(Arrays.asList("Loading VirtualRyuugaku2..."), Prefix.VRG),

		COMMON_INVALID_1(Arrays.asList("Invalid: "), Prefix.VRG),
		COMMON_NOT_AIR_IN_HAND_0(Arrays.asList("Please hold nothing."), Prefix.VRG),
		COMMON_WRONG_0(Arrays.asList(ChatColor.RED + "Wrong!"), Prefix.VRG),
		COMMON_CORRECT_0(Arrays.asList(ChatColor.GREEN + "Correct!"), Prefix.VRG),
		COMMON_NOT_SELECT_CONVERSATION_0(Arrays.asList("You haven't select any NPCs."), Prefix.VRG),

		VRG_1(Arrays.asList(""), Prefix.VRG),
		ERROR_1(Arrays.asList(""), Prefix.ERROR),
		INVALID_1(Arrays.asList(""), Prefix.INVALID),
		EDITOR_1(Arrays.asList(""), Prefix.EDITOR),
		COMMON_TP_1(Arrays.asList("Teleporting to " + ChatColor.AQUA), Prefix.VRG),
		REQUEST_1(Arrays.asList(""), Prefix.REQUEST),


		CONVERSATION_QUESTION_1(Arrays.asList("" + ChatColor.GOLD + "[Question] "), Prefix.NONE),
		CONVERSATION_FINISH_0(Arrays.asList(ChatColor.GREEN + "You have finished the new conversation."), Prefix.VRG),
		CONEVRSATION_FINISH_ALL_1(Arrays.asList(ChatColor.GREEN + "You have finished all of the conversation(", ")."), Prefix.VRG),

		TALK_TITLE_1(Arrays.asList(
				ChatColor.GRAY + "\n === ",
				ChatColor.GRAY + " === "),
				Prefix.NONE),

		GAME_RULE_TITLE_0(Arrays.asList("" + ChatColor.GREEN + ChatColor.BOLD + "The Rules of Global Games"), Prefix.NONE),
		GAME_RULE_2(Arrays.asList(" ", " : "), Prefix.NONE),
		GAME_PLEASE_LOAD_0(Arrays.asList("Please load a global game at first."), Prefix.VRG),
		GAME_JOIN_TP_2(Arrays.asList("" + ChatColor.YELLOW, ChatColor.RESET + " joined the Minigame! (", ") [/vrg game join]"), Prefix.VRG),
		GAME_FIND_PEOPLE_TITLE_0(Arrays.asList("Find and left-click on the person!"), Prefix.MINI_GAME),
		GAME_FINISH_0(Arrays.asList("Finished!"), Prefix.MINI_GAME),
		GAME_STARTING_1(Arrays.asList("Starting! ( ", " )"), Prefix.MINI_GAME),
		GAME_NEW_STAGE_1(Arrays.asList("A New Game Will Start Soon! (",  ") [/vrg game join]"), Prefix.MINI_GAME),
		GAME_QUESTION_2(Arrays.asList("What is ", " in ", "?"), Prefix.MINI_GAME),
		GAME_TRY_FINISH_0(Arrays.asList("There is no next sentence. Please finish the game. /vrg game finish"), Prefix.VRG),
		GAME_REPEAT_0(Arrays.asList("Wanna try the stage again? [/vrg game repeat]"), Prefix.MINI_GAME),
		GAME_REPEATED_1(Arrays.asList("The last stage was repeated! (", ")"), Prefix.MINI_GAME),
		GAME_STARTED_0(Arrays.asList(ChatColor.RED + "The game has already started!"), Prefix.MINI_GAME),
		GAME_ANSWER_PUBLIC_0(Arrays.asList("To answer [ /ans p <ANSWER> ]"), Prefix.NONE),

		TOUR_JOIN_TP_2(Arrays.asList("" + ChatColor.YELLOW, ChatColor.RESET + " joined the tour! (", ") [/vrg tour join]"), Prefix.VRG),
		TOUR_TRY_MINIGAME_1(Arrays.asList("Wanna try minigames here? [/vrg game start ", "]"), Prefix.MINI_GAME),

		STAGE_INFO_TITLE_1(Arrays.asList(
				getPartition("=", "*", ChatColor.GOLD, ChatColor.YELLOW, 3) + "  " + ChatColor.GOLD + ChatColor.BOLD,
				getPartition("=", "*", ChatColor.GOLD, ChatColor.YELLOW, 3)),
				Prefix.NONE),
		STAGE_INFO_NPC_1(Arrays.asList(" NPCs: "), Prefix.NONE),
		STAGE_INFO_CONVERSATION_1(Arrays.asList(" Conversations: "), Prefix.NONE),
		STAGE_INFO_QUESTION_1(Arrays.asList(" Questions: "), Prefix.NONE),
		STAGE_INFO_KEY_SENTENCE_1(Arrays.asList(" Key Sentences: "), Prefix.NONE),
		STAGE_INFO_SCORE_1(Arrays.asList(" Minigame: "), Prefix.NONE),


		STAGE_LIST_1(Arrays.asList(ChatColor.AQUA + "Stages: " + ChatColor.RESET), Prefix.VRG),

		BOOK_GET_0(Arrays.asList("You got a new book. Enjoy writing!"), Prefix.VRG),
		BOOK_INVALID_0(Arrays.asList("Invalid book. Check how to write one."), Prefix.VRG),
		BOOK_IMPORTED_0(Arrays.asList("Your sentences are successfully imported."), Prefix.VRG),
		BOOK_NOT_YOURS_0(Arrays.asList("This book is not yours."), Prefix.VRG),
		BOOK_NOT_IN_HAND_0(Arrays.asList("Please Hold a written book in your hand."), Prefix.VRG),

		NPC_CHANGE_1(Arrays.asList("Some NPCs may be Changed (", ")."), Prefix.VRG),

		COMMAND_HELP_TITLE_0(Arrays.asList(
				getPartition("=", "*", ChatColor.GOLD, ChatColor.YELLOW, 3) + "  " +
				ChatColor.GOLD + ChatColor.BOLD + "Command Help" +
				getPartition("=", "*", ChatColor.GOLD, ChatColor.YELLOW, 3) + ChatColor.RESET),
				Prefix.NONE),
		COMMAND_HELP_2(Arrays.asList("", ChatColor.GRAY + "  :  " + ChatColor.RESET), Prefix.NONE),
		COMMAND_INVALID_PARAMS_1(Arrays.asList(ChatColor.RED + "Invalid parameters: " + ChatColor.RESET), Prefix.VRG),
		COMMAND_NO_PERMISSION_1(Arrays.asList("You don't have the permission: "), Prefix.VRG),

		EXPRESSIONS_1(Arrays.asList("Current display mode: "), Prefix.VRG),
		EXPRESSIONS_OFF_0(Arrays.asList("Current display mode: OFF"), Prefix.VRG),

		KEY_TOGGLE_1(Arrays.asList("You toggled the selected sentence as key or not: "), Prefix.VRG),
		COMMAND_DICTIONARY_1(Arrays.asList("[Jisho] http://jisho.org/search/", "\n[Weblio] http://ejje.weblio.jp/content/", "\n[ALC] http://eow.alc.co.jp/search?q="), Prefix.NONE),
		COMMAND_DICTIONARY_0(Arrays.asList(
				"[Jisho] http://jisho.org/"
				+ "\n[Weblio] http://ejje.weblio.jp/"
				+ "\n[Alc] http://www.alc.co.jp/"
				+ "\n[GoogleTranslate] https://translate.google.com/"
				), Prefix.NONE),

//		(Arrays.asList(""), Prefix.VRG),
		;

		private List<String> messages = Arrays.asList("");
		private Prefix prefix = Prefix.NONE;
		private Message(List<String> messages, Prefix prefix) {
			this.messages = messages;
			this.prefix = prefix;
		}

		private String getMessage(List<String> opts) {
			String message = "";
			for (int i = 0; i < messages.size(); i++) {
				message += messages.get(i);
				if(opts == null) {
					return message;
				} else if (i < opts.size()) {
					message += opts.get(i);
				}
			}
			return message;
		}

		public void print(List<String> opts, CommandSender sender) {
			if(sender != null) {
				sender.sendMessage(prefix.getPrefix() + getMessage(opts));
			}
		}
		public void print(List<String> opts, CommandSender[] senders) {
			if(senders != null) {
				for (CommandSender sender : senders) {
					print(opts, sender);
				}
			}
		}
		public void broadcast(List<String> opts) {
			for(Player player: Bukkit.getServer().getOnlinePlayers()) {
				print(opts, player);
			}
		}

		@Deprecated
		public void print(CommandSender[] senders, String[] opts) {
			if (senders != null) {
				for (CommandSender sender : senders) {
					print(sender, opts);
				}
			}
		}
		@Deprecated
		public void print(CommandSender sender, String[] opts) {
			if(sender != null) {
				sender.sendMessage(prefix.getPrefix() + getMessage(opts));
			}
		}
		@Deprecated
		private String getMessage(String[] opts) {
			if (opts == null) {
				return "";
			}
			return getMessage(Arrays.asList(opts));
		}
	}
}











































//private static final String MESSENGER_PREFIX = "" + ChatColor.GOLD + ChatColor.BOLD +"[" + ChatColor.YELLOW + "VRG" + ChatColor.GOLD + ChatColor.BOLD + "] " + ChatColor.RESET;

//	public enum Message0 {
//		CANT_EDIT_TALKER_0,
//		EDITED_TALKER_0, SET_LANGUAGE_1,
//		OWN_TALKER_0,
//		ALREADY_OWNED_0,
//		SELECT_TALKER_1,
//		NO_EXPRESSION_LINE_LANG_2,
//		NO_QUESTION_LANG_1,
//		NO_SENTENCE_0,
//		GOT_NEW_MESSAGE_1,
//		SHOW_COMMENT_2,
//		INVALID_TALKER_1,
//		NEW_COMMENT_4,
//		SENT_COMMENT_0,
//		NO_NEW_COMMENT_0,
//		NO_COMMENT_0,
//		DONE_COMMENT_1,
//
//		CONVERSATION_SPEAK_2,
//		CONVERSATION_QUESTION_1,
//		CONVERSATION_QUESTION_COMPLITE_1,
//		CONVERSATION_TALK_START_1,
//		CONVERSATION_TALK_FINISH_0,
//		CONVERSATION_INFO_LABEL_1,
//		CONVERSATION_INFO_DATA_3,
//		CONVERSATION_INFO_SENTENCE_3,
//		CONVERSATION_KEY_SENTENCE_EN_1,
//		CONVERSATION_KEY_SENTENCE_JP_1,
//		CONVERSATION_KEY_SENTENCE_1,
//
//		STAGE_INVALID_1,
//		STAGE_RUNNING_0,
//		STAGE_NOT_RUNNING_0,
//		STAGE_CORRECT_1,
//		STAGE_WRONG_0,
//		STAGE_FINISH_1,
//		STAGE_LIST_1,
//		STAGE_INFO_6,
//		STAGE_TP_1,
//
//		TELEPORT_0,
//		STAGE_QUESTION_2,
//		CORRECT_0,
//		WRONG_0,
//
//		FIND_PEOPLE_FINISH_2,
//		FIND_PEOPLE_REMOVE_1,
//
//		COMMAND_VRG_JAPANESE_1,
//		COMMAND_VRG_LANGUAGE_1,
//		COMMAND_VRG_EXPRESSIONS_1,
//		COMMAND_VRG_EXPRESSIONS_OFF_0,
//		DESCRIPTION_2,
//		DESCRIPTION_KEY_2,
//		CONVERSATION_HINT_2,
//
//		GAME_NOT_ALLOWED_TO_ANSWER_0,
//		GAME_FIND_PEOPLE_MISSION_0,
//		GAME_FIND_PEOPLE_QUEST_1,
//		GAME_NO_WINNERS_0,
//		GAME_RESULTS_1,
//		GAME_WINNERS_1,
//		GAME_CHEAT_1,
//		GAME_JOIN_TP_1,
//		GAME_EVENT_RULE_2,
//
//		MYSELF_STAGE_RELOAD_2,
//
//		COMMON_INVALID_PARAMETER_1,
//		COMMON_NOT_AIR_IN_HAND_0,
//
//		BOOK_NOT_YOURS_0,
//		BOOK_INVALID_0,
//		BOOK_IMPORTED_0,
//		BOOK_NOT_IN_HAND_0,
//		BOOK_GET_0,
//		PUBLIC_GAME_FIND_PEOPLE_0,
//
//		;
//	}
//
//	public static String getMessage(Message0 key, String[] opts) {
//		String message = MESSENGER_PREFIX;
//		switch(key) {
//		case PUBLIC_GAME_FIND_PEOPLE_0: message += "Starting the minigame [Find People]."; break;
//
//		case COMMON_INVALID_PARAMETER_1: message += "Invalid Parameters: " + opts[0]; break;
//		case COMMON_NOT_AIR_IN_HAND_0: message += "Please hand nothing in your hand"; break;
//
//		case MYSELF_STAGE_RELOAD_2: message += "The stage was reloaded. (" + opts[0] + ", " + opts[1] + ")"; break;
//
//		case CANT_EDIT_TALKER_0: message += "You can't edit this talker."; break;
//		case EDITED_TALKER_0: message += "This talker was edited."; break;
//		case SET_LANGUAGE_1: message += "You set your language in " + opts[0] + "."; break;
//		case OWN_TALKER_0: message += "You own this talker."; break;
//		case ALREADY_OWNED_0: message += "You have already owned a talker in this stage."; break;
//		case SELECT_TALKER_1: message += "You selected " + opts[0] + ChatColor.RESET + "."; break;
//		case NO_EXPRESSION_LINE_LANG_2: message += "No expression in line " + opts[0] + " in " + opts[1] + "."; break;
//		case NO_QUESTION_LANG_1: message += "No question in " + opts[0] + "."; break;
//		case NO_SENTENCE_0: message += "The talker has no sentence."; break;
//		case GOT_NEW_MESSAGE_1: message += "You got " + opts[0] + " new messages. /talker comment new"; break;
//		case SHOW_COMMENT_2: message += "Showing comments for " + opts[0] + ChatColor.RESET + "(ID: " + opts[1] + ")."; break;
//		case INVALID_TALKER_1: message += "Invalid talker(ID: " + opts[0] + ")."; break;
//		case NEW_COMMENT_4: message += opts[0] + " new comments with " +opts[1] + ChatColor.RESET + "(ID: " + opts[2] + "). From: " + opts[3] + ")."; break;
//		case SENT_COMMENT_0: message += "You sent a comment."; break;
//		case NO_NEW_COMMENT_0: message += "You don't have any new comments."; break;
//		case NO_COMMENT_0: message += "The selected talker doesn't have any comments."; break;
//
//		case CONVERSATION_SPEAK_2: message = opts[0] + ChatColor.GREEN + " \"" + ChatColor.RESET + opts[1] + ChatColor.GREEN + "\"" + ChatColor.RESET; break;
//		case CONVERSATION_QUESTION_1: message = ChatColor.GREEN + "[Question] " + ChatColor.RESET + opts[0]; break;
//		case CONVERSATION_QUESTION_COMPLITE_1: message += ChatColor.GREEN + "You have answered all the questions correctly!" + ChatColor.RESET + " (" + opts[0] + ")";  break;
//		case CONVERSATION_TALK_START_1: message += "The conversation has started."; break;
//		case CONVERSATION_TALK_FINISH_0: message += ChatColor.RED + "The conversation is finished." + ChatColor.RESET; break;//Expression from ALC.
//		case CONVERSATION_INFO_LABEL_1: message = ChatColor.GOLD +  "[Talker] " + ChatColor.RESET + opts[0] + ChatColor.RESET; break;
//		case CONVERSATION_INFO_DATA_3: message = " ID: " + opts[0] + ", EDITOR: " + opts[1] + ", STAGE: " + opts[2]; break;
//		case CONVERSATION_INFO_SENTENCE_3: message = " SENT(" + opts[0] + ") " + opts[1] + ": " + opts[2]; break;
//		case CONVERSATION_KEY_SENTENCE_1: message += opts[0] + ChatColor.RESET + "'s Key Sentence."; break;
//		case CONVERSATION_KEY_SENTENCE_EN_1: message = " EN: " + ChatColor.GOLD + " \"" + ChatColor.RESET + opts[0] + ChatColor.GOLD + "\"" + ChatColor.RESET; break;
//		case CONVERSATION_KEY_SENTENCE_JP_1: message = " JP: " + ChatColor.GOLD + " \"" + ChatColor.RESET + opts[0] + ChatColor.GOLD + "\"" + ChatColor.RESET; break;
//		case CONVERSATION_HINT_2: message = "" + ChatColor.GOLD + ChatColor.BOLD +"[" + ChatColor.YELLOW + "Hint" + ChatColor.GOLD + ChatColor.BOLD + "] " + ChatColor.GREEN + opts[0] + ": " + ChatColor.RESET + opts[1]; break;
//
//		case STAGE_INVALID_1: message += "Invalid stage name(" + opts[0] + ")"; break;
//		case STAGE_RUNNING_0: message += "A game is already running right now. Try later."; break;
//		case STAGE_NOT_RUNNING_0: message += "No game is running for now."; break;
//		case STAGE_QUESTION_2: message =  ChatColor.GREEN + "[Question]" + ChatColor.RESET + " What is \"" + opts[0] + "\" in " + opts[1] + "?"; break;
//		case STAGE_CORRECT_1: message += ChatColor.GREEN + "Correct Answer!" + ChatColor.RESET + " (Answers: " + opts[0] + ")"; break;
//		case STAGE_WRONG_0: message += ChatColor.DARK_RED + "Wrong Answer!"; break;
//		case STAGE_FINISH_1: message += ChatColor.GREEN + opts[0] + " successfully completed the stage!"; break;
//		case STAGE_LIST_1: message = "" + ChatColor.GOLD + ChatColor.BOLD +"[" + ChatColor.YELLOW + "STAGES" + ChatColor.GOLD + ChatColor.BOLD + "] " + ChatColor.RESET + opts[0]; break;
//		case STAGE_INFO_6: message =
//				getPartition("=", "*", ChatColor.GOLD, ChatColor.YELLOW, 3) + "  " + ChatColor.GREEN + ChatColor.BOLD + opts[0] + getPartition("=", "*", ChatColor.GOLD, ChatColor.YELLOW, 3) + ChatColor.RESET + "\n"
//				+ " NPCs: " + opts[1] + "\n"
//				+ " Sentences: " + opts[2] + "\n"
//				+ " Questions: " + opts[3] + " / " + opts[4] + "\n"
//				+ " Key Sentences: " + opts[5];
//			break;
//		case STAGE_TP_1: message = "Teleporting to " + opts[0] + "."; break;
//
//		case TELEPORT_0: message += "Teleporting..."; break;
//		case GAME_FIND_PEOPLE_MISSION_0: message += ChatColor.LIGHT_PURPLE + "Find This Person!"; break;
//		case GAME_FIND_PEOPLE_QUEST_1: message = ChatColor.GOLD + "???" + ChatColor.RESET + ": " + opts[0] ; break;
//		case FIND_PEOPLE_FINISH_2: message += ChatColor.GREEN + opts[0] + " successufully finished the game!" + ChatColor.RESET + " (FindPpl: " + opts[1] + ")"; break;
//		case CORRECT_0: message += ChatColor.GREEN + "Correct!"; break;
//		case WRONG_0: message += ChatColor.DARK_RED + "Wrong!"; break;
//
//		case FIND_PEOPLE_REMOVE_1: message = " [FindPpl: " + opts[0] + "] Removed the game!"; break;
//		case COMMAND_VRG_JAPANESE_1: message += "Your Japanese mode is set as " + opts[0] + "."; break;
//		case COMMAND_VRG_LANGUAGE_1: message += "Your learning language is set as " + opts[0] + "."; break;
//		case COMMAND_VRG_EXPRESSIONS_1: message += "Your current languages are " + opts[0] + "."; break;
//		case COMMAND_VRG_EXPRESSIONS_OFF_0: message += "Your current VRG output is OFF."; break;
//		case DESCRIPTION_2: message = opts[0] + ChatColor.RESET + ": " + opts[1]; break;
//		case DESCRIPTION_KEY_2: message = opts[0] + ChatColor.RESET + ": " + opts[1] + " " + MARK_KEY; break;
//
//		case GAME_NOT_ALLOWED_TO_ANSWER_0: message += "You are not allowed to answer the question."; break;
//		case GAME_NO_WINNERS_0: message += "No Winners!"; break;
//		case GAME_RESULTS_1: message += "Results: " + opts[0]; break;
//		case GAME_WINNERS_1: message += "Winners: " + opts[0]; break;
//		case GAME_CHEAT_1: message += "You are cheating!(Score: " + opts[0] + ")"; break;
//		case GAME_EVENT_RULE_2: message += opts[0] + ": Score(" + opts[1] + ")"; break;
//
//		case BOOK_NOT_YOURS_0: message += "This book is not yours."; break;
//		case BOOK_NOT_IN_HAND_0: message += "Please Hold a written book in your hand."; break;
//		case BOOK_INVALID_0: message += "Invalid book. Check how to write one."; break;
//		case BOOK_IMPORTED_0: message += "Your sentences are successfully imported."; break;
//		case BOOK_GET_0: message += "You got a new book. Enjoy writing!"; break;
//
//		default: break;
//		}
//		return message;
//	}
//
//	public static void print(List<Player> listPlayer, String message) {
//		Debug.printDebugMessage("", new Exception());
//		for(Player player : listPlayer) {
//			print(player, message);
//		}
//	}
//	public static void print(Player player, String message) {
//		Debug.printDebugMessage("", new Exception());
//		if(player.isOnline()) {
//			player.sendMessage(message);
//		}
//	}
//	public static void broadcast(String message) {
//		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
//			if(player.isOnline()) {
//				try {
//					player.sendMessage(message);
//				} catch (Exception e) {
//				}
//			}
//		}
//	}

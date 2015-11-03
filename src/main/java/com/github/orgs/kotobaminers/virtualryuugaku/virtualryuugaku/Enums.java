package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation.CheckState;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class Enums {//public enums
	public enum Commands {
		VIRTUALRYUUGAKU_OP(
				null,
				Arrays.asList("vrgop", "virtualryuugakuop"),
				"Commands for the ops.",
				new ArrayList<String>(),
				CommandPermission.OP),
		SAVE(
				VIRTUALRYUUGAKU_OP,
				Arrays.asList("save", "s"),
				"To save Virtual Ryuugaku.",
				new ArrayList<String>(),
				CommandPermission.OP),
		RELOAD(
				VIRTUALRYUUGAKU_OP,
				Arrays.asList("reload", "r"),
				"To load Virtual Ryuugaku.",
				new ArrayList<String>(),
				CommandPermission.OP),


		VIRTUALRYUUGAKU_TEACHER(
				null,
				Arrays.asList("vrgt", "virtualryuugakuteacher"),
				"Commands for the teachers.",
				new ArrayList<String>(),
				CommandPermission.TEACHER),
		KEY(
				VIRTUALRYUUGAKU_TEACHER,
				Arrays.asList("key", "k"),
				"Toggle your current sentence as key.",
				new ArrayList<String>(),
				CommandPermission.TEACHER),

		VIRTUALRYUUGAKU(
				null,
				Arrays.asList("vrg", "virtualryuugaku"),
				"Learning languages and Minigames.",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		DICTIONARY(
				VIRTUALRYUUGAKU,
				Arrays.asList("d", "dic", "dictionary"),
				"Show a link for the JP word/sentence",
				Arrays.asList("<WORD|SENTENCE>"),
				CommandPermission.PLAYERS),
		DICTIONARY_BROADCAST(
				VIRTUALRYUUGAKU,
				Arrays.asList("dbc", "dicbc"),
				"Show a translation for EN learners.",
				Arrays.asList("<WORD|SENTENCE>"),
				CommandPermission.PLAYERS),

		EN(
				VIRTUALRYUUGAKU,
				Arrays.asList("en"),
				"Toggle the EN description mode.",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		ROMAJI(
				VIRTUALRYUUGAKU,
				Arrays.asList("romaji", "r"),
				"Toggle the ROMAJI description mode.",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		KANA(
				VIRTUALRYUUGAKU,
				Arrays.asList("kana"),
				"Toggle the KANA description mode.",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		KANJI(
				VIRTUALRYUUGAKU,
				Arrays.asList("kanji"),
				"Toggle the KANJI description mode.",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),

		TP(
				VIRTUALRYUUGAKU,
				Arrays.asList("tp"),
				"Teleport to the stage",
				Arrays.asList("<STAGE>"),
				CommandPermission.PLAYERS),
		INFO(
				VIRTUALRYUUGAKU,
				Arrays.asList("info", "infomation", "i"),
				"Information of the stage",
				Arrays.asList("<STAGE>"),
				CommandPermission.PLAYERS),
		LIST(
				VIRTUALRYUUGAKU,
				Arrays.asList("list", "l"),
				"Names of the all stages",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),

		MYSELF(
				VIRTUALRYUUGAKU,
				Arrays.asList("myself", "m"),
				"Edit your own npcs and sentences",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),

		MYSELF_BOOK(
				MYSELF,
				Arrays.asList("book", "b"),
				"Get a new empty book",
				Arrays.asList("<MYSELF STAGE>"),
				CommandPermission.PLAYERS),
		MYSELF_UPDATE(
				MYSELF,
				Arrays.asList("update", "u"),
				"Update your own npcs and sentences",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		MYSELF_RELOAD(
				MYSELF,
				Arrays.asList("reload", "r"),
				"Reload the npcs",
				Arrays.asList("<MYSELF STAGE>", "<" + CheckState.KEY.toString() + "|" + CheckState.UNCHECKED.toString() + "|" + CheckState.CHECKED.toString()),
				CommandPermission.PLAYERS),

		GAME(
				VIRTUALRYUUGAKU,
				Arrays.asList("game", "g"),
				"Minigames",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		GAME_START(
				GAME,
				Arrays.asList("start", "s"),
				"Start a minigame",
				Arrays.asList("<STAGE>"),
				CommandPermission.PLAYERS),
		GAME_FINISH(
				GAME,
				Arrays.asList("finish", "f"),
				"Finish a minigame",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		GAME_NEXT(
				GAME,
				Arrays.asList("next", "n"),
				"Continue to the next question",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		GAME_RULE(
				GAME,
				Arrays.asList("rule", "r"),
				"Rules of a minigame",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),

		ANSWER(
				null,
				Arrays.asList("ans", "answer"),
				"Commands for answering",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		ANSWER_GAME(
				ANSWER,
				Arrays.asList("g", "game"),
				"Answering for the global game",
				Arrays.asList("<ANSWER>"),
				CommandPermission.PLAYERS),
		ANSWER_CONVERSATION(
				ANSWER,
				Arrays.asList("c", "conv", "conversation"),
				"Answering for the conversation question",
				Arrays.asList("<ANSWER>"),
				CommandPermission.PLAYERS),

		;

		private Commands parent;
		private List<String> aliace = new ArrayList<String>();
		private String description;
		private List<String> usage;
		protected CommandPermission permission;

		private static List<String> commandColor = Arrays.asList("" + ChatColor.GOLD + ChatColor.BOLD, "" + ChatColor.YELLOW, "" + ChatColor.GREEN, "" + ChatColor.AQUA);

		private Commands(Commands parent, List<String> aliace, String description, List<String> usage, CommandPermission permission) {
			this.parent = parent;
			this.aliace = aliace;
			this.description = description;
			this.usage = usage;
			this.permission = permission;
		}

		public List<String> getPath() {
			List<String> path = new ArrayList<String>();
			path.add(aliace.get(0));
			Commands search = parent;
			while(!(search == null)) {
				path.add(0, search.aliace.get(0));
				search = search.parent;
			}
			return path;
		}

		public List<String> getFullUsage() {
			List<String> full = new ArrayList<String>();
			full.addAll(getPath());
			full.addAll(this.usage);
			return full;
		}

		public static Commands getCommand(List<String> path) {
			Commands parent = Commands.VIRTUALRYUUGAKU;
			for(Commands root : getRoot()) {
				if(root.aliace.contains(path.get(0).toLowerCase())) {
					parent = root;
					boolean next = false;
					if (1 < path.size()) {
						for (int i = 1; i < path.size(); i++) {
							next = false;
							for(Commands child : parent.getChildren()) {
								if(child.aliace.contains(path.get(i))) {
									parent = child;
									next = true;
									break;
								}
							}
							if (next == false) {
								break;
							}
						}
					}
				}
			}
			return parent;
		}

		public void printInfo(CommandSender sender) {
			List<Commands> children = getChildren();
			if (0 < children.size()) {
				Message.COMMAND_HELP_TITLE_0.print(sender, null);
				for (Commands child : children) {
					child.printHelp(sender);
				}
			}
		}

		public void printHelp(CommandSender sender) {
			List<Commands> children = getChildren();
			if (0 < children.size()) {
				for (Commands commands : children) {
					commands.printHelp(sender);
				}
			} else {
				printUsage(sender);
			}
		}

		public void printUsage(CommandSender sender) {
			List<String> usage = getFullUsage();
			String command = ChatColor.GRAY + " /";
			for (int i = 0; i < usage.size(); i++) {
				if (i < commandColor.size()) {
					command += commandColor.get(i);
				} else {
					command += ChatColor.RESET;
				}
				command += usage.get(i) + " ";
			}
			command = command.substring(0, command.length() - 1);
			String[] opts = {command, this.description};
			Message.COMMAND_HELP_2.print(sender, opts);
		}

		private List<Commands> getChildren() {
			List<Commands> commands = new ArrayList<Commands>();
			for (Commands command : Commands.values()) {
				if(command.parent == this) {
					commands.add(command);
				}
			}
			return commands;
		}

		public static List<Commands> getRoot() {
			List<Commands> commands = new ArrayList<Commands>();
			for (Commands command : Commands.values()) {
				if(command.parent == null) {
					commands.add(command);
				}
			}
			return commands;
		}

		public boolean canPerform(CommandSender sender) {
			switch(permission) {
			case PLAYERS:
				return true;
			case OP:
				if (sender instanceof Player) {
					if(sender.isOp()) {
						return true;
					}
				} else if(sender instanceof ConsoleCommandSender) {
					return true;
				}
				break;
			case TEACHER:
				if (sender instanceof Player) {
					if (ControllerConversation.getTeachers().contains(sender.getName())) {
					return true;
					}
				}
				break;
			case CONSOLE:
				break;
			default:
				break;
			}
			return false;
		}

		public boolean isRunnableChild() {
			if (0 == getChildren().size()) {
				return true;
			}
			return false;
		}
	}

	public enum CommandPermission {
		PLAYERS, OP, TEACHER, CONSOLE,;
	}




	public enum Expression {EN, KANJI, KANA, ROMAJI;
		public static Expression lookup(String name) {
			try {
				return Expression.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				return Expression.EN;
			}
		}
	}
	public enum Language {EN, JP;
		public static Language lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return Language.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return Language.JP;
			}
		}
	}
	public enum PathConversation {STAGE, EDITOR, EN, KANJI, KANA, KEY, Q, A, COMMENT}
}

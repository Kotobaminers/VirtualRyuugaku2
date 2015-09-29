package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class Enums {//public enums
	public enum Commands {
		VIRTUALRYUUGAKU(
				null,
				Arrays.asList("vrg", "virtualryuugaku"),
				"Learning languages and Minigames",
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
				"Answer for the global game",
				Arrays.asList("<ANSWER>"),
				CommandPermission.PLAYERS),

		;

		private Commands parent;
		private List<String> aliace = new ArrayList<String>();
		private String description;
		private List<String> usage;
		private CommandPermission permission;

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

		public static Commands getCommand(List<String> path) throws Exception {
			if (0 < path.size()) {
				for(Commands root : getRoot()) {
					if(root.aliace.contains(path.get(0).toLowerCase())) {
						Commands parent = root;
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
						return parent;
					}
				}
			}
			throw new Exception("Invalid Command: " + UtilitiesGeneral.joinStrings(path, " "));
		}

		public void printInfo(CommandSender sender) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				List<Commands> children = getChildren();
				if (0 < children.size()) {
					Message.COMMAND_HELP_TITLE_0.print(player, null);
					for (Commands child : children) {
						child.printHelp(player);
					}
				}
			}
			//Nothing will be printed when the command has no children.
		}

		public void printHelp(Player player) {
			List<Commands> children = getChildren();
			if (0 < children.size()) {
				for (Commands commands : children) {
					commands.printHelp(player);
				}
			} else {
				printUsage(player);
			}
		}

		public void printUsage(Player player) {
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
			Message.COMMAND_HELP_2.print(player, opts);
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

	}

	public enum CommandPermission {
		PLAYERS, OP, TEACHER, CONSOLE,;
		public boolean canPerform(CommandSender sender) {
			switch(this) {
			case PLAYERS:
				return true;
			case OP:
				if (sender instanceof Player) {
					sender.isOp();
					return true;
				}
				break;
			case TEACHER:
				break;
			case CONSOLE:
				break;
			default:
				break;
			}
			return false;
		}
	}




	public enum Expression {NONE, EN, KANJI, KANA, ROMAJI;
		public static Expression lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return Expression.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return Expression.ROMAJI;
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

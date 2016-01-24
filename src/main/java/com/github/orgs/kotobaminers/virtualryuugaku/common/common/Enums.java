package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerVRG.Message;

public class Enums {//public enums
	public enum Commands {
		VIRTUALRYUUGAKU_DEBUG(
				null,
				Arrays.asList("vrgd", "vrgdbg", "vrgdebug"),
				"Commands for the ops.",
				new ArrayList<String>(),
				CommandPermission.OP),
		DEBUG_MODE(
				VIRTUALRYUUGAKU_DEBUG,
				Arrays.asList("mode", "m"),
				"To toggle the debug mode.",
				new ArrayList<String>(),
				CommandPermission.OP),
		DEBUG_INTERVAL(
				VIRTUALRYUUGAKU_DEBUG,
				Arrays.asList("interval", "i"),
				"To set the public game interval for debugging.",
				new ArrayList<String>(),
				CommandPermission.OP),
		DEBUG_DEBUG(
				VIRTUALRYUUGAKU_DEBUG,
				Arrays.asList("debug", "d"),
				"",
				new ArrayList<String>(),
				CommandPermission.OP),
		DEBUG_EFFECT(
				VIRTUALRYUUGAKU_DEBUG,
				Arrays.asList("effect", "e"),
				"",
				new ArrayList<String>(),
				CommandPermission.OP),
		DEBUG_SOUND(
				VIRTUALRYUUGAKU_DEBUG,
				Arrays.asList("sound", "s"),
				"",
				new ArrayList<String>(),
				CommandPermission.OP),

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


		VIRTUALRYUUGAKU(
				null,
				Arrays.asList("vrg", "virtualryuugaku"),
				"Learning languages and Minigames.",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		EDIT(
				VIRTUALRYUUGAKU,
				Arrays.asList("edit", "e"),
				"To edit a selected sentence.",
				Arrays.asList("<SENTENCE>"),
				CommandPermission.PLAYERS
				),
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
//		MYSELF_RELOAD(
//				MYSELF,
//				Arrays.asList("reload", "r"),
//				"Reload the npcs",
//				Arrays.asList("<MYSELF STAGE>", "<" + CheckState.KEY.toString() + "|" + CheckState.UNCHECKED.toString() + "|" + CheckState.CHECKED.toString()),
//				CommandPermission.PLAYERS),

		GAME(
				VIRTUALRYUUGAKU,
				Arrays.asList("game", "g"),
				"Minigames",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		GAME_START(
				GAME,
				Arrays.asList("start", "s"),
				"To start a minigame",
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
		GAME_JOIN(
				GAME,
				Arrays.asList("join", "j"),
				"To join the minigame.",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		GAME_REPEAT(
				GAME,
				Arrays.asList("repeat"),
				"To repeat the last game.",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),

		TOUR(
				VIRTUALRYUUGAKU,
				Arrays.asList("tour", "t"),
				"Commands for a tour.",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		TOUR_START(
				TOUR,
				Arrays.asList("start", "s"),
				"To start a tour.",
				Arrays.asList("<STAGE>"),
				CommandPermission.PLAYERS),
		TOUR_JOIN(
				TOUR,
				Arrays.asList("join", "j"),
				"To join the tour.",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		TOUR_NEXT(
				TOUR,
				Arrays.asList("next", "n"),
				"To continue to the next conversation.",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		TOUR_PREVIOUS(
				TOUR,
				Arrays.asList("previous", "p"),
				"To return to the previous conversation.",
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
		ANSWER_PUBLIC_GAME(
				ANSWER,
				Arrays.asList("p", "pub", "public"),
				"Answering for the public game",
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
		PLAYERS, OP, CONSOLE,;
	}

	public enum SpellType {EN(3), KANJI(14), KANA(1), ROMAJI(2);
		private int data;
		private SpellType(int i) {
			this.data = i;
		}
		public short getData() {
			return (short) data;
		}
		public ItemStack getIconItem() {
			return new ItemStack(Material.WOOL, 1, getData());
		}
		public static Optional<SpellType> createSpellType(ItemStack item) {
			if (item.getType() == Material.WOOL) {
				for (SpellType spell : SpellType.values()) {
					if (spell.getData() == item.getDurability()) {
						return Optional.of(spell);
					}
				}
			}
			return Optional.empty();
		}
	}

	public enum Language {EN, JP;
		public static Language getRandom() {
			Random random = new Random();
			Integer value = random.nextInt(Language.values().length);
			return Language.values()[value];
		}
		public Language getOppositeLanguage() {
			Language language = EN;
			if (!language.equals(this)) {
				language = JP;
			}
			return language;
		}
	}
	public enum PathConversation {STAGE, EDITOR, EN, KANJI, KANA, KEY, Q, A, COMMENT}

	public enum GameMode {
		FREE("Free", Material.FEATHER, Arrays.asList("To Look Around")),
		TOUR("Tour", Material.COMPASS, Arrays.asList("To Join A Tour")),
		TRAINING("Training", Material.IRON_SPADE, Arrays.asList("To Start Training")),
		ANKI("Anki", Material.BOOK_AND_QUILL, Arrays.asList("To Memorize Sentences")),
		MY_NPC("Your NPC", Material.SKULL_ITEM, Arrays.asList("To Create Your NPC")),
		;

		private GameMode(String mode, Material material, List<String> lore) {
			this.mode = mode;
			this.material = material;
			this.lore = lore;
		}
		public String mode;
		public Material material;
		public List<String> lore;

		public static GameMode create(Material material) {
			for (GameMode gameMode : GameMode.values()) {
				if (gameMode.material.equals(material)) {
					return gameMode;
				}
			}
			return GameMode.FREE;
		}

		public ItemStack createIcon(String stage) {
			ItemStack item = new ItemStack(material);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(mode);
			List<String> lore = new ArrayList<String>();
			lore.add(stage);
			lore.addAll(this.lore);
			meta.setLore(lore);
			if (this.equals(MY_NPC)) {
				SkullMeta skull = (SkullMeta) meta;
				skull.setOwner("kai_f");
			}
			item.setItemMeta(meta);
			return item;
		}

		public String getSubtitle() {
			return "~ " + this.mode + " Mode ~";
		}
	}




}

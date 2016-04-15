package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;

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
		EMPTY(
				VIRTUALRYUUGAKU_OP,
				Arrays.asList("empty"),
				"",
				new ArrayList<String>(),
				CommandPermission.OP),
		UNIT(
				VIRTUALRYUUGAKU_OP,
				Arrays.asList("unit", "u"),
				"To manage vrg units",
				new ArrayList<String>(),
				CommandPermission.OP),
		UNIT_CREATE(
				UNIT,
				Arrays.asList("create", "c"),
				"To create a vrg unit",
				Arrays.asList("<UNIT NAME>", "<NPC ID>"),
				CommandPermission.OP),
		UNIT_DELETE(
				UNIT,
				Arrays.asList("delete", "d"),
				"To delete a vrg unit",
				Arrays.asList("<UNIT NAME>"),
				CommandPermission.OP
				),
		UNIT_ADD_HELPER(
				UNIT,
				Arrays.asList("addhelper", "ah"),
				"To add an npc to the unit",
				Arrays.asList("<UNIT_NAME>", "NPC_ID"),
				CommandPermission.OP),
		UNIT_ADD_PLAYER(
				UNIT,
				Arrays.asList("ap", "addp", "addplayer"),
				"To add a player NPC",
				Arrays.asList("<UNIT NAME>", "<NPC ID>"),
				CommandPermission.OP
				),
		UNIT_ADD_PLAYER_QUESTION(
				UNIT,
				Arrays.asList("apq", "addpq", "addplayerquestion"),
				"To add a player question",
				Arrays.asList("<UNIT NAME>", "<POSITION>", "<QUESTION>"),
				CommandPermission.OP
				),
		UNIT_EDIT_PLAYER_QUESTION(
				UNIT,
				Arrays.asList("epq", "editpq", "editplayerquestion"),
				"To edit a player question",
				Arrays.asList("<UNIT NAME>", "<POSITION>", "<QUESTION>"),
				CommandPermission.OP
				),
		UNIT_REMOVE_PLAYER_QUESTION(
				UNIT,
				Arrays.asList("rpq", "removepq", "removeplayerquestion"),
				"To remove a player question",
				Arrays.asList("<UNIT NAME>", "<POSITION>"),
				CommandPermission.OP
				),

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
				CommandPermission.PLAYERS),
		ADD_ANSWER(
				VIRTUALRYUUGAKU,
				Arrays.asList("aa", "addanswer", "addans"),
				"To add an answer from the selected question",
				Arrays.asList("<ANSWER>"),
				CommandPermission.PLAYERS),
		REMOVE_ANSWER(
				VIRTUALRYUUGAKU,
				Arrays.asList("ra", "remans", "removeanswer", "removeans"),
				"To remove an answer from the selected question",
				Arrays.asList("<ANSWER>"),
				CommandPermission.PLAYERS),
		DELETE_QUESTION(
				VIRTUALRYUUGAKU,
				Arrays.asList("dq", "deletequestion", "delq"),
				"To unregister the question and it's answers",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),

		REQUEST(
				VIRTUALRYUUGAKU,
				Arrays.asList("r", "req", "request"),
				"",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		REQUEST_TELEPORT_PLAYER(
				REQUEST,
				Arrays.asList("tp", "teleport"),
				"",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		REQUEST_TELEPORT_NPC(
				REQUEST,
				Arrays.asList("tpn", "teleportnpc"),
				"",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		REQUEST_ACCEPT(
				REQUEST,
				Arrays.asList("a", "accept"),
				"",
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

		INFO(
				VIRTUALRYUUGAKU,
				Arrays.asList("info", "infomation", "i"),
				"Information of the stage",
				Arrays.asList("<STAGE>"),
				CommandPermission.PLAYERS),
		LIST(
				VIRTUALRYUUGAKU,
				Arrays.asList("list", "l"),
				"Display the list of the units",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),

		ANSWER(
				null,
				Arrays.asList("ans", "answer"),
				"Commands for answering",
				new ArrayList<String>(),
				CommandPermission.PLAYERS),
		ANSWER_HELPER(
				ANSWER,
				Arrays.asList("h", "helper"),
				"Answering for the helper NPC's question",
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
				Message.COMMAND_HELP_TITLE_0.print(Arrays.asList(""), sender);
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
			Message.COMMAND_HELP_2.print(Arrays.asList(command, this.description), sender);
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

	public enum Language {EN, JP, NONE;
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
		YOUR_NPC("Your NPC", Material.SKULL_ITEM, Arrays.asList("To Create Your NPC")),
		;

		private GameMode(String mode, Material material, List<String> lore) {
			this.displayName = mode;
			this.material = material;
			this.lore = lore;
		}
		public String displayName;
		public Material material;
		public List<String> lore;

		public static Optional<GameMode> create(Material material) {
			return Stream.of(GameMode.values())
					.filter(mode -> mode.material.equals(material))
					.findFirst();
		}

		public ItemStack createIcon(String stage) {
			ItemStack item = new ItemStack(material);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(displayName);
			List<String> lore = new ArrayList<String>();
			lore.add(stage);
			lore.addAll(this.lore);
			meta.setLore(lore);
			if (this.equals(YOUR_NPC)) {
				SkullMeta skull = (SkullMeta) meta;
				skull.setOwner("kai_f");
			}
			item.setItemMeta(meta);
			return item;
		}
	}


}

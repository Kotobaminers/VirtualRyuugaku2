package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Language;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.CommandConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.myself.myself.CommandMyself;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.stage.stage.CommandGlobal;
import com.github.orgs.kotobaminers.virtualryuugaku.stage.stage.CommandStage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandVirtualRyuugaku extends MyCommand {
	public CommandVirtualRyuugaku(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	private enum Commands {
		NONE, LANGUAGE, LANG, EN, KANJI, KANA, ROMAJI, TEST, BOOK, S, STAGE, GLOBAL, G, MYSELF, MY, SELF, ME, CONVERSATION, CONV;
		public static Commands lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return Commands.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return Commands.NONE;
			}
		}
	}

	@Override
	public void runCommand() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(0 < args.length) {
			Commands commands = Commands.lookup(args[0]);
			switch(commands) {
			case LANGUAGE:
			case LANG:
				commandLanguage();
				break;
			case EN:
			case KANJI:
			case KANA:
			case ROMAJI:
				commandToggleExpression();
				break;
			case TEST:
				commandTest();
				break;
			case NONE:
				break;
			case BOOK:
				commandBook();

//				String[] book = {"aaa", "bbb"};
//				BookOpen.openBook(player, book);
				break;
			case S:
			case STAGE:
				new CommandStage(player, command, args).runCommand();
				break;
			case GLOBAL:
			case G:
				new CommandGlobal(player, command, args).runCommand();
				break;
			case ME:
			case MY:
			case MYSELF:
			case SELF:
				new CommandMyself(player, command, args).runCommand();
				break;
			case CONV:
			case CONVERSATION:
				new CommandConversation(player, command, args).runCommand();
				break;
			default:
				break;
			}
		}
	}

	private void commandBook() {
		if (player.getItemInHand().getType().equals(Material.AIR)) {
			String stage = "<STAGE>";
			if (1 < args.length) {
				stage = args[1];
			}
			ItemStack item = new ItemStack(Material.BOOK_AND_QUILL, 1);
			BookMeta book = (BookMeta) item.getItemMeta();
			List<String> pages = Arrays.asList(player.getName() + "\n" + stage);
			book.setPages(pages);
			item.setItemMeta(book);
			player.setItemInHand(item);
			String opts[] = {};
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.BOOK_GET_0, opts));
			Effects.playSound(player, Scene.APPEAR);
		} else {
			String opts[] = {};
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.COMMON_NOT_AIR_IN_HAND_0, opts));
			Effects.playSound(player, Scene.BAD);
		}
	}

	private void commandTest() {
	}

	private void commandToggleExpression() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(0 < args.length) {
			String expressionString = args[0];
			Expression expression = Expression.lookup(expressionString);
			DataManagerPlayer.toggleExpression(player.getName(), expression);
			DataPlayer data = DataManagerPlayer.getDataPlayer(player);
			List<String> expressions = new ArrayList<String>();
			for(Expression search : data.expressions) {
				expressions.add(search.toString());
			}
			if(0 < expressions.size()) {
				String[] opts = {UtilitiesGeneral.joinStrings(expressions, ", ")};
				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.COMMAND_VRG_EXPRESSIONS_1, opts));
			} else {
				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.COMMAND_VRG_EXPRESSIONS_OFF_0, null));
			}
		}
	}

	private void commandLanguage() {
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		switch(data.language) {
		case EN:
			data.language = Language.JP;
			break;
		case JP:
			data.language = Language.EN;
			break;
		default:
			break;
		}
		String[] opts = {data.language.toString()};
		player.sendMessage(MessengerGeneral.getMessage(Message.COMMAND_VRG_LANGUAGE_1, opts));
	}
}

package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Commands;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandExecutorPlugin implements CommandExecutor {
	public CommandExecutorPlugin(VirtualRyuugaku plugin) {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<String> path = new ArrayList<String>();
		path.add(label);
		path.addAll(Arrays.asList(args));
		Commands command2;
		try {
			command2 = Commands.getCommand(path);
			command2.printInfo(sender);
			CommandPerformer performer = new CommandPerformer(sender, command2, label, args);
			switch (command2) {
			case EN:
				performer.commandEN();
				break;
			case ROMAJI:
				performer.commandRomaji();
				break;
			case KANA:
				performer.commandKana();
				break;
			case KANJI:
				performer.commandKanji();
				break;

			case TP:
				performer.commandTP();
				break;
			case INFO:
				performer.commandInfo();
				break;
			case LIST:
				performer.commandList();
				break;

			case GAME_START:
				performer.commandGameStart();
				break;
			case GAME_FINISH:
				performer.commandGameFinish();
				break;
			case GAME_NEXT:
				performer.commandGameNext();
				break;
			case GAME_RULE:
				performer.commandGameRule();
				break;

			case MYSELF_BOOK:
				performer.commandMyselfBook();
				break;
			case MYSELF_UPDATE:
				performer.commandMyselfUpdate();
				break;
			case MYSELF_RELOAD:
				performer.commandMyselfReload();
				break;

			case ANSWER_GAME:
				performer.commandAnswerGame();
				break;

			case GAME:
			case VIRTUALRYUUGAKU:
			case ANSWER:
			case MYSELF:
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}


}

//		@Override
//		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//			UtilitiesProgramming.printDebugMessage("", new Exception());
//		Commands0 commands = Commands0.lookup(label);
//		if(commands.equals(Commands0.VRGDBG)) {
//			if(sender instanceof Player) {
//				if(!sender.isOp()) return false;
//			}
//			new CommandVirturalRyuugakuDebug().printDebug(args);
//			return true;
//		}
//		if(sender instanceof Player) {
//			UtilitiesProgramming.printDebugMessage("", new Exception());
//			Player player = (Player) sender;
//			MyCommand myCommand = null;
//			switch(commands) {
//			case CONVERSATION:
//			case CONV:
//				myCommand = new CommandConversation(player, command, args);
//				break;
//			case CONVERSATIONOP:
//			case CONVOP:
//				myCommand = new CommandConversationOP(player, command, args);
//				break;
//			case VIRTUALRYUUGAKU:
//			case VRG:
//				myCommand = new CommandVirtualRyuugaku(player, command, args);
//				break;
//			case VIRTUALRYUUGAKUOP:
//			case VRGOP:
//				myCommand = new CommandVirtualRyuugakuOP(player, command, args);
//				break;
//			case STAGEOP:
//				myCommand = new CommandStageOP(player, command, args);
//				break;
//			case ANSWER:
//			case ANS:
//				myCommand = new CommandAnswer(player, command, args);
//				break;
//			case VRGDBG:
//			//VRGDBG should be placed the upper section.
//			default:
//				return false;
//			}
//			myCommand.runCommand();
//			return true;
//		}
//		return false;
//	}

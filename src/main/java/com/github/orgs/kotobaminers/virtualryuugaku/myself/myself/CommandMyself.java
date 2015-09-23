package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation.CheckState;
import com.github.orgs.kotobaminers.virtualryuugaku.stage.stage1.GameGlobalHandler0;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandMyself extends MyCommand{

	public CommandMyself(Player player, Command command, String[] args) {
		super(player, command, args);
	}

	private enum Commands {
		DEFAULT, UPDATE, RELOAD, TRAINING, T, MYSELF, ME, INFO,;
		private static Commands lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return Commands.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return Commands.DEFAULT;
			}
		}
	}

	@Override
	public void runCommand() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(1 < args.length) {
			Commands commands = Commands.lookup(args[1]);
			switch(commands) {
			case UPDATE:
				commandUpdate();
				break;
			case RELOAD:
				commandReload();
				break;
			case TRAINING:
			case T:
				commandTraining();
				break;
			case ME:
			case MYSELF:
				commandMyself();
				break;
			case INFO:
				commandInfo();
				break;
			case DEFAULT:
			default:
				break;
			}
		}
	}

	private void commandInfo() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String name = "";
		if(2 < args.length) {
			name = args[2];
		} else {
			name = player.getName();
		}
		InfoMyselfPlayer.printInfo();
	}

	private void commandMyself() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		ControllerMyself.updateMe(player);
	}

	private void commandTraining() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(2 < args.length) {
			String stage = args[2].toUpperCase();
			try {
				GameGlobalHandler0.loadMyselfTraining(stage);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			GameGlobalHandler0.giveNextQuestion(player);
		}
	}

	private void commandReload() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(2 < args.length) {
			StageMyself stage = null;
			String name = args[2].toUpperCase();
			CheckState state = CheckState.RECOMMENDED;
			if (3 < args.length) {
				String stateString = args[3];
				for (CheckState search : CheckState.values()) {
					if (search.toString().equalsIgnoreCase(stateString)) {
						state = CheckState.valueOf(stateString.toUpperCase());
						break;
					}
				}
			}

			try {
				stage = StageMyself.createStageMyself(name);
				stage.changeNPC(state);
				String[] opts = {name, state.toString()};
				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.MYSELF_STAGE_RELOAD_2, opts));
				Effects.playSound(player, Scene.NOTICE);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				String[] opts = {name};
				UtilitiesProgramming.printDebugMessage("Invalid Stage Name: " + name, new Exception());
				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.COMMON_INVALID_PARAMETER_1, opts));
				Effects.playSound(player, Scene.BAD);
				return;
			}
		} else {
			UtilitiesProgramming.printDebugMessage("/vrg myself random <STAGE>", new Exception());//TODO
		}
	}

	private void commandUpdate() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		ConversationBook book = null;
		try {
			book = ConversationBook.createConversatinBook(player);
			UtilitiesProgramming.printDebugMessage("", new Exception());
			if(book.isMine()) {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				ControllerMyself.importBook(book);
				String[] opts = {};
				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.BOOK_IMPORTED_0, opts));
				Effects.playSound(player, Scene.GOOD);
			} else {
				String[] opts = {};
				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.BOOK_NOT_YOURS_0, opts));
				Effects.playSound(player, Scene.BAD);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


//	private void commandName() {
//		UtilitiesProgramming.printDebugMessage("", new Exception());
//		if(2 < args.length) {
//			String name = args[2];
//			Integer select = DataManagerPlayer.getDataPlayer(player).select;
//			NPCHandler.changeType(select, EntityType.PLAYER);
//			NPCHandler.changeName(select, name);
//		}
//	}

package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import org.bukkit.command.Command;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.stage.stage.GameGlobalHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.vrgnpc.vrgnpc.NPCHandler;

public class CommandMyself extends MyCommand{

	public CommandMyself(Player player, Command command, String[] args) {
		super(player, command, args);
	}

	private enum Commands {
		DEFAULT, UPDATE, NAME, RANDOM, TRAINING, T, ;
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
			case NAME:
				commandName();
				break;
			case RANDOM:
				commandRandom();
				break;
			case TRAINING:
			case T:
				commandTraining();
				break;
			case DEFAULT:
			default:
				break;
			}
		}
	}

	private void commandTraining() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(2 < args.length) {
			String stage = args[2];
			try {
				GameGlobalHandler.loadMyselfTraining(stage);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			GameGlobalHandler.giveNextQuestion(player);
		}
	}

	private void commandRandom() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(2 < args.length) {
			StageMyself stage = null;
			String name = args[2].toUpperCase();
			try {
				stage = StageMyself.createStageMyself(name);
				stage.changeNPCRandom();
				return;
			} catch (Exception e) {
				e.printStackTrace();
				UtilitiesProgramming.printDebugMessage("Invalid Stage Name: " + name, new Exception());//TODO
				return;
			}
		} else {
			UtilitiesProgramming.printDebugMessage("/vrg myself random <STAGE>", new Exception());//TODO
		}
	}

	private void commandUpdate() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		ConversationBook book = ConversationBook.createConversatinBook(player);
		book.update();
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(book.isMine()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			ControllerMyself.importBook(book);
		} else {
			UtilitiesProgramming.printDebugMessage("This book is not yours.", new Exception());//TODO
		}
	}

	private void commandName() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(2 < args.length) {
			String name = args[2];
			Integer select = DataManagerPlayer.getDataPlayer(player).select;
			UtilitiesProgramming.printDebugMessage("", new Exception());
			NPCHandler.changeType(select, EntityType.PLAYER);
			UtilitiesProgramming.printDebugMessage("", new Exception());
			NPCHandler.changeName(select, name);
		}
	}
}

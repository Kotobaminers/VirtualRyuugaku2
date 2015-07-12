package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandGlobal extends MyCommand {
	public CommandGlobal(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	private enum Commands {
		NEXT, FINISH, FIND, FINDPPL, FINDPEOPLE, FP, DEFAULT;
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
			case NEXT:
				commandNext();
				break;
			case FIND:
			case FINDPEOPLE:
			case FINDPPL:
			case FP:
				commandFindPeople();
				break;
			case FINISH:
				commandFinish();
				break;
			case DEFAULT:
			default:
				break;
			}
		}
	}

	private void commandFinish() {
		GameGlobalHandler.finishGame();
	}

	private void commandNext() {
		GameGlobalHandler.giveNextQuestion(player);
	}

	private void commandFindPeople() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(2 < args.length) {
			String stage = args[2];
			GameGlobalHandler.loadFindPeople(stage);
			GameGlobalHandler.giveNextQuestion(player);
		}
	}

}

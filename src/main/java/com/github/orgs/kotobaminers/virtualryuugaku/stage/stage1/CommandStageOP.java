package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage1;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandStageOP extends MyCommand {
	public CommandStageOP(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	private enum Commands {
		NONE, NEXT, STOP;
		private static Commands lookup(String name) {
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
			case NONE:
				break;
			case NEXT:
//				commandNext();
				break;
			case STOP:
				break;
			default:
				break;
			}
		}
	}

	//After Cancelling the task, error happens to start run Task again...
//	private void commandNext() {
//		UtilitiesProgramming.printDebugMessage("", new Exception());
//		if(!RunnableStage.running) {
//			MessengerGeneral.print(player, Message.GAME_STAGE_NOT_RUNNING_0, null);
//			return;
//		}
//		UtilitiesProgramming.printDebugMessage("", new Exception());
//		RunnableStage.getGame().cancel();
//		UtilitiesProgramming.printDebugMessage("", new Exception());
//		RunnableStage.getGame().runTaskTimer(DataManagerPlugin.plugin, 0, GameStage.interval);//Imediatelly run task
//	}
}
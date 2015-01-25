package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandStage extends MyCommand {
	public CommandStage(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	private enum Commands {
		NONE, START, INFO,;
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
			case INFO:
				break;
			case START:
				commandStart();
				break;
			default:
				break;
			}
		}
	}

	private void commandStart() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(1< args.length) {
			if(GameStage.active) {
				MessengerGeneral.print(player, Message.GAME_STAGE_RUNNING_0, null);
				return;
			}
			String stage = args[1];
			GameStage.loadNewGame(stage);
			if(!GameStage.isValid()) {
				String[] opts = {stage};
				MessengerGeneral.print(player, Message.GAME_STAGE_INVALID_1, opts);
				return;
			}
			GameStage.questNext();
		}
	}
}
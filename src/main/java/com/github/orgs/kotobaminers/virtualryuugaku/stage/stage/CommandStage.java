package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Broadcast;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.DataManagerPlugin;

public class CommandStage extends MyCommand {
	public CommandStage(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	private enum Commands {
		NONE, START, INFO, ANSWER, A;
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
			case A:
			case ANSWER:
				commandAnswer();
				break;
			default:
				break;
			}
		}
	}

	private void commandAnswer() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(1< args.length) {
			List<String> list = new ArrayList<String>();
			for(Integer i = 1; i < args.length; i++) {
				list.add(args[i]);
			}
			String answer = UtilitiesGeneral.joinStrings(list, " ");
			for(String correct : StageGameHandler.answers) {
				if(correct.equalsIgnoreCase(answer)) {
					StageGameHandler.correct(player);
					String total = StageGameHandler.getScoreCurrent(player).toString();
					String[] opts = {UtilitiesGeneral.joinStrings(StageGameHandler.answers, ", "), total};
					MessengerGeneral.print(player, Message.GAME_STAGE_CORRECT_2, opts);
					return;
				}
			}
			String[] opts = {answer};
			MessengerGeneral.print(player, Message.GAME_STAGE_WRONG_1, opts);
		}
	}

	private void commandStart() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(1< args.length) {
			if(StageGameHandler.running) {
				MessengerGeneral.print(player, Message.GAME_STAGE_RUNNING_0, null);
				return;
			}
			String stage = args[1];
			StageGameHandler.loadNewGame(stage);
			if(!StageGameHandler.getGame().isValid()) {
				String[] opts = {stage};
				MessengerGeneral.print(player, Message.GAME_STAGE_INVALID_1, opts);
				return;
			}

			String[] opts = {stage.toUpperCase()};
			MessengerGeneral.broadcast(Broadcast.GAME_STAGE_START_1, opts);
			StageGameHandler.running = true;
			StageGameHandler.getGame().runTaskTimer(DataManagerPlugin.plugin, StageGameHandler.ready, StageGameHandler.interval);
		}
	}
}
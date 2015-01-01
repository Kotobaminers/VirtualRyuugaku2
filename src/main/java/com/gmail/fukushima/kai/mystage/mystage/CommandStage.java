package com.gmail.fukushima.kai.mystage.mystage;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.common.common.MyCommand;
import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class CommandStage extends MyCommand {
	public CommandStage(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	public enum Commands {
		NONE, INFO, INFOMATION;
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
		if(0 < args.length) {
			Commands commands = Commands.lookup(args[0]);
			switch(commands) {
			case NONE:
				break;
			case INFO:
				DataPlayer data = DataManagerPlayer.getDataPlayer(player);
				Integer id = data.select;
				DataManagerStage.loadStageById(id).printInformation(player);
				break;
			case INFOMATION:
				DataPlayer data2 = DataManagerPlayer.getDataPlayer(player);
				Integer id2 = data2.select;
				DataManagerStage.loadStageById(id2).printInformation(player);
				break;
			default:
				break;
			}
		}
	}
}
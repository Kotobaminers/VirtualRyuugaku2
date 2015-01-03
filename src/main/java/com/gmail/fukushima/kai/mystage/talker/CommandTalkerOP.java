package com.gmail.fukushima.kai.mystage.talker;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
import com.gmail.fukushima.kai.utilities.utilities.MyCommand;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class CommandTalkerOP extends MyCommand {
	public CommandTalkerOP(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	public enum Commands {
		NONE, DEBUG;
		public static Commands lookup(String name) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			Commands commands = Commands.NONE;
			try {
				commands = Commands.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
			}
			UtilitiesProgramming.printDebugMessage(commands.toString(), new Exception());
			return commands;
		}
	}
	@Override
	public void runCommand() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(!player.isOp()) return;
		if(0 < args.length) {
			Commands commands = Commands.lookup(args[0]);
			switch(commands) {
			case NONE:
				break;
			case DEBUG:
				commandDebug();
				break;
			default:
				break;
			}
		}
	}
	private void commandDebug() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		Talker talker = DataManagerTalker.getTalker(data.select);
		UtilitiesProgramming.printDebugTalker(talker);
	}
}
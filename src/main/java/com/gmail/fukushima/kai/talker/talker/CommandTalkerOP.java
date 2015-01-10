package com.gmail.fukushima.kai.talker.talker;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
import com.gmail.fukushima.kai.utilities.utilities.MyCommand;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesGeneral;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class CommandTalkerOP extends MyCommand {
	public CommandTalkerOP(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	public enum Commands {
		NONE, DEBUG, CREATE;
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
		if(0 < args.length) {
			Commands commands = Commands.lookup(args[0]);
			switch(commands) {
			case NONE:
				break;
			case DEBUG:
				commandDebug();
				break;
			case CREATE:
				commandCreate();
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
	private void commandCreate() {
		if(2 == args.length) {
			String create = args[0];
			String name = args[1];
			String[] argsCreate = {"npc", create, name, "--type", "CREEPER"};
			String commandCreate = UtilitiesGeneral.joinStringsWithSpace(argsCreate);
			Bukkit.getServer().dispatchCommand(player, commandCreate);

			String[] argsOwner = {"npc", "owner", "@VIRTUALRYUUGAKU"};
			String commandOwner = UtilitiesGeneral.joinStringsWithSpace(argsOwner);
			Bukkit.getServer().dispatchCommand(player, commandOwner);
		}
	}
}
package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.common.common.CommandTalker;
import com.gmail.fukushima.kai.common.common.MyCommand;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class CommandExecutorPlugin implements CommandExecutor {
	public enum Commands {
		NONE,
		VIRTUALRYUUGAKU, VRG,
		VIRTUALRYUUGAKUOP, VRGOP,
		STAGE, STAGEOP,
		TALKER, TALKEROP;
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
	public CommandExecutorPlugin(VirtualRyuugaku2 plugin) {
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(sender instanceof Player) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			Player player = (Player) sender;
			if(Settings.protectionCommandOP) {
				UtilitiesProgramming.printDebugMessage("protectionCommandOP is true", new Exception());
				if(!player.isOp()) {
					player.sendMessage("OP protection is enabled.");
					return false;
				}
			}
			Commands commands = Commands.lookup(label);
			MyCommand myCommand = new CommandEmpty(player, command, args);
			switch(commands) {
			case NONE:
				break;
			case STAGE:
				break;
			case STAGEOP:
				break;
			case TALKER:
				myCommand = new CommandTalker(player, command, args);
				break;
			case TALKEROP:
				break;
			case VIRTUALRYUUGAKU:
				break;
			case VIRTUALRYUUGAKUOP:
				break;
			case VRG:
				break;
			case VRGOP:
				break;
			default:
				break;
			}
			myCommand.runCommand();
		}
		return false;
	}
}
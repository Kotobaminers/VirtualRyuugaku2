package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.common.common.CommandEmpty;
import com.gmail.fukushima.kai.talker.talker.CommandTalker;
import com.gmail.fukushima.kai.talker.talker.CommandTalkerOP;
import com.gmail.fukushima.kai.utilities.utilities.MyCommand;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class CommandExecutorPlugin implements CommandExecutor {
	public enum Commands {
		VIRTUALRYUUGAKU, VRG,
		VIRTUALRYUUGAKUOP, VRGOP, VRGCO,
		STAGE, STAGEOP,
		TALKER, TALKEROP,
		;
		public static Commands lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return Commands.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return Commands.VRG;
			}
		}
	}
	public CommandExecutorPlugin(VirtualRyuugaku2 plugin) {
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Commands commands = Commands.lookup(label);
		if(commands.equals(Commands.VRGCO)) {
			new CommandVirturalRyuugakuConsole().printDebug(args);
			return true;
		}
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
			MyCommand myCommand = new CommandEmpty(player, command, args);
			switch(commands) {
			case TALKER:
				myCommand = new CommandTalker(player, command, args);
				break;
			case TALKEROP:
				myCommand = new CommandTalkerOP(player, command, args);
				break;
			case VIRTUALRYUUGAKU:
				player.sendMessage(DataManagerPlugin.plugin.getName() + " developped by kai_f");
				break;
			case VRG:
				player.sendMessage(DataManagerPlugin.plugin.getName() + " developped by kai_f");
				break;
			case VIRTUALRYUUGAKUOP:
				myCommand = new CommandVirtualRyuugakuOP(player, command, args);
				break;
			case VRGOP:
				myCommand = new CommandVirtualRyuugakuOP(player, command, args);
				break;
			//VRGCO should be placed the upper section.
			default:
				break;
			}
			myCommand.runCommand();
			return true;
		}
		return false;
	}
}
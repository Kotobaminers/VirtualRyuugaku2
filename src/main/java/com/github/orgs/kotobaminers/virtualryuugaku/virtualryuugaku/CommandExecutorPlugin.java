package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.CommandEmpty;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.CommandTalker;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.CommandTalkerOP;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandExecutorPlugin implements CommandExecutor {
	public enum Commands {
		VIRTUALRYUUGAKU, VRG,
		VIRTUALRYUUGAKUOP, VRGOP, VRGDBG,
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
	public CommandExecutorPlugin(VirtualRyuugaku plugin) {
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Commands commands = Commands.lookup(label);
		if(commands.equals(Commands.VRGDBG)) {
			if(sender instanceof Player) {
				if(!sender.isOp()) return false;
			}
			new CommandVirturalRyuugakuDebug().printDebug(args);
			return true;
		}
		if(sender instanceof Player) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			Player player = (Player) sender;
			MyCommand myCommand = new CommandEmpty(player, command, args);
			switch(commands) {
			case TALKER:
				myCommand = new CommandTalker(player, command, args);
				break;
			case TALKEROP:
				myCommand = new CommandTalkerOP(player, command, args);
				break;
			case VIRTUALRYUUGAKU:
			case VRG:
				player.sendMessage(DataManagerPlugin.plugin.getName() + " developped by kai_f");
				break;
			case VIRTUALRYUUGAKUOP:
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
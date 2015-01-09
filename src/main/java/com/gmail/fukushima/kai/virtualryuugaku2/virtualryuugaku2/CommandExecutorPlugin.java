package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.common.common.CommandEmpty;
import com.gmail.fukushima.kai.shadow.shadow.CommandVirtualRyuugakuShadow;
import com.gmail.fukushima.kai.stage.stage.CommandStage;
import com.gmail.fukushima.kai.talker.talker.CommandTalker;
import com.gmail.fukushima.kai.talker.talker.CommandTalkerOP;
import com.gmail.fukushima.kai.utilities.utilities.MyCommand;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class CommandExecutorPlugin implements CommandExecutor {
	public enum Commands {
		NONE,
		VIRTUALRYUUGAKU, VRG,
		VIRTUALRYUUGAKUOP, VRGOP, VRGCO,
		STAGE, STAGEOP,
		TALKER, TALKEROP,
		SHADOW, SHADOWOP


		;
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
			case NONE:
				break;
			case STAGE:
				myCommand = new CommandStage(player, command, args);
				break;
			case STAGEOP:
				break;
			case TALKER:
				myCommand = new CommandTalker(player, command, args);
				break;
			case TALKEROP:
				myCommand = new CommandTalkerOP(player, command, args);
				break;
			case VIRTUALRYUUGAKU:
				break;
			case VRG:
				break;
			case VIRTUALRYUUGAKUOP:
				myCommand = new CommandVirtualRyuugakuOP(player, command, args);
				break;
			case VRGOP:
				myCommand = new CommandVirtualRyuugakuOP(player, command, args);
				break;
			case SHADOW:
				myCommand = new CommandVirtualRyuugakuShadow(player, command, args);
				break;
			case SHADOWOP:
				break;
			//VRGCO should be placed the uppersection.
			default:
				break;
			}
			myCommand.runCommand();
			return true;
		}
		return false;
	}
}
package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.utilities.utilities.MyCommand;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class CommandVirtualRyuugakuOP extends MyCommand {
	public CommandVirtualRyuugakuOP(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	public enum Commands {
		NONE, RELOAD, SAVE, DEBUGMODE;
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
		if(!player.isOp()) return;
		if(0 < args.length) {
			Commands commands = Commands.lookup(args[0]);
			switch(commands) {
			case NONE:
				break;
			case RELOAD:
				commandReload();
				break;
			case SAVE:
				commandSave();
				break;
			case DEBUGMODE:
				commandDebugMode();
				break;
			default:
				break;
			}
		}
	}
	private void commandDebugMode() {
		if(!Settings.debugMessage) {
			Settings.debugMessage = true;
			player.sendMessage("[VRG Debug] Message = " + Settings.debugMessage + ", BC = " + Settings.debugMessageBroadcast);
		} else {
			if(!Settings.debugMessageBroadcast) {
				Settings.debugMessageBroadcast = true;
				player.sendMessage("[VRG Debug] Message = " + Settings.debugMessage + ", BC = " + Settings.debugMessageBroadcast);
			} else {
				Settings.debugMessage = false;
				Settings.debugMessageBroadcast = false;
				player.sendMessage("[VRG Debug] Message = " + Settings.debugMessage + ", BC = " + Settings.debugMessageBroadcast);
			}
		}
	}
	private void commandReload() {
		DataManagerPlugin.loadPlugin();;
	}
	private void commandSave() {
		DataManagerPlugin.savePlugin();;
	}
}
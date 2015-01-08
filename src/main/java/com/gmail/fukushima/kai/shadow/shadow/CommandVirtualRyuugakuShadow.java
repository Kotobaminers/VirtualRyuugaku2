package com.gmail.fukushima.kai.shadow.shadow;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.utilities.utilities.MyCommand;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class CommandVirtualRyuugakuShadow extends MyCommand {
	public CommandVirtualRyuugakuShadow(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	public enum Commands {
		NONE, SENTENCE, ANSWER, QUESTION, DELETE;
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
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(0 < args.length) {
			Commands commands = Commands.lookup(args[0]);
			switch(commands) {
			case NONE:
				break;
			case ANSWER:
//				commandAnswer();
				break;
			case DELETE:
				break;
			case QUESTION:
				break;
			case SENTENCE:
				break;
			default:
				break;
			}

		}
	}
}

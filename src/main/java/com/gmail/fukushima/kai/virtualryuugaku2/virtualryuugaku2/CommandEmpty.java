package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.common.common.MyCommand;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class CommandEmpty extends MyCommand {
	public CommandEmpty(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	@Override
	public void runCommand() {
		UtilitiesProgramming.printDebugMessage("Empty Command", new Exception());
	}
}

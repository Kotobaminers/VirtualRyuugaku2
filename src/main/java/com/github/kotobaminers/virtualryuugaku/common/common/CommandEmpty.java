package com.github.kotobaminers.virtualryuugaku.common.common;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandEmpty extends MyCommand {
	public CommandEmpty(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	@Override
	public void runCommand() {
		UtilitiesProgramming.printDebugMessage("Empty Command", new Exception());
	}
}

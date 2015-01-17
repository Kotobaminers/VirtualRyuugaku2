package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public abstract class MyCommand {
	public Player player;
	public Command command;
	public String[] args;
	public MyCommand(Player player, Command command, String[] args) {
		this.player = player;
		this.command = command;
		this.args = args;
	}
	public abstract void runCommand();
}
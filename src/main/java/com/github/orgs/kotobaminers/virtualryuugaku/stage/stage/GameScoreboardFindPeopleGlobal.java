package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import org.bukkit.ChatColor;

public class GameScoreboardFindPeopleGlobal extends GameScoreboard {
	private static final String NAME_BOARD = "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "* Find People *";

	@Override
	public String getNameBoard() {
		return NAME_BOARD;
	}
}
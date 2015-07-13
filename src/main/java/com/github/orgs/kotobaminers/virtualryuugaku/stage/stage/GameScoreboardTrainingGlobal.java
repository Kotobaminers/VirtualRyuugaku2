package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import org.bukkit.ChatColor;

public class GameScoreboardTrainingGlobal extends GameScoreboard {
	private static final String NAME_BOARD = "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "* Training *";

	@Override
	public String getNameBoard() {
		return NAME_BOARD;
	}
}
package com.github.orgs.kotobaminers.virtualryuugaku.game.game;

import java.util.LinkedHashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.ScoreboardUtility;

public class GameGlobalScoreboard extends ScoreboardUtility {
	private static Scoreboard scoreboard;
	private static Objective objective;
	private static final String NAME_BOARD = "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "* SCORE *";

	public void update(Player player, LinkedHashMap<String, Integer> scores) {
		updateScoreboard(player, NAME_BOARD, scores);
	}

	@Override
	public Scoreboard getScoreboard(String key) {
		return scoreboard;
	}

	@Override
	public Objective getObjective(String key) {
		return objective;
	}

	@Override
	public void putScoreboard(String name, Scoreboard board) {
		scoreboard = board;
	}

	@Override
	public void putObjective(String name, Objective obj) {
		objective = obj;
	}
}
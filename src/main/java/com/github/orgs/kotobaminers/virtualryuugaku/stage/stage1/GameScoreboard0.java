package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage1;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.ScoreboardUtility;

public abstract class GameScoreboard0 extends ScoreboardUtility {
	private static Scoreboard scoreboard;
	private static Objective objective;

	public void update(Player player) {
		updateScoreboard(player, getNameBoard(), GameGlobal0.scores);
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
	public abstract String getNameBoard();
}
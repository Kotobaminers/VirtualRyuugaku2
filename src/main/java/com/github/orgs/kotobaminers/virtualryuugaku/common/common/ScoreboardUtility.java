package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.LinkedHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.DataManagerPlugin;

public abstract class ScoreboardUtility {
	public void updateScoreboard(Player player, String name, LinkedHashMap<String, Integer> scores) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		setupScoreboard(player);
		Objective objective = getObjective(player.getName());
		objective.setDisplayName(name);
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for (String label : scores.keySet()) {
			UtilitiesProgramming.printDebugMessage("" + label + " " + scores.get(label), new Exception());
			Score score = objective.getScore(label);
			score.setScore(scores.get(label).intValue());
		}
		player.setScoreboard(getScoreboard(player.getName()));
		SchedulerScoreboardHide scheduler = new SchedulerScoreboardHide();
		scheduler.setPlayer(player);
		scheduler.runTaskLater(DataManagerPlugin.plugin, 20*4);
	}
	private void setupScoreboard(Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("Board", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		putScoreboard(player.getName(), board);
		putObjective(player.getName(), objective);
	}
	public abstract Scoreboard getScoreboard(String key);
	public abstract Objective getObjective(String key);
	public abstract void putScoreboard(String name, Scoreboard board);
	public abstract void putObjective(String name, Objective objective);
}
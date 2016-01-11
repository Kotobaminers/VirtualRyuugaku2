package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.VirtualRyuugakuManager;

public abstract class ScoreboardUtility extends BukkitRunnable {
	private Scoreboard board;
	private Objective objective;
	private Player player;
	public Map<String, Integer> score = new HashMap<String, Integer>();

	public void updateScoreboard(Player player) {
		Debug.printDebugMessage("", new Exception());
		setupScoreboard();
		objective.setDisplayName(getName());
		Debug.printDebugMessage("", new Exception());
		for (String label : score.keySet()) {
			Debug.printDebugMessage("" + label + " " + score.get(label), new Exception());
			Score scoreObject = objective.getScore(label);
			scoreObject.setScore(score.get(label).intValue());
		}
		player.setScoreboard(board);
		hideScoreboard(player);
	}

	private void setupScoreboard() {
		Debug.printDebugMessage("", new Exception());
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("Board", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.board = board;
		this.objective = objective;
	}

	public void hideScoreboard(Player player) {
		this.player = player;
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.runTaskLater(VirtualRyuugakuManager.plugin, this, 100L);
	}

	@Override
	public void run() {
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	public abstract String getName();
}
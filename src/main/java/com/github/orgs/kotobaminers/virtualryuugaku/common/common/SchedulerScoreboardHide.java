package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SchedulerScoreboardHide extends BukkitRunnable {
	private Player player;
	public SchedulerScoreboardHide create() {
		return new SchedulerScoreboardHide();
	}
	@Override
	public void run() {
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
}
package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import org.bukkit.entity.Player;


public class PublicEmptyGame extends PublicGame {
	public static long interval = 20L * 30;

	@Override
	public void continueGame() {
	}

	@Override
	public void giveCurrentQuestion(Player player) {
	}

	@Override
	public boolean hasQuestedAll() {
		return false;
	}

	@Override
	public long getInterval() {
		return interval;
	}

	@Override
	public void validateAnswer(Player player, String answer) {
	}

	@Override
	public void eventCorrect(Player player) {
	}

	@Override
	public void eventWrong(Player player) {
	}

	@Override
	protected void printStart(Player player) {
		// TODO Auto-generated method stub

	}


}

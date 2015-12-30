package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public abstract class PublicGame {
	public static PublicGameScore score = new PublicGameScore();

	public abstract void continueGame();
	public void finishGame() {
		UtilitiesProgramming.printDebugMessage("",new Exception());
		broadcastResult();
		score.broadcastScore();
	}

	private void broadcastResult() {
		UtilitiesProgramming.printDebugMessage("",new Exception());
	}

	public abstract boolean isFinished();
	public abstract long getInterval();
	public abstract void validateAnswer(String answer);
	public abstract void validateEvent(Player player);

	public abstract void eventCorrect();
	public abstract void eventWrong();
}
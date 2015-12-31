package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGameController.PublicGameMode;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public abstract class PublicGame {
	public PublicGameScore score = new PublicGameScore();
	public PublicGameMode mode = PublicGameMode.FIND_PEOPLE;

	public abstract void continueGame();
	public void finishGame() {
		Debug.printDebugMessage("",new Exception());
		broadcastResult();
		score.broadcastScore();
	}

	private void broadcastResult() {
		Debug.printDebugMessage("",new Exception());
	}

	public abstract boolean isFinished();
	public abstract long getInterval();
	public abstract void validateAnswer(String answer);
	public abstract void validateEvent(Player player);

	public abstract void eventCorrect();
	public abstract void eventWrong();
}
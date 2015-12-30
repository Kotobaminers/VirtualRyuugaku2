package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message0;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class PublicEventGame extends PublicGame {

	private long interval = 30L;
	private boolean finished = false;

	@Override
	public void continueGame() {
		UtilitiesProgramming.printDebugMessage("",  new Exception());
		broadcastGame();
		finished = true;
	}

	private void broadcastGame() {
		UtilitiesProgramming.printDebugMessage("",  new Exception());
		MessengerGeneral.broadcast(MessengerGeneral.getMessage(Message0.PUBLIC_GAME_FIND_PEOPLE_0, null));
	}

	@Override
	public boolean isFinished() {
		return finished;
	}
	@Override
	public long getInterval() {
		return interval;
	}

	@Override
	public void validateAnswer(String answer) {
	}

	@Override
	public void eventCorrect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventWrong() {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateEvent(Player player) {
		// TODO
	}
}
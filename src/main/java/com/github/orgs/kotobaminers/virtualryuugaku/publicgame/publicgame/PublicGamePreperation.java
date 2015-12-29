package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message0;

public class PublicGamePreperation extends PublicGame {

	private Integer duration = 10;
	private boolean finished = false;

	@Override
	public void continueGame() {
		broadcastGame();
	}

	private void broadcastGame() {
		MessengerGeneral.broadcast(MessengerGeneral.getMessage(Message0.PUBLIC_GAME_PREPERATION_0, null));
	}

	@Override
	public boolean isFinished() {
		return false;
	}

}

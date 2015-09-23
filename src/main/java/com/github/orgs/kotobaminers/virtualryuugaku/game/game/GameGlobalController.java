package com.github.orgs.kotobaminers.virtualryuugaku.game.game;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Controller;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;

public class GameGlobalController extends Controller {

	public GameGlobal game;

	@Override
	public void setStorage() {
		game = new GameGlobal();
	}

	@Override
	public Storage getStorage() {
		return game;
	}

}

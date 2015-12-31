package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Controller;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;

public class PlayerDataController extends Controller {
	public static Storage storage = new PlayerDataStorage();

	@Override
	public void initializeStorage() {
		storage = new PlayerDataStorage();
		storage.initialize();
	}

	@Override
	public Storage getStorage() {
		return storage;
	}
}
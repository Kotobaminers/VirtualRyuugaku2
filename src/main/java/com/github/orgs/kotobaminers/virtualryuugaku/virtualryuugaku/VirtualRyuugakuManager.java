package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Romaji;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.StageController;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.StageStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataController;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public final class VirtualRyuugakuManager {
	public static VirtualRyuugaku plugin;
	public static void loadPlugin() {
		Debug.printDebugMessage("[VirtualRyuugaku] Loading Library Data", new Exception());
		new Romaji().load();

		Debug.printDebugMessage("[VirtualRyuugaku] Loading Player Data", new Exception());
		new PlayerDataController().initializeStorage();

		Debug.printDebugMessage("[VirtualRyuugaku] Initializing Conversation", new Exception());
		new StageController().initializeStorage();
		StageStorage.printDebugMessage();

	}

	public static void savePlugin() {
		Debug.printDebugMessage("[VRG] Saving Virtual Ryuugaku...", new Exception());
//		new ControllerConversation().saveStorage();
//		new DataManagerPlayer().save();

	}
}
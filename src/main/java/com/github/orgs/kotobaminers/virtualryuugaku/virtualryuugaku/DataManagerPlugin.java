package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.DataManagerRomaji;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.LibraryManager;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.ConfigHandlerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public final class DataManagerPlugin {
	public static VirtualRyuugaku plugin;
	public static void initializeLoader(VirtualRyuugaku plugin) {
		DataManagerPlugin.plugin = plugin;
	}
	public static void loadPlugin() {

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Library Data", new Exception());
		new LibraryManager().initialize(plugin);
		new DataManagerRomaji().load();

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Player Data", new Exception());
		new ConfigHandlerPlayer().initialize(plugin);
		new DataManagerPlayer().load();

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Initializing Conversation", new Exception());
		new ControllerConversation().initializeStorage();

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Finishing Loading Plugin Data", new Exception());
	}

	public static void savePlugin() {
		UtilitiesProgramming.printDebugMessage("[VRG] Saving Virtual Ryuugaku...", new Exception());
		new ControllerConversation().saveStorage();

//		new DataManagerPlayer().save();

	}
}
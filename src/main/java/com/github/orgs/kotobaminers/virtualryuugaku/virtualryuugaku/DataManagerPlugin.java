package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataManagerCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.DataManagerRomaji;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.LibraryManager;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.ConfigHandlerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.ConfigHandlerTalker;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.DataManagerTalker;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public final class DataManagerPlugin {
	public static VirtualRyuugaku plugin;
	public static void initializeLoader(VirtualRyuugaku plugin) {
		DataManagerPlugin.plugin = plugin;
	}
	public static void loadPlugin() {
		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Citizens Data", new Exception());
		new DataManagerCitizens().loadAll();

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Library Data", new Exception());
		new LibraryManager().initialize(plugin);
		new DataManagerRomaji().loadAll();

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Player Data", new Exception());
		new ConfigHandlerPlayer().initialize(plugin);
		new DataManagerPlayer().loadAll();

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Talker Data", new Exception());
		new ConfigHandlerTalker().initialize(plugin);
		new DataManagerTalker().loadAll();

		savePlugin();

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Finishing Loading Plugin Data", new Exception());
	}

	public static void savePlugin() {
		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Saving Player Data", new Exception());
		new DataManagerPlayer().saveAll();

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Saving Talker Data", new Exception());
		new DataManagerTalker().saveAll();

	}
}
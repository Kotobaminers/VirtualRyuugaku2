package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import com.gmail.fukushima.kai.citizens.citizens.DataManagerCitizens;
import com.gmail.fukushima.kai.common.common.DataManagerCommon;
import com.gmail.fukushima.kai.player.player.ConfigHandlerPlayer;
import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.talker.talker.ConfigHandlerTalker;
import com.gmail.fukushima.kai.talker.talker.DataManagerTalker;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public final class DataManagerPlugin {
	public static VirtualRyuugaku2 plugin;
	public static void initializeLoader(VirtualRyuugaku2 plugin) {
		DataManagerPlugin.plugin = plugin;
	}
	public static void loadPlugin() {
		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Citizens Data", new Exception());
		new DataManagerCitizens().load();

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Common Data", new Exception());
		new DataManagerCommon().load();

//		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Stage", new Exception());
//		new DataManagerStage().load();//This needs to be loaded after importRomaji() and importCitizens()

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Player Data", new Exception());
		new ConfigHandlerPlayer().initialize(plugin);
		new DataManagerPlayer().load();

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Talker Data", new Exception());
		new ConfigHandlerTalker().initialize(plugin);
		new DataManagerTalker().load();

//		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Shadow", new Exception());
//		new ConfigHandlerComment().initialize(plugin);
//		new DataManagerComment().load();

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Finishing Loading Plugin Data", new Exception());
	}

	public static void savePlugin() {
		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Saving Player Data", new Exception());
		new DataManagerPlayer().saveAll();

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Saving Talker Data", new Exception());
		new DataManagerTalker().saveAll();

//		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Saving Comment Data", new Exception());
//		new DataManagerComment().saveAll();
	}
}
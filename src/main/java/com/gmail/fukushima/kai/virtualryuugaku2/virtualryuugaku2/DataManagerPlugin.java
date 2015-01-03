package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import com.gmail.fukushima.kai.citizens.citizens.DataManagerCitizens;
import com.gmail.fukushima.kai.common.common.DataManagerCommon;
import com.gmail.fukushima.kai.mystage.mystage.DataManagerStage;
import com.gmail.fukushima.kai.player.player.ConfigHandlerPlayer;
import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public final class DataManagerPlugin {
	public static VirtualRyuugaku2 plugin;
	public static void initializeLoader(VirtualRyuugaku2 plugin) {
		DataManagerPlugin.plugin = plugin;
	}
	public static void loadPlugin() {
		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Citizens", new Exception());
		new DataManagerCitizens().load();

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Common", new Exception());
		new DataManagerCommon().load();

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Stage", new Exception());
		new DataManagerStage().load();//This needs importRomaji() and importCitizens()

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Player", new Exception());
		new ConfigHandlerPlayer().initialize(plugin);
		new DataManagerPlayer().load();//This needs ConfigHandlerPlayer().initialize(plugin)

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Successfully Loaded Plugin Data", new Exception());
	}

	public static void savePlugin() {
//		DataManagerPlayer.saveMapDataPlayer();
		//TODO DataManager.save***();
	}
}
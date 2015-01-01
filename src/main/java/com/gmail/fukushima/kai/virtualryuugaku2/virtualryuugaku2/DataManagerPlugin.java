package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import java.io.IOException;

import com.gmail.fukushima.kai.common.common.ConfigHandlerPlayer;
import com.gmail.fukushima.kai.common.common.DataManagerCitizens;
import com.gmail.fukushima.kai.common.common.DataManagerCommon;
import com.gmail.fukushima.kai.common.common.DataManagerPlayer;
import com.gmail.fukushima.kai.mystage.mystage.DataManagerStage;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public final class DataManagerPlugin {
	public static VirtualRyuugaku2 plugin;
	public static void initializeLoader(VirtualRyuugaku2 plugin) {
		DataManagerPlugin.plugin = plugin;
	}
	public static void loadPlugin() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataManagerCitizens.importCitizens();

		UtilitiesProgramming.printDebugMessage("", new Exception());
		try {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			DataManagerCommon.importRomaji();
		} catch (IOException e) {
			e.printStackTrace();
		}

		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataManagerStage.importStage();//This needs importRomaji() and importCitizens()

		new ConfigHandlerPlayer().initialize(plugin);
		DataManagerPlayer.importDataPlayer();//This needs ConfigHandlerPlayer().initialize(plugin)
	}

	public static void savePlugin() {
		DataManagerPlayer.saveMapDataPlayer();
	}
}
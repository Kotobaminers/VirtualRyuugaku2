package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import java.io.IOException;

import com.gmail.fukushima.kai.common.common.DataManagerCitizens;
import com.gmail.fukushima.kai.common.common.DataManagerCommon;
import com.gmail.fukushima.kai.common.common.UtilitiesProgramming;
import com.gmail.fukushima.kai.mystage.mystage.DataManagerStage;


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
	}
}
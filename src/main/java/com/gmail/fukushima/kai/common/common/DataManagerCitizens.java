package com.gmail.fukushima.kai.common.common;

import java.io.File;

import net.citizensnpcs.api.npc.NPCDataStore;
import net.citizensnpcs.api.npc.SimpleNPCDataStore;
import net.citizensnpcs.api.util.Storage;
import net.citizensnpcs.api.util.YamlStorage;
import net.citizensnpcs.npc.CitizensNPCRegistry;

import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;
import com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2.DataManagerPlugin;


public class DataManagerCitizens {
	public static CitizensNPCRegistry npcs;
	private static String citizensDataFolder = DataManagerPlugin.plugin.getDataFolder().getParent() + "//Citizens//saves.yml";

	public static void importCitizens() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		NPCDataStore saves = createStorage();
		npcs = new CitizensNPCRegistry(saves);
		saves.loadInto(npcs);
	}

	private static NPCDataStore createStorage() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Storage saves = null;
		if (saves == null) {
			saves = new YamlStorage(new File(citizensDataFolder), "Citizens NPC Storage");
		}
		if (!saves.load()) {
			return null;
		}
		NPCDataStore data = SimpleNPCDataStore.create(saves);
		return data;
	}
}
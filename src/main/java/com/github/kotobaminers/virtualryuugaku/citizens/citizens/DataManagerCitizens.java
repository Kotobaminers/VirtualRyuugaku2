package com.github.kotobaminers.virtualryuugaku.citizens.citizens;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.kotobaminers.virtualryuugaku.utilities.utilities.DataManager;
import com.github.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.kotobaminers.virtualryuugaku.virtualryuugaku.DataManagerPlugin;

public class DataManagerCitizens implements DataManager {
	private static Map<Integer, DataCitizens> mapDataCitizns = new HashMap<Integer, DataCitizens>();
	private static String citizensDataFolder = DataManagerPlugin.plugin.getDataFolder().getParent() + "//Citizens//saves.yml";
	private static final String KEY_NPC = "npc";
	private static final String KEY_NAME = "name";

	@Override
	public void loadAll() {
		initialize();
		importCitizens();
	}
	@Override
	public void initialize() {
		setMapDataCitizns(new HashMap<Integer, DataCitizens>());
	}
	private static void importCitizens() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(citizensDataFolder));
		MemorySection memory = (MemorySection) config.get(KEY_NPC);
		for(String key : memory.getKeys(false)) {
			Integer id = Integer.parseInt(key);
			String name = memory.getString(key + "." + KEY_NAME);
			if(0 <= id) {
				DataCitizens data = new DataCitizens();
				data.id = id;
				data.name = name;
				getMapDataCitizns().put(id, data);
			}
		}
	}

	@Override
	public void saveAll() {
	}
	public static Map<Integer, DataCitizens> getMapDataCitizns() {
		return mapDataCitizns;
	}
	private static void setMapDataCitizns(Map<Integer, DataCitizens> mapDataCitizns) {
		DataManagerCitizens.mapDataCitizns = mapDataCitizns;
	}

}
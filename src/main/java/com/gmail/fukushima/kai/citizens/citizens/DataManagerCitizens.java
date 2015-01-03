package com.gmail.fukushima.kai.citizens.citizens;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.fukushima.kai.utilities.utilities.DataManager;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;
import com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2.DataManagerPlugin;

public class DataManagerCitizens implements DataManager {
	public static Map<Integer, DataCitizens> mapDataCitizns = new HashMap<Integer, DataCitizens>();
	private static String citizensDataFolder = DataManagerPlugin.plugin.getDataFolder().getParent() + "//Citizens//saves.yml";
	private static final String KEY_NPC = "npc";
	private static final String KEY_NAME = "name";

	private static void importCitizens() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(citizensDataFolder));
		MemorySection memory = (MemorySection) config.get(KEY_NPC);
		for(String key : memory.getKeys(false)) {
			UtilitiesProgramming.printDebugMessage(key, new Exception());
			Integer id = Integer.valueOf(key);
			String name = memory.getString(key + "." + KEY_NAME);
			if(0 <= id) {
				DataCitizens data = new DataCitizens();
				data.id = id;
				data.name = name;
				mapDataCitizns.put(id, data);
				UtilitiesProgramming.printDebugCitizens(data);
			}
		}
	}
	public static String loadNameById(Integer id) {
		DataCitizens data = mapDataCitizns.get(id);
		String name = data.name;
		return name;
	}
	public static Boolean isValidId(Integer id) {
		for(DataCitizens data : mapDataCitizns.values()) {
			if(data.id.equals(id)) return true;
		}
		return false;
	}
	@Override
	public void initialize() {
		mapDataCitizns = new HashMap<Integer, DataCitizens>();
	}
	@Override
	public void load() {
		initialize();
		importCitizens();
	}
	@Override
	public void saveAll() {
		// TODO Auto-generated method stub
	}
}
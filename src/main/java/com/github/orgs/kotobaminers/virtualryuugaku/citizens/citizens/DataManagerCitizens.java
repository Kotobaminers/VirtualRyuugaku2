package com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.DataManager;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.DataManagerPlugin;

public class DataManagerCitizens implements DataManager {
	private static Map<Integer, DataCitizens> mapDataCitizens = new HashMap<Integer, DataCitizens>();
	private static String citizensDataFolder = DataManagerPlugin.plugin.getDataFolder().getParent() + "//Citizens//saves.yml";
	private static final String KEY_NPC = "npc";
	private static final String KEY_NAME = "name";
	private static final String KEY_LOCATION = "traits.location";
	private static final String KEY_WORLD = "world";
	private static final String KEY_X = "x";
	private static final String KEY_Y = "y";
	private static final String KEY_Z = "z";

	public static void addNPC(Integer id) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(citizensDataFolder));
		MemorySection memory = (MemorySection) config.get(KEY_NPC);
		if(memory.contains(id.toString())) {
			String name = memory.getString(id.toString() + "." + KEY_NAME);
			DataCitizens data = new DataCitizens();
			data.id = id;
			data.name = name;
			getMapDataCitizens().put(id, data);
		} else {
			UtilitiesProgramming.printDebugMessage("NON Existing ID in citizens", new Exception());
		}
	}

	public static Boolean existsNPC(Integer id) {
		if(getMapDataCitizens().keySet().contains(id)) {
			return true;
		} else {
			UtilitiesProgramming.printDebugMessage("Invalid DataCitizens ID: " + id, new Exception());
		}
		return false;
	}

	public static DataCitizens getDataCitizens(Integer id) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataCitizens data = new DataCitizens();
		if(getMapDataCitizens().keySet().contains(id)) {
			data = getMapDataCitizens().get(id);
		} else {
			UtilitiesProgramming.printDebugMessage("Invalid DataCitizens ID: " + id, new Exception());
		}
		return data;
	}

	@Override
	public void load() {
		initialize();
		importCitizens();
	}
	@Override
	public void initialize() {
		setMapDataCitizens(new HashMap<Integer, DataCitizens>());
	}
	private static void importCitizens() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(citizensDataFolder));
		MemorySection memory = (MemorySection) config.get(KEY_NPC);
		for(String key : memory.getKeys(false)) {
			Integer id = Integer.parseInt(key);
			if(0 <= id) {
				try {
					String name = memory.getString(key + "." + KEY_NAME);
					DataCitizens data = new DataCitizens();
					data.id = id;
					data.name = name;
					MemorySection memoryLocation = (MemorySection) memory.get(key + "." + KEY_LOCATION);
					String worldName = memoryLocation.getString(KEY_WORLD);
					World world = DataManagerPlugin.plugin.getServer().getWorld(worldName);
					Double x = Double.parseDouble(memoryLocation.getString(KEY_X));
					Double y = Double.parseDouble(memoryLocation.getString(KEY_Y));
					Double z = Double.parseDouble(memoryLocation.getString(KEY_Z));
					data.location = new Location(world, x, y, z);
					UtilitiesProgramming.printDebugMessage(x.toString()+y.toString()+z.toString()+worldName , new Exception());
					getMapDataCitizens().put(id, data);
				} catch(Exception e){
				}
			}
		}
	}

	@Override
	public void save() {
	}

	public static Map<Integer, DataCitizens> getMapDataCitizens() {
		return mapDataCitizens;
	}
	private static void setMapDataCitizens(Map<Integer, DataCitizens> mapDataCitizens) {
		DataManagerCitizens.mapDataCitizens = mapDataCitizens;
	}
}
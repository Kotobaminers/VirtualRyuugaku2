package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.YamlController;

public class ConfigCitizens implements YamlController {

	public Map<Integer, String> names = new HashMap<Integer, String>();

	public static final String FILE = Bukkit.getPluginManager().getPlugin("Citizens").getDataFolder() + "//saves.yml";
	private static YamlConfiguration config = null;

	private static final String KEY_NPC = "npc";
	private static final String KEY_NAME = "name";

	public boolean existsNPC(Integer id) {
		if (names.containsKey(id)) {
			return true;
		}
		return false;
	}

	public String getNPCName(Integer id) {
		String name = "";
		if (names.containsKey(id)) {
			name = names.get(id);
		}
		return name;
	}

	@Override
	public void importConfiguration() {
		setConfig();
		UtilitiesProgramming.printDebugMessage("", new Exception());
		MemorySection memory = (MemorySection) config.get(KEY_NPC);
		for(String key : memory.getKeys(false)) {
			try {
			Integer id = Integer.parseInt(key);
				if(0 <= id) {
					String name = memory.getString(key + "." + KEY_NAME);
					names.put(id, name);
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	@Override
	public void setConfig() {
		config = YamlConfiguration.loadConfiguration(new File(FILE));
	}
	@Override
	public YamlConfiguration getConfig() {
		return config;
	}
}

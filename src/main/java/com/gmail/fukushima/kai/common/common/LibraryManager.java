package com.gmail.fukushima.kai.common.common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fukushima.kai.utilities.utilities.InitializerWithPlugin;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;


public final class LibraryManager implements InitializerWithPlugin {
	private static final String DIRECTORY_BASE = "LIBRARY";
	private static final String EXTENSION_YAML = ".yml";
	private static String directory;
	private enum Library {STAGE}
	@Override
	public void initialize(JavaPlugin plugin) {
		directory = plugin.getDataFolder() + "//" + DIRECTORY_BASE + "//";
	}
	public static Map<String, YamlConfiguration> getListLibraryStage() {
		Map<String, YamlConfiguration> list = new HashMap<String, YamlConfiguration>();
		File stage = new File(directory + Library.STAGE.toString());
		UtilitiesProgramming.printDebugMessage(stage.getAbsolutePath(), new Exception());
		File[] files = stage.listFiles();
		for(File file : files) {
			if(file.getAbsolutePath().endsWith(EXTENSION_YAML)) {
				String name = file.getName().substring(0, file.getName().length() - EXTENSION_YAML.length());
				list.put(name, YamlConfiguration.loadConfiguration(file));
			}
		}
		return list;
	}
}
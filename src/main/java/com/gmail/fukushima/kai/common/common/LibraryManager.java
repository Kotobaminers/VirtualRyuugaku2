package com.gmail.fukushima.kai.common.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
		directory = plugin.getDataFolder() + "\\" + DIRECTORY_BASE + "\\";
	}
	public static List<YamlConfiguration> getListLibraryStage() {
		List<YamlConfiguration> list = new ArrayList<YamlConfiguration>();
		File stage = new File(directory + Library.STAGE.toString());
		UtilitiesProgramming.printDebugMessage(stage.getAbsolutePath(), new Exception());
		File[] files = stage.listFiles();
		for(File file : files) {
			file.getAbsolutePath().endsWith(EXTENSION_YAML);
			list.add(YamlConfiguration.loadConfiguration(file));
		}
		return list;
	}
}
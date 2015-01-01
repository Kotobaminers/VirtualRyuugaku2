package com.gmail.fukushima.kai.common.common;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fukushima.kai.utilities.utilities.ConfigHandler;

public class ConfigHandlerPlayer extends ConfigHandler {
	public static final String DIRECTORY = "PLAYER";
	public static final String FILE_NAME = "PLAYER.yml";
	public static final Integer lineInitial = 0;
	public enum PathPlayer {LINE}
	public static DataPlayer loadDataPlayer(String name) {
		DataPlayer data = new DataPlayer();
		data.name = name;
		data.line = loadLine(name);
		return data;
	}
	private static Integer loadLine(String name) {
		Integer line = lineInitial;
		String path = name + "." + PathPlayer.LINE.toString();
		if(ConfigHandlerPlayer.config.isInt(path)) {
			line = ConfigHandlerPlayer.config.getInt(path);
		}
		return line;
	}

	@Override
	public void initialize(JavaPlugin plugin) {
		String path = plugin.getDataFolder() + "\\" + DIRECTORY + "\\" +FILE_NAME;
		file = new File(path);
		config = YamlConfiguration.loadConfiguration(file);
	}
	public static void putDataPlayer(DataPlayer data) {
		String pathLine = data.name + "." + PathPlayer.LINE;
		config.set(pathLine, data.line);
	}
}
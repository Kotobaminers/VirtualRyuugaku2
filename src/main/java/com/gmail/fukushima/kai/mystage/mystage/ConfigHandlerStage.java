package com.gmail.fukushima.kai.mystage.mystage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2.DataManagerPlugin;

public class ConfigHandlerStage {
	public FileConfiguration config;
	public File file;
	public enum Path {CREATER, TALKER}

	public ConfigHandlerStage(String name) {
		file = new File(DataManagerPlugin.plugin.getDataFolder() + "\\" + "STAGE" + "\\" + name + ".yml");
		config = YamlConfiguration.loadConfiguration(file);
	}
	public ConfigHandlerStage(File file) {
		this.file = file;
		config = YamlConfiguration.loadConfiguration(file);
	}

	public void save(List<Stage> listStage) {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
package com.gmail.fukushima.kai.stage.stage;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class ConfigHandlerOldStage {
	/*
	 * This class is not extends ConfigHandler class
	 */
	public static final String DIRECTORY = "STAGE";
	public FileConfiguration config;
	public File file;
	public enum Path {CREATOR, TALKER}
	public ConfigHandlerOldStage(File file) {
		this.file = file;
		this.config = YamlConfiguration.loadConfiguration(file);
	}
}
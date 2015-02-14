package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.ConfigHandler;

public class GlobalStageConfigHandler extends ConfigHandler {
	public static YamlConfiguration config;
	public static File file;
	public static final String DIRECTORY = "STAGE";
	public static final String FILE_NAME = "CONFIG.yml";
	private enum Path {INTERVAL, READY}
	public static Integer importInterval() {
		Integer interval = config.getInt(Path.INTERVAL.toString());
		return interval;
	}
	public static Integer importReady() {
		Integer ready = config.getInt(Path.READY.toString());
		return ready;
	}
	@Override
	public File getFile() {
		return file;
	}
	@Override
	public YamlConfiguration getConfig() {
		return config;
	}
	@Override
	public void setFile(File file) {
		GlobalStageConfigHandler.file = file;
	}
	@Override
	public void setConfig(YamlConfiguration config) {
		GlobalStageConfigHandler.config = config;
	}
	@Override
	public String getFileName() {
		return FILE_NAME;
	}
	@Override
	public String getDirectory() {
		return DIRECTORY;
	}
}
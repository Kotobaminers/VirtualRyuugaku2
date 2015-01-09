package com.gmail.fukushima.kai.comment.comment;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.fukushima.kai.utilities.utilities.ConfigHandler;

public class ConfigHandlerComment extends ConfigHandler {
	public static YamlConfiguration config;
	public static File file;
	public static final String DIRECTORY = "COMMENT";
	public static final String FILE_NAME = "COMMENT.yml";
	public enum PathComment {COMMENT, STATE}
	public static DataComment loadDataComment(String owner) {
		DataComment data = new DataComment();
		return data;
	}
	public static void saveDataComment(DataComment data) {
		String path = data.owner + "." + data.id.toString() + "." + data.sender;
		config.set(path + "." + PathComment.COMMENT, data.comment);
		config.set(path + "." + PathComment.STATE, data.state.toString());
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
		ConfigHandlerComment.file = file;
	}
	@Override
	public void setConfig(YamlConfiguration config) {
		ConfigHandlerComment.config = config;
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
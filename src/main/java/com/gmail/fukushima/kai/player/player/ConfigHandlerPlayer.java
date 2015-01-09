package com.gmail.fukushima.kai.player.player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.fukushima.kai.player.player.DataPlayer.Language;
import com.gmail.fukushima.kai.utilities.utilities.ConfigHandler;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class ConfigHandlerPlayer extends ConfigHandler {
	public static YamlConfiguration config;
	public static File file;
	public static final String DIRECTORY = "PLAYER";
	public static final String FILE_NAME = "PLAYER.yml";
	public static final Integer lineInitial = 0;
	public static final Integer selectInitial = -1;
	public enum PathPlayer {LINE, SELECT, LANGUAGE, DONE}
	public static DataPlayer loadDataPlayer(String name) {
		DataPlayer data = new DataPlayer();
		data.name = name;
		data.line = loadLine(name);
		data.select = loadSelect(name);
		data.language = loadLanguage(name);
		data.done = loadDone(name);
		return data;
	}
	private static List<Integer> loadDone(String name) {
		List<Integer> done = new ArrayList<Integer>();
		String path = name + "." + PathPlayer.DONE.toString();
		if(ConfigHandlerPlayer.config.isList(path)) {
			done = ConfigHandlerPlayer.config.getIntegerList(path);
		}
		return done;
	}
	private static Language loadLanguage(String name) {
		String language = "";
		String path = name + "." + PathPlayer.LANGUAGE.toString();
		if(ConfigHandlerPlayer.config.isString(path)) {
			language = ConfigHandlerPlayer.config.getString(path);
		}
		return Language.lookup(language);
	}
	private static Integer loadLine(String name) {
		Integer line = lineInitial;
		String path = name + "." + PathPlayer.LINE.toString();
		if(ConfigHandlerPlayer.config.isInt(path)) {
			line = ConfigHandlerPlayer.config.getInt(path);
		}
		return line;
	}
	private static Integer loadSelect(String name) {
		Integer line = selectInitial;
		String path = name + "." + PathPlayer.SELECT.toString();
		if(ConfigHandlerPlayer.config.isInt(path)) {
			line = ConfigHandlerPlayer.config.getInt(path);
		}
		return line;
	}
	public static void saveDataPlayer(DataPlayer data) {
		String name = data.name;
		String pathLine = name + "." + PathPlayer.LINE;
		config.set(pathLine, data.line);
		String pathSelect = name + "." + PathPlayer.SELECT;
		config.set(pathSelect, data.select);
		String pathLanguage = name + "." + PathPlayer.LANGUAGE;
		config.set(pathLanguage, data.language.toString());
		String pathDone = name + "." + PathPlayer.DONE;
		config.set(pathDone, data.done);
		UtilitiesProgramming.printDebugPlayer(data);
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
		ConfigHandlerPlayer.file = file;
	}
	@Override
	public void setConfig(YamlConfiguration config) {
		ConfigHandlerPlayer.config = config;
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
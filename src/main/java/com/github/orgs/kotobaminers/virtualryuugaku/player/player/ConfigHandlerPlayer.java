package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.ConfigHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Language;

public class ConfigHandlerPlayer extends ConfigHandler {
	public static YamlConfiguration config;
	public static File file;
	public static final String DIRECTORY = "CONFIG";
	public static final String FILE_NAME = "config.yml";
	public static final Integer lineInitial = 0;
	public static final Integer selectInitial = -1;
	public static final String base = "PLAYER";
	public enum PathPlayer {LINE, SELECT, LANGUAGE, DONE}
	public static List<DataPlayer> importDataPlayer() {
		List<DataPlayer> list = new ArrayList<DataPlayer>();
		for(String key : config.getKeys(false)) {
			if(key.equalsIgnoreCase(base)) {
				MemorySection memory = (MemorySection) config.get(key);
				for(String name : memory.getKeys(false)) {
					MemorySection memoryPlayer = (MemorySection) memory.get(name);
					DataPlayer data = new DataPlayer();
					data.name = name;
					data.line = loadLine(memoryPlayer);
					data.select = loadSelect(memoryPlayer);
					data.language = loadLanguage(memoryPlayer);
					list.add(data);
				}
			}
		}
		return list;
	}

	private static Integer loadLine(MemorySection memory) {
		Integer line = lineInitial;
		String path = PathPlayer.LINE.toString();
		if(memory.isInt(path)) {
			line = memory.getInt(path);
		}
		return line;
	}
	private static Integer loadSelect(MemorySection memory) {
		Integer select = selectInitial;
		String path = PathPlayer.SELECT.toString();
		if(memory.isList(path)) {
			select = memory.getInt(path);
		}
		return select;
	}
	private static Language loadLanguage(MemorySection memory) {
		Language language = Language.JP;
		String path = PathPlayer.LANGUAGE.toString();
		if(memory.isString(path)) {
			String string = memory.getString(path);
			language = Language.lookup(string);
		}
		return language;
	}

	public static void saveDataPlayer(DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String name = data.name;
		String pathLine = base + "." + name + "." + PathPlayer.LINE;
		config.set(pathLine, data.line);
		String pathSelect = base + "." + name + "." + PathPlayer.SELECT;
		config.set(pathSelect, data.select);
		String pathDone = base + "." + name + "." + PathPlayer.DONE;
		config.set(pathDone, data.questionDone);
		String pathLanguage = base + "." + name + "." + PathPlayer.LANGUAGE.toString();
		config.set(pathLanguage, data.language.toString());
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
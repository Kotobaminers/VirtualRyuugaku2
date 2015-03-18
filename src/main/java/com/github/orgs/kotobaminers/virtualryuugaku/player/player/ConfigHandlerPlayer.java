package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.ConfigHandler;

public class ConfigHandlerPlayer extends ConfigHandler {
	public static YamlConfiguration config;
	public static File file;
	public static final String DIRECTORY = "PLAYER";
	public static final String FILE_NAME = "PLAYER.yml";
	public static final Integer lineInitial = 0;
	public static final Integer selectInitial = -1;
	public enum PathPlayer {LINE, SELECT, LANGUAGE, DONE}
	public static List<DataPlayer> importDataPlayer() {
		List<DataPlayer> list = new ArrayList<DataPlayer>();
		for(String name : config.getKeys(false)) {
			MemorySection memory = (MemorySection) config.get(name);
			DataPlayer data = new DataPlayer();
			data.name = name;
			data.line = loadLine(memory);
			data.select = loadSelect(memory);
			data.expression = loadLanguage(memory);
			list.add(data);
		}
		return list;
	}
	private static Expression loadLanguage(MemorySection memory) {
		String language = "";
		String path = PathPlayer.LANGUAGE.toString();
		if(memory.isString(path)) {
			language = memory.getString(path);
		}
		return Expression.lookup(language);
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
		Integer select = -1;
		String path = PathPlayer.SELECT.toString();
		if(memory.isList(path)) {
			select = memory.getInt(path);
		}
		return select;
	}
	public static void saveDataPlayer(DataPlayer data) {
		String name = data.name;
		String pathLine = name + "." + PathPlayer.LINE;
		config.set(pathLine, data.line);
		String pathSelect = name + "." + PathPlayer.SELECT;
		config.set(pathSelect, data.select);
		String pathLanguage = name + "." + PathPlayer.LANGUAGE;
		config.set(pathLanguage, data.expression.toString());
		String pathDone = name + "." + PathPlayer.DONE;
		config.set(pathDone, data.done);
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
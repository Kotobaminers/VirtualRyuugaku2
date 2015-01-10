package com.gmail.fukushima.kai.talker.talker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.fukushima.kai.mytalker.mytalker.Stage;
import com.gmail.fukushima.kai.utilities.utilities.ConfigHandler;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesGeneral;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class ConfigHandlerTalker extends ConfigHandler {
	public static YamlConfiguration config;
	public static File file;
	public static final String DIRECTORY = "TALKER";
	public static final String FILE_NAME = "TALKER.yml";
	public enum PathStage {TALKER, EDITOR}
	public enum PathTalker {NAME, OWNE, ENGL, KANJ, KANA, QUES, ANSW, COMM}
	public List<Talker> importTalker() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Talker> list = new ArrayList<Talker>();
		for(String stage : config.getKeys(false)) {
			UtilitiesProgramming.printDebugMessage(stage, new Exception());
			MemorySection memory = (MemorySection) config.get(stage);
			for(String talkerPath : memory.getKeys(false)) {
				if(talkerPath.equalsIgnoreCase(PathStage.TALKER.toString())) {
					UtilitiesProgramming.printDebugMessage(talkerPath, new Exception());
					MemorySection memory2 = (MemorySection) memory.get(talkerPath);
					for(String idString : memory2.getKeys(false)) {
						UtilitiesProgramming.printDebugMessage(idString, new Exception());
						Integer id = Integer.parseInt(idString.toString());
						Talker talker = new Talker();
						talker.id = id;
						talker.nameStage = stage;
						String[] array = {stage, talkerPath, idString};
						String path = UtilitiesGeneral.joinArraysStringWithDot(array);
						talker.name = config.getString(path + "." + PathTalker.NAME.toString());
						talker.owner = config.getString(path + "." + PathTalker.OWNE.toString());
						list.add(talker);
					}
				}
			}
		}
		return list;
	}
	public static List<Stage> importStage() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Stage> list = new ArrayList<Stage>();
		for(String name : config.getKeys(false)) {
			UtilitiesProgramming.printDebugMessage(name, new Exception());
			MemorySection memory = (MemorySection) config.get(name);
			List<String> editor = new ArrayList<String>();
			List<Integer> listId = new ArrayList<Integer>();
			for(String pathStage : memory.getKeys(false)) {
				if(pathStage.equalsIgnoreCase(PathStage.EDITOR.toString())) {
					UtilitiesProgramming.printDebugMessage(pathStage, new Exception());
					String path = name + "." + PathStage.EDITOR;
					if(config.isList(path)) {
						System.out.println(editor);
						editor = config.getStringList(path);
					}
				}
				if(pathStage.equalsIgnoreCase(PathStage.TALKER.toString())) {
					MemorySection memory2 = (MemorySection) memory.get(pathStage);
					for(String idString : memory2.getKeys(false)) {
						UtilitiesProgramming.printDebugMessage(idString, new Exception());
						listId.add(Integer.parseInt(idString));
					}
				}
			}
			Stage stage = new Stage();
			stage.name = name;
			stage.editor = editor;
			stage.listId = listId;
			list.add(stage);
		}
		return list;
	}
	public static void saveTalker(Talker talker) {
		String[] arrays = {talker.nameStage, PathStage.TALKER.toString(), talker.id.toString()};
		String path = UtilitiesGeneral.joinArraysStringWithDot(arrays);
		config.set(path + "." + PathTalker.NAME, talker.name);
		config.set(path + "." + PathTalker.OWNE, talker.owner);
	}
	public static void saveStage(Stage stage) {
		//Only saving editor is enough because the others are saved in saveTalker(talker);
		String path = stage.name + "." + PathStage.EDITOR.toString();
		UtilitiesProgramming.printDebugMessage(path, new Exception());
		config.set(path, stage.editor);
	}
	@Override
	public void setFile(File file) {
		ConfigHandlerTalker.file = file;
	}
	@Override
	public void setConfig(YamlConfiguration config) {
		ConfigHandlerTalker.config = config;
	}
	@Override
	public String getFileName() {
		return FILE_NAME;
	}
	@Override
	public String getDirectory() {
		return DIRECTORY;
	}
	@Override
	public File getFile() {
		return file;
	}
	@Override
	public YamlConfiguration getConfig() {
		return config;
	}
}
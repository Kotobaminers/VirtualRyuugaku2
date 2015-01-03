package com.gmail.fukushima.kai.mystage.mystage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmail.fukushima.kai.citizens.citizens.DataManagerCitizens;
import com.gmail.fukushima.kai.common.common.Sentence;
import com.gmail.fukushima.kai.mystage.mystage.ConfigHandlerStage.Path;
import com.gmail.fukushima.kai.mystage.talker.DataManagerTalker;
import com.gmail.fukushima.kai.mystage.talker.Talker;
import com.gmail.fukushima.kai.mystage.talker.Talker.TypeTalker;
import com.gmail.fukushima.kai.utilities.utilities.DataManager;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;
import com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2.DataManagerPlugin;

public class DataManagerStage implements DataManager {
	public static Map<String, Stage> mapStage = new HashMap<String, Stage>();
	private static String baseDirectory = DataManagerPlugin.plugin.getDataFolder() + "//" + ConfigHandlerStage.DIRECTORY + "//";
	private static final String extension = ".yml";
	private static void importStage() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		mapStage = new HashMap<String, Stage>();
		for(File file : new File(baseDirectory).listFiles()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			if(file.getName().endsWith(extension)) {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				String name = file.getName().substring(0, file.getName().length() - extension.length());
				UtilitiesProgramming.printDebugMessage(name, new Exception());
				ConfigHandlerStage config = new ConfigHandlerStage(file);
				String creator = config.config.getString(Path.CREATOR.toString());
				List<Map<?, ?>> listMap = config.config.getMapList(Path.TALKER.toString());
				List<Integer> listId = new ArrayList<Integer>();
				for(Map<?, ?> mapTalker : listMap) {
					for(Object key : mapTalker.keySet()) {
						Integer id = Integer.valueOf(key.toString());
						Map<?, ?> mapSentence = (Map<?, ?>) mapTalker.get(key);
						Talker talker = createTalker(id, creator, mapSentence);
						if(0 < talker.listSentence.size()) {
							DataManagerTalker.importTalker(talker);
							listId.add(id);
						}
					}
				}
				Stage stage = new Stage(name, creator, listId);
				mapStage.put(name, stage);
			}
		}
	}
	public static Stage loadStageById(Integer id) {
		for(Stage stage : mapStage.values()) {
			for(Integer idSearch : stage.listId) {
				if(id.equals(idSearch)) {
					return stage;
				}
			}
		}
		UtilitiesProgramming.printDebugMessage("Couldn't find the stage: ID = " + id , new Exception());
		return new Stage();
	}


	public static void saveStage() {
	}
	public static void addStage(String name) {
	}
	public static void removeStage() {
	}
	public static void addTalker(Stage stage) {
	}
	public static void removeTalker(Stage stage) {
	}
	private static Talker createTalker(Integer id, String owner, Map<?, ?> map) {
		Talker talker = new Talker();
		if(!DataManagerCitizens.isValidId(id)) {
			UtilitiesProgramming.printDebugMessage("Error: Invalid ID: " + id, new Exception());
			return talker;
		}
		String name = DataManagerCitizens.loadNameById(id);
		talker.name = name;
		talker.id = id;
		talker.type = TypeTalker.STAGE;
		talker.owner = owner;
		for(Object key : map.keySet()) {
			Map<?, ?> mapSentence = (Map<?, ?>) map.get(key);
			Integer num = Integer.valueOf(key.toString());
			Sentence sentence = createSentence(mapSentence);
			if(0 < sentence.en.size() || 0 < sentence.kanji.size() || 0 < sentence.kana.size()) {
				if(num > 0) {
					talker.listSentence.add(sentence);
				} else if(num.equals(0)) {
					talker.question = sentence;
				} else if(num < 0) {
					talker.answer = sentence;
				}
			}
		}
		return talker;
	}
	public static Sentence createSentence(Map<?, ?> map) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<String> kanji = new ArrayList<String>();
		List<String> kana = new ArrayList<String>();
		List<String> en = new ArrayList<String>();
		List<String> hint = new ArrayList<String>();
		for(Object key : map.keySet()) {
			String expression = map.get(key).toString();
			String language = key.toString().toUpperCase();
			switch(language) {
			case "KANJI":
				kanji.add(expression);
				break;
			case "KANA":
				kana.add(expression);
				break;
			case "EN":
				en.add(expression);
				break;
			case "HINT":
				hint.add(expression);
				break;
			default:
				break;
			}
		}
		return new Sentence(kanji, kana, en, hint);
	}
	@Override
	public void initialize() {
		mapStage = new HashMap<String, Stage>();
	}
	@Override
	public void load() {
		initialize();importStage();
	}
	@Override
	public void saveAll() {
		// TODO Auto-generated method stub

	}
}
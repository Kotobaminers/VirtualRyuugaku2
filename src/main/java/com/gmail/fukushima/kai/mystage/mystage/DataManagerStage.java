package com.gmail.fukushima.kai.mystage.mystage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.citizensnpcs.api.npc.NPC;

import com.gmail.fukushima.kai.common.common.DataManagerCitizens;
import com.gmail.fukushima.kai.common.common.Sentence;
import com.gmail.fukushima.kai.mystage.mystage.ConfigHandlerStage.Path;
import com.gmail.fukushima.kai.mystage.talker.Talker;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;
import com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2.DataManagerPlugin;

public class DataManagerStage {
	public static List<Stage> listStage = new ArrayList<Stage>();
	private static String baseDirectory = DataManagerPlugin.plugin.getDataFolder() + "\\" + ConfigHandlerStage.DIRECTORY + "\\";
	private static final String extension = ".yml";
	public static void importStage() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(File file : new File(baseDirectory).listFiles()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			if(file.getName().endsWith(extension)) {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				String name = file.getName().substring(0, file.getName().length() - extension.length());
				UtilitiesProgramming.printDebugMessage(name, new Exception());
				ConfigHandlerStage config = new ConfigHandlerStage(file);
				String creator = config.config.getString(Path.CREATOR.toString());
				List<Map<?, ?>> listMap = config.config.getMapList(Path.TALKER.toString());
				List<Talker> listTalker = new ArrayList<Talker>();
				for(Map<?, ?> mapTalker : listMap) {
					for(Object key : mapTalker.keySet()) {
						Integer id = (Integer) key;
						Map<?, ?> mapSentence = (Map<?, ?>) mapTalker.get(key);
						Talker talker = createTalker(id, mapSentence);
						if(0 < talker.listSentence.size()) {
							listTalker.add(talker);
						}
					}
				}
				Stage stage = new Stage(name, creator, listTalker);
				listStage.add(stage);
			}
		}
	}
	public static Talker loadTalkerById(Integer id) {
		Talker talker = new Talker();
		for(Stage stage : listStage) {
			for(Talker search : stage.listTalker) {
				if(search.id.equals(id)) {
					return search;
				}
			}
		}
		UtilitiesProgramming.printDebugMessage("Couldn't find the talker: ID = " + id , new Exception());
		return talker;
	}
	public static Stage loadStageById(Integer id) {
		for(Stage stage : listStage) {
			for(Talker search : stage.listTalker) {
				if(search.id.equals(id)) {
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
	public static Talker createTalker(Integer id, Map<?, ?> map) {
		Talker talker = new Talker();
		NPC npc = DataManagerCitizens.npcs.getById(id);
		if(npc == null) {
			System.out.println("Error: Invalid ID: DataNabagerStage");
		} else {
			talker.name = npc.getFullName();
			talker.id = id;
			for(Object key : map.keySet()) {
				Map<?, ?> mapSentence = (Map<?, ?>) map.get(key);
				Integer num = Integer.valueOf(key.toString());
				UtilitiesProgramming.printDebugMessage(num.toString(), new Exception());
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
		}
		return talker;
	}
	private static Sentence createSentence(Map<?, ?> map) {
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
}
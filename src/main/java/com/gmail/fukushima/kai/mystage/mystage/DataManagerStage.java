package com.gmail.fukushima.kai.mystage.mystage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.citizensnpcs.api.npc.NPC;

import com.gmail.fukushima.kai.common.common.DataManagerCitizens;
import com.gmail.fukushima.kai.common.common.Sentence;
import com.gmail.fukushima.kai.common.common.UtilitiesProgramming;
import com.gmail.fukushima.kai.mystage.talker.Talker;
import com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2.DataManagerPlugin;

public class DataManagerStage {
	public static List<Stage> listStage = new ArrayList<Stage>();
	private static String baseDirectory = DataManagerPlugin.plugin.getDataFolder() + "\\" + "STAGE" + "\\";
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
				String creator = config.config.getString("CREATOR");
				List<Map<?, ?>> listMap = config.config.getMapList("TALKER");
				List<Talker> listTalker = new ArrayList<Talker>();
				for(Map<?, ?> map : listMap) {
					for(Object key : map.keySet()) {
						Integer id = (Integer) key;
						Map<?, ?> mapTalker = (Map<?, ?>) map.get(key);
						Talker talker = createTalker(id, mapTalker);
						if(0 < talker.listSentence.size()) {
							listTalker.add(talker);
						}
					}
					Stage stage = new Stage(name, creator, listTalker);
					UtilitiesProgramming.printDebugStage(stage);
					listStage.add(stage);
				}
			}
		}
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
				Integer number = (Integer) key;
				Sentence sentence = createSentence(mapSentence);
				if(0 < sentence.en.size() || 0 < sentence.kanji.size() || 0 < sentence.kana.size()) {
					talker.listSentence.add(sentence);
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
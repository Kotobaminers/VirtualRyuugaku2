package com.gmail.fukushima.kai.talker.talker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmail.fukushima.kai.mytalker.mytalker.Stage;
import com.gmail.fukushima.kai.utilities.utilities.DataManager;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class DataManagerTalker implements DataManager {
	//mapTalker
	public static Map<Integer, Talker> mapTalker = new HashMap<Integer, Talker>();
	public static Talker getTalker(Integer id) {
		Talker talker = new Talker();
		if(mapTalker.containsKey(id)) {
			talker = mapTalker.get(id);
		}
		return talker;
	}
	private static void putTalker(Talker talker) {
		mapTalker.put(talker.id, talker);
	}
	@Override
	public void load() {
		initialize();
		loadMapTalker();
		loadIndexStage();
	}
	@Override
	public void initialize() {
		indexStage = new HashMap<String, Stage>();
	}
	private static void loadMapTalker() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Talker> list = new ConfigHandlerTalker().importTalker();
		for(Talker talker : list) {
			putTalker(talker);
		}
	}
	@Override
	public void saveAll() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(Stage stage : indexStage.values()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			ConfigHandlerTalker.saveStage(stage);
		}
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(Talker talker : mapTalker.values()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			ConfigHandlerTalker.saveTalker(talker);
		}
		new ConfigHandlerTalker().save();
	}
	public static void addTalker(Talker talker) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(!talker.isValid()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			return;
		}
		UtilitiesProgramming.printDebugMessage("", new Exception());
		putTalker(talker);
		Stage stage = indexStage.get(talker.nameStage);
		stage.addTalker(talker);
		System.out.println("ID tuikasareteiruto shitano putStage is not needed: " + indexStage.get(talker.nameStage).listId);
		putStage(stage);
	}
	//indexStage
	public static Map<String, Stage> indexStage = new HashMap<String, Stage>();
	private static void loadIndexStage() {
		List<Stage> list = ConfigHandlerTalker.importStage();
		for(Stage stage : list) {
			putStage(stage);
		}
	}
	public static Stage getStage(String name) {
		Stage data = new Stage();
		if(indexStage.containsKey(name)) {
			data = indexStage.get(name);
		} else {
			UtilitiesProgramming.printDebugMessage("Couldn't find any valid DataShadowTopic.", new Exception());
		}
		return data;
	}
	private static void putStage(Stage data) {
		indexStage.put(data.name, data);
	}
}
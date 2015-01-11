package com.gmail.fukushima.kai.talker.talker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.fukushima.kai.common.common.LibraryManager;
import com.gmail.fukushima.kai.utilities.utilities.DataManager;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class DataManagerTalker implements DataManager {
	//mapTalker
	private static Map<Integer, Talker> mapTalker = new HashMap<Integer, Talker>();
	public static Talker getTalker(Integer id) {
		Talker talker = new Talker();
		if(getMapTalker().containsKey(id)) {
			talker = getMapTalker().get(id);
		}
		return talker;
	}
	private static void putTalker(Talker talker) {
		getMapTalker().put(talker.id, talker);
	}
	@Override
	public void loadAll() {
		initialize();
		loadMapTalker();
//		loadIndexStage();
	}
	@Override
	public void initialize() {
		mapTalker = new HashMap<Integer, Talker>();
//		indexStage = new HashMap<String, Stage>();
	}
	private static void loadMapTalker() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Talker> list = ConfigHandlerTalker.importTalkerDefault();
		for(Talker talker : list) {
			putTalker(talker);
		}
		List<YamlConfiguration> listConfig = LibraryManager.getListLibraryStage();
		for(YamlConfiguration config : listConfig) {
			List<Talker> override = new ArrayList<Talker>();
			override = LibraryHandlerTalker.importTalkerLibrary(config);
			for(Talker talker : override) {
				UtilitiesProgramming.printDebugTalker(talker);
				putTalker(talker);
			}
		}
	}
	@Override
	public void saveAll() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
//		for(Stage stage : getIndexStage().values()) {
//			UtilitiesProgramming.printDebugMessage("", new Exception());
//			ConfigHandlerTalker.saveStage(stage);
//		}
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(Talker talker : getMapTalker().values()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			ConfigHandlerTalker.saveTalker(talker);
		}
		new ConfigHandlerTalker().save();
	}
	public static void registerTalker(Talker talker) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(!talker.isValid()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			return;
		}
		putTalker(talker);
//		Stage stage = getIndexStage().get(talker.nameStage);
//		stage.addTalker(talker);//Since indexStage is static, putStage(stage) is not needed.
	}
	//indexStage
//	private static Map<String, Stage> indexStage = new HashMap<String, Stage>();
//	private static void loadIndexStage() {
//		Map<String, Stage> map = ConfigHandlerTalker.importStage();
//		for(Talker talker : getMapTalker().values()) {
//			map.get(talker.nameStage).listId.add(talker.id);
//		}
//	}
//	public static Stage getStage(String name) {
//		Stage data = new Stage();
//		if(getIndexStage().containsKey(name)) {
//			data = getIndexStage().get(name);
//		} else {
//			UtilitiesProgramming.printDebugMessage("Couldn't find any valid DataShadowTopic.", new Exception());
//		}
//		return data;
//	}
//	private static void putStage(Stage data) {
//		getIndexStage().put(data.name, data);
//	}

	public static Map<Integer, Talker> getMapTalker() {
		return mapTalker;
	}
}
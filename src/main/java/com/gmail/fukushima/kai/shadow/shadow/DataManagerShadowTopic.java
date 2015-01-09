package com.gmail.fukushima.kai.shadow.shadow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.citizens.citizens.DataManagerCitizens;
import com.gmail.fukushima.kai.common.common.Sentence;
import com.gmail.fukushima.kai.shadow.shadow.ConfigHandlerShadowTopic.Path;
import com.gmail.fukushima.kai.shadow.shadow.ConfigHandlerShadowTopic.DataShadowData;
import com.gmail.fukushima.kai.stage.stage.DataManagerStage;
import com.gmail.fukushima.kai.talker.talker.DataManagerTalker;
import com.gmail.fukushima.kai.talker.talker.Talker;
import com.gmail.fukushima.kai.talker.talker.Talker.TypeTalker;
import com.gmail.fukushima.kai.utilities.utilities.DataManager;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class DataManagerShadowTopic implements DataManager {
	public static Map<String, DataShadowTopic> mapDataShadowTopic = new HashMap<String, DataShadowTopic>();
	public static DataShadowTopic getDataShadowTopic(String nameTopic) {
		DataShadowTopic data = new DataShadowTopic();
		if(mapDataShadowTopic.containsKey(nameTopic)) {
			data = mapDataShadowTopic.get(nameTopic);
		} else {
			UtilitiesProgramming.printDebugMessage("Couldn't find any valid DataShadowTopic.", new Exception());
		}
		return data;
	}
	public static DataShadowTopic getDataShadowTopic(Integer id) {
		DataShadowTopic data = new DataShadowTopic();
		for(DataShadowTopic search : mapDataShadowTopic.values()) {
			if(search.listId.equals(id)) {
				return search;
			}
		}
		return data;
	}
	private static void importDataShadowTopic() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Map<String, DataShadowTopic> map = new HashMap<String, DataShadowTopic>();
		for(String key : ConfigHandlerShadowTopic.config.getKeys(false)) {
			UtilitiesProgramming.printDebugMessage(key, new Exception());
			DataShadowTopic data = ConfigHandlerShadowTopic.loadDataShadowTopic(key);
			UtilitiesProgramming.printDebugShadowTopic(data);
			map.put(key, data);
			List<DataShadowData> list = new ConfigHandlerShadowTopic().loadListPrivateDataShadow(key);
			for(DataShadowData privateData : list) {
				String path = key + "." + Path.TALKER.toString() + "." + privateData.creator;
				UtilitiesProgramming.printDebugMessage(path + privateData.creator + privateData.id, new Exception());
				Talker talker = Talker.importTalker(new ConfigHandlerShadowTopic(), path, privateData.id, TypeTalker.SHADOW);
				DataManagerTalker.putTalker(talker);
			}
		}
		mapDataShadowTopic = map;
	}
	public static void putDataShadowTopic(DataShadowTopic data) {
		mapDataShadowTopic.put(data.nameTopic, data);
	}
	public static void saveDataShadowTopic(DataShadowTopic data) {
		putDataShadowTopic(data);
		ConfigHandlerShadowTopic.saveDataShadowTopic(data);
	}
	private static void saveMapDataShadowTopic() {
		for(DataShadowTopic data : mapDataShadowTopic.values()) {
			ConfigHandlerShadowTopic.saveDataShadowTopic(data);
		}
		new ConfigHandlerShadowTopic().save();
	}
	public static void addCreated(Player player, Integer id, String nameTopic) {
		DataShadowTopic data = getDataShadowTopic(nameTopic);
		if(!data.isValid()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			return;
		}
		List<String> created = new ArrayList<String>();
		created.addAll(data.created);
		if(!created.contains(player.getName())) {
			created.add(player.getName());
			data.created = created;
			putDataShadowTopic(data);
			UtilitiesProgramming.printDebugShadowTopic(data);
		} else {
			UtilitiesProgramming.printDebugMessage("This Talker has already been added.", new Exception());
		}
	}
	@Override
	public void initialize() {
		mapDataShadowTopic = new HashMap<String, DataShadowTopic>();
	}
	@Override
	public void load() {
		initialize();
		importDataShadowTopic();
	}
	@Override
	public void saveAll() {
		saveMapDataShadowTopic();
	}
	public static Boolean exists(String nameTopic) {
		for(String name : mapDataShadowTopic.keySet()) {
			if(nameTopic.equalsIgnoreCase(name)) return true;
		}
		return false;
	}
	public static Talker createTalker(Integer id, String owner, Map<?, ?> map) {
		Talker talker = new Talker();
		if(!DataManagerCitizens.isValidId(id)) {
			UtilitiesProgramming.printDebugMessage("Error: Invalid ID: " + id, new Exception());
			return talker;
		}
		String name = DataManagerCitizens.loadNameById(id);
		talker.name = name;
		talker.id = id;
		talker.type = TypeTalker.SHADOW;
		talker.owner = owner;
		for(Object key : map.keySet()) {
			Map<?, ?> mapSentence = (Map<?, ?>) map.get(key);
			Integer num = Integer.valueOf(key.toString());
			Sentence sentence = DataManagerStage.createSentence(mapSentence);
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
}
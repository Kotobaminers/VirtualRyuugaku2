package com.gmail.fukushima.kai.shadow.shadow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.citizens.citizens.DataManagerCitizens;
import com.gmail.fukushima.kai.common.common.Sentence;
import com.gmail.fukushima.kai.mystage.mystage.DataManagerStage;
import com.gmail.fukushima.kai.mystage.talker.Talker;
import com.gmail.fukushima.kai.mystage.talker.Talker.TypeTalker;
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
		for(String key : ConfigHandlerShadow.config.getKeys(false)) {
			DataShadowTopic data = ConfigHandlerShadow.loadDataShadow(key);
			map.put(key, data);
		}
		mapDataShadowTopic = map;
	}
	private static void saveMapDataShadowTopic() {
		for(DataShadowTopic data : mapDataShadowTopic.values()) {
			ConfigHandlerShadow.saveDataShadowTopic(data);
		}
		ConfigHandlerShadow.save();
	}
	public static void saveDataShadowTopic(DataShadowTopic data) {
		putDataShadowTopic(data);
		ConfigHandlerShadow.saveDataShadowTopic(data);
	}
	public static void putDataShadowTopic(DataShadowTopic data) {
		mapDataShadowTopic.put(data.nameTopic, data);
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
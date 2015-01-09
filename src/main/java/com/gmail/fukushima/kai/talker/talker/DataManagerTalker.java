package com.gmail.fukushima.kai.talker.talker;

import java.util.HashMap;
import java.util.Map;

import com.gmail.fukushima.kai.utilities.utilities.DataManager;

public class DataManagerTalker implements DataManager {
	public static Map<Integer, Talker> mapTalker = new HashMap<Integer, Talker>();
	public static Talker getTalker(Integer id) {
		Talker talker = new Talker();
		if(mapTalker.containsKey(id)) {
			talker = mapTalker.get(id);
		}
		return talker;
	}
	public static void putTalker(Talker talker) {
		mapTalker.put(talker.id, talker);
	}
	@Override
	public void initialize() {
		mapTalker = new HashMap<Integer, Talker>();
	}
	@Override
	public void load() {//mapTalker will be imported from other DataManager: Stage, Shadow
	}
	@Override
	public void saveAll() {//No Save
	}
}
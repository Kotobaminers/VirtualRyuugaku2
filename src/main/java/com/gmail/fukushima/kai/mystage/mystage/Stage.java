package com.gmail.fukushima.kai.mystage.mystage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmail.fukushima.kai.mystage.talker.Talker;

public class Stage {
	public String name;
	public String creator;
	public List<Talker> listTalker;
	public Stage(String name, String creator, List<Talker> listTalker) {
		this.name = name;
		this.creator = creator;
		this.listTalker = listTalker;
	}
	public void toMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(name, "");
		for(Talker talker : listTalker) {
			talker.toMap();
		}
	}
}
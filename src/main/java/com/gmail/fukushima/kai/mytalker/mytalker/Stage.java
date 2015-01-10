package com.gmail.fukushima.kai.mytalker.mytalker;

import java.util.ArrayList;
import java.util.List;

import com.gmail.fukushima.kai.talker.talker.Talker;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class Stage {
	public String name = "";
	public List<String> editor = new ArrayList<String>();
	public List<Integer> listId = new ArrayList<Integer>();
	public enum Language {EN, JP;
		public static Language lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return Language.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return Language.JP;
			}
		}
	}
	public Boolean isValid() {
		if(0 < name.length()) {
			return true;
		}
		UtilitiesProgramming.printDebugMessage("Invalid DataShadowTopic", new Exception());
		return false;
	}
	public void addTalker(Talker talker) {
		if(!listId.contains(talker.id)) {
			listId.add(talker.id);
		}
	}
}
package com.gmail.fukushima.kai.shadow.shadow;

import java.util.ArrayList;
import java.util.List;

import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class DataShadowTopic {
	public String nameTopic = "";
	public List<String> created = new ArrayList<String>();
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
		if(0 < nameTopic.length()) {
			return true;
		}
		UtilitiesProgramming.printDebugMessage("Invalid DataShadowTopic", new Exception());
		return false;
	}
}
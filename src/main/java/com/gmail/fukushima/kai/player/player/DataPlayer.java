package com.gmail.fukushima.kai.player.player;

import java.util.ArrayList;
import java.util.List;

import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class DataPlayer {
	public String name = "";
	public Integer line = 0;
	public Integer select = -1;
	public Language language = Language.JP;
	public List<Integer> done = new ArrayList<Integer>();
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
		if(0 < name.length()) return true;
		return false;
	}
}
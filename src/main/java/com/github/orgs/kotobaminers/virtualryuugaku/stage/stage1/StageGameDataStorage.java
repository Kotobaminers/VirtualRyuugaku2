package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage1;

import java.util.HashMap;
import java.util.Map;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;


public abstract class StageGameDataStorage {
	public static Map<String, PracticeStageHandler> mapPractice = new HashMap<String, PracticeStageHandler>();
	public static PracticeStageHandler getPractice(String key) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		PracticeStageHandler practice = new PracticeStageHandler();
		if(mapPractice.containsKey(key)) {
			practice = mapPractice.get(key);
		} else {
			UtilitiesProgramming.printDebugMessage("Invalid Name: " + key, new Exception());
		}
		return practice;
	}
	public static boolean existsPractice(String name) {
		if(mapPractice.containsKey(name)) {
			return true;
		}
		return false;
	}

}
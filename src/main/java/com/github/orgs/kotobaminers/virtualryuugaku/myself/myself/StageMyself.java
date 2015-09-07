package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import java.util.Collections;
import java.util.List;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.vrgnpc.vrgnpc.NPCHandler;

public class StageMyself {
	public String name = "";

	private StageMyself() {}

	public static StageMyself createStageMyself(String name) throws Exception{
		StageMyself stage = new StageMyself();
		if(ControllerMyself.existsStage(name)) {
			stage.name = name;
		} else {
			throw new Exception("Invalid Stage Name: " + name);
		}
		return stage;
	}

	public List<Integer> getListID() {
		List<Integer> list = ControllerMyself.getIDFromStage(name);
		return list;
	}

	public void changeNPCRandom() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Integer> ids = getListID();

		List<String> players = ControllerMyself.getPlayerNamesAll(name);
		Integer max = ids.size();
		if(players.size() < max) {
			max = players.size();
		}
		Collections.shuffle(ids);
		Collections.shuffle(players);
		for (int i = 0; i < max; i++) {
			try {
				NPCHandler.changeNPCAsPlayer(ids.get(i), name, players.get(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (int i = max; i < ids.size(); i++) {
			try {
				NPCHandler.changeNPCAsEmpty(ids.get(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
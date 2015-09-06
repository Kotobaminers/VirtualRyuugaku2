package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import java.util.Collections;
import java.util.List;

import org.bukkit.entity.EntityType;

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
			NPCHandler.changeType(ids.get(i), EntityType.PLAYER);
			NPCHandler.changeName(ids.get(i), players.get(i));
			ControllerMyself.setTalkParams(players.get(i), name, ids.get(i));
		}
		for (int i = max; i < ids.size(); i++) {
			NPCHandler.changeName(ids.get(i), "EMPTY");
			NPCHandler.changeType(ids.get(i), EntityType.CREEPER);
		}
	}
}

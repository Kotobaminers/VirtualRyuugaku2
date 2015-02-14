package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class GlobalStageRunnable extends BukkitRunnable {
	public String stage = "";
	public List<Talk> listTalk = new ArrayList<Talk>();
	public Integer count = -1;
	private static Map<String, GlobalStageDataPlayer> mapDataPlayer = new HashMap<String, GlobalStageDataPlayer>();

	public boolean isValid() {
		if(0 < listTalk.size()) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		if(count < listTalk.size()) {
			count++;
			GlobalStageGameHandler.questNext();
		} else {
			this.cancel();
			GlobalStageGameHandler.printEnd();
			GlobalStageGameHandler.initializeData();
			GlobalStageGameHandler.initializeGame();
		}
	}

	public GlobalStageDataPlayer getData(String name) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		GlobalStageDataPlayer data = new GlobalStageDataPlayer();
		if(getMapDataPlayer().containsKey(name)) {
			data = getMapDataPlayer().get(name);
		} else {
			data.name = name;
			setData(data);
		}
		return data;
	}
	private void setData(GlobalStageDataPlayer data) {
		mapDataPlayer.put(data.name, data);
	}

	public Map<String, GlobalStageDataPlayer> getMapDataPlayer() {
		return mapDataPlayer;
	}
	public void setMapDataPlayer(Map<String, GlobalStageDataPlayer> mapDataPlayer) {
		GlobalStageRunnable.mapDataPlayer = mapDataPlayer;
	}

}
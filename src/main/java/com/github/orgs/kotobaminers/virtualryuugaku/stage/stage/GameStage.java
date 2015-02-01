package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class GameStage extends BukkitRunnable {
	public String stage = "";
	public List<Talk> listTalk = new ArrayList<Talk>();
	public Integer count = -1;
	private static Map<String, DataGameStagePlayer> mapDataPlayer = new HashMap<String, DataGameStagePlayer>();

	public boolean isValid() {
		if(0 < listTalk.size()) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		if(count < listTalk.size()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			count++;
			StageGameHandler.questNext();
		} else {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			this.cancel();
			StageGameHandler.printEnd();
			StageGameHandler.initializeGame();
		}
	}

	public DataGameStagePlayer getData(String name) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataGameStagePlayer data = new DataGameStagePlayer();
		if(getMapDataPlayer().containsKey(name)) {
			data = getMapDataPlayer().get(name);
			UtilitiesProgramming.printDebugMessage("" + data.score, new Exception());
		} else {
			UtilitiesProgramming.printDebugMessage("New Data" + data.score, new Exception());
			data.name = name;
			setData(data);
		}
		return data;
	}
	private void setData(DataGameStagePlayer data) {
		mapDataPlayer.put(data.name, data);
	}

	public Map<String, DataGameStagePlayer> getMapDataPlayer() {
		return mapDataPlayer;
	}
	public void setMapDataPlayer(Map<String, DataGameStagePlayer> mapDataPlayer) {
		GameStage.mapDataPlayer = mapDataPlayer;
	}

}
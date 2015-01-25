package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Broadcast;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.Talker;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class GameStage extends BukkitRunnable {
	public String stage;
	public DataGameStage data;
	public List<Talker> listTalker;
	public Integer count = 0;

	public boolean isValid() {
		if(0 < listTalker.size()) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		if(count < listTalker.size()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			DataManagerStage.questNext();
			count++;
		} else {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			this.cancel();
			printEnd();
			DataManagerStage.initializeGame();
		}
	}

	private void printEnd() {
		String[] opts = {stage.toUpperCase()};
		MessengerGeneral.broadcast(Broadcast.GAME_STAGE_END_1, opts);
	}

}
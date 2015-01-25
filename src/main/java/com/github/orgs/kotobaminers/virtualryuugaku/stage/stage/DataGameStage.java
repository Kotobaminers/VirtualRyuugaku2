package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.Map;

import org.bukkit.entity.Player;

public class DataGameStage {
	Map<Player, DataGameStagePlayer> mapDataPlayer;

	public class DataGameStagePlayer {
		Player player;
		Integer correct;
		Integer score;
		Boolean canAnswer;
	}
}

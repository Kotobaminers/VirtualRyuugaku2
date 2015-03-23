package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class GameFindPeopleHandler {
	private static Map<String, GameFindPeople> mapGame = new HashMap<String, GameFindPeople>();
	public static Map<String, GameFindPeople> getMapGame() {
		return mapGame;
	}

	public static void loadNewGame(Player player, String name, String stage) {
		GameFindPeople game = GameFindPeople.createGame(player, stage);
		mapGame.put(name, game);
		game.tryNext();
	}
	public static void initializeGame(Player player) {
		if (mapGame.containsKey(player.getName())) {
			mapGame.remove(player.getName());
		} else {
			UtilitiesProgramming.printDebugMessage("No GameFindPeople", new Exception());
		}
	}

	public static GameFindPeople getGame(String name) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		GameFindPeople game = new GameFindPeople();
		if(mapGame.containsKey(name)) {
			game = mapGame.get(name);
		} else {
			UtilitiesProgramming.printDebugMessage("No GameFindPeople", new Exception());
		}
		return game;
	}
	public static boolean hasGame(Player player) {
		if (getMapGame().containsKey(player.getName())) {
			return true;
		}
		return false;
	}
}
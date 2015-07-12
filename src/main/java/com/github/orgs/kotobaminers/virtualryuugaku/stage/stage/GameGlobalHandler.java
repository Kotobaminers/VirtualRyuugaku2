package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class GameGlobalHandler {
	public static GameGlobal game;

	public static void loadFindPeople(String name) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		game = new GameFindPeopleGlobal();
		game.loadGame(name);
	}

	public static void giveNextQuestion(Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if (game.hasNextKeyTalk()) {
			game.giveNextQuestion();
		} else {
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.GAME_TRY_FINISH_0, null));
		}
	}

	public static void finishGame() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		game.finishGame();
		UtilitiesProgramming.printDebugMessage("", new Exception());
	}
}
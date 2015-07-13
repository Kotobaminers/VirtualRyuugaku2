package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class GameGlobalHandler {
	public static GameGlobal game;

	public static void initializeGameGlobal() {
		game = new GameTrainingGlobal();
	}

	public static void loadTraining(String name) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		game = new GameTrainingGlobal();
		game.loadGame(name);
	}
	public static void loadFindPeople(String name) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		game = new GameFindPeopleGlobal();
		game.loadGame(name);
	}

	public static void giveNextQuestion(Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if (game.hasNextKeyTalk()) {
			game.refreshCantAnswer();
			player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
			game.giveNextQuestion();
		} else {
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.GAME_TRY_FINISH_0, null));
		}
	}

	public static void finishGame() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		game.finishGame();
	}

	public static void validataAnswer(Player player, String answer) {
		if(GameGlobal.cantAnswer.contains(player.getName())) {
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.GAME_NOT_ALLOWED_TO_ANSWER_0, null));
			return;
		}

		if (game.validateAnswer(player, answer)) {
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.CORRECT_0, null) );
		} else {
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
			game.printWrong(player);
		}
	}

	public static void updataScoreboard(Player player) {
		game.updateScoreboard(player);
	};
}
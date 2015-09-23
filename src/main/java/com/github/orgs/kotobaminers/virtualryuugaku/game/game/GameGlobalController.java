package com.github.orgs.kotobaminers.virtualryuugaku.game.game;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Controller;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.GameGlobalScoreboard;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class GameGlobalController extends Controller {

	public static GameGlobal game = new GameGlobal();

	public static final Integer WRITE_CORRECT = 3;
	public static final Integer WRITE_WRONG = 0;
	public static final Integer FIND_CORRECT = 2;
	public static final Integer FIND_WRONG = -1;
	public static final Integer LOOK_TALK = -1;

	@Override
	public void setStorage() {
		game = new GameGlobal();
	}

	@Override
	public Storage getStorage() {
		return game;
	}

	public static void loadGame(String stage) {
		game.load(stage);
	}

	public static void giveNextQuestion(Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if (game.hasNextTalk()) {
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
		if(game.cantAnswer.contains(player.getName())) {
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.GAME_NOT_ALLOWED_TO_ANSWER_0, null));
			return;
		}

		if (game.validateAnswer(player, answer)) {
			eventWriteCorrect(player);
		} else {
			eventWriteWrong(player);
		}
	}

	private static void eventWriteWrong(Player player) {
		player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.WRONG_0, null) );
		game.addScore(player, WRITE_WRONG);
	}


	private static void eventWriteCorrect(Player player) {
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
		String[] opts = {UtilitiesGeneral.joinStrings(game.getCurrentAnswers(), ", ")};
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.STAGE_CORRECT_1, opts));
		game.addScore(player, WRITE_CORRECT);
		game.cantAnswer.add(player.getName());
	}

	public static boolean isValidGame() {
		if (0 < game.talks.size()) {
			return true;
		}
		return false;
	}

	public static void updataScoreboard(Player player) {
		new GameGlobalScoreboard().update(player, game.scores);
	}

	public static void checkFindPeople(NPC npc) {

	}
}


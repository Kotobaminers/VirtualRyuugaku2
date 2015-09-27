package com.github.orgs.kotobaminers.virtualryuugaku.game.game;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Controller;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message0;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.game.game.GameGlobal.EventScore;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class ControllerGameGlobal extends Controller {

	public static GameGlobal game = new GameGlobal();

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
		String[] opts = {stage.toLowerCase()};
		MessengerGeneral.broadcast(MessengerGeneral.getMessage(Message0.GAME_JOIN_TP_1, opts));
	}

	public static void giveNextQuestion(Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if (game.hasNextTalk()) {
			game.giveNextQuestion();
		} else {
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.GAME_TRY_FINISH_0, null));
		}
	}

	public static void finishGame() {
		game.finishGame();
	}

	public static void validataAnswer(Player player, String answer) {
		if(game.cantAnswer.contains(player.getName())) {
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.GAME_NOT_ALLOWED_TO_ANSWER_0, null));
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
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.WRONG_0, null) );
		game.addScore(player, EventScore.ANSWER_WRONGLY);
	}


	private static void eventWriteCorrect(Player player) {
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
		String[] opts = {UtilitiesGeneral.joinStrings(game.getCurrentAnswers(), ", ")};
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.STAGE_CORRECT_1, opts));
		game.addScore(player, EventScore.ANSWER_CORRECTLY);
		game.cantAnswer.add(player.getName());
	}

	private static void eventFindWrong(Player player) {
		player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.WRONG_0, null) );
		game.addScore(player, EventScore.LEFT_CLICK_NPC_WRONGLY);
	}


	private static void eventFindCorrect(Player player) {
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.CORRECT_0, null));
		game.addScore(player, EventScore.LEFT_CLICK_NPC_CORRECTLY);
		game.cantFind.add(player.getName());
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

	public static void checkFindPeople(Player player, NPC npc) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if (!game.cantFind.contains(player.getName())) {
			try {
				Conversation conversation = ControllerConversation.getConversation(npc);
				if(conversation.listTalk.contains(game.getCurrentKeyTalk())) {
					eventFindCorrect(player);
				} else {
					eventFindWrong(player);
				}
				updataScoreboard(player);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void cheat(Player player) {
		game.addScore(player, EventScore.CHEAT_A_CONVERSATION);
		updataScoreboard(player);
		String[] opts = {"" + EventScore.CHEAT_A_CONVERSATION.getScore()};
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.GAME_CHEAT_1, opts));
	}

	public static void printRule(Player player) {
		Player[] players = {player};
		String[] opts = {""};
		Message.GAME_RULE_TITLE_0.print(players, opts);
		for (EventScore event : EventScore.values()) {
			Message.GAME_RULE_2.print(players, event.getMessageOpts());
		}
	}
}

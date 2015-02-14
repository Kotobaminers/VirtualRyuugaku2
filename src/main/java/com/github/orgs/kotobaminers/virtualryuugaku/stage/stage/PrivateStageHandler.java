package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class PrivateStageHandler {
	private static Map<String, LocalStage> mapLocalStage = new HashMap<String, LocalStage>();

	public static void runNext(String name) {
		LocalStage game = getGame(name);
		if(game.count < game.listTalk.size()) {
			game.questNext();
		} else {
			game.printEnd();
			initializeGame(name);
		}
	}

	public static boolean isValidGame(String name) {
		if(0 < getGame(name).listTalk.size()) {
			return true;
		}
		return false;
	}

	private static LocalStage getGame(String name) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(getMapLocalStage().containsKey(name)) {
			UtilitiesProgramming.printDebugMessage("valid local stage: " + name, new Exception());
			return getMapLocalStage().get(name);
		}
		UtilitiesProgramming.printDebugMessage("invalid local stage: " + name, new Exception());
		return new LocalStage();
	}

	public static void initializeGame(String name) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		getMapLocalStage().put(name, new LocalStage());
	}

	public static Map<String, LocalStage> getMapLocalStage() {
		return mapLocalStage;
	}

	public static void validateAnswer(Player player, String answer) {
		String name = player.getName();
		LocalStage game = getGame(name);
		List<String> uppers = new ArrayList<String>();
		for(String upper : game.answers) {
			uppers.add(upper.toUpperCase());
		}
		if(uppers.contains(answer.toUpperCase())) {
			game.correct(player);
			String total = game.getScoreTotal().toString();
			String[] opts = {UtilitiesGeneral.joinStrings(game.answers, ", "), total};
			MessengerGeneral.print(player, Message.GAME_STAGE_CORRECT_2, opts);
		} else {
			String[] opts = {answer};
			MessengerGeneral.print(player, Message.GAME_STAGE_WRONG_1, opts);
		}
		runNext(name);
	}
}
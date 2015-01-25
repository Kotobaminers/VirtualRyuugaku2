package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Broadcast;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.DataManagerTalker;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.Talker;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class GameStage {
	public static String stage;
	public static DataGameStage data;
	public static List<Talker> listTalker;
	public static Integer count = 0;
	public static final Integer ready = 20 * 5;
	public static final Integer interval = 20 * 5;
	public static Boolean active = false;

	public static void initialize() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		stage = "";
		data = new DataGameStage();
		listTalker = new ArrayList<Talker>();
		count = 0;
		active = false;
	}

	public static void loadNewGame(String stage) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		GameStage.initialize();;
		GameStage.stage = stage;
		for(Talker talker : DataManagerTalker.getMapTalker().values()) {
			if(talker.stage.equalsIgnoreCase(stage)) {
				GameStage.listTalker.add(talker);
			}
		}
		Collections.shuffle(GameStage.listTalker);
	}

	public static void questNext() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(count.equals(0)) {
			printStart();
		}
		if(listTalker.size() <= count) {
			UtilitiesProgramming.printDebugMessage("Error: listTalker.size() <= count", new Exception());
			return;
		}
		Talker talker = listTalker.get(count);
		Description description = talker.getKeyDescription();
		Random random = new Random();
		String message = "";
		String languageAnswer= "";
		int number = random.nextInt(2);//Random Number 0 or 1
		switch(number) {
		case 0:
			message = description.express(Expression.EN);
			languageAnswer = "Japanese";
			break;
		case 1:
			message = description.express(Expression.KANA);
			languageAnswer = "English";
			break;
		default: break;
		}
		Integer question = count + 1;
		String[] opts = {question.toString(), message, languageAnswer};
		MessengerGeneral.broadcast(Broadcast.GAME_STAGE_QUEST_3, opts);
		count++;
		if(listTalker.size() <= count) {
			initialize();
			printEnd();
		}
	}
	private static void printStart() {
		String[] opts = {stage.toUpperCase()};
		MessengerGeneral.broadcast(Broadcast.GAME_STAGE_START_1, opts);
	}
	private static void printEnd() {
		String[] opts = {stage.toUpperCase()
				};
		MessengerGeneral.broadcast(Broadcast.GAME_STAGE_END_1, opts);
	}

	public static boolean isValid() {
		if(0 < listTalker.size()) {
			return true;
		}
		return false;
	}
}
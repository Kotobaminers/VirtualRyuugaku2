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
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.DataManager;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class DataManagerStage implements DataManager {
	private static GameStage game = new GameStage();
	public static Boolean running = false;
	public static String question = "";
	public static List<String> answers = new ArrayList<String>();
	public static Integer ready;
	public static Integer interval;

	public void cancel() {
		getGame().cancel();
	}
	public void run() {
		getGame().run();
	}

	public static void initializeGame() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataManagerStage.setGame(new GameStage());
		getGame().stage = "";
		getGame().data = new DataGameStage();
		getGame().listTalker = new ArrayList<Talker>();
		getGame().count = 0;
		DataManagerStage.running = false;
	}

	public static void loadNewGame(String stage) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		initializeGame();;
		getGame().stage = stage;
		for(Talker talker : DataManagerTalker.getMapTalker().values()) {
			if(talker.stage.equalsIgnoreCase(stage)) {
				getGame().listTalker.add(talker);
			}
		}
		Collections.shuffle(getGame().listTalker);
	}

	public static void questNext() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(getGame().listTalker.size() <= getGame().count) {
			UtilitiesProgramming.printDebugMessage("Error: listTalker.size() <= count", new Exception());
			return;
		}
		Talker talker = getGame().listTalker.get(getGame().count);
		Description description = talker.getKeyDescription();
		answers = new ArrayList<String>();
		Random random = new Random();
		String languageAnswer= "";
		int language = random.nextInt(2);//Random Number 0 or 1
		switch(language) {
		case 0:
			question = description.express(Expression.EN);
			answers.addAll(description.kanji);
			answers.addAll(description.kana);
			answers.addAll(description.romaji);
			languageAnswer = "Japanese";
			break;
		case 1:
			question = description.express(Expression.KANA);
			answers.addAll(description.en);
			languageAnswer = "English";
			break;
		default: break;
		}
		Integer number = getGame().count + 1;
		String[] opts = {number.toString(), question, languageAnswer};
		MessengerGeneral.broadcast(Broadcast.GAME_STAGE_QUEST_3, opts);
	}

	public static GameStage getGame() {
		return game;
	}
	public static void setGame(GameStage game) {
		DataManagerStage.game = game;
	}
	@Override
	public void initialize() {}//Not needed methods
	@Override
	public void saveAll() {}//Not needed methods
	@Override
	public void loadAll() {
		ready = ConfigHandlerStage.importReady() * 20;//To second
		interval = ConfigHandlerStage.importInterval() * 20;//To second
	}
}

package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.DataManager;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class GlobalStageGameHandler implements DataManager {
	private static GlobalStageRunnable game = new GlobalStageRunnable();
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
		GlobalStageGameHandler.setGame(new GlobalStageRunnable());
		GlobalStageGameHandler.running = false;
	}

	public static void loadNewGame(String stage) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		initializeGame();
		getGame().stage = stage;
		for(Conversation conversation : DataManagerConversation.getMapConversation().values()) {
			UtilitiesProgramming.printDebugMessage(stage + conversation.stage, new Exception());
			if(conversation.stage.equalsIgnoreCase(stage)) {
				for(Integer key : conversation.getKey()) {
					if(key < conversation.listTalk.size()) {
						UtilitiesProgramming.printDebugMessage("" + key + " " + conversation.listTalk.size(), new Exception());
						getGame().listTalk.add(conversation.listTalk.get(key));
					}
				}
			}
		}
		Collections.shuffle(getGame().listTalk);
	}

	public static void questNext() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
//		if(getGame().listTalk.size() <= getGame().count) {
//			UtilitiesProgramming.printDebugMessage("Error: listTalker.size() <= count", new Exception());
//			return;
//		}
//		Description description = getGame().listTalk.get(getGame().count).description;
//		answers = new ArrayList<String>();
//		Random random = new Random();
//		String languageAnswer= "";
//		int language = random.nextInt(2);//Random Number 0 or 1
//		switch(language) {
//		case 0:
//			question = description.express(Expression.EN);
//			answers.addAll(description.kanji);
//			answers.addAll(description.kana);
//			answers.addAll(description.romaji);
//			languageAnswer = "Japanese";
//			break;
//		case 1:
//			question = description.express(Expression.KANA);
//			answers.addAll(description.en);
//			languageAnswer = "English";
//			break;
//		default: break;
//		}
//		Integer number = getGame().count + 1;
//		String[] opts = {number.toString(), question, languageAnswer};
//		MessengerGeneral.broadcast(Broadcast.GAME_STAGE_QUEST_4, opts);
	}

	public static void printEnd() {
//		String[] opts = {getGame().stage.toUpperCase()};
//		MessengerGeneral.broadcast(Broadcast.GAME_STAGE_END_1, opts);
//		for (GlobalStageDataPlayer dataPlayer : getGame().getMapDataPlayer().values()) {
//			Integer total = dataPlayer.getScoreTotal();
//			String[] opts2 = {dataPlayer.name, total.toString()};
//			MessengerGeneral.broadcast(Broadcast.STAGE_TOTAL_2, opts2);
//		}
	}
	public static void initializeData() {
		getGame().setMapDataPlayer(new HashMap<String, GlobalStageDataPlayer>());
	}

	public static void correct(Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		GlobalStageRunnable game = getGame();
		GlobalStageDataPlayer data = game.getData(player.getName());
		UtilitiesProgramming.printDebugMessage("" + data.score, new Exception());
		if(data.isEmptyScore()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			data.score =  new ArrayList<Integer>();
			for(int i = 0; i < game.listTalk.size(); i++) {
				data.score.add(0);
			}
		}
		data.score.set(game.count, 1);
		UtilitiesProgramming.printDebugMessage("" + data.score, new Exception());
	}

	public static Integer getScoreCurrent(Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		return getGame().getData(player.getName()).getScoreTotal();
	}

	public static GlobalStageRunnable getGame() {
		return game;
	}
	public static void setGame(GlobalStageRunnable game) {
		GlobalStageGameHandler.game = game;
	}
	@Override
	public void initialize() {}//Not needed methods
	@Override
	public void saveAll() {}//Not needed methods
	@Override
	public void loadAll() {
		ready = GlobalStageConfigHandler.importReady() * 20;//To second
		interval = GlobalStageConfigHandler.importInterval() * 20;//To second
	}
}

package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.FireworkEffect.Type;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.FireworkUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.FireworkUtility.FireworkColor;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMulti;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer.PlayerScore;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public abstract class StageGameHandler {
	public List<StageQuestion> stageQuestions = new ArrayList<StageQuestion>();
	public Integer index = -1;

	public abstract void loadNewGame(String name, String stage);
	public void loadNewGameSorted(String stage) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(ConversationMulti conversation : DataManagerConversation.getMapConversation().values()) {
			UtilitiesProgramming.printDebugMessage(conversation.stage + " " + stage, new Exception());
			UtilitiesProgramming.printDebugConversation(conversation);
			if(conversation.stage.equalsIgnoreCase(stage)) {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				for(Talk talk : conversation.getKeyTalk()) {
					stageQuestions.add(new StageQuestion().loadStageQuestionRandom(talk));
				}
			}
		}
		return;
	}
	public abstract void saveThis();
	public abstract void initialize();

	public void runNext(Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		addIndex();
		StageQuestion stageQuestion = getStageQuestion();
		giveQuestion(stageQuestion, player);
	}
	private void addIndex() {
		index++;
	}
	public boolean isFinished() {
		UtilitiesProgramming.printDebugMessage(index.toString() + " " + stageQuestions.size(), new Exception());
		if(stageQuestions.size() <= index + 1) {
			return true;
		}
		return false;
	}

	public StageQuestion getStageQuestion() {
		UtilitiesProgramming.printDebugMessage(index.toString(), new Exception());
		StageQuestion stageQuestion = stageQuestions.get(index);
		return stageQuestion;
	}

	public void giveQuestion(StageQuestion stageQuestion, Player player) {
		player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
		stageQuestion.printQuestion(player);
	}

	public void validateAnswer(Player player, String answer) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		StageQuestion stageQuestion = getStageQuestion();
		if(stageQuestion.isValidAnswer(answer)) {
			validateCorrect(player);
			if(isFinished()) {
				finishGame(player);
				initialize();
				return;
			} else {
				runNext(player);
				return;
			}
		} else {
			validateWrong(player);
		}
	}
	private void finishGame(Player player) {
		String[] opts = {player.getName()};
		MessengerGeneral.broadcast(MessengerGeneral.getMessage(Message.STAGE_FINISH_1, opts));
		FireworkUtility.shootFirework(player.getWorld(), player.getLocation(), Type.BALL_LARGE, FireworkColor.GREEN, FireworkColor.AQUA, 0);
		DataManagerPlayer.getDataPlayer(player).addScore(player, PlayerScore.PRACTICE);
	}
	private void validateCorrect(Player player) {
		StageQuestion stageQuestion = getStageQuestion();
		String[] opts = {UtilitiesGeneral.joinStrings(stageQuestion.getAnswers(), ", ")};
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.STAGE_CORRECT_1, opts));
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
	}
	private void validateWrong(Player player) {
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.STAGE_WRONG_0, null));
		player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
	}

	public static boolean existsStage(String stage) {
		for(ConversationMulti conversation : DataManagerConversation.getMapConversation().values()) {
			if(conversation.stage.equalsIgnoreCase(stage)) {
				if(0 < conversation.getKeyTalk().size()) {
					return true;
				}
			}
		}
		UtilitiesProgramming.printDebugMessage("Invalid Stage: " + stage, new Exception());
		return false;
	}

//	public StageGame getGameSorted(String stage) {
//		UtilitiesProgramming.printDebugMessage("", new Exception());
//		StageGame game = new StageGame();
//
//		List<Talk> listTalk = new ArrayList<Talk>();
//		for(Conversation conversation : DataManagerConversation.getMapConversation().values()) {
//			if(conversation.stage.equalsIgnoreCase(stage)) {
//				for(Integer key : conversation.getKey()) {
//					listTalk.add(conversation.listTalk.get(key));
//				}
//			}
//		}
//		if(0 < listTalk.size()) {
//			for(Talk talk : listTalk) {
//				Description description = talk.description;
//				List<String> answers = new ArrayList<String>();
//				Random random = new Random();
//				int language = random.nextInt(2);//Random Number 0 or 1
//				switch(language) {
//				case 0:
//					game.languages.add(StageGame.JAPANESE);
//					game.questions.add(description.express(Expression.EN));
//					answers.addAll(description.kanji);
//					answers.addAll(description.kana);
//					answers.addAll(description.romaji);
//					break;
//				case 1:
//					game.languages.add(StageGame.ENGLISH);
//					game.questions.add(description.express(Expression.KANA));
//					answers.addAll(description.en);
//					break;
//				default: break;
//				}
//				game.listAnswers.add(answers);
//			}
//			game.stage = stage;
//		} else {
//			UtilitiesProgramming.printDebugMessage("Invalid Game: " + stage, new Exception());
//		}
//		return game;
//	}
//
//	public StageGame getGameShuffled(String stage) {
//		UtilitiesProgramming.printDebugMessage("", new Exception());
//		StageGame game = getGameSorted(stage);
//		List<Integer> order = new ArrayList<Integer>();
//		for(Integer i = 0; i < game.questions.size(); i++) {
//			order.add(i);
//		}
//		Collections.shuffle(order);
//		List<String> questions = new ArrayList<String>();
//		List<List<String>> listAnswers = new ArrayList<List<String>>();
//		List<String> languages = new ArrayList<String>();
//		List<String> listName = new ArrayList<String>();
//		for(Integer i : order) {
//			questions.add(game.questions.get(i));
//			listAnswers.add(game.listAnswers.get(i));
//			languages.add(game.languages.get(i));
//			listName.add(game.listName.get(i));
//			game.questions = questions;
//			game.listAnswers = listAnswers;
//			game.languages = languages;
//			game.listName = listName;
//		}
//		return game;
//	}
//	public StageGame getGameEmpty() {
//		return new StageGame();
//	}
//	public StageGame getNewGame(String stage) {
//		UtilitiesProgramming.printDebugMessage("", new Exception());
//		StageGame game = new StageGame();
//		game.stage = stage;
//		for(Conversation conversation : DataManagerConversation.getMapConversation().values()) {
//			if(conversation.stage.equalsIgnoreCase(stage)) {
//				for(Integer key : conversation.getKey()) {
//					Description description = conversation.listTalk.get(key).description;
//					game.readDescription(description);
//				}
//			}
//		}
//		return game;
//	}
}

package com.github.orgs.kotobaminers.virtualryuugaku.stage.stagetest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public abstract class StageGameHandler {
	public List<StageQuestion> stageQuestions = new ArrayList<StageQuestion>();
	public Integer index = 0;

	public abstract void loadNewGame(String name, String stage);
	public void loadNewGameSorted(String stage) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(Conversation conversation : DataManagerConversation.getMapConversation().values()) {
			UtilitiesProgramming.printDebugMessage(conversation.stage + " " + stage, new Exception());
			if(conversation.stage.equalsIgnoreCase(stage)) {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				for(Integer key : conversation.getKey()) {
					UtilitiesProgramming.printDebugMessage("", new Exception());
					stageQuestions.add(new StageQuestion().loadStageQuestionRandom(conversation.listTalk.get(key)));
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
		printQuestion(stageQuestion, player);
	}
	private void printFinished(Player player) {
		player.sendMessage("Finished");
	}
	private void addIndex() {
		if(!index.equals(0)) {
			index++;
		}
	}
	public boolean isFinished() {
		if(index < stageQuestions.size()) {
			return true;
		}
		return false;
	}

	public StageQuestion getStageQuestion() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		StageQuestion stageQuestion = stageQuestions.get(index);
		return stageQuestion;
	}

	public void printQuestion(StageQuestion stageQuestion, Player player) {
		stageQuestion.printQuestion(player);
	}

	public void validateAnswer(Player player, String answer) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		StageQuestion stageQuestion = getStageQuestion();
		if(stageQuestion.isValidAnswer(answer)) {
			printCorrect(player);
		} else {
			printWrong(player);
		}
		if(isFinished()) {
			printFinished(player);
			initialize();
			return;
		}
		runNext(player);
	}
	private void printCorrect(Player player) {
		player.sendMessage("correct");
	}
	private void printWrong(Player player) {
		player.sendMessage("wrong");
	}

	public static boolean existsStage(String stage) {
		for(Conversation conversation : DataManagerConversation.getMapConversation().values()) {
			if(conversation.stage.equalsIgnoreCase(stage)) {
				if(0 < conversation.getKey().size()) {
					return true;
				} else {
					UtilitiesProgramming.printDebugMessage("Valid Game But Without Keys: " + stage, new Exception());
					return false;
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

package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
;
public class StageGame {
	public static String JAPANESE = "Japanese";
	public static String ENGLISH = "English";
	public List<String> questions = new ArrayList<String>();
	public List<List<String>> listAnswers = new ArrayList<List<String>>();
	public List<String> languages = new ArrayList<String>();
	public List<String> listName = new ArrayList<String>();
	public String stage = "";

	public String getQuestion(Integer index) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String question = "";
		if(index < questions.size()) {
			question = questions.get(index);
		} else {
			UtilitiesProgramming.printDebugMessage("Invalid Number", new Exception());
		}
		return question;
	}

	public List<String> getAnswers(Integer index) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<String> answers = new ArrayList<String>();
		if(index < listAnswers.size()) {
			answers = listAnswers.get(index);
		} else {
			UtilitiesProgramming.printDebugMessage("Invalid Number", new Exception());
		}
		return answers;
	}

	public StageGame getGameSorted(String stage) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		StageGame game = new StageGame();

		List<Talk> listTalk = new ArrayList<Talk>();
		for(Conversation conversation : DataManagerConversation.getMapConversation().values()) {
			if(conversation.stage.equalsIgnoreCase(stage)) {
				for(Integer key : conversation.getKey()) {
					listTalk.add(conversation.listTalk.get(key));
				}
			}
		}
		if(0 < listTalk.size()) {
			for(Talk talk : listTalk) {
				Description description = talk.description;
				List<String> answers = new ArrayList<String>();
				Random random = new Random();
				int language = random.nextInt(2);//Random Number 0 or 1
				switch(language) {
				case 0:
					languages.add(JAPANESE);
					questions.add(description.express(Expression.EN));
					answers.addAll(description.kanji);
					answers.addAll(description.kana);
					answers.addAll(description.romaji);
					break;
				case 1:
					languages.add(ENGLISH);
					questions.add(description.express(Expression.KANA));
					answers.addAll(description.en);
					break;
				default: break;
				}
				listAnswers.add(answers);
			}
			this.stage = stage;
		} else {
			UtilitiesProgramming.printDebugMessage("Invalid Game: " + stage, new Exception());
		}
		return game;
	}
	public StageGame getGameShuffled(String stage) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		StageGame game = getGameSorted(stage);
		List<Integer> order = new ArrayList<Integer>();
		for(Integer i = 0; i < questions.size(); i++) {
			order.add(i);
		}
		Collections.shuffle(order);
		List<String> questions = new ArrayList<String>();
		List<List<String>> listAnswers = new ArrayList<List<String>>();
		List<String> languages = new ArrayList<String>();
		List<String> listName = new ArrayList<String>();
		for(Integer i : order) {
			questions.add(game.questions.get(i));
			listAnswers.add(game.listAnswers.get(i));
			languages.add(game.languages.get(i));
			listName.add(game.listName.get(i));
			game.questions = questions;
			game.listAnswers = listAnswers;
			game.languages = languages;
			game.listName = listName;
		}
		return game;
	}
	public StageGame getGameEmpty() {
		return new StageGame();
	}
	public Integer size() {
		Integer size = 0;
		if((questions.size() == listAnswers.size()) && (questions.size() == listName.size()) && (questions.size() == languages.size())) {
			size = questions.size();
		} else {
			UtilitiesProgramming.printDebugMessage("Error: StageGame sizes are not same", new Exception());
		}
		return size;
	}
}

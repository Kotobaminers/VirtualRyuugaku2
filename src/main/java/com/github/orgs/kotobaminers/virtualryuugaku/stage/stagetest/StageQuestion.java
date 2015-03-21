package com.github.orgs.kotobaminers.virtualryuugaku.stage.stagetest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class StageQuestion {
	public Talk talk = new Talk();
	public Language language = Language.JAPANESE;
	private enum Language {JAPANESE, ENGLISH;
		public static Language getRandom() {
			Language language = JAPANESE;
			Integer random = new Random().nextInt(2);
			switch(random) {
			case 0:
				language = ENGLISH;
				break;
			}
			return language;
		}
		public static String convertString(Language language) {
			String string = "";
			switch(language) {
			case ENGLISH:
				string = "English";
				break;
			case JAPANESE:
				string = "Japanese";
				break;
			default:
				break;
			}
			return string;
		}
	}

	public StageQuestion loadStageQuestionRandom(Talk talk) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		this.talk = talk;
		language = Language.getRandom();
		return this;
	}

	public void printQuestion(Player player) {
		String[] opts = {getQuestion(player), Language.convertString(language)};
		UtilitiesProgramming.printDebugMessage(UtilitiesGeneral.joinStrings(opts, " "), new Exception());
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.STAGE_QUESTION_2, opts));
	}

	public String getQuestion(Player player) {
		String question = "";
		switch(language) {
		case ENGLISH:
			question = getQuestionEnglish();
			break;
		case JAPANESE:
			question = getQuestionJapanese(player);
			break;
		default:
			break;
		}
		return question;
	}
	private String getQuestionEnglish() {
		return talk.description.getEnglish();
	}
	private String getQuestionJapanese(Player player) {
		return talk.description.getJapanese(player);
	}

	public boolean isValidAnswer(String answer) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<String> answers = getAnswers();
		UtilitiesProgramming.printDebugMessage(answer + " " + UtilitiesGeneral.joinStrings(answers, "-"), new Exception());
		for(String search : answers) {
			if(search.equalsIgnoreCase(answer)) {
				return true;
			}
		}
		return false;
	}
	public List<String> getAnswers() {
		List<String> answers = new ArrayList<String>();
		switch(language) {
		case ENGLISH:
			answers = getAnswersEnglish();
			break;
		case JAPANESE:
			answers = getAnswersJapanese();
			break;
		default:
			break;
		}
		return answers;
	}
	private List<String> getAnswersEnglish() {
		return talk.description.getJapaneseList();
	}
	private List<String> getAnswersJapanese() {
		return talk.description.getEnglishList();
	}



}

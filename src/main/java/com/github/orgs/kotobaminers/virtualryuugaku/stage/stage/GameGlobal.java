package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Language;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.FireworkUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.FireworkUtility.FireworkColor;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public abstract class GameGlobal {
	public static LinkedHashMap<String, Integer> scores = new LinkedHashMap<String, Integer>();
	public static List<Talk> keys = new ArrayList<Talk>();
	protected static Integer count = -1;
	public static List<String> cantAnswer = new ArrayList<String>();
	public static List<Language> listLanguage = new ArrayList<Language>();

	public static void initializeGameGlobal() {
		scores = new LinkedHashMap<String, Integer>();
		keys = new ArrayList<Talk>();
		count = -1;
		cantAnswer = new ArrayList<String>();
		listLanguage = new ArrayList<Language>();
	}
 	public abstract void loadGame(String name);

 	public abstract void giveNextQuestion();

	public abstract void finishGame();

	public void printResult() {
		Integer max = 0;
		for(Integer value : scores.values()) {
			if(max < value) {
				max = value;
			}
		}
		if(0 < max) {
			List<String> results = new ArrayList<String>();
			List<String> winners = new ArrayList<String>();
			for(String name : scores.keySet()) {
				results.add(name + "(" + scores.get(name) + ")");
				if(max == scores.get(name)) {
					winners.add(name);
				}
				String[] opts = {UtilitiesGeneral.joinStrings(results, ", ")};
				MessengerGeneral.broadcast(MessengerGeneral.getMessage(Message.GAME_RESULTS_1, opts));
				String[] optsWinners = {UtilitiesGeneral.joinStrings(winners, ", ")};
				MessengerGeneral.broadcast(MessengerGeneral.getMessage(Message.GAME_WINNERS_1, optsWinners));
				for(Player player : Bukkit.getServer().getOnlinePlayers()) {
					if(winners.contains(player.getName())) {
						shootFirework(player);
					}
				}
			}
		} else {
			MessengerGeneral.broadcast(MessengerGeneral.getMessage(Message.GAME_NO_WINNERS_0, null));
		}
	}

	public boolean validateAnswer(Player player, String answer) {
		List<String> answers = getCurrentAnswers();
		for(String target : answers) {
			if(target.equalsIgnoreCase(answer)) {
				addScore(player);
				cantAnswer.add(player.getName());
				return true;
			}
		}
		return false;
	}
	public abstract void printCorrect(Player player);
	public void printWrong(Player player) {
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.WRONG_0, null) );
	}

	public Talk getCurrentKeyTalk() {
		Talk talk = new Talk();
		talk = keys.get(count);
		return talk;
	}
	public List<String> getCurrentAnswers() {
		List<String> answers = new ArrayList<String>();
		Description description = getCurrentKeyTalk().description;
		Language language = getCurrentLanguage();

		switch(language) {
		case EN:
			answers.addAll(description.getJapaneseList());
			break;
		case JP:
			answers.addAll(description.getEnglishList());
		default:
			break;
		}
		return answers;
	}
	public List<String> getCurrentQuestions() {
		List<String> questions = new ArrayList<String>();
		Description description = getCurrentKeyTalk().description;
		Language language = getCurrentLanguage();

		switch(language) {
		case EN:
			questions.addAll(description.getEnglishList());
			break;
		case JP:
			questions.addAll(description.getJapaneseList());
		default:
			break;
		}
		return questions;
	}
	public String getCurrentAnswersPlayer(Player player) {
		String answers = "";
		Description description = getCurrentKeyTalk().description;
		Language language = getCurrentLanguage();

		switch(language) {
		case EN:
			answers = UtilitiesGeneral.joinStrings(description.getJapaneseListPlayer(player), ", ");
			break;
		case JP:
			answers = UtilitiesGeneral.joinStrings(description.getEnglishList(), ", ");
		default:
			break;
		}
		return answers;
	}
	public String getCurrentQuestionsPlayer(Player player) {
		String questions = "";
		Description description = getCurrentKeyTalk().description;
		Language language = getCurrentLanguage();

		switch(language) {
		case EN:
			questions = UtilitiesGeneral.joinStrings(description.getEnglishList(), ", ");
			break;
		case JP:
			questions = UtilitiesGeneral.joinStrings(description.getJapaneseListPlayer(player), ", ");
		default:
			break;
		}
		return questions;
	}

	public Language getCurrentLanguage() {
		Language language = Language.JP;
		if(count < listLanguage.size()) {
			language = listLanguage.get(count);
		}
		return language;
	}
	public Language getOppositeLanguage() {
		Language language = getCurrentLanguage();
		switch(language) {
		case JP:
			language = Language.EN;
			break;
		case EN:
		default:
			language = Language.JP;
			break;
		}
		return language;
	}

	public boolean hasNextKeyTalk() {
		UtilitiesProgramming.printDebugMessage("" + keys.size() 	, new Exception());
		if (0 < keys.size()) {
			if(count + 1 < keys.size()) {
				return true;
			}
		}
		return false;
	}

	public void addCount() {
		count++;
	}

	public abstract void updateScoreboard(Player player);

	public void addScore(Player player) {
		Integer score = 0;
		if(scores.containsKey(player.getName())) {
			score = scores.get(player.getName());
		}
		scores.put(player.getName(), ++score);
	}
	public void refreshCantAnswer() {
		cantAnswer = new ArrayList<String>();
	}

	public void setLanguageJapanese() {
		listLanguage = new ArrayList<Language>();
		for (int i = 0; i < keys.size(); i++) {
			listLanguage.add(Language.JP);
		}
	}
	public void setLanguageEnglish() {
		listLanguage = new ArrayList<Language>();
		for (int i = 0; i < keys.size(); i++) {
			listLanguage.add(Language.EN);
		}
	}
	public void setLanguageRandom() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		listLanguage = new ArrayList<Language>();
		Random random = new Random();
		Language language = Language.JP;
		UtilitiesProgramming.printDebugMessage("" + keys.size(), new Exception());
		for (int i = 0; i < keys.size(); i++) {
			Integer value = random.nextInt(2);
			UtilitiesProgramming.printDebugMessage("" + value, new Exception());
			switch(value) {
			case 0:
				language = Language.JP;
				listLanguage.add(language);
				break;
			case 1:
			default:
				language = Language.EN;
				listLanguage.add(language);
				break;
			}
		}
		System.out.println(listLanguage);
	}
	public void shootFirework(Player player) {
		FireworkUtility.shootFirework(player.getWorld(), player.getLocation(), Type.BALL_LARGE, FireworkColor.GREEN, FireworkColor.AQUA, 0);
	}
}

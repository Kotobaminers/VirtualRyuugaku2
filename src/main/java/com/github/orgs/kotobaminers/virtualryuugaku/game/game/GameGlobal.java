package com.github.orgs.kotobaminers.virtualryuugaku.game.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message0;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Language;

public class GameGlobal implements Storage {

	protected static final Integer COUNT_INITIAL = -1;
	public String stageName = "";
	public LinkedHashMap<String, Integer> scores = new LinkedHashMap<String, Integer>();
	public List<Talk> talks = new ArrayList<Talk>();
	protected static Integer count = COUNT_INITIAL;
	public List<String> cantAnswer = new ArrayList<String>();
	public List<String> cantFind = new ArrayList<String>();
	public List<Language> listLanguage = new ArrayList<Language>();

	public enum EventScore {
		ANSWER_CORRECTLY(3),
		ANSWER_WRONGLY(0),
		LEFT_CLICK_NPC_CORRECTLY(2),
		LEFT_CLICK_NPC_WRONGLY(-1),
		CHEAT_A_CONVERSATION(-1);
		private final int score;
		private EventScore(int code) {
			this.score = code;
		}
		public int getScore() {
			return score;
		}

		public String[] getMessageOpts() {
			String name = this.toString();
			String pre = "" + ChatColor.RESET;
			if (0 < score) {
				pre = ChatColor.GREEN  + "+";
			} else if(score < 0) {
				pre = "" + ChatColor.DARK_RED;
			}
			String scoreStr = pre + this.score;
			String[] opts = {name, scoreStr};
			return opts;
		}
	}

	@Override
	public void load(String stage) {
		initialize();
		try {
			stageName = stage;
			for (Conversation conversation : ControllerConversation.getConversations(stage)) {
				talks.addAll(conversation.getKeyTalk());
				Collections.shuffle(talks);
			}
			for (int i = 0; i < talks.size(); i++) {
				listLanguage.add(getLanguageRandom());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize() {
		scores = new LinkedHashMap<String, Integer>();
		talks = new ArrayList<Talk>();
		cantAnswer = new ArrayList<String>();
		listLanguage = new ArrayList<Language>();
		count = COUNT_INITIAL;
	}

	public void giveNextQuestion() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		addCount();
		refreshCantAnswer();
		refreshCantFind();
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
			String message = getCurrentQuestionsPlayer(player);
			String[] opts = {message, getOppositeLanguage().toString()};
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.STAGE_QUESTION_2, opts));
		}
	}

	public void finishGame() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		printResult();
		saveScores();
		initialize();
	}

	public void saveScores() {
		for (Entry<String, Integer> entry : scores.entrySet()) {
			DataPlayer data = DataManagerPlayer.getDataPlayer(entry.getKey());
			if (data.getScore(stageName) < entry.getValue()) {
				data.putScore(stageName, entry.getValue());
			}
		}
	}


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
			}
			String[] opts = {UtilitiesGeneral.joinStrings(results, ", ")};
			MessengerGeneral.broadcast(MessengerGeneral.getMessage(Message0.GAME_RESULTS_1, opts));
			String[] optsWinners = {UtilitiesGeneral.joinStrings(winners, ", ")};
			MessengerGeneral.broadcast(MessengerGeneral.getMessage(Message0.GAME_WINNERS_1, optsWinners));
			for(Player player : Bukkit.getServer().getOnlinePlayers()) {
				if(winners.contains(player.getName())) {
					Effects.shootFirework(player);
				}
			}
		} else {
			MessengerGeneral.broadcast(MessengerGeneral.getMessage(Message0.GAME_NO_WINNERS_0, null));
			for(Player player : Bukkit.getServer().getOnlinePlayers()) {
				Effects.playSound(player, Scene.BAD);
			}
		}
	}

	public boolean validateAnswer(Player player, String answer) {
		List<String> answers = getCurrentAnswers();
		for(String target : answers) {
			if(target.equalsIgnoreCase(answer)) {
				return true;
			}
		}

		return false;
	}

	public Talk getCurrentKeyTalk() {
		Talk talk = new Talk();
		talk = talks.get(count);
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

	public boolean hasNextTalk() {
		UtilitiesProgramming.printDebugMessage("" + talks.size() 	, new Exception());
		if (0 < talks.size()) {
			if(count + 1 < talks.size()) {
				return true;
			}
		}
		return false;
	}

	public void addCount() {
		count++;
	}
	public void addScore(Player player, EventScore event) {
		Integer score = 0;
		if(scores.containsKey(player.getName())) {
			score = scores.get(player.getName());
		}
		score = score + event.score;
		scores.put(player.getName(), score);
	}

	public void refreshCantAnswer() {
		cantAnswer = new ArrayList<String>();
	}
	public void refreshCantFind() {
		cantFind = new ArrayList<String>();
	}

	public void setLanguageJapanese() {
		listLanguage = new ArrayList<Language>();
		for (int i = 0; i < talks.size(); i++) {
			listLanguage.add(Language.JP);
		}
	}
	public void setLanguageEnglish() {
		listLanguage = new ArrayList<Language>();
		for (int i = 0; i < talks.size(); i++) {
			listLanguage.add(Language.EN);
		}
	}
	private Language getLanguageRandom() {
		Random random = new Random();
		Language language = Language.JP;
		Integer value = random.nextInt(2);
		switch(value) {
		case 0:
			language = Language.JP;
			break;
		case 1:
		default:
			language = Language.EN;
			break;
		}
		return language;
	}

	public List<String> getRule() {
		List<String> rule = new ArrayList<String>();
		for (EventScore event : EventScore.values()) {
			String line= event.toString() + ": ";
		}

		return rule;
	}

	@Override
	public void load() {
	}
	@Override
	public void save() {
	}
	@Override
	public void setData() {
	}

}
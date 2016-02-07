package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Language;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.VRGSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGameController.PublicGameMode;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public class PublicCommandGameQuestion {
	private VRGSentence talk = new VRGSentence();
	private Language answerLanguage = Language.EN;
	private PublicGameMode mode = PublicGameMode.ANKI;
	private List<String> answers = new ArrayList<String>();
	private List<String> questions = new ArrayList<String>();

	public enum ScoreEvent {
		ANSWER_CORRECTLY(3),
		ANSWER_WRONGLY(0),
		CHEAT_A_CONVERSATION(-1);
		private final int score;
		private ScoreEvent(int code) {
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

	public PublicCommandGameQuestion(VRGSentence talk, PublicGameMode mode, Language answerLanguage) {
		this.talk = talk;
		this.mode = mode;
		this.answerLanguage = answerLanguage;

		List<String> japaneseList = this.talk.description.getJapaneseList();
		List<String> englishList = this.talk.description.getEnglishList();
		switch (this.answerLanguage) {
		case EN:
			answers = englishList;
			questions = japaneseList;
			break;
		case JP:
		default:
			answers = japaneseList;
			questions = englishList;
			break;
		}
	}

	public boolean validateAnswer(String answer) {
		for (String search : answers) {
			if (search.equalsIgnoreCase(answer)) {
				return true;
			}
		}
		return false;
	}

	public void broadcastQuestion() {
		Debug.printDebugMessage(talk.description.getEnglishJoined(), new Exception());
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			printQuestion(player);
		}
	}
	public void printQuestion(Player player) {
		String[] opts = {getQuestionExpression(player), answerLanguage.toString()};
		Message.GAME_QUESTION_2.print(player, opts);
	}

	private String getQuestionExpression(Player player) {
		String expression = "";
		switch(answerLanguage) {
		case EN:
			expression = talk.description.getJapaneseJoined(player);
			break;
		case JP:
			expression = talk.description.getEnglishJoined();
			break;
		default:
			break;
		}
		return expression;
	}

	public Integer getScore() {
		return 1;
	}
}
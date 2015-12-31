package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message0;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.NPCSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGameController.PublicGameMode;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Language;

public class PublicCommandGameQuestion {
	private NPCSentence talk = new NPCSentence();
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

	public PublicCommandGameQuestion(NPCSentence talk, PublicGameMode mode, Language answerLanguage) {
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
		Debug.printDebugMessage("", new Exception());
		String message = Utility.joinStrings(questions, ", ");
		String[] opts = {message, answerLanguage.toString()};
		System.out.println(message + " " + talk.description.romaji + " " + answerLanguage.name());
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.STAGE_QUESTION_2, opts));
		}
	}

	public Integer getScore() {
		return 1;
	}
}
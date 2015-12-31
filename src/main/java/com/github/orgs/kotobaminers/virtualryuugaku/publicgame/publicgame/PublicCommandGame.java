package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.NPCSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGameController.PublicGameMode;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Language;


public class PublicCommandGame extends PublicGame {
	public List<PublicCommandGameQuestion> questions = new ArrayList<PublicCommandGameQuestion>();

	public static long interval = 200L;
	private Integer count = -1;


	public PublicCommandGame(List<NPCSentence> talks, PublicGameMode mode) {
		for (NPCSentence talk : talks) {
			questions.add(new PublicCommandGameQuestion(talk, mode, Language.getRandom()));
		}
	}

	@Override
	public void continueGame() {
		giveQuestion();
	}

	@Override
	public boolean isFinished() {
		Debug.printDebugMessage("" + count + " " + questions.size(), new Exception());
		if (questions.size() <= count + 1) {
			return true;
		}
		return false;
	}

	@Override
	public long getInterval() {
		return interval;
	}

	private void giveQuestion() {
		count++;
		PublicCommandGameQuestion question = getCurrentQuestion();
		question.broadcastQuestion();
	}

	@Override
	public void validateAnswer(String answer) {
		if (0 <= count) {
			if (getCurrentQuestion().validateAnswer(answer)) {
				eventCorrect();
			} else {
				eventWrong();
			}
		}
	}

	private PublicCommandGameQuestion getCurrentQuestion() {
		return questions.get(count);
	}

	@Override
	public void eventCorrect() {
		Debug.printDebugMessage("", new Exception());
	}

	@Override
	public void eventWrong() {
		Debug.printDebugMessage("", new Exception());
	}

	@Override
	public void validateEvent(Player player) {
	}
}

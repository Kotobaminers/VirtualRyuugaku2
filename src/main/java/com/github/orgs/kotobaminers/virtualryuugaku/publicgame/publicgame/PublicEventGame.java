package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import java.util.ArrayList;
import java.util.List;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.NPCSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGameController.PublicGameMode;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public class PublicEventGame extends PublicGame {
	List<NPCSentence> sentences = new ArrayList<NPCSentence>();

	public static long interval = 100L;
	private Integer count = -1;


	public PublicEventGame(List<NPCSentence> sentences, PublicGameMode mode) {
		this.sentences = sentences;
		this.mode = mode;
	}

	public void eventTouchNPC(NPC npc, Player player) {
		if (npc.getId() == getCurrentSentence().id) {
			eventCorrect();
		} else {
			eventWrong();
		}
	}

	@Override
	public void continueGame() {
		giveQuestion();
	}

	@Override
	public boolean isFinished() {
		Debug.printDebugMessage("" + count + " " + sentences.size(), new Exception());
		if (sentences.size() <= count + 1) {
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
		NPCSentence sentence= getCurrentSentence();
		Debug.printDebugMessage("" + sentence.description.en.get(0), new Exception());
	}

	@Override
	public void validateAnswer(String answer) {
	}

	private NPCSentence getCurrentSentence() {
		return sentences.get(count);
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

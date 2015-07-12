package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.ArrayList;
import java.util.List;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;


public abstract class GameGlobal {
	public List<Talk> keys = new ArrayList<Talk>();
	protected Integer count = -1;

 	public abstract GameGlobal loadGame(String name);

 	public abstract void giveNextQuestion();

	public abstract void finishGame();

	public abstract void validateAnswer(String[] args);

	public Talk getCurrentKeyTalk() {
		Talk talk = new Talk();
		if(hasNextKeyTalk()) {
			talk = keys.get(count);
		}
		return talk;
	}

	public boolean hasNextKeyTalk() {
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
}
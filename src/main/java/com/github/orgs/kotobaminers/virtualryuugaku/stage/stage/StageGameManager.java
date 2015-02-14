package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.Map;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;

public abstract class StageGameManager {
	public Integer index = 0;

	public void addIndex() {
		this.index++;
	}

	public StageGame getGame(String key) {
		return getMapStageGame().get(key);
	}
	public StageGame putGame(String key, StageGame game) {
		return getMapStageGame().put(key, game);
	}
	public abstract Map<String, StageGame> getMapStageGame();

	public void runNext(String key) {
		StageGame game = getGame(key);
		if(index < game.size()) {
			String[] opts = {index.toString(), game.getQuestion(index), game.languages.get(index), game.listName.get(index)};
			String message = MessengerGeneral.getMessage(Message.STAGE_GAME_QUESTION_4, opts);
			printQuestion(message);
		} else {
			printEnd();
			putGame(key, game.getGameEmpty());
		}
	}

	protected abstract void printEnd();

	protected abstract void printQuestion(String question);
}
package com.github.orgs.kotobaminers.virtualryuugaku.stage.stagetest;



public class PracticeStageHandler extends StageGameHandler {
	public String name = "";
	@Override
	public void loadNewGame(String name, String stage) {
		this.name = name;
		loadNewGameSorted(stage);
		saveThis();
	}
	@Override
	public void saveThis() {
		StageGameDataStorage.mapPractice.put(name, this);
	}
	@Override
	public void initialize() {
		StageGameDataStorage.mapPractice.remove(name);
	}
}
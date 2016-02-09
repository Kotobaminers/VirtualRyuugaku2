package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.List;

public class Unit {
	private List<List<HolographicSentence>> helperSentences = new ArrayList<List<HolographicSentence>>();
	private List<List<HolographicSentence>> playerSentences = new ArrayList<List<HolographicSentence>>();
	private List<Integer> playerNPCIds = new ArrayList<Integer>();
	private List<String> playerQuestions = new ArrayList<String>();

	public List<List<HolographicSentence>> getHelperSentences() {
		return helperSentences;
	}
	public void setHelperSentences(List<List<HolographicSentence>> helperSentences) {
		this.helperSentences = helperSentences;
	}
	public List<List<HolographicSentence>> getPlayerSentences() {
		return playerSentences;
	}
	public void setPlayerSentences(List<List<HolographicSentence>> playerSentences) {
		this.playerSentences = playerSentences;
	}
	public List<Integer> getPlayerNPCIds() {
		return playerNPCIds;
	}
	public void setPlayerNPCIds(List<Integer> playerNPCIds) {
		this.playerNPCIds = playerNPCIds;
	}
	public List<String> getPlayerQuestions() {
		return playerQuestions;
	}
	public void setPlayerQuestions(List<String> playerQuestions) {
		this.playerQuestions = playerQuestions;
	}
}

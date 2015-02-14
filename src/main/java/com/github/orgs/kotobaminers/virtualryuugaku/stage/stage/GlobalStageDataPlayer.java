package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.ArrayList;
import java.util.List;

public class GlobalStageDataPlayer {
	String name;
	List<Integer> score = new ArrayList<Integer>();
	public Integer getScoreTotal() {
		Integer total = 0;
		for(int i : score) {
			total += i;
		}
		return total;
	}
	public boolean isEmptyScore() {
		if(0 < score.size()) {
			return false;
		}
		return true;
	}
}
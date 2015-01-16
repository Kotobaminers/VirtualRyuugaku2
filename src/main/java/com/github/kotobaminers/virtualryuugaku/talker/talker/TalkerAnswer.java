package com.github.kotobaminers.virtualryuugaku.talker.talker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TalkerAnswer {
	private Map<KeyAnswer, List<String>> mapAnswers = new HashMap<KeyAnswer, List<String>>();
	public enum KeyAnswer {EN, JP}
	public List<String> getEn() {
		List<String> answers = new ArrayList<String>();
		KeyAnswer key = KeyAnswer.EN;
		if(mapAnswers.containsKey(key)) {
			answers = mapAnswers.get(key);
		}
		return answers;
	}
	public List<String> getJp() {
		List<String> answers = new ArrayList<String>();
		KeyAnswer key = KeyAnswer.JP;
		if(mapAnswers.containsKey(key)) {
			answers = mapAnswers.get(key);
		}
		return answers;
	}
	private void setEn(List<String> en) {
		mapAnswers.put(KeyAnswer.EN, en);
	}
	private void setJp(List<String> jp) {
		mapAnswers.put(KeyAnswer.JP, jp);
	}
	public TalkerAnswer create(List<String> en, List<String> jp) {
		TalkerAnswer answer = new TalkerAnswer();
		answer.setEn(en);
		answer.setJp(jp);
		return answer;
	}
}
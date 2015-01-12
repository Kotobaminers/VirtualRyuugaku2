package com.gmail.fukushima.kai.talker.talker;

import java.util.HashMap;
import java.util.Map;

public class TalkerQuestion {
	private Map<KeyQuestion, String> mapQuestion = new HashMap<KeyQuestion, String>();
	public  enum KeyQuestion {EN, JP}
	public String getEn() {
		String question = "";
		KeyQuestion key = KeyQuestion.EN;
		if(mapQuestion.containsKey(key)) {
			question = mapQuestion.get(key);
		}
		return question;
	}
	public String getJp() {
		String question = "";
		KeyQuestion key = KeyQuestion.JP;
		if(mapQuestion.containsKey(key)) {
			question = mapQuestion.get(key);
		}
		return question;
	}
	private void setEn(String en) {
		mapQuestion.put(KeyQuestion.EN, en);
	}
	private void setJp(String jp) {
		mapQuestion.put(KeyQuestion.JP, jp);
	}
	public TalkerQuestion create(String en, String jp) {
		TalkerQuestion question = new TalkerQuestion();
		question.setEn(en);
		question.setJp(jp);
		return question;
	}
}
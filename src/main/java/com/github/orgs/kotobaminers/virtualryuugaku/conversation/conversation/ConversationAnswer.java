package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversationAnswer {
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
	public ConversationAnswer create(List<String> en, List<String> jp) {
		ConversationAnswer answer = new ConversationAnswer();
		answer.setEn(en);
		answer.setJp(jp);
		return answer;
	}
}
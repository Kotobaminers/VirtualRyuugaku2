package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;


public class ConversationQuestion {
	private String question = "";
	private List<String> answers = new ArrayList<String>();
	private List<Integer> key = new ArrayList<Integer>();
	private String stageName = "";

	public static ConversationQuestion create(String q, List<String> a, List<Integer> key, String stage) {
		ConversationQuestion question = new ConversationQuestion();
		question.question = q;
		question.answers = a;
		question.key = key;
		question.stageName = stage;
		return question;
	}

	public void giveQuestion(Player player, ConversationQuestion q) {
	}

	public String getQuestion() {
		return question;
	}
	public List<String> getAnswers() {
		return answers;
	}
	public List<Integer> getKey() {
		return key;
	}
}
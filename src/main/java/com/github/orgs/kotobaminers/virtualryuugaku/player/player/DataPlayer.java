package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMyself;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationQuestion;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Language;

public class DataPlayer {
	public String name = "";
	public Integer line = lineIni;
	public Integer select = -1;
	public Language language = Language.JP;
	public Conversation conversation = new ConversationMyself();
	public Set<List<Integer>> questionDone = new HashSet<List<Integer>>();
	public Set<List<Integer>> conversationDone = new HashSet<List<Integer>>();

	private Map<String, Integer> score = new HashMap<String, Integer>();

	public List<Expression> expressions = new ArrayList<Expression>(Arrays.asList(Expression.EN, Expression.KANJI, Expression.KANA, Expression.ROMAJI));
	public ConversationQuestion question = new ConversationQuestion();

	public static final Integer lineIni = -1;

	public Integer getScore(String stage) {
		Integer value = 0;
		for (String search : score.keySet()) {
			if (stage.equalsIgnoreCase(search)) {
				value = score.get(stage);
			}
		}
		return value;
	}
	public void putScore(String stage, Integer value) {
		score.put(stage.toUpperCase(), value);
	}


}
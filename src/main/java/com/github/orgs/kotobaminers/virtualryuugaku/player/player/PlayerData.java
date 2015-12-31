package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMulti;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationQuestion;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Expression;

public class PlayerData {
	public String name = "";
	public UUID uuid = null;
	public Integer line = lineIni;
	private static final Integer lineIni = -1;
	public Conversation conversation = new ConversationMulti();

	private Map<String, Integer> score = new HashMap<String, Integer>();

	public List<Expression> expressions = new ArrayList<Expression>(Arrays.asList(Expression.EN, Expression.KANJI, Expression.KANA, Expression.ROMAJI));
	public ConversationQuestion question = new ConversationQuestion();


	public void putScore(String stage, Integer value) {
		score.put(stage.toUpperCase(), value);
	}

	public void printDebugMessage() {
		System.out.println("PlayerData: " + name + " " + line + " " + uuid.toString());
	}
}
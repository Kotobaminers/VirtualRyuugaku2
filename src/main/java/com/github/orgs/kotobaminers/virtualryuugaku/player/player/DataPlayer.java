package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMyself;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationQuestion;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Language;

public class DataPlayer {
	public String name = "";
	public Integer line = lineIni;
	public Integer select = -1;
	public Language language = Language.JP;
	public Conversation conversation = new ConversationMyself();
	public List<List<Integer>> questionDone = new ArrayList<List<Integer>>();
	public List<Expression> expressions = new ArrayList<Expression>(Arrays.asList(Expression.EN, Expression.KANJI, Expression.KANA, Expression.ROMAJI));
	public ConversationQuestion question = new ConversationQuestion();
	public Map<PlayerScore, Integer> mapScore = new HashMap<PlayerScore, Integer>();
	public enum PlayerScore {PRACTICE, FIND_PEOPLE}

	public static final Integer lineIni = -1;

	public void addQuestionDone(Player player, List<Integer> key) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		if(!data.questionDone.contains(key)) {
			data.questionDone.add(key);
		}
	}

	public void addLine(Conversation current) {
		if (!conversation.equals(current)) {
			line = 0;
		} else if(line + 1 < current.listTalk.size()) {
			line++;
		} else {
			line = 0;
		}
	}
}
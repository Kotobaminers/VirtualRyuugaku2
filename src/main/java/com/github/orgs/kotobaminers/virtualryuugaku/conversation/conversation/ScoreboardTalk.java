package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.ScoreboardUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer.PlayerScore;

public class ScoreboardTalk extends ScoreboardUtility {
	private static Map<String, Scoreboard> mapScoreboard = new HashMap<String, Scoreboard>();
	private static Map<String, Objective> mapObjective = new HashMap<String, Objective>();
	public static final String NAME_BOARD = ChatColor.LIGHT_PURPLE + "* Mahou Jisho *";
	public static final String QUESTION = ChatColor.GOLD + "Question";
	public static final String FIND_PEOPLE = ChatColor.GREEN + "Find People";
	public static final String PRACTICE = ChatColor.AQUA + "Practice";

	public void update(Player player, Conversation conversation, DataPlayer data) {
		LinkedHashMap<String, Integer> scores = new LinkedHashMap<String, Integer>();
		Integer keyTotal = 0;
		String stage = conversation.stage;
		for(Conversation search : DataManagerConversation.getMapConversation().values()) {
			if(stage.equalsIgnoreCase(search.stage)) {
				keyTotal += search.getKey().size();
			}
		}
		scores.put(stage, keyTotal);
		if(data.line < conversation.listTalk.size()) {
			Talk talk = conversation.listTalk.get(data.line);
			scores.put(talk.name, talk.id.intValue());
		}
		Integer question = DataManagerConversation.getNumberQuestion(stage);
		scores.put(QUESTION + "(" + question.toString() + ")", data.questionDone.size());
		scores.put(FIND_PEOPLE, data.getScore(PlayerScore.FIND_PEOPLE));
		scores.put(PRACTICE, data.getScore(PlayerScore.PRACTICE));//TODO data.practice will be replaced.
		updateScoreboard(player, NAME_BOARD, scores);
	}

	@Override
	public Scoreboard getScoreboard(String key) {
		return mapScoreboard.get(key);
	}
	@Override
	public Objective getObjective(String key) {
		return mapObjective.get(key);
	}
	@Override
	public void putScoreboard(String name, Scoreboard board) {
		mapScoreboard.put(name, board);
	}
	@Override
	public void putObjective(String name, Objective objective) {
		mapObjective.put(name, objective);
	}

}
package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Japanese;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Language;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class DataPlayer {
	public String name = "";
	public Integer line = 0;
	public Integer select = -1;
	public Expression expression = Expression.ROMAJI;
	public Language language = Language.JP;
	public Japanese japanese = Japanese.ROMAJI;
	public List<Integer> done = new ArrayList<Integer>();
	public List<Expression> expressions = new ArrayList<Expression>(Arrays.asList(Expression.EN, Expression.KANJI, Expression.KANA, Expression.ROMAJI));
	public Map<PlayerScore, Integer> mapScore = new HashMap<PlayerScore, Integer>();
	public enum PlayerScore {PRACTICE, FIND_PEOPLE}

	public Integer getScore(Player player, PlayerScore score) {
		Integer value = 0;
		if (mapScore.containsKey(score)) {
			value = mapScore.get(score);
		} else {
			mapScore.put(score, value);
		}
		return value;
	}
	public void addScore(Player player, PlayerScore score) {
		Integer value = getScore(player, score);
		mapScore.put(score, ++value);
		UtilitiesProgramming.printDebugMessage(getScore(player, PlayerScore.FIND_PEOPLE).toString(), new Exception());
	}


}
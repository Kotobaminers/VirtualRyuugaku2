package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Japanese;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Language;

public class DataPlayer {
	public String name = "";
	public Integer line = 0;
	public Integer select = -1;
	public Expression expression = Expression.ROMAJI;
	public Language language = Language.JP;
	public Japanese japanese = Japanese.ROMAJI;
	public List<Integer> done = new ArrayList<Integer>();
	public List<Expression> expressions = new ArrayList<Expression>(Arrays.asList(Expression.EN, Expression.KANJI, Expression.KANA, Expression.ROMAJI));
}
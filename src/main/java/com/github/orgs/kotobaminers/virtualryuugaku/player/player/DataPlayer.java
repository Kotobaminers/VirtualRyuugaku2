package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import java.util.ArrayList;
import java.util.List;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;

public class DataPlayer {
	public String name = "";
	public Integer line = 0;
	public Integer select = -1;
	public Expression language = Expression.ROMAJI;
	public List<Integer> done = new ArrayList<Integer>();
}
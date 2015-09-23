package com.github.orgs.kotobaminers.virtualryuugaku.game.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Language;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;

public class GameGlobal implements Storage {

	private static final Integer COUNT_INITIAL = -1;

	public static LinkedHashMap<String, Integer> scores = new LinkedHashMap<String, Integer>();
	public static List<Talk> talks = new ArrayList<Talk>();
	protected static Integer count = COUNT_INITIAL;
	public static List<String> cantAnswer = new ArrayList<String>();
	public static List<Language> listLanguage = new ArrayList<Language>();



	@Override
	public void load() {
	}
	@Override
	public void load(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize() {

	}


	@Override
	public void save() {
	}

	@Override
	public void setData() {
	}

}
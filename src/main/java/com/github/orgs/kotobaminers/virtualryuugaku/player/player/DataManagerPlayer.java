package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.DataManager;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Expression;

public class DataManagerPlayer implements DataManager {
	private static Map<String, DataPlayer> mapDataPlayer = new HashMap<String, DataPlayer>();

	public static DataPlayer getDataPlayer(Player player) {
		return getDataPlayer(player.getName());
	}
	public static DataPlayer getDataPlayer(String name) {
		DataPlayer data = new DataPlayer();
		if(getMapDataPlayer().containsKey(name)) {
			data = getMapDataPlayer().get(name);
		} else {
			data.name = name;
			putDataPlayer(data);//In the case without existing playerdata, one is created here.
		}
		return data;
	}
	public static void putDataPlayer(DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		getMapDataPlayer().put(data.name, data);
	}
	public static void toggleExpression(DataPlayer data, Expression expression) {
		List<Expression> expressions = data.expressions;
		if(expressions.contains(expression)) {
			while(expressions.remove(expression)) {};
		} else {
			expressions.add(expression);
		}
	}

	public static Talk getTalk(DataPlayer data) throws Exception{
		if (data.line < data.conversation.listTalk.size()) {
			return data.conversation.listTalk.get(data.line);
		}
		throw new Exception("Invalid talk: Name" + data.name);
	}


	public static Map<String, DataPlayer> getMapDataPlayer() {
		return mapDataPlayer;
	}
	private static void setMapDataPlayer(Map<String, DataPlayer> mapDataPlayer) {
		DataManagerPlayer.mapDataPlayer = mapDataPlayer;
	}

	@Override
	public void load() {
		initialize();
		loadDataPlayer();
	}
	@Override
	public void initialize() {
		setMapDataPlayer(new HashMap<String, DataPlayer>());
	}
	private static void loadDataPlayer() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Map<String, DataPlayer> map = new HashMap<String, DataPlayer>();
		List<DataPlayer> list = ConfigHandlerPlayer.importDataPlayer();
		for(DataPlayer data : list) {
			map.put(data.name, data);
		}
		setMapDataPlayer(map);
	}

	@Override
	public void save() {
		for(DataPlayer data : getMapDataPlayer().values()) {
			ConfigHandlerPlayer.saveDataPlayer(data);
		}
		new ConfigHandlerPlayer().save();
	}

	public Conversation loadCurrentConversation(Player player) {
		DataPlayer data = getDataPlayer(player);
		Conversation conversation = data.conversation;
		return conversation;
	}
}
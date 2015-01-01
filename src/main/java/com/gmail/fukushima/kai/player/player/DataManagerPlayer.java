package com.gmail.fukushima.kai.player.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class DataManagerPlayer {
	public static Map<String, DataPlayer> mapDataPlayer = new HashMap<String, DataPlayer>();

	public static DataPlayer getDataPlayer(Player player) {
		DataPlayer data = new DataPlayer();
		if(mapDataPlayer.containsKey(player.getName())) {
			data = mapDataPlayer.get(player.getName());
		} else {
			data.name = player.getName();
		}
		return data;
	}

	public static void importDataPlayer() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(String key : ConfigHandlerPlayer.config.getKeys(false)) {
			DataPlayer data = ConfigHandlerPlayer.loadDataPlayer(key);
			mapDataPlayer.put(key, data);
		}
	}
	public static void saveMapDataPlayer() {
		for(DataPlayer data : mapDataPlayer.values()) {
			ConfigHandlerPlayer.saveDataPlayer(data);
		}
		ConfigHandlerPlayer.save();
	}
	public static void saveDataPlayer(DataPlayer data) {
		ConfigHandlerPlayer.saveDataPlayer(data);
	}
	public static void putDataPlayer(DataPlayer data) {
		mapDataPlayer.put(data.name, data);
	}

	public static void addDone(Player player, Integer id) {
		DataPlayer data = getDataPlayer(player);
		List<Integer> done = new ArrayList<Integer>();
		done.addAll(data.done);
		if(!done.contains(id)) {
			done.add(id);
			data.done = done;
			putDataPlayer(data);
		} else {
			UtilitiesProgramming.printDebugMessage("This Talker has already been added.", new Exception());
		}
	}
}
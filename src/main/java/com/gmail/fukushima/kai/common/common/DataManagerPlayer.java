package com.gmail.fukushima.kai.common.common;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class DataManagerPlayer {
	public static Map<String, DataPlayer> mapDataPlayer = new HashMap<String, DataPlayer>();

	public static DataPlayer getDataPlayer(Player player) {
		DataPlayer data = new DataPlayer();
		if(mapDataPlayer.containsKey(player.getName())) {
			data = mapDataPlayer.get(player.getName());
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
	public static void saveDataPlayer() {
		for(DataPlayer data : mapDataPlayer.values()) {
			ConfigHandlerPlayer.putDataPlayer(data);
		}
		ConfigHandlerPlayer.save();
	}
}
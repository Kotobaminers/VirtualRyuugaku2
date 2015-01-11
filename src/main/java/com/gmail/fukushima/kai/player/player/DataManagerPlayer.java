package com.gmail.fukushima.kai.player.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.utilities.utilities.DataManager;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class DataManagerPlayer implements DataManager {
	private static Map<String, DataPlayer> mapDataPlayer = new HashMap<String, DataPlayer>();

	public static DataPlayer getDataPlayer(Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataPlayer data = new DataPlayer();
		if(getMapDataPlayer().containsKey(player.getName())) {
			data = getMapDataPlayer().get(player.getName());
		}
		return data;
	}
	public static void putDataPlayer(DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		getMapDataPlayer().put(data.name, data);
	}

	@Override
	public void loadAll() {
		initialize();
		importDataPlayer();
	}
	@Override
	public void initialize() {
		setMapDataPlayer(new HashMap<String, DataPlayer>());
	}
	private static void importDataPlayer() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Map<String, DataPlayer> map = new HashMap<String, DataPlayer>();
		for(String key : ConfigHandlerPlayer.config.getKeys(false)) {
			DataPlayer data = ConfigHandlerPlayer.loadDataPlayer(key);
			map.put(key, data);
		}
		setMapDataPlayer(map);
	}

	@Override
	public void saveAll() {
		saveMapDataPlayer();
	}
	private static void saveMapDataPlayer() {
		for(DataPlayer data : getMapDataPlayer().values()) {
			ConfigHandlerPlayer.saveDataPlayer(data);
		}
		new ConfigHandlerPlayer().save();
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

	public static Map<String, DataPlayer> getMapDataPlayer() {
		return mapDataPlayer;
	}
	private static void setMapDataPlayer(Map<String, DataPlayer> mapDataPlayer) {
		DataManagerPlayer.mapDataPlayer = mapDataPlayer;
	}
}
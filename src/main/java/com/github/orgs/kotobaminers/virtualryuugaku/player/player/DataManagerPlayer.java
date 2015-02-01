package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.DataManager;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class DataManagerPlayer implements DataManager {
	private static Map<String, DataPlayer> mapDataPlayer = new HashMap<String, DataPlayer>();

	public static DataPlayer getDataPlayer(Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataPlayer data = new DataPlayer();
		if(getMapDataPlayer().containsKey(player.getName())) {
			data = getMapDataPlayer().get(player.getName());
		} else {
			data.name = player.getName();
			putDataPlayer(data);//In the case without existing playerdata, one is created here.
		}
		return data;
	}
	public static void putDataPlayer(DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		getMapDataPlayer().put(data.name, data);
	}

	public static Map<String, DataPlayer> getMapDataPlayer() {
		return mapDataPlayer;
	}
	private static void setMapDataPlayer(Map<String, DataPlayer> mapDataPlayer) {
		DataManagerPlayer.mapDataPlayer = mapDataPlayer;
	}

	public static void selectTalker(Player player, Integer id) {
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		data.select = id;
		data.line = 0;
		String[] opts = {id.toString()};
		MessengerGeneral.print(player, Message.SELECT_TALKER_1, opts);
	}

	@Override
	public void loadAll() {
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
	public void saveAll() {
		saveMapDataPlayer();
	}
	private static void saveMapDataPlayer() {
		for(DataPlayer data : getMapDataPlayer().values()) {
			ConfigHandlerPlayer.saveDataPlayer(data);
		}
		new ConfigHandlerPlayer().save();
	}
}
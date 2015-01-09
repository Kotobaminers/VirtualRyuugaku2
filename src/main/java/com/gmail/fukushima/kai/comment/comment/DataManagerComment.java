package com.gmail.fukushima.kai.comment.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.fukushima.kai.comment.comment.ConfigHandlerComment.PathComment;
import com.gmail.fukushima.kai.comment.comment.DataComment.CommentState;
import com.gmail.fukushima.kai.utilities.utilities.DataManager;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class DataManagerComment implements DataManager {
	public static Map<String, DataComment> mapDataComment = new HashMap<String, DataComment>();
	public static DataComment loadComment(String key) {
		DataComment data = new DataComment();
		if(mapDataComment.containsKey(key)) {
			data = mapDataComment.get(key);
		}
		return data;
	}
	public static DataComment loadComment(String owner, Integer id, String sender) {
		String key = createKey(owner, id, sender);
		DataComment data = loadComment(key);
		return data;
	}
	@Override
	public void initialize() {
		mapDataComment = new HashMap<String, DataComment>();
	}
	@Override
	public void load() {
		initialize();
		importMapDataComment();
	}
	private void importMapDataComment() {
		YamlConfiguration config = ConfigHandlerComment.config;
		for(String owner : config.getKeys(false)) {
			MemorySection memoryOwner = (MemorySection) config.get(owner);
			for(String idString : memoryOwner.getKeys(false)) {
				Integer id = Integer.parseInt(idString);
				MemorySection memoryId = (MemorySection) memoryOwner.get(idString);
				for(String sender : memoryId.getKeys(false)) {
					DataComment data = new DataComment();
					data.owner = owner;
					data.id = id;
					data.sender = sender;
					String path = owner + "." + idString + "." + sender;
					String pathComment = path + "." + PathComment.COMMENT.toString();
					data.comment = config.getString(pathComment);
					String pathState = path + "." + PathComment.STATE.toString();
					String stateString = config.getString(pathState);
					data.state = CommentState.lookup(stateString);
					putData(data);
				}
			}
		}
	}
	@Override
	public void saveAll() {
		for(DataComment data : mapDataComment.values()) {
			UtilitiesProgramming.printDebugComment(data);
			ConfigHandlerComment.saveDataComment(data);
		}
		new ConfigHandlerComment().save();
	}
	public static void putData(DataComment data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String key = createKey(data.owner, data.id, data.sender);
		DataComment dataLoad = loadComment(key);
		if(data.isSame(dataLoad)) {
			UtilitiesProgramming.printDebugMessage("Same DataComment Given.", new Exception());
		} else {
			mapDataComment.put(key, data);
		}
	}
	public static void saveAtConfig(DataComment data) {
		ConfigHandlerComment.saveDataComment(data);
	}
	public static List<DataComment> loadListCommentById(String owner, Integer id) {
		List<DataComment> list = new ArrayList<DataComment>();
		String keyStart = createKey(owner, id, "");
		for(String key : mapDataComment.keySet()) {
			if(key.startsWith(keyStart)) {
				list.add(loadComment(key));
			}
		}
		return list;
	}
	private static String createKey(String owner, Integer id, String sender) {
		String key = owner + "@" + id.toString() + "@" + sender;
		return key;
	}
}
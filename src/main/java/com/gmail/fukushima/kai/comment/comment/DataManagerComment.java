package com.gmail.fukushima.kai.comment.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmail.fukushima.kai.utilities.utilities.DataManager;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class DataManagerComment implements DataManager {
	private static Map<String, DataComment> mapDataComment = new HashMap<String, DataComment>();
	private static DataComment getComment(String key) {
		DataComment data = new DataComment();
		if(getMapDataComment().containsKey(key)) {
			data = getMapDataComment().get(key);
		}
		return data;
	}
	public static void putData(DataComment data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String key = createKey(data.editor, data.id, data.sender);
		DataComment dataLoad = getComment(key);
		if(data.isSame(dataLoad)) {
			UtilitiesProgramming.printDebugMessage("Same DataComment Given.", new Exception());
		} else {
			getMapDataComment().put(key, data);
		}
	}
	public static DataComment getComment(String owner, Integer id, String sender) {
		String key = createKey(owner, id, sender);
		DataComment data = getComment(key);
		return data;
	}

	@Override
	public void loadAll() {
		initialize();
		loadMapDataComment();
	}
	@Override
	public void initialize() {
		setMapDataComment(new HashMap<String, DataComment>());
	}
	private void loadMapDataComment() {
		List<DataComment> list = ConfigHandlerComment.importListDataComment();
		for(DataComment data : list) {
			putData(data);
		}
	}

	@Override
	public void saveAll() {
		for(DataComment data : getMapDataComment().values()) {
			UtilitiesProgramming.printDebugComment(data);
			ConfigHandlerComment.saveDataComment(data);
		}
		new ConfigHandlerComment().save();
	}

	public static List<DataComment> loadListCommentById(String owner, Integer id) {
		List<DataComment> list = new ArrayList<DataComment>();
		String keyStart = createKey(owner, id, "");
		for(String key : getMapDataComment().keySet()) {
			if(key.startsWith(keyStart)) {
				list.add(getComment(key));
			}
		}
		return list;
	}

	private static String createKey(String owner, Integer id, String sender) {
		String key = owner + "@" + id.toString() + "@" + sender;
		return key;
	}
	public static Map<String, DataComment> getMapDataComment() {
		return mapDataComment;
	}
	public static void setMapDataComment(Map<String, DataComment> mapDataComment) {
		DataManagerComment.mapDataComment = mapDataComment;
	}
}
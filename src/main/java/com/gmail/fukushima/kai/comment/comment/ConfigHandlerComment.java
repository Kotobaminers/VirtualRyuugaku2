package com.gmail.fukushima.kai.comment.comment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.fukushima.kai.comment.comment.DataComment.CommentState;
import com.gmail.fukushima.kai.utilities.utilities.ConfigHandler;

public class ConfigHandlerComment extends ConfigHandler {
	public static YamlConfiguration config;
	public static File file;
	public static final String DIRECTORY = "COMMENT";
	public static final String FILE_NAME = "COMMENT.yml";
	public enum PathComment {COMMENT, STATE}
	public static DataComment loadDataComment(String owner) {
		DataComment data = new DataComment();
		return data;
	}
	public static List<DataComment> importListDataComment() {
		List<DataComment> list = new ArrayList<DataComment>();
		for(String owner : config.getKeys(false)) {
			MemorySection memoryOwner = (MemorySection) config.get(owner);
			for(String idString : memoryOwner.getKeys(false)) {
				Integer id = Integer.parseInt(idString);
				MemorySection memoryId = (MemorySection) memoryOwner.get(idString);
				for(String sender : memoryId.getKeys(false)) {
					DataComment data = new DataComment();
					data.editor = owner;
					data.id = id;
					data.sender = sender;
					String path = owner + "." + idString + "." + sender;
					String pathComment = path + "." + PathComment.COMMENT.toString();
					data.expression = config.getString(pathComment);
					String pathState = path + "." + PathComment.STATE.toString();
					String stateString = config.getString(pathState);
					data.state = CommentState.lookup(stateString);
					list.add(data);
				}
			}
		}
		return list;
	}
	public static void saveDataComment(DataComment data) {
		String path = data.editor + "." + data.id.toString() + "." + data.sender;
		config.set(path + "." + PathComment.COMMENT, data.expression);
		config.set(path + "." + PathComment.STATE, data.state.toString());
	}
	@Override
	public File getFile() {
		return file;
	}
	@Override
	public YamlConfiguration getConfig() {
		return config;
	}
	@Override
	public void setFile(File file) {
		ConfigHandlerComment.file = file;
	}
	@Override
	public void setConfig(YamlConfiguration config) {
		ConfigHandlerComment.config = config;
	}
	@Override
	public String getFileName() {
		return FILE_NAME;
	}
	@Override
	public String getDirectory() {
		return DIRECTORY;
	}
}
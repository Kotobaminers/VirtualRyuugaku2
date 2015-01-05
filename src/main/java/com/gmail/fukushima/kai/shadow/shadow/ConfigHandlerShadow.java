package com.gmail.fukushima.kai.shadow.shadow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fukushima.kai.mystage.talker.DataManagerTalker;
import com.gmail.fukushima.kai.mystage.talker.Talker;
import com.gmail.fukushima.kai.utilities.utilities.ConfigHandler;

public class ConfigHandlerShadow extends ConfigHandler {
	public static final String DIRECTORY = "SHADOW";
	public static final String FILE_NAME = "SHADOW.yml";
	public enum Path {TALKER}
	public static DataShadowTopic loadDataShadow(String nameTopic) {
		DataShadowTopic data = new DataShadowTopic();
		List<Integer> listId = new ArrayList<Integer>();
		List<String> created = new ArrayList<String>();
		for(String key : config.getKeys(false)) {
			if(key.equalsIgnoreCase(nameTopic)) {
				List<Map<?, ?>> listMap = config.getMapList(nameTopic + "." + Path.TALKER);
				for(Map<?, ?> mapOwner : listMap) {
					for(Object owner : mapOwner.keySet()) {
						String nameOwner = owner.toString();
						Map<?, ?> mapTalker = (Map<?, ?>) mapOwner.get(nameOwner);
						for(Object key2 : mapTalker.keySet()) {
							Integer id = Integer.valueOf(key2.toString());
							Map<?, ?> mapSentence = (Map<?, ?>) mapTalker.get(key2);
							Talker talker = DataManagerShadowTopic.createTalker(id, nameOwner, mapSentence);
							if(0 < talker.listSentence.size()) {
								if(!created.contains(nameOwner)) {
									created.add(nameOwner);
									listId.add(id);
									DataManagerTalker.importTalker(talker);
								}
							}
						}
					}
				}
			}
		}
		data.nameTopic = nameTopic;
		data.listId = listId;
		data.created = created;
		return data;
	}
	@Override
	public void initialize(JavaPlugin plugin) {
		String path = plugin.getDataFolder() + "\\" + DIRECTORY + "\\" +FILE_NAME;
		file = new File(path);
		config = YamlConfiguration.loadConfiguration(file);
	}
	public static void saveDataShadowTopic(DataShadowTopic data) {
		String pathBase = data.nameTopic;
		for(Integer id : data.listId) {
			Talker talker = DataManagerTalker.getTalker(id);
			String path = pathBase + "." + Path.TALKER + "." + talker.owner;
			talker.saveAtConfig(config, path);//TODO
		}
//		save();
	}
}
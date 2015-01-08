package com.gmail.fukushima.kai.shadow.shadow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fukushima.kai.mystage.talker.DataManagerTalker;
import com.gmail.fukushima.kai.mystage.talker.Talker;
import com.gmail.fukushima.kai.utilities.utilities.ConfigHandler;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class ConfigHandlerShadow extends ConfigHandler {
	public static YamlConfiguration config;
	public static File file;
	public static final String DIRECTORY = "SHADOW";
	public static final String FILE_NAME = "SHADOW.yml";
	public enum Path {TALKER}
	public static DataShadowTopic loadDataShadow(String nameTopic) {
		DataShadowTopic data = new DataShadowTopic();
		List<Integer> listId = new ArrayList<Integer>();
		List<String> created = new ArrayList<String>();
		List<PrivateDataShadow> list = PrivateDataShadow.loadPrivateDataShadow(nameTopic);
		for(PrivateDataShadow privateData : list) {
			listId.add(privateData.id);
			created.add(privateData.creator);
		}
		data.nameTopic = nameTopic;
		data.listId = listId;
		data.created = created;
		return data;
	}
	public List<PrivateDataShadow> loadListPrivateDataShadow(String nameTopic) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<PrivateDataShadow> list = new ArrayList<PrivateDataShadow>();
		for(String key : config.getKeys(false)) {
			UtilitiesProgramming.printDebugMessage(key + nameTopic, new Exception());
			if(key.equalsIgnoreCase(nameTopic)) {
				MemorySection memory = (MemorySection) config.get(key);
				for(String talker : memory.getKeys(false)) {
					if(talker.equalsIgnoreCase(Path.TALKER.toString())) {
						UtilitiesProgramming.printDebugMessage(talker, new Exception());
						MemorySection memory2 = (MemorySection) memory.get(talker);
						for(String creator : memory2.getKeys(false)) {
							UtilitiesProgramming.printDebugMessage(creator, new Exception());
							MemorySection memory3 = (MemorySection) memory2.get(creator);
							for(String idString : memory3.getKeys(false)) {
								UtilitiesProgramming.printDebugMessage(idString, new Exception());
								Integer id = Integer.valueOf(idString.toString());
								list.add(new PrivateDataShadow(id, creator));
							}
						}
					}
				}
			}
		}
		return list;
	}
	@Override
	public void initialize(JavaPlugin plugin) {
		String path = plugin.getDataFolder() + "\\" + DIRECTORY + "\\" +FILE_NAME;
		file = new File(path);
		config = YamlConfiguration.loadConfiguration(file);
	}
	public static void saveDataShadowTopic(DataShadowTopic data) {
		String pathBase = data.nameTopic;
		ConfigHandlerShadow handler = new ConfigHandlerShadow();
		for(Integer id : data.listId) {
			Talker talker = DataManagerTalker.getTalker(id);
			String path = pathBase + "." + Path.TALKER + "." + talker.owner;
			talker.saveAtConfig(handler, path);
		}
	}
	@Override
	public File getFile() {
		return file;
	}
	@Override
	public YamlConfiguration getConfig() {
		return config;
	}
	public static class PrivateDataShadow {
		public Integer id;
		public String creator;
		public PrivateDataShadow(Integer id, String creator) {
			this.id = id;
			this.creator = creator;
		}
		public static List<PrivateDataShadow> loadPrivateDataShadow(String nameTopic) {
			List<PrivateDataShadow> list = new ArrayList<PrivateDataShadow>();
			for(String key : config.getKeys(false)) {
				if(key.equalsIgnoreCase(nameTopic)) {
					List<Map<?, ?>> listMap = config.getMapList(nameTopic + "." + Path.TALKER);
					for(Map<?, ?> mapCreator : listMap) {
						for(Object objCreator : mapCreator.keySet()) {
							String creator = objCreator.toString();
							Map<?, ?> mapTalker = (Map<?, ?>) mapCreator.get(creator);
							for(Object key2 : mapTalker.keySet()) {
								Integer id = Integer.valueOf(key2.toString());
								list.add(new PrivateDataShadow(id, creator));
							}
						}
					}
				}
			}
			return list;
		}
	}
}
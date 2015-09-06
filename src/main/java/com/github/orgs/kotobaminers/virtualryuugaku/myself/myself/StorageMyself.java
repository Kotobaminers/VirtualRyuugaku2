package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.YamlController;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.DataManagerPlugin;

public class StorageMyself implements Storage, YamlController{
	public static HashMap<DataKeyMyself, ConversationMyself> mapConversationMyself = new HashMap<DataKeyMyself, ConversationMyself>();
	public static Map<String, List<Integer>> mapMyselfNPC = new HashMap<String, List<Integer>>();
	public static final String FILE = DataManagerPlugin.plugin.getDataFolder() + "//CONFIG//MYSELF.yml";
	private static YamlConfiguration config = null;
	private static final Integer DUMMY_ID = 0;

	@Override
	public void load() {
		importConfiguration();
	}

	@Override
	public void initialize() {
		mapConversationMyself = new HashMap<DataKeyMyself, ConversationMyself>();
		mapMyselfNPC = new HashMap<String, List<Integer>>();
		setConfig();
		load();
	}

	@Override
	public void setData() {

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setConfig() {
		config = YamlConfiguration.loadConfiguration(new File(FILE));
	}

	@Override
	public YamlConfiguration getConfig() {
		return config;
	}

	@Override
	public void importConfiguration() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		MemorySection search = null;
		for(String key : config.getKeys(true)) {
			UtilitiesProgramming.printDebugMessage(key, new Exception());
			//mapMyselfNPC
			if(key.equalsIgnoreCase("MYSELF.ID")) {
				search = (MemorySection) config.get(key);
				for(String stage : search.getKeys(false)) {
					mapMyselfNPC.put(stage, search.getIntegerList(stage));
				}
			}

			//mapConversationMyself
			if(key.equalsIgnoreCase("MYSELF.STAGE")) {
				search = (MemorySection) config.get(key);
				MemorySection memoryStage = null;
				List<String> kanji = new ArrayList<String>();
				List<String> kana = new ArrayList<String>();
				List<String> en = new ArrayList<String>();

				for(String stage : search.getKeys(false)) {
					memoryStage = (MemorySection) search.get(stage);
					MemorySection memoryPlayer = null;
					for(String owner : memoryStage.getKeys(false)) {
						memoryPlayer = (MemorySection) memoryStage.get(owner);
						kanji = memoryPlayer.getStringList("KANJI");
						kana = memoryPlayer.getStringList("KANA");
						en = memoryPlayer.getStringList("EN");
						Integer size = en.size();
						if(size.equals(kana.size()) && size.equals(kana.size())) {
							ConversationMyself conversation = new ConversationMyself();
							conversation.stage = stage;
							conversation.editor = Arrays.asList(owner);
							for(int i = 0; i < size; i++) {
								Description description = Description.create(kanji.get(i), kana.get(i), en.get(i), new ArrayList<String>());
								Talk talk = new Talk().create(DUMMY_ID, owner, description);
								conversation.listTalk.add(talk);
							}
							if(0 < conversation.listTalk.size()) {
								mapConversationMyself.put(new DataKeyMyself(owner, stage), conversation);
							}
						}
					}
				}
			}
		}
	}
}

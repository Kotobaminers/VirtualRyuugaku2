package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.LibraryManager;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.YamlController;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.DataManagerPlugin;

public class StorageConversation implements Storage, YamlController {

	protected static Set<Conversation> conversations = new HashSet<Conversation>();

	public static Map<String, List<Integer>> mapMyselfNPC = new HashMap<String, List<Integer>>();
	public static HashMap<Integer, String> mapMe = new HashMap<Integer, String>();
	public static List<String> teachers = new ArrayList<String>();


	public static final String FILE = DataManagerPlugin.plugin.getDataFolder() + "//CONFIG//CONFIG.yml";
	private static YamlConfiguration config = null;
	private static final Integer DUMMY_ID = 0;

	@Override
	public void load() {
		// TODO Auto-generated method stub
	}

	@Override
	public void load(String key) {
	}

	@Override
	public void initialize() {
		conversations = new HashSet<Conversation>();
		mapMyselfNPC = new HashMap<String, List<Integer>>();
		mapMe = new HashMap<Integer, String>();
		setConfig();
		importConfiguration();
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setData() {
	}

	@Override
	public void setConfig() {
		config = YamlConfiguration.loadConfiguration(new File(FILE));
	}

	@Override
	public YamlConfiguration getConfig() {
		return config;
	}

	public void importMyself() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		MemorySection search = null;
		for(String key : config.getKeys(true)) {
			UtilitiesProgramming.printDebugMessage(key, new Exception());

			//TEACHER
			if(key.equalsIgnoreCase("MYSELF.TEACHER")) {
				teachers.addAll(config.getStringList(key));
			}

			//SETTING
			if(key.equalsIgnoreCase("MYSELF.SETTING")) {
				search = (MemorySection) config.get(key);
				MemorySection stage = null;
				for(String name : search.getKeys(false)) {
					stage = (MemorySection) search.get(name);
					mapMyselfNPC.put(name, stage.getIntegerList("ID"));
					mapMe.put(stage.getInt("ME"), name);
				}
			}

			//CONVERSATION_MYSELF
			if(key.equalsIgnoreCase("MYSELF.CONVERSATION")) {
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
								conversations.add(conversation);
							}
						}
					}
				}
			}
		}

	}

	private void importMulti() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<ConversationMulti> list = new ArrayList<ConversationMulti>();

		Map<String, YamlConfiguration> mapConfig = LibraryManager.getListLibraryStage();
		for(String stage : mapConfig.keySet()) {
			conversations.addAll(LibraryHandlerConversation.importConversationLibrary(stage, mapConfig.get(stage)));
		}
	}

	@Override
	public void importConfiguration() {
		importMyself();
		importMulti();
	}
}

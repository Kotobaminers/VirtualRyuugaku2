package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

	public static Set<Conversation> conversations = new HashSet<Conversation>();

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
		saveMyself();
		try {
			config.save(new File(FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveMyself() {
		Set<ConversationMyself> myselfs = new HashSet<ConversationMyself>();
		for (Conversation conversation : conversations) {
			if (conversation instanceof ConversationMyself) {
				myselfs.add((ConversationMyself) conversation);
			}
		}
		String pathDescription = "MYSELF.DESCRIPTION";
		config.createSection(pathDescription, toMapDescription(myselfs));

		String pathKey = "MYSELF.KEY";
		Map<String, Map<String, List<Integer>>> keys = toMapKey(myselfs);
		config.createSection(pathKey, keys);
	}

	private Map<String, Map<String, List<Integer>>> toMapKey(Set<ConversationMyself> myselfs) {
		Map<String, Map<String, List<Integer>>> stages = new HashMap<String, Map<String,List<Integer>>>();
		for (ConversationMyself myself : myselfs) {
			List<Integer> key = new ArrayList<Integer>();
			for (Talk talk : myself.listTalk) {
				if (talk.key == true) {
					key.add(myself.listTalk.indexOf(talk));
				}
			}
			if (0 < myself.editor.size() && 0 < myself.stageName.length()) {
				String editor = myself.editor.get(0);
				String stageName = myself.stageName;
				Map<String, List<Integer>> tmpPlayers = new HashMap<String, List<Integer>>();
				if (stages.containsKey(stageName)) {
					tmpPlayers = stages.get(stageName);
				}
				tmpPlayers.put(editor, key);
				stages.put(stageName, tmpPlayers);
			}
		}
		return stages;
	}

	private Map<String, Map<String, Map<String, List<String>>>> toMapDescription(Set<ConversationMyself> conversations) {
		Map<String, Map<String, Map<String, List<String>>>> stages = new HashMap<String, Map<String,Map<String,List<String>>>>();

		for (ConversationMyself myself : conversations) {
			Map<String, List<String>> descriptions = new HashMap<String, List<String>>();
			List<String> kanji = new ArrayList<String>();
			List<String> kana = new ArrayList<String>();
			List<String> en = new ArrayList<String>();

			for (Talk talk : myself.listTalk) {
				kanji.addAll(talk.description.kanji);
				kana.addAll(talk.description.kana);
				en.addAll(talk.description.en);
			}
			descriptions.put("KANJI",kanji);
			descriptions.put("KANA",kana);
			descriptions.put("EN", en);

			if (0 < myself.editor.size() && 0 < myself.stageName.length()) {
				String editor = myself.editor.get(0);
				String stageName = myself.stageName;
				Map<String, Map<String, List<String>>> tmpPlayers = new HashMap<String, Map<String,List<String>>>();
				if (stages.containsKey(stageName)) {
					tmpPlayers = stages.get(stageName);
				}
				tmpPlayers.put(editor, descriptions);
				stages.put(stageName, tmpPlayers);
 			}
		}
		return stages;
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

	private Set<ConversationMyself> toConversationMyself(Map<String, Map<String, List<Integer>>> mapKeys, Map<String, Map<String, Map<String, List<String>>>> mapDescriptions) {
		Set<ConversationMyself> conversations = new HashSet<ConversationMyself>();
		for (Entry<String, Map<String, Map<String, List<String>>>> entryStages : mapDescriptions.entrySet()) {
			String stage = entryStages.getKey();
			Map<String, Map<String, List<String>>> mapPlayers = entryStages.getValue();
			for (Entry<String, Map<String, List<String>>> entryPlayers : mapPlayers.entrySet()) {
				String player = entryPlayers.getKey();
				List<String> en = entryPlayers.getValue().get("EN");
				List<String> kanji = entryPlayers.getValue().get("KANJI");
				List<String> kana = entryPlayers.getValue().get("KANA");
				Integer size = en.size();
				if (mapKeys.containsKey(stage)) {
					Map<String, List<Integer>> mapStagesKey = mapKeys.get(stage);
					if (mapStagesKey.containsKey(player)) {
						List<Integer> keys = mapStagesKey.get(player);
						ConversationMyself myself = new ConversationMyself();
						myself.stageName = stage;
						myself.editor = Arrays.asList(player);
						if(size.equals(kana.size()) && size.equals(kanji.size())) {
							for(int i = 0; i < size; i++) {
								Description description = Description.create(kanji.get(i), kana.get(i), en.get(i), new ArrayList<String>());
								Talk talk = new Talk().create(DUMMY_ID, player, description);
								if (keys.contains(i)) {
									talk.key = true;
								}
								myself.listTalk.add(talk);
							}
							if(0 < myself.listTalk.size()) {
								conversations.add(myself);
							}
						}
					}
				}

			}
		}
		return conversations;
	}

	public void importMyself() {
		UtilitiesProgramming.printDebugMessage("", new Exception());


		Map<String, Map<String, List<Integer>>> mapKeys = new HashMap<String, Map<String,List<Integer>>>();
		Map<String, Map<String, Map<String, List<String>>>> mapDescriptions = new HashMap<String, Map<String,Map<String,List<String>>>>();
		for(String key : config.getKeys(true)) {
			UtilitiesProgramming.printDebugMessage(key, new Exception());

			if(key.equalsIgnoreCase("MYSELF.TEACHER")) {
				teachers.addAll(config.getStringList(key));
			}

			if(key.equalsIgnoreCase("MYSELF.SETTING")) {
				MemorySection search = (MemorySection) config.get(key);
				MemorySection stage = null;
				for(String name : search.getKeys(false)) {
					stage = (MemorySection) search.get(name);
					mapMyselfNPC.put(name, stage.getIntegerList("ID"));
					mapMe.put(stage.getInt("ME"), name);
				}
			}

			if (key.equalsIgnoreCase("MYSELF.KEY")) {
				MemorySection stages = (MemorySection) config.get(key);
				for (String stage : stages.getKeys(false)) {
					MemorySection players = (MemorySection) stages.get(stage);
					for (String player : players.getKeys(false)) {
						Map<String, List<Integer>> mapPlayers = new HashMap<String, List<Integer>>();
						if (mapKeys.containsKey(stage)) {
							mapPlayers = mapKeys.get(stage);
						}
						mapPlayers.put(player, players.getIntegerList(player));
						mapKeys.put(stage, mapPlayers);
					}
				}
			}

			List<String> descriptionKeys = Arrays.asList("EN", "KANJI", "KANA");
			if(key.equalsIgnoreCase("MYSELF.DESCRIPTION")) {
				MemorySection stages = (MemorySection) config.get(key);
				for(String stage : stages.getKeys(false)) {
					MemorySection players = (MemorySection) stages.get(stage);
					for(String owner : players.getKeys(false)) {
						MemorySection descriptions = (MemorySection) players.get(owner);
						Map<String, List<String>> map = new HashMap<String, List<String>>();
						for (String descriptionKey : descriptionKeys) {
							map.put(descriptionKey, descriptions.getStringList(descriptionKey));
						}
						Map<String, Map<String, List<String>>> map2 = new HashMap<String, Map<String, List<String>>>();
						if (mapDescriptions.containsKey(stage)) {
							map2 = mapDescriptions.get(stage);
						}
						map2.put(owner, map);
						mapDescriptions.put(stage, map2);
					}
				}
			}
		}
		Set<ConversationMyself> myselfs = toConversationMyself(mapKeys, mapDescriptions);
		StorageConversation.conversations.addAll(myselfs);

	}


			//CONVERSATION_MYSELF
//			if(key.equalsIgnoreCase("MYSELF.DESCRIPTION")) {
//				search = (MemorySection) config.get(key);
//				MemorySection memoryStage = null;
//				List<String> kanji = new ArrayList<String>();
//				List<String> kana = new ArrayList<String>();
//				List<String> en = new ArrayList<String>();
//
//				for(String stage : search.getKeys(false)) {
//					memoryStage = (MemorySection) search.get(stage);
//					MemorySection memoryPlayer = null;
//					for(String owner : memoryStage.getKeys(false)) {
//						memoryPlayer = (MemorySection) memoryStage.get(owner);
//						kanji = memoryPlayer.getStringList("KANJI");
//						kana = memoryPlayer.getStringList("KANA");
//						en = memoryPlayer.getStringList("EN");
//						Integer size = en.size();
//						if(size.equals(kana.size()) && size.equals(kana.size())) {
//							ConversationMyself conversation = new ConversationMyself();
//							conversation.stageName = stage;
//							conversation.editor = Arrays.asList(owner);
//							for(int i = 0; i < size; i++) {
//								Description description = Description.create(kanji.get(i), kana.get(i), en.get(i), new ArrayList<String>());
//								Talk talk = new Talk().create(DUMMY_ID, owner, description);
//								conversation.listTalk.add(talk);
//							}
//							if(0 < conversation.listTalk.size()) {
//								conversations.add(conversation);
//							}
//						}
//					}
//				}
//			}

	private void importMulti() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
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

package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.PathConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Romaji;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.VRGManager;

public class StageStorage implements Storage {

	static Set<Stage> stages = new HashSet<Stage>();
	public static final String CITIZENS_FILE = Bukkit.getPluginManager().getPlugin("Citizens").getDataFolder() + "//saves.yml";
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(CITIZENS_FILE));

	public static Map<String, YamlConfiguration> getListLibraryStage() {
		Map<String, YamlConfiguration> list = new HashMap<String, YamlConfiguration>();
		File stage = new File(VRGManager.plugin.getDataFolder() + "//STAGE");
		File[] files = stage.listFiles();
		for(File file : files) {
			if(file.getAbsolutePath().endsWith(".yml")) {
				String name = file.getName().substring(0, file.getName().length() - ".yml".length());
				list.put(name, YamlConfiguration.loadConfiguration(file));
			}
		}
		return list;
	}

	public static Set<Stage> getStages() {
		return stages;
	}

	public static Optional<Stage> findStage(final String name) {
		return getStages().stream()
			.filter(stage -> stage.name.equalsIgnoreCase(name))
			.findFirst();
	}

	public static Set<String> getStageNames() {
		Set<String> names = new HashSet<String>();
		for (Stage stage: stages) {
			names.add(stage.name);
		}
		return names;
	}

	@Override
	public void initialize() {
		stages = new HashSet<Stage>();
		importStages();
	}

	@Override
	public void save() {
	}

	private void importStages() {
		stages = new HashSet<Stage>();
		Map<String, YamlConfiguration> mapConfig = getListLibraryStage();
		Set<Integer> ids = importCitizensIds();
		for(Entry<String, YamlConfiguration> entry : mapConfig.entrySet()) {
			stages.add(importStage(entry.getKey(), entry.getValue()));
		}

		for (Stage stage : stages) {
			for (List<Integer> index : stage.npcConversations.keySet()) {
				for (Integer id : index) {
					if (!ids.contains(id)) {
						stage.npcConversations.remove(index);
					}
				}
			}
		}
	}
	private enum PathStage {CONVERSATION, EDITOR, LEARNER_NPC, LEARNER_QUESTION}

	public static Stage importStage(String stageName, YamlConfiguration library) {
		Stage stage = new Stage();

		stage.name = stageName;
		library.getStringList(PathStage.EDITOR.toString())
			.forEach(editor -> stage.editor.add(UUID.fromString(editor)));
		library.getIntegerList(PathStage.LEARNER_NPC.toString())
			.forEach(id -> stage.displayNPC.put(id, new LearnerConversation()));
		stage.learnerQuestions = library.getStringList(PathStage.LEARNER_QUESTION.toString());

		for(String talkerPath : library.getKeys(false)) {
			if(talkerPath.equalsIgnoreCase(PathStage.CONVERSATION.toString())) {
				MemorySection memory = (MemorySection) library.get(talkerPath);
				for(String idString : memory.getKeys(false)) {
					MemorySection memoryId = (MemorySection) memory.get(idString);
					List<Integer> index = Utility.toListInteger(idString);
					NPCConversation conversation = new NPCConversation();
					conversation.stageName = stageName;
					//Name will be imported from citizens data.
					if(memoryId.contains(Enums.PathConversation.EN.toString()) && memoryId.contains(Enums.PathConversation.KANJI.toString()) && memoryId.contains(Enums.PathConversation.KANA.toString())) {
						List<String> kanji = memoryId.getStringList(PathConversation.KANJI.toString());
						List<String> kana = memoryId.getStringList(PathConversation.KANA.toString());
						List<String> en = memoryId.getStringList(PathConversation.EN.toString());
						Integer size = index.size();
						if(size.equals(kanji.size()) && size.equals(kana.size()) && size.equals(en.size())) {
							for(int i = 0; i < size; i++) {
								Integer id = index.get(i);
								Description description = Description.create(kanji.get(i), kana.get(i), en.get(i), new ArrayList<String>());
								VRGSentence talk = VRGSentence.create(id, description);
								conversation.sentences.add(talk);
							}
						} else {
							break;
						}
					}

					String path = Enums.PathConversation.KEY.toString();
					if(memoryId.contains(path)) {
						List<Integer> key = memoryId.getIntegerList(path);
						for (int i = 0; i < conversation.sentences.size(); i++) {
							if(key.contains(i + 1)) {
								conversation.sentences.get(i).key = true;
							}
						}
					}

					if(memoryId.contains(PathConversation.Q.toString()) && memoryId.contains(PathConversation.A.toString())) {
						String q = memoryId.getString(PathConversation.Q.toString());
						List<String> a = Romaji.addRomaji(memoryId.getStringList(PathConversation.A.toString()));
						conversation.question = ConversationQuestion.create(q, a, index, stageName);
					}
					conversation.index = index;
					stage.npcConversations.put(index, conversation);
				}
			}
		}
		return stage;
	}

	private static Set<Integer> importCitizensIds() {
		Debug.printDebugMessage("", new Exception());
		Set<Integer> ids = new HashSet<Integer>();
		MemorySection memory = (MemorySection) config.get("npc");
		for(String key : memory.getKeys(false)) {
			try {
				Integer id = Integer.parseInt(key);
				ids.add(id);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return ids;
	}

	public static void printDebugMessage() {
		for (Stage stage : stages) {
			stage.printDebugMessage();
		}
	}



}

//	public static Map<String, List<Integer>> mapMyselfNPC = new HashMap<String, List<Integer>>();
//	public static HashMap<Integer, String> mapMe = new HashMap<Integer, String>();
//	public static List<String> teachers = new ArrayList<String>();


//	public static final String FILE = DataManagerPlugin.plugin.getDataFolder() + "//CONFIG//CONFIG.yml";
//	private static YamlConfiguration config = null;
//	private static final Integer DUMMY_ID = 0;

//	private void saveMyself() {
//		Set<ConversationMyself> myselfs = new HashSet<ConversationMyself>();
//		for (Conversation conversation : conversations) {
//			if (conversation instanceof ConversationMyself) {
//				myselfs.add((ConversationMyself) conversation);
//			}
//		}
//		String pathDescription = "MYSELF.DESCRIPTION";
//		config.createSection(pathDescription, toMapDescription(myselfs));
//
//		String pathKey = "MYSELF.KEY";
//		Map<String, Map<String, List<Integer>>> keys = toMapKey(myselfs);
//		config.createSection(pathKey, keys);
//	}

//	private Map<String, Map<String, List<Integer>>> toMapKey(Set<ConversationMyself> myselfs) {
//		Map<String, Map<String, List<Integer>>> stages = new HashMap<String, Map<String,List<Integer>>>();
//		for (ConversationMyself myself : myselfs) {
//			List<Integer> key = new ArrayList<Integer>();
//			for (Talk talk : myself.listTalk) {
//				if (talk.key == true) {
//					key.add(myself.listTalk.indexOf(talk));
//				}
//			}
//			if (0 < myself.editor.size() && 0 < myself.stageName.length()) {
//				String editor = myself.editor.get(0);
//				String stageName = myself.stageName;
//				Map<String, List<Integer>> tmpPlayers = new HashMap<String, List<Integer>>();
//				if (stages.containsKey(stageName)) {
//					tmpPlayers = stages.get(stageName);
//				}
//				tmpPlayers.put(editor, key);
//				stages.put(stageName, tmpPlayers);
//			}
//		}
//		return stages;
//	}

//	private Map<String, Map<String, Map<String, List<String>>>> toMapDescription(Set<ConversationMyself> conversations) {
//		Map<String, Map<String, Map<String, List<String>>>> stages = new HashMap<String, Map<String,Map<String,List<String>>>>();
//
//		for (ConversationMyself myself : conversations) {
//			Map<String, List<String>> descriptions = new HashMap<String, List<String>>();
//			List<String> kanji = new ArrayList<String>();
//			List<String> kana = new ArrayList<String>();
//			List<String> en = new ArrayList<String>();
//
//			for (Talk talk : myself.listTalk) {
//				kanji.addAll(talk.description.kanji);
//				kana.addAll(talk.description.kana);
//				en.addAll(talk.description.en);
//			}
//			descriptions.put("KANJI",kanji);
//			descriptions.put("KANA",kana);
//			descriptions.put("EN", en);
//
//			if (0 < myself.editor.size() && 0 < myself.stageName.length()) {
//				String editor = myself.editor.get(0);
//				String stageName = myself.stageName;
//				Map<String, Map<String, List<String>>> tmpPlayers = new HashMap<String, Map<String,List<String>>>();
//				if (stages.containsKey(stageName)) {
//					tmpPlayers = stages.get(stageName);
//				}
//				tmpPlayers.put(editor, descriptions);
//				stages.put(stageName, tmpPlayers);
// 			}
//		}
//		return stages;
//	}

//	private Set<ConversationMyself> toConversationMyself(Map<String, Map<String, List<Integer>>> mapKeys, Map<String, Map<String, Map<String, List<String>>>> mapDescriptions) {
//		Set<ConversationMyself> conversations = new HashSet<ConversationMyself>();
//		for (Entry<String, Map<String, Map<String, List<String>>>> entryStages : mapDescriptions.entrySet()) {
//			String stage = entryStages.getKey();
//			Map<String, Map<String, List<String>>> mapPlayers = entryStages.getValue();
//			for (Entry<String, Map<String, List<String>>> entryPlayers : mapPlayers.entrySet()) {
//				String player = entryPlayers.getKey();
//				List<String> en = entryPlayers.getValue().get("EN");
//				List<String> kanji = entryPlayers.getValue().get("KANJI");
//				List<String> kana = entryPlayers.getValue().get("KANA");
//				Integer size = en.size();
//				if (mapKeys.containsKey(stage)) {
//					Map<String, List<Integer>> mapStagesKey = mapKeys.get(stage);
//					if (mapStagesKey.containsKey(player)) {
//						List<Integer> keys = mapStagesKey.get(player);
//						ConversationMyself myself = new ConversationMyself();
//						myself.stageName = stage;
//						myself.editor = Arrays.asList(player);
//						if(size.equals(kana.size()) && size.equals(kanji.size())) {
//							for(int i = 0; i < size; i++) {
//								Description description = Description.create(kanji.get(i), kana.get(i), en.get(i), new ArrayList<String>());
//								Talk talk = new Talk().create(DUMMY_ID, player, description);
//								if (keys.contains(i)) {
//									talk.key = true;
//								}
//								myself.listTalk.add(talk);
//							}
//							if(0 < myself.listTalk.size()) {
//								conversations.add(myself);
//							}
//						}
//					}
//				}
//
//			}
//		}
//		return conversations;
//	}

//	public void importMyself() {
//		UtilitiesProgramming.printDebugMessage("", new Exception());
//
//
//		Map<String, Map<String, List<Integer>>> mapKeys = new HashMap<String, Map<String,List<Integer>>>();
//		Map<String, Map<String, Map<String, List<String>>>> mapDescriptions = new HashMap<String, Map<String,Map<String,List<String>>>>();
//		for(String key : config.getKeys(true)) {
//			UtilitiesProgramming.printDebugMessage(key, new Exception());
//
//			if(key.equalsIgnoreCase("MYSELF.TEACHER")) {
//				teachers.addAll(config.getStringList(key));
//			}
//
//			if(key.equalsIgnoreCase("MYSELF.SETTING")) {
//				MemorySection search = (MemorySection) config.get(key);
//				MemorySection stage = null;
//				for(String name : search.getKeys(false)) {
//					stage = (MemorySection) search.get(name);
//					mapMyselfNPC.put(name, stage.getIntegerList("ID"));
//					mapMe.put(stage.getInt("ME"), name);
//				}
//			}
//
//			if (key.equalsIgnoreCase("MYSELF.KEY")) {
//				MemorySection stages = (MemorySection) config.get(key);
//				for (String stage : stages.getKeys(false)) {
//					MemorySection players = (MemorySection) stages.get(stage);
//					for (String player : players.getKeys(false)) {
//						Map<String, List<Integer>> mapPlayers = new HashMap<String, List<Integer>>();
//						if (mapKeys.containsKey(stage)) {
//							mapPlayers = mapKeys.get(stage);
//						}
//						mapPlayers.put(player, players.getIntegerList(player));
//						mapKeys.put(stage, mapPlayers);
//					}
//				}
//			}
//
//			List<String> descriptionKeys = Arrays.asList("EN", "KANJI", "KANA");
//			if(key.equalsIgnoreCase("MYSELF.DESCRIPTION")) {
//				MemorySection stages = (MemorySection) config.get(key);
//				for(String stage : stages.getKeys(false)) {
//					MemorySection players = (MemorySection) stages.get(stage);
//					for(String owner : players.getKeys(false)) {
//						MemorySection descriptions = (MemorySection) players.get(owner);
//						Map<String, List<String>> map = new HashMap<String, List<String>>();
//						for (String descriptionKey : descriptionKeys) {
//							map.put(descriptionKey, descriptions.getStringList(descriptionKey));
//						}
//						Map<String, Map<String, List<String>>> map2 = new HashMap<String, Map<String, List<String>>>();
//						if (mapDescriptions.containsKey(stage)) {
//							map2 = mapDescriptions.get(stage);
//						}
//						map2.put(owner, map);
//						mapDescriptions.put(stage, map2);
//					}
//				}
//			}
//		}
//		Set<ConversationMyself> myselfs = toConversationMyself(mapKeys, mapDescriptions);
//		StorageConversation.conversations.addAll(myselfs);
//	}
//}

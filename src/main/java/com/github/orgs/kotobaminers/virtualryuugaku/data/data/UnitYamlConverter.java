package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.PathConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.VRGManager;

public class UnitYamlConverter {
	enum UnitPath {CONVERSATION, EDITOR, LEARNER_NPC, LEARNER_QUESTION, KEY, Q, A}
	enum LearnerSentencePath {LEARNER_SENTENCE, STAGE, NAME}
	enum SentencePath {KANJI, KANA, EN}
	enum ConfigPath {ONLINE_NPCS}

	private static final File UNIT_DIR = new File(VRGManager.plugin.getDataFolder() + "//STAGE");
	private static final File CONFIG_FILE = new File(VRGManager.plugin.getDataFolder() + "//CONFIG//CONFIG.yml");
	private static final YamlConfiguration config = YamlConfiguration.loadConfiguration(CONFIG_FILE);

	//Imporer
	public static void importAll() {
		importUnits();
		importPlayerSentences();
		importOnlineNPCs();
	}

	private static void importOnlineNPCs() {
		OnlinePlayerNPCs.setOnlineIds(config.getIntegerList(ConfigPath.ONLINE_NPCS.name()));
	}
	private static final void importUnits() {
		Arrays.asList(UNIT_DIR.listFiles()).stream()
			.filter(file -> file.getAbsolutePath().endsWith(".yml"))
			.forEach(file -> importUnit(YamlConfiguration.loadConfiguration(file), extractName(file)));
	}

	private static void importUnit(YamlConfiguration config, String name) {
		name = name.toUpperCase();
		Unit unit = UnitStorage.units.getOrDefault(name, new Unit(name));
		Optional<ConfigurationSection> conversationSection = Stream.of(config)
			.filter(config2 -> config2.isConfigurationSection(UnitPath.CONVERSATION.toString()))
			.map(config2 -> (ConfigurationSection) config2.get(UnitPath.CONVERSATION.toString()))
			.findFirst();

		Stream.of(config).filter(c -> c.isList(UnitPath.LEARNER_QUESTION.name()))
			.findFirst().map(c -> c.getStringList(UnitPath.LEARNER_QUESTION.name()))
			.ifPresent(qs -> {
				List<String> playerQuestions = new ArrayList<>();
				playerQuestions.addAll(qs);
				for(int i = 0; i < Unit.MAX_SENTENCE - qs.size(); i++) {
					playerQuestions.add(Unit.TALK_FREELY);
				}
				unit.setPlayerQuestions(playerQuestions);
			});

		Stream.of(config).filter(c -> c.isList(UnitPath.LEARNER_NPC.name()))
			.findFirst().map(c -> Utility.toListInteger(c.getString(UnitPath.LEARNER_NPC.name())))
			.ifPresent(ids -> unit.setPlayerNPCIds(ids));

		List<List<HolographicSentence>> listlist = new ArrayList<>();
		conversationSection.ifPresent(section ->
			section.getKeys(false).stream()
			.forEach(key -> {
				List<HelperSentence> list= new ArrayList<>();
				ConfigurationSection section2 = (ConfigurationSection) section.get(key);
				HelperSentence.create(
					section2.getStringList(SentencePath.KANJI.name()),
					section2.getStringList(SentencePath.KANA.name()),
					section2.getStringList(SentencePath.EN.name()),
					Utility.toListInteger(key)
				).ifPresent(present -> list.addAll(present));
				List<Boolean> keys = section2.getBooleanList(PathConversation.KEY.name());
				for(int i = 0; i < keys.size(); i++) {
					list.get(i).setKey(keys.get(i));
				}
				if(0 < list.size()) {
					List<HolographicSentence> sentences = new ArrayList<HolographicSentence>();
					sentences.addAll(list);
					QuestionSentence.create(section2, Utility.toListInteger(key)).ifPresent(q -> sentences.add(q));
					listlist.add(sentences);
				}
			})
		);
		listlist.stream().forEach(ls -> ls.forEach(s -> s.setUnitName(unit.getName())));
		unit.setHelperSentences(listlist);
		UnitStorage.units.put(name, unit);
		return;
	}

	private static final String extractName(File file) {
		return file.getName().substring(0,  file.getName().length() - ".yml".length());
	}

	public static void importPlayerSentences() {
		if (!isValidConfig()) {
			System.out.println("Invalid CONFIG.yml");
			return;
		}
		MemorySection uuidSection = (MemorySection) config.get(LearnerSentencePath.LEARNER_SENTENCE.name());
		uuidSection.getKeys(false).stream()
			.forEach(uuidString -> {
				UUID uuid = UUID.fromString(uuidString);
				MemorySection unitSection = (MemorySection) uuidSection.get(uuidString + "." + LearnerSentencePath.STAGE.name());
				String name = uuidSection.getString(uuidString + "." + LearnerSentencePath.NAME.name());
				unitSection.getKeys(false).stream()
					.forEach(unitName -> {
						Unit unit = UnitStorage.units.getOrDefault(unitName, new Unit(unitName));
						Optional.of(unit.getPlayerQuestions()).ifPresent(questions -> {
							List<HolographicSentence> list = new ArrayList<>();
							MemorySection sentencesSection = (MemorySection) unitSection.get(unitName);
							List<String> kanji = sentencesSection.getStringList(SentencePath.KANJI.name());
							List<String> kana = sentencesSection.getStringList(SentencePath.KANA.name());
							List<String> en = sentencesSection.getStringList(SentencePath.EN.name());
							for (int i = 0; i < Unit.MAX_SENTENCE; i++) {
								if (i < kanji.size() && i < kana.size() && i < en.size()) {
									list.add(new PlayerSentence(
										Arrays.asList(kanji.get(i)),
										Arrays.asList(kana.get(i)),
										Arrays.asList(en.get(i)),
										uuid,
										name));
								} else {
									list.add(PlayerSentence.createEmpty(uuid, name));
								}
							}
							if (0 < list.size()) {
								unit.getPlayerSentences().add(list);
							}
						});
					});
			});
//		SentenceStorage.learnerSentences.values().stream()
//			.forEach(lls -> lls.forEach(ls -> ls.stream().forEach(s -> System.out.println(s.getHolographicLines(Arrays.asList(SpellType.KANJI, SpellType.KANA, SpellType.ROMAJI, SpellType.EN))))));
	}

	private static boolean isValidConfig() {
		if(config.isConfigurationSection(LearnerSentencePath.LEARNER_SENTENCE.name())) {
			return true;
		}
		return false;
	}

	public static void save() {
		UnitStorage.units.values()
			.forEach(unit -> saveUnit(unit));
		savePlayerSentences();
	}

//	private static void disableNotExistingUnits() {
//		SentenceStorage.helperSentences.keySet().stream().forEach(System.out::println);
//		Stream.of(UNIT_DIR.listFiles())
//			.filter(file -> file.getName().endsWith(".yml"))
//			.filter(file ->
//				!SentenceStorage.helperSentences.keySet().contains(file.getName().substring(0,  file.getName().length() - ".yml".length()).toUpperCase()))
//			.forEach(file -> file.renameTo(new File(file.getAbsolutePath() + "_disabled")));
//	}

	private static void saveUnit(Unit unit) {
		File file = new File(UNIT_DIR + "//" + unit.getName() + ".yml");
		YamlConfiguration yaml = new YamlConfiguration();
		Map<List<String>, Map<String, List<String>>> conversations = new HashMap<>();

		unit.getHelperSentences().stream().forEach(ls -> {
			yaml.set(UnitPath.LEARNER_NPC.name(), unit.getPlayerNPCIds());
			yaml.set(UnitPath.LEARNER_QUESTION.name(), unit.getPlayerQuestions());

			List<HelperSentence> lh = ls.stream().filter(s -> s instanceof HelperSentence)
			.map(s -> (HelperSentence) s).collect(Collectors.toList());

			Map<String, List<String>> sentence = new HashMap<String, List<String>>();
			sentence.put(PathConversation.EN.name(), lh.stream().map(h -> h.getLines(SpellType.EN).get().get(0)).collect(Collectors.toList()));
			sentence.put(PathConversation.KANJI.name(), lh.stream().map(h -> h.getLines(SpellType.KANJI).get().get(0)).collect(Collectors.toList()));
			sentence.put(PathConversation.KANA.name(), lh.stream().map(h -> h.getLines(SpellType.KANA).get().get(0)).collect(Collectors.toList()));
			sentence.put(PathConversation.KEY.name(), lh.stream().map(h -> String.valueOf(h.isKey())).collect(Collectors.toList()));

			Optional<QuestionSentence> q = ls.stream().filter(s -> s instanceof QuestionSentence)
				.map(q2 -> (QuestionSentence) q2)
				.findFirst();
			q.ifPresent(q2 -> sentence.put(PathConversation.Q.name(), Arrays.asList(q2.getQuestion())));
			q.ifPresent(q2 -> sentence.put(PathConversation.A.name(), q2.getAnswers()));

			List<String> ids = lh.stream().map(h -> h.getId().toString()).collect(Collectors.toList());
			conversations.put(ids, sentence);
		});
		yaml.createSection(UnitPath.CONVERSATION.name(), conversations);

		try {
			yaml.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void savePlayerSentences() {
		Map<String, Map<String, Map<String, Map<String, List<String>>>>> uuids = new HashMap<>();
		Map<String, String> names = new HashMap<String,String>();

		UnitStorage.units.values().stream()
			.forEach(unit -> {
				List<String> uuid = new ArrayList<>();
				List<String> name = new ArrayList<>();
				String stage = unit.getName();
				List<List<PlayerSentence>> sentence = unit.getPlayerSentences().stream().map(ls -> ls.stream().map(s -> (PlayerSentence) s).collect(Collectors.toList())).collect(Collectors.toList());
				sentence.stream().forEach(ls -> {
					if(!ls.stream().allMatch(s -> TalkSentence.isEmpty(s))) {
						List<String> kanji = new ArrayList<String>();
						List<String> kana = new ArrayList<String>();
						List<String> en = new ArrayList<String>();
						ls.forEach(s -> {
							kanji.add(s.getJapanese().getLine(Arrays.asList(SpellType.KANJI)).get().get(0));
							kana.add(s.getJapanese().getLine(Arrays.asList(SpellType.KANA)).get().get(0));
							en.add(s.getEnglish().getLine().get(0));
							if (!uuid.contains(s.getUniqueId())) {
								uuid.add(s.getUniqueId().toString());
							}
							if (!name.contains(s.getDisplayName())) {
								name.add(s.getDisplayName());
							}
						});
						uuids.computeIfAbsent(uuid.get(0), initial -> new HashMap<String, Map<String, Map<String, List<String>>>>());
						uuids.get(uuid.get(0)).computeIfAbsent(LearnerSentencePath.STAGE.name(), initial -> new HashMap<String, Map<String, List<String>>>());
						uuids.get(uuid.get(0)).get(LearnerSentencePath.STAGE.name()).computeIfAbsent(stage, initial -> new HashMap<String, List<String>>());

						uuids.get(uuid.get(0)).get(LearnerSentencePath.STAGE.name()).get(stage).put(SentencePath.KANJI.name(), kanji);
						uuids.get(uuid.get(0)).get(LearnerSentencePath.STAGE.name()).get(stage).put(SentencePath.KANA.name(), kana);
						uuids.get(uuid.get(0)).get(LearnerSentencePath.STAGE.name()).get(stage).put(SentencePath.EN.name(), en);

						names.put(uuid.get(0), name.get(0));
						uuid.clear();
						name.clear();
					}
				});
			});
		YamlConfiguration yaml = config;
		yaml.createSection(LearnerSentencePath.LEARNER_SENTENCE.name(), uuids);
		names.entrySet().forEach(entry -> yaml.set(LearnerSentencePath.LEARNER_SENTENCE + "." + entry.getKey() + "." + LearnerSentencePath.NAME.name(), entry.getValue()));
		try {
			yaml.save(CONFIG_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


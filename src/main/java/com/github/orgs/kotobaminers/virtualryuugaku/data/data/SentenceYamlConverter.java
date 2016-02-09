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

public class SentenceYamlConverter {
	enum PathStage {CONVERSATION, EDITOR, LEARNER_NPC, LEARNER_QUESTION, KEY, Q, A}
	enum LearnerSentencePath {LEARNER_SENTENCE, STAGE, NAME}
	enum SentencePath {KANJI, KANA, EN}

	private static final File UNIT_DIR = new File(VRGManager.plugin.getDataFolder() + "//STAGE");
	private static final File CONFIG_FILE = new File(VRGManager.plugin.getDataFolder() + "//CONFIG//CONFIG.yml");
	private static final YamlConfiguration config = YamlConfiguration.loadConfiguration(CONFIG_FILE);

	//Imporer
	public static final void importOwnerSentences() {
		Arrays.asList(UNIT_DIR.listFiles()).stream()
			.filter(file -> file.getAbsolutePath().endsWith(".yml"))
			.forEach(file -> importUnit(YamlConfiguration.loadConfiguration(file), extractName(file)));
	}

	private static void importUnit(YamlConfiguration config, String name) {
		Unit unit = new Unit();
		name = name.toUpperCase();
		Optional<ConfigurationSection> conversationSection = Stream.of(config)
			.filter(config2 -> config2.isConfigurationSection(PathStage.CONVERSATION.toString()))
			.map(config2 -> (ConfigurationSection) config2.get(PathStage.CONVERSATION.toString()))
			.findFirst();

		Stream.of(config).filter(c -> c.isList(PathStage.LEARNER_QUESTION.name()))
			.findFirst().map(c -> c.getStringList(PathStage.LEARNER_QUESTION.name()))
			.ifPresent(qs -> unit.setPlayerQuestions(qs));

		Stream.of(config).filter(c -> c.isList(PathStage.LEARNER_NPC.name()))
			.findFirst().map(c -> Utility.toListInteger(c.getString(PathStage.LEARNER_NPC.name())))
			.ifPresent(ids -> unit.setPlayerNPCIds(ids));

		List<List<HolographicSentence>> listlist = new ArrayList<>();
		conversationSection.ifPresent(section ->
			section.getKeys(false).stream()
			.forEach(key -> {
				List<HolographicSentence> list= new ArrayList<>();
				ConfigurationSection section2 = (ConfigurationSection) section.get(key);
				HelperSentence.create(
					section2.getStringList(SentencePath.KANJI.toString()),
					section2.getStringList(SentencePath.KANA.toString()),
					section2.getStringList(SentencePath.EN.toString()),
					Utility.toListInteger(key)
				).ifPresent(present -> list.addAll(present));
				QuestionSentence.create(section2, Utility.toListInteger(key)).ifPresent(q -> list.add(q));
				if(0 < list.size()) {
					listlist.add(list);
				}
			})
		);
		unit.setHelperSentences(listlist);
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
				MemorySection stageSection = (MemorySection) uuidSection.get(uuidString + "." + LearnerSentencePath.STAGE.name());
				String name = uuidSection.getString(uuidString + "." + LearnerSentencePath.NAME.name());
				stageSection.getKeys(false).stream()
					.forEach(unitName -> {
						Unit unit = SentenceStorage.units.getOrDefault(unitName, new Unit());
						Optional.of(unit.getPlayerQuestions()).ifPresent(questions -> {
							List<HolographicSentence> list = new ArrayList<>();
							MemorySection sentencesSection = (MemorySection) stageSection.get(unitName);
							List<String> kanji = sentencesSection.getStringList(SentencePath.KANJI.name());
							List<String> kana = sentencesSection.getStringList(SentencePath.KANA.name());
							List<String> en = sentencesSection.getStringList(SentencePath.EN.name());
							for (int i = 0; i < questions.size(); i++) {
								if (i < kanji.size() && i < kana.size() && i < en.size()) {
									list.add(new PlayerSentence(
											Arrays.asList(kanji.get(i)),
											Arrays.asList(kana.get(i)),
											Arrays.asList(en.get(i)),
											uuid,
											questions.get(i),
											name));
								} else {
									list.add(PlayerSentence.createEmpty(uuid, name, questions.get(i)));
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
		SentenceStorage.units.entrySet()
			.forEach(entry -> saveUnit(entry.getKey(), entry.getValue()));
//		savePlayerSentences();
//		disableNotExistingUnits();
	}


//	private static void disableNotExistingUnits() {
//		SentenceStorage.helperSentences.keySet().stream().forEach(System.out::println);
//		Stream.of(UNIT_DIR.listFiles())
//			.filter(file -> file.getName().endsWith(".yml"))
//			.filter(file ->
//				!SentenceStorage.helperSentences.keySet().contains(file.getName().substring(0,  file.getName().length() - ".yml".length()).toUpperCase()))
//			.forEach(file -> file.renameTo(new File(file.getAbsolutePath() + "_disabled")));
//	}

	private static void saveUnit(String stage, Unit unit) {
		File file = new File(UNIT_DIR + "//" + stage + ".yml");
		YamlConfiguration yaml = new YamlConfiguration();
		Map<List<String>, Map<String, List<String>>> map2 = new HashMap<>();

		unit.getPlayerSentences().stream().forEach(ls -> {
		yaml.set(PathStage.LEARNER_NPC.name(), unit.getPlayerNPCIds());
		yaml.set(PathStage.LEARNER_QUESTION.name(), unit.getPlayerQuestions());

		List<HelperSentence> lo = ls.stream().filter(s -> s instanceof HelperSentence)
			.map(s -> (HelperSentence) s).collect(Collectors.toList());

			Map<String, List<String>> map = new HashMap<String, List<String>>();
			map.put(PathConversation.EN.name(), lo.stream().map(o -> o.getLines(SpellType.EN).get().get(0)).collect(Collectors.toList()));
			map.put(PathConversation.KANJI.name(), lo.stream().map(o -> o.getLines(SpellType.KANJI).get().get(0)).collect(Collectors.toList()));
			map.put(PathConversation.KANA.name(), lo.stream().map(o -> o.getLines(SpellType.KANA).get().get(0)).collect(Collectors.toList()));

			Optional<QuestionSentence> q = ls.stream().filter(s -> s instanceof QuestionSentence)
				.map(q2 -> (QuestionSentence) q2)
				.findFirst();
			q.ifPresent(q2 -> map.put(PathConversation.Q.name(), Arrays.asList(q2.getQuestion())));
			q.ifPresent(q2 -> map.put(PathConversation.A.name(), q2.getAnswers()));

			List<String> ids = lo.stream().map(o -> o.getId().toString()).collect(Collectors.toList());
			map2.put(ids, map);
		});
		yaml.createSection(PathStage.CONVERSATION.name(), map2);

		try {
			yaml.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void savePlayerSentences() {
//		Map<String, Map<String, Map<String, Map<String, List<String>>>>> uuids = new HashMap<>();
//		Map<String, String> names = new HashMap<String,String>();
//
//		SentenceStorage.playerSentences.entrySet().stream()
//			.forEach(entry -> {
//				List<String> uuid = new ArrayList<>();
//				List<String> name = new ArrayList<>();
//				String stage = entry.getKey();
//				List<List<PlayerSentence>> sentence = entry.getValue().stream().map(ls -> ls.stream().map(s -> (PlayerSentence) s).collect(Collectors.toList())).collect(Collectors.toList());
//				sentence.forEach(ls -> {
//					List<String> kanji = new ArrayList<String>();
//					List<String> kana = new ArrayList<String>();
//					List<String> en = new ArrayList<String>();
//					ls.forEach(s -> {
//						kanji.add(s.getJapanese().getLine(Arrays.asList(SpellType.KANJI)).get().get(0));
//						kana.add(s.getJapanese().getLine(Arrays.asList(SpellType.KANA)).get().get(0));
//						en.add(s.getEnglish().getLine().get(0));
//						if (!uuid.contains(s.getUniqueId())) {
//							uuid.add(s.getUniqueId().toString());
//						}
//						if (!name.contains(s.getDisplayName())) {
//							name.add(s.getDisplayName());
//						}
//					});
//					uuids.computeIfAbsent(uuid.get(0), initial -> new HashMap<String, Map<String, Map<String, List<String>>>>());
//					uuids.get(uuid.get(0)).computeIfAbsent(LearnerSentencePath.STAGE.name(), initial -> new HashMap<String, Map<String, List<String>>>());
//					uuids.get(uuid.get(0)).get(LearnerSentencePath.STAGE.name()).computeIfAbsent(stage, initial -> new HashMap<String, List<String>>());
//
//					uuids.get(uuid.get(0)).get(LearnerSentencePath.STAGE.name()).get(stage).put(SentencePath.KANJI.name(), kanji);
//					uuids.get(uuid.get(0)).get(LearnerSentencePath.STAGE.name()).get(stage).put(SentencePath.KANA.name(), kana);
//					uuids.get(uuid.get(0)).get(LearnerSentencePath.STAGE.name()).get(stage).put(SentencePath.EN.name(), en);
//
//					names.put(uuid.get(0), name.get(0));
//					uuid.clear();
//					name.clear();
//				});
//			});
//		YamlConfiguration yaml = new YamlConfiguration();
//		yaml.createSection(LearnerSentencePath.LEARNER_SENTENCE.name(), uuids);
//		names.entrySet().forEach(entry -> yaml.set(LearnerSentencePath.LEARNER_SENTENCE + "." + entry.getKey() + "." + LearnerSentencePath.NAME.name(), entry.getValue()));
//		try {
//			yaml.save(CONFIG_FILE);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}


package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.VirtualRyuugakuManager;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;

public class SentenceStorage {
	public static Map<String, List<List<HolographicSentence>>> ownerSentences = new HashMap<>();

	public static Map<String, List<Integer>> learnerIds = new HashMap<>();
	public static Map<String, List<String>> learnerQuestions = new HashMap<>();
	public static Map<UUID, List<List<LearnerSentence>>> learnerSentences = new HashMap<>();

	private static final File BASE = new File(VirtualRyuugakuManager.plugin.getDataFolder() + "//STAGE");
	private static final YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(VirtualRyuugakuManager.plugin.getDataFolder() + "//CONFIG//CONFIG.yml"));
	enum PathStage {CONVERSATION, EDITOR, LEARNER_NPC, LEARNER_QUESTION, KEY, Q, A}
	enum LearnerSentencePath {LEARNER_SENTENCE, STAGE}
	enum SentencePath {KANJI, KANA, EN}

	public static void showHolograms(Player player, NPC npc) {
		Optional<List<HolographicSentence>> list = findHolographicSentences.apply(npc);
		list.ifPresent(l -> HologramStorage.updateHologram(npc, l, player));
	}

	public static Optional<List<String>> findLearnerQuestions(String stage) {
		if (learnerQuestions.containsKey(stage.toUpperCase())) {
			return Optional.of(learnerQuestions.get(stage.toUpperCase()));
		}
		return Optional.empty();
	}

	public static Optional<String> findStageName(Integer id) {
		Optional<String> name = learnerIds.entrySet()
			.stream().filter(e -> e.getValue().contains(id))
			.findFirst().map(e -> e.getKey());
		if (name.isPresent()) {
			return name;
		}
		for (Entry<String, List<List<HolographicSentence>>> lls : ownerSentences.entrySet()) {
			for (List<HolographicSentence> ls : lls.getValue()) {
				for (HolographicSentence s : ls) {
					if (s.getId() == id) {
						return Optional.of(lls.getKey());
					}
				}
			}
		}
		return Optional.empty();
	}

	public static List<LearnerSentence> getLearnerSentencesOrDefault(UUID uuid, String stage) {
		if (learnerSentences.containsKey(uuid)) {
			Optional<List<LearnerSentence>> optional = learnerSentences.get(uuid).stream()
				.filter(list -> list.stream()
					.allMatch(s -> s.getStage().equalsIgnoreCase(stage)))
				.findFirst();
			if (optional.isPresent()) {
				return optional.get();
			}
		}
		return new ArrayList<LearnerSentence>();
	}

	public static Function<NPC, Optional<List<HolographicSentence>>> findHolographicSentences = (npc) -> {
		for (List<List<HolographicSentence>> lls : ownerSentences.values()) {
			for (List<HolographicSentence> ls : lls) {
				if(ls.stream().anyMatch(s -> s.getId().equals(npc.getId()))) {
					return Optional.of(ls);
				}
			}
		}

		Optional<String> stage = findStageName(npc.getId());
		UUID uuid = npc.getTrait(Owner.class).getOwnerId();
		if(learnerSentences.containsKey(uuid)) {
			for(List<LearnerSentence> ls : learnerSentences.get(uuid)) {
				if(ls.stream().map(s -> s.getStage().toUpperCase()).collect(Collectors.toList()).contains(stage.get().toUpperCase())) {
 					return Optional.ofNullable(ls.stream()
						.map(s -> (HolographicSentence) s)
						.collect(Collectors.toList()));
				}
			}
		}
		return Optional.empty();
	};

	public static Optional<List<HolographicSentence>> findOwnerSentences(Integer id) {
		for(List<List<HolographicSentence>> lls : ownerSentences.values())
			for (List<HolographicSentence> ls : lls) {
				for (HolographicSentence sentence : ls) {
					if (sentence.getId().equals(id)) {
						return Optional.of(ls);
					}
				}
			}
		return Optional.empty();
	}

	private static void importStage(YamlConfiguration config, String name) {
		name = name.toUpperCase();
		Optional<ConfigurationSection> conversationSection = Arrays.asList(config).stream()
			.filter(config2 -> config2.isConfigurationSection(PathStage.CONVERSATION.toString()))
			.map(config2 -> (ConfigurationSection) config2.get(PathStage.CONVERSATION.toString()))
			.findFirst();

		Optional<List<String>> learnerQuestion = Stream.of(config).filter(c -> c.isList(PathStage.LEARNER_QUESTION.name()))
			.findFirst().map(c -> c.getStringList(PathStage.LEARNER_QUESTION.name()));
		if (learnerQuestion.isPresent()) {
			learnerQuestions.put(name, learnerQuestion.get());
		}

		Optional<List<Integer>> ids = Stream.of(config).filter(c -> c.isList(PathStage.LEARNER_NPC.name()))
			.findFirst().map(c -> c.getIntegerList(PathStage.LEARNER_NPC.name()));
		if (ids.isPresent()) {
			learnerIds.put(name, ids.get());
		}

		List<List<HolographicSentence>> listlist = new ArrayList<>();
		conversationSection.ifPresent(section ->
			section.getKeys(false).stream()
			.forEach(key -> {
				List<HolographicSentence> list= new ArrayList<>();
				ConfigurationSection section2 = (ConfigurationSection) section.get(key);
				OwnerSentence.create(
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
		ownerSentences.put(name, listlist);
		return;
	}

	public static final void importSentences() {
		Arrays.asList(BASE.listFiles()).stream()
			.filter(file -> file.getAbsolutePath().endsWith(".yml"))
			.forEach(file -> importStage(YamlConfiguration.loadConfiguration(file), extractName(file)));
	}

	private static final String extractName(File file) {
		return file.getName().substring(0,  file.getName().length() - ".yml".length());
	}

	public static final Set<Integer> getAllIds() {
		Set<Integer> ids = new HashSet<>();
		ownerSentences.values().forEach(lls ->
			lls.forEach(ls ->
				ls.forEach(s -> ids.add(s.getId()))));
		learnerIds.values().forEach(li -> ids.addAll(li));
		return ids;
	}

	public static Function<String, Optional<List<List<HolographicSentence>>>> findStage = (name) -> {
		if (ownerSentences.containsKey(name.toUpperCase())) {
			return Optional.of(ownerSentences.get(name.toUpperCase()));
		}
		return Optional.empty();
	};

	public static void importLearnerSentences() {
		MemorySection uuidSection = (MemorySection) config.get(LearnerSentencePath.LEARNER_SENTENCE.name());
		uuidSection.getKeys(false).stream()
			.forEach(uuid -> {
				MemorySection stageSection = (MemorySection) uuidSection.get(uuid + "." + LearnerSentencePath.STAGE.name());
				List<List<LearnerSentence>> listSentences = stageSection.getKeys(false).stream()
					.map(stage -> {
						List<LearnerSentence> list = new ArrayList<>();
						findLearnerQuestions(stage).ifPresent(questions -> {
							MemorySection sentencesSection = (MemorySection) stageSection.get(stage);
							List<String> kanji = sentencesSection.getStringList(SentencePath.KANJI.name());
							List<String> kana = sentencesSection.getStringList(SentencePath.KANA.name());
							List<String> en = sentencesSection.getStringList(SentencePath.EN.name());
							if (kanji.size() == kana.size() && kanji.size() == en.size()) {
								for (int i = 0; i < questions.size(); i++) {
									if (i < kanji.size()) {
										list.add(new LearnerSentence(
												Arrays.asList(kanji.get(i)),
												Arrays.asList(kana.get(i)),
												Arrays.asList(en.get(i)),
												stage,
												questions.get(i)));
									} else {
										list.add(LearnerSentence.createEmpty(stage, questions.get(i)));
									}
								}
							}
						});
						return list;
					})
					.collect(Collectors.toList());
				learnerSentences.put(UUID.fromString(uuid), listSentences);
			});
	}

	public static boolean hasLearnerSentence(Player player, String stage) {
		List<LearnerSentence> sentences = SentenceStorage.getLearnerSentencesOrDefault(player.getUniqueId(), stage);
		if(sentences.stream().anyMatch(s -> s.getStage().equalsIgnoreCase(stage))) {
			return true;
		}
		return false;
	}

	public static void addEmptyLearnerSentence(UUID uuid, String stage) {
		if(SentenceStorage.learnerSentences.containsKey(uuid)) {
				SentenceStorage.learnerSentences.remove(uuid);
		}
		List<LearnerSentence> list = findLearnerQuestions(stage).get().stream()
			.map(q -> LearnerSentence.createEmpty(stage, q))
			.collect(Collectors.toList());
		List<List<LearnerSentence>> list2 = SentenceStorage.learnerSentences.get(uuid);
		list2.add(list);
	}
}


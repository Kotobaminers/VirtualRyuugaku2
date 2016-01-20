package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerData;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.VirtualRyuugakuManager;

public class SentenceStorage {
	public static Map<String, List<List<Sentence>>> sentences = new HashMap<String, List<List<Sentence>>>();
	public static Map<String, List<Integer>> learnerIds = new HashMap<String, List<Integer>>();
	public static Map<UUID, List<List<LearnerSentence>>> learnerSentences = new HashMap<UUID, List<List<LearnerSentence>>>();

	private static final File BASE = new File(VirtualRyuugakuManager.plugin.getDataFolder() + "//STAGE");
	enum PathStage {CONVERSATION, EDITOR, LEARNER_NPC, LEARNER_QUESTION, KEY, KANJI, KANA, EN, Q, A}

	public static void talk(Player player, Integer id) {
		Optional<List<Sentence>> list = findNPCSentences.apply(id);
		list.ifPresent(l -> HologramStorage.updateHologram(id, l, player));
	}

	public static Function<String, Optional<List<List<Sentence>>>> findStage = (name) -> {
		if (sentences.containsKey(name.toUpperCase())) {
			return Optional.of(sentences.get(name.toUpperCase()));
		}
		return Optional.empty();
	};

	public static Optional<String> findStageNameByLearner(Integer id) {
		return learnerIds.entrySet()
			.stream().filter(e -> e.getValue().contains(id))
			.findFirst().map(e -> e.getKey());
	}

	public static Optional<List<LearnerSentence>> findLearnerSentences(UUID uuid, String stage) {
		if (learnerSentences.containsKey(uuid)) {
			return learnerSentences.get(uuid).stream().filter(list -> list.get(0).getStage().equalsIgnoreCase(stage)).findFirst();
		}
		return Optional.empty();
	}

	public static Function<Integer, Optional<List<Sentence>>> findNPCSentences = (id) -> {
		for (List<List<Sentence>> lls : sentences.values()) {
			for (List<Sentence> ls : lls) {
				if(ls.stream().anyMatch(s -> s.getId().equals(id))) {
					return Optional.of(ls);
				}
			}
		}
		return Optional.empty();
	};

	public static BiFunction<List<Sentence>, Player, Optional<Sentence>> findNPCSentence = (list, player) -> {
		PlayerData data = PlayerDataStorage.getDataPlayer(player);
		if(data.getLine()  < list.size() - 1) {
			data.line++;
			return Optional.of(list.get(data.getLine()));
		}
		data.line = 0;
		return Optional.of(list.get(data.getLine()));
	};

	private static void importStage(YamlConfiguration config, String name) {
		name = name.toUpperCase();
		Optional<ConfigurationSection> conversationSection = Arrays.asList(config).stream()
			.filter(config2 -> config2.isConfigurationSection(PathStage.CONVERSATION.toString()))
			.map(config2 -> (ConfigurationSection) config2.get(PathStage.CONVERSATION.toString()))
			.findFirst();
		Optional<List<Integer>> ids = Stream.of(config).filter(c -> c.isList(PathStage.LEARNER_NPC.name()))
			.findFirst().map(c -> c.getIntegerList(PathStage.LEARNER_NPC.name()));
		if (ids.isPresent()) {
			learnerIds.put(name, ids.get());
		}

		List<List<Sentence>> listlist = new ArrayList<>();
		conversationSection.ifPresent(section ->
			section.getKeys(false).stream()
			.forEach(key -> {
				List<Sentence> list= new ArrayList<>();
				ConfigurationSection section2 = (ConfigurationSection) section.get(key);
				TalkSentence.createTalkSentences(
					section2.getStringList(PathStage.KANJI.toString()),
					section2.getStringList(PathStage.KANA.toString()),
					section2.getStringList(PathStage.EN.toString()),
					Utility.toListInteger(key)
				).ifPresent(present -> list.addAll(present));
				QuestionSentence.create(section2, Utility.toListInteger(key)).ifPresent(q -> list.add(q));
				if(0 < list.size()) {
					listlist.add(list);
				}
			})
		);
		sentences.put(name, listlist);
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

	public static final Set<Integer> getIds() {
		Set<Integer> ids = new HashSet<>();
		sentences.values().forEach(lls ->
			lls.forEach(ls ->
				ls.forEach(s -> ids.add(s.getId()))));
		return ids;
	}

	public static void test() {
		Optional<List<LearnerSentence>> sentences = LearnerSentence.createLearnerSentences(
			Arrays.asList("漢字"),
			Arrays.asList("かな"),
			Arrays.asList("en"),
			"TMP");
		learnerSentences.put(Bukkit.getOfflinePlayer("kai_f").getUniqueId(), Arrays.asList(sentences.get()));
	}
}





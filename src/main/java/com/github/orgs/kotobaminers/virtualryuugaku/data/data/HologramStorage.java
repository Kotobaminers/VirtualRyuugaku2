package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.SentenceHologram;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

import net.md_5.bungee.api.ChatColor;

public class HologramStorage {
	private static Map<List<Integer>, SentenceHologram> holograms = new HashMap<>();
	private static final Integer DURATION = 10 * 20;

	public static SentenceHologram getHologramSafe(Integer id) {
		Optional<Entry<List<Integer>, SentenceHologram>> hologram = holograms.entrySet().stream().filter(h -> h.getKey().contains(id)).findFirst();
		if (hologram.isPresent()) {
			return hologram.get().getValue();
		}
		return new SentenceHologram();
	}

	public static void updateHologram(Integer id, List<Sentence> list, Player player) {
		if (list.size() < 1) {
			return;
		}
		SentenceHologram hologram = getHologramSafe(id);
		List<SpellType> spells = PlayerDataStorage.getDataPlayer(player).getSpellTypes();
		Integer count = hologram.count;

		Optional<Sentence> sentence = Optional.empty();
		if (hologram.count < list.size()) {
			sentence = Optional.of(list.get(hologram.count));
			count = hologram.count + 1;
		} else if (0 < list.size()) {
			sentence = Optional.of(list.get(0));
			count = 1;
		}

		if (sentence.isPresent()) {
			List<String> lines = new ArrayList<String>();
			if (sentence.get() instanceof TalkSentence) {
				getTalkSentenceLines((TalkSentence) sentence.get(), spells)
					.ifPresent(l -> lines.addAll(l));
			} else if(sentence.get() instanceof QuestionSentence){
				QuestionSentence question = (QuestionSentence) sentence.get();
				lines.add(question.getQuestion());
			}
			hologram.remove();
			hologram = new SentenceHologram(NPCHandler.findNPC(sentence.get().getId()).get().getStoredLocation(), lines);
			hologram.count = count;
			hologram.spawnTemp(DURATION);

			holograms.put(list.stream().map(l -> l.getId()).collect(Collectors.toList()), hologram);
			sentence.get().playEffect(player);
		}
	}

	private static Optional<List<String>> getTalkSentenceLines(TalkSentence sentence, List<SpellType> spells) {
		List<String> lines = new ArrayList<String>();
		sentence.getJapanese().getLine(spells).ifPresent(l ->
			l.stream().forEach(s -> lines.add(ChatColor.BOLD + s)));
		sentence.getEnglish().getLine(spells).ifPresent(l ->
			l.stream().forEach(s -> lines.add(ChatColor.BOLD + s)));
		if (0 < lines.size()) {
			return Optional.of(lines);
		}
		return Optional.empty();
	}

	public static void initialize() {
		SentenceStorage.getIds().stream().forEach(id ->
			NPCHandler.findNPC(id).ifPresent(npc ->
				Stream.of(Utility.getNearbyEntities(npc.getStoredLocation(), 1))
					.filter(e -> e.getType().equals(EntityType.ARMOR_STAND))
					.filter(e -> {ArmorStand armor = (ArmorStand) e;
						return !armor.isVisible() && !armor.hasGravity() && armor.isCustomNameVisible();
						})
					.forEach(armor -> armor.remove())));
	}

}
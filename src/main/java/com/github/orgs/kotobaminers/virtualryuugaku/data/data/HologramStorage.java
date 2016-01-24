package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.SentenceHologram;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

import net.citizensnpcs.api.npc.NPC;

public class HologramStorage {
	public static Map<List<Integer>, SentenceHologram> holograms = new HashMap<>();
	private static final Integer DURATION = 10 * 20;

	public static void updateHologram(NPC npc, List<HolographicSentence> sentences, Player player) {
		if (sentences.size() < 1) {
			return;
		}
		SentenceHologram hologram = getHologramSafe(npc.getId());

		HolographicSentence sentence = null;
		if (hologram.isShown()) {
			if (hologram.count < sentences.size() - 1) {
				hologram.count++;
			} else {
				hologram.count = 0;
			}
		}
		sentence = sentences.get(hologram.count);

		hologram.cancelOldTask();
		hologram.remove();
		hologram.setLines(sentence.getHolographicLines(PlayerDataStorage.getDataPlayer(player).getSpellTypes()));
		hologram.setLocation(sentence.getHologramLocation(npc));
		hologram.spawnTemp(DURATION);
		sentence.registerHologram(hologram, npc, sentences);
		sentence.playEffect(player, sentence.getHologramLocation(npc));
	}

	public static void initialize() {
		SentenceStorage.getAllIds().stream().forEach(id ->
			NPCHandler.findNPC(id).ifPresent(npc ->
				Stream.of(Utility.getNearbyEntities(npc.getStoredLocation().add(0, 1, 0), 1))
					.filter(e -> e.getType().equals(EntityType.ARMOR_STAND))
					.filter(e -> {ArmorStand armor = (ArmorStand) e;
						return !armor.isVisible() && !armor.hasGravity() && armor.isCustomNameVisible();
						})
					.forEach(armor -> armor.remove())));
	}

	static SentenceHologram getHologramSafe(Integer id) {
		Optional<Entry<List<Integer>, SentenceHologram>> hologram = holograms.entrySet().stream().filter(h -> h.getKey().contains(id)).findFirst();
		if (hologram.isPresent()) {
			return hologram.get().getValue();
		}
		return new SentenceHologram();
	}
}
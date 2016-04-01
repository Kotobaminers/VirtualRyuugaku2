package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.SentenceHologram;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class HologramStorage {
	public static Map<List<Integer>, SentenceHologram> holograms = new HashMap<>();
	private static final Integer DURATION = 10 * 20;
	private static final String KEY_MARK = "" + ChatColor.YELLOW + ChatColor.BOLD + "*";

	public static void updateHologram(NPC npc, Unit unit, Player player) {
		Optional<List<HolographicSentence>> optional = unit.findDisplayedLS(npc);
		if(!optional.isPresent()) {
			return;
		}
		List<HolographicSentence> sentences = optional.get();
		if (sentences.size() < 1) {
			return;
		}
		SentenceHologram hologram = getHologramSafe(npc.getId());

		HolographicSentence sentence = null;
		if (!(hologram.count < sentences.size())) {
			hologram.count = 0;
		} else 	if (hologram.isShown()) {
			if (hologram.count < sentences.size() - 1) {
				for (int i = hologram.count + 1; i < sentences.size(); i++) {
					if (sentences.get(i) instanceof TalkSentence) {
						if (i < unit.getPlayerQuestions().size()) {
							if(!unit.getPlayerQuestions().get(i).equalsIgnoreCase(Unit.TALK_FREELY)) {
								hologram.count = i;
								break;
							} else if (!TalkSentence.isEmpty(((TalkSentence) sentences.get(i)))) {
								hologram.count = i;
								break;
							}
						}
						if(i == sentences.size() - 1) {
							hologram.count = 0;
							break;
						}
					} else {
						hologram.count = i;
						break;
					}
				}
			} else {
				hologram.count = 0;
			}
		} else {
			String a = "";
		}
		sentence = sentences.get(hologram.count);

		hologram.cancelOldTask();
		hologram.remove();

		List<String> holographicLines = new ArrayList<String>();
		findPrefix(unit, sentence, hologram.count).ifPresent(prefix -> holographicLines.add(prefix));
		holographicLines.addAll(sentence.getHolographicLines(PlayerDataStorage.getPlayerData(player).getSpellTypes()));
		hologram.setLines(holographicLines);
		hologram.setLocation(sentence.getHologramLocation(npc));
		hologram.spawnTemp(DURATION);
		sentence.registerHologram(hologram, npc, sentences);
		sentence.playEffect(player, sentence.getHologramLocation(npc));
	}

	private static Optional<String> findPrefix(Unit unit, HolographicSentence sentence, Integer count) {
		if (sentence instanceof PlayerSentence) {
			if (count < unit.getPlayerQuestions().size()) {
				return Optional.of("" + ChatColor.GREEN + ChatColor.BOLD + unit.getPlayerQuestions().get(count));
			}
		} else if(sentence instanceof HelperSentence) {
			String prefix = "";
			if(((HelperSentence) sentence).isKey()) {
				prefix += KEY_MARK;
			}
			prefix += "" + ChatColor.GREEN + ChatColor.BOLD + "Conversation(" + (count + 1) + ")";
			return Optional.of(prefix);
		}
		return Optional.empty();
	}

	public static void initialize() {
		UnitStorage.getAllIds().stream().forEach(id ->
			NPCUtility.findNPC(id).ifPresent(npc ->
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
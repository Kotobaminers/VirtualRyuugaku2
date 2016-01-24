package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.SentenceHologram;

import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class OwnerSentence extends TalkSentence {

	public OwnerSentence(List<String> kanji, List<String> kana, List<String> en, Integer id) {
		super(kanji, kana, en, id);
	}

	public static Optional<List<OwnerSentence>> create(List<String> kanji, List<String> kana, List<String> en, List<Integer> ids) {
		List<OwnerSentence> sentences = new ArrayList<OwnerSentence>();
		if(kanji.size() == kana.size() && kanji.size() == en.size() && kanji.size() == ids.size()) {
			for (int i = 0; i < kanji.size(); i++) {
				sentences.add(new OwnerSentence(
						Arrays.asList(kanji.get(i)),
						Arrays.asList(kana.get(i)),
						Arrays.asList(en.get(i)),
						ids.get(i))
				);
			}
			if (0 < sentences.size()) {
				return Optional.of(sentences);
			}
		}
		return Optional.empty();
	}

	@Override
	public List<String> getHolographicLines(List<SpellType> spells) {
		List<String> lines = new ArrayList<String>();
		this.getJapanese().getLine(spells).ifPresent(l ->
			l.stream().forEach(s -> lines.add(ChatColor.BOLD + s)));
		this.getEnglish().getLine(spells).ifPresent(l ->
			l.stream().forEach(s -> lines.add(ChatColor.BOLD + s)));
		return lines;
	}

	@Override
	public Optional<List<ItemStack>> giveIcons() {
		return giveSentenceIcons();
	}

	@Override
	public Optional<List<ItemStack>> giveEmptyIcons() {
		return Optional.empty();
	}

	@Override
	public void registerHologram(SentenceHologram hologram, NPC npc, List<HolographicSentence> sentences) {
		HologramStorage.holograms.put(sentences.stream().map(s -> s.getId()).collect(Collectors.toList()), hologram);
	}

	@Override
	public Location getHologramLocation(NPC npc) {
		return NPCHandler.findNPC(id).get().getStoredLocation();
	}
}

package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.SentenceHologram;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.GUIIcon;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class HelperSentence extends TalkSentence {
	private Optional<UUID> owner = Optional.empty();

	public HelperSentence(List<String> kanji, List<String> kana, List<String> en, Integer id) {
		super(kanji, kana, en, id);
	}

	public static Optional<List<HelperSentence>> create(List<String> kanji, List<String> kana, List<String> en, List<Integer> ids) {
		List<HelperSentence> sentences = new ArrayList<HelperSentence>();
		if(kanji.size() == kana.size() && kanji.size() == en.size() && kanji.size() == ids.size()) {
			for (int i = 0; i < kanji.size(); i++) {
				sentences.add(new HelperSentence(
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

	public static HelperSentence createEmpty(Integer id) {
		HelperSentence sentence = new HelperSentence(Arrays.asList(EMPTY_KANJI), Arrays.asList(EMPTY_KANA), Arrays.asList(EMPTY_EN), id);
		sentence.getJapanese().setRomaji(EMPTY_ROMAJI);
		return sentence;
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
		Optional<List<ItemStack>> icons = giveSentenceIcons();
		ItemStack skull = GUIIcon.PLAYER_SKULL.createItem();
		Optional<NPC> npc = NPCUtility.findNPC(getId());
		Optional<String> skin = npc.map(n -> NPCUtility.findSkinName(n))
			.filter(name -> name.isPresent())
			.map(name -> name.get());
		if (skin.isPresent()) {
			skull = Utility.setSkullOwner(skull, skin.get());
		}
		skull = Utility.setItemDisplayName(skull, npc.get().getName());
		if (icons.isPresent()) {
			icons.get().add(0, skull);
			return icons;
		}
		return Optional.empty();
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
	public Location getTalkerLocation(NPC npc) {
		return NPCUtility.findNPC(id).get().getStoredLocation();
	}

	public Optional<UUID> getOwner() {
		return owner;
	}

}

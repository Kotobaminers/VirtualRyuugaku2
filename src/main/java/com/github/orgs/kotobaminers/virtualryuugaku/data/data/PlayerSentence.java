package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.SentenceHologram;

import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class PlayerSentence extends TalkSentence {
	public static final Integer DEFAULT_ID= -1;

	private UUID uuid = null;
	private String displayName = "";

	public PlayerSentence(List<String> kanji, List<String> kana, List<String> en, UUID uuid, String name) {
		super(kanji, kana, en, DEFAULT_ID);
		this.uuid = uuid;
		this.displayName = name;
	}

	public static PlayerSentence createEmpty(UUID uuid, String name) {
		PlayerSentence sentence = new PlayerSentence(Arrays.asList(EMPTY_KANJI), Arrays.asList(EMPTY_KANA), Arrays.asList(EMPTY_EN), uuid, name);
		sentence.getJapanese().setRomaji(EMPTY_ROMAJI);
		return sentence;
	}

	public boolean isEmpty() {
		if (getJapanese().getLine().equals(Arrays.asList(EMPTY_KANJI, EMPTY_KANA, EMPTY_ROMAJI))
				&& getEnglish().getLine().equals(Arrays.asList(EMPTY_EN))) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> getHolographicLines(List<SpellType> spells) {
		ArrayList<String> lines = new ArrayList<String>();
		spells.stream().forEach(spell -> this.getLines(spell).get().forEach(line -> lines.add(ChatColor.BOLD + line)));
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
	public Location getTalkerLocation(NPC npc) {
		return npc.getStoredLocation();
	}
	@Override
	public void registerHologram(SentenceHologram hologram, NPC npc, List<HolographicSentence> sentences) {
		HologramStorage.holograms.put(Arrays.asList(npc.getId()), hologram);
	}

	public UUID getUniqueId() {
		return uuid;
	}
	public String getDisplayName() {
		return displayName;
	}
}

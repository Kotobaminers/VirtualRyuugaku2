package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.SentenceHologram;

import net.citizensnpcs.api.npc.NPC;

public class LearnerSentence extends TalkSentence {
	private static final Integer DEFAULT_ID= -1;

	public LearnerSentence(List<String> kanji, List<String> kana, List<String> en, String stage, String question) {
		super(kanji, kana, en, DEFAULT_ID);
		this.stage = stage;
		this.question = question;
	}
	private String stage = "";
	private String question = "";

	public String getStage() {
		return stage;
	}
	public String getQuestion() {
		return question;
	}

	public static Optional<List<LearnerSentence>> create(List<String> kanji, List<String> kana, List<String> en, String stage, String question) {
		List<LearnerSentence> sentences = new ArrayList<LearnerSentence>();
		if(kanji.size() == kana.size() && kanji.size() == en.size() && 0 < stage.length()) {
			for (Integer i = 0; i < kanji.size(); i++) {
				LearnerSentence sentence = new LearnerSentence(Arrays.asList(kanji.get(i)), Arrays.asList(kana.get(i)), Arrays.asList(en.get(i)), stage, question);
				sentences.add(sentence);
			}
		}
		if (0 < sentences.size()) {
			return Optional.ofNullable(sentences);
		}
		return Optional.empty();
	}

	public static LearnerSentence createEmpty(String stage, String question) {
		LearnerSentence sentence = new LearnerSentence(Arrays.asList("Enter kanji"), Arrays.asList("Enter kana"), Arrays.asList("Enter English"), stage, question);
		sentence.getJapanese().setRomaji("Enter romaji");
		return sentence;
	}

	@Override
	public List<String> getHolographicLines(List<SpellType> spells) {
		ArrayList<String> lines = new ArrayList<String>();
		lines.add(question);
		spells.stream().forEach(spell -> lines.addAll(this.getLines(spell).get()));
		return lines;
	}

	@Override
	public Optional<List<ItemStack>> giveIcons() {
		List<ItemStack> icons = new ArrayList<ItemStack>();
		ItemStack info = new ItemStack(Material.WOOL);
		ItemMeta meta = info.getItemMeta();
		meta.setDisplayName(question);
		info.setItemMeta(meta);
		icons.add(info);
		icons.addAll(giveSentenceIcons().get());
		return Optional.of(icons);
	}

	@Override
	public Optional<List<ItemStack>> giveEmptyIcons() {
		return Optional.empty();
	}
	@Override
	public Location getHologramLocation(NPC npc) {
		return npc.getStoredLocation();
	}
	@Override
	public void registerHologram(SentenceHologram hologram, NPC npc, List<HolographicSentence> sentences) {
		HologramStorage.holograms.put(Arrays.asList(npc.getId()), hologram);
	}
}

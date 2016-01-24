package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.LearnerSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;

public class LearnerSentenceSelector extends SentenceSelector {
	public static final String TITLE = "Player's Sentences";
	public List<LearnerSentence> sentences = new ArrayList<LearnerSentence>();

	public LearnerSentenceSelector(List<HolographicSentence> sentences) {
		this.sentences = sentences.stream()
			.map(sentence -> (LearnerSentence) sentence)
			.collect(Collectors.toList());
	}

	public  LearnerSentenceSelector() {
	}

	@Override
	public List<List<ItemStack>> getIcons() {
		return sentences.stream()
			.map(sentence -> sentence.giveIcons().get())
			.collect(Collectors.toList());
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public Optional<HolographicSentence> selectSentence(InventoryClickEvent event) {
		int index = event.getRawSlot() % 9;
		Optional<List<LearnerSentence>> sentences = SentenceStorage.findStageName(PlayerDataStorage.getDataPlayer((Player) event.getWhoClicked()).getSelectId())
			.map(s -> SentenceStorage.getLearnerSentencesOrDefault(event.getWhoClicked().getUniqueId(), s));
		if (sentences.isPresent()) {
			if (index < sentences.get().size()) {
				return Optional.of(sentences.get().get(index));
			}
		}
		return Optional.empty();
	}

}

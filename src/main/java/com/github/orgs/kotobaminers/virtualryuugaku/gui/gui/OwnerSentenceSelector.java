package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.OwnerSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.QuestionSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;

public class OwnerSentenceSelector extends SentenceSelector {
	public static final String TITLE = "Example Sentences";
	private List<OwnerSentence> ownerSentences = new ArrayList<>();
	private Optional<QuestionSentence> question = Optional.empty();

	public OwnerSentenceSelector(List<HolographicSentence> sentences) {
		for (HolographicSentence sentence : sentences) {
			if (sentence instanceof OwnerSentence) {
				ownerSentences.add((OwnerSentence) sentence);
			} else if(sentence instanceof QuestionSentence) {
				question = Optional.of((QuestionSentence) sentence);
			}
		}
	}

	public OwnerSentenceSelector() {
	}

	@Override
	public List<List<ItemStack>> getIcons() {
		List<List<ItemStack>> listIcons = ownerSentences.stream()
			.map(sentence -> sentence.giveIcons())
			.filter(icons -> icons.isPresent())
			.map(icons -> icons.get())
			.collect(Collectors.toList());
		question.ifPresent(q -> q.giveIcons().ifPresent(icons -> listIcons.add(icons)));
		return listIcons;
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public Optional<HolographicSentence> selectSentence(InventoryClickEvent event) {
		int index = event.getRawSlot() % 9;
		Integer selectId = PlayerDataStorage.getDataPlayer((Player) event.getWhoClicked()).getSelectId();
		Optional<List<HolographicSentence>> sentences = SentenceStorage.findOwnerSentences(selectId);
		if (sentences.isPresent()) {
			if (index < sentences.get().size()) {
				return Optional.of(sentences.get().get(index));
			}
		}
		return Optional.empty();
	}
}


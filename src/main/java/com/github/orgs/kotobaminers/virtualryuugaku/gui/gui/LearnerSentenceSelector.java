package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.inventory.ItemStack;

import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.PlayerSentence;

public class LearnerSentenceSelector extends SentenceSelector {
	public static final String TITLE = "Player's Sentences";
	public List<PlayerSentence> sentences = new ArrayList<PlayerSentence>();

	public LearnerSentenceSelector(List<HolographicSentence> sentences) {
		this.sentences = sentences.stream()
			.map(sentence -> (PlayerSentence) sentence)
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
	public List<ItemStack> getOptionIcons() {
		return Stream.of(GUIIcon.FREE_UP, GUIIcon.RESPAWN)
			.map(option ->option.createItem())
			.collect(Collectors.toList());
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

//	@Override
//	public Optional<HolographicSentence> selectSentence(InventoryClickEvent event) {
//		int index = event.getRawSlot() % 9;
//		Optional<List<LearnerSentence>> sentences = SentenceStorage.findStageName(PlayerDataStorage.getDataPlayer((Player) event.getWhoClicked()).getSelectId())
//			.map(s -> SentenceStorage.getLearnerSentencesEvenPutEmpty(event.getWhoClicked().getUniqueId(), event.getWhoClicked().getName() ,s).get());
//		if (sentences.isPresent()) {
//			if (index < sentences.get().size()) {
//				return Optional.of(sentences.get().get(index));
//			}
//		}
//		return Optional.empty();
//	}

}

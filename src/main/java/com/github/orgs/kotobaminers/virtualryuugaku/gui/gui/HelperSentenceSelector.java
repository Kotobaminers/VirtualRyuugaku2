package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.inventory.ItemStack;

import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HelperSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.QuestionSentence;

public class HelperSentenceSelector extends SentenceSelector {
	public static final String TITLE = "Example Sentences";
	private List<HelperSentence> ownerSentences = new ArrayList<>();
	private Optional<QuestionSentence> question = Optional.empty();


	public HelperSentenceSelector(List<HolographicSentence> sentences) {
		for (HolographicSentence sentence : sentences) {
			if (sentence instanceof HelperSentence) {
				ownerSentences.add((HelperSentence) sentence);
			} else if(sentence instanceof QuestionSentence) {
				question = Optional.of((QuestionSentence) sentence);
			}
		}
	}

	public HelperSentenceSelector() {
	}

	@Override
	public List<List<ItemStack>> getIcons() {
		List<List<ItemStack>> listIcons = ownerSentences.stream()
			.map(sentence -> sentence.giveIcons())
			.filter(icons -> icons.isPresent())
			.map(icons -> icons.get())
			.collect(Collectors.toList());
		if(question.isPresent()) {
			question.get().giveIcons().ifPresent(icons -> listIcons.add(icons));
		} else {
			QuestionSentence.createEmpty(0).giveEmptyIcons()
				.ifPresent(empty -> listIcons.add(empty));
		}
		return listIcons;
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public List<ItemStack> getOptionIcons() {
		return Stream.of(GUIIcon.RESPAWN, GUIIcon.ROLE_PLAY)
				.map(option ->option.createItem())
				.collect(Collectors.toList());
	}
}


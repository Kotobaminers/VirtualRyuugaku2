package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.PlayerSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.Unit;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.Unit.UnitType;

public class PlayerSentenceSelector extends SentenceSelector {
	public static final String TITLE = "Player's Sentences";
	public List<PlayerSentence> sentences = new ArrayList<>();
	private List<String> questions = new ArrayList<>();
	private UnitType type = UnitType.NORMAL;

	public static PlayerSentenceSelector create(Unit unit, List<HolographicSentence> sentences) {
		PlayerSentenceSelector selector = new PlayerSentenceSelector();
		selector.sentences = sentences.stream()
			.filter(sentence -> sentence instanceof PlayerSentence)
			.map(sentence -> (PlayerSentence) sentence)
			.collect(Collectors.toList());
		selector.questions = unit.getPlayerQuestions();
		selector.type = unit.getType();
		return selector;
	}
	public static PlayerSentenceSelector create() {
		return new PlayerSentenceSelector();
	}
	private PlayerSentenceSelector() {
	}

	@Override
	public List<List<ItemStack>> getIcons() {
		List<ItemStack> qs = questions.stream().map(q -> {
			ItemStack icon = GUIIcon.PLAYER_QUESTION.createItem();
			ItemMeta itemMeta = icon.getItemMeta();
			itemMeta.setDisplayName(q);
			icon.setItemMeta(itemMeta);
			return icon;
		}).collect(Collectors.toList());
		List<List<ItemStack>> icons = sentences.stream()
			.map(sentence -> sentence.giveIcons().get())
			.collect(Collectors.toList());
		List<List<ItemStack>> result = new ArrayList<>();
		for (int i = 0; i < qs.size(); i++) {
			if (i < icons.size()) {
				List<ItemStack> list = new ArrayList<>();
				list.addAll(icons.get(i));
				list.add(0, qs.get(i));
				result.add(list);
			}
		}
		return result;

	}
	@Override
	public List<ItemStack> getOptionIcons() {
		return type.getOptions().stream()
			.map(option ->option.createItem())
			.collect(Collectors.toList());
	}

	@Override
	public String getTitle() {
		return TITLE;
	}
}

package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class SentenceOptionSelector extends VRGGUI{
	public static final String TITLE = "Select Sentence Options!";
	private static final List<GUIIcon> OPTIONS = Arrays.asList(GUIIcon.CHANGE_SPEAKER, GUIIcon.PREPEND_SENTENCE, GUIIcon.APPEND_SENTENCE, GUIIcon.REMOVE_SENTENCE, GUIIcon.BACK);
	private Integer position = 0;

	private SentenceOptionSelector() {};

	public static SentenceOptionSelector create(Integer position) {
		SentenceOptionSelector selector = new SentenceOptionSelector();
		selector.position = position;
		return selector;
	}

	@Override
	public String getTitle() {
		return TITLE;
	}
	@Override
	public Inventory createInventory() {
		Inventory inventory = Bukkit.createInventory(null, 54, getTitle());
		OPTIONS.stream().forEach(icon -> inventory.addItem(icon.createItem()));
		return inventory;
	}
	public Integer getPosition() {
		return position;
	}
}

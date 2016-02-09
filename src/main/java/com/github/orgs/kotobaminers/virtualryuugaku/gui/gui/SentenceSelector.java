package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HelperSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.PlayerSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.QuestionSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceEditor;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;

public abstract class SentenceSelector extends VRGGUI {
	public abstract List<List<ItemStack>> getIcons();
	public abstract List<ItemStack> getOptionIcons();

	public static Optional<SentenceSelector> create(List<HolographicSentence> sentences) {
		if (0 < sentences.size()) {
			if(sentences.stream().allMatch(sentence -> sentence instanceof PlayerSentence)) {
				return Optional.of(new LearnerSentenceSelector(sentences));
			}
			if(sentences.stream().allMatch(sentence -> sentence instanceof HelperSentence || sentence instanceof QuestionSentence)) {
				return Optional.of(new OwnerSentenceSelector(sentences));
			}
		}
		return Optional.empty();
	}

	@Override
	public Inventory createInventory() {
		Inventory inventory = Bukkit.createInventory(null, size, getTitle());
		Integer low = 0;
		for (List<ItemStack> list : getIcons()) {
			Integer column = 0;
			for (ItemStack item : list) {
				item.setAmount(low + 1);
				inventory.setItem(low + column++ * 9, item);
			}
			low++;
		}


		Integer position = new Integer(size - 1);
		for (ItemStack item : getOptionIcons()) {
			inventory.setItem(position--, item);
		}
		return inventory;
	}

	public static Optional<SentenceSelector> create(InventoryClickEvent event) {
		switch(event.getInventory().getTitle()) {
		case LearnerSentenceSelector.TITLE:
			return Optional.of(new LearnerSentenceSelector());
		case OwnerSentenceSelector.TITLE:
			return Optional.of(new OwnerSentenceSelector());
		}
		return Optional.empty();
	}

	public Optional<SentenceEditor> createEditor(InventoryClickEvent event, GUIIcon icon) {
		int id = PlayerDataStorage.getPlayerData((Player) event.getWhoClicked()).getSelectId();
		int index = event.getRawSlot() % 9;
		switch (icon) {
			case EN:
				return Optional.of(SentenceEditor.create(id, index, Optional.of(SpellType.EN)));
			case KANJI:
				return Optional.of(SentenceEditor.create(id, index, Optional.of(SpellType.KANJI)));
			case KANA:
				return Optional.of(SentenceEditor.create(id, index, Optional.of(SpellType.KANA)));
			case QUESTION:
				return Optional.of(SentenceEditor.create(id, index, Optional.empty()));
		default:
			return Optional.empty();
		}
	}

}


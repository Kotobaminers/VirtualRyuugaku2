package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.LearnerSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.OwnerSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.QuestionSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceEditor;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;

public abstract class SentenceSelector extends VRGGUI {
	public abstract List<List<ItemStack>> getIcons();

	public static Optional<SentenceSelector> create(List<HolographicSentence> sentences) {
		if (0 < sentences.size()) {
			if(sentences.stream().allMatch(sentence -> sentence instanceof LearnerSentence)) {
				return Optional.of(new LearnerSentenceSelector(sentences));
			}
			if(sentences.stream().allMatch(sentence -> sentence instanceof OwnerSentence || sentence instanceof QuestionSentence)) {
				return Optional.of(new OwnerSentenceSelector(sentences));
			}
		}
		return Optional.empty();
	}

	@Override
	public Inventory createInventory() {
		Inventory inventory = Bukkit.createInventory(null, 54, getTitle());
		Integer low = 0;
		for (List<ItemStack> list : getIcons()) {
			Integer column = 0;
			for (ItemStack item : list) {
				item.setAmount(low + 1);
				inventory.setItem(low + column++ * 9, item);
			}
			low++;
		}
		return inventory;
	}

	@Override
	public void eventInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Optional<SpellType> spell = SpellType.createSpellType(event.getCurrentItem());
		selectSentence(event)
			.ifPresent(s ->
				spell.ifPresent(sp ->
					PlayerDataStorage.getDataPlayer(player).editor = Optional.of(new SentenceEditor(s, sp))));
		event.setCancelled(true);
		player.closeInventory();
	}

	public abstract Optional<HolographicSentence> selectSentence(InventoryClickEvent event);
}




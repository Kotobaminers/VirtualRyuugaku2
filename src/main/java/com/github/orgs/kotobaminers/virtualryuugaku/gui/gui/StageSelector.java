package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;

public class StageSelector extends VRGGUI {
	public static final String TITLE = "Select Stage!";

	@Override
	public void eventInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		String stage = event.getCurrentItem().getItemMeta().getDisplayName();
		event.setCancelled(true);
		Optional<GameModeSelector> selector = GameModeSelector.create(stage);
		selector.ifPresent(s -> {
			player.openInventory(s.createInventory());
			playSoundClick(player);
		});
	}

	@Override
	public Inventory createInventory() {
		Inventory inventory = Bukkit.createInventory(null, 54, getTitle());
		SentenceStorage.ownerSentences.keySet().stream()
			.map(stage -> {
				ItemStack item = new ItemStack(Material.WOOL);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(stage);
				item.setItemMeta(meta);
				return item;})
			.collect(Collectors.toList())
			.forEach(item -> inventory.addItem(item));
		return inventory;
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

}

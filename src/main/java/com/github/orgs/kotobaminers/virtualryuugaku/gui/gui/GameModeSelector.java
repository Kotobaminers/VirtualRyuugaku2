package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;

public class GameModeSelector extends VRGGUI {
	public static final String TITLE = "Select Game Mode!";
	private String stage = "";

	public GameModeSelector(String stage) {
		this.stage = stage;
	}

	public static Optional<GameModeSelector> create(String stage) {
		if (SentenceStorage.units.containsKey(stage)) {
			GameModeSelector selector = new GameModeSelector(stage);
			return Optional.of(selector);
		}
		return Optional.empty();
	}

	@Override
	public String getTitle() {
		return TITLE;
	}
	public String getStage() {
		return stage;
	}

	@Override
	public Inventory createInventory() {
		Inventory inventory = Bukkit.createInventory(null, 54, getTitle());
		Stream.of(GUIIcon.YOUR_NPC, GUIIcon.FREE, GUIIcon.TOUR, GUIIcon.TRAINING, GUIIcon.ANKI)
			.map(mode -> {
				ItemStack item = mode.createItem();
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(stage);
				lore.addAll(meta.getLore());
				meta.setLore(lore);
				item.setItemMeta(meta);
				return item;
				})
			.forEach(item -> inventory.addItem(item));
		return inventory;
	}
}


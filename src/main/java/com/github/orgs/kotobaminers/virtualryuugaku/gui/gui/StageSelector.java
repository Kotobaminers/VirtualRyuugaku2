package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;

public class StageSelector extends VRGGUI {
	public static final String TITLE = "Select Stage!";

	@Override
	public Inventory createInventory() {
		Inventory inventory = Bukkit.createInventory(null, size, getTitle());
		SentenceStorage.units.keySet().stream()
			.map(stage -> {
				ItemStack item = GUIIcon.UNIT.createItem();
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(stage);
				item.setItemMeta(meta);
				return item;})
			.collect(Collectors.toList())
			.forEach(item -> inventory.addItem(item));
		Integer position = new Integer(size - 1);
		List<ItemStack> items = Arrays.asList(GUIIcon.OPTION).stream()
			.map(icon -> icon.createItem())
			.collect(Collectors.toList());
		for (ItemStack item : items) {
			inventory.setItem(position--, item);
		}
		return inventory;
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

}

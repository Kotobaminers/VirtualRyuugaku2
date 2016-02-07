package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class VRGGUI {
	protected static final int size = 54;

	public abstract String getTitle();
	public abstract Inventory createInventory();

	public static Optional<VRGGUI> createOnPlayerInteract(PlayerInteractEvent event) {
		Action action = event.getAction();
		if (action == Action.PHYSICAL) {
			return Optional.empty();
		}
		ItemStack item = event.getItem();
		if (item == null) {
			return Optional.empty();
		}
		if (item.getType() == Material.BOOK) {
			return Optional.of(new StageSelector());
		}
		return Optional.empty();
	}

	public static boolean hasValidItem(ItemStack item) {
		if (item == null
				|| item.getType() == Material.AIR
				|| !item.hasItemMeta()) {
			return false;
		}
		return true;
	}

	public static boolean isValidSlot(InventoryClickEvent event) {
		if(event.getRawSlot() < size) {
			return true;
		}
		return false;
	}
}


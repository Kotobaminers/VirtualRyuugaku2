package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class VRGGUI {
	public abstract String getTitle();

	public abstract void eventInventoryClick(InventoryClickEvent event);
	public abstract Inventory createInventory();

	public static Optional<VRGGUI> createOnInventoryClick(InventoryClickEvent event) {
		switch(event.getInventory().getTitle()) {
			case LearnerSentenceSelector.TITLE:
				return Optional.of(new LearnerSentenceSelector());
			case OwnerSentenceSelector.TITLE:
				return Optional.of(new OwnerSentenceSelector());
			case StageSelector.TITLE:
				return Optional.of(new StageSelector());
			case GameModeSelector.TITLE:
				return Optional.of(new GameModeSelector(event.getCurrentItem().getItemMeta().getLore().get(0)));
			default:
				break;
		}
		return Optional.empty();
	}

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

	public static boolean hasInvalidItem(ItemStack item) {
		if (item == null
				|| item.getType() == Material.AIR
				|| !item.hasItemMeta()) {
			return true;
		}
		return false;
	}

	protected void playSoundClick(Player player) {
		player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
	}
}


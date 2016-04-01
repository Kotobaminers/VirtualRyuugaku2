package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class VRGGUI {
	protected static int MAX_SIZE = 54;

	public abstract String getTitle();
	public abstract Inventory createInventory();
	public abstract void executeClickedEvent(InventoryClickEvent event);

	public static void eventClicked(InventoryClickEvent event) {
		String title = event.getInventory().getTitle();
		switch(title) {
		case UnitSelector.TITLE:
			Player player = (Player) event.getWhoClicked();
			new UnitSelector(player).executeClickedEvent(event);
			break;
		case SentenceOptionSelector.TITLE:
			new SentenceOptionSelector().executeClickedEvent(event);
			break;
		case OptionSelector.TITLE:
			new OptionSelector().executeClickedEvent(event);
			break;
		case HelperSentenceSelector.TITLE:
			new HelperSentenceSelector().executeClickedEvent(event);
			break;
		case PlayerSentenceSelector.TITLE:
			new PlayerSentenceSelector().executeClickedEvent(event);
			break;
		case GameModeSelector.TITLE:
			new GameModeSelector().executeClickedEvent(event);
			break;
		case SpawnNPCSelector.TITLE:
			new SpawnNPCSelector().executeClickedEvent(event);
			break;
		case RolePlaySelector.TITLE:
			new RolePlaySelector().executeClickedEvent(event);
			break;
		}
	}

	public static Optional<VRGGUI> createOnPlayerInteract(PlayerInteractEvent event) {
		Action action = event.getAction();
		if (action == Action.PHYSICAL) return Optional.empty();
		ItemStack item = event.getItem();
		if (item == null) return Optional.empty();
		if (item.getType() == Material.BOOK) return Optional.of(new UnitSelector(event.getPlayer()));
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
		if(event.getRawSlot() < MAX_SIZE) return true;
		return false;
	}
}


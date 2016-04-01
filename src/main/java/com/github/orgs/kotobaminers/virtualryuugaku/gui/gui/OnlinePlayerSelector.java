package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

import net.md_5.bungee.api.ChatColor;

public abstract class OnlinePlayerSelector extends VRGGUI {
	private static final List<String> lore = Arrays.asList(ChatColor.GREEN + "Online");
	@Override
	public Inventory createInventory() {
		Inventory inventory = Bukkit.createInventory(null, MAX_SIZE, getTitle());
		Bukkit.getOnlinePlayers().stream()
			.map(player -> {
				ItemStack item = GUIIcon.PLAYER_SKULL.createItem();
				Utility.setSkullOwner(item, player.getName());
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setLore(lore);
				item.setItemMeta(itemMeta);
				return item;
			}).forEach(i -> inventory.addItem(i));
		return inventory;
	}
}

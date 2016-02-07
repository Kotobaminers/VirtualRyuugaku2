package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerData;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;

import net.md_5.bungee.api.ChatColor;

public class OptionSelector extends VRGGUI {
	public static final String TITLE = "Select Options!";
	private PlayerData data = null;
	private static final List<GUIIcon> LANGUAGES = Arrays.asList(GUIIcon.DISPLAY_EN, GUIIcon.DISPLAY_KANJI, GUIIcon.DISPLAY_KANA, GUIIcon.DISPLAY_ROMAJI);
	private static final List<GUIIcon> OPTIONS = Arrays.asList(GUIIcon.BACK);
	private static final List<SpellType> SPELLS = Arrays.asList(SpellType.EN, SpellType.KANJI, SpellType.KANA, SpellType.ROMAJI);
	private static final String ON = "" + ChatColor.GREEN + ChatColor.BOLD + "ON";
	private static final String OFF = "" + ChatColor.RED + ChatColor.BOLD + "OFF";

	private OptionSelector() {};

	public static OptionSelector create(Player player) {
		OptionSelector selector = new OptionSelector();
		selector.data = PlayerDataStorage.getPlayerData(player);
		return selector;
	}

	@Override
	public String getTitle() {
		return TITLE;
	}
	@Override
	public Inventory createInventory() {
		Inventory inventory = Bukkit.createInventory(null, 54, getTitle());

		List<ItemStack> items = LANGUAGES.stream()
			.map(icon -> icon.createItem())
			.collect(Collectors.toList());
		List<String> lores = SPELLS.stream()
			.map(spell -> data.expressions.contains(spell))
			.map(valid -> {
				if(valid) {
					return ON;
				} else {
					return OFF;
				}})
			.collect(Collectors.toList());
		for (int i = 0; i < items.size(); i++) {
			ItemStack itemStack = items.get(i);
			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setLore(Arrays.asList(lores.get(i)));
			itemStack.setItemMeta(itemMeta);
			inventory.addItem(itemStack);
		}
		OPTIONS.stream().forEach(opt -> inventory.addItem(opt.createItem()));
		return inventory;
	}
}


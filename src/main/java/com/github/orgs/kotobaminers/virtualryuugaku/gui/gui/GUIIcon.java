package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum GUIIcon {
	PLAYER_SKULL(Material.SKULL_ITEM, 3, "Speaker", null),
	EN(Material.WOOL, 3, "Enter English", null),
	KANJI(Material.WOOL, 14, "Enter kanji", null),
	KANA(Material.WOOL, 1, "Enter kana", null),
	ROMAJI(Material.WOOL, 2, "Enter romaji", null),
	HELPER_QUESTION(Material.WOOL, 5, "Question", null),

	KEY(Material.GOLD_INGOT, 0, "KEY", null),
	PREPEND_SENTENCE(Material.WOOL, 8, "Prepend a new sentence", null),
	APPEND_SENTENCE(Material.WOOL, 7, "Append a new sentence", null),
	REMOVE_SENTENCE(Material.GLASS, 0, "Remove this sentence", null),
	CHANGE_SPEAKER(Material.SKULL_ITEM, 0, "Chage the speaker", null),
	BACK(Material.BARRIER, 0, "Back to the previous menu", null),

	SPAWN_NPC(Material.BED, 0, "Set your spawn NPC's conversation", null),

	PLAYER_QUESTION(Material.WOOL, 0, "Question", null),

	FREE_UP(Material.GLASS_BOTTLE, 0, "Free up this NPC", null),
	RESPAWN(Material.EMERALD, 0, "Reload this NPC", null),

	FREE(Material.FEATHER, 0, "Free", Arrays.asList("To Look Around")),
	TOUR(Material.COMPASS, 0, "Tour", Arrays.asList("To Join A Tour")),
	TRAINING(Material.IRON_SPADE, 0, "Training", Arrays.asList("To Start Training")),
	ANKI(Material.BOOK_AND_QUILL, 0, "Anki", Arrays.asList("To Memorize Sentences")),
	YOUR_NPC(Material.SKULL_ITEM, 4, "Your NPC", Arrays.asList("To Create Your NPC")),

	UNIT_DONE_0(Material.REDSTONE_BLOCK, 0, "Unit", null),
	UNIT_DONE_1(Material.GOLD_BLOCK, 0, "Unit", null),
	UNIT_DONE_2(Material.EMERALD_BLOCK, 0, "Unit", null),
	UNIT_DONE_MAX(Material.DIAMOND_BLOCK, 0, "Unit", null),
	OPTION(Material.RECORD_10, 0, "Option", null),

	DISPLAY_EN(Material.STAINED_GLASS, 3, "Display English", null),
	DISPLAY_KANJI(Material.STAINED_GLASS, 14, "Display kanji", null),
	DISPLAY_KANA(Material.STAINED_GLASS, 1, "Display kana", null),
	DISPLAY_ROMAJI(Material.STAINED_GLASS, 2, "Display romaji", null),
	ROLE_PLAY(Material.YELLOW_FLOWER, 0, "Role Play", null),
	;

	private Material material;
	private short data;
	private String displayName;
	private Optional<List<String>> lore;

	private GUIIcon(Material material, int data, String displayName, List<String> lore) {
		this.material = material;
		this.data = (short) data;
		this.displayName = displayName;
		this.lore = Optional.ofNullable(lore);
	}

	public static Optional<GUIIcon> create(InventoryClickEvent event) {
		return Stream.of(GUIIcon.values())
			.filter(i -> i.getMaterial().equals(event.getCurrentItem().getType()) && i.getData() == event.getCurrentItem().getDurability())
			.findFirst();
	}

	public ItemStack createItem() {
		ItemStack item = new ItemStack(this.material, 1, data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayName);
		lore.ifPresent(l -> meta.setLore(l));
		item.setItemMeta(meta);
		return item;
	}

	public Material getMaterial() {
		return material;
	}
	public short getData() {
		return data;
	}
	public Optional<List<String>> getLore() {
		return lore;
	}
}


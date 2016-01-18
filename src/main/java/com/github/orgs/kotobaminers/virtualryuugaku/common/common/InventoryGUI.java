package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;

import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class InventoryGUI {
	public static final String SELECTOR_NPC = "" + ChatColor.GREEN + ChatColor.BOLD +  "Select NPC!";
	public static final String SELECTOR_MODE = "" + ChatColor.AQUA + ChatColor.BOLD + "Select Game Mode!";
	public static final String SELECTOR_STAGE = "" + ChatColor.RED + ChatColor.BOLD + "Select Stage!";
	public static final String MODE_FREE = "Free";
	public static final List<String> LORE_FREE = Arrays.asList("To look around", "(No Games)");
	public static final String MODE_TOUR = "Tour";
	public static final List<String> LORE_TOUR = Arrays.asList("To look around in order");

	public static Inventory createStageSelector() {
		Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, SELECTOR_STAGE);
		List<String> stages = SentenceStorage.sentences.keySet().stream().collect(Collectors.toList());
		stages.stream().forEach(stage -> {
			ItemStack item = new ItemStack(Material.values()[stages.indexOf(stage) + 1]);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(stage);
			item.setItemMeta(meta);
			inventory.setItem(stages.indexOf(stage), item);
		});
		return inventory;
	}

	public static Inventory createGameModeSelector(String stage) {
		Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, SELECTOR_MODE);

		ItemStack item = new ItemStack(Material.FEATHER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MODE_FREE);
		List<String> lore = new ArrayList<String>();
		lore.add(stage);
		lore.addAll(LORE_FREE);
		meta.setLore(lore);
		item.setItemMeta(meta);
		inventory.addItem(item);

		item = new ItemStack(Material.COMPASS);
		meta = item.getItemMeta();
		meta.setDisplayName(MODE_TOUR);
		lore = new ArrayList<String>();
		lore.add(stage);
		lore.addAll(LORE_TOUR);
		meta.setLore(lore);
		item.setItemMeta(meta);
		inventory.addItem(item);

		return inventory;
	}

	public Inventory createSelectNPCGUI(String stage) {
		Set<Integer> ids = new HashSet<Integer>();
		SentenceStorage.findStage.apply(stage).get().stream()
			.forEach(ls -> ls.forEach(s -> ids.add(s.getId())));
		Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, "Select NPC");
		List<ItemStack> skulls = ids.stream().map(id -> NPCHandler.findNPC(id))
			.filter(npc -> npc.isPresent())
			.map(npc -> getSkull.apply(npc.get()))
			.collect(Collectors.toList());

		for (int i = 0; i < skulls.size(); i++) {
			inventory.setItem(i, skulls.get(i));
		}
		return inventory;
	}


	private static Function<NPC, ItemStack> getSkull = (npc) -> {
		if(npc.data().has("cached-skin-uuid")) {
			Object uuid= npc.data().get("cached-skin-uuid", UUID.class);
			String id = uuid.toString();
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
			SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
			skullMeta.setLore(Arrays.asList("キヌゴシ村へようこそ！", "この文章は長い場合どんな風に。", "a", "a", "a", "a", "a", "a", "a"));
			skullMeta.setOwner(id);
			skullMeta.setDisplayName(npc.getName());
			skull.setItemMeta(skullMeta);
			return skull;
		} else {
			return new ItemStack(Material.STONE);
		}
	};


}

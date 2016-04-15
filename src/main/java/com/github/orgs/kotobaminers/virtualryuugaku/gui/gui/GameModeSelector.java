package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.UnitStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

public class GameModeSelector extends VRGGUI {
	public static final String TITLE = "Select Game Mode!";
	private String unit = "";

	public GameModeSelector(String unit) {
		this.unit = unit;
	}

	public GameModeSelector() {
	}

	public static Optional<GameModeSelector> create(String unit) {
		if (UnitStorage.units.containsKey(unit.toUpperCase())) {
			GameModeSelector selector = new GameModeSelector(unit);
			return Optional.of(selector);
		}
		return Optional.empty();
	}

	@Override
	public String getTitle() {
		return TITLE;
	}
	public String getUnit() {
		return unit;
	}

	@Override
	public Inventory createInventory() {
		Inventory inventory = Bukkit.createInventory(null, VRGGUI.MAX_SIZE, getTitle());
		getGUIIcons().stream()
			.map(mode -> {
				ItemStack item = mode.createItem();
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(unit);
				lore.addAll(meta.getLore());
				meta.setLore(lore);
				item.setItemMeta(meta);
				return item;
				})
			.forEach(item -> inventory.addItem(item));
		return inventory;
	}

	@Override
	public void executeClickedEvent(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			GUIIcon.create(event).ifPresent(icon -> {
				switch (icon) {
				case ANKI:
				case FREE:
				case TOUR:
				case TRAINING:
					UnitStorage.findUnit(event.getCurrentItem().getItemMeta().getLore().get(0).toUpperCase())
					.ifPresent(u -> u.getHelperSentences().stream()
							.findFirst().ifPresent(ls -> ls.stream()
									.findFirst().ifPresent(s ->
									NPCUtility.findNPC(s.getId())
									.ifPresent(npc -> {
										Utility.teleportToNPC((Player) event.getWhoClicked(), npc);
										Utility.sendTitle(
												(Player) event.getWhoClicked(),
												event.getCurrentItem().getItemMeta().getLore().get(0),
												icon.getLore().get().get(0));
									}))));
					return;
				case YOUR_NPC:
					UnitStorage.findUnit(event.getCurrentItem().getItemMeta().getLore().get(0).toUpperCase())
					.ifPresent(unit -> {
						List<Integer> ids = unit.getPlayerNPCIds();
						if (0< ids.size()) {
							NPCUtility.findNPC(ids.get(0)).ifPresent(npc -> {
								Utility.teleportToNPC((Player) event.getWhoClicked(), npc);
								Utility.sendTitle(
										(Player) event.getWhoClicked(),
										event.getCurrentItem().getItemMeta().getLore().get(0),
										icon.getLore().get().get(0));
							});
						}});
					return;
				case BACK:
					player.openInventory(new UnitSelector(player).createInventory());
					player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
					return;
				default:
					break;
				}
			});
		}
	}

	public List<GUIIcon> getGUIIcons() {
		return Arrays.asList(GUIIcon.YOUR_NPC, GUIIcon.FREE, GUIIcon.TOUR, GUIIcon.TRAINING, GUIIcon.ANKI);
	}
}


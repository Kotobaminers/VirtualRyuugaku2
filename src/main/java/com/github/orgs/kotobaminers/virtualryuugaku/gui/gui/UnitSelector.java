package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.QuestionSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.Unit;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.UnitScore;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.UnitStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerData;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

import net.md_5.bungee.api.ChatColor;

public class UnitSelector extends VRGGUI {
	public static final String TITLE = "Select Stage!";
	private Optional<Player> player = Optional.empty();

	public UnitSelector(Player player) {
		this.player = Optional.of(player);
	}

	private ItemStack createIcon(Unit unit) {
		PlayerData data = PlayerDataStorage.getPlayerData(player.get());
		UnitScore score = player.map(p -> {
			return data.scores.getOrDefault(unit.getName(), new UnitScore(""));
		}).orElse(new UnitScore(""));
		List<Integer> questions = unit.getHelperSentences().stream()
			.flatMap(ls -> ls.stream().filter(s -> s instanceof QuestionSentence))
			.map(s -> s.getId())
			.collect(Collectors.toList());
		long count = score.done.stream().filter(d -> questions.contains(d)).count();
		String number = count + " / " + questions.size();
		if (questions.size() <= count ) {
			number = "" + ChatColor.GREEN + ChatColor.BOLD + number;
		} else {
			number = "" + ChatColor.RED + ChatColor.BOLD + number;
		}
		number = "Questions: " + number;

		long rate = unit.calculateAchievementRate(data);
		String achievement = "Achievement: ";
		if (rate == 0) {
			achievement += ChatColor.RED;
		} else {
			achievement += ChatColor.GREEN;
		}
		achievement += "" + ChatColor.BOLD + rate + "%";

		ItemStack item = GUIIcon.UNIT_DONE_0.createItem();
		if (100 <= rate) {
			item = GUIIcon.UNIT_DONE_MAX.createItem();
		} else if(90 <= rate) {
			item = GUIIcon.UNIT_DONE_2.createItem();
		} else if(50 <= rate) {
			item = GUIIcon.UNIT_DONE_1.createItem();
		} else {
			item = GUIIcon.UNIT_DONE_0.createItem();
		}

		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setLore(Arrays.asList(achievement, number));
		itemMeta.setDisplayName(unit.getName());
		item.setItemMeta(itemMeta);
		return item;
	}

	@Override
	public Inventory createInventory() {
		Inventory inventory = Bukkit.createInventory(null, MAX_SIZE, getTitle());
		UnitStorage.units.keySet().stream().sorted().map(key -> UnitStorage.units.get(key))
			.map(unit -> createIcon(unit))
			.forEach(inventory::addItem);
		Integer position = new Integer(MAX_SIZE - 1);
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

	@Override
	public void executeClickedEvent(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			GUIIcon.create(event).ifPresent(icon -> {
				switch (icon) {
				case OPTION:
					player.openInventory(	OptionSelector.create(player).createInventory());
					player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
					return;
				case UNIT_DONE_0:
				case UNIT_DONE_1:
				case UNIT_DONE_2:
				case UNIT_DONE_MAX:
					UnitStorage.findUnit(event.getCurrentItem().getItemMeta().getDisplayName().toUpperCase())
						.map(u -> u.getHelperSentences())
						.flatMap(lls -> lls.stream().findFirst())
						.flatMap(ls -> ls.stream().findFirst())
						.map(s -> s.getId())
						.flatMap(id -> NPCUtility.findNPC(id))
						.ifPresent(npc -> {
							Utility.teleportToNPC((Player) event.getWhoClicked(), npc);
							Utility.sendTitle(
									(Player) event.getWhoClicked(),
									event.getCurrentItem().getItemMeta().getDisplayName(),
									"Enjoy Learning Languages");
						});
					return;
				default:
					break;
			}});
		}
	}
	public void setPlayer(Player player) {
		this.player = Optional.of(player);
	}
}

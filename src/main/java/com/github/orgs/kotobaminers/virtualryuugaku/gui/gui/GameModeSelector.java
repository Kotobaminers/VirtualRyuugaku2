package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.Optional;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.GameMode;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

public class GameModeSelector extends VRGGUI {
	public static final String TITLE = "Select Game Mode!";
	private String stage = "";

	public GameModeSelector(String stage) {
		this.stage = stage;
	}

	public static Optional<GameModeSelector> create(String stage) {
		if (SentenceStorage.ownerSentences.containsKey(stage)) {
			GameModeSelector selector = new GameModeSelector(stage);
			return Optional.of(selector);
		}
		return Optional.empty();
	}

	@Override
	public String getTitle() {
		return TITLE;
	}
	public String getStage() {
		return stage;
	}

	@Override
	public void eventInventoryClick(InventoryClickEvent event) {
		event.getWhoClicked().closeInventory();
		SentenceStorage.findStage.apply(stage)
			.ifPresent(lls -> lls.stream()
				.findFirst().ifPresent(ls -> ls.stream()
					.findFirst().ifPresent(s ->
						NPCHandler.findNPC(s.getId())
							.ifPresent(npc -> {
								Utility.teleportToNPC((Player) event.getWhoClicked(), npc);
								Utility.sendTitle(
										(Player) event.getWhoClicked(),
										stage,
										GameMode.create(event.getCurrentItem().getType()).getSubtitle());
							})
		)));
		event.setCancelled(true);
	}

	@Override
	public Inventory createInventory() {
		Inventory inventory = Bukkit.createInventory(null, 54, getTitle());
		Stream.of(GameMode.values())
			.map(mode -> mode.createIcon(stage))
			.forEach(icon -> inventory.addItem(icon));
		return inventory;
	}
}

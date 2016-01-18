package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.InventoryGUI;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicEventGame;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGameController;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGameController.PublicGameMode;
import com.github.orgs.kotobaminers.virtualryuugaku.publictour.publictour.PublicTourController;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class Events implements Listener {
	@EventHandler
	public void onClickNPCLeft(NPCLeftClickEvent event) {
		Player player = event.getClicker();
		if(PublicGameController.game instanceof PublicEventGame &&
				PublicGameController.mode.equals(PublicGameMode.FIND_PEOPLE)) {
			PublicEventGame eventGame = (PublicEventGame) PublicGameController.game;
			eventGame.eventTouchNPC(event.getNPC(), player);
		}
	}

	@EventHandler
	public void onClickNPCRight(NPCRightClickEvent event) {
		NPC npc = event.getNPC();
		SentenceStorage.talk(event.getClicker(), npc.getId());
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action action = event.getAction();
		if (action == Action.PHYSICAL) {
			return;
		}
		ItemStack item = event.getItem();
		if (item == null) {
			return;
		}
		if (item.getType() == Material.BOOK) {
			event.getPlayer().openInventory(InventoryGUI.createStageSelector());
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().equalsIgnoreCase(InventoryGUI.SELECTOR_STAGE)) {
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);
			if (hasInvalidItem(event.getCurrentItem())) {
				player.closeInventory();
				return;
			}
			player.openInventory(	InventoryGUI.createGameModeSelector(event.getCurrentItem().getItemMeta().getDisplayName()));
			return;

		} else if (event.getInventory().getTitle().equalsIgnoreCase(InventoryGUI.SELECTOR_MODE)) {
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);
			if (hasInvalidItem(event.getCurrentItem())) {
				player.closeInventory();
				return;
			}
			if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(InventoryGUI.MODE_FREE)) {
				SentenceStorage.findStage.apply(event.getCurrentItem().getItemMeta().getLore().get(0))
					.ifPresent(stage -> stage.stream().findFirst()
					.ifPresent(list -> list.stream().findFirst()
					.ifPresent(s -> NPCHandler.findNPC(s.getId())
					.ifPresent(npc -> Utility.teleportToNPC(player, npc)))));
				Utility.sendTitle(player, event.getCurrentItem().getItemMeta().getLore().get(0), "Enjoy Learning!");
			} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(InventoryGUI.MODE_TOUR)) {
				PublicTourController.join(event.getCurrentItem().getItemMeta().getLore().get(0), player);
			}
			return;
		}
	}

	private boolean hasInvalidItem(ItemStack item) {
		if (item == null
				|| item.getType() == Material.AIR
				|| !item.hasItemMeta()) {
			return true;
		}
		return false;
	}
}

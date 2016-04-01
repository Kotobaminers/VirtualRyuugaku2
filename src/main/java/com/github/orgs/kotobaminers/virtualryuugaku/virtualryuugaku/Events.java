package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HologramStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.OnlinePlayerNPCs;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceEditor.EditMode;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.UnitStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.GameModeSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.HelperSentenceSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.OptionSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.PlayerSentenceSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.SentenceOptionSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.SentenceSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.SpawnNPCSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.UnitSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.VRGGUI;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerData;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class Events implements Listener {
	@EventHandler
	public void onClickNPCLeft(NPCLeftClickEvent event) {
		NPC npc = event.getNPC();
		Player player = event.getClicker();
		PlayerData playerData = PlayerDataStorage.getPlayerData(player);

		if (playerData.editor.isPresent()) {
			if (playerData.editor.get().checkEditMode(EditMode.CHANGE_ID)) {
				playerData.editor.get().eventChangeId(npc, player);
				return;
			}
		}

		playerData.selectNPC(npc);

		if(UnitStorage.eventLeftClickPlayerNPC(npc, player)) {
			return;
		}
		SentenceSelector.create(event.getNPC()).ifPresent(selector -> player.openInventory(selector.createInventory()));
	}

	@EventHandler
	public void onClickNPCRight(NPCRightClickEvent event) {
		PlayerDataStorage.getPlayerData(event.getClicker()).selectNPC(event.getNPC());
		UnitStorage.findDisplayedUnit(event.getNPC()).ifPresent(unit ->
			HologramStorage.updateHologram(event.getNPC(), unit, event.getClicker()));
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		VRGGUI.createOnPlayerInteract(event)
			.ifPresent(gui -> {
				event.getPlayer().openInventory(gui.createInventory());
				event.setCancelled(true);
			});
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!isVRGGUI(event.getInventory())) {
			return;
		}
		event.setCancelled(true);
		event.getWhoClicked().closeInventory();
		if (VRGGUI.hasValidItem(event.getCurrentItem()) && VRGGUI.isValidSlot(event)) {
			VRGGUI.eventClicked(event);
		}
	}

	private boolean isVRGGUI(Inventory inventory) {
		String title = inventory.getTitle();
		if (title.equalsIgnoreCase(UnitSelector.TITLE) ||
				title.equalsIgnoreCase(GameModeSelector.TITLE) ||
				title.equalsIgnoreCase(PlayerSentenceSelector.TITLE) ||
				title.equalsIgnoreCase(HelperSentenceSelector.TITLE) ||
				title.equalsIgnoreCase(SentenceOptionSelector.TITLE) ||
				title.equalsIgnoreCase(OptionSelector.TITLE) ||
				title.equalsIgnoreCase(SpawnNPCSelector.TITLE))
		{
			return true;
		}
		return false;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(VRGManager.plugin, new Runnable() {
			public void run() {
				OnlinePlayerNPCs.updateOnlineNPCs();
			}
		}, 20L);
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		OnlinePlayerNPCs.updateOnlineNPCs();
	}
}

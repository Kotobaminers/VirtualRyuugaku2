package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import java.util.List;
import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HologramStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceEditor.EditMode;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.GUIIcon;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.GameModeSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.LearnerSentenceSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.OptionSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.OwnerSentenceSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.SentenceOptionSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.SentenceSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.StageSelector;
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

		NPCUtility.updateLearnerNPC(npc, player);

		Optional<List<HolographicSentence>> sentences = SentenceStorage.findLS(npc.getId());
		if (!sentences.isPresent()) {
			return;
		}


		sentences.ifPresent(s -> SentenceSelector.create(s).ifPresent(ss -> player.openInventory(ss.createInventory())));


//		if(PublicGameController.game instanceof PublicEventGame &&
//				PublicGameController.mode.equals(PublicGameMode.FIND_PEOPLE)) {
//			PublicEventGame eventGame = (PublicEventGame) PublicGameController.game;
//			eventGame.eventTouchNPC(event.getNPC(), player);
//		}
	}

	@EventHandler
	public void onClickNPCRight(NPCRightClickEvent event) {
		PlayerDataStorage.getPlayerData(event.getClicker()).selectNPC(event.getNPC());
		SentenceStorage.findLS(event.getNPC().getId()).ifPresent(ls ->
			HologramStorage.updateHologram(event.getNPC(), ls, event.getClicker()));
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
			GUIIcon.create(event).ifPresent(icon -> icon.eventClicked(event));
		}
	}

	private boolean isVRGGUI(Inventory inventory) {
		String title = inventory.getTitle();
		if (title.equalsIgnoreCase(StageSelector.TITLE) ||
				title.equalsIgnoreCase(GameModeSelector.TITLE) ||
				title.equalsIgnoreCase(LearnerSentenceSelector.TITLE) ||
				title.equalsIgnoreCase(OwnerSentenceSelector.TITLE) ||
				title.equalsIgnoreCase(SentenceOptionSelector.TITLE) ||
				title.equalsIgnoreCase(OptionSelector.TITLE)) {
			return true;
		}
		return false;
	}
}

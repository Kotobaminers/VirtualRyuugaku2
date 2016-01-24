package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.GameModeSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.LearnerSentenceSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.OwnerSentenceSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.SentenceSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.StageSelector;
import com.github.orgs.kotobaminers.virtualryuugaku.gui.gui.VRGGUI;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class Events implements Listener {
	@EventHandler
	public void onClickNPCLeft(NPCLeftClickEvent event) {
		Player player = event.getClicker();
		NPC npc = event.getNPC();
		PlayerDataStorage.getDataPlayer(player).selectNPC(npc);

		//updateEmptyNPC()

		if(NPCHandler.isYourNPC(npc, player.getUniqueId())) {

		} else if(NPCHandler.isEmptyLearner(npc)) {
			SentenceStorage.findStageName(npc.getId()).ifPresent(stg -> {
				if(!SentenceStorage.learnerSentences.containsKey(player.getUniqueId())) {
					NPCHandler.ownNPC(npc, player);
					SentenceStorage.addEmptyLearnerSentence(player.getUniqueId(), stg);
					return;
				} else {
					Set<String> stages = new HashSet<>();
					SentenceStorage.learnerSentences.get(player.getUniqueId())
						.forEach(ls -> ls.forEach(s -> stages.add(s.getStage())));
					if(stages.stream().noneMatch(stg2 -> stg2.equalsIgnoreCase(stg))) {
						NPCHandler.ownNPC(npc, player);
						SentenceStorage.addEmptyLearnerSentence(player.getUniqueId(), stg);
						return;
					}
				}
			});
		}

		Optional<List<HolographicSentence>> sentences = SentenceStorage.findHolographicSentences.apply(npc);
		if(sentences.isPresent()) {
			SentenceSelector.create(sentences.get()).ifPresent(ss -> player.openInventory(ss.createInventory()));
			return;
		}






//		if(PublicGameController.game instanceof PublicEventGame &&
//				PublicGameController.mode.equals(PublicGameMode.FIND_PEOPLE)) {
//			PublicEventGame eventGame = (PublicEventGame) PublicGameController.game;
//			eventGame.eventTouchNPC(event.getNPC(), player);
//		}
	}

	@EventHandler
	public void onClickNPCRight(NPCRightClickEvent event) {
		SentenceStorage.showHolograms(event.getClicker(), event.getNPC());
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		VRGGUI.createOnPlayerInteract(event)
			.ifPresent(gui -> event.getPlayer().openInventory(gui.createInventory()));
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!isVRGGUI(event.getInventory())) {
			return;
		}
		if (VRGGUI.hasInvalidItem(event.getCurrentItem())) {
			event.setCancelled(true);
			event.getWhoClicked().closeInventory();
			return;
		}
		VRGGUI.createOnInventoryClick(event)
			.ifPresent(gui -> gui.eventInventoryClick(event));
	}

	private boolean isVRGGUI(Inventory inventory) {
		String title = inventory.getTitle();
		if (title.equalsIgnoreCase(StageSelector.TITLE) ||
				title.equalsIgnoreCase(GameModeSelector.TITLE) ||
				title.equalsIgnoreCase(LearnerSentenceSelector.TITLE) ||
				title.equalsIgnoreCase(OwnerSentenceSelector.TITLE)) {
			return true;
		}
		return false;
	}

}

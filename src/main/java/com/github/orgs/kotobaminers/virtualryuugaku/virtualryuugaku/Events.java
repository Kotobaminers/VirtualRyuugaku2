package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.StageController;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicEventGame;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGameController;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGameController.PublicGameMode;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public class Events implements Listener {
	public static EventCreate flagEventCreate = EventCreate.NONE;
	public static EventSelect flagEventSelect = EventSelect.NONE;
	public enum EventCreate {NONE, REGISTER_EMPTY}
	public enum EventSelect {NONE}

	@EventHandler
	public void onClickNPCLeft(NPCLeftClickEvent event) {
		Debug.printDebugMessage("", new Exception());
		Player player = event.getClicker();
		if(PublicGameController.game instanceof PublicEventGame &&
				PublicGameController.mode.equals(PublicGameMode.FIND_PEOPLE)) {
			PublicEventGame eventGame = (PublicEventGame) PublicGameController.game;
			eventGame.eventTouchNPC(event.getNPC(), player);
		}
	}

	@EventHandler
	public void onClickNPCRight(NPCRightClickEvent event) {
		Debug.printDebugMessage("", new Exception());
		NPC npc = event.getNPC();
		try {
			Conversation conversation = StageController.getConversation(npc.getId());
			conversation.talk(event.getClicker(), npc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@EventHandler
//	public void onJoin(PlayerJoinEvent event) {
//		UtilitiesProgramming.printDebugMessage("", new Exception());
//	}
}
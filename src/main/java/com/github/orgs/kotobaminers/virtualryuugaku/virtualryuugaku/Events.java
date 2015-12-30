package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class Events implements Listener {
	public static EventCreate flagEventCreate = EventCreate.NONE;
	public static EventSelect flagEventSelect = EventSelect.NONE;
	public enum EventCreate {NONE, REGISTER_EMPTY}
	public enum EventSelect {NONE}

	@EventHandler
	public void onClickNPCLeft(NPCLeftClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Player player = event.getClicker();
	}

	@EventHandler
	public void onClickNPCRight(NPCRightClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		NPC npc = event.getNPC();

		try {
			Conversation conversation = ControllerConversation.getConversation(npc);
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
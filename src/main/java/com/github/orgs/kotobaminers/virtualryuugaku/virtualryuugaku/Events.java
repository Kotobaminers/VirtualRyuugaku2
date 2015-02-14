package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import net.citizensnpcs.api.event.NPCCreateEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.event.NPCSelectEvent;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.comment.CommentHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.EventConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class Events implements Listener {
	public static EventCreate flagEventCreate = EventCreate.NONE;
	public static EventSelect flagEventSelect = EventSelect.NONE;
	public enum EventCreate {NONE, REGISTER_EMPTY}
	public enum EventSelect {NONE}

	@EventHandler
	public void onClickNPCLeft(NPCLeftClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
//		NPC npc = event.getNPC();
//		if(DataManagerConversation.existsTalker(npc)) {//Talker
//			Integer id = npc.getId();
//			Conversation talker = DataManagerConversation.getTalker(id);
//			new EventConversation(npc, talker, event.getClicker()).printKeySentence();
//		} else {
//			UtilitiesProgramming.printDebugMessage("NON talker. ID: " + npc.getId(), new Exception());
//		}
	}

	@EventHandler
	public void onClickNPCRight(NPCRightClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		NPC npc = event.getNPC();
		if(DataManagerConversation.existsConversation(npc)) {
			Integer id = npc.getId();
			Conversation conversation = DataManagerConversation.getConversation(id);
			if(conversation.hasEditor()) {//Talker without editors is Empty Talker
				new EventConversation(npc, conversation, event.getClicker()).talk();
			} else {
				new EventConversation(npc, conversation, event.getClicker()).ownEmptyTalker();
			}
			return;
		} else {
			UtilitiesProgramming.printDebugMessage("NON talker: ID: " + npc.getId(), new Exception());
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Player player = event.getPlayer();
		try {
			CommentHandler.printCommentNew(player);
		} catch(Exception e) {
			UtilitiesProgramming.printDebugMessage("ERROR: ", e);
		}
	}

	@EventHandler
	public void onCreateNPC(NPCCreateEvent event) {//This event is triggered when the server enabled.
		UtilitiesProgramming.printDebugMessage("NPCCreateEvent: Flag = " + flagEventCreate, new Exception());
		EventCreate flag = EventCreate.valueOf(flagEventCreate.toString());
		resetEventFlag();
		switch(flag) {
		case REGISTER_EMPTY:
			Player player = Bukkit.getPlayer(DataEventCreate.player.getUniqueId());
			String stage = DataEventCreate.nameStage.toUpperCase();
			DataEventCreate.initialize();
			NPC npc = event.getNPC();
			Conversation talker = DataManagerConversation.getConversation(npc.getId());
			new EventConversation(npc, talker, player).registerEmptyTalker(stage);
			break;
		case NONE:
			break;
		default:
			break;
		}
	}

	@EventHandler
	public void onSelectNPC(NPCSelectEvent event) {
		UtilitiesProgramming.printDebugMessage("NPCSelectEvent", new Exception());
	}

	private void resetEventFlag() {
		flagEventCreate = EventCreate.NONE;
	}



}

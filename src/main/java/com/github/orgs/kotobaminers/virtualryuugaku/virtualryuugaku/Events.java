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

import com.github.orgs.kotobaminers.virtualryuugaku.talker.comment.CommentHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.DataManagerTalker;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.EventTalker;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.Talker;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class Events implements Listener {
	public static EventCreate flagEventCreate = EventCreate.NONE;
	public static EventSelect flagEventSelect = EventSelect.NONE;
	public enum EventCreate {NONE, REGISTER_EMPTY}
	public enum EventSelect {NONE}

	@EventHandler
	public void onClickNPCLeft(NPCLeftClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		NPC npc = event.getNPC();
		if(DataManagerTalker.existsTalker(npc)) {//Talker
			Integer id = npc.getId();
			Talker talker = DataManagerTalker.getTalker(id);
			new EventTalker(npc, talker, event.getClicker()).printKeySentence();
		} else {
			UtilitiesProgramming.printDebugMessage("NON talker. ID: " + npc.getId(), new Exception());
		}
	}

	@EventHandler
	public void onClickNPCRight(NPCRightClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		NPC npc = event.getNPC();
		if(DataManagerTalker.existsTalker(npc)) {
			Integer id = npc.getId();
			Talker talker = DataManagerTalker.getTalker(id);
			if(talker.hasEditor()) {//Talker without editors is Empty Talker
				new EventTalker(npc, talker, event.getClicker()).talk();
			} else {
				new EventTalker(npc, talker, event.getClicker()).ownEmptyTalker();
			}
			return;
		} else {
			UtilitiesProgramming.printDebugMessage("NON talker. ID: " + npc.getId(), new Exception());
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
			Talker talker = DataManagerTalker.getTalker(npc.getId());
			new EventTalker(npc, talker, player).registerEmptyTalker(stage);
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

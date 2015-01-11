package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.fukushima.kai.talker.stage.EventStage;
import com.gmail.fukushima.kai.talker.talker.DataManagerTalker;
import com.gmail.fukushima.kai.talker.talker.EventTalker;
import com.gmail.fukushima.kai.talker.talker.Talker;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class Events implements Listener {
	@EventHandler
	public void onClickNPCLeft(NPCLeftClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		NPC npc = event.getNPC();
		if(Talker.isTalker(npc)) {//Talker
			Integer id = npc.getId();
			Talker talker = DataManagerTalker.getTalker(id);
			new EventTalker(npc, talker, event.getClicker()).quest();
		} else {
			UtilitiesProgramming.printDebugMessage("This is not a talker.", new Exception());
		}
	}
	@EventHandler
	public void onClickNPCRight(NPCRightClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		NPC npc = event.getNPC();
		EntityType type = npc.getEntity().getType();
		switch(type) {
		case CREEPER:
			new EventStage(npc, event.getClicker()).createTalker();
			return;
		case PLAYER:
			if(Talker.isTalker(npc)) {//Talker
				Integer id = event.getNPC().getId();
				Talker talker = DataManagerTalker.getTalker(id);
				new EventTalker(npc, talker, event.getClicker()).talk();
			} else {//Not Talker
				UtilitiesProgramming.printDebugMessage("This is not a talker.", new Exception());
			}
		default:
			break;
		}

	}
}
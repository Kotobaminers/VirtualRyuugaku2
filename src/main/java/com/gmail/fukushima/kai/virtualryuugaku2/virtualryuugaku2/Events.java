package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.fukushima.kai.shadow.shadow.EventShadow;
import com.gmail.fukushima.kai.talker.talker.DataManagerTalker;
import com.gmail.fukushima.kai.talker.talker.EventTalker;
import com.gmail.fukushima.kai.talker.talker.Talker;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class Events implements Listener {
	@EventHandler
	public void onClickNPCLeft(NPCLeftClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		NPC npc = event.getNPC();
		Integer id = npc.getId();
		Talker talker = DataManagerTalker.getTalker(id);
		new EventTalker(npc, talker, event.getClicker()).quest();
	}
	@EventHandler
	public void onClickNPCRight(NPCRightClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		NPC npc = event.getNPC();
		Entity entity = npc.getEntity();
		EntityType type = entity.getType();
		switch(type) {
		case ENDER_CRYSTAL:
			new EventShadow(npc, event.getClicker()).create();
			return;
		case PLAYER:
			Integer id = event.getNPC().getId();
			Talker talker = DataManagerTalker.getTalker(id);
			new EventTalker(npc, talker, event.getClicker()).talk();
		default:
			break;
		}
	}
}
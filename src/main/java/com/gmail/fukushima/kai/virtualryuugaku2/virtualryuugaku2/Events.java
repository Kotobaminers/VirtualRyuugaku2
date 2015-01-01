package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.fukushima.kai.common.common.EventEmpty;
import com.gmail.fukushima.kai.mystage.mystage.DataManagerStage;
import com.gmail.fukushima.kai.mystage.mystage.Stage;
import com.gmail.fukushima.kai.mystage.talker.EventTalker;
import com.gmail.fukushima.kai.mystage.talker.Talker;
import com.gmail.fukushima.kai.utilities.utilities.Event;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class Events implements Listener {
	@EventHandler
	public void onClickNPCRight(NPCRightClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Event myEvent = loadEventClickRight(event);
		myEvent.runEvent();
	}
	@EventHandler
	public void onClickNPCLeft(NPCLeftClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		NPC npc = event.getNPC();
		Player player = event.getClicker();
		Owner owner = npc.getTrait(Owner.class);
		player.sendMessage("Owner" + owner.getOwner());
	}
	private Event loadEventClickRight(NPCClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		NPC npc = event.getNPC();
		for(Stage stage : DataManagerStage.listStage) {
			for(Talker talker : stage.listTalker) {
				if(talker.id.equals(npc.getId())) {
					return new EventTalker(npc, talker, event.getClicker());
				}
			}
		}
		return new EventEmpty();
	}
}
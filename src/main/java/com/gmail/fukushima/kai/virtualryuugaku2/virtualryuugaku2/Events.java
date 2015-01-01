package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.fukushima.kai.mystage.mystage.DataManagerStage;
import com.gmail.fukushima.kai.mystage.mystage.Stage;
import com.gmail.fukushima.kai.mystage.talker.EventTalker;
import com.gmail.fukushima.kai.mystage.talker.Talker;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class Events implements Listener {
	@EventHandler
	public void onClickNPCRight(NPCRightClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		NPC npc = event.getNPC();
		for(Stage stage : DataManagerStage.listStage) {
			for(Talker talker : stage.listTalker) {
				if(talker.id.equals(npc.getId())) {
					new EventTalker(npc, talker, event.getClicker()).talk();
				}
			}
		}
	}
	@EventHandler
	public void onClickNPCLeft(NPCLeftClickEvent event) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		NPC npc = event.getNPC();
		for(Stage stage : DataManagerStage.listStage) {
			for(Talker talker : stage.listTalker) {
				if(talker.id.equals(npc.getId())) {
					new EventTalker(npc, talker, event.getClicker()).quest();
				}
			}
		}
	}
}
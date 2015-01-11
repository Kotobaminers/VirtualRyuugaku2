package com.gmail.fukushima.kai.talker.stage;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.talker.talker.DataManagerTalker;
import com.gmail.fukushima.kai.talker.talker.Talker;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesGeneral;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class EventStage {
	public static final String markTopic = "@";
	public NPC npc;
	public Player player;
	public EventStage(NPC npc, Player player) {
		this.npc = npc;
		this.player = player;
	}
	public void createTalker() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String owner = npc.getTrait(Owner.class).getOwner();//Check: Owner
		if(!owner.equalsIgnoreCase("@VIRTUALRYUUGAKU")) {
			UtilitiesProgramming.printDebugMessage("Invalid Owner: " + owner, new Exception());
			return;
		}
		Integer id = npc.getId();
		String stage = npc.getName();
		if(!stage.startsWith(markTopic)) {//Check: Mark
			UtilitiesProgramming.printDebugMessage("Invalid TopicName: " + stage, new Exception());
			return;
		}
		stage = stage.substring(markTopic.length());
		//TODO check if stage exists
		UtilitiesGeneral.runCommandAsOP(player, "npc select " + id);
		UtilitiesGeneral.runCommandAsOP(player, "npc type player");
		UtilitiesGeneral.runCommandAsOP(player, "npc rename " + player.getName());
		UtilitiesGeneral.runCommandAsOP(player, "npc owner " + player.getName());
		UtilitiesGeneral.runCommandAsOP(player, "npc rotate");
		UtilitiesGeneral.runCommandAsOP(player, "npc skin " + player.getName());
		Talker talker = new Talker();
		talker.id = id;
		talker.name = player.getName();
		talker.editor.add(player.getName());
		talker.nameStage = stage;
		DataManagerTalker.registerTalker(talker);
	}
	public void talk() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
	}
}
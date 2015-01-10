package com.gmail.fukushima.kai.mytalker.mytalker;

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
	public void create() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Integer id = npc.getId();
		String nameTopic = npc.getName();
		if(!nameTopic.startsWith(markTopic)) {
			UtilitiesProgramming.printDebugMessage("Invalid TopicName: " + nameTopic, new Exception());
			return;
		}
		nameTopic = nameTopic.substring(markTopic.length());
//		if(!DataManagerTalker.exists(nameTopic)) {
//			UtilitiesProgramming.printDebugMessage("Invalid TopicName: " + nameTopic, new Exception());
//			return;
//		}
		String owner = npc.getTrait(Owner.class).getOwner();
		if(!owner.equalsIgnoreCase("@VIRTUALRYUUGAKU")) {
			UtilitiesProgramming.printDebugMessage("Invalid Owner: " + owner, new Exception());
			return;
		}
		Stage topic = DataManagerTalker.getStage(nameTopic);
		if(!topic.listId.contains(id)) {
			UtilitiesGeneral.runCommandAsOP(player, "npc select " + id);
			UtilitiesGeneral.runCommandAsOP(player, "npc type player");
			UtilitiesGeneral.runCommandAsOP(player, "npc rename " + player.getName());
			UtilitiesGeneral.runCommandAsOP(player, "npc owner " + player.getName());
			UtilitiesGeneral.runCommandAsOP(player, "npc skin " + player.getName());
			UtilitiesGeneral.runCommandAsOP(player, "npc rotate");
			Talker talker = new Talker();
			talker.id = id;
			talker.name = player.getName();
			talker.owner = player.getName();
//			DataManagerTalker.addTalker(player, nameTopic, talker);
		} else {
			player.sendMessage("You've already created your shadow in this topic.");
		}
	}
	public void talk() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
	}
}
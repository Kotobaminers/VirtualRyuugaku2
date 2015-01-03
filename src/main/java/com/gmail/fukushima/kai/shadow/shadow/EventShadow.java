package com.gmail.fukushima.kai.shadow.shadow;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.utilities.utilities.UtilitiesGeneral;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class EventShadow {
	public static final String markTopic = "@";
	public NPC npc;
	public Player player;
	public EventShadow(NPC npc, Player player) {
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
		if(!DataManagerShadowTopic.exists(nameTopic)) {
			UtilitiesProgramming.printDebugMessage("Invalid TopicName: " + nameTopic, new Exception());
			return;
		}
		DataShadowTopic topic = DataManagerShadowTopic.getDataShadowTopic(nameTopic);
		if(!topic.created.contains(player.getName())) {
			UtilitiesGeneral.runCommandAsOP(player, "npc select " + id);
			UtilitiesGeneral.runCommandAsOP(player, "npc type player");
			UtilitiesGeneral.runCommandAsOP(player, "npc rename " + player.getName());
			UtilitiesGeneral.runCommandAsOP(player, "npc skin " + player.getName());
			UtilitiesGeneral.runCommandAsOP(player, "npc rotate");
			DataManagerShadowTopic.addCreated(player, id, nameTopic);
		} else {
			player.sendMessage("You've already created your shadow in this topic.");
		}
	}
	public void talk() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
	}
}
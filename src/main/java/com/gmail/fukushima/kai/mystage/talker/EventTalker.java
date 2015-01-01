package com.gmail.fukushima.kai.mystage.talker;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.utilities.utilities.Event;

public class EventTalker extends Event {
	NPC npc;
	Talker talker;
	Player player;
	public EventTalker(NPC npc, Talker talker, Player player) {
		this.npc = npc;
		this.talker = talker;
		this.player = player;
	}

	@Override
	public void runEvent() {
		
	}
}
package com.gmail.fukushima.kai.mystage.talker;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.common.common.DataManagerPlayer;
import com.gmail.fukushima.kai.common.common.DataPlayer;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class EventTalker {
	public NPC npc;
	public Talker talker;
	public Player player;
	public EventTalker(NPC npc, Talker talker, Player player) {
		this.npc = npc;
		this.talker = talker;
		this.player = player;
	}

	public void talk() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		if(!data.select.equals(npc.getId())) {
			data.select = npc.getId();
			DataManagerPlayer.putDataPlayer(data);
			printSelect();
			return;
		} else {
			talker.talkNext(player, data);
			return;
		}
	}
	public void quest() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		if(!data.select.equals(npc.getId())) {
			data.select = npc.getId();
			DataManagerPlayer.putDataPlayer(data);
			printSelect();
			return;
		} else {
			talker.quest(player, data);
			return;
		}
	}

	public void printSelect() {
		player.sendMessage("You selected " + npc.getFullName());
	}
}
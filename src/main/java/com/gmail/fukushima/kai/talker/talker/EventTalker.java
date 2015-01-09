package com.gmail.fukushima.kai.talker.talker;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
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
			if(!talker.hasSentence()) return;
			talker.talkNext(player, data);
			return;
		}
	}
	public void quest() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		if(!data.isValid()) return;
		UtilitiesProgramming.printDebugMessage("", new Exception());
		UtilitiesProgramming.printDebugPlayer(data);
		if(!data.select.equals(npc.getId())) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			data.select = npc.getId();
			DataManagerPlayer.putDataPlayer(data);
			printSelect();
			return;
		} else {
			if(!talker.hasSentence()) return;
			talker.quest(player, data);
			return;
		}
	}

	public void printSelect() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		player.sendMessage("You selected " + npc.getFullName());
	}
}
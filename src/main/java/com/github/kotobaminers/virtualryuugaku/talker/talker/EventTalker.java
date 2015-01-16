package com.github.kotobaminers.virtualryuugaku.talker.talker;

import java.util.ArrayList;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.github.kotobaminers.virtualryuugaku.common.common.Messenger;
import com.github.kotobaminers.virtualryuugaku.common.common.Messenger.Message;
import com.github.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class EventTalker {
	private NPC npc;
	private Talker talker;
	private Player player;
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
			String[] opts = {talker.name};
			Messenger.print(player, Message.SELECT_TALKER_1, opts);
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
			String[] opts = {talker.name};
			Messenger.print(player, Message.SELECT_TALKER_1, opts);
			return;
		} else {
			talker.quest(player, data);
			return;
		}
	}

	public void ownEmptyTalker() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(Talker search : DataManagerTalker.getMapTalker().values()) {
			UtilitiesProgramming.printDebugMessage(search.stage + talker.stage, new Exception());
			if(search.stage.equalsIgnoreCase(talker.stage)) {
				if(search.editor.contains(player.getName())) {
					Messenger.print(player, Message.ALREADY_OWNED_0, null);
					return;
				}
			}
		}
		npc.setBukkitEntityType(EntityType.PLAYER);
		npc.setName(player.getName());//NPC's skin will change.
		talker.name = player.getName();
		talker.editor.add(player.getName());
		Messenger.print(player, Message.OWN_TALKER_0, null);
	}

	public void registerEmptyTalker(String stage) {
		talker.id = npc.getId();
		talker.stage = stage;
		talker.name = player.getName();
		talker.editor = new ArrayList<String>();
		DataManagerTalker.registerTalker(talker);
	}
}
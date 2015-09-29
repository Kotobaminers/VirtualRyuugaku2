package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.List;
import java.util.Set;

import net.citizensnpcs.api.npc.NPC;



public class ConversationMyself extends Conversation {

	@Override
	public List<Integer> getIDSorted() throws Exception {
		String name = "";
		if (0 < listTalk.size()) {
			name = listTalk.get(0).name;//TODO
		}
		throw new Exception("NPC not exists: Stage" + stageName + ", Name" + name);
	}

	@Override
	public boolean isChangebleID() {
		return true;
	}

	@Override
	public Set<NPC> getNPCs() {
		return null;
	}




}
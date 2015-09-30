package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import java.util.HashSet;
import java.util.Set;

import net.citizensnpcs.api.npc.NPC;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation.CheckState;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMyself;

public class StageMyself extends Stage {
	public Set<ConversationMyself> conversations = new HashSet<ConversationMyself>();

	@Override
	public Set<NPC> getNPCs() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Integer> getIDs() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void changeNPCs(CheckState check) {

	}
}

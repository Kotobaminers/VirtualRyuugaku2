package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.citizensnpcs.api.npc.NPC;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation.CheckState;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMyself;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.NPCHandler;

public class StageMyself extends Stage {
	public Set<ConversationMyself> conversations = new HashSet<ConversationMyself>();

	public void changeNPCs(CheckState check) {
		Set<ConversationMyself> conversationsMyself = getConversationsMyself(conversations, check);
		Set<Integer> ids = getIDsAll();
		List<NPC> npcs = new ArrayList<NPC>();
		List<String> players = new ArrayList<String>();
		for (ConversationMyself myself : conversationsMyself) {
			if (0 < myself.listTalk.size()) {
				players.add(myself.listTalk.get(0).name);
			}
		}

		for (Integer id : ids) {
			try {
				npcs.add(NPCHandler.getNPC(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (0 < npcs.size()) {
			Collections.shuffle(npcs);

			Integer max = npcs.size();
			if(players.size() < max) {
				max = players.size();
			}
			for (int i = 0; i < max; i++) {
				NPCHandler.changeNPCAsPlayer(npcs.get(i), players.get(i));
			}
			for (int i = max; i < ids.size(); i++) {
				NPCHandler.changeNPCAsEmpty(npcs.get(i));
			}
		}
	}

	public Set<Integer> getIDsAll() {
		String stageName = getStageName();
		Set<Integer> ids = new HashSet<Integer>();
		try {
			ids.addAll(ControllerConversation.getMyselfIDs(stageName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ids;
	}

	@Override
	public String getStageName() {
		String name = "";
		for (ConversationMyself conversation : conversations) {
			return conversation.stageName;
		}
		return name;
	}
}
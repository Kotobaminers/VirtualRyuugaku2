package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.citizensnpcs.api.npc.NPC;

import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.NPCHandler;



public class ConversationMyself extends Conversation {

	@Override
	public List<Integer> getIDSorted() {
		List<Integer> list = new ArrayList<Integer>();
		String name = "";
		if (0 < listTalk.size()) {
			name = listTalk.get(0).name;
			List<Integer> myselfIDs;
			try {
				myselfIDs = ControllerConversation.getMyselfIDs(stageName);
				Set<NPC> npcs = new HashSet<NPC>();
				for (Integer id : myselfIDs) {
					try {
						npcs.add(NPCHandler.getNPC(id));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (NPC npc : npcs) {
					if (npc.getName().equalsIgnoreCase(name)) {
						for (int i = 0; i < listTalk.size(); i++) {
							list.add(npc.getId());
						}
						return list;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
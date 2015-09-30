package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.List;

import net.citizensnpcs.api.npc.NPC;

import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.NPCHandler;



public class ConversationMyself extends Conversation {

	@Override
	public List<Integer> getIDSorted() throws Exception {
		String name = "";
		if (0 < listTalk.size()) {
			name = listTalk.get(0).name;
			if(ControllerConversation.existsMyselfStage(stageName)) {
				List<Integer> myselfIDs = ControllerConversation.getMyselfIDs(stageName);
				for (Integer id : myselfIDs) {
					NPC npc = NPCHandler.getNPC(id);
					if (npc.getName().equalsIgnoreCase(name)) {
						List<Integer> list = new ArrayList<Integer>();
						for (int i = 0; i < listTalk.size(); i++) {
							list.add(id);
						}
						return list;
					}
				}
			}
		}
		throw new Exception("NPC not exists: Stage" + stageName + ", Name" + name);
	}
}
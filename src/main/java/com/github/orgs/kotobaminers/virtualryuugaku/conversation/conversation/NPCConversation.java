package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.List;



public class NPCConversation extends Conversation {

	@Override
	public List<Integer> getIDSorted() {
		List<Integer> order = new ArrayList<Integer>();
		for(VRGSentence talk : sentences) {
			order.add(talk.id);
		}
		return order;
	}
}
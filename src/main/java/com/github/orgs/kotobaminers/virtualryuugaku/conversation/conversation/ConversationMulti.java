package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.List;

public class ConversationMulti extends Conversation {

	public List<Integer> getOrder() {
		List<Integer> order = new ArrayList<Integer>();
		for(Talk talk : listTalk) {
			order.add(talk.id);
		}
		return order;
	}

}
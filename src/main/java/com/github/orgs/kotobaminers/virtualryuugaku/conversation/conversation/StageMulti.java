package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.HashSet;
import java.util.Set;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation.CheckState;

public class StageMulti extends Stage {
	private StageMulti() {}
	public static StageMulti create() {
		return new StageMulti();
	}

	public Set<ConversationMulti> conversations = new HashSet<ConversationMulti>();

	@Override
	public Set<Conversation> getConversations(CheckState check) {
		Set<Conversation> set = new HashSet<Conversation>();
		for (ConversationMulti conversation : conversations) {
			if (conversation.getCheckState().equals(check)) {
				set.add(conversation);
			}
		}
		return set;
	}
	@Override
	public Set<Conversation> getConversations() {
		Set<Conversation> set = new HashSet<Conversation>();
		for (ConversationMulti conversation : conversations) {
			set.add((Conversation) conversation);
		}
		return set;
	}

}
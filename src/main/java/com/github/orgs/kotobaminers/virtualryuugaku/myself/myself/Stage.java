package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.citizensnpcs.api.npc.NPC;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation.CheckState;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMulti;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMyself;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.NPCHandler;

public abstract class Stage {

	public static StageMyself createStageMyself(String name) throws Exception {
		if (ControllerConversation.existsMyselfStage(name)) {
			StageMyself stage = new StageMyself();
			stage.conversations = new HashSet<ConversationMyself>();
			List<Conversation> conversations2 = ControllerConversation.getConversations(name);
			for (Conversation conversation : conversations2) {
				if (conversation instanceof ConversationMyself) {
					stage.conversations.add((ConversationMyself) conversation);
				}
			}
			if (0 < stage.conversations.size()) {
				return stage;
			}
		}
		throw new Exception("Invalid Stage Name: " + name);
	}

	public abstract String getStageName();

	public Set<ConversationMulti> getConversationsMulti(Set<ConversationMulti> conversationSet, CheckState check) {
		Set<ConversationMulti> conversations = new HashSet<ConversationMulti>();
		for (ConversationMulti conversation : conversationSet) {
			if (conversation.getCheckState().equals(check)) {
				conversations.add(conversation);
			}
		}
		return conversations;
	}

	public Set<ConversationMyself> getConversationsMyself(Set<ConversationMyself> conversationSet, CheckState check) {
		Set<ConversationMyself> conversations = new HashSet<ConversationMyself>();
		for (ConversationMyself conversation : conversationSet) {
			if (conversation.getCheckState().equals(check)) {
				conversations.add(conversation);
			}
		}
		return conversations;
	}

	public Set<NPC> getNPCs(Set<Conversation> conversations) {
		Set<Integer> ids = new HashSet<Integer>();
		for (Conversation conversation : conversations) {
			ids.addAll(conversation.getIDSorted());
		}
		Set<NPC> npcs = new HashSet<NPC>();
		for (Integer id : ids) {
			try {
				npcs.add(NPCHandler.getNPC(id));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return npcs;
	}
}
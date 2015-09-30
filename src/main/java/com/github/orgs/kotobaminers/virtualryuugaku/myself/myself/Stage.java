package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.citizensnpcs.api.npc.NPC;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation.CheckState;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMyself;

public abstract class Stage {
	private List<Conversation> conversations = new ArrayList<Conversation>();


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

	public String getName() {
		return conversations.get(0).stageName;
	}

	private Set<Conversation> getConversations(CheckState check) {
		Set<Conversation> conversations = new HashSet<Conversation>();
		for (Conversation conversation : conversations) {
			if (conversation.getCheckState().equals(check)) {
				conversations.add(conversation);
			}
		}
		return conversations;
	}

	public Set<ConversationMyself> getConversationsMyself(CheckState check) {
		Set<Conversation> conversations2 = getConversations(check);
		Set<ConversationMyself> set = new HashSet<ConversationMyself>();
		for (Conversation conversation : conversations2) {
			if (conversation instanceof ConversationMyself) {
				set.add((ConversationMyself) conversation);
			}
		}
		return set;
	}

	public abstract Set<NPC> getNPCs() throws Exception;
//		List<Integer> ids = new ArrayList<Integer>();
//		for (Conversation conversation : conversations) {
//			conversation.getIDSorted();
//		}
//
//		throw new Exception("NPC not exists: Stage Name: " + getName());
//	}

	public abstract Set<Integer> getIDs() throws Exception;

//		String stageName = getName();
//
//		for (String name : StorageConversation.mapMyselfNPC.keySet()) {
//			if (name.equalsIgnoreCase(stageName)) {
//				return StorageConversation.mapMyselfNPC.get(name);
//			}
//		}
//
//		List<Integer> ids = new ArrayList<Integer>();
//		for (Conversation conversation : StorageConversation.conversations) {
//			if (conversation.stageName.equalsIgnoreCase(stageName)) {
//				if (!conversation.isChangebleID()) {
//					ids.addAll(conversation.getIDSorted());
//				}
//			}
//		}
//		if (0 < ids.size()) {
//			return ids;
//		}
//		throw new Exception("Invalid Stage Name: " + stageName);

//	public void changeNPC(CheckState state) throws Exception {
//		for (Conversation conversation : conversations) {
//			if (!conversation.isChangebleID()) {
//				throw new Exception("Contains not id changeble conversation");
//			}
//		}
//		Integer max = ids.size();
//		if(players.size() < max) {
//			max = players.size();
//		}
//		Collections.shuffle(ids);
//		Collections.shuffle(players);
//		for (int i = 0; i < max; i++) {
//			try {
//				NPCHandler.changeNPCAsPlayer(ids.get(i), name, players.get(i));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		for (int i = max; i < ids.size(); i++) {
//			try {
//				NPCHandler.changeNPCAsEmpty(ids.get(i));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}



//	public Set<Integer> getID() {
//		Set<String> stageMyself = new HashSet<String>();
//		Set<Integer> ids = new HashSet<Integer>();
//		for (Conversation conversation : conversations) {
//			if(conversation instanceof ConversationMulti) {
//				try {
//					ids.addAll(conversation.getIDSorted());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else if(conversation instanceof ConversationMyself) {
//				stageMyself.add(conversation.stageName);
//			}
//		}
//		for (String myself : stageMyself) {
//		}
//		return ids;
//	}


}
package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.citizensnpcs.api.npc.NPC;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.StorageConversation;

public class Stage {
	private List<Conversation> conversations = new ArrayList<Conversation>();

	public enum CheckState {NOT_EXISTS, UNCHECKED, CHECKED, RECOMMENDED,;
		public CheckState getCheckState(Conversation conversation) {
			CheckState state = CheckState.UNCHECKED;
			if (!(0 < conversation.listTalk.size())) {
				state = CheckState.NOT_EXISTS;
				return state;
			}

			for (String teacher : ControllerConversation.getTeachers()) {
				if ( conversation.recommenders.contains(teacher)) {
					state = CheckState.RECOMMENDED;
					return state;
				}
			}

			if (0 <  conversation.getCorrectors().size() || 0 <  conversation.recommenders.size()) {
				state = CheckState.CHECKED;
				return state;
			}

			return state;
		}
	}



	private Stage() {}

	public static Stage createStage(String name) throws Exception {
		Stage stage = new Stage();
		stage.conversations = new ArrayList<Conversation>();
		stage.conversations.addAll(ControllerConversation.getConversations(name));
		return stage;
	}

	public String getName() {
		return conversations.get(0).stageName;
	}

	public Set<NPC> getNPCs() throws Exception {
		List<Integer> ids = new ArrayList<Integer>();
		for (Conversation conversation : conversations) {
			conversation.getIDSorted();
		}

		throw new Exception("NPC not exists: Stage Name: " + getName());
	}

	public List<Integer> getIDs() throws Exception {
		String stageName = getName();

		for (String name : StorageConversation.mapMyselfNPC.keySet()) {
			if (name.equalsIgnoreCase(stageName)) {
				return StorageConversation.mapMyselfNPC.get(name);
			}
		}

		List<Integer> ids = new ArrayList<Integer>();
		for (Conversation conversation : StorageConversation.conversations) {
			if (conversation.stageName.equalsIgnoreCase(stageName)) {
				if (!conversation.isChangebleID()) {
					ids.addAll(conversation.getIDSorted());
				}
			}
		}
		if (0 < ids.size()) {
			return ids;
		}
		throw new Exception("Invalid Stage Name: " + stageName);
	}

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
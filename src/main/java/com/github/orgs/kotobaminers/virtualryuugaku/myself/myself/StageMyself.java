package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;


public class StageMyself /*extends Stage */{
}

//	public Set<ConversationMyself> conversations = new HashSet<ConversationMyself>();
//
//	private StageMyself() {}
//	public static StageMyself create() {
//		return new StageMyself();
//	}
//
//	public void changeNPCs(CheckState check) {
//		Set<Integer> ids = getIDsAll();
//		List<NPC> npcs = new ArrayList<NPC>();
//		List<String> players = new ArrayList<String>();
//		for (Conversation conversation : conversations) {
//			if (0 < conversation.listTalk.size()) {
//				players.add(conversation.listTalk.get(0).name);
//			}
//		}
//
//		for (Integer id : ids) {
//			try {
//				npcs.add(NPCHandler.getNPC(id));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		if (0 < npcs.size()) {
//			Collections.shuffle(npcs);
//
//			Integer max = npcs.size();
//			if(players.size() < max) {
//				max = players.size();
//			}
//			for (int i = 0; i < max; i++) {
//				NPCHandler.changeNPCAsPlayer(npcs.get(i), players.get(i));
//			}
//			for (int i = max; i < ids.size(); i++) {
//				NPCHandler.changeNPCAsEmpty(npcs.get(i));
//			}
//		}
//	}
//
//	public Set<Integer> getIDsAll() {
//		Set<Integer> ids = new HashSet<Integer>();
//		try {
//			ids.addAll(ControllerConversation.getMyselfIDs(name));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return ids;
//	}
//
//	@Override
//	public Set<Conversation> getConversations(CheckState check) {
//		Set<Conversation> set = new HashSet<Conversation>();
//		for (ConversationMyself conversation : conversations) {
//			if (conversation.getCheckState().equals(check)) {
//				set.add(conversation);
//			}
//		}
//		return set;
//	}
//	@Override
//	public Set<Conversation> getConversations() {
//		Set<Conversation> set = new HashSet<Conversation>();
//		for (ConversationMyself conversation : conversations) {
//			set.add((Conversation) conversation);
//		}
//		return set;
//	}
//
//
//}
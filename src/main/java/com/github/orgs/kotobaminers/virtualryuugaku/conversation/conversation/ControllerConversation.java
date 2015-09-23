package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.List;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Controller;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class ControllerConversation extends Controller {

	public ConversationStorage storage;

	@Override
	public void setStorage() {
		storage = new ConversationStorage();
	}

	@Override
	public Storage getStorage() {
		return storage;
	}

	public List<Conversation> getConversationsByStage(String stage) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Conversation> conversations = new ArrayList<Conversation>();
		for (Conversation conversation : ConversationStorage.conversations) {
			if (conversation.stage.equalsIgnoreCase(stage)) {
				conversations.add(conversation);
			}
		}
		return conversations;
	}

	public Conversation getConversationByID(Integer id) throws Exception {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for (Conversation conversation : ConversationStorage.conversations) {
			if (conversation.getOrder().contains(id)) {
				UtilitiesProgramming.printDebugMessage("ID: " + id.toString() + ": Multi", new Exception());
				return conversation;
//			} else if(myself) {
//				UtilitiesProgramming.printDebugMessage("ID: " + id.toString() + ": Myself", new Exception());
			}
		}
		throw new Exception("Not valid id for conversation: " + id.toString());
	}
}
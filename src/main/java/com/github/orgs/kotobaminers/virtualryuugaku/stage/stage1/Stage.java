package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMulti;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation0.DataManagerConversation;

public class Stage {
	public List<ConversationMulti> conversations = new ArrayList<ConversationMulti>();
	public String name = "";

	public static Stage createStage(String name) {
		Stage stage = new Stage();
		stage.name = name;
		stage.setConversations(name);
		return stage;
	}

	public void setConversations(String name) {
		conversations = new ArrayList<ConversationMulti>();
		for (ConversationMulti conversation : DataManagerConversation.getMapConversation().values()) {
			if(conversation.stage.equalsIgnoreCase(name)) {
				conversations.add(conversation);
			}
		}
	}

	public void shuffleConversations() {
		Collections.shuffle(conversations);
	}
}
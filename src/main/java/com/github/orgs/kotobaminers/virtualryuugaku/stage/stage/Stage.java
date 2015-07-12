package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.DataManagerConversation;

public class Stage {
	public List<Conversation> conversations = new ArrayList<Conversation>();
	public String name = "";

	public static Stage createStage(String name) {
		Stage stage = new Stage();
		stage.name = name;
		stage.setConversations(name);
		return stage;
	}

	public void setConversations(String name) {
		conversations = new ArrayList<Conversation>();
		for (Conversation conversation : DataManagerConversation.getMapConversation().values()) {
			if(conversation.stage.equalsIgnoreCase(name)) {
				conversations.add(conversation);
			}
		}
	}

	public void shuffleConversations() {
		Collections.shuffle(conversations);
	}
}
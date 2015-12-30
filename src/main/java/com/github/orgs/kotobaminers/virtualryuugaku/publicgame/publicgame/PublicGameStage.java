package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.StorageConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class PublicGameStage {
	public List<Conversation> conversations = new ArrayList<Conversation>();

	public PublicGameStage() {
		setStageRandom();
	}

	private List<Conversation> loadConversations(String stageName) {
		UtilitiesProgramming.printDebugMessage(stageName, new Exception());
		List<Conversation> conversations = new ArrayList<Conversation>();
		try {
			conversations = ControllerConversation.getConversations(stageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conversations;
	}

	public void setStageRandom() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<String> stages = new ArrayList<String>();
		for (Conversation conversation : StorageConversation.conversations) {
			if (!stages.contains(conversation.stageName)) {
				if (0 < conversation.getKeyTalk().size()) {
					stages.add(conversation.stageName);
				}
			}
		}
		Collections.shuffle(stages);
		if (0 < stages.size()) {
			conversations = loadConversations(stages.get(0));
		}
	}

	public List<Talk> getKeyTalk() {
		List<Talk> talks = new ArrayList<Talk>();
		for (Conversation conversation : conversations) {
			for (Talk talk : conversation.listTalk) {
				if (talk.key) {
					talks.add(talk);
				}
			}
		}
		return talks;
	}

}
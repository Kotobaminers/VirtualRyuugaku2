package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public class Stage {
	public Map<List<Integer>, Conversation> conversations = new HashMap<List<Integer>, Conversation>();
	public List<UUID> editor = new ArrayList<UUID>();
	String name = "";
	public void printDebugMessage() {
		Debug.printDebugMessage(name, new Exception());
		for (Conversation conversation : conversations.values()) {
			conversation.printDebugMessage();
		}
	}

	public List<NPCSentence> getKeySentences() {
		List<NPCSentence> sentences = new ArrayList<NPCSentence>();
		for (Conversation conversation : conversations.values()) {
			for (NPCSentence sentence : conversation.sentences) {
				if (sentence.key) {
					sentences.add(sentence);
				}
			}
		}
		return sentences;
	}
}
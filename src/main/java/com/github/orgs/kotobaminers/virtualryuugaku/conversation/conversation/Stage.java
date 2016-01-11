package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public class Stage {

	public String name = "";
	public Map<List<Integer>, NPCConversation> npcConversations = new LinkedHashMap<List<Integer>, NPCConversation>();

	public Map<Integer, LearnerConversation> displayNPC = new HashMap<Integer, LearnerConversation>();
	public Map<UUID, LearnerConversation> playerConversations = new HashMap<UUID, LearnerConversation>();

	public List<UUID> editor = new ArrayList<UUID>();
	public List<String> learnerQuestions = new ArrayList<String>();

	public void printDebugMessage() {
		Debug.printDebugMessage(name, new Exception());
		for (Conversation conversation : npcConversations.values()) {
			conversation.printDebugMessage();
		}
	}

	public List<VRGSentence> getKeySentences() {
		List<VRGSentence> sentences = new ArrayList<VRGSentence>();
		for (Conversation conversation : npcConversations.values()) {
			for (VRGSentence sentence : conversation.sentences) {
				if (sentence.key) {
					sentences.add(sentence);
				}
			}
		}
		return sentences;
	}

	void updateLearnerNPC() {

	}
	public void giveLearnerNPCBook(Player player) {

	}

}
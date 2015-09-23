package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import net.citizensnpcs.api.npc.NPC;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Controller;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class ControllerConversation extends Controller {

	public StorageConversation storage;

	@Override
	public void setStorage() {
		storage = new StorageConversation();
	}

	@Override
	public Storage getStorage() {
		return storage;
	}

	public static List<Conversation> getConversationsByStage(String stage) throws Exception {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Conversation> conversations = new ArrayList<Conversation>();
		for (Conversation conversation : StorageConversation.conversations) {
			if (conversation.stage.equalsIgnoreCase(stage)) {
				conversations.add(conversation);
			}
		}
		if (conversations.size() == 0){
			throw new Exception("Invalid Stage: " + stage);
		}
		return conversations;
	}

	public static Conversation getConversation(NPC npc) throws Exception {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Integer id = npc.getId();
		//Myself
		if (isMyself(id)) {
			return getConversationMyself(npc);
		}

		//Multi
		for (Conversation conversation : StorageConversation.conversations) {
			if (conversation instanceof ConversationMulti) {
				if (conversation.getOrder().contains(id)) {
					return conversation;
				}
			}
		}
		throw new Exception("Not valid id for conversation: " + id.toString());
	}

	public static boolean isMyself(Integer id) {
		for (List<Integer> ids: StorageConversation.mapMyselfNPC.values()) {
			System.out.println(ids + "" + id);
			if (ids.contains(id)) {
				return true;
			}
		}
		return false;
	}

	public static String getMyselfStage(Integer id) throws Exception {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for (Entry<String, List<Integer>> entry : StorageConversation.mapMyselfNPC.entrySet()) {
			if (entry.getValue().contains(id)) {
				return entry.getKey();
			}
		}
		throw new Exception("Not Myself ID: " + id.toString());
	}

	public static ConversationMyself getConversationMyself(String name, String stage) throws Exception{
		for (Conversation conversation : StorageConversation.conversations) {
			if (conversation instanceof ConversationMyself) {
				ConversationMyself myself = (ConversationMyself) conversation;
				if (myself.stage.equalsIgnoreCase(stage)) {
					for (String e : myself.editor) {
						if (e.equalsIgnoreCase(name)) {
							UtilitiesProgramming.printDebugMessage("Myself: " + name + ", " + stage, new Exception());
							return myself;
						}
					}
				}
			}
		}
		throw new Exception("Not Myself: " + name + ", " + stage);
	}

	public static ConversationMyself getConversationMyself(NPC npc) throws Exception{
		Integer id = npc.getId();
		if (isMyself(id)) {
			String name = npc.getName();
			String stage = getMyselfStage(id);
			return getConversationMyself(name, stage);
		}
		throw new Exception("Not Myself: " + id.toString());
	}
}



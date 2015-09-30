package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Controller;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;
import com.github.orgs.kotobaminers.virtualryuugaku.myself.myself.ConversationBook;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class ControllerConversation extends Controller {

	public static StorageConversation storage;

	@Override
	public void setStorage() {
		storage = new StorageConversation();
	}

	@Override
	public Storage getStorage() {
		return storage;
	}

	public static List<Conversation> getConversations(String stage) throws Exception {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Conversation> conversations = new ArrayList<Conversation>();
		for (Conversation conversation : StorageConversation.conversations) {
			if (conversation.stageName.equalsIgnoreCase(stage)) {
				conversations.add(conversation);
			}
		}
		if (conversations.size() == 0){
			throw new Exception("Invalid Stage: " + stage);
		}
		return conversations;
	}

	public static Set<Conversation> getConversations() {
		return StorageConversation.conversations;
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
				if (conversation.getIDSorted().contains(id)) {
					return conversation;
				}
			}
		}
		throw new Exception("Not valid id for conversation: " + id.toString());
	}

	public static boolean isMyself(Integer id) {
		for (List<Integer> ids: StorageConversation.mapMyselfNPC.values()) {
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

	private static ConversationMyself getConversationMyself(String name, String stage) throws Exception{
		for (Conversation conversation : StorageConversation.conversations) {
			if (conversation instanceof ConversationMyself) {
				ConversationMyself myself = (ConversationMyself) conversation;
				if (myself.stageName.equalsIgnoreCase(stage)) {
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

	private static ConversationMyself getConversationMyself(NPC npc) throws Exception{
		Integer id = npc.getId();
		if (isMyself(id)) {
			String name = npc.getName();
			String stage = getMyselfStage(id);
			return getConversationMyself(name, stage);
		}
		throw new Exception("Not Myself: " + id.toString());
	}

	public static List<String> getTeachers() {
		return StorageConversation.teachers;
	}

	public static List<String> getStages() {
		Set<String> set = new HashSet<String>();
		for (Conversation conversation : getConversations()) {
			set.add(conversation.stageName);
		}
		List<String> stages = new ArrayList<String>();
		stages.addAll(set);
		return stages;
	}

	public static List<String> getStagesMyself() {
		List<String> stages = new ArrayList<String>();
		for(String search : StorageConversation.mapMyselfNPC.keySet()) {
			stages.add(search);
		}
		return stages;
	}

	public static List<String> getStagesCourse() {
		List<String> myself = getStagesMyself();
		List<String> all = getStages();
		List<String> course = new ArrayList<String>();
		for (String stage : all) {
			if (!myself.contains(stage)) {
				course.add(stage);
			}
		}
		return course;
	}

	public static void importBook(Player player) {
		ConversationBook ready = new ConversationBook();
		ConversationBook book = new ConversationBook();
		try {
			book = ready.createConversatinBook(player);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		if(book.isMine()) {
			StorageConversation.conversations.add(book.conversation);
			Message.BOOK_IMPORTED_0.print(player, null);
			Effects.playSound(player, Scene.GOOD);
		} else {
			Message.BOOK_NOT_YOURS_0.print(player, null);
			Effects.playSound(player, Scene.BAD);
		}
	}

	public static boolean existsMyselfStage(String stage) {
		for(String search : StorageConversation.mapMyselfNPC.keySet()) {
			if(search.equalsIgnoreCase(stage)) {
				return true;
			}
		}
		return false;
	}

	public static List<Integer> getMyselfIDs(String stage) throws Exception {
		if (existsMyselfStage(stage)) {
			List<Integer> list = StorageConversation.mapMyselfNPC.get(stage);
			return list;
		}
		throw new Exception("Invalis Stage Name: " + stage);
	}
}



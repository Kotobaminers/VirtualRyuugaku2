package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Controller;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMyself;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation.CheckState;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.vrgnpc.vrgnpc.NPCHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.vrgnpc.vrgnpc.NPCHandler.NPCType;


public class ControllerMyself extends Controller {
	public static StorageMyself storage = null;

	@Override
	public void setStorage() {
		storage = new StorageMyself();
	}

	@Override
	public Storage getStorage() {
		return storage;
	}

	public static boolean isNPCMyself(NPC npc) {//This needs a npc to confirm the NPC exists.
		for(List<Integer> list : StorageMyself.mapMyselfNPC.values()) {
			Integer id = npc.getId();
			if(list.contains(id)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNPCMyself(Integer id) {
		NPCType type = NPCHandler.getNPCType(id);
		if(type.equals(NPCType.MYSELF)) {
			return true;
		}
		return false;
	}

	public static void importBook(ConversationBook book) {
		DataKeyMyself key = new DataKeyMyself(book.owner, book.stage);
		StorageMyself.mapConversationMyself.put(key, book.conversation);
	}

	public static void happensEvent(NPC npc, Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(isNPCMyself(npc)) {
			Integer id = npc.getId();
			String stage = getStageFromID(id).toUpperCase();
			ConversationMyself conversation = new ConversationMyself();
			DataKeyMyself key = new DataKeyMyself(npc.getName(), stage);
			if(StorageMyself.mapConversationMyself.containsKey(key)) {
				conversation = StorageMyself.mapConversationMyself.get(key);
			}
			conversation.talk(player);
		}
	}

	public static String getStageFromID(Integer id) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String stage = "";
		for(Entry<String, List<Integer>> entry : StorageMyself.mapMyselfNPC.entrySet()) {
			if(entry.getValue().contains(id)) {
				stage = entry.getKey();
				break;
			}
		}
		UtilitiesProgramming.printDebugMessage(stage, new Exception());
		return stage;
	}

	public static List<Integer> getIDFromStage(String stage) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Integer> ids = new ArrayList<Integer>();
		if(StorageMyself.mapMyselfNPC.containsKey(stage)) {
			List<Integer> search = StorageMyself.mapMyselfNPC.get(stage);
			for (Integer id : search) {
				if (isNPCMyself(id)) {
					ids.add(id);
				} else {
					UtilitiesProgramming.printDebugMessage("Not Myself NPC: " + id.toString(), new Exception());
				}
			}
		}
		return ids;
	}

	public static List<String> getPlayerNamesAll(String stage) {
		List<String> names = new ArrayList<String>();
		for (DataKeyMyself key : StorageMyself.mapConversationMyself.keySet()) {
			if (key.stage.equalsIgnoreCase(stage)) {
				names.add(key.owner);
			}
		}
		return names;
	}

	public static List<String> getPlayerNamesByCheckState(String stage, CheckState state) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<String> names = new ArrayList<String>();
		for (Entry<DataKeyMyself, ConversationMyself> entry : StorageMyself.mapConversationMyself.entrySet()) {
			if (entry.getKey().stage.equalsIgnoreCase(stage)) {
				UtilitiesProgramming.printDebugMessage(entry.getValue().getCheckState().toString(), new Exception());
				if (entry.getValue().getCheckState().equals(state)) {
					names.add(entry.getKey().owner);
				}
			}
		}
		return names;
	}

	public static List<ConversationMyself> getConversationsCurrent(String stage) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<ConversationMyself> conversations = new ArrayList<ConversationMyself>();
		List<NPC> npcs = new ArrayList<NPC>();
		List<Integer> ids = getIDFromStage(stage);
		System.out.println(ids);
		for (Integer id : ids) {
			npcs.add(NPCHandler.getNPC(id));
		}
		for(NPC npc : npcs) {
			DataKeyMyself key = new DataKeyMyself(npc.getName(), stage);
			UtilitiesProgramming.printDebugMessage(npc.getName() + stage, new Exception());
			if(StorageMyself.mapConversationMyself.containsKey(key)) {
				conversations.add(StorageMyself.mapConversationMyself.get(key));
			}
		}
		return conversations;
	}

	public static boolean isValidBook(ConversationBook book) {
		if(0 < book.owner.length() && 0 < book.stage.length() && 0 < book.conversation.listTalk.size()) {
			for(String search : StorageMyself.mapMyselfNPC.keySet()) {
				if(search.equalsIgnoreCase(book.stage)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean existsStage(String stage) {
		if(StorageMyself.mapMyselfNPC.containsKey(stage.toUpperCase())) {
			return true;
		}
		UtilitiesProgramming.printDebugMessage("Not Exists StageMyself: " + stage, new Exception());
		return false;
	}

	public static void setTalkParams(String npcName, String stageName, Integer id) {
		DataKeyMyself key = new DataKeyMyself(npcName, stageName);
		if(StorageMyself.mapConversationMyself.containsKey(key)) {
			ConversationMyself conversation = StorageMyself.mapConversationMyself.get(key);
			for(Talk talk : conversation.listTalk) {
				talk.id = id;
				talk.name = npcName;
			}
		}
	}

	public static void printMe(NPC npc, Player player) {
		Integer id = npc.getId();
		String stage = StorageMyself.mapMe.get(id);
		ConversationMyself conversation = new ConversationMyself();
		DataKeyMyself key = new DataKeyMyself(npc.getName(), stage);
		if(StorageMyself.mapConversationMyself.containsKey(key)) {
			conversation = StorageMyself.mapConversationMyself.get(key);
		}
		conversation.talk(player);
	}

	public static void updateMe(Player player) {
		List<String> myStage = new ArrayList<String>();
		String name = player.getName();
		for (DataKeyMyself key : StorageMyself.mapConversationMyself.keySet()) {
			if (key.owner.equalsIgnoreCase(name)) {
				myStage.add(key.stage.toUpperCase());
			}
		}
		for (Entry<Integer, String> entry : StorageMyself.mapMe.entrySet()) {
			String stage = entry.getValue().toUpperCase();
			Integer id = entry.getKey();
			if (myStage.contains(stage)) {
				try {
					NPCHandler.changeNPCAsPlayer(id, stage, name);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					NPCHandler.changeNPCAsEmpty(id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static boolean isMe(Integer id) {
		if (StorageMyself.mapMe.containsKey(id)) {
			UtilitiesProgramming.printDebugMessage("It's ME: " + id.toString(), new Exception());
			return true;
		}
		return false;
	}

	public static InfoMyselfPlayer createInfo(String player) {
		InfoMyselfPlayer info = new InfoMyselfPlayer();
		info.player = player;


		return info;
	}

	public static void printDebugMyselfAll() {
		for(Conversation conversation :StorageMyself.mapConversationMyself.values()) {
			UtilitiesProgramming.printDebugMessage(conversation.getDebugMessage(), new Exception());
			for(Talk talk : conversation.listTalk) {
				UtilitiesProgramming.printDebugMessage(talk.getDebugMessage(), new Exception());
			}
		}
	}

	public static List<String> getTeachers() {
		return StorageMyself.teachers;
	}

}

package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Controller;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public class StageController extends Controller {
	public static StageStorage storage = new StageStorage();

	@Override
	public void initializeStorage() {
		Debug.printDebugMessage("", new Exception());
		storage.initialize();
	}

	@Override
	public Storage getStorage() {
		return storage;
	}

	public static Conversation getConversation(Integer id) throws Exception {
		for (Stage stage : storage.stages) {
			for (List<Integer> index : stage.conversations.keySet()) {
				if (index.contains(id)) {
					return stage.conversations.get(index);
				}
			}
		}
		throw new Exception();
	}

	public static Stage getStageRandom() {
		Debug.printDebugMessage("", new Exception());
		Stage stage = new Stage();
		List<Stage> list = new ArrayList<Stage>();
		for (Stage search : StageStorage.stages) {
			list.add(search);
		}
		Collections.shuffle(list);
		if (0 < list.size()) {
			stage = list.get(0);
		}
		return stage;
	}
}

//	private static ConversationMyself getConversationMyself(NPC npc) throws Exception{
//		Integer id = npc.getId();
//		if (isMyself(id)) {
//			String name = npc.getName();
//			String stage = getMyselfStage(id);
//			return getConversationMyself(name, stage);
//		}
//		throw new Exception("Not Myself: " + id.toString());
//	}
//
//	public static List<String> getStagesMyself() {
//		List<String> stages = new ArrayList<String>();
//		for(String search : StorageConversation.mapMyselfNPC.keySet()) {
//			stages.add(search);
//		}
//		return stages;
//	}
//
//	public static List<String> getStagesCourse() {
//		List<String> myself = getStagesMyself();
//		List<String> all = getStages();
//		List<String> course = new ArrayList<String>();
//		for (String stage : all) {
//			if (!myself.contains(stage)) {
//				course.add(stage);
//			}
//		}
//		return course;
//	}
//
//	public static void importBook(Player player) {
//		ConversationBook ready = new ConversationBook();
//		ConversationBook book = new ConversationBook();
//		try {
//			book = ready.createConversatinBook(player);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return;
//		}
//		if(book.isMine()) {
//			updateConversationMyself(book.conversation);
//			Message.BOOK_IMPORTED_0.print(player, null);
//			Effects.playSound(player, Scene.GOOD);
//		} else {
//			Message.BOOK_NOT_YOURS_0.print(player, null);
//			Effects.playSound(player, Scene.BAD);
//		}
//	}
//
//	private static void updateConversationMyself(ConversationMyself myself) {
//		String editor = "";
//		if (0< myself.editor.size()) {
//			editor = myself.editor.get(0);
//			try {
//				ConversationMyself target = getConversationMyself(editor, myself.stageName);
//				StorageConversation.conversations.remove(target);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		StorageConversation.conversations.add(myself);
//	}
//
//	public static boolean existsMyselfStage(String stage) {
//		for(String search : StorageConversation.mapMyselfNPC.keySet()) {
//			if(search.equalsIgnoreCase(stage)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public static List<Integer> getMyselfIDs(String stage) throws Exception {
//		if (existsMyselfStage(stage)) {
//			List<Integer> list = StorageConversation.mapMyselfNPC.get(stage);
//			return list;
//		}
//		throw new Exception("Invalis Stage Name: " + stage);
//	}
//
//	public static boolean isMyself(Integer id) {
//	for (List<Integer> ids: StorageConversation.mapMyselfNPC.values()) {
//		if (ids.contains(id)) {
//			return true;
//		}
//	}
//	return false;
//}
//
//public static String getMyselfStage(Integer id) throws Exception {
//	UtilitiesProgramming.printDebugMessage("", new Exception());
//	for (Entry<String, List<Integer>> entry : StorageConversation.mapMyselfNPC.entrySet()) {
//		if (entry.getValue().contains(id)) {
//			return entry.getKey();
//		}
//	}
//	throw new Exception("Not Myself ID: " + id.toString());
//}
//
//private static ConversationMyself getConversationMyself(String name, String stage) throws Exception{
//	for (Conversation conversation : StorageConversation.conversations) {
//		if (conversation instanceof ConversationMyself) {
//			ConversationMyself myself = (ConversationMyself) conversation;
//			if (myself.stageName.equalsIgnoreCase(stage)) {
//				for (String e : myself.editor) {
//					if (e.equalsIgnoreCase(name)) {
//						UtilitiesProgramming.printDebugMessage("Myself: " + name + ", " + stage, new Exception());
//						return myself;
//					}
//				}
//			}
//		}
//	}
//	throw new Exception("Not Myself: " + name + ", " + stage);
//}
package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataManagerCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.LibraryManager;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.DataManager;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class DataManagerConversation implements DataManager {
	//mapTalker
	private static Map<List<Integer>, Conversation> mapConversation = new HashMap<List<Integer>, Conversation>();

	@Override
	public void loadAll() {
		initialize();
		loadMapConversation();
	}
	@Override
	public void initialize() {
		mapConversation = new HashMap<List<Integer>, Conversation>();
	}
	private static void loadMapConversation() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Conversation> list = new ArrayList<Conversation>();
		list.addAll(ConfigHandlerConversation.importConversationDefault());
		Map<String, YamlConfiguration> mapConfig = LibraryManager.getListLibraryStage();
		for(String stage : mapConfig.keySet()) {
			list.addAll(LibraryHandlerConversation.importConversationLibrary(stage, mapConfig.get(stage)));
		}
		for(Conversation conversation : list) {
			if(Conversation.isValidCitizensId(conversation.getOrder())) {
				overrideCitizens(conversation);
				if(getMapConversation().containsKey(conversation.getOrder())) {
					overrideComment(conversation);
				}
				putTalker(conversation);
			} else {
				UtilitiesProgramming.printDebugMessage("Non Existing NPC ID: " + conversation.getOrder(), new Exception());
			}
		}
	}
	private static void overrideComment(Conversation conversation) {
		UtilitiesProgramming.printDebugMessage("Overriding Comment: ID: " + conversation.getOrder(), new Exception());
		conversation.mapComment = getTalker(conversation.getOrder()).mapComment;
	}
	private static void overrideCitizens(Conversation conversation) {
		for (Talk talk : conversation.listTalk) {
			String name = DataManagerCitizens.getDataCitizens(talk.id).name;
			UtilitiesProgramming.printDebugMessage("Overriding Citizens: ID: " + talk.id + ", Name: " + name, new Exception());
			talk.name = name;
		}
	}

	public static void printHint(Player player, String stage) {
		List<String> answers = new ArrayList<String>();
		for(Conversation conversation : getMapConversation().values()) {
			if(conversation.stage.equalsIgnoreCase(stage)) {
				answers.addAll(conversation.question.getAnswers());
			}
		}
		Collections.shuffle(answers);
		String[] opts = {stage, UtilitiesGeneral.joinStrings(answers, ", ")};
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.CONVERSATION_HINT_2, opts));
	}

	@Override
	public void saveAll() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(Conversation talker : getMapConversation().values()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			ConfigHandlerConversation.saveConversation(talker);
		}
		new ConfigHandlerConversation().save();
	}

	public static void registerConversation(Conversation conversation) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(conversation.isEmpty()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
		} else {
			putTalker(conversation);
		}
	}
	public static boolean existsConversation(NPC npc) {
		Integer id = npc.getId();
		for(List<Integer> order : getMapConversation().keySet()) {
			if(order.contains(id)) {
				return true;
			}
		}
		UtilitiesProgramming.printDebugMessage("NON Conversation", new Exception());
		return false;
	}

	public static Integer getNumberQuestion(String stage) {
		Integer question = 0;
		for(Conversation search : DataManagerConversation.getMapConversation().values()) {
			if(search.stage.equalsIgnoreCase(stage)) {
				if(search.hasValidQuestion()) {
					question++;
				}
			}
		}
		return question;
	}


	public static Map<List<Integer>, Conversation> getMapConversation() {
		return mapConversation;
	}
	public static Conversation getTalker(List<Integer> order) {
		Conversation conversation = new Conversation();
		if(getMapConversation().containsKey(order)) {
			conversation = getMapConversation().get(order);
		}
		return conversation;
	}
	public static Conversation getConversation(Integer id) {
		Conversation conversation = new Conversation();
		for(List<Integer> order : getMapConversation().keySet()) {
			if(order.contains(id)) {
				conversation = getMapConversation().get(order);
				return conversation;
			}
		}
		return conversation;
	}
	private static void putTalker(Conversation talker) {
		getMapConversation().put(talker.getOrder(), talker);
	}
}
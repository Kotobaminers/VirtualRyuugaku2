package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataManagerCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.PathConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationAnswer.KeyAnswer;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationQuestion.KeyQuestion;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class LibraryHandlerConversation {
	private enum PathStage {CONVERSATION, EDITOR}
	//Talker
	public static List<Conversation> importTalkerLibrary(String stage, YamlConfiguration library) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Conversation> list = new ArrayList<Conversation>();
		UtilitiesProgramming.printDebugMessage("Stage: " + stage, new Exception());
		List<String> editor = library.getStringList(PathConversation.EDITOR.toString());
		for(String talkerPath : library.getKeys(false)) {
			if(talkerPath.equalsIgnoreCase(PathStage.CONVERSATION.toString())) {
				MemorySection memory = (MemorySection) library.get(talkerPath);
				for(String idString : memory.getKeys(false)) {
					MemorySection memoryId = (MemorySection) memory.get(idString);
					List<Integer> order = UtilitiesGeneral.toListInteger(idString);
					Conversation conversation = new Conversation();
					conversation.stage = stage;
					conversation.editor.addAll(editor);
					//Name will be imported from citizens data.

					UtilitiesProgramming.printDebugMessage("", new Exception());
					if(memoryId.contains(Enums.PathConversation.EN.toString()) && memoryId.contains(Enums.PathConversation.KANJI.toString()) && memoryId.contains(Enums.PathConversation.KANA.toString())) {
						List<String> kanji = memoryId.getStringList(PathConversation.KANJI.toString());
						List<String> kana = memoryId.getStringList(PathConversation.KANA.toString());
						List<String> en = memoryId.getStringList(PathConversation.EN.toString());
						Integer size = order.size();
						if(size.equals(kana.size()) && size.equals(kana.size()) && size.equals(en.size())) {
							for(int i = 0; i < size; i++) {
								UtilitiesProgramming.printDebugMessage("", new Exception());
								if(Conversation.isValidCitizensId(order)) {
									String name = DataManagerCitizens.getDataCitizens(order.get(i)).name;
									Description description = Description.create(kanji.get(i), kana.get(i), en.get(i), new ArrayList<String>());
									Talk talk = new Talk().create(order.get(i), name, description);
									UtilitiesProgramming.printDebugTalk(talk);
									conversation.listTalk.add(talk);
								} else {
									UtilitiesProgramming.printDebugMessage("Invalid NPC: " + i, new Exception());
								}
							}
						}
					}

					String path = Enums.PathConversation.KEY.toString();
					if(memoryId.contains(path)) {
						List<Integer> key = new ArrayList<Integer>();
						for (int i : memoryId.getIntegerList(path)) {
							key.add(i - 1);
						}
						conversation.setKey(key);
					}

					path = Enums.PathConversation.Q.toString();
					if(memoryId.contains(path + "." + KeyQuestion.EN.toString()) && memoryId.contains(path + "." + KeyQuestion.JP.toString())) {
						String questionEn = memoryId.getString(PathConversation.Q + "." + KeyQuestion.EN);
						String questionJp = memoryId.getString(PathConversation.Q + "." + KeyQuestion.JP);
						conversation.question = new ConversationQuestion().create(questionEn, questionJp);
					}

					path = Enums.PathConversation.A.toString();
					if(memoryId.contains(path + "." + KeyAnswer.EN.toString()) && memoryId.contains(path + "." + KeyAnswer.JP.toString())) {
						List<String> answerEn = memoryId.getStringList(PathConversation.A + "." + KeyAnswer.EN);
						List<String> answerJp = memoryId.getStringList(PathConversation.A + "." + KeyAnswer.JP);
						conversation.answer = new ConversationAnswer().create(answerEn, answerJp);
					}

					//Comments will be imported from Talker.yml
					list.add(conversation);
					UtilitiesProgramming.printDebugConversation(conversation);
				}
			}
		}
		return list;
	}
}
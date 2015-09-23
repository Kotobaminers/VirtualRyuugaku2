package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.ConfigCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.PathConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class LibraryHandlerConversation {
	private enum PathStage {CONVERSATION, EDITOR}
	public static List<ConversationMulti> importConversationLibrary(String stage, YamlConfiguration library) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<ConversationMulti> list = new ArrayList<ConversationMulti>();
		UtilitiesProgramming.printDebugMessage("Stage: " + stage, new Exception());
		List<String> editor = library.getStringList(PathConversation.EDITOR.toString());

		ConfigCitizens citizens = new ConfigCitizens();
		citizens.importConfiguration();

		for(String talkerPath : library.getKeys(false)) {
			if(talkerPath.equalsIgnoreCase(PathStage.CONVERSATION.toString())) {
				MemorySection memory = (MemorySection) library.get(talkerPath);
				for(String idString : memory.getKeys(false)) {
					MemorySection memoryId = (MemorySection) memory.get(idString);
					List<Integer> order = UtilitiesGeneral.toListInteger(idString);
					ConversationMulti conversation = new ConversationMulti();
					conversation.stage = stage;
					conversation.editor.addAll(editor);
					//Name will be imported from citizens data.
					if(memoryId.contains(Enums.PathConversation.EN.toString()) && memoryId.contains(Enums.PathConversation.KANJI.toString()) && memoryId.contains(Enums.PathConversation.KANA.toString())) {
						List<String> kanji = memoryId.getStringList(PathConversation.KANJI.toString());
						List<String> kana = memoryId.getStringList(PathConversation.KANA.toString());
						List<String> en = memoryId.getStringList(PathConversation.EN.toString());
						Integer size = order.size();
						if(size.equals(kanji.size()) && size.equals(kana.size()) && size.equals(en.size())) {
							for(int i = 0; i < size; i++) {
								UtilitiesProgramming.printDebugMessage("", new Exception());
								Integer id = order.get(i);
								if (citizens.existsNPC(id)) {
									String name = citizens.getNPCName(id);
									Description description = Description.create(kanji.get(i), kana.get(i), en.get(i), new ArrayList<String>());
									Talk talk = new Talk().create(id, name, description);
									UtilitiesProgramming.printDebugTalk(talk);
									conversation.listTalk.add(talk);
								} else {
									UtilitiesProgramming.printDebugMessage("NotExistNPC: " + id.toString(), new Exception());
								}
							}
						}
					}

					String path = Enums.PathConversation.KEY.toString();
					if(memoryId.contains(path)) {
						List<Integer> key = memoryId.getIntegerList(path);
						for (int i = 0; i < conversation.listTalk.size(); i++) {
							if(key.contains(i + 1)) {
								conversation.listTalk.get(i).key = true;
							}
						}
					}

					if(memoryId.contains(PathConversation.Q.toString()) && memoryId.contains(PathConversation.A.toString())) {
						String q = memoryId.getString(PathConversation.Q.toString());
						List<String> a = UtilitiesGeneral.addRomaji(memoryId.getStringList(PathConversation.A.toString()));
						conversation.question = ConversationQuestion.create(q, a, order, stage);
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
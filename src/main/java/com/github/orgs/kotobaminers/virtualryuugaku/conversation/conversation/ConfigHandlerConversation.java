package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataManagerCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.PathComment;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.PathConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.comment.DataComment;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.comment.DataComment.CommentState;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationAnswer.KeyAnswer;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationQuestion.KeyQuestion;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.ConfigHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class ConfigHandlerConversation extends ConfigHandler {
	public static YamlConfiguration config;
	public static File file;
	public static final String DIRECTORY = "TALKER";
	public static final String FILE_NAME = "TALKER.yml";
	//Talker
	public static List<Conversation> importConversationDefault() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Conversation> list = new ArrayList<Conversation>();
		for(String idString : config.getKeys(false)) {
			MemorySection memoryId = (MemorySection) config.get(idString);
			List<Integer> order = UtilitiesGeneral.toListInteger(idString);
			Conversation conversation = new Conversation();
			conversation.stage = memoryId.getString(PathConversation.STAGE.toString());
			conversation.editor.addAll(memoryId.getStringList(PathConversation.EDITOR.toString()));
			//Name will be imported from citizens data.

			if(memoryId.contains(Enums.PathConversation.EN.toString()) && memoryId.contains(Enums.PathConversation.KANJI.toString()) && memoryId.contains(Enums.PathConversation.KANA.toString())) {
				List<String> kanji = memoryId.getStringList(PathConversation.KANJI.toString());
				List<String> kana = memoryId.getStringList(PathConversation.KANA.toString());
				List<String> en = memoryId.getStringList(PathConversation.EN.toString());
				Integer size = order.size();
				if(size.equals(kana.size()) && size.equals(kana.size()) && size.equals(en.size())) {
					for(int i = 0; i < size; i++) {
						if(Conversation.isValidCitizensId(order)) {
							String name = DataManagerCitizens.getDataCitizens(order.get(i)).name;
							Description description = Description.create(kanji.get(i), kana.get(i), en.get(i), new ArrayList<String>());
							Talk talk = new Talk().create(order.get(i), name, description);
							conversation.listTalk.add(talk);
						} else {
							UtilitiesProgramming.printDebugMessage("Invalid NPC: " + i, new Exception());
						}
					}
				} else {
					UtilitiesProgramming.printDebugMessage("Invalid: if(size.equals(kana.size()) && size.equals(kana.size()) && size.equals(en.size()))", new Exception());
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

			if(memoryId.contains(PathConversation.COMMENT.toString())) {
				Map<String, DataComment> mapComment = new HashMap<String, DataComment>();
				MemorySection memoryComment = (MemorySection) memoryId.get(PathConversation.COMMENT.toString());
				for(String sender : memoryComment.getKeys(false)) {
					DataComment comment = new DataComment();
					comment.sender = sender;
					String pathComment = sender;
					comment.expression = memoryComment.getString(pathComment + "." + PathComment.EXPRESSION);
					comment.state = CommentState.lookup(memoryComment.getString(pathComment + "." + PathComment.STATE));
					mapComment.put(sender, comment);
				}
				conversation.mapComment = mapComment;
			}

			list.add(conversation);
		}
		return list;
	}
	public static void saveConversation(Conversation conversation) {
		if(conversation.listTalk.size() < 1) return;
		List<Integer> id = new ArrayList<Integer>();
		for (Talk talk : conversation.listTalk) {
			id.add(talk.id);
		}
		String path = UtilitiesGeneral.toString(id);
		config.set(path + "." + PathConversation.EDITOR, conversation.editor);
		config.set(path + "." + PathConversation.STAGE, conversation.stage);
		List<String> kanji = new ArrayList<String>();
		List<String> kana = new ArrayList<String>();
		List<String> en = new ArrayList<String>();
		for(Talk talk : conversation.listTalk) {
			Description description = talk.description;
			String enAdd = "";
			String kanjiAdd = "";
			String kanaAdd = "";
			if(0 < description.en.size()) enAdd = description.en.get(0);
			if(0 < description.kanji.size()) kanjiAdd = description.kanji.get(0);
			if(0 < description.kana.size()) kanaAdd = description.kana.get(0);
			en.add(enAdd);
			kanji.add(kanjiAdd);
			kana.add(kanaAdd);
		}
		config.set(path + "." + PathConversation.KANJI, kanji);
		config.set(path + "." + PathConversation.KANA, kana);
		config.set(path + "." + PathConversation.EN, en);
		config.set(path + "." + PathConversation.KEY, conversation.getKey());
		config.set(path + "." + PathConversation.Q + "." + KeyQuestion.EN, conversation.question.getEn());
		config.set(path + "." + PathConversation.Q + "." + KeyQuestion.JP, conversation.question.getJp());
		config.set(path + "." + PathConversation.A + "." + KeyAnswer.EN, conversation.answer.getEn());
		config.set(path + "." + PathConversation.A + "." + KeyAnswer.JP, conversation.answer.getJp());
		for(DataComment comment : conversation.mapComment.values()) {
			String pathComment = path + "." + PathConversation.COMMENT + "." + comment.sender;
			config.set(pathComment + "." + PathComment.STATE, comment.state.toString());
			config.set(pathComment + "." + PathComment.EXPRESSION, comment.expression);
		}
	}
	@Override
	public void setFile(File file) {
		ConfigHandlerConversation.file = file;
	}
	@Override
	public void setConfig(YamlConfiguration config) {
		ConfigHandlerConversation.config = config;
	}
	@Override
	public String getFileName() {
		return FILE_NAME;
	}
	@Override
	public String getDirectory() {
		return DIRECTORY;
	}
	@Override
	public File getFile() {
		return file;
	}
	@Override
	public YamlConfiguration getConfig() {
		return config;
	}
}
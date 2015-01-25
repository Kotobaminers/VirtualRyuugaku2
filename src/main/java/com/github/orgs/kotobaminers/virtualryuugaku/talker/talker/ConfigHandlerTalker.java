package com.github.orgs.kotobaminers.virtualryuugaku.talker.talker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.PathTalker;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.comment.DataComment;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.comment.DataComment.CommentState;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.TalkerAnswer.KeyAnswer;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.TalkerQuestion.KeyQuestion;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.ConfigHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class ConfigHandlerTalker extends ConfigHandler {
	public static YamlConfiguration config;
	public static File file;
	public static final String DIRECTORY = "TALKER";
	public static final String FILE_NAME = "TALKER.yml";
	public enum PathStage {TALKER, EDITOR}
	public enum PathComment {STATE, EXPRESSION}
	//Talker
	public static List<Talker> importTalkerDefault() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Talker> list = new ArrayList<Talker>();
		for(String idString : config.getKeys(false)) {
			MemorySection memoryId = (MemorySection) config.get(idString);
			Integer id = Integer.parseInt(idString.toString());
			Talker talker = new Talker();
			talker.id = id;
			talker.stage = memoryId.getString(PathTalker.STAGE.toString());
			talker.editor.addAll(memoryId.getStringList(PathTalker.EDITOR.toString()));

			if(memoryId.contains(Enums.PathTalker.EN.toString()) && memoryId.contains(Enums.PathTalker.KANJI.toString()) && memoryId.contains(Enums.PathTalker.KANA.toString())) {
				List<String> kanji = memoryId.getStringList(PathTalker.KANJI.toString());
				List<String> kana = memoryId.getStringList(PathTalker.KANA.toString());
				List<String> en = memoryId.getStringList(PathTalker.EN.toString());
				Integer size = kanji.size();
				List<Description> listSentence = new ArrayList<Description>();
				if(size.equals(kana.size()) && size.equals(en.size())) {
					for(int i = 0; i < size; i++) {
						listSentence.add(Description.create(kanji.get(i), kana.get(i), en.get(i), new ArrayList<String>()));
					}
				}
				talker.listSentence = listSentence;
			}

			String path = Enums.PathTalker.KEY.toString();
			if(memoryId.contains(path)) {
				talker.setKey(memoryId.getInt(path));
			}

			path = Enums.PathTalker.Q.toString();
			if(memoryId.contains(path + "." + KeyQuestion.EN.toString()) && memoryId.contains(path + "." + KeyQuestion.JP.toString())) {
				String questionEn = memoryId.getString(PathTalker.Q + "." + KeyQuestion.EN);
				String questionJp = memoryId.getString(PathTalker.Q + "." + KeyQuestion.JP);
				talker.question = new TalkerQuestion().create(questionEn, questionJp);
			}

			path = Enums.PathTalker.A.toString();
			if(memoryId.contains(path + "." + KeyAnswer.EN.toString()) && memoryId.contains(path + "." + KeyAnswer.JP.toString())) {
				List<String> answerEn = memoryId.getStringList(PathTalker.A + "." + KeyAnswer.EN);
				List<String> answerJp = memoryId.getStringList(PathTalker.A + "." + KeyAnswer.JP);
				talker.answer = new TalkerAnswer().create(answerEn, answerJp);
			}

			if(memoryId.contains(PathTalker.COMMENT.toString())) {
				Map<String, DataComment> mapComment = new HashMap<String, DataComment>();
				MemorySection memoryComment = (MemorySection) memoryId.get(PathTalker.COMMENT.toString());
				for(String sender : memoryComment.getKeys(false)) {
					DataComment comment = new DataComment();
					comment.sender = sender;
					String pathComment = sender;
					comment.expression = memoryComment.getString(pathComment + "." + PathComment.EXPRESSION);
					comment.state = CommentState.lookup(memoryComment.getString(pathComment + "." + PathComment.STATE));
					mapComment.put(sender, comment);
				}
				talker.mapComment = mapComment;
			}

			list.add(talker);
		}
		return list;
	}
	public static void saveTalker(Talker talker) {
		String path = talker.id.toString();
		config.set(path + "." + PathTalker.EDITOR, talker.editor);
		config.set(path + "." + PathTalker.STAGE, talker.stage);
		List<String> kanji = new ArrayList<String>();
		List<String> kana = new ArrayList<String>();
		List<String> en = new ArrayList<String>();
		for(Description description : talker.listSentence) {
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
		config.set(path + "." + PathTalker.KANJI, kanji);
		config.set(path + "." + PathTalker.KANA, kana);
		config.set(path + "." + PathTalker.EN, en);
		config.set(path + "." + PathTalker.KEY, talker.getKey());
		config.set(path + "." + PathTalker.Q + "." + KeyQuestion.EN, talker.question.getEn());
		config.set(path + "." + PathTalker.Q + "." + KeyQuestion.JP, talker.question.getJp());
		config.set(path + "." + PathTalker.A + "." + KeyAnswer.EN, talker.answer.getEn());
		config.set(path + "." + PathTalker.A + "." + KeyAnswer.JP, talker.answer.getJp());
		for(DataComment comment : talker.mapComment.values()) {
			String pathComment = path + "." + PathTalker.COMMENT + "." + comment.sender;
			config.set(pathComment + "." + PathComment.STATE, comment.state.toString());
			config.set(pathComment + "." + PathComment.EXPRESSION, comment.expression);
		}
	}
	@Override
	public void setFile(File file) {
		ConfigHandlerTalker.file = file;
	}
	@Override
	public void setConfig(YamlConfiguration config) {
		ConfigHandlerTalker.config = config;
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
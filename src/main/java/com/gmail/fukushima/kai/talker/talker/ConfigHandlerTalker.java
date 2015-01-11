package com.gmail.fukushima.kai.talker.talker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.fukushima.kai.comment.comment.DataComment;
import com.gmail.fukushima.kai.comment.comment.DataComment.CommentState;
import com.gmail.fukushima.kai.common.common.Description;
import com.gmail.fukushima.kai.talker.talker.TalkerAnswer.KeyAnswer;
import com.gmail.fukushima.kai.talker.talker.TalkerQuestion.KeyQuestion;
import com.gmail.fukushima.kai.utilities.utilities.ConfigHandler;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class ConfigHandlerTalker extends ConfigHandler {
	public static YamlConfiguration config;
	public static File file;
	public static final String DIRECTORY = "TALKER";
	public static final String FILE_NAME = "TALKER.yml";
	public enum PathStage {TALKER, EDITOR}
	public enum PathTalker {NAME, EDITOR, STAGE, EN, KANJI, KANA, Q, A, COMMENT}
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
			String path = idString;
			talker.nameStage = memoryId.getString(PathTalker.STAGE.toString());
			talker.name = memoryId.getString(PathTalker.NAME.toString());
			talker.editor.addAll(memoryId.getStringList(PathTalker.EDITOR.toString()));
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
			String questionEn = memoryId.getString(PathTalker.Q + "." + KeyQuestion.EN);
			String questionJp = memoryId.getString(PathTalker.Q + "." + KeyQuestion.JP);
			talker.question = new TalkerQuestion().create(questionEn, questionJp);
			List<String> answerEn = memoryId.getStringList(PathTalker.A + "." + KeyAnswer.EN);
			List<String> answerJp = memoryId.getStringList(PathTalker.A + "." + KeyAnswer.JP);
			talker.answer = new TalkerAnswer().create(answerEn, answerJp);
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
			list.add(talker);
		}
		return list;
	}
	public static void saveTalker(Talker talker) {
		String path = talker.id.toString();
		config.set(path + "." + PathTalker.NAME, talker.name);
		config.set(path + "." + PathTalker.EDITOR, talker.editor);
		config.set(path + "." + PathTalker.STAGE, talker.nameStage);
		List<String> kanji = new ArrayList<String>();
		List<String> kana = new ArrayList<String>();
		List<String> en = new ArrayList<String>();
		for(Description description : talker.listSentence) {
			kanji.add(description.kanji.get(0));
			kana.add(description.kana.get(0));
			en.add(description.en.get(0));
		}
		config.set(path + "." + PathTalker.KANJI, kanji);
		config.set(path + "." + PathTalker.KANA, kana);
		config.set(path + "." + PathTalker.EN, en);
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
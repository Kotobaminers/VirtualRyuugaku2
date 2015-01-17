package com.github.orgs.kotobaminers.virtualryuugaku.talker.talker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.comment.DataComment;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.comment.DataComment.CommentState;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.TalkerAnswer.KeyAnswer;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.TalkerQuestion.KeyQuestion;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class LibraryHandlerTalker {
	public enum PathStage {TALKER, EDITOR}
	public enum PathTalker {NAME, EDITOR, EN, KANJI, KANA, Q, A, COMMENT}
	public enum PathComment {STATE, EXPRESSION}
	//Talker
	public static List<Talker> importTalkerLibrary(String stage, YamlConfiguration library) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Talker> list = new ArrayList<Talker>();
		UtilitiesProgramming.printDebugMessage("Stage: " + stage, new Exception());
		List<String> editor = library.getStringList(PathTalker.EDITOR.toString());
		for(String talkerPath : library.getKeys(false)) {
			if(talkerPath.equalsIgnoreCase(PathStage.TALKER.toString())) {
				MemorySection memory = (MemorySection) library.get(talkerPath);
				for(String idString : memory.getKeys(false)) {
					MemorySection memoryId = (MemorySection) memory.get(idString);

					Integer id = Integer.parseInt(idString.toString());
					Talker talker = new Talker();
					talker.id = id;
					talker.name = memoryId.getString(PathTalker.NAME.toString());
					talker.stage = stage;
					talker.editor.addAll(editor);

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

					if(memoryId.contains(PathTalker.COMMENT.toString())) {
						Map<String, DataComment> mapComment = new HashMap<String, DataComment>();
						MemorySection memoryComment = (MemorySection) memoryId.get(PathTalker.COMMENT.toString());
						if(memoryComment == null) {
							System.out.println("NULL");
						}
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
					UtilitiesProgramming.printDebugTalker(talker);
				}
			}
		}
		return list;
	}
}
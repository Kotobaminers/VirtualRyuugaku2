package com.github.orgs.kotobaminers.virtualryuugaku.talker.talker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.PathTalker;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.TalkerAnswer.KeyAnswer;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.talker.TalkerQuestion.KeyQuestion;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class LibraryHandlerTalker {
	private enum PathStage {TALKER, EDITOR}
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
					talker.stage = stage;
					talker.editor.addAll(editor);
					//Name will be imported from citizens data.

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

					//Comments will be imported from Talker.yml
					list.add(talker);
					UtilitiesProgramming.printDebugTalker(talker);
				}
			}
		}
		return list;
	}
}
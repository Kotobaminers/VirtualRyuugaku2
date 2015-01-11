package com.gmail.fukushima.kai.talker.talker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.fukushima.kai.common.common.Description;
import com.gmail.fukushima.kai.talker.talker.TalkerAnswer.KeyAnswer;
import com.gmail.fukushima.kai.talker.talker.TalkerQuestion.KeyQuestion;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesGeneral;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class LibraryHandlerTalker {
	public enum PathStage {TALKER, EDITOR}
	public enum PathTalker {NAME, EDITOR, EN, KANJI, KANA, Q, A}
	//Talker
	public static List<Talker> importTalkerLibrary(YamlConfiguration library) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Talker> list = new ArrayList<Talker>();
		String stage = library.getName();
		List<String> editor = library.getStringList(PathTalker.EDITOR.toString());
		for(String talkerPath : library.getKeys(false)) {
			if(talkerPath.equalsIgnoreCase(PathStage.TALKER.toString())) {
				UtilitiesProgramming.printDebugMessage(talkerPath, new Exception());
				MemorySection memory = (MemorySection) library.get(talkerPath);
				for(String idString : memory.getKeys(false)) {
					UtilitiesProgramming.printDebugMessage(idString, new Exception());
					Integer id = Integer.parseInt(idString.toString());
					Talker talker = new Talker();
					talker.id = id;
					talker.nameStage = stage;
					String[] array = {stage, talkerPath, idString};
					String path = UtilitiesGeneral.joinStrings(array, ".");
					talker.name = library.getString(path + "." + PathTalker.NAME.toString());
					talker.editor.addAll(editor);
					UtilitiesProgramming.printDebugTalker(talker);

					List<String> kanji = library.getStringList(path + "." + PathTalker.KANJI.toString());
					List<String> kana = library.getStringList(path + "." + PathTalker.KANA.toString());
					List<String> en = library.getStringList(path + "." + PathTalker.EN.toString());
					Integer size = kanji.size();
					List<Description> listSentence = new ArrayList<Description>();
					if(size.equals(kana.size()) && size.equals(en.size())) {
						for(int i = 0; i < size; i++) {
							listSentence.add(Description.create(kanji.get(i), kana.get(i), en.get(i), new ArrayList<String>()));
						}
					}
					talker.listSentence = listSentence;
					UtilitiesProgramming.printDebugTalker(talker);

					String questionEn = library.getString(path + "." + PathTalker.Q + "." + KeyQuestion.EN);
					String questionJp = library.getString(path + "." + PathTalker.Q + "." + KeyQuestion.JP);
					talker.question = new TalkerQuestion().create(questionEn, questionJp);
					UtilitiesProgramming.printDebugTalker(talker);

					List<String> answerEn = library.getStringList(path + "." + PathTalker.A + "." + KeyAnswer.EN);
					List<String> answerJp = library.getStringList(path + "." + PathTalker.A + "." + KeyAnswer.JP);
					talker.answer = new TalkerAnswer().create(answerEn, answerJp);
					UtilitiesProgramming.printDebugTalker(talker);

					list.add(talker);
				}
			}
		}
		return list;
	}
}
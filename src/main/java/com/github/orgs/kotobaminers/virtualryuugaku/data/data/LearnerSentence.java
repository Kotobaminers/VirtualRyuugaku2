package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LearnerSentence extends TalkSentence {
	public LearnerSentence(List<String> kanji, List<String> kana, List<String> en, Integer id) {
		super(kanji, kana, en, id);
	}

	private String stage = "";

	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}

	public static Optional<List<LearnerSentence>> createLearnerSentences(List<String> kanji, List<String> kana, List<String> en, String stage) {
		List<LearnerSentence> sentences = new ArrayList<LearnerSentence>();
		if(kanji.size() == kana.size() && kanji.size() == en.size() && 0 < stage.length()) {
			for (Integer i = 0; i < kanji.size(); i++) {
				LearnerSentence sentence = new LearnerSentence(Arrays.asList(kanji.get(i)), Arrays.asList(kana.get(i)), Arrays.asList(en.get(i)), -1);
				sentence.setStage(stage);
				sentences.add(sentence);
			}
		}
		if (0 < sentences.size()) {
			return Optional.ofNullable(sentences);
		}
		return Optional.empty();
	}



}

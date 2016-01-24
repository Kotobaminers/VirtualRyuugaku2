package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;

public class SentenceEditor {
	private SpellType spell = null;
	public HolographicSentence sentence = null;

	public SentenceEditor(HolographicSentence sentence, SpellType spell) {
		this.sentence = sentence;
		this.spell = spell;
	}

	public void edit(String edit) {
		if (sentence != null && spell != null) {
			sentence.update(edit, spell);
			sentence = null;
			spell = null;
		}
	}
}

package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.Optional;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;

public class SentenceEditor {
	private Integer id;
	private Integer index;
	private SpellType spell;

	public static Optional<SentenceEditor> create(Integer id, Integer index, SpellType spell, Player player) {
		SentenceEditor editor = new SentenceEditor();
		Optional<String> stage = SentenceStorage.findStageNameByLearner(id);
		if (stage.isPresent()) {
			return SentenceStorage.findLearnerSentences(player.getUniqueId(), stage.get())
			.map(list -> {
				if (index < list.size()) {
					editor.id = id;
					editor.index= index;
					editor.spell = spell;
					return editor;
				}
				return null;
			});
		}

		return SentenceStorage.findNPCSentences.apply(id)
			.map(list -> {
				if (index < list.size()) {
					editor.id = id;
					editor.index= index;
					editor.spell = spell;
					return editor;
				}
				return null;
			});
	}

	public boolean editSentence(String line, Player player) {
		Optional<Sentence> sentence = findSentence(player);
		String aaa = "aaa";
		if (sentence.isPresent()) {
			sentence.get().update(line, spell);
			return true;
		}
		return false;
	}

	private Optional<Sentence> findSentence(Player player) {
		Optional<String> stage = SentenceStorage.findStageNameByLearner(id);
		if (stage.isPresent()) {
			return SentenceStorage.findLearnerSentences(player.getUniqueId(), stage.get())
				.map(list -> {
					if (index < list.size()) {
						return list.get(index);
					}
					return null;
				});
		}
		return SentenceStorage.findNPCSentences.apply(this.id)
			.map(list -> {
				if (index < list.size()) {
					return list.get(index);
				}
				return null;
			});
	}

}

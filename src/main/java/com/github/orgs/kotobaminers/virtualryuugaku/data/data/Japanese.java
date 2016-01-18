package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Romaji;

public class Japanese extends Line {
	private Map<SpellType, List<String>> lines;

	public Japanese(List<String> kanji, List<String> kana) {
		lines = new HashMap<SpellType, List<String>>();
		lines.put(SpellType.KANJI, kanji);
		lines.put(SpellType.KANA, kana);
		lines.put(SpellType.ROMAJI, kana.stream().
				map(line -> Romaji.toRomaji(line)).
				collect(Collectors.toList()));
	}

	@Override
	public Optional<List<String>> getLine(List<SpellType> spells) {
		List<String> expressions = new ArrayList<String>();
		spells.stream().filter(spell -> lines.containsKey(spell))
			.forEach(spell -> expressions.addAll(lines.get(spell)));
		if (0 < expressions.size()) {
			return Optional.ofNullable(expressions);
		}
		return Optional.empty();
	}
	@Override
	public List<String> getLine() {
		List<String> all = new ArrayList<String>();
		Arrays.asList(SpellType.KANJI, SpellType.KANA, SpellType.ROMAJI).stream()
			.filter(spell -> lines.containsKey(spell))
			.forEach(spell -> all.addAll(lines.get(spell)));
		return all;
	}

}

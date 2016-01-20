package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;

public class English extends Line {
	private List<String> en;

	public English(List<String> en) {
		this.en = en;
	}

	@Override
	public List<String> getLine() {
		return en;
	}

	@Override
	public Optional<List<String>> getLine(List<SpellType> spells) {
		if (spells.contains(SpellType.EN)) {
			return Optional.ofNullable(en);
		}
		return Optional.empty();
	}

	@Override
	public void update(String line, SpellType spell) {
		this.en = Arrays.asList(line);
	}
}

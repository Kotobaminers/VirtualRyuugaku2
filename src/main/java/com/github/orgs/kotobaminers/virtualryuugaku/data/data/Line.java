package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.List;
import java.util.Optional;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;

public abstract class Line {
	public abstract Optional<List<String>> getLine(List<SpellType> spell);
	public abstract List<String> getLine();
}

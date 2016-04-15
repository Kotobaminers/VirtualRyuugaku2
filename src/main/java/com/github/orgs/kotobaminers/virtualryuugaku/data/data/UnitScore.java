package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.HashSet;
import java.util.Set;

public class UnitScore {
	public String name = "";
	public Set<Integer> done = new HashSet<>();

	public UnitScore(String name) {
		this.name = name;
	}

}

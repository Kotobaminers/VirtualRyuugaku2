package com.gmail.fukushima.kai.common.common;

import java.util.List;

import org.bukkit.entity.Player;

public class Sentence extends Description {
	public Sentence(List<String> kanji, List<String> kana, List<String> en, List<String> hint) {
		super(kanji, kana, en, hint);
	}
	public Sentence() {
		super();
	}
	@Override
	public void sendMessage(Player player) {
	}
}

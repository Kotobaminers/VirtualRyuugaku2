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
//	public void saveAtConfig(ConfigHandler configHandler, String path) {
//		configHandler.getConfig().set(path + "." + Path.ENGL, en);
//		configHandler.getConfig().set(path + "." + Path.KANJ, kanji);
//		configHandler.getConfig().set(path + "." + Path.KANA, kana);
//	}
}

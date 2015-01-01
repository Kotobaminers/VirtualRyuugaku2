package com.gmail.fukushima.kai.common.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.utilities.utilities.UtilitiesGeneral;

public abstract class Description {
	public List<String> kanji;
	public List<String> kana;
	public List<String> en;
	public List<String> romaji;
	public List<String> tips;
	private final List<ChatColor> colorsJp = Arrays.asList(ChatColor.WHITE, ChatColor.GRAY, ChatColor.DARK_GRAY);
	private final List<ChatColor> colorsSingle = Arrays.asList(ChatColor.WHITE);
	public Description() {
	}
	public Description(List<String> kanji, List<String> kana, List<String> en, List<String> tips) {
		this.kanji = kanji;
		this.kana = kana;
		this.en = en;
		this.tips = tips;
		List<String> romaji = new ArrayList<String>();
		for(String string : kana) {
			romaji.add(UtilitiesGeneral.toRomaji(string));
		}
		this.romaji = romaji;
	}
	public void sendDescription(Player player) {
		sendEn(player);
		sendJp(player);
		sendTips(player);
	}
	public void sendEn(Player player) {
		player.sendMessage(UtilitiesGeneral.joinListListString(Arrays.asList(en), colorsSingle));
	}
	public void sendJp(Player player) {
		player.sendMessage(UtilitiesGeneral.joinListListString(Arrays.asList(kanji, kana, romaji), colorsJp));
	}
	public void sendTips(Player player) {
		player.sendMessage(UtilitiesGeneral.joinListListString(Arrays.asList(tips), colorsSingle));
	}
	public abstract void sendMessage(Player player);
	public void printDebug() {
		System.out.println("  " + kanji + kana + en + romaji + tips);
	}
}
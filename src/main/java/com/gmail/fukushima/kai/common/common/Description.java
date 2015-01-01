package com.gmail.fukushima.kai.common.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.utilities.utilities.UtilitiesGeneral;

public abstract class Description {
	public List<String> kanji = new ArrayList<String>();
	public List<String> kana = new ArrayList<String>();
	public List<String> en = new ArrayList<String>();
	public List<String> romaji = new ArrayList<String>();
	public List<String> tips = new ArrayList<String>();
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
	public String loadEn() {
		String message = UtilitiesGeneral.joinListListString(Arrays.asList(en), colorsSingle);
		return message;
	}
	public String loadJp() {
		String message = UtilitiesGeneral.joinListListString(Arrays.asList(kanji, kana, romaji), colorsJp);
		return message;
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
	public Boolean validEn(String answer) {
		for(String search : en) {
			if(search.equalsIgnoreCase(answer)) {
				return true;
			}
		}
		return false;
	}
	public Boolean validJp(String answer) {
		for(String search : romaji) {
			if(search.equalsIgnoreCase(answer)) {
				return true;
			}
		}
		for(String search : kana) {
			if(search.equalsIgnoreCase(answer)) {
				return true;
			}
		}
		for(String search : kanji) {
			if(search.equalsIgnoreCase(answer)) {
				return true;
			}
		}
		return false;
	}
}
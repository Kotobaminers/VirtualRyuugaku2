package com.gmail.fukushima.kai.common.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;

import com.gmail.fukushima.kai.utilities.utilities.UtilitiesGeneral;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class Description {
	public List<String> kanji = new ArrayList<String>();
	public List<String> kana = new ArrayList<String>();
	public List<String> en = new ArrayList<String>();
	public List<String> romaji = new ArrayList<String>();
	public List<String> tips = new ArrayList<String>();
	private final List<ChatColor> colorsJp = Arrays.asList(ChatColor.WHITE, ChatColor.GRAY, ChatColor.DARK_GRAY);
	private final List<ChatColor> colorsSingle = Arrays.asList(ChatColor.WHITE);
	public enum Path {ENGL, KANJ, KANA}
	public enum Expression {NONE, EN, KANJI, KANA;
		public static Expression lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return Expression.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return Expression.NONE;
			}
		}
	}

	public static Description create(String kanji, String kana, String en, List<String> tips) {
		Description description = new Description();
		description.kanji = Arrays.asList(kanji);
		description.kana = Arrays.asList(kana);
		description.en = Arrays.asList(en);
		description.tips = tips;
		description.romaji = Arrays.asList(UtilitiesGeneral.toRomaji(kana));
		return description;
	}

	public String loadEn() {
		String message = "";
		if(0 < UtilitiesGeneral.getTotalLengthStrings(en)) {
			message = UtilitiesGeneral.joinListListString(Arrays.asList(en), colorsSingle);
		}
		return message;
	}
	public String loadJp() {
		String message = "";
		if(0 < UtilitiesGeneral.getTotalLengthStrings(kanji) || 0 < UtilitiesGeneral.getTotalLengthStrings(kana)) {
			message = UtilitiesGeneral.joinListListString(Arrays.asList(kanji, kana, romaji), colorsJp);
		}
		return message;
	}

	public void printDebug() {
		UtilitiesProgramming.printDebugMessage("  " + kanji + kana + en + romaji + tips, new Exception());
	}
}
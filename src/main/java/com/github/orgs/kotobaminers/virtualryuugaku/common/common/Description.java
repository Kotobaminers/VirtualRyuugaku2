package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;

public class Description {
	private static final String SPACER = ChatColor.RED + ", " + ChatColor.RESET;

	public List<String> kanji = new ArrayList<String>();
	public List<String> kana = new ArrayList<String>();
	public List<String> en = new ArrayList<String>();
	public List<String> romaji = new ArrayList<String>();
	public List<String> tips = new ArrayList<String>();
	public enum Path {ENGL, KANJ, KANA}

	public static Description create(String kanji, String kana, String en, List<String> tips) {
		Description description = new Description();
		description.kanji = Arrays.asList(kanji);
		description.kana = Arrays.asList(kana);
		description.en = Arrays.asList(en);
		description.tips = tips;
		description.romaji = Arrays.asList(UtilitiesGeneral.toRomaji(kana));
		return description;
	}

	public String express(Expression expression) {
		List<String> list = new ArrayList<String>();
		switch(expression) {
		case ROMAJI://No break;
			list.add(expressRomaji());
		case KANA://No break;
			list.add(expressKana());
		case KANJI:
			list.add(expressKanji());
			break;
		case EN:
			list.add(expressEn());
			break;
		case NONE:
			break;
		default:
			break;
		}
		String message = UtilitiesGeneral.joinStrings(list, SPACER);
		return message;
	}
	private String expressEn() {
		String message = UtilitiesGeneral.joinStrings(en, SPACER);
		return message;
	}
	private String expressKanji() {
		String message = UtilitiesGeneral.joinStrings(kanji, SPACER);
		return message;
	}
	private String expressKana() {
		String message = UtilitiesGeneral.joinStrings(kana, SPACER);
		return message;
	}
	private String expressRomaji() {
		String message = UtilitiesGeneral.joinStrings(romaji, SPACER);
		return message;
	}
}
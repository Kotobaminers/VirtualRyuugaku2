package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
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

	public void print(Player player) {
		List<Expression> expressions = DataManagerPlayer.getDataPlayer(player).expressions;
		if(expressions.contains(Expression.EN)) {
			String[] opts = {getEnglish()};
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.DESCRIPTION_0, opts));
		}
		List<String> listJapanese = new ArrayList<String>();
		if(expressions.contains(Expression.KANJI)) {
			listJapanese.add(expressKanji());
		}
		if(expressions.contains(Expression.KANA)) {
			listJapanese.add(expressKana());
		}
		if(expressions.contains(Expression.ROMAJI)) {
			listJapanese.add(expressRomaji());
		}
		String japanese = UtilitiesGeneral.joinStrings(listJapanese, ", ");
		if(0 < japanese.length()) {
			String[] opts = {MessengerGeneral.PREFIX_JP + japanese};
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.DESCRIPTION_0, opts));
		}

	}

	public String getExpression(Player player) {
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		String message = "";
		switch(data.language) {
		case EN:
			message = getEnglish();
			break;
		case JP:
			message = getJapanese(player);
			break;
		case DEFAULT:
		default:
			break;
		}
		return message;
	}
	public String getJapanese(Player player) {
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		List<String> japanese = new ArrayList<String>();
		switch(data.japanese) {
		case ROMAJI:
			japanese.addAll(kanji);
			japanese.addAll(kana);
			japanese.addAll(romaji);
			break;
		case KANA:
			japanese.addAll(kanji);
			japanese.addAll(kana);
			break;
		case KANJI:
			japanese.addAll(kanji);
			break;
		case DEFAULT:
		default:
			break;
		}
		String message = MessengerGeneral.PREFIX_JP + UtilitiesGeneral.joinStrings(japanese, SPACER);
		return message;
	}
	public String getEnglish() {
		String message = MessengerGeneral.PREFIX_EN + UtilitiesGeneral.joinStrings(en, SPACER);
		return message;
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
	public List<String> getJapaneseList() {
		List<String> list = new ArrayList<String>();
		list.addAll(kanji);
		list.addAll(kana);
		list.addAll(romaji);
		return list;
	}
	public List<String> getEnglishList() {
		List<String> list = new ArrayList<String>();
		list.addAll(en);
		return list;
	}

}
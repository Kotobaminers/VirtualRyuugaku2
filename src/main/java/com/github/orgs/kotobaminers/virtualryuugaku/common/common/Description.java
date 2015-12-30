package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Expression;

public class Description {
	private static final String SPACER = ChatColor.RED + ", " + ChatColor.RESET;

	public List<String> kanji = new ArrayList<String>();
	public List<String> kana = new ArrayList<String>();
	public List<String> en = new ArrayList<String>();
	public List<String> romaji = new ArrayList<String>();
	public List<String> tips = new ArrayList<String>();

	public static Description create(String kanji, String kana, String en, List<String> tips) {
		Description description = new Description();
		description.kanji = Arrays.asList(kanji);
		description.kana = Arrays.asList(kana);
		description.en = Arrays.asList(en);
		description.tips = tips;
		description.romaji = Arrays.asList(UtilitiesGeneral.toRomaji(kana));
		return description;
	}

	public String getJapaneseJoined(Player player) {
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		List<String> japanese = new ArrayList<String>();
		for(Expression expression : data.expressions) {
			switch(expression) {
			case ROMAJI:
				japanese.addAll(romaji);
				break;
			case KANA:
				japanese.addAll(kana);
				break;
			case KANJI:
				japanese.addAll(kanji);
				break;
			default:
				break;
			}
		}
		String message = UtilitiesGeneral.joinStrings(japanese, SPACER);
		return message;
	}

	public String getEnglishJoined() {
		String message = UtilitiesGeneral.joinStrings(en, SPACER);
		return message;
	}
	public String getKanjiJoined() {
		String message = UtilitiesGeneral.joinStrings(kanji, SPACER);
		return message;
	}
	public String getKanaJoined() {
		String message = UtilitiesGeneral.joinStrings(kana, SPACER);
		return message;
	}
	public String getRomajiJoined() {
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
package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class Letters {
	private String hiragana = "";
	private String katakana = "";
	private String romaji = "";
	private String type = "";
	public void printDebug() {
		String[] debug = {hiragana, katakana, romaji, type};
		UtilitiesProgramming.printDebugMessage(UtilitiesGeneral.joinStrings(debug, " "), new Exception());
	}
	public String getHiragana() {
		return hiragana;
	}
	public void setHiragana(String hiragana) {
		this.hiragana = hiragana;
	}
	public String getKatakana() {
		return katakana;
	}
	public void setKatakana(String katakana) {
		this.katakana = katakana;
	}
	public String getRomaji() {
		return romaji;
	}
	public void setRomaji(String romaji) {
		this.romaji = romaji;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}

package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class Enums {//public enums
	public enum Commands {
		VIRTUALRYUUGAKU, VRG,
		VIRTUALRYUUGAKUOP, VRGOP, VRGDBG,
		STAGE, STAGEOP,
		CONVERSATION, CONVERSATIONOP,
		CONV, CONVOP,
		ANSWER, ANS,
		;
		public static Commands lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return Commands.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return Commands.VRG;
			}
		}
	}
	public enum Expression {NONE, EN, KANJI, KANA, ROMAJI;
		public static Expression lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return Expression.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return Expression.ROMAJI;
			}
		}
	}
	public enum Language {DEFAULT, EN, JP;
		public static Language lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return Language.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return Language.JP;
			}
		}
	}
	public enum Japanese {DEFAULT, KANJI, KANA, ROMAJI;
	public static Japanese lookup(String name) {
		try {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			return Japanese.valueOf(name.toUpperCase());
		} catch (IllegalArgumentException e) {
			UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
			return Japanese.ROMAJI;
		}
	}
}
	public enum PathConversation {STAGE, EDITOR, EN, KANJI, KANA, KEY, Q, A, COMMENT}
	public enum PathComment {STATE, EXPRESSION}
}

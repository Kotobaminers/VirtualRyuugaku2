package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class Enums {//public enums
	public enum Commands {
		VIRTUALRYUUGAKU, VRG,
		VIRTUALRYUUGAKUOP, VRGOP, VRGDBG,
		STAGE, STAGEOP,
		CONVERSATION, CONVERSATIONOP,
		CONV, CONVOP,
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
	public enum PathConversation {STAGE, EDITOR, EN, KANJI, KANA, KEY, Q, A, COMMENT}
	public enum PathComment {STATE, EXPRESSION}
}

package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class Enums {//public enums
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
}

package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class CommandVirturalRyuugakuConsole {
	public enum Debug {
		NONE, CITIZENS, PLAYER, RELOAD, TALKER, COMMENT;
		public static Debug lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return Debug.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return Debug.NONE;
			}
		}
	}
	public void printDebug(String[] args) {
		if(0 < args.length) {
			Debug debug = Debug.lookup(args[0]);
			switch(debug) {
			case CITIZENS:
				UtilitiesProgramming.printDebugCitizensAll();
				break;
			case PLAYER:
				UtilitiesProgramming.printDebugPlayerAll();
				break;
			case RELOAD:
				DataManagerPlugin.loadPlugin();;
				break;
			case TALKER:
				UtilitiesProgramming.printDebugTalkerAll();
				break;
			case COMMENT:
				UtilitiesProgramming.printDebugCommentAll();
				break;
			case NONE:
				break;
			default:
				break;
			}
		}
	}
}
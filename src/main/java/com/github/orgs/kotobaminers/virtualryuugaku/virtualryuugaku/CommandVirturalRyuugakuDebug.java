package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandVirturalRyuugakuDebug {
	public enum Debug {
		NONE, PLAYER, RELOAD, MODE;
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
			case PLAYER:
				UtilitiesProgramming.printDebugPlayerAll();
				break;
			case RELOAD:
				DataManagerPlugin.loadPlugin();;
				break;
			case MODE:
				commandDebugMode();
				break;
			case NONE:
				break;
			default:
				break;
			}
		}
	}
	private void commandDebugMode() {
		if(!Settings.debugMessage) {
			Settings.debugMessage = true;
			UtilitiesProgramming.printDebugMessage("[VRG Debug] Message = " + Settings.debugMessage + ", BC = " + Settings.debugMessageBroadcast, new Exception());
		} else {
			if(!Settings.debugMessageBroadcast) {
				Settings.debugMessageBroadcast = true;
				UtilitiesProgramming.printDebugMessage("[VRG Debug] Message = " + Settings.debugMessage + ", BC = " + Settings.debugMessageBroadcast, new Exception());
			} else {
				Settings.debugMessage = false;
				Settings.debugMessageBroadcast = false;
				UtilitiesProgramming.printDebugMessage("[VRG Debug] Message = " + Settings.debugMessage + ", BC = " + Settings.debugMessageBroadcast, new Exception());
			}
		}
	}
}
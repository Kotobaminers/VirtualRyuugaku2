package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.LibraryManager;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation0.ConfigHandlerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation0.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.myself.myself.ControllerMyself;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.vrgnpc.vrgnpc.DataManagerVRGNPC;

public class CommandVirturalRyuugakuDebug {
	public enum Debug {
		NONE, CITIZENS, PLAYER, RELOAD, CONVERSATION, COMMENT, MODE, CONV, LOADCONV, LOADCONVERSATION, NPC, LOADNPC, MYSELF;
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
			case MYSELF:
				ControllerMyself.printDebugMyselfAll();
				break;
			case CITIZENS:
				UtilitiesProgramming.printDebugCitizensAll();
				break;
			case PLAYER:
				UtilitiesProgramming.printDebugPlayerAll();
				break;
			case RELOAD:
				DataManagerPlugin.loadPlugin();;
				break;
			case CONVERSATION:
			case CONV:
				UtilitiesProgramming.printDebugConversationAll();
				break;
			case COMMENT:
				UtilitiesProgramming.printDebugCommentAll();
				break;
			case MODE:
				commandDebugMode();
				break;
			case LOADCONVERSATION:
			case LOADCONV:
				new ConfigHandlerConversation().initialize(DataManagerPlugin.plugin);
				new DataManagerConversation().load();
				break;
			case NPC:
				UtilitiesProgramming.printDebugVRGNPCAll();
				break;
			case LOADNPC:
				new LibraryManager().initialize(DataManagerPlugin.plugin);
				new DataManagerVRGNPC().load();
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
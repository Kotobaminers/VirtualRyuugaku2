package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Settings;


public class Debug {
	public static void printDebugMessage(String message, Exception exception) {
		if(!Settings.debugMessage) return;
		StackTraceElement element = exception.getStackTrace()[0];
		String nameClass = element.getClassName();
		String[] split = nameClass.split("\\.");
		nameClass = split[split.length - 1];
		String nameMethod = element.getMethodName();
		String line = String.valueOf(element.getLineNumber());
		if(Settings.debugMessageBroadcast) {
			String[] broadcast = {ChatColor.RED.toString(), message, ChatColor.GRAY.toString(), nameClass, nameMethod, ChatColor.WHITE.toString(), line};
			Bukkit.broadcastMessage(Utility.joinStrings(broadcast, " "));
		} else {
			String[] broadcast = {message, nameClass, nameMethod, line};
			Bukkit.getLogger().info(Utility.joinStrings(broadcast, " "));
		}
	}

//	public static void printDebugPlayerAll() {
//		printDebugMessage("", new Exception());
//		for(DataPlayer data : DataManagerPlayer.getMapDataPlayer().values()) {
//			printDebugPlayer(data);
//		}
//	}
//	public static void printDebugPlayer(DataPlayer data) {
//		printDebugMessage("[Debug DataPlayer] " + data.name, new Exception());
//		printDebugMessage(" LINE: " + data.line, new Exception());
//	}
//	public static void printDebugConversation(Conversation conversation) {
//		if(!(0 < conversation.listTalk.size())) return;
//		printDebugMessage("[Debug Conversation] " + "STAGE: " + conversation.stageName + ", EDITOR: " + conversation.editor, new Exception());
//		for(Talk talk : conversation.listTalk) {
//			printDebugTalk(talk);
//		}
//		printDebugMessage(conversation.question.getQuestion(), new Exception());
//		printDebugMessage(UtilitiesGeneral.joinStrings(conversation.question.getAnswers(), ", "), new Exception());
//	}
//	public static void printDebugTalk(Talk talk) {
//		printDebugMessage("[Debug Talk] ID: " + talk.id, new Exception());
//		printDebugDescription(talk.description);
//	}
}
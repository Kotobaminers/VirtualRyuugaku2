package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Settings;


public class Debug {
	private static final List<UUID> DEBUGGER = Arrays.asList(
		UUID.fromString("de7bd32b-48a9-4aae-9afa-ef1de55f5bad"),
		UUID.fromString("ae6898a3-63f9-45b0-ac17-c8be45210d18"));

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
			Bukkit.broadcastMessage(String.join(" ", broadcast));
		} else {
			String[] broadcast = {ChatColor.RED + " *" + ChatColor.RESET, message, nameClass, nameMethod, line};
			Bukkit.broadcastMessage(String.join(" ", broadcast));
			for (Player online : Bukkit.getOnlinePlayers()) {
				if (DEBUGGER.contains(online.getUniqueId())) {
					Bukkit.broadcastMessage(String.join(" ", broadcast));
				}
			}
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
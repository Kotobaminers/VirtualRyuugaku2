package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.ConfigCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.comment.DataComment;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMulti;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation0.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Settings;
import com.github.orgs.kotobaminers.virtualryuugaku.vrgnpc.vrgnpc.DataManagerVRGNPC;
import com.github.orgs.kotobaminers.virtualryuugaku.vrgnpc.vrgnpc.VRGNPC;


public class UtilitiesProgramming {
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
			Bukkit.broadcastMessage(UtilitiesGeneral.joinStrings(broadcast, " "));
		} else {
			String[] broadcast = {message, nameClass, nameMethod, line};
			Bukkit.getLogger().info(UtilitiesGeneral.joinStrings(broadcast, " "));
		}
	}

	public static void printDebugCitizensAll() {
		printDebugMessage("", new Exception());
		for(DataCitizens data : ConfigCitizens.getMapDataCitizens().values()) {
			printDebugCitizens(data);
		}
	}
	public static void printDebugPlayerAll() {
		printDebugMessage("", new Exception());
		for(DataPlayer data : DataManagerPlayer.getMapDataPlayer().values()) {
			printDebugPlayer(data);
		}
	}
	public static void printDebugConversationAll() {
		printDebugMessage("", new Exception());
		for(ConversationMulti data : DataManagerConversation.getMapConversation().values()) {
			printDebugConversation(data);
		}
	}
	public static void printDebugCommentAll() {
		printDebugMessage("", new Exception());
		for(ConversationMulti data : DataManagerConversation.getMapConversation().values()) {
			for(DataComment comment : data.mapComment.values()) {
				printDebugComment(comment);
			}
		}
	}
	public static void printDebugVRGNPCAll() {
		printDebugMessage("", new Exception());
		for(VRGNPC vrgnpc : DataManagerVRGNPC.getMapVRGNPC().values()) {
			printDebugVRGNPC(vrgnpc);
		}
	}
	private static void printDebugVRGNPC(VRGNPC data) {
		printDebugMessage("[Debug VRGNPC] NAME: " + data.name + ", STAGE: " + data.stage + ", ID: " + data.id + ", EDITOR: " + UtilitiesGeneral.joinStrings(data.editor, ", ") + UtilitiesGeneral.joinStrings(data.editor, ", "), new Exception());
		for(Description description : data.listDescription) {
			printDebugDescription(description);
		}
	}
	public static void printDebugPlayer(DataPlayer data) {
		printDebugMessage("[Debug DataPlayer] " + data.name, new Exception());
		printDebugMessage(" LINE: " + data.line, new Exception());
		printDebugMessage(" SELECT: " + data.select, new Exception());
		printDebugMessage(" LANGUAGE: " + data.language, new Exception());
	}
	public static void printDebugConversation(Conversation conversation) {
		if(!(0 < conversation.listTalk.size())) return;
		printDebugMessage("[Debug Conversation] " + "STAGE: " + conversation.stage + ", EDITOR: " + conversation.editor, new Exception());
		for(Talk talk : conversation.listTalk) {
			printDebugTalk(talk);
		}
		printDebugMessage(conversation.question.getQuestion(), new Exception());
		printDebugMessage(UtilitiesGeneral.joinStrings(conversation.question.getAnswers(), ", "), new Exception());
		for(DataComment comment : conversation.mapComment.values()) {
			printDebugComment(comment);
		}
	}
	public static void printDebugCitizens(DataCitizens citizens) {
		printDebugMessage("ID: " + citizens.id + ", NAME: " + citizens.name, new Exception());
	}
	public static void printDebugComment(DataComment comment) {
		printDebugMessage("[Debug Comment] " + " SENDER: " + comment.sender + ", STATE: " + comment.state, new Exception());
		printDebugMessage(" COMMENT: " + comment.expression, new Exception());
	}
	public static void printDebugTalk(Talk talk) {
		printDebugMessage("[Debug Talk] ID: " + talk.id, new Exception());
		printDebugDescription(talk.description);
	}
	public static void printDebugDescription(Description description) {
		printDebugMessage(description.getEnglishJoined() + "/" + description.getKanjiJoined() + "/" + description.getKanaJoined() + "/" + description.getRomajiJoined(), new Exception());
	}

	public static void printCharCode(String string) {
		String dat = string;
		char[] buf1 = dat.toCharArray();
		String buf2 = new String("");

		for (char buf : buf1) {
			buf2 += Integer.toString(buf, 16) + " ";
		}
		UtilitiesProgramming.printDebugMessage("CharCode: " + buf2, new Exception());
	}
}
package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataManagerCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.comment.DataComment;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Settings;


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
		for(DataCitizens data : DataManagerCitizens.getMapDataCitizens().values()) {
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
		for(Conversation data : DataManagerConversation.getMapConversation().values()) {
			printDebugConversation(data);
		}
	}
	public static void printDebugCommentAll() {
		printDebugMessage("", new Exception());
		for(Conversation data : DataManagerConversation.getMapConversation().values()) {
			for(DataComment comment : data.mapComment.values()) {
				printDebugComment(comment);
			}
		}
	}

	public static void printDebugPlayer(DataPlayer data) {
		printDebugMessage("[Debug DataPlayer] " + data.name, new Exception());
		printDebugMessage(" LINE: " + data.line, new Exception());
		printDebugMessage(" SELECT: " + data.select, new Exception());
	}
	public static void printDebugConversation(Conversation conversation) {
		if(!(0 < conversation.listTalk.size())) return;
		printDebugMessage("[Debug Conversation] " + "STAGE: " + conversation.stage + ", EDITOR: " + conversation.editor + ", KEY: " + conversation.getKey(), new Exception());
		for(Talk talk : conversation.listTalk) {
			printDebugTalk(talk);
		}
		printDebugMessage(" Q(EN): " + conversation.question.getEn(), new Exception());
		printDebugMessage(" Q(JP): " + conversation.question.getJp(), new Exception());
		printDebugMessage(" A(EN): " + UtilitiesGeneral.joinStrings(conversation.answer.getEn(), ", "), new Exception());
		printDebugMessage(" A(JP): " + UtilitiesGeneral.joinStrings(conversation.answer.getJp(), ", "), new Exception());
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
		printDebugMessage("[Debug Talk] NAME: " + talk.name + ", ID: " + talk.id, new Exception());
		printDebugMessage(talk.description.express(Expression.EN), new Exception());
		printDebugMessage(talk.description.express(Expression.KANJI), new Exception());
		printDebugMessage(talk.description.express(Expression.KANA), new Exception());
		printDebugMessage(talk.description.express(Expression.ROMAJI), new Exception());
	}
}
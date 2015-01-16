package com.github.kotobaminers.virtualryuugaku.utilities.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.github.kotobaminers.virtualryuugaku.citizens.citizens.DataCitizens;
import com.github.kotobaminers.virtualryuugaku.citizens.citizens.DataManagerCitizens;
import com.github.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.kotobaminers.virtualryuugaku.talker.comment.DataComment;
import com.github.kotobaminers.virtualryuugaku.talker.talker.DataManagerTalker;
import com.github.kotobaminers.virtualryuugaku.talker.talker.Talker;
import com.github.kotobaminers.virtualryuugaku.virtualryuugaku.Settings;


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
			Bukkit.broadcastMessage(UtilitiesGeneral.joinStringsWithSpace(broadcast));
		} else {
			String[] broadcast = {message, nameClass, nameMethod, line};
			Bukkit.getLogger().info(UtilitiesGeneral.joinStrings(broadcast, " "));
		}
	}
	public static void printDebugCitizensAll() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(DataCitizens data : DataManagerCitizens.getMapDataCitizns().values()) {
			UtilitiesProgramming.printDebugCitizens(data);
		}
	}
	public static void printDebugPlayerAll() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(DataPlayer data : DataManagerPlayer.getMapDataPlayer().values()) {
			UtilitiesProgramming.printDebugPlayer(data);
		}
	}
	public static void printDebugTalkerAll() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(Talker data : DataManagerTalker.getMapTalker().values()) {
			UtilitiesProgramming.printDebugTalker(data);
		}
	}
	public static void printDebugCommentAll() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(Talker data : DataManagerTalker.getMapTalker().values()) {
			for(DataComment comment : data.mapComment.values()) {
				UtilitiesProgramming.printDebugComment(comment);
			}
		}
	}
	public static void printDebugPlayer(DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("[Debug DataPlayer] " + data.name, new Exception());
		UtilitiesProgramming.printDebugMessage(" LINE: " + data.line, new Exception());
		UtilitiesProgramming.printDebugMessage(" SELECT: " + data.select, new Exception());
	}
	public static void printDebugTalker(Talker talker) {
		if(talker.id < 0) return;
		UtilitiesProgramming.printDebugMessage("[Debug Talker] " + talker.name, new Exception());
		UtilitiesProgramming.printDebugMessage(" ID: " + talker.id + ", EDITOR: " + talker.editor + ", STAGE: " + talker.stage, new Exception());
		for(Description sentence : talker.listSentence) {
			UtilitiesProgramming.printDebugMessage(" SEN: " + sentence.loadEn(), new Exception());
			UtilitiesProgramming.printDebugMessage(" SEN: " + sentence.loadJp(), new Exception());
		}
		UtilitiesProgramming.printDebugMessage(" Q(EN): " + talker.question.getEn(), new Exception());
		UtilitiesProgramming.printDebugMessage(" Q(JP): " + talker.question.getJp(), new Exception());
		UtilitiesProgramming.printDebugMessage(" A(EN): " + UtilitiesGeneral.joinStrings(talker.answer.getEn(), ", "), new Exception());
		UtilitiesProgramming.printDebugMessage(" A(JP): " + UtilitiesGeneral.joinStrings(talker.answer.getJp(), ", "), new Exception());
		for(DataComment comment : talker.mapComment.values()) {
			UtilitiesProgramming.printDebugComment(comment);
		}
	}
	public static void printDebugCitizens(DataCitizens citizens) {
		UtilitiesProgramming.printDebugMessage("ID: " + citizens.id + ", NAME: " + citizens.name, new Exception());
	}
	public static void printDebugComment(DataComment comment) {
		UtilitiesProgramming.printDebugMessage("[Debug Comment] " + " SENDER: " + comment.sender + ", STATE: " + comment.state, new Exception());
		UtilitiesProgramming.printDebugMessage(" COMMENT: " + comment.expression, new Exception());
	}
}
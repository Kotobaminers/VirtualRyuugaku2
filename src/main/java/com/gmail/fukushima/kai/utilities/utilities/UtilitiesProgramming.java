package com.gmail.fukushima.kai.utilities.utilities;

import org.bukkit.Bukkit;

import com.gmail.fukushima.kai.citizens.citizens.DataCitizens;
import com.gmail.fukushima.kai.citizens.citizens.DataManagerCitizens;
import com.gmail.fukushima.kai.comment.comment.DataComment;
import com.gmail.fukushima.kai.comment.comment.DataManagerComment;
import com.gmail.fukushima.kai.common.common.Description;
import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
import com.gmail.fukushima.kai.talker.talker.DataManagerTalker;
import com.gmail.fukushima.kai.talker.talker.Talker;
import com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2.Settings;


public class UtilitiesProgramming {
	public static void printDebugMessage(String message, Exception exception) {
		if(!Settings.debugMessage) return;
		StackTraceElement element = exception.getStackTrace()[0];
		String nameClass = element.getClassName();
		String[] split = nameClass.split("\\.");
		nameClass = split[split.length - 1];
		String nameMethod = element.getMethodName();
		String line = String.valueOf(element.getLineNumber());
		String[] broadcast = {message, nameClass, nameMethod, line};
		if(Settings.debugMessageBroadcast) {
			Bukkit.broadcastMessage(UtilitiesGeneral.joinStringsWithSpace(broadcast));
		} else {
			Bukkit.getLogger().info(UtilitiesGeneral.joinStringsWithSpace(broadcast));
		}
	}
	public static void printDebugCitizensAll() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(DataCitizens data : DataManagerCitizens.mapDataCitizns.values()) {
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
		for(DataComment data : DataManagerComment.getMapDataComment().values()) {
			UtilitiesProgramming.printDebugComment(data);
		}
	}
	public static void printDebugPlayer(DataPlayer data) {
		Bukkit.getLogger().info("[Debug DataPlayer] " + data.name);
		Bukkit.getLogger().info(" LINE: " + data.line);
		Bukkit.getLogger().info(" SELECT: " + data.select);
	}
	public static void printDebugTalker(Talker talker) {
		if(talker.id < 0) return;
		Bukkit.getLogger().info("[Debug Talker] " + talker.name);
		Bukkit.getLogger().info(" ID: " + talker.id + ", EDITOR: " + talker.editor + ", STAGE: " + talker.nameStage);
		for(Description sentence : talker.listSentence) {
			Bukkit.getLogger().info(" SEN: " + sentence.loadEn());
			Bukkit.getLogger().info(" SEN: " + sentence.loadJp());
		}
		Bukkit.getLogger().info(" Q(EN): " + talker.question.getEn());
		Bukkit.getLogger().info(" Q(JP): " + talker.question.getJp());
		Bukkit.getLogger().info(" A(EN): " + UtilitiesGeneral.joinStrings(talker.answer.getEn(), ", "));
		Bukkit.getLogger().info(" A(JP): " + UtilitiesGeneral.joinStrings(talker.answer.getJp(), ", "));
		for(DataComment comment : talker.mapComment.values()) {
			UtilitiesProgramming.printDebugComment(comment);
		}
	}
	public static void printDebugCitizens(DataCitizens citizens) {
		Bukkit.getLogger().info("ID: " + citizens.id + ", NAME: " + citizens.name);
	}
	public static void printDebugComment(DataComment comment) {
		Bukkit.getLogger().info("[Debug Comment] " + " SENDER: " + comment.sender + ", STATE: " + comment.state);
		Bukkit.getLogger().info(" COMMENT: " + comment.expression);
	}
}
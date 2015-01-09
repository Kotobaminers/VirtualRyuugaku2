package com.gmail.fukushima.kai.utilities.utilities;

import org.bukkit.Bukkit;

import com.gmail.fukushima.kai.citizens.citizens.DataCitizens;
import com.gmail.fukushima.kai.citizens.citizens.DataManagerCitizens;
import com.gmail.fukushima.kai.comment.comment.DataComment;
import com.gmail.fukushima.kai.comment.comment.DataManagerComment;
import com.gmail.fukushima.kai.common.common.Sentence;
import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
import com.gmail.fukushima.kai.shadow.shadow.DataManagerShadowTopic;
import com.gmail.fukushima.kai.shadow.shadow.DataShadowTopic;
import com.gmail.fukushima.kai.stage.stage.DataManagerStage;
import com.gmail.fukushima.kai.stage.stage.Stage;
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
		for(DataPlayer data : DataManagerPlayer.mapDataPlayer.values()) {
			UtilitiesProgramming.printDebugPlayer(data);
		}
	}
	public static void printDebugStageAll() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(Stage data : DataManagerStage.mapStage.values()) {
			UtilitiesProgramming.printDebugStage(data);
		}
	}
	public static void printDebugShadowAll() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(DataShadowTopic data : DataManagerShadowTopic.mapDataShadowTopic.values()) {
			UtilitiesProgramming.printDebugShadowTopic(data);
		}
	}
	public static void printDebugTalkerAll() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(Talker data : DataManagerTalker.mapTalker.values()) {
			UtilitiesProgramming.printDebugTalker(data);
		}
	}
	public static void printDebugCommentAll() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(DataComment data : DataManagerComment.mapDataComment.values()) {
			UtilitiesProgramming.printDebugComment(data);
		}
	}
	public static void printDebugStage(Stage stage) {
		Bukkit.getLogger().info("[Debug Stage] " + stage.name);
		Bukkit.getLogger().info(" CREATOR: " + stage.creator);
		for(Integer id : stage.listId) {
			Talker talker = DataManagerTalker.getTalker(id);
			printDebugTalker(talker);
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
		Bukkit.getLogger().info(" ID: " + talker.id + " TYPE: " + talker.type.toString() + " OWNER: " + talker.owner);
		for(Sentence sentence : talker.listSentence) {
			Bukkit.getLogger().info(" SEN: " + sentence.loadEn());
			Bukkit.getLogger().info(" SEN: " + sentence.loadJp());
		}
		Bukkit.getLogger().info(" QUE: " + talker.question.loadEn());
		Bukkit.getLogger().info(" QUE: " + talker.question.loadJp());
		Bukkit.getLogger().info(" ANS: " + talker.answer.loadEn());
		Bukkit.getLogger().info(" ANS: " + talker.answer.loadJp());
	}
	public static void printDebugCitizens(DataCitizens citizens) {
		Bukkit.getLogger().info("ID: " + citizens.id + " Name: " + citizens.name);
	}
	public static void printDebugShadowTopic(DataShadowTopic shadow) {
		Bukkit.getLogger().info("[Debug ShadowTopic] " + shadow.nameTopic);
		String message = " CREATED: " + shadow.created;
		Bukkit.getLogger().info(message);
		for(Integer id : shadow.listId) {
			Talker talker = DataManagerTalker.getTalker(id);
			UtilitiesProgramming.printDebugTalker(talker);
		}
	}
	public static void printDebugComment(DataComment comment) {
		Bukkit.getLogger().info("[Debug Comment] OWNER: " + comment.owner + " ID: " + comment.id.toString() + " SENDER: " + comment.sender + " STATE: " + comment.state);
		Bukkit.getLogger().info(" COMMENT: " + comment.comment);
	}
}
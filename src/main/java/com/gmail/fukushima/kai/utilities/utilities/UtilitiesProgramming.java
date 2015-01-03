package com.gmail.fukushima.kai.utilities.utilities;

import org.bukkit.Bukkit;

import com.gmail.fukushima.kai.citizens.citizens.DataCitizens;
import com.gmail.fukushima.kai.citizens.citizens.DataManagerCitizens;
import com.gmail.fukushima.kai.common.common.Sentence;
import com.gmail.fukushima.kai.mystage.mystage.DataManagerStage;
import com.gmail.fukushima.kai.mystage.mystage.Stage;
import com.gmail.fukushima.kai.mystage.talker.DataManagerTalker;
import com.gmail.fukushima.kai.mystage.talker.Talker;
import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
import com.gmail.fukushima.kai.shadow.shadow.DataManagerShadowTopic;
import com.gmail.fukushima.kai.shadow.shadow.DataShadowTopic;
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
			System.out.println(UtilitiesGeneral.joinStringsWithSpace(broadcast));
		}
	}
	public static void printDebugCitizensAll() {
		for(DataCitizens data : DataManagerCitizens.mapDataCitizns.values()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			UtilitiesProgramming.printDebugCitizens(data);
		}
	}
	public static void printDebugPlayerAll() {
		for(DataPlayer data : DataManagerPlayer.mapDataPlayer.values()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			UtilitiesProgramming.printDebugPlayer(data);
		}
	}
	public static void printDebugStageAll() {
		for(Stage data : DataManagerStage.mapStage.values()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			UtilitiesProgramming.printDebugStage(data);
		}
	}
	public static void printDebugShadowAll() {
		for(DataShadowTopic data : DataManagerShadowTopic.mapDataShadowTopic.values()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			UtilitiesProgramming.printDebugShadowTopic(data);
		}
	}
	public static void printDebugTalkerAll() {
		for(Talker data : DataManagerTalker.mapTalker.values()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			UtilitiesProgramming.printDebugTalker(data);
		}
	}
	public static void printDebugStage(Stage stage) {
		System.out.println("[Debug Stage] " + stage.name);
		System.out.println(" CREATOR: " + stage.creator);
		for(Integer id : stage.listId) {
			Talker talker = DataManagerTalker.getTalker(id);
			printDebugTalker(talker);
		}
	}
	public static void printDebugPlayer(DataPlayer data) {
		System.out.println("[Debug DataPlayer] " + data.name);
		System.out.println(" LINE: " + data.line);
		System.out.println(" SELECT: " + data.select);
	}
	public static void printDebugTalker(Talker talker) {
		if(talker.id < 0) return;
		System.out.println("[Debug Talker] " + talker.name);
		System.out.println(" ID: " + talker.id + " TYPE: " + talker.type.toString() + " OWNER: " + talker.owner);
		for(Sentence sentence : talker.listSentence) {
			System.out.println(" SEN: " + sentence.loadEn());
			System.out.println(" SEN: " + sentence.loadJp());
		}
		System.out.println(" QUE: " + talker.question.loadEn());
		System.out.println(" QUE: " + talker.question.loadJp());
		System.out.println(" ANS: " + talker.answer.loadEn());
		System.out.println(" ANS: " + talker.answer.loadJp());
	}
	public static void printDebugCitizens(DataCitizens citizens) {
		System.out.println("ID: " + citizens.id + " Name: " + citizens.name);
	}
	public static void printDebugShadowTopic(DataShadowTopic shadow) {
		System.out.println("[Debug ShadowTopic] " + shadow.nameTopic);
		String message = " CREATED: " + shadow.created;
		System.out.println(message);
		for(Integer id : shadow.listId) {
			Talker talker = DataManagerTalker.getTalker(id);
			UtilitiesProgramming.printDebugTalker(talker);
		}
	}
}
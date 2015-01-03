package com.gmail.fukushima.kai.utilities.utilities;

import org.bukkit.Bukkit;

import com.gmail.fukushima.kai.citizens.citizens.DataCitizens;
import com.gmail.fukushima.kai.common.common.Sentence;
import com.gmail.fukushima.kai.mystage.mystage.Stage;
import com.gmail.fukushima.kai.mystage.talker.Talker;
import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
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
	public static void printDebugStage(Stage stage) {
		if(!Settings.debugStage) return;
		System.out.println("[Debug Stage] " + stage.name);
		System.out.println(" Creator: " + stage.creator);
		for(Talker talker : stage.listTalker) {
			printDebugTalker(talker);
			for(Sentence sentence : talker.listSentence) {
				sentence.printDebug();
			}
		}
	}
	public static void printDebugMapDataPlayer() {
		if(!Settings.debugPlayer) return;
		for(DataPlayer data : DataManagerPlayer.mapDataPlayer.values()) {
			printDebugDataPlayer(data);
		}
	}
	public static void printDebugDataPlayer(DataPlayer data) {
		if(!Settings.debugPlayer) return;
		System.out.println("[Debug DataPlayer] " + data.name);
		System.out.println(" LINE: " + data.line);
		System.out.println(" SELECT: " + data.select);
	}
	public static void printDebugTalker(Talker talker) {
		System.out.println("[Debug Talker]" + talker.name);
		System.out.println(" ID: " + talker.id);
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
		if(!Settings.debugCitizens) return;
		System.out.println("ID: " + citizens.id + " Name: " + citizens.name);
	}
}
package com.gmail.fukushima.kai.common.common;

import com.gmail.fukushima.kai.mystage.mystage.Stage;
import com.gmail.fukushima.kai.mystage.talker.Talker;
import com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2.Settings;


public class UtilitiesProgramming {
	public static void printDebugMessage(String message, Exception exception) {
		if(Settings.debugMessage) {
			StackTraceElement element = exception.getStackTrace()[0];
			String nameClass = element.getClassName();
			String[] splited = nameClass.split("\\.");
			String nameMethod = element.getMethodName();
			String line = String.valueOf(element.getLineNumber());
			String[] broadcast = {message, splited[splited.length-1], nameMethod, line};
			System.out.println(UtilitiesGeneral.joinStringsWithSpace(broadcast));
		}
	}
	public static void printDebugStage(Stage stage) {
		if(Settings.debugStage) {
			System.out.println("[Debug Stage] " + stage.name);
			System.out.println(" Creator: " + stage.creator);
			for(Talker talker : stage.listTalker) {
				talker.printDebug();
				for(Sentence sentence : talker.listSentence) {
					sentence.printDebug();
				}
			}
		}
	}
}
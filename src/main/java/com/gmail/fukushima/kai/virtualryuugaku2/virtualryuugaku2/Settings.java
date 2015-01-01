package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

public final class Settings {
	public static boolean debugMessage = true;
	public static boolean debugStage = true;
	public static boolean debugPlayer = true;
	public static boolean protectionCommandOP = true;
	public static boolean debugMessageBroadcast = false;

	public static boolean toggleDebug() {
		return !debugMessage;
	}
}
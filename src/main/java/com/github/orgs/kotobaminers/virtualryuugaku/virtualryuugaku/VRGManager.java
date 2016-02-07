package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Romaji;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceYamlConverter;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public final class VRGManager {
	public static VirtualRyuugaku plugin;
	public static void loadPlugin() {
		Debug.printDebugMessage("[VirtualRyuugaku] Loading Library Data", new Exception());
		new Romaji().load();

		Debug.printDebugMessage("[VirtualRyuugaku] Loading Player Data", new Exception());
		PlayerDataStorage.initialize();

		SentenceStorage.initialize();
		SentenceYamlConverter.importOwnerSentences();
		SentenceYamlConverter.importPlayerSentences();
	}

	public static void savePlugin() {
		Debug.printDebugMessage("[VRG] Saving Virtual Ryuugaku...", new Exception());
		SentenceYamlConverter.save();
	}
}
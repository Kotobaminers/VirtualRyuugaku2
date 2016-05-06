package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import org.bukkit.Bukkit;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Romaji;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HologramStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.OnlinePlayerNPCs;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.UnitStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.UnitYamlConverter;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public final class VRGManager {
	public static VirtualRyuugaku plugin;
	public static void loadPlugin() {
		Debug.printDebugMessage("[VirtualRyuugaku] Loading Library Data", new Exception());
		Romaji.load();
		PlayerDataStorage.initialize();

		UnitStorage.initialize();
		UnitYamlConverter.importAll();
		OnlinePlayerNPCs.initialize();

		Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				HologramStorage.initialize();
			}
		}, 20L);
}

	public static void savePlugin() {
		Debug.printDebugMessage("[VRG] Saving Virtual Ryuugaku...", new Exception());
		UnitYamlConverter.save();
	}
}
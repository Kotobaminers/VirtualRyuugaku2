package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fukushima.kai.common.common.UtilitiesProgramming;

public class VirtualRyuugaku2 extends JavaPlugin {
	@Override
	public void onEnable() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataManagerPlugin.initializeLoader(this);
		DataManagerPlugin.loadPlugin();

		getServer().getPluginManager().registerEvents(new Events(), this);

	}
	@Override
	public void onDisable() {
	}
}
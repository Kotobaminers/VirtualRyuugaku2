package com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2;

import java.nio.charset.Charset;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;
import com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2.CommandExecutorPlugin.Commands;

public class VirtualRyuugaku2 extends JavaPlugin {
	@Override
	public void onEnable() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataManagerPlugin.initializeLoader(this);

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Plugin Data", new Exception());
		DataManagerPlugin.loadPlugin();

		UtilitiesProgramming.printDebugTalkerAll();
		UtilitiesProgramming.printDebugStageAll();

		getServer().getPluginManager().registerEvents(new Events(), this);
		for(Commands command : Commands.values()) {
			this.getCommand(command.toString()).setExecutor(new CommandExecutorPlugin(this));
		}
		System.out.println("My Default Charset: " + Charset.defaultCharset());

		UtilitiesProgramming.printDebugTalkerAll();

		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataManagerPlugin.savePlugin();
	}
	@Override
	public void onDisable() {
		DataManagerPlugin.savePlugin();
	}
}
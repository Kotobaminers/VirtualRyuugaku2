package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Commands;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class VirtualRyuugaku extends JavaPlugin {
	@Override
	public void onEnable() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataManagerPlugin.initializeLoader(this);

		UtilitiesProgramming.printDebugMessage("[VirtualRyuugaku] Loading Plugin Data", new Exception());
		DataManagerPlugin.loadPlugin();

		getServer().getPluginManager().registerEvents(new Events(), this);
		for(Commands command : Commands.values()) {
			this.getCommand(command.toString()).setExecutor(new CommandExecutorPlugin(this));
		}

		UtilitiesProgramming.printDebugMessage("", new Exception());

//		DataManagerPlugin.savePlugin();
	}

	@Override
	public void onDisable() {
//		DataManagerPlugin.savePlugin();
	}
}
package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Commands;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public class VirtualRyuugaku extends JavaPlugin {
	@Override
	public void onEnable() {
		VRGManager.plugin = this;

		Debug.printDebugMessage("[VirtualRyuugaku] Loading Plugin Data", new Exception());
		VRGManager.loadPlugin();

		getServer().getPluginManager().registerEvents(new Events(), this);

		for(Commands command : Commands.getRoot()) {
			this.getCommand(command.toString()).setExecutor(new CommandExecutorPlugin(this));
		}
	}

	@Override
	public void onDisable() {
		VRGManager.savePlugin();
	}
}
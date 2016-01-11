package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGameController;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Commands;

public class VirtualRyuugaku extends JavaPlugin {
	@Override
	public void onEnable() {
		Debug.printDebugMessage("", new Exception());
		VirtualRyuugakuManager.plugin = this;

		Debug.printDebugMessage("[VirtualRyuugaku] Loading Plugin Data", new Exception());
		VirtualRyuugakuManager.loadPlugin();

		Debug.printDebugMessage("", new Exception());
		getServer().getPluginManager().registerEvents(new Events(), this);

		Debug.printDebugMessage("", new Exception());
		for(Commands command : Commands.getRoot()) {
			this.getCommand(command.toString()).setExecutor(new CommandExecutorPlugin(this));
		}

		Debug.printDebugMessage("", new Exception());
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(this, new PublicGameController(this), 80L);

//		DataManagerPlugin.savePlugin();

	}

	@Override
	public void onDisable() {
		VirtualRyuugakuManager.savePlugin();
	}
}
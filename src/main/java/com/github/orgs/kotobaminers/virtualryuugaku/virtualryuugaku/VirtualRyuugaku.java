package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Commands;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HologramStorage;
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

		Bukkit.getServer().getScheduler().runTaskLater(this, new Runnable() {
			@Override
			public void run() {
				HologramStorage.initialize();
					}
		}, 20L);


//		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
//		scheduler.scheduleSyncDelayedTask(this, new PublicGameController(this), 80L);
	}

	@Override
	public void onDisable() {
		VRGManager.savePlugin();
	}
}
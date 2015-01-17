package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ConfigHandler implements InitializerWithPlugin {
	public void save() {
		try {
			getConfig().save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public abstract File getFile();
	public abstract YamlConfiguration getConfig();
	@Override
	public void initialize(JavaPlugin plugin) {
		String path = plugin.getDataFolder() + "//" + getDirectory() + "//" + getFileName();
		setFile(new File(path));
		setConfig(YamlConfiguration.loadConfiguration(getFile()));
		UtilitiesProgramming.printDebugMessage(getFile().getAbsolutePath() + getFile().exists(), new Exception());
	}
	public abstract void setFile(File file);
	public abstract void setConfig(YamlConfiguration config);
	public abstract String getFileName();
	public abstract String getDirectory();
}
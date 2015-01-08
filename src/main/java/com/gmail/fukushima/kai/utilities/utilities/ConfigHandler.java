package com.gmail.fukushima.kai.utilities.utilities;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

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
}
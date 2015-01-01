package com.gmail.fukushima.kai.utilities.utilities;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.fukushima.kai.common.common.InitializerWithPlugin;

public abstract class ConfigHandler  implements InitializerWithPlugin {
	public static FileConfiguration config;
	public static File file;

	public static void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
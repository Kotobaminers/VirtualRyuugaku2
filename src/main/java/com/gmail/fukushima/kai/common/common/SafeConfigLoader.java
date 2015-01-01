package com.gmail.fukushima.kai.common.common;

import org.bukkit.configuration.file.YamlConfiguration;

public abstract class SafeConfigLoader extends YamlConfiguration {
	public Integer loadInt(String path, Integer initial) {
		if(isInt(path)) return getInt(path);
		return initial;
	}
	public String loadString(String path, String initial) {
		if(isInt(path)) return getString(path);
		return initial;
	}
}
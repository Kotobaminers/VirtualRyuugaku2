package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import org.bukkit.configuration.file.YamlConfiguration;


public interface YamlController {
	public void importConfiguration();
	public void setConfig();
	public YamlConfiguration getConfig();
}
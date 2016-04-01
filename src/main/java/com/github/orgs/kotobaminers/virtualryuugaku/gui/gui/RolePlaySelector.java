package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

public class RolePlaySelector extends OnlinePlayerSelector {
	static final String TITLE = "Role Play Selector";

	@Override
	public void executeClickedEvent(InventoryClickEvent event) {
	}

	@Override
	public String getTitle() {
		return TITLE;
	}
}

package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import org.bukkit.entity.Player;

public abstract class Sentence {
	public abstract void print(Player player);
	public abstract void playEffect(Player player);
	protected Integer id = 0;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
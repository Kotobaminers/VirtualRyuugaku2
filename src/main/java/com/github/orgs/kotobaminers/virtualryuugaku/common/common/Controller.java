package com.github.orgs.kotobaminers.virtualryuugaku.common.common;


public abstract class Controller {

	public void initializeStorage() {
		setStorage();
		getStorage().initialize();
	}

	public void loadStorage() {
		getStorage().load();
	}

	public void saveStorage() {
		getStorage().save();
	}

	public abstract void setStorage();
	public abstract Storage getStorage();
}
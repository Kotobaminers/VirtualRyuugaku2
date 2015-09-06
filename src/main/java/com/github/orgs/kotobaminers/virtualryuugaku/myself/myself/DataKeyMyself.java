package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.DataKey;

public class DataKeyMyself implements DataKey {
	public String owner = "";
	public String stage = "";

	public DataKeyMyself(String owner, String stage) {
		this.owner = owner;
		this.stage = stage;
	}

	public boolean equals(Object object) {
		if(object instanceof DataKeyMyself) {
			DataKeyMyself key = (DataKeyMyself) object;
			if(key.owner.equalsIgnoreCase(owner) && key.stage.equalsIgnoreCase(stage)) {
				return true;
			}
		}
		return false;
	}

	public int hashCode() {
		return owner.hashCode() + stage.hashCode();
	}
}
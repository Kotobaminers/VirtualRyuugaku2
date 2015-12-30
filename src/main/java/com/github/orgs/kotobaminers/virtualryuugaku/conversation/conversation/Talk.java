package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;

public class Talk {
	public Integer id = 0;
	public String name = "";
	public Description description;
	public boolean key = false;

	public Talk create(Integer id, String name, Description description) {
		Talk talk = new Talk();
		talk.id = id;
		talk.name = name;
		talk.description = description;
		return talk;
	}
}
package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;



public class NPCSentence {
	public Integer id = 0;
	public Description description;
	public boolean key = false;

	public NPCSentence create(Integer id, Description description) {
		NPCSentence talk = new NPCSentence();
		talk.id = id;
		talk.description = description;
		return talk;
	}


}
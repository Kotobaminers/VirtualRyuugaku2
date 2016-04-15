package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;



public class VRGSentence {
	public Integer id = 0;
	public Description description;
	public boolean key = false;

	public static VRGSentence create(Integer id, Description description) {
		VRGSentence talk = new VRGSentence();
		talk.id = id;
		talk.description = description;
		return talk;
	}


}
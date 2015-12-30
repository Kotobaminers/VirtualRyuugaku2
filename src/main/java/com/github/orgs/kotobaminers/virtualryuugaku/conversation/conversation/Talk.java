package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;

public class Talk {
	public Integer id = 0;
	public String name = "";
	public Description description;
	public boolean key = false;
	public Map<String, String> mapComment = new HashMap<String, String>();

	public Talk create(Integer id, String name, Description description) {
		Talk talk = new Talk();
		talk.id = id;
		talk.name = name;
		talk.description = description;
		return talk;
	}











	public String getDebugMessage() {
		String message = "ID: " + id.toString() + ", " + description.getEnglishJoined() + "/" + description.getKanjiJoined() + "/" + description.getKanaJoined() + "/" + description.getRomajiJoined();
		return message;
	}








	public List<String> getCorrectors() {
		List<String> correctors = new ArrayList<String>();
		for (String corrector : mapComment.keySet()) {
			correctors.add(corrector);
		}
		return correctors;
	}

}
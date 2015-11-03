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
//	public void print(Player player, Conversation conversation) {
//		List<Expression> expressions = DataManagerPlayer.getDataPlayer(player).expressions;
//		if(0 < expressions.size()) {
//			String opts[] = {"" + conversation.getTalkerColor(id), name};
//			Message.TALK_TITLE_2.print(player, opts);
//		}
//
//
//		if(expressions.contains(Expression.EN)) {
//			String[] opts = {"" + conversation.getTalkerColor(id) + ChatColor.BOLD + name + ChatColor.RESET, };
//			if(key) {
//				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.DESCRIPTION_KEY_2, opts));
//			} else {
//				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.DESCRIPTION_2, opts));
//			}
//		}
//		List<String> listJapanese = new ArrayList<String>();
//		if(expressions.contains(Expression.KANJI)) {
//			listJapanese.add(description.getKanjiJoined());
//		}
//		if(expressions.contains(Expression.KANA)) {
//			listJapanese.add(description.getKanaJoined());
//		}
//		if(expressions.contains(Expression.ROMAJI)) {
//			listJapanese.add(description.getRomajiJoined());
//		}
//		String japanese = UtilitiesGeneral.joinStrings(listJapanese, ", ");
//		if(0 < japanese.length()) {
//			String[] opts = {name, description.getJapaneseJoined(player)};
//			if(key) {
//				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.DESCRIPTION_KEY_2, opts));
//			} else {
//				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.DESCRIPTION_2, opts));
//			}
//		}
//	}

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
package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message0;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Expression;

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
	public void print(Player player) {
		List<Expression> expressions = DataManagerPlayer.getDataPlayer(player).expressions;
		if(0 < expressions.size()) {
			MessengerGeneral.print(player, MessengerGeneral.getPartitionGreen());
		}
		if(expressions.contains(Expression.EN)) {
			String[] opts = {name, description.getEnglishJoined()};
			if(key) {
				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.DESCRIPTION_KEY_2, opts));
			} else {
				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.DESCRIPTION_2, opts));
			}
		}
		List<String> listJapanese = new ArrayList<String>();
		if(expressions.contains(Expression.KANJI)) {
			listJapanese.add(description.getKanjiJoined());
		}
		if(expressions.contains(Expression.KANA)) {
			listJapanese.add(description.getKanaJoined());
		}
		if(expressions.contains(Expression.ROMAJI)) {
			listJapanese.add(description.getRomajiJoined());
		}
		String japanese = UtilitiesGeneral.joinStrings(listJapanese, ", ");
		if(0 < japanese.length()) {
			String[] opts = {name, description.getJapaneseJoined(player)};
			if(key) {
				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.DESCRIPTION_KEY_2, opts));
			} else {
				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.DESCRIPTION_2, opts));
			}
		}
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
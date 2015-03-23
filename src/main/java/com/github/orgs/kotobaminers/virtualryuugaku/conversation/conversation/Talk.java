package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;

public class Talk {
	public Integer id;
	public String name;
	public Description description;

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
			MessengerGeneral.print(player, MessengerGeneral.getPartitionDefault());
		}
		if(expressions.contains(Expression.EN)) {
			String[] opts = {name, description.getEnglishJoined()};
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.DESCRIPTION_EN_2, opts));
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
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.DESCRIPTION_JP_2, opts));
		}
	}

}

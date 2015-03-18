package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

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

	public void printExpression(Player player, DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String expression = description.express(data.expression);
		if(0 < expression.length()) {
			String[] opts = {name, expression};
			MessengerGeneral.print(player, Message.CONVERSATION_SPEAK_2, opts);
		} else {
			int line = data.line.intValue();
			String[] opts = {String.valueOf(line + 1), data.expression.toString()};
			MessengerGeneral.print(player, Message.NO_EXPRESSION_LINE_LANG_2, opts);
		}
	}
}

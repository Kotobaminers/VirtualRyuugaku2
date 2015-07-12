package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;


public class GameFindPeopleGlobal extends GameGlobal{

	@Override
	public void giveNextQuestion() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		addCount();
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			Talk talk = getCurrentKeyTalk();
			String message = talk.description.getSentenceLearning(player);
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.FIND_PEOPLE_MISSION_0, null));
			String[] opts = {message};
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.FIND_PEOPLE_QUEST_1, opts));
		}
	}

	@Override
	public void finishGame() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		// TODO Auto-generated method stub
	}

	@Override
	public void validateAnswer(String[] args) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		// TODO Auto-generated method stub
	}

	@Override
	public GameFindPeopleGlobal loadGame(String name) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		GameFindPeopleGlobal game = new GameFindPeopleGlobal();

		for(Conversation conversation : DataManagerConversation.getMapConversation().values()) {
			if (conversation.stage.equalsIgnoreCase(name)) {
				keys.addAll(conversation.getKeyTalk());
			}
		}
		Collections.shuffle(keys);
		return game;
	}
}
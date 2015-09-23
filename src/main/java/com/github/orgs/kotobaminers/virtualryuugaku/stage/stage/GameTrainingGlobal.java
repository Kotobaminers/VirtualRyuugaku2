package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMulti;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;


public class GameTrainingGlobal extends GameGlobal{

	@Override
	public void giveNextQuestion() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		addCount();
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			String message = getCurrentQuestionsPlayer(player);
			String[] opts = {message, getOppositeLanguage().toString()};
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.STAGE_QUESTION_2, opts));
		}
	}

	@Override
	public void finishGame() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		printResult();
		initializeGameGlobal();
	}

	@Override
	public void loadGame(String name) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		initializeGameGlobal();

		for(ConversationMulti conversation : DataManagerConversation.getMapConversation().values()) {
			if (conversation.stage.equalsIgnoreCase(name)) {
				talks.addAll(conversation.getKeyTalk());
			}
		}
		Collections.shuffle(talks);

		setLanguageRandom();
	}

	@Override
	public void updateScoreboard(Player player) {
		new GameScoreboardTrainingGlobal().update(player);
	}


	@Override
	public void printCorrect(Player player) {
		String[] opts = {UtilitiesGeneral.joinStrings(getCurrentAnswers(), ", ")};
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.STAGE_CORRECT_1, opts));
	}
}
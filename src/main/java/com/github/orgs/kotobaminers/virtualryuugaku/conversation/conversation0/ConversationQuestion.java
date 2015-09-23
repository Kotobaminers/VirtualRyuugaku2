package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation0;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.FireworkEffect.Type;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.FireworkUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.FireworkUtility.FireworkColor;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;


public class ConversationQuestion {
	private String question = "";
	private List<String> answers = new ArrayList<String>();
	private List<Integer> key = new ArrayList<Integer>();
	private String stage = "";

	public static ConversationQuestion create(String q, List<String> a, List<Integer> key, String stage) {
		ConversationQuestion question = new ConversationQuestion();
		question.question = q;
		question.answers = a;
		question.key = key;
		question.stage = stage;
		return question;
	}

	public void giveQuestion(Player player, ConversationQuestion q) {
		MessengerGeneral.print(player, MessengerGeneral.getPartitionQuestion());
		String[] opts = {question};
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.CONVERSATION_QUESTION_1, opts));
		DataManagerPlayer.getDataPlayer(player).question = q;
	}

	public void validateQuestion(Player player, String answer) {
		for (String search : answers) {
			if (search.equalsIgnoreCase(answer)) {
				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.CORRECT_0, null));
				player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
				DataPlayer data = DataManagerPlayer.getDataPlayer(player);
				data.addQuestionDone(player, key);
				Integer question = DataManagerConversation.getNumberQuestion(stage);
				if (question <= data.questionDone.size()) {
					String[] opts = {stage};
					FireworkUtility.shootFirework(player.getWorld(), player.getLocation(), Type.BALL_LARGE, FireworkColor.GREEN, FireworkColor.AQUA, 0);
					MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.CONVERSATION_QUESTION_COMPLITE_1, opts));
				}
				return;
			}
		}
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.WRONG_0, null));
		player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
	}

	public String getQuestion() {
		return question;
	}
	public List<String> getAnswers() {
		return answers;
	}
	public List<Integer> getKey() {
		return key;
	}
}
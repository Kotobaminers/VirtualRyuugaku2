package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message0;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;


public class ConversationQuestion {
	private String question = "";
	private List<String> answers = new ArrayList<String>();
	private List<Integer> key = new ArrayList<Integer>();
	private String stageName = "";

	public static ConversationQuestion create(String q, List<String> a, List<Integer> key, String stage) {
		ConversationQuestion question = new ConversationQuestion();
		question.question = q;
		question.answers = a;
		question.key = key;
		question.stageName = stage;
		return question;
	}

	public void giveQuestion(Player player, ConversationQuestion q) {
		MessengerGeneral.print(player, MessengerGeneral.getPartitionPurple());
		String[] opts = {question};
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.CONVERSATION_QUESTION_1, opts));
		Effects.playSound(player, Scene.APPEAR);
		DataManagerPlayer.getDataPlayer(player).question = q;
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

	public void validateQuestion(Player player, String answer) {
//		for (String search : answers) {
//			if (search.equalsIgnoreCase(answer)) {
//				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.CORRECT_0, null));
//				Effects.playSound(player, Scene.GOOD);
//				DataPlayer data = DataManagerPlayer.getDataPlayer(player);
//				data.questionDone.add(key);
//
//				Stage stage;
//				try {
//					stage = Stage.createStage(stageName);
//					Integer max = stage.getQuestionMax();
//					Integer done = stage.getQuestionDone(data);
//					String[] opts = {stage.getQuestionDoneByMax(data)};
//					Message.STAGE_INFO_QUESTION_1.print(player, opts);
//					if (max <= done) {
//						String[] opts2 = {stageName};
//						Effects.shootFirework(player);
//						MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.CONVERSATION_QUESTION_COMPLITE_1, opts2));
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return;
//			}
//		}
//		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.WRONG_0, null));
//
//		List<String> hints = new ArrayList<String>();
//		for (String string : answers) {
//			hints.add(UtilitiesGeneral.showSameCharacters(string, answer));
//		}
//		String[] opts = {" [Hints] " + UtilitiesGeneral.joinStrings(hints, ", ")};
//		Message.COMMON_EMPTY_1.print(player, opts);
//
//		Effects.playSound(player, Scene.BAD);
	}


}
package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataManagerCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerCommandUsage;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerCommandUsage.Usage;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.comment.DataComment;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class Conversation {
	public String stage = "";
	public List<String> editor = new ArrayList<String>();
	public List<Talk> listTalk = new ArrayList<Talk>();
	private List<Integer> key = new ArrayList<Integer>();
	public Map<String, DataComment> mapComment = new HashMap<String, DataComment>();
	public ConversationQuestion question = new ConversationQuestion();
	public ConversationAnswer answer = new ConversationAnswer();

	public List<Integer> getOrder() {
		List<Integer> order = new ArrayList<Integer>();
		for(Talk talk : listTalk) {
			order.add(talk.id);
		}
		return order;
	}

	public void talkNext(Player player, DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(0 < listTalk.size()) {
			if(listTalk.size() - 1 < data.line) {
				data.line = 0;
			}
			Talk talk = listTalk.get(data.line);
			if(data.line.equals(0)) {
				String[] opts = {talk.name};
				MessengerGeneral.print(player, Message.CONVERSATION_SPEAK_START_1, opts);
			}
			talk.printExpression(player, data);
			data.line++;
			if(listTalk.size() - 1 < data.line) {
				MessengerGeneral.print(player, Message.CONVERSATION_SPEAK_FINISH_0, null);
			}
		} else {
			MessengerGeneral.print(player, Message.NO_SENTENCE_0, null);
			if(editor.contains(player.getName())) {
				MessengerCommandUsage.print(player, Usage.TALKER_SENTENCE_0, null);
			}
		}
	}
	public Boolean isEmpty() {
		if(0 < listTalk.size()) {
			return false;
		}
		return true;
	}
	public Boolean hasAnswerEn() {
		if(0 < answer.getEn().size()) return true;
		UtilitiesProgramming.printDebugMessage("No Answer in EN", new Exception());
		return false;
	}
	public Boolean hasAnswerJp() {
		if(0 < answer.getJp().size()) return true;
		UtilitiesProgramming.printDebugMessage("No Answer in JP", new Exception());
		return false;
	}
	public void printInformation(Player player) {
//		if(isEmpty()) return;
//		String[] opts = {name};
//		MessengerGeneral.print(player, Message.TALKER_INFO_LABEL_1, opts);
//		String editors = UtilitiesGeneral.joinStrings(editor, ", ");
//		String[] opts2 = {id.toString(), editors, stage};
//		MessengerGeneral.print(player, Message.TALKER_INFO_DATA_3, opts2);
//		Integer count = 0;
//		List<Expression> expressions = Arrays.asList(Expression.EN, Expression.KANJI, Expression.KANA);
//		for(Talk talk : listTalk) {
//			Description sentence = talk.description;
//			count++;
//			for(Expression expression : expressions) {
//				String[] opts3 = {count.toString(), expression.toString(), sentence.express(expression)};
//				MessengerGeneral.print(player, Message.TALKER_INFO_SENTENCE_3, opts3);
//			}
//		}
	}
	public Boolean canEdit(String playerName) {
		if(editor.contains(playerName)){
			return true;
		}
		return false;
	}
	public static Boolean isValidCitizensId(List<Integer> order) {
		for(Integer id : order) {
			if(!DataManagerCitizens.getMapDataCitizens().keySet().contains(id)) {
				return false;
			}
		}
		return true;
	}
	public boolean hasEditor() {
		UtilitiesProgramming.printDebugMessage("" + editor.size(), new Exception());
		if(0 < editor.size()) {
			return true;
		}
		return false;
	}
	public List<Integer> getKey() {
		return key;
	}
	public void setKey(List<Integer> key) {
		this.key = key;
	}
}
package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Effect;
import org.bukkit.Sound;
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
			talk.print(player);
		} else {
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.NO_SENTENCE_0, null));
			if(editor.contains(player.getName())) {
				MessengerCommandUsage.print(player, Usage.TALKER_SENTENCE_0, null);
			}
		}
	}
	public void talkEffect(Player player, NPC npc) {
		player.getWorld().playEffect(npc.getStoredLocation().add(0, 2, 0), Effect.SMOKE, 22);//data is 22(None direction value).
	}
	public void talkSound(Player player) {
		player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
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
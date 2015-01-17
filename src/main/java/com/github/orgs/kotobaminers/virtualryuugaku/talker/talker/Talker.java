package com.github.orgs.kotobaminers.virtualryuugaku.talker.talker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataManagerCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.comment.DataComment;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class Talker {
	public Integer id = -1;
	public String name = "";
	public String stage = "";
	public List<String> editor = new ArrayList<String>();
	public List<Description> listSentence = new ArrayList<Description>();
	public TalkerQuestion question = new TalkerQuestion();
	public TalkerAnswer answer = new TalkerAnswer();
	public Map<String, DataComment> mapComment = new HashMap<String, DataComment>();

	public void talkNext(Player player, DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(0 < listSentence.size()) {
			if(listSentence.size() - 1 < data.line) {
				data.line = 0;
			}
			Description sentence = listSentence.get(data.line);
			printExpression(player, sentence, data);
			data.line++;
		} else {
			MessengerGeneral.print(player, Message.NO_SENTENCE_0, null);
		}
	}
	private void printExpression(Player player, Description sentence, DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String expression = sentence.express(data.language);
		if(0 < expression.length()) {
			String[] opts = {name, expression};
			MessengerGeneral.print(player, Message.TALKER_SPEAK_2, opts);
		} else {
			int line = data.line.intValue();
			String[] opts = {String.valueOf(line + 1), data.language.toString()};
			MessengerGeneral.print(player, Message.NO_EXPRESSION_LINE_LANG_2, opts);
		}
	}

	public void quest(Player player, DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String message = "";
		switch(data.language) {
		case EN:
			message = question.getEn();
			break;
		case KANJI:
			message = question.getJp();
			break;
		case KANA:
		case NONE:
		case ROMAJI:
		default:
			break;
		}
		if(0 < message.length()) {
			String[] opts = {message};
			MessengerGeneral.print(player, Message.TALKER_QUESTION_1, opts);
		} else {
			String[] opts = {data.language.toString()};
			MessengerGeneral.print(player, Message.NO_QUESTION_LANG_1, opts);
		}
	}
	public Boolean isEmpty() {
		if(0 < name.length()){
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
		if(isEmpty()) return;
		player.sendMessage("[Talker] " + name);
		player.sendMessage(" ID: " + id + ", EDITOR: " + editor + ", STAGE: " + stage);
		Integer count = 0;
		for(Description sentence : listSentence) {
			count++;
			player.sendMessage(" SENT(" + count + ") EN: " + sentence.express(Expression.EN));
			player.sendMessage(" SENT(" + count + ") JP: " + sentence.express(Expression.KANJI));
		}
		player.sendMessage(" Q(EN): " + question.getEn());
		player.sendMessage(" Q(JP): " + question.getJp());
	}
	public Boolean canEdit(String playerName) {
		if(editor.contains(playerName)){
			return true;
		}
		return false;
	}
	public static Boolean isValidCitizensId(Integer id) {
		for(DataCitizens data : DataManagerCitizens.getMapDataCitizns().values()) {
			if(data.id.equals(id)) {
				return true;
			}
		}
		return false;
	}
	public boolean hasEditor() {
		UtilitiesProgramming.printDebugMessage("" + editor.size(), new Exception());
		if(0 < editor.size()) {
			return true;
		}
		return false;
	}
}
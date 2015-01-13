package com.gmail.fukushima.kai.talker.talker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.citizens.citizens.DataCitizens;
import com.gmail.fukushima.kai.citizens.citizens.DataManagerCitizens;
import com.gmail.fukushima.kai.common.common.Description;
import com.gmail.fukushima.kai.common.common.Messenger;
import com.gmail.fukushima.kai.common.common.Messenger.Message;
import com.gmail.fukushima.kai.player.player.DataPlayer;
import com.gmail.fukushima.kai.talker.comment.DataComment;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

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
			Messenger.print(player, Message.NO_SENTENCE_0, null);
		}
	}
	private void printExpression(Player player, Description sentence, DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String expression = "";
		switch (data.language) {
		case EN:
			expression = sentence.loadEn();
			break;
		case JP:
			expression = sentence.loadJp();
			break;
		default:
			break;
		}
		if(0 < expression.length()) {
			expression = name + ": " + expression;
			player.sendMessage(expression);
		} else {
			int line = data.line.intValue();
			String[] opts = {String.valueOf(line + 1), data.language.toString()};
			Messenger.print(player, Message.NO_EXPRESSION_LINE_LANG_2, opts);
		}
	}

	public void quest(Player player, DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String message = "";
		switch(data.language) {
		case EN:
			message = question.getEn();
			break;
		case JP:
			message = question.getJp();
			break;
		default:
			break;
		}
		if(0 < message.length()) {
			message = "[Question] " + message;
			player.sendMessage(message);
		} else {
			String[] opts = {data.language.toString()};
			Messenger.print(player, Message.NO_QUESTION_LANG_1, opts);
		}
	}
	public Boolean hasName() {
		if(0 < name.length()){
			return true;
		}
		return false;
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
		if(!hasName()) return;
		player.sendMessage("[Talker] " + name);
		player.sendMessage(" ID: " + id + ", EDITOR: " + editor + ", STAGE: " + stage);
		Integer count = 0;
		for(Description sentence : listSentence) {
			count++;
			player.sendMessage(" SENT(" + count + ") EN: " + sentence.loadEn());
			player.sendMessage(" SENT(" + count + ") JP: " + sentence.loadJp());
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
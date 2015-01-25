package com.github.orgs.kotobaminers.virtualryuugaku.talker.talker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataManagerCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerCommandUsage;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerCommandUsage.Usage;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.talker.comment.DataComment;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class Talker {
	public Integer id = -1;
	public String name = "";
	public String stage = "";
	public List<String> editor = new ArrayList<String>();
	public List<Description> listSentence = new ArrayList<Description>();
	private Integer key = 0;
	public Map<String, DataComment> mapComment = new HashMap<String, DataComment>();
	public TalkerQuestion question = new TalkerQuestion();
	public TalkerAnswer answer = new TalkerAnswer();

	public void talkNext(Player player, DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(0 < listSentence.size()) {
			if(listSentence.size() - 1 < data.line) {
				data.line = 0;
			}
			if(data.line.equals(0)) {
				String[] opts = {name};
				MessengerGeneral.print(player, Message.TALKER_SPEAK_START_1, opts);
			}
			Description sentence = listSentence.get(data.line);
			printExpression(player, sentence, data);
			data.line++;
			if(listSentence.size() - 1 < data.line) {
				MessengerGeneral.print(player, Message.TALKER_SPEAK_FINISH_0, null);
			}
		} else {
			MessengerGeneral.print(player, Message.NO_SENTENCE_0, null);
			if(editor.contains(player.getName())) {
				MessengerCommandUsage.print(player, Usage.TALKER_SENTENCE_0, null);
			}
		}
	}
	private void printExpression(Player player, Description sentence, DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String expression = sentence.express(data.language);
		if(0 < expression.length()) {
			String[] opts = {expression};
			MessengerGeneral.print(player, Message.TALKER_SPEAK_1, opts);
		} else {
			int line = data.line.intValue();
			String[] opts = {String.valueOf(line + 1), data.language.toString()};
			MessengerGeneral.print(player, Message.NO_EXPRESSION_LINE_LANG_2, opts);
		}
	}

	public Boolean isEmpty() {
		if(0 < name.length()) {
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
		String[] opts = {name};
		MessengerGeneral.print(player, Message.TALKER_INFO_LABEL_1, opts);
		String editors = UtilitiesGeneral.joinStrings(editor, ", ");
		String[] opts2 = {id.toString(), editors, stage};
		MessengerGeneral.print(player, Message.TALKER_INFO_DATA_3, opts2);
		Integer count = 0;
		List<Expression> expressions = Arrays.asList(Expression.EN, Expression.KANJI, Expression.KANA);
		for(Description sentence : listSentence) {
			count++;
			for(Expression expression : expressions) {
				String[] opts3 = {count.toString(), expression.toString(), sentence.express(expression)};
				MessengerGeneral.print(player, Message.TALKER_INFO_SENTENCE_3, opts3);
			}
		}
	}
	public Boolean canEdit(String playerName) {
		if(editor.contains(playerName)){
			return true;
		}
		return false;
	}
	public static Boolean isValidCitizensId(Integer id) {
		for(DataCitizens data : DataManagerCitizens.getMapDataCitizens().values()) {
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
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public Description getKeyDescription() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Description description = new Description();
		Integer key = getKey() - 1;
		UtilitiesProgramming.printDebugMessage(key.toString() + " " + listSentence.size(), new Exception());
		if(key < listSentence.size()) {
			description = listSentence.get(key);
		}
		return description;
	}
}
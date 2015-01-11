package com.gmail.fukushima.kai.talker.talker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.comment.comment.DataComment;
import com.gmail.fukushima.kai.common.common.Description;
import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesGeneral;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class Talker {
	public Integer id = -1;
	public String name = "";
	public String nameStage = "";
	public List<String> editor = new ArrayList<String>();
	public List<Description> listSentence = new ArrayList<Description>();
	public TalkerQuestion question = new TalkerQuestion();
	public TalkerAnswer answer = new TalkerAnswer();
	public Map<String, DataComment> mapComment = new HashMap<String, DataComment>();
	public void talkNext(Player player, DataPlayer data) {
		Integer line = data.line;
		if(listSentence.size() - 1 < line) {
			line = 0;
		} else {
			Description sentence = listSentence.get(line);
			switch(data.language) {
			case EN:
				printMessageEn(player, sentence);
				break;
			case JP:
				printMessageJp(player, sentence);
				break;
			default:
				break;
			}
			if(listSentence.size() - 1 <= line) {
				line = 0;
			} else {
				line++;
			}
		}
		data.line = line;
		DataManagerPlayer.putDataPlayer(data);
	}
	public void quest(Player player, DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(0 < question.getEn().length() || 0 < question.getJp().length()) {
			switch(data.language) {
			case EN:
				printQuestionEn(player);
				break;
			case JP:
				printQuestionJp(player);
				break;
			default:
				break;
			}
		} else {
			player.sendMessage("No Question.");
		}
	}
	private void printMessageEn(Player player, Description sentence) {
		String message = name + ": " + sentence.loadEn();
		player.sendMessage(message);
	}
	public Boolean isValid() {
		if(0 < name.length()){
			return true;
		}
		return false;
	}
	public Boolean isYours(String playerName) {
		if(editor.equals(playerName)){
			return true;
		}
		return false;
	}
	private void printMessageJp(Player player, Description sentence) {
		String message = name + ": " + sentence.loadJp();
		player.sendMessage(message);
	}
	private void printQuestionEn(Player player) {
		String message = "[Question] " + question.getEn();
		player.sendMessage(message);
	}
	private void printQuestionJp(Player player) {
		String message = "[Question] " + question.getJp();
		player.sendMessage(message);
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
	public Boolean hasSentence() {
		if(0 < listSentence.size()) {
			return true;
		}
		UtilitiesProgramming.printDebugMessage("No Sentence", new Exception());
		return false;
	}
	public void printInformation(Player player) {
		if(!isValid()) return;
		player.sendMessage("[Talker] " + name);
		player.sendMessage(" ID: " + id + " OWNER: " + editor);
		Integer count = 0;
		for(Description sentence : listSentence) {
			count++;
			player.sendMessage(" SENT(" + count + ") EN: " + sentence.loadEn());
			player.sendMessage(" SENT(" + count + ") JP: " + sentence.loadJp());
		}
		player.sendMessage(" Q(EN): " + question.getEn());
		player.sendMessage(" Q(JP): " + question.getJp());
		player.sendMessage(" A(EN): " + UtilitiesGeneral.joinStrings(answer.getEn(), ", "));
		player.sendMessage(" A(JP): " + UtilitiesGeneral.joinStrings(answer.getJp(), ", "));
	}
	public static boolean isTalker(NPC npc) {
		if(DataManagerTalker.getMapTalker().containsKey(npc.getId())) {
			return true;
		}
		return false;
	}
}
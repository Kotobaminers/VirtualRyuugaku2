package com.gmail.fukushima.kai.talker.talker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.common.common.Description.Path;
import com.gmail.fukushima.kai.common.common.Sentence;
import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
import com.gmail.fukushima.kai.utilities.utilities.ConfigHandler;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class Talker {
	public String name = "";
	public String owner = "";
	public Integer id = -1;
	public List<Sentence> listSentence = new ArrayList<Sentence>();
	public Sentence question = new Sentence();
	public Sentence answer = new Sentence();
	public TypeTalker type;
	public enum TypeTalker {NONE, STAGE, SHADOW;
		public static TypeTalker lookup(String name) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			TypeTalker type = TypeTalker.NONE;
			try {
				type = TypeTalker.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
			}
			return type;
		}
	}
	public void talkNext(Player player, DataPlayer data) {
		Integer line = data.line;
		if(listSentence.size() - 1 < line) {
			line = 0;
		} else {
			Sentence sentence = listSentence.get(line);
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
		if(0 < question.en.size() || 0 < question.kanji.size() || 0 < question.kana.size()) {
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
	private void printMessageEn(Player player, Sentence sentence) {
		String message = name + ": " + sentence.loadEn();
		player.sendMessage(message);
	}
	public void saveAtConfig(ConfigHandler configHandler, String pathBase) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String path = pathBase + "." + this.id.toString();
		configHandler.getConfig().set(path + "." + "NAME", name);
		configHandler.getConfig().set(path + "." + "OWNER", owner);
		List<String> listEn = new ArrayList<String>();
		List<String> listKanji = new ArrayList<String>();
		List<String> listKana = new ArrayList<String>();
		for(Sentence sentence : listSentence) {
			listEn.add(findSentenceFirst(sentence.en));
			listKanji.add(findSentenceFirst(sentence.kanji));
			listKana.add(findSentenceFirst(sentence.kana));
		}
		configHandler.getConfig().set(path + "." + "ENGL", listEn);
		configHandler.getConfig().set(path + "." + "KANJ", listKanji);
		configHandler.getConfig().set(path + "." + "KANA", listKana);
		configHandler.save();
	}
	public static Talker importTalker(ConfigHandler configHandler, String path2, Integer id, TypeTalker type) {
		String path = path2 + "." + id.toString();
		String name = configHandler.getConfig().getString(path + "." + "NAME");
		String owner = configHandler.getConfig().getString(path + "." + "OWNER");
		List<String> listEn = configHandler.getConfig().getStringList(path + "." + Path.ENGL.toString());
		List<String> listKanji = configHandler.getConfig().getStringList(path + "." + Path.KANJ.toString());
		List<String> listKana = configHandler.getConfig().getStringList(path + "." + Path.KANA.toString());
		List<Sentence> listSentence = new ArrayList<Sentence>();
		for(Integer i = 0; i < listEn.size(); i++) {
			List<String> tempEn = Arrays.asList(listEn.get(i));
			List<String> tempKanji = Arrays.asList(listKanji.get(i));
			List<String> tempKana = Arrays.asList(listKana.get(i));
			List<String> tempHint = Arrays.asList("");
			listSentence.add(new Sentence(tempKanji, tempKana, tempEn, tempHint));
		}
		Talker talker = new Talker();
		talker.id = id;
		talker.name = name;
		talker.owner = owner;
		talker.type = type;
		talker.listSentence = listSentence;
		return talker;
	}
	private String findSentenceFirst(List<String> list) {
		String sentence = "No Sentence.";
		if(0 < list.size()) {
			sentence = list.get(0);
		}
		return sentence;
	}
	public Boolean isValid() {
		if(0 < name.length()){
			return true;
		}
		return false;
	}
	public Boolean isYours(String playerName) {
		if(owner.equals(playerName)){
			return true;
		}
		return false;
	}

	private void printMessageJp(Player player, Sentence sentence) {
		String message = name + ": " + sentence.loadJp();
		player.sendMessage(message);
	}
	private void printQuestionEn(Player player) {
		String message = "[Question] " + question.loadEn();
		player.sendMessage(message);
	}
	private void printQuestionJp(Player player) {
		String message = "[Question] " + question.loadJp();
		player.sendMessage(message);
	}
	public Boolean hasAnswerEn() {
		if(0 < answer.en.size()) return true;
		UtilitiesProgramming.printDebugMessage("No Answer in EN", new Exception());
		return false;
	}
	public Boolean hasAnswerJp() {
		if(0 < answer.kanji.size() && 0 < answer.kana.size()) return true;
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
		player.sendMessage(" ID: " + id + " TYPE: " + type.toString() + " OWNER: " + owner);
		Integer count = 0;
		for(Sentence sentence : listSentence) {
			count++;
			player.sendMessage(" SENT(" + count + ") EN: " + sentence.loadEn());
			player.sendMessage(" SENT(" + count + ") JP: " + sentence.loadJp());
		}
		player.sendMessage(" QUES EN: " + question.loadEn());
		player.sendMessage(" QUES JP: " + question.loadJp());
		player.sendMessage(" ANSW EN: " + answer.loadEn());
		player.sendMessage(" ANSW JP: " + answer.loadJp());
	}
}
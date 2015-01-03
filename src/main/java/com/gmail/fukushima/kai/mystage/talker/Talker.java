package com.gmail.fukushima.kai.mystage.talker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.common.common.Sentence;
import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class Talker {
	public String name;
	public String owner;
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
	public void saveAtConfig(FileConfiguration config, String path) {//TODO
//		Integer count = 0;
//		for(Sentence sentence : listSentence) {
//			Map<>
//			config.set(path, value);
//		}
//		} else if(num.equals(0)) {
//			talker.question = sentence;
//		} else if(num < 0) {
//			talker.answer = sentence;
//		}
	}
}
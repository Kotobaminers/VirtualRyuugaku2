package com.gmail.fukushima.kai.mystage.talker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.common.common.Sentence;
import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class Talker {
	public String name;
	public Integer id;
	public List<Sentence> listSentence = new ArrayList<Sentence>();
	public Sentence question = new Sentence();
	public Sentence answer = new Sentence();
	public void talkNext(Player player, DataPlayer data) {
		Integer line = data.line;
		if(listSentence.size() - 1 < line) {
			line = 0;
		} else {
			Sentence sentence = listSentence.get(line);
			printEnMessage(player, sentence);
			printJpMessage(player, sentence);
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
		if(0 < question.en.size() || 0 < question.kanji.size() || 0 < question.kana.size()) {
			printEnMessage(player, question);
			printJpMessage(player, question);
		} else {
			player.sendMessage("No Question.");
		}
	}
	private void printEnMessage(Player player, Sentence sentence) {
		String message = "(EN) " + sentence.loadEn();
		player.sendMessage(message);
	}
	private void printJpMessage(Player player, Sentence sentence) {
		String message = "(JP) " + sentence.loadJp();
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
}
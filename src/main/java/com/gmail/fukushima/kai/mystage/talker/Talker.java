package com.gmail.fukushima.kai.mystage.talker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.common.common.DataManagerPlayer;
import com.gmail.fukushima.kai.common.common.DataPlayer;
import com.gmail.fukushima.kai.common.common.Sentence;

public class Talker {
	public String name;
	public Integer id;
	public List<Sentence> listSentence = new ArrayList<Sentence>();
	public void printDebug() {
		System.out.println(" [Debug Talker]" + name);
		System.out.println("  ID: " + id);
	}
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
		DataManagerPlayer.saveDataPlayer(data);
	}
	private void printEnMessage(Player player, Sentence sentence) {
		String message = "(EN) " + sentence.loadEn();
		player.sendMessage(message);
	}
	private void printJpMessage(Player player, Sentence sentence) {
		String message = "(JP) " + sentence.loadJp();
		player.sendMessage(message);
	}
}
package com.github.orgs.kotobaminers.virtualryuugaku.stage.stagetest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Broadcast;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class PrivateStage {
	public String name = "";
	public String stage = "";
	public List<Talk> listTalk = new ArrayList<Talk>();
	public Integer count = -1;
	public String question = "";
	public List<String> answers = new ArrayList<String>();
	List<Integer> score = new ArrayList<Integer>();

	public PrivateStage(String name) {
		this.name = name;
	}

	public void questNext() {
		count++;
		UtilitiesProgramming.printDebugMessage("Count: "+ count, new Exception());
		if(listTalk.size() <= count) {
			UtilitiesProgramming.printDebugMessage("Error: listTalker.size() <= count", new Exception());
			return;
		}
		Talk talk = listTalk.get(count);
		Description description = talk.description;
		answers = new ArrayList<String>();
		Random random = new Random();
		String languageAnswer= "";
		int language = random.nextInt(2);//Random Number 0 or 1
		switch(language) {
		case 0:
			question = description.express(Expression.EN);
			answers.addAll(description.kanji);
			answers.addAll(description.kana);
			answers.addAll(description.romaji);
			languageAnswer = "Japanese";
			break;
		case 1:
			question = description.express(Expression.KANA);
			answers.addAll(description.en);
			languageAnswer = "English";
			break;
		default: break;
		}
		Integer number = count + 1;
		String[] opts = {number.toString(), question, languageAnswer, talk.name};
		MessengerGeneral.broadcast(Broadcast.GAME_STAGE_QUEST_4, opts);
	}

	public void printEnd() {
		String[] opts = {stage.toUpperCase()};
		MessengerGeneral.broadcast(Broadcast.GAME_STAGE_END_1, opts);
		Integer total = getScoreTotal();
		String[] opts2 = {name, total.toString()};
		MessengerGeneral.broadcast(Broadcast.STAGE_TOTAL_2, opts2);
	}

	public void correct(Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(!(0 < score.size())) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			score =  new ArrayList<Integer>();
			for(int i = 0; i < listTalk.size(); i++) {
				score.add(0);
			}
		}
		score.set(count, 1);
	}

	public Integer getScoreTotal() {
		Integer total = 0;
		for(Integer s : score) {
			total += s;
		}
		return total;
	}
}
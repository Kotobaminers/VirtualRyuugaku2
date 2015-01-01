package com.gmail.fukushima.kai.mystage.mystage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.mystage.talker.Talker;
import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesGeneral;

public class Stage {
	public String name;
	public String creator;
	public List<Talker> listTalker;
	public Stage() {
	}
	public Stage(String name, String creator, List<Talker> listTalker) {
		this.name = name;
		this.creator = creator;
		this.listTalker = listTalker;
	}
	public void printInformation(Player player) {
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		List<Integer> done = data.done;
		Integer countDone = 0;
		Integer countQuestion = 0;
		List<String> listName = new ArrayList<String>();
		for(Talker talker : listTalker) {
			if(talker.hasAnswerEn() && talker.hasAnswerJp()) {
				countQuestion++;
				if(done.contains(talker.id)) countDone++;
			}
			listName.add(talker.name);
		}
		String talkers = UtilitiesGeneral.joinStringsWithSpace((String[])listName.toArray(new String[listName.size()]));
		player.sendMessage("[Stage Info] " + name + " created by " + creator);
		player.sendMessage("Questions: " + countQuestion);
		player.sendMessage("Done: " + countDone);
		player.sendMessage("Talkers: " + talkers);
		if(countQuestion <= countDone) {
			player.sendMessage("You have completed this stage!");;
		}
	}
}
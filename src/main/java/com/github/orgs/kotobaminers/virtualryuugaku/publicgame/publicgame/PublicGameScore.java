package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerVRG.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.ScoreboardUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGame.EventScore;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

public class PublicGameScore extends ScoreboardUtility {

	public void broadcastScore() {
		List<String> result = new ArrayList<String>();
		for (Entry<String, Integer> entry  : score.entrySet()) {
			result.add(entry.getKey() + ": " + entry.getValue().toString());
		}
		if (0 < result.size()) {
			String[] opts = {Utility.joinStrings(result, ", ")};
			Message.NONE_1.broadcast(opts);
		}
	}

	public void refreshScore() {
		score = new HashMap<String, Integer>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (PublicGameController.join.contains(player.getUniqueId())) {
				score.put(player.getName(), 0);
			}
		}
	}

	public void addScore(String name, EventScore event) {
		Debug.printDebugMessage("", new Exception());
		Integer current = 0;
		if (score.containsKey(name)) {
			current = score.get(name);
		}
		score.put(name, current + event.getScore());
	}

	public void effectFinish() {
		if (score.size() < 1) {
			return;
		}
		Collection<Integer> values = score.values();
		Integer max = Collections.max(values);
		if (max < 1) {
			return;
		}
		for (Entry<String, Integer> entry : score.entrySet()) {
			if (entry.getValue() == max) {
				String name = entry.getKey();
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (player.getName().equals(name)) {
						Effects.shootFirework(player);
						break;
					}
				}
			}
		}
	}

	@Override
	public String getName() {
		return "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "* SCORE *";
	}
}

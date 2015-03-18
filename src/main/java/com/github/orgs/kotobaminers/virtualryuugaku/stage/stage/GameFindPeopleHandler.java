package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.vrgnpc.vrgnpc.DataManagerVRGNPC;
import com.github.orgs.kotobaminers.virtualryuugaku.vrgnpc.vrgnpc.VRGNPC;

public class GameFindPeopleHandler {
	private static Map<String, GameFindPeople> mapGame = new HashMap<String, GameFindPeople>();
	public static Map<String, GameFindPeople> getMapGame() {
		return mapGame;
	}

	public static void loadNewGame(String name, String stage) {
		GameFindPeople game = new GameFindPeople();
		List<VRGNPC> listVRGNPC = DataManagerVRGNPC.getListVRGNPC(stage);
		Collections.shuffle(listVRGNPC);
		game.stage = stage;
		game.listVRGNPC = listVRGNPC;
		getMapGame().put(name, game);
	}
	public static void removeGame(Player player) {
		String name = player.getName();
		if(hasGame(name)) {
			GameFindPeople game = getGame(name);
			String[] opts = {game.stage};
			player.sendMessage(MessengerGeneral.getMessage(Message.FIND_PEOPLE_REMOVE_1, opts));
			getMapGame().remove(name);
		}
	}

	public static void tryNext(Player player) {
		String name = player.getName();
		if(!isValidCount(name)) {
			return;
		}
		GameFindPeople game = getGame(name);
		String[] opts = {game.stage};
		player.sendMessage(MessengerGeneral.getMessage(Message.FIND_PEOPLE_QUEST_1, opts));
		DataManagerVRGNPC.printListDescription(game.listVRGNPC.get(game.count).id, player);
	}

	public static GameFindPeople getGame(String name) {
		GameFindPeople game = new GameFindPeople();
		if(mapGame.containsKey(name)) {
			UtilitiesProgramming.printDebugMessage("No Game: " + name, new Exception());
			game = mapGame.get(name);
		}
		return game;
	}

	public static VRGNPC getVRGNPCCurrent(String name) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		VRGNPC vrgnpc = new VRGNPC();
		if(isValidCount(name)) {
			GameFindPeople game = getGame(name);
			vrgnpc = game.listVRGNPC.get(game.count);
		}
		return vrgnpc;
	}

	public static Boolean isValidCount(String name) {
		GameFindPeople game = new GameFindPeople();
		if(hasGame(name)) {
			game = getGame(name);
			if(game.count < game.listVRGNPC.size()) {
				return true;
			} else {
				UtilitiesProgramming.printDebugMessage("Invalid count: " + game.count + "/" + game.listVRGNPC.size(), new Exception());
			}
		}
		return false;
	}

	public static Boolean hasGame(String name) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(mapGame.containsKey(name)) {
			return true;
		}
		UtilitiesProgramming.printDebugMessage("Has No Game", new Exception());
		return false;
	}

	public static void checkPerson(Player player, Integer id) {
		String name = player.getName();
		if(isValidCount(name)) {
			if(getVRGNPCCurrent(name).id.equals(id)) {
				player.sendMessage("Correct Person.");
				player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
				GameFindPeople game = getGame(name);
				game.count++;
				if(isFinished(name)) {
					String[] opts = {game.stage};
					player.sendMessage(MessengerGeneral.getMessage(Message.FIND_PEOPLE_FINISH_1, opts));
					GameFindPeopleHandler.removeGame(player);
				} else {
					tryNext(player);
				}
			} else {
				player.sendMessage("Wrong Person.");
				player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
				GameFindPeopleHandler.removeGame(player);
			}
		}
	}

	public static Boolean isFinished(String name) {
		if(hasGame(name)) {
			GameFindPeople game = getGame(name);
			if(game.listVRGNPC.size() <= game.count) {
				return true;
			}
		}
		return false;
	}
}
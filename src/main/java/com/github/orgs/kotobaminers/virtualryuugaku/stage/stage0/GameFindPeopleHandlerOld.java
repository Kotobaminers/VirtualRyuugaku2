package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage0;

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

public class GameFindPeopleHandlerOld {
	private static Map<String, GameFindPeopleOld> mapGame = new HashMap<String, GameFindPeopleOld>();
	public static Map<String, GameFindPeopleOld> getMapGame() {
		return mapGame;
	}

	public static void loadNewGame(String name, String stage) {
		GameFindPeopleOld game = new GameFindPeopleOld();
		List<VRGNPC> listVRGNPC = DataManagerVRGNPC.getListVRGNPC(stage);
		Collections.shuffle(listVRGNPC);
		game.stage = stage;
		game.listVRGNPC = listVRGNPC;
		getMapGame().put(name, game);
	}
	public static void removeGame(Player player) {
		String name = player.getName();
		if(hasGame(name)) {
			GameFindPeopleOld game = getGame(name);
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
		GameFindPeopleOld game = getGame(name);
		String[] opts = {game.stage};
		player.sendMessage(MessengerGeneral.getMessage(Message.GAME_FIND_PEOPLE_MISSION_0, opts));
		DataManagerVRGNPC.printListDescription(game.listVRGNPC.get(game.count).id, player);
	}

	public static GameFindPeopleOld getGame(String name) {
		GameFindPeopleOld game = new GameFindPeopleOld();
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
			GameFindPeopleOld game = getGame(name);
			vrgnpc = game.listVRGNPC.get(game.count);
		}
		return vrgnpc;
	}

	public static Boolean isValidCount(String name) {
		GameFindPeopleOld game = new GameFindPeopleOld();
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
				GameFindPeopleOld game = getGame(name);
				game.count++;
				if(isFinished(name)) {
					String[] opts = {game.stage};
					player.sendMessage(MessengerGeneral.getMessage(Message.FIND_PEOPLE_FINISH_2, opts));
					GameFindPeopleHandlerOld.removeGame(player);
				} else {
					tryNext(player);
				}
			} else {
				player.sendMessage("Wrong Person.");
				player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
				GameFindPeopleHandlerOld.removeGame(player);
			}
		}
	}

	public static Boolean isFinished(String name) {
		if(hasGame(name)) {
			GameFindPeopleOld game = getGame(name);
			if(game.listVRGNPC.size() <= game.count) {
				return true;
			}
		}
		return false;
	}
}
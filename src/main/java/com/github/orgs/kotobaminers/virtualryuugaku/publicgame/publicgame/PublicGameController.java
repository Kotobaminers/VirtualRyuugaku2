package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Stage;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.StageController;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGame.EventScore;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public class PublicGameController extends BukkitRunnable {
	public static JavaPlugin plugin;
	public static Stage stage = StageController.getStageRandom();
	public static PublicGame game;
	public static String lastStage = "";
	public static Set<UUID> join = new HashSet<UUID>();
	public static PublicGameMode mode = PublicGameMode.FIND_PEOPLE;
	private enum State {
		TO_NEXT_STAGE,
		TO_NEXT_GAME,
		TO_QUESTION,
		TO_FINISH,
	}
	private State state = State.TO_QUESTION;

	public enum PublicGameMode {FIND_PEOPLE, ANKI;
		public static PublicGameMode giveNext(PublicGameMode mode) {
			Integer position = 0;
			for (PublicGameMode search : PublicGameMode.values()) {
				if (search.equals(mode)) {
					if (position < PublicGameMode.values().length - 1) {
						return PublicGameMode.values()[position + 1];
					} else {
						return PublicGameMode.FIND_PEOPLE;
					}
				}
				position++;
			}
			return PublicGameMode.FIND_PEOPLE;
		}
	}

	public PublicGameController(JavaPlugin plugin) {
		PublicGameController.plugin = plugin;
		loadGame();
	}

	public static void loadStage(String stage) {//Need to check stageName is valid
		PublicGameController.stage = StageController.getStage(stage);
		join = new HashSet<UUID>();
		mode = PublicGameMode.FIND_PEOPLE;
		loadGame();
	}

	public static void loadLastStage() {
		loadStage(lastStage);
	}

	private State updateState() {
		Debug.printDebugMessage(state.name(), new Exception());
		switch (state) {
		case TO_NEXT_STAGE:
			return State.TO_NEXT_GAME;
		case TO_NEXT_GAME:
			return State.TO_QUESTION;
		case TO_QUESTION:
			if (game.hasQuestedAll()) {
				return State.TO_FINISH;
			}
			return State.TO_QUESTION;
		case TO_FINISH:
			if (mode.equals(PublicGameMode.values()[PublicGameMode.values().length - 1])) {
				return State.TO_NEXT_STAGE;
			} else {
				return State.TO_NEXT_GAME;
			}
		default:
			break;
		}
		return State.TO_QUESTION;
	}

	@Override
	public void run() {
		state = updateState();

		switch(state) {
		case TO_NEXT_STAGE:
			Debug.printDebugMessage("",new Exception());
			saveLastGame();
			stage = StageController.getStageRandom();
			join = new HashSet<UUID>();
			Message.GAME_NEW_STAGE_1.broadcast(Arrays.asList(stage.name));
			break;

		case TO_NEXT_GAME:
			mode = PublicGameMode.giveNext(mode);
			loadGame();
			game.startGame();
//			break;

		case TO_QUESTION:
			Debug.printDebugMessage("",new Exception());
			game.continueGame();
			break;

		case TO_FINISH:
			Debug.printDebugMessage("",new Exception());
			game.finishGame();
			loadEmptyGame();
			break;

		default:
			break;
		}

		Bukkit.getServer().getScheduler().runTaskLater(plugin, this, game.getInterval());
	}

	private void saveLastGame() {
		lastStage = stage.name;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (PublicGameController.join.contains(player.getUniqueId())) {
				Message.GAME_REPEAT_0.print(player, null);
			}
		}
	}

	private static void loadGame() {
		Debug.printDebugMessage(mode.name(), new Exception());
		game = new PublicEventGame(stage.getKeySentences(), mode);
		switch(mode) {
		case FIND_PEOPLE:
			game = new PublicEventGame(stage.getKeySentences(), mode);
			break;
		case ANKI:
			game = new PublicCommandGame(stage.getKeySentences(), mode);
			break;
		default:
			break;
		}
	}

	private void loadEmptyGame() {
		Debug.printDebugMessage("", new Exception());
		game = new PublicEmptyGame();
	}

	public static void joinPlayer(Player player) {
		join.add(player.getUniqueId());
		game.score.addScore(player.getName(), EventScore.JOIN);
	}
}




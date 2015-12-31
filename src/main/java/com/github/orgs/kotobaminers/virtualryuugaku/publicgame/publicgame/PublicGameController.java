package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Stage;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.StageController;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public class PublicGameController extends BukkitRunnable {
	public static JavaPlugin plugin;
	public static Stage stage = StageController.getStageRandom();
	public static PublicGame game;
	public long delay = 20L;
	public static PublicGameMode mode = PublicGameMode.FIND_PEOPLE;

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

	@Override
	public void run() {
		runScheduledTask();
		Bukkit.getServer().getScheduler().runTaskLater(plugin, this, game.getInterval());
	}

	public void loadGame() {
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

	public void runScheduledTask() {
		Debug.printDebugMessage("" + game.isFinished() + " " + mode.name(),new Exception());
		if (game.isFinished()) {
			Debug.printDebugMessage("",new Exception());
			game.finishGame();
			if (mode.equals(PublicGameMode.values()[PublicGameMode.values().length - 1])) {
				Debug.printDebugMessage("",new Exception());
				stage = StageController.getStageRandom();
			}
			mode = PublicGameMode.giveNext(mode);
			loadGame();
		} else {
			Debug.printDebugMessage("",new Exception());
			game.continueGame();
		}
	}



}
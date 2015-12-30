package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class PublicGameController extends BukkitRunnable {
	public static JavaPlugin plugin;
	public static PublicGameStage stage = new PublicGameStage();
	public static PublicGame game = new PublicEventGame();
	public long delay = 20L;
	private PublicGameMode mode = PublicGameMode.FIND_PEOPLE;

	enum PublicGameMode {FIND_PEOPLE, ANKI;
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
		stage = new PublicGameStage();
		loadGame();
	}

	@Override
	public void run() {
		runScheduledTask();
		Bukkit.getServer().getScheduler().runTaskLater(plugin, this, game.getInterval());
	}

	public void loadGame() {
		UtilitiesProgramming.printDebugMessage(mode.name(), new Exception());
		game = new PublicEventGame();
		switch(mode) {
		case FIND_PEOPLE:
			game = new PublicEventGame();
			break;
		case ANKI:
			game = new PublicCommandGame(stage.getKeyTalk(), mode);
			break;
		default:
			break;
		}
	}

	public void runScheduledTask() {
		UtilitiesProgramming.printDebugMessage("" + game.isFinished() + " " + mode.name(),new Exception());
		if (game.isFinished()) {
			UtilitiesProgramming.printDebugMessage("",new Exception());
			game.finishGame();
			if (mode.equals(PublicGameMode.values()[PublicGameMode.values().length - 1])) {
				UtilitiesProgramming.printDebugMessage("",new Exception());
				stage.setStageRandom();
			}
			mode = PublicGameMode.giveNext(mode);
			loadGame();
		} else {
			UtilitiesProgramming.printDebugMessage("",new Exception());
			game.continueGame();
		}
	}



}
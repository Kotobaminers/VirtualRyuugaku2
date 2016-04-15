package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGameController.PublicGameMode;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;

public abstract class PublicGame {
	public PublicGameScore score = new PublicGameScore();
	public PublicGameMode mode = PublicGameMode.FIND_PEOPLE;
	public Set<UUID> cantAnswer = new HashSet<UUID>();

	public enum EventScore {
		WRITE_CORRECTLY(3),
		WRUTE_WRONGLY(0),
		FIND_CORRECTLY(2),
		FIND_WRONGLY(-1),
		CHEAT_A_CONVERSATION(-1),
		JOIN(0);
		private final int score;
		private EventScore(int code) {
			this.score = code;
		}
		public int getScore() {
			return score;
		}
	}

	public abstract void continueGame();
	public abstract void giveCurrentQuestion(Player player);
	public void finishGame() {
		Debug.printDebugMessage("",new Exception());
		if (0 < score.score.size()) {
			Message.GAME_FINISH_0.broadcast(null);
			score.broadcastScore();
			score.effectFinish();
		}
	}
	public void startGame() {
		Debug.printDebugMessage("",new Exception());
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (PublicGameController.join.contains(player.getUniqueId())) {
				Message.GAME_STARTING_1.broadcast(Arrays.asList(mode.toString()));
				printStart(player);
			}
		}
	}

	public boolean canAnswer(UUID uuid) {
		if (PublicGameController.join.contains(uuid) && !cantAnswer.contains(uuid)) {
			return true;
		}
		return false;
	}

	public abstract boolean hasQuestedAll();
	public abstract long getInterval();
	public abstract void validateAnswer(Player player, String answer);

	public abstract void eventCorrect(Player player);
	public abstract void eventWrong(Player player);

	protected abstract void printStart(Player player);
}
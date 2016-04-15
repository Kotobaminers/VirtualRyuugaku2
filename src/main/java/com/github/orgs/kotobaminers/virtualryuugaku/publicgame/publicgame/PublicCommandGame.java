package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Language;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.VRGSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGameController.PublicGameMode;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;

public class PublicCommandGame extends PublicGame {
	public List<PublicCommandGameQuestion> questions = new ArrayList<PublicCommandGameQuestion>();
	private Set<UUID> cantAnswer = new HashSet<UUID>();
	public static long interval = 20L * 30;
	private Integer count = -1;

	public PublicCommandGame(List<VRGSentence> talks, PublicGameMode mode) {
		for (VRGSentence talk : talks) {
			questions.add(new PublicCommandGameQuestion(talk, mode, Language.getRandom()));
		}
		this.mode = mode;
		this.score.refreshScore();
	}

	@Override
	public void continueGame() {
		cantAnswer = new HashSet<UUID>();
		count++;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (PublicGameController.join.contains(player.getUniqueId())) {
				giveCurrentQuestion(player);
				Effects.playSound(player, Scene.APPEAR);
			}
		}
	}

	@Override
	public boolean hasQuestedAll() {
		Debug.printDebugMessage("" + count + " " + questions.size(), new Exception());
		if (questions.size() <= count + 1) {
			return true;
		}
		return false;
	}

	@Override
	public long getInterval() {
		return interval;
	}

	@Override
	public void giveCurrentQuestion(Player player) {
		PublicCommandGameQuestion question = getCurrentQuestion();
		question.printQuestion(player);
	}

	@Override
	public void validateAnswer(Player player, String answer) {
		if (cantAnswer.contains(player.getUniqueId())) {
			return;
		}
		if (0 <= count) {
			if (getCurrentQuestion().validateAnswer(answer)) {
				eventCorrect(player);
				cantAnswer.add(player.getUniqueId());
			} else {
				eventWrong(player);
			}
			score.updateScoreboard(player);
		}
	}

	private PublicCommandGameQuestion getCurrentQuestion() {
		return questions.get(count);
	}

	@Override
	public void eventCorrect(Player player) {
		Debug.printDebugMessage("", new Exception());
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
		Message.COMMON_CORRECT_0.print(player, null);
		cantAnswer.add(player.getUniqueId());
		score.addScore(player.getName(), PublicGame.EventScore.WRITE_CORRECTLY);
	}

	@Override
	public void eventWrong(Player player) {
		Debug.printDebugMessage("", new Exception());
		player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
		Message.COMMON_WRONG_0.print(player, null);
		score.addScore(player.getName(), PublicGame.EventScore.WRUTE_WRONGLY);
	}

	@Override
	protected void printStart(Player player) {
		Message.GAME_ANSWER_PUBLIC_0.print(player, null);
	}

}

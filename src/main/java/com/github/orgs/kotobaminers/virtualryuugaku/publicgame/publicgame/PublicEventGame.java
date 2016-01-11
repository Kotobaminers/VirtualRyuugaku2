package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerVRG.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.VRGSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame.PublicGameController.PublicGameMode;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;

public class PublicEventGame extends PublicGame {
	List<VRGSentence> sentences = new ArrayList<VRGSentence>();

	public static long interval = 20L * 20;
	private Integer count = -1;

	public PublicEventGame(List<VRGSentence> sentences, PublicGameMode mode) {
		this.sentences = sentences;
		this.mode = mode;
		this.score.refreshScore();
	}

	public void eventTouchNPC(NPC npc, Player player) {
		if (!canAnswer(player.getUniqueId())) {
			return;
		}

		if (npc.getId() == getCurrentSentence().id) {
			eventCorrect(player);
		} else {
			eventWrong(player);
		}
		score.updateScoreboard(player);
	}

	@Override
	public void continueGame() {
		cantAnswer = new HashSet<UUID>();
		count++;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (PublicGameController.join.contains(player.getUniqueId())) {
				giveCurrentQuestion(player);
			}
		}
	}

	@Override
	public void giveCurrentQuestion(Player player) {
		VRGSentence sentence= getCurrentSentence();
		Message.GAME_FIND_PEOPLE_TITLE_0.print(player, null);
		Effects.playSound(player, Scene.APPEAR);
		String[] optsEn = {sentence.description.getEnglishJoined()};
		Message.NONE_1.print(player, optsEn);
		String[] optsJp = {sentence.description.getJapaneseJoined(player)};
		Message.NONE_1.print(player, optsJp);
	}

	@Override
	public boolean hasQuestedAll() {
		Debug.printDebugMessage("" + count + " " + sentences.size(), new Exception());
		if (sentences.size() <= count + 1) {
			return true;
		}
		return false;
	}

	@Override
	public long getInterval() {
		return interval;
	}

	private VRGSentence getCurrentSentence() {
		return sentences.get(count);
	}

	@Override
	public void eventCorrect(Player player) {
		Debug.printDebugMessage("", new Exception());
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
		Message.COMMON_CORRECT_0.print(player, null);
		cantAnswer.add(player.getUniqueId());
		score.addScore(player.getName(), PublicGame.EventScore.FIND_CORRECTLY);
	}

	@Override
	public void eventWrong(Player player) {
		Debug.printDebugMessage("", new Exception());
		player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
		Message.COMMON_WRONG_0.print(player, null);
		score.addScore(player.getName(), PublicGame.EventScore.FIND_WRONGLY);
	}

	@Override
	public void validateAnswer(Player player, String answer) {
	}

	@Override
	protected void printStart(Player player) {
	}
}

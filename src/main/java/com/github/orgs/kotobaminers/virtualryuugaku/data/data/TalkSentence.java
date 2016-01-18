package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

public class TalkSentence extends Sentence {;
	private Japanese japanese;
	private English english;

	public TalkSentence(List<String> kanji, List<String> kana, List<String> en, Integer id) {
		this.japanese = new Japanese(kanji, kana);
		this.english = new English(en);
		this.id = id;
	}

	public static Optional<List<Sentence>> createSentenceList(List<String> kanji, List<String> kana, List<String> en, List<Integer> ids) {
		List<Sentence> sentences = new ArrayList<Sentence>();
		if(kanji.size() == kana.size() && kanji.size() == en.size() && kanji.size() == ids.size()) {
			for (Integer i = 0; i < kanji.size(); i++) {
				sentences.add(new TalkSentence(Arrays.asList(kanji.get(i)), Arrays.asList(kana.get(i)), Arrays.asList(en.get(i)), ids.get(i)));
			}
		}
		if (0 < sentences.size()) {
			return Optional.ofNullable(sentences);
		}
		return Optional.empty();
	}

	@Override
	public void print(Player player) {

	}

	public Japanese getJapanese() {
		return japanese;
	}

	public English getEnglish() {
		return english;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void playEffect(Player player) {
		NPCHandler.findNPC(getId()).ifPresent(n -> {
			Location location = n.getStoredLocation();
			Utility.lookAt(player, location);
			player.getWorld().spigot().playEffect(location.add(0, 2, 0), Effect.NOTE, 25, 10, 0, 0, 0, 0, 1, 10);
			player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);}
		);
	}
}

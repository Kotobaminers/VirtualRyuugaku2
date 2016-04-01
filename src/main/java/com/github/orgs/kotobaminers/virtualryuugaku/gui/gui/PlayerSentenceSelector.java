package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.OnlinePlayerNPCs;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.PlayerSentence;

public class PlayerSentenceSelector extends SentenceSelector {
	public static final String TITLE = "Player's Sentences";
	public List<PlayerSentence> sentences = new ArrayList<>();
	private List<String> questions = new ArrayList<>();
	private boolean spawnNPC = false;

	public PlayerSentenceSelector(List<HolographicSentence> sentences, List<String> questions, int id) {
		this.sentences = sentences.stream()
			.map(sentence -> (PlayerSentence) sentence)
			.collect(Collectors.toList());
		this.questions = questions;
		if (OnlinePlayerNPCs.getOnlineIds().contains(id)) {
			spawnNPC = true;
		}
	}
	public PlayerSentenceSelector() {
	}

	@Override
	public List<List<ItemStack>> getIcons() {
		List<ItemStack> qs = questions.stream().map(q -> {
			ItemStack icon = GUIIcon.PLAYER_QUESTION.createItem();
			ItemMeta itemMeta = icon.getItemMeta();
			itemMeta.setDisplayName(q);
			icon.setItemMeta(itemMeta);
			return icon;
		}).collect(Collectors.toList());
		List<List<ItemStack>> icons = sentences.stream()
			.map(sentence -> sentence.giveIcons().get())
			.collect(Collectors.toList());
		List<List<ItemStack>> result = new ArrayList<>();
		for (int i = 0; i < qs.size(); i++) {
			if (i < icons.size()) {
				List<ItemStack> list = new ArrayList<>();
				list.addAll(icons.get(i));
				list.add(0, qs.get(i));
				result.add(list);
			}
		}
		return result;

	}
	@Override
	public List<ItemStack> getOptionIcons() {
		List<GUIIcon> icons = null;
		if (spawnNPC) {
			icons = Arrays.asList(GUIIcon.RESPAWN, GUIIcon.SPAWN_NPC);
		} else {
			icons = Arrays.asList(GUIIcon.FREE_UP, GUIIcon.RESPAWN);
		}
		return icons.stream()
			.map(option ->option.createItem())
			.collect(Collectors.toList());
	}

	@Override
	public String getTitle() {
		return TITLE;
	}
}

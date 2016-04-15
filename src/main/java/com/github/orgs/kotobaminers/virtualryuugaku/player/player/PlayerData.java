package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Language;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.PlayerRequest;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceEditor;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.UnitScore;

import net.citizensnpcs.api.npc.NPC;

public class PlayerData {
	public UUID uuid = null;
	public Integer line = 0;
	private Integer select= -1;
	public Map<String, UnitScore> scores = new HashMap<>();
	public Optional<SentenceEditor> editor = Optional.empty();
	public Optional<PlayerRequest> request = Optional.empty();
	public List<SpellType> expressions = new ArrayList<SpellType>(Arrays.asList(SpellType.EN, SpellType.KANJI, SpellType.KANA, SpellType.ROMAJI));
	public Language learn = Language.NONE;

	@Deprecated
	public Conversation conversation = null;

	public void addHelperQuestionDone(String unit, Integer id) {
		scores.computeIfAbsent(unit.toUpperCase(), key -> new UnitScore(key));
		scores.get(unit.toUpperCase()).done.add(id);
	}

	public PlayerData(Player player) {
		this.uuid = player.getUniqueId();
	}
	public PlayerData(UUID uuid) {
		this.uuid = uuid;
	}
	public void printDebugMessage() {
	}

	public UUID getUuid() {
		return uuid;
	}
	public Integer getLine() {
		return line;
	}
	public List<SpellType> getSpellTypes() {
		return Stream.of(SpellType.values()).filter(spell -> expressions.contains(spell))
			.collect(Collectors.toList());
	}
	public void selectNPC(NPC npc) {
		this.select = npc.getId();
	}
	public Integer getSelectId() {
		return this.select;
	}
	public void toggleSpellType(SpellType spell) {
		if (expressions.contains(spell)) {
			expressions.remove(spell);
		} else {
			expressions.add(spell);
		}
	}
}

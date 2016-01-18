package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationQuestion;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.NPCConversation;

public class PlayerData {
	public String name = "";
	public UUID uuid = null;
	public Integer line = 0;

	public Conversation conversation = new NPCConversation();

	public List<SpellType> expressions = new ArrayList<SpellType>(Arrays.asList(SpellType.EN, SpellType.KANJI, SpellType.KANA, SpellType.ROMAJI));
	public ConversationQuestion question = new ConversationQuestion();

	public PlayerData(Player player) {
		this.name = player.getName();
		this.uuid = player.getUniqueId();
	}
	public PlayerData() {
	}

	public void printDebugMessage() {
		System.out.println("PlayerData: " + name + " " + line + " " + uuid.toString());
	}

	public String getName() {
		return name;
	}
	public UUID getUuid() {
		return uuid;
	}
	public Integer getLine() {
		return line;
	}
	public List<SpellType> getSpellTypes() {
		return expressions;
	}
}

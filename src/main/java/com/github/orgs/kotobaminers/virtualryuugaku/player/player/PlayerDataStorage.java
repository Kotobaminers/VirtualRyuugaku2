package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Storage;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.NPCSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.VirtualRyuugakuManager;

public class PlayerDataStorage implements Storage {
	public static Map<UUID, PlayerData> playerStorage = new HashMap<UUID, PlayerData>();
	public static final Integer lineInitial = 0;
	public static final String base = "PLAYER";

	public static PlayerData getDataPlayer(Player player) {
		return getDataPlayer(player.getUniqueId());
	}
	public static PlayerData getDataPlayer(UUID uuid) {
		PlayerData data = new PlayerData();
		if(playerStorage.containsKey(uuid)) {
			data = playerStorage.get(uuid);
		} else {
			data.uuid = uuid;
			playerStorage.put(data.uuid, data);
		}
		return data;
	}

	public static void toggleExpression(PlayerData data, Expression expression) {
		List<Expression> expressions = data.expressions;
		if(expressions.contains(expression)) {
			while(expressions.remove(expression)) {};
		} else {
			expressions.add(expression);
		}
	}

	public static NPCSentence getTalk(PlayerData data) throws Exception{
		if (data.line < data.conversation.sentences.size()) {
			return data.conversation.sentences.get(data.line);
		}
		throw new Exception("Invalid talk: Name" + data.name);
	}

	public void importPlayerData() {
		File file = new File(VirtualRyuugakuManager.plugin.getDataFolder() + "//CONFIG//CONFIG.yml");
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
		for(String key : yaml.getKeys(false)) {
			if(key.equalsIgnoreCase(base)) {
				MemorySection memory = (MemorySection) yaml.get(key);
				for (String uuidString : memory.getKeys(false)) {
					PlayerData data = createPlayerData((MemorySection) memory.get(uuidString), uuidString);
					playerStorage.put(data.uuid, data);
				}
			}
		}
	}

	private PlayerData createPlayerData(MemorySection memory, String key) {
		PlayerData data = new PlayerData();
		data.uuid = UUID.fromString(key);
		data.name = memory.getString("NAME");
		data.line = memory.getInt("LINE");
		return data;
	}

	@Override
	public void save() {
	}

	public Conversation loadCurrentConversation(Player player) {
		PlayerData data = getDataPlayer(player);
		Conversation conversation = data.conversation;
		return conversation;
	}
	@Override
	public void initialize() {
		playerStorage = new HashMap<UUID, PlayerData>();
		importPlayerData();
	}

	public static void printDebugMessage() {
		for (PlayerData data : playerStorage.values()) {
			data.printDebugMessage();
		}
	}
}
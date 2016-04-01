package com.github.orgs.kotobaminers.virtualryuugaku.player.player;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.VRGSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceEditor;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.VRGManager;

public class PlayerDataStorage {
	private static Map<UUID, PlayerData> playerStorage = new HashMap<UUID, PlayerData>();
	private static final String base = "PLAYER";

	private PlayerDataStorage() {
	}

	public static PlayerData getPlayerData(Player player) {
		playerStorage.computeIfAbsent(player.getUniqueId(), key -> playerStorage.put(key, new PlayerData(player)));
		return playerStorage.get(player.getUniqueId());
	}

	public static PlayerData getPlayerData(UUID uuid) {
		playerStorage.computeIfAbsent(uuid, key -> playerStorage.put(key, new PlayerData(uuid)));
		return playerStorage.get(uuid);
	}

	public static void addLine(Player player) {
		getPlayerData(player).line += 1;
	}

	public static VRGSentence getTalk(PlayerData data) throws Exception{
		if (data.line < data.conversation.sentences.size()) {
			return data.conversation.sentences.get(data.line);
		}
		throw new Exception();
	}

	public static void importPlayerData() {
		File file = new File(VRGManager.plugin.getDataFolder() + "//CONFIG//CONFIG.yml");
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

	private static PlayerData createPlayerData(MemorySection memory, String key) {
		PlayerData data = new PlayerData(UUID.fromString(key));
		data.line = memory.getInt("LINE");
		return data;
	}

	public Conversation loadCurrentConversation(Player player) {
		PlayerData data = getPlayerData(player);
		Conversation conversation = data.conversation;
		return conversation;
	}

	public static void initialize() {
		playerStorage = new HashMap<UUID, PlayerData>();
		importPlayerData();
	}

	public static void printDebugMessage() {
		for (PlayerData data : playerStorage.values()) {
			data.printDebugMessage();
		}
	}

	public static void openEditor(Player player, Optional<SentenceEditor> editor) {
		if (editor.isPresent()) {
			getPlayerData(player).editor = editor;
			Message.EDITOR_1.print(Arrays.asList("Opening a VRG editor"), player);
			editor.get().printEditorHelp(player);
		}
	}
	public static void closeEditor(Player player) {
		getPlayerData(player).editor = Optional.empty();
		Message.EDITOR_1.print(Arrays.asList("Closing the VRG editor"), player);
	}

}
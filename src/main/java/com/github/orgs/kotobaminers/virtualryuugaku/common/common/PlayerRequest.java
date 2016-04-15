package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.Arrays;
import java.util.Optional;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

public class PlayerRequest {
	private Optional<Player> sender = Optional.empty();
	private Optional<Player> receiver = Optional.empty();
	private Optional<Integer> npcId = Optional.empty();

	private RequestMode mode = RequestMode.NONE;

	enum RequestMode {
		TELEPORT_PLAYER("Will you teleport to the player?"),
		TELEPORT_NPC("Will you teleport to an npc?"),
		NONE(""),
		;

		private String message = "";
		private RequestMode(String message) {
			this.message = message;
		}
		public String getMessage() {
			return message;
		}

	}

	public PlayerRequest(Player sender) {
		this.sender = Optional.ofNullable(sender);
	}

	public static PlayerRequest createTeleportToNPCRequest(Player sender, int id) {
		PlayerRequest request = new PlayerRequest(sender);
		request.mode = RequestMode.TELEPORT_NPC;
		request.npcId= Optional.of(id);
		return request;
	}
	public static PlayerRequest createTeleportToPlayerRequest(Player sender) {
		PlayerRequest request = new PlayerRequest(sender);
		request.mode = RequestMode.TELEPORT_PLAYER;
		return request;
	}

	public void sendRequest(Player receiver) {
		this.receiver = Optional.of(receiver);
		PlayerDataStorage.getPlayerData(receiver).request = Optional.of(this);
		sendMessage(receiver);
	}

	private void sendMessage(Player receiver) {
		String name = sender.map(s -> s.getName()).orElse("");
		sender.ifPresent(s -> Message.REQUEST_1.print(Arrays.asList(mode.getMessage() + " from " +  name + ": To accept, [/vrg r a]"), receiver));
	}

	public void execute() {
		switch(mode) {
		case TELEPORT_NPC:
			receiver.ifPresent(r -> sender.ifPresent(s -> npcId.ifPresent(id -> NPCUtility.findNPC(id).ifPresent(npc -> {
				Utility.teleportToNPC(r, npc);
				Utility.teleportToNPC(s, npc);
			}))));
		case TELEPORT_PLAYER:
			sender.ifPresent(s -> receiver.ifPresent(c -> c.teleport(s.getLocation())));
		case NONE:
		default:
			break;
		}
	}

	public void printSendingMessage() {
	}
}

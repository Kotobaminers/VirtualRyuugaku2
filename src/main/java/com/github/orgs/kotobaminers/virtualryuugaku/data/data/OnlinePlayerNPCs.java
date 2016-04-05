package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;

import net.citizensnpcs.api.npc.NPC;

public class OnlinePlayerNPCs {
	private static List<Integer> ids = new ArrayList<>();
	public static Map<UUID, String> unitNames = new HashMap<>();

	public static void updateOnlineNPCs() {
		List<Player> online = Bukkit.getOnlinePlayers().stream().collect(Collectors.toList());
		List<NPC> npcs = ids.stream().map(id ->NPCUtility.findNPC(id))
			.filter(opt -> opt.isPresent())
			.map(opt -> opt.get())
			.collect(Collectors.toList());
		List<NPC> despawns = npcs.stream().filter(NPC::isSpawned)
			.filter(npc -> !online.stream().map(Player::getName).anyMatch(name -> name.equalsIgnoreCase(npc.getName())))
			.collect(Collectors.toList());
		List<Player> spawns = online.stream().filter(player -> !npcs.stream().map(npc -> npc.getName()).anyMatch(name -> player.getName().equalsIgnoreCase(name)))
			.collect(Collectors.toList());
		despawns.forEach(NPCUtility::changeNPCAsEmpty);
		despawns.forEach(NPC::despawn);
		spawns.forEach(player ->
			npcs.stream().filter(npc -> !npc.isSpawned()).findFirst()
				.ifPresent(n -> NPCUtility.renameNPCAsPlayer(n, player.getName(), player.getUniqueId()))
			);
	}
	static void setOnlineIds(List<Integer> ids) {
		OnlinePlayerNPCs.ids = ids;
	}
}


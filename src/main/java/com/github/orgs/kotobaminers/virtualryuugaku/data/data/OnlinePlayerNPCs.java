package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;

import net.citizensnpcs.api.npc.NPC;

public class OnlinePlayerNPCs {
	private static List<Integer> ids = new ArrayList<>();
	public static Map<UUID, String> unitNames = new HashMap<>();
	
	public static void updateOnlineNPCsJoin() {
		updateOnlineNPCs(Optional.empty());
	}
	public static void updateOnlineNPCsQuit(Player quit) {
		updateOnlineNPCs(Optional.of(quit));
	}

	private static void updateOnlineNPCs(Optional<Player> quit) {
		List<Player> onlinePlayers = Bukkit.getOnlinePlayers().stream().collect(Collectors.toList());
		if (quit.isPresent()) {
			onlinePlayers = onlinePlayers.stream()
				.filter(player -> !player.getName().equalsIgnoreCase(quit.get().getName()))
				.collect(Collectors.toList());
		}
		List<NPC> npcs = ids.stream()
			.map(id -> NPCUtility.findNPC(id))
			.filter(opt -> opt.isPresent())
			.map(opt -> opt.get())
			.collect(Collectors.toList());

		List<String> onlines = onlinePlayers.stream()
			.map(Player::getName).collect(Collectors.toList());
		List<String> spawnings = npcs.stream()
			.filter(NPC::isSpawned)
			.map(NPC::getName)
			.collect(Collectors.toList());

		List<String> spawners = onlines.stream()
			.filter(name -> 
				!spawnings.contains(name))
			.collect(Collectors.toList());
		List<String> despawners = spawnings.stream()
			.filter(name -> !onlines.contains(name))
			.collect(Collectors.toList());
		
		npcs.stream()
			.filter(npc -> despawners.contains(npc.getName()))
			.forEach(npc -> {
				NPCUtility.changeNPCAsEmpty(npc);
				npc.despawn();
			});
		onlinePlayers.stream()
			.filter(player -> spawners.contains(player.getName()))
			.forEach(player -> npcs.stream()
				.filter(npc -> !npc.isSpawned())
				.findFirst()
				.ifPresent(npc -> {
					npc.spawn(npc.getStoredLocation());
					NPCUtility.renameNPCAsPlayer(npc, player.getName(), player.getUniqueId());
					UnitStorage.findUnit(npc).ifPresent(unit -> unit.addEmptyPlayerSentencesIfAbsent(npc));
				})
			);
	}

	static void setOnlineIds(List<Integer> ids) {
		OnlinePlayerNPCs.ids = ids;
	}
	public static void initialize() {
		ids.stream()
			.map(id -> NPCUtility.findNPC(id))
			.forEach(opt -> 
				opt.ifPresent(npc -> {
					NPCUtility.changeNPCAsEmpty(npc);
					npc.despawn();
				})
			);
		updateOnlineNPCs(Optional.empty());
	}
}


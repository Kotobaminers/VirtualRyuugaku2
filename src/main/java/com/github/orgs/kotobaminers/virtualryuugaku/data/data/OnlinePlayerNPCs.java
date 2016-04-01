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

	public static void updateOnlineNPCs() {
		List<NPC> npcs = ids.stream().map(id ->NPCUtility.findNPC(id))
			.filter(opt -> opt.isPresent())
			.map(opt -> opt.get())
			.collect(Collectors.toList());
		List<Player> players = Bukkit.getOnlinePlayers().stream().collect(Collectors.toList());
		for (int i = 0; i < npcs.size(); i++) {
			if (i < players.size()) {
				Player player = players.get(i);
				NPCUtility.renameNPCAsPlayer(npcs.get(i), player.getName(), player.getUniqueId());
				if (!unitNames.containsKey(player.getUniqueId())) {
					UnitStorage.units.keySet().stream().sorted().findFirst()
						.ifPresent(key -> {
							unitNames.put(player.getUniqueId(), key);
							UnitStorage.units.get(key).addEmptyPlayerSentencesIfAbsent(player);
						});
				}
			} else {
				npcs.get(i).despawn();
			}
		}
	}

	public static Optional<Unit> findUnit(NPC npc) {
		if (!ids.contains(npc.getId())) return Optional.empty();
		return NPCUtility.findSkinUUID(npc).map(u -> unitNames.getOrDefault(u, null))
			.flatMap(name -> UnitStorage.findUnit(name));
	}

	static void setOnlineIds(List<Integer> ids) {
		OnlinePlayerNPCs.ids = ids;
	}
	public static List<Integer> getOnlineIds() {
		return ids;
	}
}


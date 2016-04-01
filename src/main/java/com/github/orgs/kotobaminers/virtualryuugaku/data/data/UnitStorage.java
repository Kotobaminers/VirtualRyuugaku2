package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

import net.citizensnpcs.api.npc.NPC;

public class UnitStorage {
	public static Map<String, Unit> units = new HashMap<String, Unit>();

	public static void initialize() {
		units = new HashMap<>();
	}

	public static void addUnit(String name, Integer id, CommandSender sender) {
		name = name.toUpperCase();
		if(UnitStorage.units.containsKey(name)) {
			Message.ERROR_1.print(Arrays.asList("Existing Unit: " + name), sender);
			return;
		}

		Optional<NPC> npc = NPCUtility.findNPC(id);
		if (!npc.isPresent()) {
			Message.ERROR_1.print(Arrays.asList("The NPC doesn't exist: " + name), sender);
			return;
		}

		if(getAllIds().contains(id)) {
			Message.ERROR_1.print(Arrays.asList("The NPC is in use: " + id), sender);
			return;
		}

		Unit unit = new Unit(name);
		List<HolographicSentence> ls = new ArrayList<>();
		ls.add((HolographicSentence) HelperSentence.createEmpty(id));
		unit.getHelperSentences().add(ls);
		units.put(name, unit);
		Message.VRG_1.print(Arrays.asList("A new unit with a HelperNPC was created: " + name + " " + id), sender);
		if (sender instanceof Player) {
			Utility.teleportToNPC((Player) sender, npc.get());
		}
	}

	public static void unregisterUnit(String name, CommandSender sender) {
		name= name.toUpperCase();
		if (units.containsKey(name)) {
			Message.VRG_1.print(Arrays.asList("Deleting the unit...: " + name), sender);
			units.remove(name);
		} else {
			Message.INVALID_1.print(Arrays.asList("Not existing unit: " + name), sender);
		}
	}

	public static Optional<Unit> findUnit(Integer id) {
		return units.values().stream().filter(unit -> {
			if(unit.getHelperSentences().stream().anyMatch(ls -> ls.stream().anyMatch(s -> s.getId().equals(id)))) return true;
			if(unit.getPlayerNPCIds().contains(id)) return true;
			return false;
		}).findFirst();
	}
	public static Optional<Unit> findUnit(String name) {
		return Optional.ofNullable(units.getOrDefault(name.toUpperCase(), null));
	}

	public static Optional<Unit> findDisplayedUnit(NPC npc) {
		if (OnlinePlayerNPCs.getOnlineIds().contains(npc.getId())) {
			return OnlinePlayerNPCs.findUnit(npc);
		}

		return units.values().stream().filter(unit -> {
				if(unit.getHelperSentences().stream()
						.anyMatch(ls -> ls.stream()
								.anyMatch(s -> s.getId().equals(npc.getId()))))
					return true;
				if(unit.getPlayerNPCIds().contains(npc.getId())) {
					Optional<UUID> optional = NPCUtility.findSkinUUID(npc);
					if (optional.isPresent()) {
						UUID skin  = optional.get();
						if (unit.getPlayerSentences().stream()
								.anyMatch(ls -> ls.stream()
										.anyMatch(s -> ((PlayerSentence) s).getUniqueId().equals(skin)))) {
							return true;
						}
					}
				}
				return false;
			}).findFirst();
	}
	public static Optional<Unit> findUnitByPlayerNPCId(Integer id) {
		return units.values().stream().filter(u -> u.getPlayerNPCIds().contains(id)).findFirst();
	}

	public static Set<Integer> getAllIds() {
		Set<Integer> ids = new HashSet<>();
		units.values().stream().forEach(unit -> {
			unit.getHelperSentences().stream().forEach(ls -> ls.forEach(s -> ids.add(s.getId())));
			ids.addAll(unit.getPlayerNPCIds());
		});
		return ids;
	}

	public static boolean eventLeftClickPlayerNPC(NPC npc, Player player) {
		Optional<Unit> unit = UnitStorage.findUnitByPlayerNPCId(npc.getId());
		if (!unit.isPresent()) return false;

		List<PlayerSentence> sentences = unit.get().getPlayerSentences().stream()
			.filter(ls -> 0 < ls.size())
			.map(ls -> ((PlayerSentence) ls.get(0)))
			.collect(Collectors.toList());
		List<UUID> uuids = sentences.stream().map(s -> s.getUniqueId()).collect(Collectors.toList());
		List<UUID> displayUUIDs = unit.get().getPlayerNPCIds().stream()
			.map(id -> NPCUtility.findNPC(id).flatMap(n -> NPCUtility.findSkinUUID(n)))
			.filter(uuid -> uuid.isPresent())
			.map(uuid -> uuid.get())
			.filter(uuid -> uuids.contains(uuid))
			.collect(Collectors.toList());
		if (NPCUtility.isEmptyPlayerNPC(npc)) {
			if (displayUUIDs.contains(player.getUniqueId())) {
				List<PlayerSentence> hiddens = sentences.stream()
					.filter(s -> !displayUUIDs.contains(s.getUniqueId()))
					.collect(Collectors.toList());
				if (0 < hiddens.size()) {
					Collections.shuffle(hiddens);
					NPCUtility.renameNPCAsPlayer(npc, hiddens.get(0).getDisplayName(), hiddens.get(0).getUniqueId());
					player.getWorld().spigot().playEffect(npc.getStoredLocation().clone().add(0,1,0), Effect.EXPLOSION, 22, 22, (float) 0.5, (float) 0.5, (float) 0.5, (float) 0, 20, 10);
					player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1f, 1f);
					return true;
				}
			} else {
				NPCUtility.renameNPCAsPlayer(npc, player.getName(), player.getUniqueId());
				unit.ifPresent(u -> u.addEmptyPlayerSentencesIfAbsent(player));
				player.getWorld().spigot().playEffect(npc.getStoredLocation().clone().add(0,1,0), Effect.EXPLOSION, 22, 22, (float) 0.5, (float) 0.5, (float) 0.5, (float) 0, 20, 10);
				player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1f, 1f);
				return true;
			}
		} else {
			Optional<UUID> skin = NPCUtility.findSkinUUID(npc);
			if (skin.isPresent()) {
				if(!displayUUIDs.contains(skin.get())) {
					NPCUtility.changeNPCAsEmpty(npc);
					player.getWorld().spigot().playEffect(npc.getStoredLocation().clone().add(0,1,0), Effect.EXPLOSION, 22, 22, (float) 0.5, (float) 0.5, (float) 0.5, (float) 0, 20, 10);
					player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1f, 1f);
					return true;
				}
			}
		}
		return false;
	}
}



package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.data.data.PlayerSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.Unit;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MetadataStore;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class NPCUtility {
	public static final List<EntityType> ALLOWED_ENTITY_TYPE = Arrays.asList(EntityType.PLAYER, EntityType.CREEPER);
	private static final String EMPTY = ChatColor.GREEN + "*EMPTY*";
	private static final List<String> skinMeta = Arrays.asList("cached-skin-uuid-name", "player-skin-textures",
			"cached-skin-uuid", "player-skin-name", "player-skin-signature");

	public static Optional<NPC> findNPC(Integer id) {
		if (id < 0) {
			return Optional.empty();
		}
		return Optional.ofNullable(CitizensAPI.getNPCRegistry().getById(id));
	}

	public static void changeNPCAsPlayer(NPC npc, String player) {
		try {
			NPCUtility.changeType(npc, EntityType.PLAYER);
			npc.setName(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void changeNPCAsEmpty(NPC npc) {
		MetadataStore data = npc.data();
		skinMeta.stream().filter(meta -> data.has(meta)).forEach(data::remove);
		npc.setBukkitEntityType(EntityType.CREEPER);
		npc.setName(EMPTY);
	}

	public static boolean isEmptyPlayerNPC(NPC npc) {
		if (npc.getEntity().getType().equals(EntityType.CREEPER) && npc.getName().equals(EMPTY)) {
			return true;
		}
		return false;
	}

	private static void changeType(NPC npc, EntityType type) throws Exception {
		if (ALLOWED_ENTITY_TYPE.contains(type)) {
			npc.setBukkitEntityType(type);
			return;
		}
		throw new Exception("NOT ALLOWED ENTITY TYPE: " + type.toString());
	}

	public static void renameNPCAsPlayer(NPC npc, String name, UUID uuid) {
		MetadataStore data = npc.data();
		skinMeta.stream().filter(meta -> data.has(meta)).forEach(data::remove);
		data.set("cached-skin-uuid-name", name);
		data.set("cached-skin-uuid", uuid.toString());
		npc.despawn();
		npc.setBukkitEntityType(EntityType.PLAYER);
		npc.setName(name);
		npc.spawn(npc.getStoredLocation());
	}

	public static void updateLearnerNPC(NPC npc, Player player) {
		Optional<Unit> unit = SentenceStorage.findUnit(npc.getId());
		if (!unit.isPresent()) return;
		if (!unit.get().getPlayerNPCIds().contains(npc.getId())) return;

		Stream<PlayerSentence> sentences = unit.get().getPlayerSentences().stream()
			.filter(ls -> 0 < ls.size())
			.map(ls -> ((PlayerSentence) ls.get(0)));
		List<UUID> displayUUIDs = sentences
			.filter(s -> !s.getId().equals(PlayerSentence.DEFAULT_ID))
			.collect(Collectors.toList())
			.stream().map(s -> s.getUniqueId()).collect(Collectors.toList());
		if (isEmptyPlayerNPC(npc)) {
			if (displayUUIDs.contains(player.getUniqueId())) {
				List<PlayerSentence> hiddens = sentences
						.filter(s -> s.getId().equals(PlayerSentence.DEFAULT_ID))
						.collect(Collectors.toList());
				if (0 < hiddens.size()) {
					Collections.shuffle(hiddens);
					renameNPCAsPlayer(npc, hiddens.get(0).getDisplayName(), hiddens.get(0).getUniqueId());
					player.getWorld().spigot().playEffect(npc.getStoredLocation().clone().add(0,1,0), Effect.EXPLOSION, 22, 22, (float) 0.5, (float) 0.5, (float) 0.5, (float) 0, 20, 10);
					player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1f, 1f);
				}
			} else {
				renameNPCAsPlayer(npc, player.getName(), player.getUniqueId());
				player.getWorld().spigot().playEffect(npc.getStoredLocation().clone().add(0,1,0), Effect.EXPLOSION, 22, 22, (float) 0.5, (float) 0.5, (float) 0.5, (float) 0, 20, 10);
				player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1f, 1f);
			}
		} else {
			findSkinUUID(npc).ifPresent(skin -> {
				if(!displayUUIDs.contains(skin)) {
					changeNPCAsEmpty(npc);
					player.getWorld().spigot().playEffect(npc.getStoredLocation().clone().add(0,1,0), Effect.EXPLOSION, 22, 22, (float) 0.5, (float) 0.5, (float) 0.5, (float) 0, 20, 10);
					player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1f, 1f);
				}
			});
		}
	}

	public static Optional<String> findSkinName(NPC npc) {
		if (npc.data().has("cached-skin-uuid-name")) {
			return Optional.ofNullable(npc.data().get("cached-skin-uuid-name"));
		}
		return Optional.empty();
	}

	public static Optional<UUID> findSkinUUID(NPC npc) {
		if (npc.data().has("cached-skin-uuid")) {
			String string = npc.data().get("cached-skin-uuid");
			return Optional.ofNullable(UUID.fromString(string));
		}
		return Optional.empty();
	}

	public static void refreshSkinMeta(NPC npc) {
		MetadataStore data = npc.data();
		skinMeta.stream().filter(meta -> data.has(meta)).forEach(data::remove);
	}


	@Deprecated
	public static NPC getNPC(Integer id) throws Exception {
		if (existsNPC(id)) {
			NPC npc = CitizensAPI.getNPCRegistry().getById(id);
			return npc;
		}
		throw new Exception("NPC not exists: ID" + id.toString());
	}

	@Deprecated
	private static Iterator<NPC> getNPCs() {
		return CitizensAPI.getNPCRegistry().iterator();
	}

	@Deprecated
	private static boolean existsNPC(Integer id) {
		for (Iterator<NPC> i = getNPCs(); i.hasNext();) {
			if (id.equals(i.next().getId())) {
				return true;
			}
		}
		return false;
	}
}

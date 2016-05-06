package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.EntityType;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MetadataStore;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class NPCUtility {
	private static final String EMPTY = ChatColor.GREEN + "*EMPTY*";
	private static final List<String> skinMeta = Arrays.asList(
			"cached-skin-uuid-name",
			"player-skin-textures",
			"cached-skin-uuid",
			"player-skin-name",
			"player-skin-signature");

	public static Optional<NPC> findNPC(Integer id) {
		if (id < 0) {
			return Optional.empty();
		}
		return Optional.ofNullable(CitizensAPI.getNPCRegistry().getById(id));
	}

	public static void changeNPCAsEmpty(NPC npc) {
		refreshSkinMeta(npc);
		npc.setBukkitEntityType(EntityType.CREEPER);
		npc.setName(EMPTY);
	}

	public static boolean isEmptyPlayerNPC(NPC npc) {
		if (npc.getEntity().getType().equals(EntityType.CREEPER) && npc.getName().equals(EMPTY)) {
			return true;
		}
		return false;
	}

	public static void renameNPCAsPlayer(NPC npc, String name, UUID uuid) {
		refreshSkinMeta(npc);
		MetadataStore data = npc.data();
		data.set("cached-skin-uuid-name", name);
		data.set("cached-skin-uuid", uuid.toString());
		npc.despawn();
		npc.setBukkitEntityType(EntityType.PLAYER);
		npc.setName(name);
		npc.spawn(npc.getStoredLocation());
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
}

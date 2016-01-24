package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;
import net.md_5.bungee.api.ChatColor;

public class NPCHandler {
	public static final List<EntityType> ALLOWED_ENTITY_TYPE = Arrays.asList(EntityType.PLAYER, EntityType.CREEPER);
	private static final String EMPTY = ChatColor.GREEN + "*EMPTY*";

	public static NPC getNPC(Integer id) throws Exception {
		if (existsNPC(id)) {
			NPC npc = CitizensAPI.getNPCRegistry().getById(id);
			return npc;
		}
		throw new Exception("NPC not exists: ID" + id.toString());
	}

	public static Optional<NPC> findNPC(Integer id) {
		if (existsNPC(id)) {
			NPC npc = CitizensAPI.getNPCRegistry().getById(id);
			return Optional.of(npc);
		}
		return Optional.empty();
	}

	private static Iterator<NPC> getNPCs() {
		return CitizensAPI.getNPCRegistry().iterator();
	}

	private static boolean existsNPC(Integer id) {
		for (Iterator<NPC> i = getNPCs(); i.hasNext();) {
			if(id == i.next().getId()) {
				return true;
			}
		}
		return false;
	}

	public static void changeNPCAsPlayer(NPC npc, String player) {
		try {
			NPCHandler.changeType(npc, EntityType.PLAYER);
			npc.setName(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void changeNPCAsEmpty(NPC npc) {
		Owner owner = npc.getTrait(Owner.class);
		owner.setOwner(EMPTY);
		npc.addTrait(owner);
		npc.setBukkitEntityType(EntityType.CREEPER);
		npc.setName(EMPTY);
	}

	private static void changeType(NPC npc, EntityType type) throws Exception {
		if(ALLOWED_ENTITY_TYPE.contains(type)) {
			npc.setBukkitEntityType(type);
			return;
		}
		throw new Exception("NOT ALLOWED ENTITY TYPE: " + type.toString());
	}

	public static void ownNPC(NPC npc, Player player) {
		Owner owner = npc.getTrait(Owner.class);
		owner.setOwner(player.getName(), player.getUniqueId());
		npc.addTrait(owner);
		npc.setName(player.getName());
		npc.setBukkitEntityType(EntityType.PLAYER);
	}

	public static boolean isEmptyLearner(NPC npc) {
		if(npc.getTrait(Owner.class).getOwner().equalsIgnoreCase(EMPTY)) {
			return true;
		}
		return false;
	}

	public static boolean isYourNPC(NPC npc, UUID uuid) {
		Owner owner = npc.getTrait(Owner.class);
		UUID npcid = owner.getOwnerId();
		if (uuid.equals(npcid)) {
			return true;
		}
		return false;
	}

}


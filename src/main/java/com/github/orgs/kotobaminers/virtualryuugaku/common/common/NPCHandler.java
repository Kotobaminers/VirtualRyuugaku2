package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.bukkit.entity.EntityType;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public class NPCHandler {
	public static final List<EntityType> ALLOWED_ENTITY_TYPE = Arrays.asList(EntityType.PLAYER, EntityType.CREEPER);

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
			NPCHandler.changeName(npc, player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void changeNPCAsEmpty(NPC npc) {
		try {
			NPCHandler.changeType(npc, EntityType.CREEPER);
			NPCHandler.changeName(npc, "EMPTY");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void changeName(NPC npc, String name) {
		npc.setName(name);
	}

	private static void changeType(NPC npc, EntityType type) throws Exception {
		if(ALLOWED_ENTITY_TYPE.contains(type)) {
			npc.setBukkitEntityType(type);
			return;
		}
		throw new Exception("NOT ALLOWED ENTITY TYPE: " + type.toString());
	}
}
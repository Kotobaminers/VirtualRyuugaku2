package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.EntityType;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class NPCHandler {
	public enum NPCType {
		//CODE is for editable -> 1 is editable, the others aren't.
		DEFAULT(0),
		NOT_EXISTS(0),
		MYSELF(1),
		ME(1),
		;

		private final int code;
		private NPCType(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
		public boolean canEdit() {
			UtilitiesProgramming.printDebugMessage("NPCType Editable: " + code, new Exception());//TODO
			if(code == 1) {
				return true;
			}
			return false;
		}
	}

	public static final List<EntityType> ALLOWED_ENTITY_TYPE = Arrays.asList(EntityType.PLAYER, EntityType.CREEPER);

	public static NPC getNPC(Integer id) throws Exception {
		if (existsNPC(id)) {
			NPC npc = CitizensAPI.getNPCRegistry().getById(id);
			return npc;
		}
		throw new Exception();
	}

	public static boolean canEdit(Integer id) {
//		NPCType type = getNPCType(id);
//		if(type.canEdit()) {
//			return true;
//		}
//		UtilitiesProgramming.printDebugMessage("YOU CAN'T EDIT THIS NPC: " + id.toString(), new Exception());//TODO
		return false;
	}

	public static NPCType getNPCType(Integer id) {
//		for(Iterator<NPC> i = CitizensAPI.getNPCRegistry().iterator(); i.hasNext();) {
//			NPC npc = i.next();
//			Integer target = npc.getId();
//			if(target.equals(id)) {
//				if(ControllerMyself.isNPCMyself(npc)) {
//					return NPCType.MYSELF;
//				}
//				if (ControllerMyself.isMe(id)) {
//					return NPCType.ME;
//				}
//				return NPCType.DEFAULT;
//			}
//		}
		return NPCType.NOT_EXISTS;
	}

	public static void changeNPCAsPlayer(Integer id, String player) {
		try {
			NPCHandler.changeType(id, EntityType.PLAYER);
			NPCHandler.changeName(id, player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void changeNPCAsEmpty(Integer id) throws Exception {
		if(canEdit(id)) {
			NPCHandler.changeType(id, EntityType.CREEPER);
			NPCHandler.changeName(id, "ENPTY");
		} else {
			throw new Exception("You can't edit this NPC: " + id.toString());
		}
	}

	private static void changeName(Integer id, String name) {
		if (existsNPC(id)) {
			try {
				NPC npc = getNPC(id);
				npc.setName(name);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
	}

	private static void changeType(Integer id, EntityType type) throws Exception {
		if(ALLOWED_ENTITY_TYPE.contains(type)) {
			NPC npc = getNPC(id);
			npc.setBukkitEntityType(type);
			return;
		} else {
			throw new Exception("NOT ALLOWED ENTITY TYPE: " + type.toString());
		}
	}

	public static Iterator<NPC> getNPCs() {
		return CitizensAPI.getNPCRegistry().iterator();
	}

	public static boolean existsNPC(Integer id) {
		for (Iterator<NPC> i = getNPCs(); i.hasNext();) {
			if(id == i.next().getId()) {
				return true;
			}
		}
		return false;
	}
}

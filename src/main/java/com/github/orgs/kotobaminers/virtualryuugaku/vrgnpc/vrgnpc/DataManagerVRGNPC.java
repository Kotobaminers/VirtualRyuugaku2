package com.github.orgs.kotobaminers.virtualryuugaku.vrgnpc.vrgnpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataManagerCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.DataManager;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.LibraryManager;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConfigHandlerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class DataManagerVRGNPC implements DataManager {
	//mapTalker
	private static Map<Integer, VRGNPC> mapVRGNPC = new HashMap<Integer, VRGNPC>();

	@Override
	public void load() {
		initialize();
		loadMapVRGNPC();
	}
	@Override
	public void initialize() {
		mapVRGNPC = new HashMap<Integer, VRGNPC>();
	}
	private static void loadMapVRGNPC() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<VRGNPC> list = new ArrayList<VRGNPC>();
		Map<String, YamlConfiguration> mapConfig = LibraryManager.getListLibraryStage();
		for(String stage : mapConfig.keySet()) {
			list.addAll(LibraryHandlerVRGNPC.importVRGNPCLibrary(stage, mapConfig.get(stage)));
		}
		for(VRGNPC vrgnpc : list) {
			if(DataManagerCitizens.existsNPC(vrgnpc.id)) {
				overrideCitizens(vrgnpc);
				getMapVRGNPC().put(vrgnpc.id, vrgnpc);
			}
		}
	}
	private static void overrideCitizens(VRGNPC vrgnpc) {
		String name = DataManagerCitizens.getDataCitizens(vrgnpc.id).name;
		vrgnpc.name = name;
	}

	@Override
	public void save() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(VRGNPC vrgnpc : getMapVRGNPC().values()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
//			ConfigHandlerConversation.saveConversation(talker);
		}
		new ConfigHandlerConversation().save();
	}

	public static boolean existsVRGNPC(int id) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(getMapVRGNPC().containsKey(id)) {
			return true;
		}
		UtilitiesProgramming.printDebugMessage("Non VRGNPC", new Exception());
		return false;
	}
	public static boolean existsVRGNPC(NPC npc) {
		return existsVRGNPC(npc.getId());
	}

	public static Map<Integer, VRGNPC> getMapVRGNPC() {
		return mapVRGNPC;
	}
	public static VRGNPC getVRGNPC(NPC npc) {
		Integer id = npc.getId();
		return getVRGNPC(id);
	}

	public static VRGNPC getVRGNPC(Integer id) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		VRGNPC vrgnpc = new VRGNPC();
		if(getMapVRGNPC().containsKey(id)) {
			vrgnpc = getMapVRGNPC().get(id);
		}
		return vrgnpc;
	}
	public static List<VRGNPC> getListVRGNPC(String stage) {
		List<VRGNPC> listVRGNPC = new ArrayList<VRGNPC>();
		for(VRGNPC vrgnpc : getMapVRGNPC().values()) {
			if(vrgnpc.stage.equalsIgnoreCase(stage)) {
				listVRGNPC.add(vrgnpc);
			}
		}
		return listVRGNPC;
	}

	public static void printListDescription(int id, Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(existsVRGNPC(id)) {
			VRGNPC vrgnpc = getVRGNPC(id);
			for(Description description : vrgnpc.listDescription) {
//				player.sendMessage(description.getExpression(player));
			}
		}
	}
	public static void printListDescriptionAll(int id, Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(existsVRGNPC(id)) {
			VRGNPC vrgnpc = getVRGNPC(id);
			for(Description description : vrgnpc.listDescription) {
//				description.print(player);
			}
		}
	}
}
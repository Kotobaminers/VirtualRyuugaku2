package com.github.orgs.kotobaminers.virtualryuugaku.talker.talker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataManagerCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.LibraryManager;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.DataManager;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class DataManagerTalker implements DataManager {
	//mapTalker
	private static Map<Integer, Talker> mapTalker = new HashMap<Integer, Talker>();

	@Override
	public void loadAll() {
		initialize();
		loadMapTalker();
	}
	@Override
	public void initialize() {
		mapTalker = new HashMap<Integer, Talker>();
	}
	private static void loadMapTalker() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<Talker> list = new ArrayList<Talker>();
		list.addAll(ConfigHandlerTalker.importTalkerDefault());
		Map<String, YamlConfiguration> mapConfig = LibraryManager.getListLibraryStage();
		for(String stage : mapConfig.keySet()) {
			list.addAll(LibraryHandlerTalker.importTalkerLibrary(stage, mapConfig.get(stage)));
		}
		for(Talker talker : list) {
			if(Talker.isValidCitizensId(talker.id)) {
				talker = DataManagerCitizens.overrideTalker(talker);
				if(getMapTalker().containsKey(talker.id)) {
					UtilitiesProgramming.printDebugMessage("Overriding NPC: ID: " + talker.id, new Exception());
					talker = overrideTalkerLibarary(talker);
				}
				putTalker(talker);
			} else {
				UtilitiesProgramming.printDebugMessage("Non Existing NPC ID: " + talker.id, new Exception());
			}
		}
	}
	private static Talker overrideTalkerLibarary(Talker talker) {
		talker.mapComment = getTalker(talker.id).mapComment;
		return talker;
	}

	@Override
	public void saveAll() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		for(Talker talker : getMapTalker().values()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			ConfigHandlerTalker.saveTalker(talker);
		}
		new ConfigHandlerTalker().save();
	}

	public static void registerTalker(Talker talker) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(talker.isEmpty()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
		} else {
			putTalker(talker);
		}
	}
	public static boolean existsTalker(NPC npc) {
		Integer id = npc.getId();
		if(!getMapTalker().containsKey(id)) {
			UtilitiesProgramming.printDebugMessage("NON Talker", new Exception());
			return false;
		}
		return true;
	}


	public static Map<Integer, Talker> getMapTalker() {
		return mapTalker;
	}
	public static Talker getTalker(Integer id) {
		Talker talker = new Talker();
		if(getMapTalker().containsKey(id)) {
			talker = getMapTalker().get(id);
		}
		return talker;
	}
	private static void putTalker(Talker talker) {
		getMapTalker().put(talker.id, talker);
	}
}
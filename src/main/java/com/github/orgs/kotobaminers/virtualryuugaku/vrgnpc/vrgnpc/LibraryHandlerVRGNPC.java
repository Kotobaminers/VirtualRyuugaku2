package com.github.orgs.kotobaminers.virtualryuugaku.vrgnpc.vrgnpc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.PathConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class LibraryHandlerVRGNPC {
	private enum PathVRGNPC {INFORMATION}
	private enum PathInformation {KANJI, KANA, EN}
	public static List<VRGNPC> importVRGNPCLibrary(String stage, YamlConfiguration library) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<VRGNPC> list = new ArrayList<VRGNPC>();
		List<String> editor = library.getStringList(PathConversation.EDITOR.toString());
		for(String talkerPath : library.getKeys(false)) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			if(talkerPath.equalsIgnoreCase(PathVRGNPC.INFORMATION.toString())) {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				MemorySection memory = (MemorySection) library.get(talkerPath);
				for(String idString : memory.getKeys(false)) {
					UtilitiesProgramming.printDebugMessage("", new Exception());
					Integer id = Integer.parseInt(idString);
					MemorySection memoryId = (MemorySection) memory.get(idString);
					VRGNPC vrgnpc = new VRGNPC();
					//Name will be imported from citizens data.
					List<Description> listDescriptions = new ArrayList<Description>();
					if(memoryId.contains(PathInformation.EN.toString()) && memoryId.contains(PathInformation.KANJI.toString()) && memoryId.contains(PathInformation.KANA.toString())) {
						UtilitiesProgramming.printDebugMessage("", new Exception());
						List<String> kanji = memoryId.getStringList(PathInformation.KANJI.toString());
						List<String> kana = memoryId.getStringList(PathInformation.KANA.toString());
						List<String> en = memoryId.getStringList(PathInformation.EN.toString());
						if(kanji.size() == kana.size() && kanji.size() == en.size()) {
							UtilitiesProgramming.printDebugMessage("", new Exception());
							for(int i = 0; i < kanji.size(); i++) {
								UtilitiesProgramming.printDebugMessage("", new Exception());
								Description description = Description.create(kanji.get(i), kana.get(i), en.get(i), new ArrayList<String>());
								listDescriptions.add(description);
							}
						}
					}
					vrgnpc.stage = stage;
					vrgnpc.id = id;
					vrgnpc.editor.addAll(editor);
					vrgnpc.listDescription.addAll(listDescriptions);
					list.add(vrgnpc);
				}
			}
		}
		return list;
	}
}
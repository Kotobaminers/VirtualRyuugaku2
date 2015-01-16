package com.github.kotobaminers.virtualryuugaku.common.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.kotobaminers.virtualryuugaku.utilities.utilities.DataManager;
import com.github.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.kotobaminers.virtualryuugaku.virtualryuugaku.DataManagerPlugin;

public class DataManagerRomaji implements DataManager {
	public enum TypeLetters {
		TTSULARGESMALL,
		LARGESMALL,
		TTSULARGE,
		LARGE,
		DAKUTEN,
		SIGNS,
		}
	private static Map<String, List<Letters>> mapLetters = new HashMap<String, List<Letters>>();

	private static final String NAME_DIRECTORY = "LIBRARY//ROMAJI";
	private static final String NAME_FILE = "ROMAJI_TABLE.txt";
	private static final String DELIMITTER = "[Type]";
	private static void importRomaji() throws IOException {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String path = DataManagerPlugin.plugin.getDataFolder() + "//" + NAME_DIRECTORY + "//" + NAME_FILE;
		File file = new File(path);
		String lineJoined = "";
		InputStream str = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(str);
		BufferedReader buffer = new BufferedReader(reader);
		List<Letters> listLetters = new ArrayList<Letters>();
		String type = "";
		Map<String, List<Letters>> map = new HashMap<String, List<Letters>>();
		while((lineJoined = buffer.readLine()) != null){
			String[] line = lineJoined.split("\t");
			if(line[0].equalsIgnoreCase(DELIMITTER)) {
				if(0 < listLetters.size()) {
					map.put(type, listLetters);
				}
				listLetters = new ArrayList<Letters>();
				type = line[1];
			} else {
				Letters letters = new Letters();
				letters.setRomaji(line[0]);
				letters.setHiragana(line[1]);
				letters.setKatakana(line[2]);
				letters.setType(type);
				listLetters.add(letters);
			}
		}
		map.put(type, listLetters);
		buffer.close();
		reader.close();
		str.close();
		setMapLetters(map);
	}
	@Override
	public void initialize() {
		setMapLetters(new HashMap<String, List<Letters>>());
	}
	@Override
	public void loadAll() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		initialize();
		try {
			importRomaji();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void saveAll() {
	}
	public static Map<String, List<Letters>> getMapLetters() {
		return mapLetters;
	}
	public static void setMapLetters(Map<String, List<Letters>> mapLetters) {
		DataManagerRomaji.mapLetters = mapLetters;
	}
}

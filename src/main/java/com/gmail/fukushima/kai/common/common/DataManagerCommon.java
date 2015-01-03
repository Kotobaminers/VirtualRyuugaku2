package com.gmail.fukushima.kai.common.common;

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

import com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2.DataManagerPlugin;

public class DataManagerCommon implements DataManager {
	public enum TypeLetters {
		TTSULARGESMALL,
		LARGESMALL,
		TTSULARGE,
		LARGE,
		DAKUTEN,
		SIGNS,
		}
	public static Map<String, List<Letters>> mapLetters = new HashMap<String, List<Letters>>();

	private static final String NAME_DIRECTORY = "COMMON";
	private static final String NAME_FILE = "ROMAJI_TABLE.txt";
	private static final String DELIMITTER = "[Type]";
	private static void importRomaji() throws IOException {
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
		mapLetters = map;
	}
	@Override
	public void initialize() {
		mapLetters = new HashMap<String, List<Letters>>();
	}
	@Override
	public void load() {
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
}

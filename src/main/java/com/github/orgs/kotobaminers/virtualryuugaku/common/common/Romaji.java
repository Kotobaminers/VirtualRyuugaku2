package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

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

import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.VRGManager;

public class Romaji {
	public enum TypeLetters {
		TTSULARGESMALL,
		LARGESMALL,
		TTSULARGE,
		LARGE,
		DAKUTEN,
		SIGNS,
		}
	private static Map<String, List<Letters>> mapLetters = new HashMap<String, List<Letters>>();
	private static final String DELIMITTER = "[Type]";
	private static final String PATH = VRGManager.plugin.getDataFolder() + "//ROMAJI//ROMAJI_TABLE.txt";

	public static String toRomaji(String kana) {
		Map<String, List<Letters>> map = Romaji.getMapLetters();
		for(TypeLetters type : TypeLetters.values()) {
			for(String key : map.keySet()) {
				if(type.toString().equalsIgnoreCase(key)) {
					for(Letters letters : map.get(key)) {
						kana = kana.replace(letters.getHiragana(), letters.getRomaji());
						kana = kana.replace(letters.getKatakana(), letters.getRomaji());
					}
				}
			}
		}
		return kana;
	}
	public static List<String> addRomaji(List<String> strings) {
		List<String> list = new ArrayList<String>();
		for (String string : strings) {
			list.add(string);
			if (!isHalfWidthAlphanumeric(string)) {
				String romaji = toRomaji(string);
				if (isHalfWidthAlphanumeric(romaji)) {
					list.add(romaji);
				}
			}
		}
		return list;
	}
	private static boolean isHalfWidthAlphanumeric(String string) {
		if ( string == null || string.length() == 0 ) {
			return false;
		}
		int len = string.length();
		byte[] bytes = string.getBytes();
		if ( len == bytes.length ) {
			return true;
		}
		return false;
	}

	private static void importRomaji() throws IOException {
		File file = new File(PATH);
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
	public void initialize() {
		setMapLetters(new HashMap<String, List<Letters>>());
	}
	public void load() {
		initialize();
		try {
			importRomaji();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Map<String, List<Letters>> getMapLetters() {
		return mapLetters;
	}
	public static void setMapLetters(Map<String, List<Letters>> mapLetters) {
		Romaji.mapLetters = mapLetters;
	}
}

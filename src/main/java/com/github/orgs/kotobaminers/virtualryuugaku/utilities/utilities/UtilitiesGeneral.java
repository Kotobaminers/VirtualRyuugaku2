package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.DataManagerRomaji;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.DataManagerRomaji.TypeLetters;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Letters;

public class UtilitiesGeneral {
	public static String toRomaji(String kana) {
		Map<String, List<Letters>> map = DataManagerRomaji.getMapLetters();
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

	public static void sendMessageAll(String message) {
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			if(player.isOnline()) {
				try {
					player.sendMessage(message);
				} catch (Exception e) {
				}
			}
		}
	}

	public static String joinStrings(List<String> strings, String spacer) {
		if(strings == null)  return "";
		String string = "";
		for(String part : strings) {
			if(0 < part.length()) {
				string += spacer + part;
			}
		}
		if(0 < string.length()) {
			string = string.substring(spacer.length(), string.length());
		}
		return string;
	}
	public static String joinStrings(String[] strings, String spacer) {
		if(strings == null)  return "";
		String string = "";
		for(String part : strings) {
			string += spacer + part;
		}
		if(0 < string.length()) {
			string = string.substring(spacer.length(), string.length());
		}
		return string;
	}

	public static Integer getTotalLengthStrings(List<String> strings) {
		String total = "";
		for(String string : strings) {
			total += string;
		}
		return total.length();
	}
	public static Integer getTotalLengthStrings(String[] strings) {
		List<String> list = Arrays.asList(strings);
		return getTotalLengthStrings(list);
	}

	public static List<Integer> toListInteger(String string) {
		List<Integer> list = new ArrayList<Integer>();
		String tmp = "";
		if(string.startsWith("[") && string.endsWith("]")) {
			tmp = string.substring(1, string.length()-1);
			String[] tmps = tmp.split(", ");
			for(String str : tmps) {
				list.add(Integer.parseInt(str));
			}
		}
		return list;
	}
	public static String toYamlStringListInteger(List<Integer> list) {
		List<String> strings = new ArrayList<String>();
		for(Integer i : list) {
			strings.add(i.toString());
		}
		String string = toYamlStringListString(strings);
		return string;
	}
	public static String toYamlStringListString(List<String> strings) {
		String string = "[" + joinStrings(strings, ", ") + "]";
		return string;
	}
}
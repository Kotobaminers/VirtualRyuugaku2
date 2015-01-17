package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
	public static void runCommandAsOP(Player player, String command) {
		if(!player.isOp()) {
			try {
				player.setOp(true);
				Bukkit.getServer().dispatchCommand(player, command);
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				player.setOp(false);
			}
		} else {
			try {
				Bukkit.getServer().dispatchCommand(player, command);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String joinListListString(List<List<String>> listList, List<ChatColor> colors) {
		String comma = ", ";
		String string = "";
		Integer maxColor = colors.size();
		Integer count = 0;
		for(List<String> list : listList) {
			ChatColor color = colors.get(count);
			count++;
			if(count.equals(maxColor)) {
				count = 0;
			}
			for(String search : list) {
				string += color + search + ChatColor.GRAY + comma;
			}
		}
		if(comma.length() < string.length()) {
			string = string.substring(0, string.length() - comma.length());
		}
		string += ChatColor.RESET;
		return string;
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

	public static String joinStringsWithSpace(String[] strings) {
		String string = "";
		for(String part : strings) {
			string += " " + part;
		}
		if(0 < string.length()) {
			string = string.substring(1, string.length());
		}
		return string;
	}
	public static String joinListStringWithDot(List<String> strings) {
		String string = "";
		for(String part : strings) {
			string += "." + part;
		}
		if(0 < string.length()) {
			string = string.substring(1, string.length());
		}
		return string;
	}
	public static String joinArraysStringWithDot(String[] strings) {
		String string = "";
		for(String part : strings) {
			string += "." + part;
		}
		if(0 < string.length()) {
			string = string.substring(1, string.length());
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
}
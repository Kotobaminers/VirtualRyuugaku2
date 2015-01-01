package com.gmail.fukushima.kai.utilities.utilities;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.common.common.DataManagerCommon;
import com.gmail.fukushima.kai.common.common.Letters;
import com.gmail.fukushima.kai.common.common.DataManagerCommon.TypeLetters;

public class UtilitiesGeneral {
	public static String toRomaji(String kana) {
		Map<String, List<Letters>> map = DataManagerCommon.mapLetters;
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
			try
			{
				player.setOp(true);
				Bukkit.getServer().dispatchCommand(player, command);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				player.setOp(false);
			}
		} else {
			try
			{
				Bukkit.getServer().dispatchCommand(player, command);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
//	public static void sendMessageAll(String message) {
//		for(Player player : StaticFieldsCommon.plugin.getServer().getOnlinePlayers()) {
//			player.sendMessage(message);
//		}
//	}
//	public static String dropColors(String str) {
//		for(String color : StaticFieldsCommon.ESSENTIALS_COLORS) {
//			str = str.replace(color, "");
//		}
//		return str;
//	}
//	public static ChatColor loadColorRandom() {
//		ChatColor color;
//		Random random = new Random();
//		Integer num = random.nextInt(StaticFieldsCommon.CHAT_COLORS.size());
//		color = StaticFieldsCommon.CHAT_COLORS.get(num);
//		return color;
//	}
//	public static String loadMarkRandom() {
//		String mark = "";
//		Random random = new Random();
//		Integer num = random.nextInt(StaticFieldsCommon.MARKS.size());
//		mark = StaticFieldsCommon.MARKS.get(num);
//		return mark;
//	}
//	public static String loadColorMarkRandom(Integer num) {
//		String mark = "";
//		for(Integer i = 0; i < num; i++) {
//			mark += "" + loadColorRandom();
//			mark += loadMarkRandom();
//		}
//		mark += ChatColor.RESET;
//		return mark;
//	}
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
	public static void sendHelpCommand(Player player, Command command) {
		String title = "*** Help: " + command.getName();
		String description = command.getDescription();
		String usage = command.getUsage();
		List<String> aliases = command.getAliases();
		player.sendMessage(title);
		player.sendMessage(description);
		player.sendMessage(usage);
		player.sendMessage(aliases.toString());
	}
}
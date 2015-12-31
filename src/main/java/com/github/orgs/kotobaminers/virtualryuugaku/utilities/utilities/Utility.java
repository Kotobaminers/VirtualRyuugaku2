package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utility {
	private static final List<String> SHOWN_CHARACTERS = Arrays.asList(",", " ", ".", "、", "。");

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

	public static String toYamlStringFromListInteger(List<Integer> list) {
		List<String> strings = new ArrayList<String>();
		for(Integer i : list) {
			strings.add(i.toString());
		}
		String string = toYamlStringFromListString(strings);
		return string;
	}
	public static String toYamlStringFromListString(List<String> strings) {
		String string = "[" + joinStrings(strings, ", ") + "]";
		return string;
	}

	public static String showSameCharacters(String answer, String suggestion) {
		String show = "";
		for (int i = 0; i < answer.length(); i++) {
			String target = answer.substring(i, i+1);
			if (SHOWN_CHARACTERS.contains(target)) {
				show += target;
			} else if (suggestion.length() < i + 1) {
				show += "*";
			} else if (target.equalsIgnoreCase(suggestion.substring(i, i+1))) {
				show += target;
			} else {
				show += "*";
			}
		}
		return show;
	}
}



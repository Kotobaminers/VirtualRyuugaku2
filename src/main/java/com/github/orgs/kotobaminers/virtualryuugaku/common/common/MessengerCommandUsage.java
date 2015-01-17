package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessengerCommandUsage {
	private static final String MESSENGER_PREFIX = "" + ChatColor.GOLD + ChatColor.BOLD +"[" + ChatColor.YELLOW + "VRG" + ChatColor.GOLD + ChatColor.BOLD + "] " + ChatColor.RESET;
	public enum Usage {
		TALKER_SELECT_0,
		TALKER_COMMENT_READ_0,
		TALKER_COMMENT_DONE_0,
		;
	}
	public static void print(Player player, Usage key, String[] opts) {
		String message = MESSENGER_PREFIX;
		switch(key) {
		case TALKER_SELECT_0: message += "/talker select <ID NUMBER>"; break;
		case TALKER_COMMENT_READ_0: message += "/talker comment read"; break;
		case TALKER_COMMENT_DONE_0: message += "/talker comment done <SENDER NAME>"; break;
		default: break;
		}
		player.sendMessage(message);
	}
}
package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandAnswer extends MyCommand {
	public String answer = "";
	public CommandAnswer(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	private enum Commands {
		CONVERSATION,;
		private static Commands lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return Commands.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return Commands.CONVERSATION;
			}
		}
	}

	@Override
	public void runCommand() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(1 < args.length) {
			Commands commands = Commands.lookup(args[0]);
			List<String> list = new ArrayList<String>();
			for(int i = 1; i < args.length; i++) {
				list.add(args[i]);
			}
			answer = UtilitiesGeneral.joinStrings(list, " ");
			switch(commands) {
			case CONVERSATION:
				commandConversation();
				break;
			default:
				break;
			}
		}
	}

	private void commandConversation() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataManagerPlayer.getDataPlayer(player).question.validateQuestion(player, answer);
	}
}
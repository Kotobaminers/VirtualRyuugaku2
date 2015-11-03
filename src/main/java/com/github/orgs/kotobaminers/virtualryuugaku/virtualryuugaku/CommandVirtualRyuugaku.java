package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message0;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.CommandConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Language;

public class CommandVirtualRyuugaku extends MyCommand {
	public CommandVirtualRyuugaku(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	private enum Commands {LANGUAGE, GLOBAL, MYSELF, CONVERSATION, NONE,;
		public static Commands lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return Commands.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return Commands.NONE;
			}
		}
	}

	@Override
	public void runCommand() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(0 < args.length) {
			Commands commands = Commands.lookup(args[0]);
			switch(commands) {
			case LANGUAGE:
				commandLanguage();
				break;
			case CONVERSATION:
				new CommandConversation(player, command, args).runCommand();
				break;
			default:
				break;
			}
		}
	}

	private void commandLanguage() {
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		switch(data.language) {
		case EN:
			data.language = Language.JP;
			break;
		case JP:
			data.language = Language.EN;
			break;
		default:
			break;
		}
		String[] opts = {data.language.toString()};
		player.sendMessage(MessengerGeneral.getMessage(Message0.COMMAND_VRG_LANGUAGE_1, opts));
	}
}

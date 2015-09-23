package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.comment.CommandConversationComment;
import com.github.orgs.kotobaminers.virtualryuugaku.myself.myself.ControllerMyself;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandConversation extends MyCommand {
	public CommandConversation(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	private enum CommandsConversation {
		DEFAULT, COMMENT, GOOD, BAD,;
		private static CommandsConversation lookup(String name) {
			try {
				return CommandsConversation.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return CommandsConversation. DEFAULT;
			}
		}
	}
	@Override
	public void runCommand() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(0 < args.length) {
			CommandsConversation commands = CommandsConversation.lookup(args[1]);
			switch(commands) {
			case GOOD:
				commandGood(player, command, args);
				break;
			case BAD:
				commandBad(player, command, args);
				break;
			case COMMENT:
				new CommandConversationComment(player, command, args).runCommand();
				break;
			case DEFAULT:
			default:
				break;
			}
		}
	}

	private void commandBad(Player player, Command command, String[] args) {
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		Conversation conversation = data.conversation;
		if (0 < conversation.listTalk.size()) {
			UtilitiesProgramming.printDebugMessage("Removing the teachers from Recommenders", new Exception());//TODO
			for (String teacher : ControllerMyself.getTeachers()) {
				if (conversation.recommenders.contains(teacher)) {
					conversation.recommenders.remove(teacher);
					UtilitiesProgramming.printDebugMessage("Removed from Recommenders: " + teacher, new Exception());//TODO
				}
			}
		} else {
			UtilitiesProgramming.printDebugMessage("You need to select a valid conversation at first.", new Exception());//TODO
		}
	}

	private void commandGood(Player player, Command command, String[] args) {
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		Conversation conversation = data.conversation;
		if (0 < conversation.listTalk.size()) {
			String name = player.getName();
			if (conversation.recommenders.contains(name)) {
				conversation.recommenders.remove(name);
				UtilitiesProgramming.printDebugMessage("Not Good: (" + conversation.stage + " : " +UtilitiesGeneral.joinStrings(conversation.editor, ", ") + ")", new Exception());//TODO
			} else {
				conversation.recommenders.add(player.getName());
				UtilitiesProgramming.printDebugMessage("Good: (" + conversation.stage + " : " +UtilitiesGeneral.joinStrings(conversation.editor, ", ") + ")", new Exception());//TODO
			}
		} else {
			UtilitiesProgramming.printDebugMessage("You need to select a valid conversation at first.", new Exception());//TODO
		}
	}
}
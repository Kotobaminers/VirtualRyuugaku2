package com.github.kotobaminers.virtualryuugaku.talker.comment;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.kotobaminers.virtualryuugaku.common.common.Messenger;
import com.github.kotobaminers.virtualryuugaku.common.common.Messenger.Message;
import com.github.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.kotobaminers.virtualryuugaku.talker.comment.DataComment.CommentState;
import com.github.kotobaminers.virtualryuugaku.talker.talker.DataManagerTalker;
import com.github.kotobaminers.virtualryuugaku.talker.talker.Talker;
import com.github.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandTalkerComment extends MyCommand {
	public CommandTalkerComment(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	public enum CommandsComment {
		NONE, SEND, READ, REMOVE, LIST;
		public static CommandsComment lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return CommandsComment.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return CommandsComment.NONE;
			}
		}
	}
	@Override
	public void runCommand() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(1 < args.length) {
			CommandsComment commands = CommandsComment.lookup(args[1]);
			switch(commands) {
			case NONE:
				break;
			case READ:
				commandRead();
				break;
			case REMOVE:
				break;
			case SEND:
				commandSend();
				break;
			case LIST:

			default:
				break;
			}
		}
		printUsageComment();
	}
	private void commandRead() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Talker talker = DataManagerTalker.getTalker(DataManagerPlayer.getDataPlayer(player).select);
		String[] opts = {talker.name, talker.id.toString()};
		Messenger.print(player, Message.SHOW_COMMENT_2, opts);

		player.sendMessage("Comments for the selected talker(ID: " + talker.id.toString() + ") are shown.");
		for(DataComment data: talker.mapComment.values()) {
			data.printInfo(player);
		}
	}
	private void commandSend() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(2 < args.length){
			Talker talker = DataManagerTalker.getTalker(DataManagerPlayer.getDataPlayer(player).select);
			List<String> list = new ArrayList<String>();
			for (int i = 2; i < args.length; i++) {
				list.add(args[i]);
			}
			String message = UtilitiesGeneral.joinStrings(list, " ");
			DataComment comment = new DataComment();
			comment.sender = player.getName();
			comment.expression = message;
			comment.state = CommentState.NEW;
			talker.mapComment.put(comment.sender, comment);
		}
	}
	private void printUsageComment() {
	}
}
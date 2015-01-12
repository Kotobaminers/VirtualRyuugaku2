package com.gmail.fukushima.kai.talker.comment;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.talker.comment.DataComment.CommentState;
import com.gmail.fukushima.kai.talker.talker.DataManagerTalker;
import com.gmail.fukushima.kai.talker.talker.Talker;
import com.gmail.fukushima.kai.utilities.utilities.MyCommand;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class CommandTalkerComment extends MyCommand {
	public CommandTalkerComment(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	public enum CommandsComment {
		NONE, SEND, READ, REMOVE, DEBUG;
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
				if(args.length == 3){
					commandSend();
				}
				break;
			case DEBUG:
				UtilitiesProgramming.printDebugCommentAll();
				break;
			default:
				break;
			}
		}
		printUsageComment();
	}
	private void commandRead() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Talker talker = DataManagerTalker.getTalker(DataManagerPlayer.getDataPlayer(player).select);
		player.sendMessage("Comments for the selected talker(ID: " + talker.id.toString() + ") are shown.");
		for(DataComment data: talker.mapComment.values()) {
			data.printInfo(player);
		}
	}
	private void commandSend() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Talker talker = DataManagerTalker.getTalker(DataManagerPlayer.getDataPlayer(player).select);
		DataComment comment = new DataComment();
		comment.sender = player.getName();
		comment.expression = args[2];
		comment.state = CommentState.NEW;
		talker.mapComment.put(comment.sender, comment);
	}
	private void printUsageComment() {
	}
}
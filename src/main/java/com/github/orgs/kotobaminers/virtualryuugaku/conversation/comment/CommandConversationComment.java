package com.github.orgs.kotobaminers.virtualryuugaku.conversation.comment;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerCommandUsage;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerCommandUsage.Usage;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.comment.DataComment.CommentState;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMulti;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation0.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandConversationComment extends MyCommand {
	public CommandConversationComment(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	public enum CommandsComment {
		NONE, SEND, READ, REMOVE, NEW, DONE;
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
			case NEW:
				commandNew();
				break;
			case DONE:
				commandDone();
				break;
			default:
				break;
			}
		}
		printUsageComment();
	}
	private void commandDone() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(2 < args.length) {
			String name = args[2];
			ConversationMulti talker = DataManagerConversation.getConversation(DataManagerPlayer.getDataPlayer(player).select);
			if(talker.canEdit(player.getName())) {
				MessengerGeneral.print(player, Message.CANT_EDIT_TALKER_0, null);
			}
			if(talker.mapComment.containsKey(name)) {
				DataComment data = talker.mapComment.get(name);
				data.state = CommentState.DONE;
				String[] opts = {data.sender};
				MessengerGeneral.print(player, Message.DONE_COMMENT_1, opts);
				return;
			}
		}
		MessengerCommandUsage.print(player, Usage.TALKER_COMMENT_DONE_0, null);
	}
	private void commandNew() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		CommentHandler.printCommentNew(player);
	}
	private void commandRead() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		ConversationMulti talker = DataManagerConversation.getConversation(DataManagerPlayer.getDataPlayer(player).select);
//		String[] opts = {talker.name, talker.id.toString()};
//		MessengerGeneral.print(player, Message.SHOW_COMMENT_2, opts);
		Integer count = 0;
		for(DataComment data: talker.mapComment.values()) {
			MessengerGeneral.printReadComment(player, data, ++count);
		}
		if(count < 1) {
			MessengerGeneral.print(player, Message.NO_COMMENT_0, null);
		} else {
			MessengerCommandUsage.print(player, Usage.TALKER_COMMENT_DONE_0, null);
		}
	}
	private void commandSend() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(2 < args.length){
			ConversationMulti talker = DataManagerConversation.getConversation(DataManagerPlayer.getDataPlayer(player).select);
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
			MessengerGeneral.print(player, Message.SENT_COMMENT_0, null);
		}
	}
	private void printUsageComment() {
	}
}
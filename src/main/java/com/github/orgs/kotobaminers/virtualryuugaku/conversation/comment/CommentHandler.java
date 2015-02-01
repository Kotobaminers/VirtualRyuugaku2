package com.github.orgs.kotobaminers.virtualryuugaku.conversation.comment;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerCommandUsage;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommentHandler {
	public static void printCommentNew(Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Boolean existsNew = false;
		for (Conversation talker : DataManagerConversation.getMapConversation().values()) {
			if (talker.editor.contains(player.getName())) {
				List<String> listSenders = new ArrayList<String>();
				Integer count = 0;
				for (DataComment comment : talker.mapComment.values()) {
					if(comment.state.equals(DataComment.CommentState.NEW) && comment.isValid()) {
						listSenders.add(comment.sender);
						count++;
					}
				}
				if(0 < listSenders.size()) {
					existsNew = true;
					String senders = UtilitiesGeneral.joinStrings(listSenders, ", ");
//					String[] opts = {count.toString(), talker.name, talker.id.toString(), senders};
//					MessengerGeneral.print(player, Message.NEW_COMMENT_4, opts);
				}
			}
		}
		if(existsNew) {
			MessengerCommandUsage.print(player, MessengerCommandUsage.Usage.TALKER_SELECT_0, null);
			MessengerCommandUsage.print(player, MessengerCommandUsage.Usage.TALKER_COMMENT_READ_0, null);
		} else {
			MessengerGeneral.print(player, Message.NO_NEW_COMMENT_0, null);
		}
	}
}
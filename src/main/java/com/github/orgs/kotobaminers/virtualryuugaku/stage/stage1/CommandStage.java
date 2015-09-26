package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message0;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandStage extends MyCommand {
	public CommandStage(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	private enum Commands {
		NONE, INFO;
		private static Commands lookup(String name) {
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
		if(1 < args.length) {
			Commands commands = Commands.lookup(args[1]);
			switch(commands) {
			case INFO:
				commandInfo();
				break;
			case NONE:
				break;
			default:
				break;
			}
		}
	}

	private void commandInfo() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(2 < args.length) {
			String stage = args[2];
			Integer npcs = 0;
			Integer done = 0;
			Integer questions = 0;
			Integer keySentence = 0;
			Integer sentence = 0;
			for(Conversation conversation : ControllerConversation.getConversations()) {
				if(stage.equalsIgnoreCase(conversation.stage)) {
					if(conversation.hasValidQuestion()) {
						questions++;
					}

					List<List<Integer>> keyDone = DataManagerPlayer.getDataPlayer(player).questionDone;
					if(keyDone.contains(conversation.question.getKey())) {
						done++;
					}

					List<Integer> ids = new ArrayList<Integer>();
					for(Talk talk : conversation.listTalk) {
						Integer id = talk.id;
						if(!ids.contains(id)) {
							ids.add(id);
						}
						if(talk.key) {
							keySentence++;
						}
					}
					npcs += ids.size();
					sentence += conversation.listTalk.size();
				}
			}
			String[] opts = {stage, npcs.toString(), sentence.toString(), done.toString(), questions.toString(), keySentence.toString()};
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.STAGE_INFO_6, opts));
		}
	}
}

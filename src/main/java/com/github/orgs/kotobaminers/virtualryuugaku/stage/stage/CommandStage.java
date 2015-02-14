package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataManagerCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Broadcast;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.DataManagerPlugin;

public class CommandStage extends MyCommand {
	public CommandStage(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	private enum Commands {
		NONE, TEST, LEARN, PRACTICE, INFO, TP;
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
		if(0 < args.length) {
			Commands commands = Commands.lookup(args[0]);
			switch(commands) {
			case NONE:
				break;
			case INFO:
				break;
			case TEST:
				commandTest();
				break;
			case TP:
				commandTP();
				break;
			case PRACTICE:
				commandPractice();
				break;
			case LEARN:
				commandLearn();
				break;
			default:
				break;
			}
		}
	}

	private void commandLearn() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(1< args.length) {
			String stage = args[1];
			String name = player.getName();
			LocalStageHandler.loadNewGame(name, stage);
			LocalStageHandler.addPlayers(name, player);
			if(!LocalStageHandler.isValidGame(name)) {
				String[] opts = {stage};
				MessengerGeneral.print(player, Message.GAME_STAGE_INVALID_1, opts);
				return;
			}
			String[] opts = {stage.toUpperCase()};
			MessengerGeneral.broadcast(Broadcast.GAME_STAGE_START_1, opts);
			List<String> strings = new ArrayList<String>();
			for(Player player : LocalStageHandler.getGame(name).getPlayers()) {
				strings.add(player.getName());
			}
			String[] opts2 = {UtilitiesGeneral.joinStrings(strings, ChatColor.RESET + ", ")};
			MessengerGeneral.broadcast(Broadcast.STAGE_PLAYERS_1, opts2);
			LocalStageHandler.runNext(name);
		}
	}

	private void commandPractice() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(1< args.length) {
			String stage = args[1];
			String name = player.getName();
			PrivateStageHandler.loadNewGame(name, stage);
			if(!PrivateStageHandler.isValidGame(name)) {
				String[] opts = {stage};
				MessengerGeneral.print(player, Message.GAME_STAGE_INVALID_1, opts);
				return;
			}
			String[] opts = {stage.toUpperCase()};
			MessengerGeneral.broadcast(Broadcast.GAME_STAGE_START_1, opts);
			PrivateStageHandler.runNext(name);
		}
	}

	private void commandTP() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(1 < args.length) {
			String stage = args[1];
			for(Conversation conversation : DataManagerConversation.getMapConversation().values()) {
				if(conversation.stage.equalsIgnoreCase(stage)) {
					for(Talk talk : conversation.listTalk) {
						Integer id = talk.id;
						if(DataManagerCitizens.getMapDataCitizens().containsKey(id)) {
							DataCitizens data = DataManagerCitizens.getDataCitizens(id);
							player.teleport(data.location);
							MessengerGeneral.print(player, Message.TELEPORT_0, null);
							return;
						} else {
							UtilitiesProgramming.printDebugMessage("Invalid ID: " + id, new Exception());
						}
					}
				}
			}
		}

	}

	private void commandTest() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(1< args.length) {
			if(GlobalStageGameHandler.running) {
				MessengerGeneral.print(player, Message.GAME_STAGE_RUNNING_0, null);
				return;
			}
			String stage = args[1];
			GlobalStageGameHandler.loadNewGame(stage);
			if(!GlobalStageGameHandler.getGame().isValid()) {
				String[] opts = {stage};
				MessengerGeneral.print(player, Message.GAME_STAGE_INVALID_1, opts);
				return;
			}

			String[] opts = {stage.toUpperCase()};
			MessengerGeneral.broadcast(Broadcast.GAME_STAGE_START_1, opts);
			GlobalStageGameHandler.running = true;
			GlobalStageGameHandler.getGame().runTaskTimer(DataManagerPlugin.plugin, GlobalStageGameHandler.ready, GlobalStageGameHandler.interval);
		}
	}
}
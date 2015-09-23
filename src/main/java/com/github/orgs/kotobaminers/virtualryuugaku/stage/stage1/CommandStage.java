package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.DataCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.citizens.citizens.ConfigCitizens;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMulti;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation0.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.stage.stage1.GameFindPeople.Mode;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandStage extends MyCommand {
	public CommandStage(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	private enum Commands {
		NONE, TEST, LEARN, TRAINING, T, INFO, TP, FIND, FINDPPL, FINDPEOPLE, FP, HINT, LIST;
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
			case NONE:
				break;
			case TP:
				commandTP();
				break;
			case INFO:
				commandInfo();
				break;
			case TEST:
				commandTest();
				break;
			case TRAINING:
			case T:
				commandTraining();
				break;
			case LEARN:
				commandLearn();
				break;
			case FIND:
			case FINDPEOPLE:
			case FINDPPL:
			case FP:
				commandFindPeople();
				break;
			case HINT:
				commandHint();
				break;
			case LIST:
				commandList();
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
			for(ConversationMulti conversation : DataManagerConversation.getMapConversation().values()) {
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
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.STAGE_INFO_6, opts));
		}
	}

	private void commandList() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		List<String> stages = new ArrayList<String>();
		for(ConversationMulti conversation : DataManagerConversation.getMapConversation().values()) {
			String stage = conversation.stage;
			if(!stages.contains(stage)) {
				stages.add(stage);
			}
		}
		String[] opts = {UtilitiesGeneral.joinStrings(stages, ChatColor.GRAY + ", " + ChatColor.RESET)};
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.STAGE_LIST_1, opts));
	}

	private void commandHint() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(2 < args.length) {
			String stage = args[2];
			DataManagerConversation.printHint(player, stage);
		}
	}

	private void commandFindPeople() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(2 < args.length) {
			String stage = args[2];
			GameFindPeopleHandler.loadNewGameRandom(player, player.getName(), stage);
			if (3 < args.length) {
				String modeString = args[3];
				if(modeString.equalsIgnoreCase(Mode.EN.toString())) {
					GameFindPeopleHandler.setGameModeEN(player);
				} else if (modeString.equalsIgnoreCase(Mode.JP.toString())) {
					GameFindPeopleHandler.setGameModeJP(player);
				}
			}
			GameFindPeopleHandler.startGame(player);
		}
	}

	private void commandLearn() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
//		if(1< args.length) {
//			String stage = args[1];
//			String name = player.getName();
//			LocalStageHandler.loadNewGame(name, stage);
//			LocalStageHandler.addPlayers(name, player);
//			if(!LocalStageHandler.isValidGame(name)) {
//				String[] opts = {stage};
//				MessengerGeneral.print(player, Message.GAME_STAGE_INVALID_1, opts);
//				return;
//			}
//			String[] opts = {stage.toUpperCase()};
//			MessengerGeneral.broadcast(Broadcast.GAME_STAGE_START_1, opts);
//			List<String> strings = new ArrayList<String>();
//			for(Player player : LocalStageHandler.getGame(name).getPlayers()) {
//				strings.add(player.getName());
//			}
//			String[] opts2 = {UtilitiesGeneral.joinStrings(strings, ChatColor.RESET + ", ")};
//			MessengerGeneral.broadcast(Broadcast.STAGE_PLAYERS_1, opts2);
//			LocalStageHandler.runNext(name);
//		}
	}

	private void commandTraining() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(2 < args.length) {
			String stage = args[2];
			String name = player.getName();
			if(!PracticeStageHandler.existsStage(stage)) {
				String[] opts = {stage};
				MessengerGeneral.print(player, Message.STAGE_INVALID_1, opts);
				return;
			}
			UtilitiesProgramming.printDebugMessage("", new Exception());
			PracticeStageHandler practice = new PracticeStageHandler();
			practice.loadNewGame(name, stage);
//			String[] opts = {stage.toUpperCase()};
//			MessengerGeneral.broadcast(Broadcast.GAME_STAGE_START_1, opts);
			UtilitiesProgramming.printDebugMessage("", new Exception());
			practice.runNext(player);
		}
	}

	private void commandTest() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
//		if(1< args.length) {
//			if(GlobalStageGameHandler.running) {
//				MessengerGeneral.print(player, Message.GAME_STAGE_RUNNING_0, null);
//				return;
//			}
//			String stage = args[1];
//			GlobalStageGameHandler.loadNewGame(stage);
//			if(!GlobalStageGameHandler.getGame().isValid()) {
//				String[] opts = {stage};
//				MessengerGeneral.print(player, Message.GAME_STAGE_INVALID_1, opts);
//				return;
//			}
//
//			String[] opts = {stage.toUpperCase()};
//			MessengerGeneral.broadcast(Broadcast.GAME_STAGE_START_1, opts);
//			GlobalStageGameHandler.running = true;
//			GlobalStageGameHandler.getGame().runTaskTimer(DataManagerPlugin.plugin, GlobalStageGameHandler.ready, GlobalStageGameHandler.interval);
//		}
	}

	private void commandTP() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(2 < args.length) {
			String stage = args[3];
			for(ConversationMulti conversation : DataManagerConversation.getMapConversation().values()) {
				if(conversation.stage.equalsIgnoreCase(stage)) {
					for(Talk talk : conversation.listTalk) {
						Integer id = talk.id;
						if(ConfigCitizens.getMapDataCitizens().containsKey(id)) {
							DataCitizens data = ConfigCitizens.getDataCitizens(id);
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
}

package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Commands;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message0;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.game.game.ControllerGameGlobal;
import com.github.orgs.kotobaminers.virtualryuugaku.myself.myself.ConversationBook;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.NPCHandler;

public class CommandPerformer {
	public CommandSender sender;
	public Commands command;
	public List<String> params;
	public CommandPerformer(CommandSender sender, Commands command, String label, String[] args) {
		this.sender = sender;
		this.command = command;

		List<String> list = new LinkedList<String>();
		list.add(label);
		list.addAll(Arrays.asList(args));
		int i = 0;
		while(i < command.getPath().size()) {
			list.remove(0);
			i++;
		}
		this.params = list;
	}

	private Player getPlayer() throws Exception {
		if (sender instanceof Player) {
			return (Player) sender;
		} else {
			throw new Exception("The command sender must be a player");
		}
	}

	private ConsoleCommandSender getConsole() throws Exception {
		if (sender instanceof ConsoleCommandSender) {
			return (ConsoleCommandSender) sender;
		} else {
			throw new Exception("The command sender must be a console");
		}
	}

	private void printInvalidParams(Player player) {
		String[] opts = {UtilitiesGeneral.joinStrings(params, ", ")};
		Message.COMMAND_INVALID_PARAMS_1.print(player, opts);
		command.printUsage(player);
	}

	public void commandTP() {
		Player player;
		try {
			player = getPlayer();
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		if(0 < params.size()) {
			String stage = params.get(0).toUpperCase();
			try {
				List<Conversation> conversations = ControllerConversation.getConversations(stage);
				if (0< conversations.size()) {
					Conversation conversation = conversations.get(0);
					if (0 < conversation.listTalk.size()) {
						Integer id = conversation.getIDSorted().get(0);
						NPC npc = NPCHandler.getNPC(id);
						Location location = npc.getStoredLocation();
						player.teleport(location);
						String[] opts = {stage};
						Message.STAGE_TP_1.print(player, opts);
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		printInvalidParams(player);
	}

	public void commandGameStart() {
		Player player;
		try {
			player = getPlayer();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		if (0 < params.size()) {
			String stage = params.get(0);
				ControllerGameGlobal.loadGame(stage);
				ControllerGameGlobal.giveNextQuestion(player);
		} else {
			printInvalidParams(player);
		}
	}

	public void commandGameFinish() {
		if(ControllerGameGlobal.isValidGame()) {
			ControllerGameGlobal.finishGame();
		} else {
			try {
				MessengerGeneral.print(getPlayer(), MessengerGeneral.getMessage(Message0.GAME_PLEASE_LOAD_0, null));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void commandGameNext() {
		try {
			Player player = getPlayer();
			if(ControllerGameGlobal.isValidGame()) {
				ControllerGameGlobal.giveNextQuestion(player);
			} else {
				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.GAME_PLEASE_LOAD_0, null));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void commandGameRule() {
		Player player;
		try {
			player = getPlayer();
			ControllerGameGlobal.printRule(player);
			Commands.ANSWER_GAME.printUsage(player);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public void commandInfo() {
		Player player;
		try {
			player = getPlayer();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		if(0 < params.size()) {
			String stage = params.get(0).toUpperCase();
			Integer npcs = 0;
			Integer done = 0;
			Integer questions = 0;
			Integer keySentence = 0;
			Integer sentence = 0;
			for(Conversation conversation : ControllerConversation.getConversations()) {
				if(stage.equalsIgnoreCase(conversation.stageName)) {
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
			if (0 < npcs && 0 < sentence) {
				String[] opts = {stage, npcs.toString(), sentence.toString(), done.toString(), questions.toString(), keySentence.toString()};
				Message.STAGE_INFO_6.print(player, opts);
			} else {
				printInvalidParams(player);
			}
		} else {
			printInvalidParams(player);
		}
	}

	public void commandList() {
		String course = UtilitiesGeneral.joinStrings(ControllerConversation.getStagesCourse(), ", ");
		String myself = UtilitiesGeneral.joinStrings(ControllerConversation.getStagesMyself(), ", ");
		String[] opts = {course, myself};
		try {
			Message.STAGE_LIST_2.print(getPlayer(), opts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void commandMyselfBook() {
		Player player;
		try {
			player = getPlayer();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		if (0 < params.size()) {
			String stage = params.get(0);
			if (ControllerConversation.existsMyselfStage(stage)) {
				ConversationBook.giveConversationBookEmpty(player, stage);
			}
		}
		printInvalidParams(player);
	}

	public void commandMyselfUpdate() {
		Player player;
		try {
			player = getPlayer();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		try {
			ControllerConversation.importBook(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void commandReload() {
		UtilitiesProgramming.printDebugMessage("", new Exception());




//		if(2 < args.length) {
//			StageMyself stage = null;
//			String name = args[2].toUpperCase();
//			CheckState state = CheckState.RECOMMENDED;
//			if (3 < args.length) {
//				String stateString = args[3];
//				for (CheckState search : CheckState.values()) {
//					if (search.toString().equalsIgnoreCase(stateString)) {
//						state = CheckState.valueOf(stateString.toUpperCase());
//						break;
//					}
//				}
//			}
//
//			try {
//				stage = StageMyself.createStageMyself(name);
//				stage.changeNPC(state);
//				String[] opts = {name, state.toString()};
//				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.MYSELF_STAGE_RELOAD_2, opts));
//				Effects.playSound(player, Scene.NOTICE);
//				return;
//			} catch (Exception e) {
//				e.printStackTrace();
//				String[] opts = {name};
//				UtilitiesProgramming.printDebugMessage("Invalid Stage Name: " + name, new Exception());
//				MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.COMMON_INVALID_PARAMETER_1, opts));
//				Effects.playSound(player, Scene.BAD);
//				return;
//			}
//		} else {
//			UtilitiesProgramming.printDebugMessage("/vrg myself random <STAGE>", new Exception());//TODO
//		}
	}


	public void commandAnswerGame() {
		Player player;
		try {
			player = getPlayer();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		String answer = "";
		if (0 < params.size()) {
			answer = UtilitiesGeneral.joinStrings(params, " ");
		} else {
			printInvalidParams(player);
			return;
		}
		if(ControllerGameGlobal.isValidGame()) {
			ControllerGameGlobal.validataAnswer(player, answer);
			ControllerGameGlobal.updataScoreboard(player);
		} else {
			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.GAME_PLEASE_LOAD_0, null));
		}
	}

}
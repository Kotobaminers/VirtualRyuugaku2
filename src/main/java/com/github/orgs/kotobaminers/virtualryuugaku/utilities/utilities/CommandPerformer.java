package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;

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

	public Player getPlayer() throws Exception {
		if (sender instanceof Player) {
			return (Player) sender;
		} else {
			throw new Exception("The command sender must be a player");
		}
	}

	public ConsoleCommandSender getConsole() throws Exception {
		if (sender instanceof ConsoleCommandSender) {
			return (ConsoleCommandSender) sender;
		} else {
			throw new Exception("The command sender must be a console");
		}
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
			command.printUsage(player);
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
			if (0 < npcs && 0 < sentence) {
				String[] opts = {stage, npcs.toString(), sentence.toString(), done.toString(), questions.toString(), keySentence.toString()};
				Message.STAGE_INFO_6.print(player, opts);
			} else {
				String[] opts = {UtilitiesGeneral.joinStrings(params, ", ")};
				Message.COMMAND_INVALID_PARAMS_1.print(player, opts);
				command.printUsage(player);
			}
		} else {
			command.printUsage(player);
		}
	}

	public void commandList() {
		String[] opts = {UtilitiesGeneral.joinStrings(ControllerConversation.getStages(), ", ")};
		try {
			Message.STAGE_LIST_1.print(getPlayer(), opts);
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
			ConversationBook.giveConversationBookEmpty(player, stage);
		} else {
			String[] opts = {UtilitiesGeneral.joinStrings(params, ", ")};
			Message.COMMAND_INVALID_PARAMS_1.print(player, opts);
		}
	}

	private void commandMyselfUpdate() {
		ConversationBook book = null;
		Player player;
		try {
			player = getPlayer();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		try {
			book = ConversationBook.createConversatinBook(player);
			if(book.isMine()) {
				ControllerConversation.importBook(book);
				Message.BOOK_IMPORTED_0.print(player, null);
				Effects.playSound(player, Scene.GOOD);
			} else {
				Message.BOOK_NOT_YOURS_0.print(player, null);
				Effects.playSound(player, Scene.BAD);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
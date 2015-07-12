package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerCommandUsage;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerCommandUsage.Usage;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.comment.CommandConversationComment;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandConversation extends MyCommand {
	public CommandConversation(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	private enum CommandsConversation {
		NONE, ANSWER, SENTENCE, COMMENT, REMOVE, LANGUAGE, LANG, INFO, STAGE, SELECT;
		private static CommandsConversation lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return CommandsConversation.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return CommandsConversation.NONE;
			}
		}
	}
	@Override
	public void runCommand() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(0 < args.length) {
			CommandsConversation commands = CommandsConversation.lookup(args[0]);
			switch(commands) {
			case NONE:
				break;
			case COMMENT:
				new CommandConversationComment(player, command, args).runCommand();
				break;
			case ANSWER:
				commandAnswer();
				break;
			case SENTENCE:
				commandSentence();
				break;
			case REMOVE:
				commandRemove();
				break;
			case LANGUAGE:
			case LANG:
//				commandLanguage();
				break;
			case INFO:
				commandInfo();
				break;
			case STAGE:
				commandStage();
				break;
			case SELECT:
				commandSelect();
				break;
			default:
				break;
			}
		}
	}
	private void commandSelect() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(args.length == 2) {
			Integer id = 0;
			try {
				id = Integer.parseInt(args[1]);
			} catch (Exception e) {
				MessengerCommandUsage.print(player, Usage.TALKER_SELECT_0, null);
				return;
			}
			DataManagerPlayer.selectTalker(player, id);
		} else {
			MessengerCommandUsage.print(player, Usage.TALKER_SELECT_0, null);
		}
	}
	private void commandStage() {
		if(args.length == 3) {
			String stage = args[2];
			Conversation talker = DataManagerConversation.getConversation(DataManagerPlayer.getDataPlayer(player).select);
			DataManagerConversation.getConversation(DataManagerPlayer.getDataPlayer(player).select).printInformation(player);
			if(talker.canEdit(player.getName())) {
				talker.stage = stage;
				DataManagerConversation.getConversation(DataManagerPlayer.getDataPlayer(player).select).printInformation(player);
				String[] args = {""};
				MessengerGeneral.print(player, Message.EDITED_TALKER_0, args);
			} else {
				String[] args = {""};
				MessengerGeneral.print(player, Message.CANT_EDIT_TALKER_0, args);
			}
		}

	}
	private void commandInfo() {
		Conversation talker = DataManagerConversation.getConversation(DataManagerPlayer.getDataPlayer(player).select);
		talker.printInformation(player);
	}
	private void commandRemove() {
		if(2 == args.length) {
			Integer line = 0;
			try {
				line = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				return;
			}
			Conversation talker = DataManagerConversation.getConversation(DataManagerPlayer.getDataPlayer(player).select);
			if(line <= talker.listTalk.size()) {
				talker.listTalk.remove(line - 1);
				talker.printInformation(player);
				String[] settings = {""};
				MessengerGeneral.print(player, Message.EDITED_TALKER_0, settings);
				return;
			}
		}
	}
	private void commandSentence() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
//		if(3 < args.length) {
//			Integer line = 0;
//			try {
//				line = Integer.parseInt(args[1]);
//			} catch(NumberFormatException e) {
////				printUsageSentence();
//				return;
//			}
//			Expression expression = Expression.lookup(args[2]);
//			if(0 < line && line < 11 && !expression.equals(Expression.NONE)) {
//				Conversation talker = DataManagerConversation.getTalker(DataManagerPlayer.getDataPlayer(player).select);
//				if(!talker.canEdit(player.getName())) {
//					String[] settings = {""};
//					MessengerGeneral.print(player, Message.CANT_EDIT_TALKER_0, settings);
//					return;
//				}
//				Integer sizeSentence = talker.listTalk.size();
//				if(sizeSentence < line) {
//					for(Integer i = 0; i < line - sizeSentence; i++) {
//						talker.listTalk.add(new Description());
//					}
//				}
//				Description sentence = talker.listTalk.get(line - 1).description;
//				List<String> strings = new ArrayList<String>();
//				for(int i = 3; i < args.length; i++) {
//					strings.add(args[i]);
//				}
//				String input = UtilitiesGeneral.joinStrings(strings, " ");
//				switch(expression) {//Romaji will be automatically created by kana.
//				case EN:
//					sentence.en = Arrays.asList(input);
//					break;
//				case KANA:
//					sentence.kana = Arrays.asList(input);
//					sentence.romaji = Arrays.asList(UtilitiesGeneral.toRomaji(input));
//					break;
//				case KANJI:
//					sentence.kanji = Arrays.asList(input);
//					break;
//				case NONE:
//					break;
//				case ROMAJI:
//					break;
//				default:
//					break;
//				}
//				String[] msg = {""};
//				MessengerGeneral.print(player, Message.EDITED_TALKER_0, msg);
//				talker.printInformation(player);
//				return;
//			}
//		}
//		printUsageSentence();
	}
	private void commandAnswer() {
//		UtilitiesProgramming.printDebugMessage("", new Exception());
//		String answer = "";
//		if(1 < args.length) {
//			List<String> list = new ArrayList<String>();
//			for(Integer i = 1; i < args.length; i++) {
//				list.add(args[i]);
//			}
//			String [] array = list.toArray(new String[list.size()]);
//			answer = UtilitiesGeneral.joinStringsWithSpace(array);
//		} else {
//			return;
//		}
//		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
//		Integer id = data.select;
//		Talker talker = DataManagerTalker.getTalker(id);
//		if(!talker.hasAnswerEn() || !talker.hasAnswerJp()) return;
//		Boolean valid = false;
//		switch(data.language) {
//		case EN:
//			valid = talker.answer.validEn(answer);
//			break;
//		case JP:
//			valid = talker.answer.validJp(answer);
//			break;
//		default:
//			break;
//		}
//		if(valid) {
//			player.sendMessage("Correct");
//			DataManagerPlayer.addDone(player, id);
//			DataManagerOldStage.loadStageById(id).printInformation(player);
//		} else {
//			player.sendMessage("Wrong");
//		}
	}
}
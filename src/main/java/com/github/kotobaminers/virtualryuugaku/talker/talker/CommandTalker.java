package com.github.kotobaminers.virtualryuugaku.talker.talker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.kotobaminers.virtualryuugaku.common.common.Messenger;
import com.github.kotobaminers.virtualryuugaku.common.common.Description.Expression;
import com.github.kotobaminers.virtualryuugaku.common.common.Messenger.Message;
import com.github.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.kotobaminers.virtualryuugaku.player.player.DataPlayer.Language;
import com.github.kotobaminers.virtualryuugaku.talker.comment.CommandTalkerComment;
import com.github.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.kotobaminers.virtualryuugaku.virtualryuugaku.CommandExecutorPlugin.Commands;

public class CommandTalker extends MyCommand {
	public CommandTalker(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	public enum CommandsTalker {
		NONE, ANSWER, SENTENCE, COMMENT, REMOVE, LANGUAGE, INFO, STAGE, SELECT;
		public static CommandsTalker lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return CommandsTalker.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return CommandsTalker.NONE;
			}
		}
	}
	@Override
	public void runCommand() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(0 < args.length) {
			CommandsTalker commands = CommandsTalker.lookup(args[0]);
			switch(commands) {
			case NONE:
				break;
			case COMMENT:
				new CommandTalkerComment(player, command, args).runCommand();
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
				commandLanguage();
				break;
			case INFO:
				commandInfo();
				break;
			case STAGE:
				commandStage();
			case SELECT:
				commandSelect();
			default:
				break;
			}
		}
	}
	private void commandSelect() {
		if(args.length == 3) {
			int id = Integer.parseInt(args[2]);
			Talker talker = DataManagerTalker.getTalker(DataManagerPlayer.getDataPlayer(player).select);
		}
	}
	private void commandStage() {
		if(args.length == 3) {
			String stage = args[2];
			Talker talker = DataManagerTalker.getTalker(DataManagerPlayer.getDataPlayer(player).select);
			DataManagerTalker.getTalker(DataManagerPlayer.getDataPlayer(player).select).printInformation(player);
			if(talker.canEdit(player.getName())) {
				talker.stage = stage;
				DataManagerTalker.getTalker(DataManagerPlayer.getDataPlayer(player).select).printInformation(player);
				String[] args = {""};
				Messenger.print(player, Message.EDITED_TALKER_0, args);
			} else {
				String[] args = {""};
				Messenger.print(player, Message.CANT_EDIT_TALKER_0, args);
			}
		}

	}
	private void commandInfo() {
		Talker talker = DataManagerTalker.getTalker(DataManagerPlayer.getDataPlayer(player).select);
		talker.printInformation(player);
	}
	private void commandRemove() {
		if(2 == args.length) {
			Integer line = 0;
			try {
				line = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				printUsageRemove();
				return;
			}
			Talker talker = DataManagerTalker.getTalker(DataManagerPlayer.getDataPlayer(player).select);
			if(line <= talker.listSentence.size()) {
				talker.listSentence.remove(line - 1);
				DataManagerTalker.registerTalker(talker);
				talker.printInformation(player);
				String[] settings = {""};
				Messenger.print(player, Message.EDITED_TALKER_0, settings);
				return;
			}
		}
		printUsageRemove();
	}
	private void printUsageRemove() {
		player.sendMessage("/" + Commands.TALKER.toString().toLowerCase() + " " + CommandsTalker.REMOVE.toString().toLowerCase() + " <LINE(1-10)>");
	}
	private void commandSentence() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(3 < args.length) {
			Integer line = 0;
			try {
				line = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				printUsageSentence();
				return;
			}
			Expression expression = Expression.lookup(args[2]);
			if(0 < line && line < 11 && !expression.equals(Expression.NONE)) {
				Talker talker = DataManagerTalker.getTalker(DataManagerPlayer.getDataPlayer(player).select);
				if(!talker.canEdit(player.getName())) {
					String[] settings = {""};
					Messenger.print(player, Message.CANT_EDIT_TALKER_0, settings);
					return;
				}
				Integer sizeSentence = talker.listSentence.size();
				if(sizeSentence < line) {
					for(Integer i = 0; i < line - sizeSentence; i++) {
						talker.listSentence.add(new Description());
					}
				}
				Description sentence = talker.listSentence.get(line - 1);
				List<String> strings = new ArrayList<String>();
				for(int i = 3; i < args.length; i++) {
					strings.add(args[i]);
				}
				String input = UtilitiesGeneral.joinStrings(strings, " ");
				switch(expression) {
				case EN:
					sentence.en = Arrays.asList(input);
					break;
				case KANA:
					sentence.kana = Arrays.asList(input);
					sentence.romaji = Arrays.asList(UtilitiesGeneral.toRomaji(input));
					break;
				case KANJI:
					sentence.kanji = Arrays.asList(input);
					break;
				default:
					break;
				}
				DataManagerTalker.registerTalker(talker);//TODO check this is needed.
				String[] msg = {""};
				Messenger.print(player, Message.EDITED_TALKER_0, msg);
				talker.printInformation(player);
				return;
			}
		}
		printUsageSentence();
	}
	private void printUsageSentence() {
		player.sendMessage("/" + Commands.TALKER.toString().toLowerCase() + " " + CommandsTalker.SENTENCE.toString().toLowerCase() + " <LINE(1-10)> <EN/KANJI/KANA> <SENTENCE>");
	}
	private void commandLanguage() {
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		switch(data.language) {
		case EN:
			data.language = Language.JP;
			break;
		case JP:
			data.language = Language.EN;
			break;
		default:
			data.language = Language.JP;
			break;
		}
		DataManagerPlayer.putDataPlayer(data);
		String[] settings = {data.language.toString()};
		Messenger.print(player, Message.SET_LANGUAGE_1, settings);
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
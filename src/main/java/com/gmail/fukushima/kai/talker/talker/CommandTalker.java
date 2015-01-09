package com.gmail.fukushima.kai.talker.talker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.common.common.Description.Expression;
import com.gmail.fukushima.kai.common.common.Sentence;
import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer.Language;
import com.gmail.fukushima.kai.stage.stage.DataManagerStage;
import com.gmail.fukushima.kai.utilities.utilities.MyCommand;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesGeneral;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;
import com.gmail.fukushima.kai.virtualryuugaku2.virtualryuugaku2.CommandExecutorPlugin.Commands;

public class CommandTalker extends MyCommand {
	public CommandTalker(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	public enum CommandsTalker {
		NONE, ANSWER, SENTENCE, COMMENT, REMOVE, LANGUAGE, INFO;
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
			default:
				break;
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
				player.sendMessage("The talker is edited.");
				DataManagerTalker.putTalker(talker);
				talker.printInformation(player);
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
		if(4 == args.length) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
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
				UtilitiesProgramming.printDebugMessage(player.getName() + talker.owner, new Exception());
				if(!talker.isYours(player.getName())) {
					player.sendMessage("This is not your talker.");
					return;
				}
				UtilitiesProgramming.printDebugMessage("", new Exception());
				Integer sizeSentence = talker.listSentence.size();
				UtilitiesProgramming.printDebugMessage(""  + sizeSentence + " " + line , new Exception());
				if(sizeSentence < line) {
					UtilitiesProgramming.printDebugMessage("", new Exception());
					for(Integer i = 0; i < line - sizeSentence; i++) {
						UtilitiesProgramming.printDebugMessage(""  + sizeSentence + " " + line  + " " + i , new Exception());
						talker.listSentence.add(new Sentence());
						Integer numberSentence = sizeSentence + i + 1;
						player.sendMessage("Added sentence(" + numberSentence +")");
					}
				}
				UtilitiesProgramming.printDebugMessage("", new Exception());
				Sentence sentence = talker.listSentence.get(line - 1);
				List<String> input = Arrays.asList(args[3]);
				UtilitiesProgramming.printDebugMessage("", new Exception());
				switch(expression) {
				case EN:
					sentence.en = input;
					break;
				case KANA:
					sentence.kana = input;
					sentence.romaji = Arrays.asList(UtilitiesGeneral.toRomaji(input.get(0)));
					break;
				case KANJI:
					sentence.kanji = input;
					break;
				default:
					break;
				}
				UtilitiesProgramming.printDebugMessage("", new Exception());
				player.sendMessage("The talker is edited.");
				DataManagerTalker.putTalker(talker);
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
		Language language = data.language;
		switch(language) {
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
		player.sendMessage("You set your learning language " + data.language.toString());
	}
	private void commandAnswer() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String answer = "";
		if(1 < args.length) {
			List<String> list = new ArrayList<String>();
			for(Integer i = 1; i < args.length; i++) {
				list.add(args[i]);
			}
			String [] array = list.toArray(new String[list.size()]);
			answer = UtilitiesGeneral.joinStringsWithSpace(array);
		} else {
			return;
		}
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		Integer id = data.select;
		Talker talker = DataManagerTalker.getTalker(id);
		if(!talker.hasAnswerEn() || !talker.hasAnswerJp()) return;
		Boolean valid = false;
		switch(data.language) {
		case EN:
			valid = talker.answer.validEn(answer);
			break;
		case JP:
			valid = talker.answer.validJp(answer);
			break;
		default:
			break;
		}
		if(valid) {
			player.sendMessage("Correct");
			DataManagerPlayer.addDone(player, id);
			DataManagerStage.loadStageById(id).printInformation(player);
		} else {
			player.sendMessage("Wrong");
		}
	}
}
package com.gmail.fukushima.kai.mystage.talker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.mystage.mystage.DataManagerStage;
import com.gmail.fukushima.kai.player.player.DataManagerPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer;
import com.gmail.fukushima.kai.player.player.DataPlayer.Language;
import com.gmail.fukushima.kai.utilities.utilities.MyCommand;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesGeneral;
import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class CommandTalker extends MyCommand {
	public CommandTalker(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	public enum Commands {
		NONE, ANSWER, EDIT, ADD, REMOVE, LANGUAGE, DEBUG;
		public static Commands lookup(String name) {
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
			case ADD:
				break;
			case ANSWER:
				commandAnswer();
				break;
			case EDIT:
				break;
			case REMOVE:
				break;
			case LANGUAGE:
				commandLanguage();
				break;
			default:
				break;
			}

		}
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
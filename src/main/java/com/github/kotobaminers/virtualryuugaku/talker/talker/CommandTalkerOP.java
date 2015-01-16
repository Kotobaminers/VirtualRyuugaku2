package com.github.kotobaminers.virtualryuugaku.talker.talker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.github.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.kotobaminers.virtualryuugaku.virtualryuugaku.DataEventCreate;
import com.github.kotobaminers.virtualryuugaku.virtualryuugaku.Events;
import com.github.kotobaminers.virtualryuugaku.virtualryuugaku.Events.EventCreate;

public class CommandTalkerOP extends MyCommand {
	private static final String DEFAULT_NAME_EMPTY_TALKER = "&a@Empty_Talker";
	public CommandTalkerOP(Player player, Command command, String[] args) {
		super(player, command, args);
	}
	public enum Commands {
		NONE, DEBUG, CREATE;
		public static Commands lookup(String name) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			Commands commands = Commands.NONE;
			try {
				commands = Commands.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
			}
			UtilitiesProgramming.printDebugMessage(commands.toString(), new Exception());
			return commands;
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
			case DEBUG:
				commandDebug();
				break;
			case CREATE:
				commandCreate();
				break;
			default:
				break;
			}
		}
	}
	private void commandDebug() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		Talker talker = DataManagerTalker.getTalker(data.select);
		UtilitiesProgramming.printDebugTalker(talker);
	}
	private void commandCreate() {
		//TODO check if the stage(args[11]) exists.
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(1 < args.length) {
			String stage = args[1];
			DataEventCreate.nameStage = stage;
			DataEventCreate.player = player;
			List<String> list = new ArrayList<String>();
			list.add("npc");
			list.add(Commands.CREATE.toString());
			list.add(DEFAULT_NAME_EMPTY_TALKER);
			if(2 < args.length) {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				list.add("--type");
				list.add(args[2]);
			}
			String commandCreate = UtilitiesGeneral.joinStrings(list, " ");
			Events.flagEventCreate = EventCreate.REGISTER_EMPTY;//NPCCreateEvent flag. The flag will be initial
			Bukkit.getServer().dispatchCommand(player, commandCreate);//NPCCreateEvent will be triggered
		}
	}
}
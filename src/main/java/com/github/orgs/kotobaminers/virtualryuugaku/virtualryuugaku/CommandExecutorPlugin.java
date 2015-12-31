package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Commands;

public class CommandExecutorPlugin implements CommandExecutor {
	private CommandPerformer performer;

	public CommandExecutorPlugin(VirtualRyuugaku plugin) {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command rawCommand, String label, String[] args) {
		Debug.printDebugMessage("", new Exception());
		List<String> path = new ArrayList<String>();
		path.add(label);
		path.addAll(Arrays.asList(args));
		Commands command = Commands.getCommand(path);
		performer = new CommandPerformer(sender, command, label, args);

		Debug.printDebugMessage("", new Exception());
		if (!performer.canPerform()) {
			return true;
		}

		Debug.printDebugMessage("", new Exception());
		if (!performer.command.isRunnableChild()) {
			performer.command.printInfo(sender);
			return true;
		}

		Debug.printDebugMessage("", new Exception());
		boolean success = performer.performCommand();//The actual line to perform the command.
		Debug.printDebugMessage("", new Exception());
		if (success == false) {
			performer.printInvalidParams();
		}
		return true;
	}
}
package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Commands;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.CommandConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.CommandConversationOP;
import com.github.orgs.kotobaminers.virtualryuugaku.stage.stage1.CommandStageOP;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.MyCommand;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class CommandExecutorPlugin implements CommandExecutor {
	public CommandExecutorPlugin(VirtualRyuugaku plugin) {
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		Commands commands = Commands.lookup(label);
		if(commands.equals(Commands.VRGDBG)) {
			if(sender instanceof Player) {
				if(!sender.isOp()) return false;
			}
			new CommandVirturalRyuugakuDebug().printDebug(args);
			return true;
		}
		if(sender instanceof Player) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			Player player = (Player) sender;
			MyCommand myCommand = null;
			switch(commands) {
			case CONVERSATION:
			case CONV:
				myCommand = new CommandConversation(player, command, args);
				break;
			case CONVERSATIONOP:
			case CONVOP:
				myCommand = new CommandConversationOP(player, command, args);
				break;
			case VIRTUALRYUUGAKU:
			case VRG:
				myCommand = new CommandVirtualRyuugaku(player, command, args);
				break;
			case VIRTUALRYUUGAKUOP:
			case VRGOP:
				myCommand = new CommandVirtualRyuugakuOP(player, command, args);
				break;
			case STAGEOP:
				myCommand = new CommandStageOP(player, command, args);
				break;
			case ANSWER:
			case ANS:
				myCommand = new CommandAnswer(player, command, args);
				break;
			case VRGDBG:
			//VRGDBG should be placed the upper section.
			default:
				return false;
			}
			myCommand.runCommand();
			return true;
		}
		return false;
	}
}
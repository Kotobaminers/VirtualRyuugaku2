package com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.CommandEmpty;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Commands;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.CommandConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.CommandConversationOP;
import com.github.orgs.kotobaminers.virtualryuugaku.stage.stage.CommandStage;
import com.github.orgs.kotobaminers.virtualryuugaku.stage.stage.CommandStageOP;
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
			MyCommand myCommand = new CommandEmpty(player, command, args);
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
				player.sendMessage(DataManagerPlugin.plugin.getName() + " developped by kai_f");
				break;
			case VIRTUALRYUUGAKUOP:
			case VRGOP:
				myCommand = new CommandVirtualRyuugakuOP(player, command, args);
				break;
			case STAGE:
				myCommand = new CommandStage(player, command, args);
				break;
			case STAGEOP:
				myCommand = new CommandStageOP(player, command, args);
				break;
			//VRGDBG should be placed the upper section.
			default:
				break;
			}
			myCommand.runCommand();
			return true;
		}
		return false;
	}
}
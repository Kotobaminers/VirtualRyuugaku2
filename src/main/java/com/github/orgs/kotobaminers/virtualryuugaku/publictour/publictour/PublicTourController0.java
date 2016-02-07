package com.github.orgs.kotobaminers.virtualryuugaku.publictour.publictour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Stage;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.StageController;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;

import net.citizensnpcs.api.npc.NPC;

public class PublicTourController0 {
	public static Stage stage = new Stage();
	public static Integer index = 0;
	public static Set<UUID> join = new HashSet<UUID>();

	public static void join(Player player) {
		Debug.printDebugMessage("",  new Exception());
		if (!hasStageLoaded(player)) {
			return;
		}
		if(hasJoined(player)) {
			String[] opts = {"You have already joined the tour."};
			Message.VRG_1.print(player, opts);
			return;
		}
		try {
			goToCurrent(player, getCurrentConversation(), getCurrentNPC());
			join.add(player.getUniqueId());
			Message.TOUR_JOIN_TP_2.broadcast(Arrays.asList(player.getName(), stage.name));
			broadcastJoining();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadTour(String name) {
		Debug.printDebugMessage("",  new Exception());
		initialize();
		stage = StageController.getStage(name);
	}

	private static boolean hasStageLoaded(Player player) {
		if(0 < stage.name.length()) {
			return true;
		}
		String[] opts = {"No stage has been loaded. [/vrg tour start <STAGE>]"};
		Message.VRG_1.print(player, opts);
		return false;
	}

	private static boolean hasJoined(Player player) {
		if (join.contains(player.getUniqueId())) {
			return true;
		}
		return false;
	}

	private static void initialize() {
		Debug.printDebugMessage("",  new Exception());
		stage = new Stage();
		index = 0;
		join = new HashSet<UUID>();
	}

	public static void continueToNext(Player sender) {
		Debug.printDebugMessage("",  new Exception());
		if (!hasStageLoaded(sender)) {
			return;
		}
		if (!hasJoined(sender)) {
			String[] opts = {"Please join the tour at first. [/vrg]"};
			Message.VRG_1.print(sender, opts);
			return;
		}
		index++;
		if (index < stage.npcConversations.size()) {
			try {
				Conversation conversation = getCurrentConversation();
				NPC npc = getCurrentNPC();
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (join.contains(player.getUniqueId())) {
						goToCurrent(player, conversation, npc);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		} else {
			finish();
		}
	}

	public static void returnToPrevious(Player sender) {
		Debug.printDebugMessage("",  new Exception());
		if (!hasStageLoaded(sender)) {
			return;
		}
		if (!hasJoined(sender)) {
			String[] opts = {"Please join the tour at first. [/vrg tour join]"};
			Message.VRG_1.print(sender, opts);
			return;
		}
		if (0 < index) {
			try {
				Conversation conversation = getCurrentConversation();
				NPC npc = getCurrentNPC();
				index--;
				for (Player search : Bukkit.getOnlinePlayers()) {
					if (join.contains(search.getUniqueId())) {
						goToCurrent(search, conversation, npc);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		} else {
			String[] opts = {"No previous conversation."};
			Message.VRG_1.print(sender, opts);
		}
	}


	private static void goToCurrent(Player player, Conversation conversation, NPC npc) {
		Debug.printDebugMessage("",  new Exception());
		Integer displayIndex = index + 1;
		String[] opts = {stage.name + " ( " + displayIndex + " / " + stage.npcConversations.size() + " )"};
		Message.COMMON_TP_1.print(player, opts);
		Location location = npc.getStoredLocation().add(0, 0, -1);
		location.setPitch(0);
		location.setYaw(0);
		player.teleport(location);
		conversation.talk(player, npc);
	}



	private static void finish() {
		Debug.printDebugMessage("",  new Exception());
		String[] opts = {"Finished the tour! (" + stage.name + ")"};
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (join.contains(player.getUniqueId())) {
				Message.VRG_1.print(player, opts);
				Effects.shootFirework(player);
			}
		}
		broadcastJoining();
		Message.TOUR_TRY_MINIGAME_1.broadcast(Arrays.asList(stage.name));
		initialize();
	}

	private static Conversation getCurrentConversation() throws Exception {
		Debug.printDebugMessage("",  new Exception());
		if (index < stage.npcConversations.size()) {
			if (stage.npcConversations.values().toArray()[index] instanceof Conversation) {
				return (Conversation) stage.npcConversations.values().toArray()[index];
			}
		}
		throw new Exception();
	}

	private static NPC getCurrentNPC() throws Exception {
		Debug.printDebugMessage("",  new Exception());
		Conversation conversation = getCurrentConversation();
		if (0 < conversation.sentences.size()) {
			NPC npc = NPCUtility.getNPC(conversation.sentences.get(0).id);
			return npc;
		}
		throw new Exception();
	}

	private static void broadcastJoining() {
		List<String> names = new ArrayList<String>();
		for (Player online : Bukkit.getOnlinePlayers()) {
			if (join.contains(online.getUniqueId())) {
				names.add(online.getName());
			}
		}
		Message.VRG_1.broadcast(Arrays.asList("Members: " + String.join(", ", names)));
	}
}

package com.github.orgs.kotobaminers.virtualryuugaku.publictour.publictour;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

import net.citizensnpcs.api.npc.NPC;

public class PublicTourController {
	public static Iterator<List<HolographicSentence>> sentences;
	private static List<HolographicSentence> current;
	private static Set<UUID> member = new HashSet<>();
	private static String stage = "";

	private static void loadTour(String name) {
		initialize();
		stage = name.toUpperCase();
		SentenceStorage.findStage.apply(name).ifPresent(lls -> sentences = lls.iterator());
		if (sentences.hasNext()) {
			current = sentences.next();
		}
	}

	private static void initialize() {
		sentences = null;
		current = null;
		member = new HashSet<UUID>();
	}

	public static void join(String name, Player player) {
		removeOfflineMember();
		if (member.size() < 1) {
			loadTour(name);
			join(player);
			return;
		} else if(name.equalsIgnoreCase(stage)) {
			join(player);
		} else {
			Message.VRG_1.print(Arrays.asList("A tour has already started."), player);
		}
	}

	public static void join(Player player) {
		removeOfflineMember();
		if (sentences == null) {
			Message.VRG_1.print(Arrays.asList("Invalid Name"), player);
			return;
		}
		if(member.contains(player.getUniqueId())) {
			String[] opts = {"You have already joined the tour."};
			Message.VRG_1.print(player, opts);
			return;
		}
		member.add(player.getUniqueId());
		String[] opts = {player.getName(), stage};
		Utility.sendTitle(player, stage, "Enjoy Lerning!");
		broadcastJoining();
		goToCurrent(player, findCurrentNPC());
	}

	public static void continueNext(Player player) {
		if (!member.contains(player.getUniqueId())) {
			String[] opts = {"Please join the tour at first. [/vrg]"};
			Message.VRG_1.print(player, opts);
			return;
		}
		if (!sentences.hasNext()) {
			finish();
			return;
		}
		current = sentences.next();
		Optional<NPC> npc = findCurrentNPC();
		npc.ifPresent(n ->
			Bukkit.getOnlinePlayers().stream()
				.filter(p -> member.contains(p.getUniqueId()))
				.forEach(p -> Utility.teleportToNPC(player, n)));
		return;
	}

	private static Optional<NPC> findCurrentNPC() {
		if (0 < current.size()) {
			return NPCUtility.findNPC(current.get(0).getId());
		}
		 return Optional.empty();
	}

	private static void goToCurrent(Player player, Optional<NPC> npc) {
		npc.ifPresent(n -> Utility.teleportToNPC(player, n));
	}

	private static void finish() {
		String[] opts = {"Finished the tour! (" + stage + ")"};
		Bukkit.getOnlinePlayers().stream()
			.filter(p -> member.contains(p.getUniqueId()))
			.forEach(p -> {
				Message.VRG_1.print(p, opts);
				Effects.shootFirework(p);}
			);
		broadcastJoining();
		Message.TOUR_TRY_MINIGAME_1.broadcast(Arrays.asList(stage));
		initialize();
	}

	private static void broadcastJoining() {
		String name = String.join(", ",
			Bukkit.getOnlinePlayers().stream()
				.filter(p -> member.contains(p.getUniqueId()))
				.map(p -> p.getName())
				.collect(Collectors.toList())
		);
		Message.VRG_1.broadcast(Arrays.asList("Members: " + name));
	}

	public static void removeOfflineMember() {
		member.stream().filter(uuid -> !Bukkit.getOnlinePlayers().stream().map(p -> p.getUniqueId())
			.collect(Collectors.toList()).contains(uuid))
			.forEach(uuid -> member.remove(uuid));
		return;
	}



}
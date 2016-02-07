package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

import net.citizensnpcs.api.npc.NPC;

public class SentenceStorage {
	public static Map<String, List<List<HolographicSentence>>> helperSentences = new HashMap<>();
	public static Map<String, List<Integer>> playerIds = new HashMap<>();
	public static Map<String, List<String>> playerQuestions = new HashMap<>();
	public static Map<String, List<List<PlayerSentence>>> playerSentences = new HashMap<>();

	public static void initialize() {
		helperSentences = new HashMap<>();
		playerIds = new HashMap<>();
		playerQuestions = new HashMap<>();
		playerSentences = new HashMap<>();
	}

	public static void showHolograms(Player player, NPC npc) {
		Optional<List<HolographicSentence>> list = findHolographicSentences.apply(npc);
		list.ifPresent(l -> HologramStorage.updateHologram(npc, l, player));
	}

	public static Optional<List<String>> findPlayerQuestions(String stage) {
		if (playerQuestions.containsKey(stage.toUpperCase())) {
			return Optional.of(playerQuestions.get(stage.toUpperCase()));
		}
		return Optional.empty();
	}

	public static Optional<String> findUnitName(Integer id) {
		Optional<String> name = playerIds.entrySet()
			.stream().filter(e -> e.getValue().contains(id))
			.findFirst().map(e -> e.getKey());
		if (name.isPresent()) {
			return name;
		}
		for (Entry<String, List<List<HolographicSentence>>> lls : helperSentences.entrySet()) {
			for (List<HolographicSentence> ls : lls.getValue()) {
				for (HolographicSentence s : ls) {
					if (s.getId().equals(id)) {
						return Optional.of(lls.getKey());
					}
				}
			}
		}
		return Optional.empty();
	}

	public static Optional<List<PlayerSentence>> getPlayerSentencesEvenPutEmpty(UUID uuid, String name, String stage) {
		if (playerIds.containsKey(stage)) {
			if (playerSentences.containsKey(stage)) {
				Optional<List<PlayerSentence>> optional = playerSentences.get(stage).stream()
						.filter(list -> list.stream()
							.allMatch(s -> s.getUniqueId().equals(uuid)))
						.findFirst();
					if (optional.isPresent()) {
						return optional;
					}
			}
			List<PlayerSentence> list = new ArrayList<PlayerSentence>();
			for(String question : SentenceStorage.playerQuestions.get(stage)) {
				list.add(PlayerSentence.createEmpty(uuid, name, question));
			}
			if (0 < list.size()) {
				if (playerSentences.containsKey(stage)) {
					SentenceStorage.playerSentences.get(stage).add(list);
					return Optional.of(list);
				} else {
					List<List<PlayerSentence>> sentences = new ArrayList<List<PlayerSentence>>();
					sentences.add(list);
					SentenceStorage.playerSentences.put(stage, sentences);
					return Optional.of(list);
				}
			}
		}
		return Optional.empty();
	}

	public static Function<NPC, Optional<List<HolographicSentence>>> findHolographicSentences = (npc) -> {
		Optional<String> stage = findUnitName(npc.getId());
		if(!stage.isPresent()) {
			return Optional.empty();
		}

		for (List<HolographicSentence> ls : helperSentences.get(stage.get())) {
			if(ls.stream().anyMatch(s -> s.getId().equals(npc.getId()))) {
				return Optional.of(ls);
			}
		}

		if(SentenceStorage.playerIds.values().stream().anyMatch(list -> list.contains(npc.getId()))) {
			Optional<UUID> uuid = NPCUtility.findSkinUUID(npc);
			Optional<String> name = NPCUtility.findSkinName(npc);
			if(uuid.isPresent() && name.isPresent()) {
				Optional<List<PlayerSentence>> sentences = getPlayerSentencesEvenPutEmpty(uuid.get(), name.get(), stage.get());
				if (sentences.isPresent()) {
					return Optional.ofNullable(sentences.get().stream()
						.map(s -> (HolographicSentence) s)
						.collect(Collectors.toList()));
				}
			}
		}
		return Optional.empty();
	};

	public static final Set<Integer> getAllIds() {
		Set<Integer> ids = new HashSet<>();
		helperSentences.values().forEach(lls ->
			lls.forEach(ls ->
				ls.forEach(s -> ids.add(s.getId()))));
		playerIds.values().forEach(li -> ids.addAll(li));
		return ids;
	}

	public static Function<String, Optional<List<List<HolographicSentence>>>> findStage = (name) -> {
		if (helperSentences.containsKey(name.toUpperCase())) {
			return Optional.of(helperSentences.get(name.toUpperCase()));
		}
		return Optional.empty();
	};

	public static void addHelperUnit(String unit, Integer id, CommandSender sender) {
		unit = unit.toUpperCase();
		if(playerIds.containsKey(unit)
				|| helperSentences.containsKey(unit)) {
			Message.ERROR_1.print(Arrays.asList("Existing Unit: " + unit), sender);
			return;
		}

		Optional<NPC> npc = NPCUtility.findNPC(id);
		if(findUnitName(id).isPresent()) {
			Message.ERROR_1.print(Arrays.asList("The NPC is in use: " + id), sender);
			return;
		}
		if (!npc.isPresent()) {
			Message.ERROR_1.print(Arrays.asList("The NPC doesn't exist: " + unit), sender);
			return;
		}
		List<HolographicSentence> ls = new ArrayList<>();
		List<List<HolographicSentence>> lls = new ArrayList<>();
		ls.add((HolographicSentence) HelperSentence.createEmpty(id));
		lls.add(ls);
		helperSentences.put(unit, lls);
		Message.VRG_1.print(Arrays.asList("A new unit with a HelperNPC was created: " + unit + " " + id), sender);
		if (sender instanceof Player) {
			Utility.teleportToNPC((Player) sender, npc.get());
		}
	}
	public static void addHelperNPC(String unit, Integer id, CommandSender sender) {
		unit = unit.toUpperCase();
		if(findUnitName(id).isPresent()) {
			Message.ERROR_1.print(Arrays.asList("The NPC is in use: " + id), sender);
			return;
		}
		Optional<NPC> npc = NPCUtility.findNPC(id);
		if (!npc.isPresent()) {
			Message.ERROR_1.print(Arrays.asList("The NPC doesn't exist: " + unit), sender);
			return;
		}
		if (helperSentences.containsKey(unit)) {
			List<HolographicSentence> ls = new ArrayList<>();
			ls.add((HolographicSentence) HelperSentence.createEmpty(id));
			helperSentences.get(unit).add(ls);
			Message.VRG_1.print(Arrays.asList("A helper NPC was added: " + unit + " " + id), sender);
			if (sender instanceof Player) {
				Utility.teleportToNPC((Player) sender, npc.get());
			}
			return;
		}
	}

	public static void removeHelperSentence(Integer id, Integer index) {
		Optional<List<List<HolographicSentence>>> lls = helperSentences.values().stream().filter(lls2 -> predicateLLS.test(lls2, id)).findFirst();
		Optional<List<HolographicSentence>> ls = lls.flatMap(ls2 -> ls2.stream().filter(ls3 -> predicateLS.test(ls3, id)).findFirst());
		if (ls.isPresent()) {
			if(0 < ls.get().size()) {
				if (1 == ls.get().size()) {
					if (1 == lls.get().size()) {

					}
				}
			}
		}
	}

	public static void unregisterHelperUnit(Integer id) {
	}


//	public static Optional<List<List<HolographicSentence>>> findListHelperSentences(Integer id) {
//		Predicate<List<List<HolographicSentence>>> isValidLLS = lls ->
//			lls.stream().anyMatch(ls ->
//				ls.stream().map(s -> s.getId().equals(id)).anyMatch(b -> b == true));
//		return helperSentences.values().stream().filter(isValidLLS).findFirst();
//	}
//	public static Optional<List<HolographicSentence>> findHelperSentences(Integer id) {
//		Predicate<List<HolographicSentence>> isValidLS = ls ->
//			ls.stream().anyMatch(s -> s.getId().equals(id));
//		return helperSentences.values().stream().flatMap(lls -> lls.stream().filter(isValidLS)).findFirst();
//	}
	public static BiPredicate<List<HolographicSentence>, Integer> predicateLS = (ls, id) ->
		ls.stream().anyMatch(s -> s.getId().equals(id));
	public static BiPredicate<List<List<HolographicSentence>>, Integer> predicateLLS = (lls, id) ->
		lls.stream().anyMatch(ls ->
			ls.stream().map(s -> s.getId().equals(id)).anyMatch(b -> b == true));
//	public static BiPredicate<Integer, >

}



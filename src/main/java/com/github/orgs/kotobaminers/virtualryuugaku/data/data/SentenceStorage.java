package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import org.bukkit.command.CommandSender;

public class SentenceStorage {
	public static Map<String, Unit> units = new HashMap<String, Unit>();

	public static void initialize() {
		units = new HashMap<>();
	}

	public static Optional<List<PlayerSentence>> getPlayerSentencesEvenPutEmpty(UUID uuid, String name, String stage) {
//		if (playerIds.containsKey(stage)) {
//			if (playerSentences.containsKey(stage)) {
//				Optional<List<HolographicSentence>> optional = playerSentences.get(stage).stream()
//						.filter(list -> list.stream()
//							.allMatch(s -> ((PlayerSentence) s).getUniqueId().equals(uuid)))
//						.findFirst();
//					if (optional.isPresent()) {
//						return optional.map(ls -> ls.stream().map(s -> (PlayerSentence) s).collect(Collectors.toList()));
//					}
//			}
//			List<PlayerSentence> list = new ArrayList<PlayerSentence>();
//			for(String question : SentenceStorage.playerQuestions.get(stage)) {
//				list.add(PlayerSentence.createEmpty(uuid, name, question));
//			}
////			if (0 < list.size()) {
////				if (playerSentences.containsKey(stage)) {
////					SentenceStorage.playerSentences.get(stage).add(list);
////					return Optional.of(list);
////				} else {
////					List<List<PlayerSentence>> sentences = new ArrayList<List<PlayerSentence>>();
////					sentences.add(list);
////					SentenceStorage.playerSentences.put(stage, sentences);
////					return Optional.of(list);
////				}
////			}
//		}
		return Optional.empty();
	}

	public static void addHelperUnit(String unit, Integer id, CommandSender sender) {
//		unit = unit.toUpperCase();
//		if(playerIds.containsKey(unit)
//				|| helperSentences.containsKey(unit)) {
//			Message.ERROR_1.print(Arrays.asList("Existing Unit: " + unit), sender);
//			return;
//		}
//
//		Optional<NPC> npc = NPCUtility.findNPC(id);
//		if(findUnitName(id).isPresent()) {
//			Message.ERROR_1.print(Arrays.asList("The NPC is in use: " + id), sender);
//			return;
//		}
//		if (!npc.isPresent()) {
//			Message.ERROR_1.print(Arrays.asList("The NPC doesn't exist: " + unit), sender);
//			return;
//		}
//		List<HolographicSentence> ls = new ArrayList<>();
//		List<List<HolographicSentence>> lls = new ArrayList<>();
//		ls.add((HolographicSentence) HelperSentence.createEmpty(id));
//		lls.add(ls);
//		helperSentences.put(unit, lls);
//		Message.VRG_1.print(Arrays.asList("A new unit with a HelperNPC was created: " + unit + " " + id), sender);
//		if (sender instanceof Player) {
//			Utility.teleportToNPC((Player) sender, npc.get());
//		}
	}

	public static void addHelperNPC(String unit, Integer id, CommandSender sender) {
//		unit = unit.toUpperCase();
//		if(findUnitName(id).isPresent()) {
//			Message.ERROR_1.print(Arrays.asList("The NPC is in use: " + id), sender);
//			return;
//		}
//		Optional<NPC> npc = NPCUtility.findNPC(id);
//		if (!npc.isPresent()) {
//			Message.ERROR_1.print(Arrays.asList("The NPC doesn't exist: " + unit), sender);
//			return;
//		}
//		if (helperSentences.containsKey(unit)) {
//			List<HolographicSentence> ls = new ArrayList<>();
//			ls.add((HolographicSentence) HelperSentence.createEmpty(id));
//			helperSentences.get(unit).add(ls);
//			Message.VRG_1.print(Arrays.asList("A helper NPC was added: " + unit + " " + id), sender);
//			if (sender instanceof Player) {
//				Utility.teleportToNPC((Player) sender, npc.get());
//			}
//			return;
//		}
	}

	public static void removeSentence(Map<String, List<List<HolographicSentence>>> map, Integer id, Integer index) {
//		Optional<List<List<HolographicSentence>>> lls = map.values().stream().filter(lls2 -> predicateLLS.test(lls2, id)).findFirst();
//		Optional<List<HolographicSentence>> ls = lls.flatMap(ls2 -> ls2.stream().filter(ls3 -> predicateLS.test(ls3, id)).findFirst());
//		if (!ls.isPresent()) return;
//		if (!(0 < ls.get().size())) return;
//
//		if (1 == ls.get().size()) {
//			if (1 == lls.get().size()) {
//				unregisterUnit(map, id);
//			} else {
//				lls.get().remove(ls.get());
//			}
//		} else if (index < ls.get().size()) {
//			ls.get().remove(ls.get().get(index));
//		}
	}
	public static void addSentence(Map<String, List<List<HolographicSentence>>> map, HolographicSentence sentence, int id, int index) {
		Optional<List<HolographicSentence>> ls = map.values().stream().flatMap(v ->
			v.stream().filter(ls2 -> predicateLS.test(ls2, id))).findFirst();
		if (!ls.isPresent()) return;
		if (! (index < ls.get().size())) return;

		ls.get().add(index, sentence);
	}

	public static void unregisterUnit(Integer id) {
		findUnitName(id).ifPresent(name -> units.remove(name));
	}

	public static BiPredicate<List<HolographicSentence>, Integer> predicateLS = (ls, id) ->
		ls.stream().anyMatch(s -> s.getId().equals(id));
	public static BiPredicate<List<List<HolographicSentence>>, Integer> predicateLLS = (lls, id) ->
		lls.stream().anyMatch(ls -> ls.stream().map(s -> s.getId().equals(id)).anyMatch(b -> b == true));


	public static BiFunction<Map<String, List<List<HolographicSentence>>>, Integer, Optional<String>> findHelperUnitName = (map, id) ->
		map.entrySet().stream()
			.filter(entry -> predicateLLS.test(entry.getValue(), id))
			.findAny().map(entry -> entry.getKey());

	public static Optional<List<List<HolographicSentence>>> findHelperLLS(Integer id) {
		return units.values().stream().filter(unit -> predicateLLS.test(unit.getHelperSentences(), id)).findFirst()
				.map(unit -> unit.getHelperSentences());
	}
	public static Optional<List<HolographicSentence>> findHelperLS(Integer id) {
		return units.values().stream().flatMap(u -> u.getHelperSentences().stream().filter(ls -> predicateLS.test(ls, id))).findFirst();
	}
	public static Optional<List<List<HolographicSentence>>> findPlayerLLS(Integer id) {
		return units.values().stream().filter(unit -> predicateLLS.test(unit.getPlayerSentences(), id)).findFirst()
				.map(unit -> unit.getPlayerSentences());
	}
	public static Optional<List<HolographicSentence>> findPlayerLS(Integer id) {
		return units.values().stream().flatMap(u -> u.getPlayerSentences().stream().filter(ls -> predicateLS.test(ls, id))).findFirst();
	}
	public static Optional<List<HolographicSentence>> findLS(Integer id) {
		Optional<List<HolographicSentence>> players = findPlayerLS(id);
		if (players.isPresent()) {
			return players;
		}
		Optional<List<HolographicSentence>> helpers = findHelperLS(id);
		if (helpers.isPresent()) {
			return helpers;
		}
		return Optional.empty();
	}

	public static Optional<String> findUnitName(Integer id) {
		Optional<Entry<String, Unit>> helpers = units.entrySet().stream().filter(u -> predicateLLS.test(u.getValue().getHelperSentences(), id)).findFirst();
		if (helpers.isPresent()) {
			return Optional.of(helpers.get().getKey());
		}
		Optional<Entry<String, Unit>> players = units.entrySet().stream().filter(u -> predicateLLS.test(u.getValue().getPlayerSentences(), id)).findFirst();
		if (players.isPresent()) {
			return Optional.of(players.get().getKey());
		}
		return Optional.empty();
	}

	public static Optional<Unit> findUnit(Integer id) {
		Optional<String> name = findUnitName(id);
		return Optional.ofNullable(units.getOrDefault(name, null));
	}

	public static Set<Integer> getAllIds() {
		Set<Integer> ids = new HashSet<>();
		units.values().stream().forEach(unit -> {
			unit.getHelperSentences().stream().forEach(ls -> ls.forEach(s -> ids.add(s.getId())));
			unit.getPlayerSentences().stream().forEach(ls -> ls.forEach(s -> ids.add(s.getId())));
		});
		return ids;
	}

}



package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.google.common.collect.Iterables;

import net.citizensnpcs.api.npc.NPC;

public class SentenceEditor {
	public enum EditMode {NONE("Quiting the editor"), CHANGE_ID("To change NPC ID mode"), EDIT("To edit the sentence mode");
		private String announce;
		private EditMode(String announce) {
			this.announce = announce;
		}
	}
	private static final int LIMIT_TALK_SENTENCE = 8;

	private Integer id = -1;
	private Integer index = 0;
	private Optional<SpellType> spell = Optional.empty();
	private EditMode mode = EditMode.NONE;

	private SentenceEditor(Integer id, Integer index, Optional<SpellType> spell) {
		this.id = id;
		this.index = index;
		this.spell = spell;
	}

	public static SentenceEditor create(Integer id, Integer index, Optional<SpellType> spell) {
		SentenceEditor editor = new SentenceEditor(id, index, spell);
		editor.id = id;
		editor.index = index;
		editor.spell = spell;
		return editor;
	}

	public Optional<List<HolographicSentence>> findSentences() {
		return SentenceStorage.findLS(id);
	}
	public Optional<HolographicSentence> findSentence() {
		return findSentence(findSentences());
	}
	private Optional<HolographicSentence> findSentence(Optional<List<HolographicSentence>>sentences){
		if (sentences.isPresent()) {
			if (index < sentences.get().size()) {
				return Optional.of(sentences.get().get(index));
			}
		}
		return Optional.empty();
	}

	public void edit(String edit, Player player) {
		if (!mode.equals(EditMode.EDIT)) {
			Message.INVALID_1.print(Arrays.asList("Please select an editable sentence"), player);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
			return;
		}
		Optional<HolographicSentence> optional = findSentence();
		if (optional.isPresent()) {
			HolographicSentence sentence = optional.get();
			if (sentence instanceof QuestionSentence) {
				sentence.update(edit, null);
			} else {
				spell.ifPresent(s -> sentence.update(edit, s));
			}
			Message.EDITOR_1.print(Arrays.asList("Edited: " + edit), player);
			player.playSound(player.getLocation(), Sound.ANVIL_USE, 1F, 1F);
		}
	}

	public boolean canEdit(Player player) {
		if (player.isOp()) {
			return true;
		}
		if (!spell.isPresent()) {
			return false;
		}
		Optional<HolographicSentence> optional = findSentence();
		if (!optional.isPresent()) {
			Message.ERROR_1.print(Arrays.asList("Broken sentences"), player);
			return false;
		}
		HolographicSentence sentence = optional.get();
		if (sentence instanceof HelperSentence) {
			HelperSentence ownerSentence = (HelperSentence) sentence;
			return ownerSentence.getOwner().map(uuid -> uuid.equals(player.getUniqueId())).isPresent();
		} else if (sentence instanceof PlayerSentence) {
			Optional<UUID> skin = NPCUtility.findNPC(id)
					.flatMap(npc -> NPCUtility.findSkinUUID(npc));
			return skin.map(s -> s.equals(player.getUniqueId())).isPresent();
		} else if(sentence instanceof QuestionSentence) {
			return false;
		}
		return false;
	}

	public void appendSentence(Player player) {
		insertSentence(index + 1);
		player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
		Message.EDITOR_1.print(Arrays.asList("Appending a new sentence "), player);
		PlayerDataStorage.closeEditor(player);
	}
	public void prependSentence(Player player) {
		insertSentence(index);
		player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
		Message.EDITOR_1.print(Arrays.asList("Prepending a new sentence "), player);
		PlayerDataStorage.closeEditor(player);
	}
	private void insertSentence(int position) {
		Optional<List<HolographicSentence>> optional = findSentences();
		Optional<List<HolographicSentence>> talks = optional.map(sentences -> sentences.stream()
			.filter(sentence -> !(sentence instanceof QuestionSentence))
			.collect(Collectors.toList()));
		if(talks.isPresent()) {
			List<HolographicSentence> list = talks.get();
			if(list.size() < LIMIT_TALK_SENTENCE  && index <= list.size()) {
				if(list.stream().allMatch(sentence -> sentence instanceof HelperSentence)) {
					optional.get().add(position, HelperSentence.createEmpty(id));
				} else if(list.stream().allMatch(sentence -> sentence instanceof PlayerSentence)) {
					PlayerSentence learner = (PlayerSentence) list.get(0);
					optional.get().add(position, PlayerSentence.createEmpty(learner.getUniqueId(), learner.getDisplayName(), "DUMMY"));
				}
			}
		}
	}

	public void removeSentence(Player player) {
		Optional<List<HolographicSentence>> optional = findSentences();
		if(optional.isPresent()) {
			if(index < optional.get().size()) {
				if (optional.get().size() == 1) {

				} else {
					optional.get().remove(optional.get().get(index));
				}
			}
		}
		player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
		Message.EDITOR_1.print(Arrays.asList("The selected sentence was removed"), player);
		PlayerDataStorage.closeEditor(player);
	}

	public void addAnswer(String answer, Player player) {
		Optional<HolographicSentence> optional = findSentence();
		if (!optional.isPresent()) {
			Message.INVALID_1.print(Arrays.asList("Please select an ediable question"), player);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
			return;
		}
		if (optional.get() instanceof QuestionSentence) {
			QuestionSentence sentence = (QuestionSentence) optional.get();
			List<String> answers = sentence.getAnswers();
			if(answers.stream().anyMatch(a -> a.equalsIgnoreCase(answer))) {
				Message.INVALID_1.print(Arrays.asList("The answer is already registered: " + answers), player);
				player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
				return;
			}
			answers.add(answer);
			Message.EDITOR_1.print(Arrays.asList("The answer was added: " + answers), player);
			player.playSound(player.getLocation(), Sound.ANVIL_USE, 1f, 1f);
			return;
		} else {
			Message.INVALID_1.print(Arrays.asList("Please select an ediable question"), player);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
			return;
		}
	}

	public void removeAnswer(String answer, Player player) {
		Optional<HolographicSentence> optional = findSentence();
		if (!optional.isPresent()) {
			Message.INVALID_1.print(Arrays.asList("Please select an editable question"), player);
			return;
		}
		if (optional.get() instanceof QuestionSentence) {
			QuestionSentence sentence = (QuestionSentence) optional.get();
			List<String> answers = sentence.getAnswers();
			for (String search : answers) {
				if (search.equalsIgnoreCase(answer)) {
					answers.remove(search);
					Message.EDITOR_1.print(Arrays.asList("The answer is removed: " + answers), player);
					player.playSound(player.getLocation(), Sound.ANVIL_USE, 1f, 1f);
					return;
				}
			}
			Message.INVALID_1.print(Arrays.asList("The answer is not registered: " + answers), player);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
		}
	}

	public void deleteQuestion(Player player) {
		Optional<HolographicSentence> optional = findSentence();
		if (!optional.isPresent()) {
			Message.INVALID_1.print(Arrays.asList("Please select an ediable question"), player);
			return;
		}
		if (optional.get() instanceof QuestionSentence) {
			Message.EDITOR_1.print(Arrays.asList("Unregistering the question..."), player);
			findSentences().get().remove(optional.get());
			player.playSound(player.getLocation(), Sound.ANVIL_USE, 1f, 1f);
		} else {
			Message.ERROR_1.print(Arrays.asList("Broken sentences"), player);
		}
	}

	public void eventChangeId(NPC npc, Player player) {
		changeId(npc, player);
		PlayerDataStorage.closeEditor(player);
	}

	private void changeId(NPC npc, Player player) {
		Integer id = npc.getId();
		Optional<List<HolographicSentence>> sentences = findSentences();
		Optional<HolographicSentence> optional = findSentence(sentences);
		if (!optional.isPresent()) {
			Message.INVALID_1.print(Arrays.asList("Please select an editable sentence"), player);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
			return;
		}
		HolographicSentence sentence = optional.get();

		if(!sentences.get().stream().map(s -> s.getId()).collect(Collectors.toList()).contains(id)) {
			Optional<String> existing = SentenceStorage.findUnitName(id);
			if (existing.isPresent()) {
				Message.INVALID_1.print(Arrays.asList("The npc is in use: " + existing.get()), player);
				player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
				return;
			}
		}

		if (sentence instanceof HelperSentence) {
			sentence.setId(npc.getId());
			Message.EDITOR_1.print(Arrays.asList("Changing the speaking npc"), player);
			player.playSound(player.getLocation(), Sound.ANVIL_USE, 1f, 1f);
			sentences.map(list -> list.stream().filter(s -> s instanceof HelperSentence)
				.collect(Collectors.toList()))
				.map(list -> Iterables.getLast(list))
				.ifPresent(last -> {
					if(last.equals(sentence)) {
						sentences.get().stream().filter(s -> s instanceof QuestionSentence)
						.forEach(q -> q.setId(sentence.getId()));
					}
				});
		} else {
			Message.INVALID_1.print(Arrays.asList("You cannot change this"), player);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
		}
	}

	public boolean checkEditMode(EditMode mode) {
		return this.mode.equals(mode);
	}
	public void updateEditMode(EditMode mode, Player player) {
		this.mode = mode;
		Message.EDITOR_1.print(Arrays.asList(mode.announce), player);
		Message.EDITOR_1.print(Arrays.asList("Left click on the target npc"), player);
		player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
	}
}
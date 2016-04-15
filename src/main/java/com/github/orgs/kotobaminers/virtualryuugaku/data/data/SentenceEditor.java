package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.google.common.collect.Iterables;

import net.citizensnpcs.api.npc.NPC;

public class SentenceEditor {
	public enum EditMode {
		NONE("Quiting the editor"),
		CHANGE_ID("To change NPC ID mode"),
		EN("To edit English"),
		KANJI("To edit kanji"),
		KANA("To edit kana"),
		QUESTION("To edit question"),
		;
		private String announce;
		private EditMode(String announce) {
			this.announce = announce;
		}
	}
	private Optional<List<HolographicSentence>> sentences = Optional.empty();
	private Optional<HolographicSentence> sentence = Optional.empty();
	private EditMode mode = EditMode.NONE;

	public SentenceEditor(Optional<List<HolographicSentence>> sentences, Optional<HolographicSentence>sentence, EditMode mode) {
		this.sentences = sentences;
		this.sentence = sentence;
		this.mode = mode;
	}

	public void edit(String edit, Player player) {
		if (!Arrays.asList(EditMode.EN, EditMode.KANJI, EditMode.KANA, EditMode.QUESTION).contains(mode)) {
			Message.INVALID_1.print(Arrays.asList("Please select an editable sentence"), player);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
			return;
		}
		if (sentence.isPresent()) {
			HolographicSentence sent = sentence.get();
			switch (mode) {
			case EN:
				sent.update(edit, SpellType.EN);
				break;
			case KANA:
				sent.update(edit, SpellType.KANA);
				break;
			case KANJI:
				sent.update(edit, SpellType.KANJI);
				break;
			case QUESTION:
				sent.update(edit, null);
				break;
			default:
				break;
			}
			Message.EDITOR_1.print(Arrays.asList("Edited: " + edit), player);
			player.playSound(player.getLocation(), Sound.ANVIL_USE, 1F, 1F);
		}
	}

	public boolean canEdit(Player player) {
		if (player.isOp()) {
			return true;
		}
		if (!sentence.isPresent()) {
			Message.ERROR_1.print(Arrays.asList("Broken sentences"), player);
			return false;
		}
		HolographicSentence sentence2 = sentence.get();
		if (sentence2 instanceof HelperSentence) {
			HelperSentence ownerSentence = (HelperSentence) sentence2;
			return ownerSentence.getOwner().map(uuid -> uuid.equals(player.getUniqueId())).isPresent();
		} else if (sentence2 instanceof PlayerSentence) {
			return ((PlayerSentence) sentence2).getUniqueId().equals(player.getUniqueId());
		} else if(sentence2 instanceof QuestionSentence) {
			return false;
		}
		return false;
	}

	public void appendSentence(Player player) {
		insertSentence(1);
		player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
		Message.EDITOR_1.print(Arrays.asList("Appending a new sentence "), player);
		PlayerDataStorage.closeEditor(player);
	}
	public void prependSentence(Player player) {
		insertSentence(0);
		player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
		Message.EDITOR_1.print(Arrays.asList("Prepending a new sentence "), player);
		PlayerDataStorage.closeEditor(player);
	}

	private void insertSentence(int plus) {
		if (!sentences.isPresent() || !sentence.isPresent()) return;
		if(Unit.MAX_SENTENCE <= sentences.get().size()) return;
		Integer position = sentences.get().indexOf(sentence.get()) + plus;
		if (position.equals(-1)) return;
		if(sentence.get() instanceof HelperSentence) {
			sentences.get().add(position, HelperSentence.createEmpty(sentence.get().getId()));
		} else if(sentence.get() instanceof PlayerSentence) {
			PlayerSentence learner = (PlayerSentence) sentence.get();
			sentences.get().add(position, PlayerSentence.createEmpty(learner.getUniqueId(), learner.getDisplayName()));
		}
	}

	public void removeSentence(Player player) {
		if (!sentences.isPresent() || !sentence.isPresent()) return;
		Optional<Unit> optUnit = UnitStorage.findUnit(sentence.get().getId());
		if (!optUnit.isPresent()) return;
		if(sentence.get() instanceof HelperSentence || sentence.get() instanceof QuestionSentence) {
			if(1 < sentences.get().size()) {
				Message.EDITOR_1.print(Arrays.asList("Removing this sentence"), player);
				sentences.get().remove(sentence.get());
				player.playSound(player.getLocation(), Sound.ANVIL_USE, 1f, 1f);
			} else if(optUnit.get().getHelperSentences().size() == 1) {
				Message.INVALID_1.print(Arrays.asList("To delete all of the sentence, try [/vrgop unit delete <NAME>]"), player);
				player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
			} else if(1 < optUnit.get().getHelperSentences().size()) {
				optUnit.get().getHelperSentences().remove(sentences.get());
				Message.EDITOR_1.print(Arrays.asList("Removing this conversation"), player);
				player.playSound(player.getLocation(), Sound.ANVIL_USE, 1f, 1f);
			}
		}
		PlayerDataStorage.closeEditor(player);
	}

	public void addAnswer(String answer, Player player) {
		if (!sentence.isPresent()) {
			Message.INVALID_1.print(Arrays.asList("Please select an ediable question"), player);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
			return;
		}
		if (sentence.get() instanceof QuestionSentence) {
			QuestionSentence question = (QuestionSentence) sentence.get();
			List<String> answers = question.getAnswers();
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
		if (!sentence.isPresent()) {
			Message.INVALID_1.print(Arrays.asList("Please select an editable question"), player);
			return;
		}
		if (sentence.get() instanceof QuestionSentence) {
			QuestionSentence question = (QuestionSentence) sentence.get();
			List<String> answers = question.getAnswers();
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
		if (!sentence.isPresent()) {
			Message.INVALID_1.print(Arrays.asList("Please select an ediable question"), player);
			return;
		}
		if (sentence.get() instanceof QuestionSentence) {
			Message.EDITOR_1.print(Arrays.asList("Unregistering the question..."), player);
			removeSentence(player);
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
		if (!sentence.isPresent()) {
			Message.INVALID_1.print(Arrays.asList("Please select an editable sentence"), player);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
			return;
		}
		HolographicSentence sent = sentence.get();

		if(!sentences.get().stream().map(s -> s.getId()).collect(Collectors.toList()).contains(id)) {
			Optional<String> existing = UnitStorage.findUnit(id).map(unit -> unit.getName());
			if (existing.isPresent()) {
				Message.INVALID_1.print(Arrays.asList("The npc is in use: " + existing.get()), player);
				player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1f, 1f);
				return;
			}
		}

		if (sent instanceof HelperSentence) {
			sent.setId(npc.getId());
			Message.EDITOR_1.print(Arrays.asList("Changing the speaking npc"), player);
			player.playSound(player.getLocation(), Sound.ANVIL_USE, 1f, 1f);
			sentences.map(list -> list.stream().filter(s -> s instanceof HelperSentence)
				.collect(Collectors.toList()))
				.map(list -> Iterables.getLast(list))
				.ifPresent(last -> {
					if(last.equals(sent)) {
						sentences.get().stream().filter(s -> s instanceof QuestionSentence)
						.forEach(q -> q.setId(sent.getId()));
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

	public void printEditorHelp(Player player) {
		switch (mode) {
		case CHANGE_ID:
			Message.EDITOR_1.print(Arrays.asList("To change the speaker, left-click a valid NPC"), player);
			break;
		case EN:
		case KANA:
		case KANJI:
			Message.EDITOR_1.print(Arrays.asList("To Edit This: [/vrg edit <SENTENCE>]"), player);
			break;
		case QUESTION:
			if (sentence.get() instanceof QuestionSentence) {
				QuestionSentence question = (QuestionSentence) sentence.get();
				Message.EDITOR_1.print(Arrays.asList("To edit the question: [/vrg edit <QUESTION>]"), player);
				Message.EDITOR_1.print(Arrays.asList("To add an answer: [/vrg aa <ANSER>]"), player);
				Message.EDITOR_1.print(Arrays.asList("To remove an answer: [/vrg ra <ANSER>]"), player);
				Message.EDITOR_1.print(Arrays.asList("To delete the question: [/vrg dq]"), player);
				Message.EDITOR_1.print(Arrays.asList("Current asnwers: [" + String.join(", ", question.getAnswers()) + "]"), player);
			} else {
				Message.ERROR_1.print(Arrays.asList("Unconsidered sentence"), player);
			}
			break;
		case NONE:
		default:
			break;
		}
	}

	public void toggleKey(Player player) {
		sentence.ifPresent(sentence -> {
			if (sentence instanceof TalkSentence) {
				TalkSentence talk = (TalkSentence) sentence;
				talk.setKey(!talk.isKey());
				Message.EDITOR_1.print(Arrays.asList("Key Sentence: " + talk.isKey()), player);
			}
		});
	}
}
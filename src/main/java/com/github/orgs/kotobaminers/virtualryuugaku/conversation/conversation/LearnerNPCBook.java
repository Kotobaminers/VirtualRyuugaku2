package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerVRG.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;

public class LearnerNPCBook {
	public static void giveBlankBookItem(Player player, String stage) {
		if (player.getItemInHand().getType().equals(Material.AIR)) {
			Optional<Stage> optional = StageStorage.findStage(stage);
			if(optional.isPresent()) {
				ItemStack item = new ItemStack(Material.BOOK_AND_QUILL);
				BookMeta book = (BookMeta) item.getItemMeta();
				book.addPage(joinTitle(player.getName(), stage));
				optional.get().learnerQuestions.forEach(page -> book.addPage("[ " + page + " ]"));
				item.setItemMeta(book);
				player.setItemInHand(item);
				Message.BOOK_GET_0.print(player, null);;
				Effects.playSound(player, Scene.APPEAR);
			} else {
				return;
			}
		} else {
			Message.COMMON_NOT_AIR_IN_HAND_0.print(player, null);
		}
	}

	static final Function<VRGSentence, Boolean> isValidSentence =
		sentence -> 0 < sentence.description.en.size() && 0 < sentence.description.kana.size();


	private static final VRGSentence translatePageToSentence(String page) {
		VRGSentence sentence = new VRGSentence();
		List<String> lines = new ArrayList<String>();
		lines.addAll(Arrays.asList(page.split("\\n")));
		if (2 < lines.size()) {
			String kanji = "";
			if (3 < lines.size()) {
				kanji = lines.get(3);
			}
			sentence.description = Description.create(kanji, lines.get(2), lines.get(1), new ArrayList<String>());
		}
		return sentence;

	}

	static final Predicate<LearnerConversation> isValidConversation =
		conversation ->
			conversation.sentences.stream()
				.map(isValidSentence)
				.anyMatch(bool -> bool ==false);

	public static Optional<LearnerConversation> createConversation(ItemStack item) {
		LearnerConversation conversation = new LearnerConversation();
		if (item.getType().equals(Material.BOOK_AND_QUILL)) {
			BookMeta book = (BookMeta) item.getItemMeta();
			book.getPages().stream()
				.filter(page -> 0 < book.getPages().indexOf(page))
				.forEach(page -> conversation.sentences.add(translatePageToSentence(page)));
		}
		return Optional.ofNullable(conversation).filter(isValidConversation);
	}

	private static String joinTitle(String player, String stage) {
		return "AUTHOR: " + player +
				"\nSTAGE: " + stage +
				"\n\n!! How to Write !!" +
				"\n[ Question ]  <-  given" +
				"\n<EN>     <-  required" +
				"\n<KANA>   <-  required" +
				"\n<KANJI>  <-  optional";
	}
}

package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerVRG.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerData;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Debug;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Expression;

public abstract class Conversation {
	public String stageName = "";
	public List<VRGSentence> sentences = new ArrayList<VRGSentence>();
	public List<Integer> index = new ArrayList<Integer>();
	public ConversationQuestion question = new ConversationQuestion();
	private static final List<ChatColor> talkerColor = Arrays.asList(
			ChatColor.LIGHT_PURPLE,
			ChatColor.YELLOW,
			ChatColor.RED,
			ChatColor.GREEN,
			ChatColor.AQUA,
			ChatColor.GOLD,
			ChatColor.BLUE);

	public void talk(Player player, NPC npc) {
		Debug.printDebugMessage("", new Exception());
		PlayerData data = PlayerDataStorage.getDataPlayer(player);
		if(0 < sentences.size()) {
			data.line++;
			if (sentences.size() < data.line || !data.conversation.equals(this)) {
				data.line = 0;
			} else if (data.line == sentences.size()) {
				if(0 < question.getQuestion().length()) {
					data.line++;
					data.conversation = this;
					question.giveQuestion(player, question);
					return;
				} else {
					data.line = 0;
				}
			}
			data.conversation = this;
			VRGSentence sentence = sentences.get(data.line);
			printTalk(sentence, player, data.line);
			Effects.effectTalk(player, sentence.id);
			Effects.playSound(player, Scene.NOTICE);

//			if (data.line == listTalk.size() - 1) {
//				if (!data.conversationDone.contains(getIDSorted())) {
//					try {
//						data.conversationDone.add(getIDSorted());
//						Stage stage = Stage.createStage(stageName);
//						String[] opts = {stage.getConversationDoneByMax(data)};
//						Effects.playSound(player, Scene.GOOD);
//						Message.STAGE_INFO_CONVERSATION_1.print(player, opts);
//						if (stage.getConversationMax() <= stage.getConversationDone(data)) {
//							Effects.shootFirework(player);
//							String[] opts2 = {stageName};
//							Message.CONEVRSATION_FINISH_ALL_1.print(player, opts2);
//						} else {
////							Message.CONVERSATION_FINISH_0.print(player, null); Too much information
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//						return;
//					}
//				}
//			}
		}
	}

	private void printTalk(VRGSentence sentence, Player player, Integer line) {
		List<Expression> expressions = PlayerDataStorage.getDataPlayer(player).expressions;
		if(0 < expressions.size()) {
			String title = "";
			title += "" + getTalkerColor(sentence.id);
			Integer number = line + 1;
			title += " (" + number + " / " + sentences.size() + ")";
			if (sentence.key) {
				title += ChatColor.YELLOW + " *KEY*" + ChatColor.RESET;
			}
			String[] opts = {title};
			Message.TALK_TITLE_1.print(player, opts);
		}
		if (expressions.contains(Expression.EN)) {
			String[] english = {" - " + Utility.joinStrings(sentence.description.getEnglishList(), ", ")};
			Message.EMPTY_1.print(player, english);
		}
		List<String> listJapanese = new ArrayList<String>();
		if(expressions.contains(Expression.KANJI)) {
			listJapanese.add(sentence.description.getKanjiJoined());
		}
		if(expressions.contains(Expression.KANA)) {
			listJapanese.add(sentence.description.getKanaJoined());
		}
		if(expressions.contains(Expression.ROMAJI)) {
			listJapanese.add(sentence.description.getRomajiJoined());
		}
		if (0 < listJapanese.size()) {
			String[] japanese = {" - " + Utility.joinStrings(listJapanese, " " + ChatColor.GRAY + ChatColor.BOLD + " / " + ChatColor.RESET)};
			Message.EMPTY_1.print(player, japanese);
		}
	}

	public Boolean isEmpty() {
		if(0 < sentences.size()) {
			return false;
		}
		return true;
	}

	public List<VRGSentence> getKeyTalk() {
		List<VRGSentence> talks = new ArrayList<VRGSentence>();
		for (VRGSentence talk : sentences) {
			if (talk.key) {
				talks.add(talk);
			}
		}
		return talks;
	}

	public void printInformation(Player player) {
	}

	public boolean hasValidQuestion() {
		if(0 < question.getQuestion().length() && 0 < question.getAnswers().size()) {
			return true;
		}
		return false;
	}

	public ChatColor getTalkerColor(Integer id) {
		ChatColor color = ChatColor.LIGHT_PURPLE;
		List<Integer> ids = getIDSorted();
		if (ids.contains(id)) {
			Integer index = ids.indexOf(id);
			Integer division = index / talkerColor.size();
			color = talkerColor.get(index - division * talkerColor.size());
		}
		return color;
	}

	public abstract List<Integer> getIDSorted();

	public void printDebugMessage() {
		List<String> en = new ArrayList<String>();
		for (VRGSentence sentence : sentences) {
			en.addAll(sentence.description.en);
		}
		Debug.printDebugMessage("" + index + " " + Utility.joinStrings(en, ", "), new Exception());
	}
}


//public Boolean canEdit(String playerName) {
//if(editor.contains(playerName)){
//	return true;
//}
//return false;
//}
//
//public boolean hasEditor() {
//Debug.printDebugMessage("" + editor.size(), new Exception());
//if(0 < editor.size()) {
//	return true;
//}
//return false;
//}
//

//	public Set<NPC> getNPCs() throws Exception{
//		Set<Integer> set = new HashSet<Integer>();
//		List<Integer> list = getIDSorted();
//		set.addAll(list);
//		Set<NPC> npcs = new HashSet<NPC>();
//		for (Integer id : set) {
//			npcs.add(NPCHandler.getNPC(id));
//		}
//		if (0 < npcs.size()) {
//			return npcs;
//		} else {
//			throw new Exception("NPC not exists: " + list);
//		}
//	}
//	public enum CheckState {NOT_EXISTS, UNCHECKED, CHECKED, KEY,;
//	public static CheckState lookup(String name) {
//		try {
//			return valueOf(name.toUpperCase());
//		} catch (IllegalArgumentException e) {
//			return NOT_EXISTS;
//		}
//	}
//}
//
//public CheckState getCheckState() {
//	if (!(0 < listTalk.size())) {
//		return CheckState.NOT_EXISTS;
//	} else if (0 < getKeyTalk().size()) {
//		return CheckState.KEY;
//	} else if (0 < getCorrectors().size() || 0 < recommenders.size()) {
//		return CheckState.CHECKED;
//	}
//	return CheckState.UNCHECKED;
//}
//
//public List<String> getCorrectors() {
//	List<String> correctors = new ArrayList<String>();
//	for (Talk talk : listTalk) {
//		correctors.addAll(talk.getCorrectors());
//	}
//	return correctors;
//}
//
//}



package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.Enums.Expression;

public abstract class Conversation {
	public String stageName = "";
	public List<String> editor = new ArrayList<String>();
	public List<Talk> listTalk = new ArrayList<Talk>();
	public ConversationQuestion question = new ConversationQuestion();
	public Set<String> recommenders = new HashSet<String>();
	private static final List<ChatColor> talkerColor = Arrays.asList(
			ChatColor.LIGHT_PURPLE,
			ChatColor.YELLOW,
			ChatColor.RED,
			ChatColor.GREEN,
			ChatColor.AQUA,
			ChatColor.GOLD,
			ChatColor.BLUE);

	public enum CheckState {NOT_EXISTS, UNCHECKED, CHECKED, KEY,;
		public static CheckState lookup(String name) {
			try {
				return valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				return NOT_EXISTS;
			}
		}
	}

	public CheckState getCheckState() {
		if (!(0 < listTalk.size())) {
			return CheckState.NOT_EXISTS;
		} else if (0 < getKeyTalk().size()) {
			return CheckState.KEY;
		} else if (0 < getCorrectors().size() || 0 < recommenders.size()) {
			return CheckState.CHECKED;
		}
		return CheckState.UNCHECKED;
	}

	public List<String> getCorrectors() {
		List<String> correctors = new ArrayList<String>();
		for (Talk talk : listTalk) {
			correctors.addAll(talk.getCorrectors());
		}
		return correctors;
	}

	public void talk(Player player, NPC npc) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		if(0 < listTalk.size()) {
			data.line++;
			if (listTalk.size() < data.line || !data.conversation.equals(this)) {
				data.line = 0;
			} else if (data.line == listTalk.size()) {
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
			printTalk(player, data.line);
			effectTalk(player, npc);
			Effects.playSound(player, Scene.NOTICE);

			if (data.line == listTalk.size() - 1) {
				if (!data.conversationDone.contains(getIDSorted())) {
					try {
						data.conversationDone.add(getIDSorted());
						Stage stage = Stage.createStage(stageName);
						String[] opts = {stage.getConversationDoneByMax(data)};
						Effects.playSound(player, Scene.GOOD);
						Message.STAGE_INFO_CONVERSATION_1.print(player, opts);
						if (stage.getConversationMax() <= stage.getConversationDone(data)) {
							Effects.shootFirework(player);
							String[] opts2 = {stageName};
							Message.CONEVRSATION_FINISH_ALL_1.print(player, opts2);
						} else {
//							Message.CONVERSATION_FINISH_0.print(player, null); Too much information
						}
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
				}
			}
		}
	}

	private void printTalk(Player player, Integer line) {
		List<Expression> expressions = DataManagerPlayer.getDataPlayer(player).expressions;
		Talk talk = listTalk.get(line);
		if(0 < expressions.size()) {
			String title = "";
			title += "" + getTalkerColor(talk.id);
			title += "" + ChatColor.BOLD + talk.name + ChatColor.RESET;
			Integer number = line + 1;
			title += " (" + number + " / " + listTalk.size() + ")";
			if (talk.key) {
				title += ChatColor.YELLOW + " *KEY*" + ChatColor.RESET;
			}
			String[] opts = {title};
			Message.TALK_TITLE_1.print(player, opts);
		}
		if (expressions.contains(Expression.EN)) {
			String[] english = {" - " + talk.description.getEnglishJoined()};
			Message.COMMON_EMPTY_1.print(player, english);
		}
		List<String> listJapanese = new ArrayList<String>();
		if(expressions.contains(Expression.KANJI)) {
			listJapanese.add(talk.description.getKanjiJoined());
		}
		if(expressions.contains(Expression.KANA)) {
			listJapanese.add(talk.description.getKanaJoined());
		}
		if(expressions.contains(Expression.ROMAJI)) {
			listJapanese.add(talk.description.getRomajiJoined());
		}
		if (0 < listJapanese.size()) {
			String[] japanese = {" - " + UtilitiesGeneral.joinStrings(listJapanese, " " + ChatColor.GRAY + ChatColor.BOLD + " / " + ChatColor.RESET)};
			Message.COMMON_EMPTY_1.print(player, japanese);
		}
	}

	private void effectTalk(Player player, NPC npc) {
		player.getWorld().playEffect(npc.getStoredLocation().add(0, 2, 0), Effect.SMOKE, 22);//data is 22(No direction value).
	}

	public Boolean isEmpty() {
		if(0 < listTalk.size()) {
			return false;
		}
		return true;
	}

	public List<Talk> getKeyTalk() {
		List<Talk> talks = new ArrayList<Talk>();
		for (Talk talk : listTalk) {
			if (talk.key) {
				talks.add(talk);
			}
		}
		return talks;
	}

	public void printInformation(Player player) {
	}

	public Boolean canEdit(String playerName) {
		if(editor.contains(playerName)){
			return true;
		}
		return false;
	}

	public boolean hasEditor() {
		UtilitiesProgramming.printDebugMessage("" + editor.size(), new Exception());
		if(0 < editor.size()) {
			return true;
		}
		return false;
	}

	public boolean hasValidQuestion() {
		UtilitiesProgramming.printDebugMessage("" + editor.size(), new Exception());
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

	public String getDebugMessage() {
		String edit = "[" + UtilitiesGeneral.joinStrings(editor, ", ") + "]";
		String message = "[CONV] STAGE: " + stageName + ", EDITOR: " + edit + "ID:" + getKeyTalk().toString();
		return message;
	}

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
}



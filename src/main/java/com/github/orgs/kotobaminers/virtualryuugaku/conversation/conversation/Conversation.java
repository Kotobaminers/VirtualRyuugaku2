package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public abstract class Conversation {
	public String stageName = "";
	public List<String> editor = new ArrayList<String>();
	public List<Talk> listTalk = new ArrayList<Talk>();
	public ConversationQuestion question = new ConversationQuestion();
	public Set<String> recommenders = new HashSet<String>();

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
		data.conversation = this;
		if(0 < listTalk.size()) {
			if(listTalk.size() <= data.line) {
				data.line = 0;
				if ( 0 < question.getQuestion().length()) {
					question.giveQuestion(player, question);
					return;
				}
			}
			Talk talk = listTalk.get(data.line);
			talk.print(player);
			addLine(data);
			soundTalk(player);
			effectTalk(player, npc);
			return;
		}
	}

	private void addLine(DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(listTalk.size() <= data.line) {
			data.line = 0;
		} else {
			data.line++;
		}
	}

	private void effectTalk(Player player, NPC npc) {
		player.getWorld().playEffect(npc.getStoredLocation().add(0, 2, 0), Effect.SMOKE, 22);//data is 22(No direction value).
	}
	private void soundTalk(Player player) {
		player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
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



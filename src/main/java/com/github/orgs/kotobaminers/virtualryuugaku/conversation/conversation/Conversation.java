package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.comment.DataComment;
import com.github.orgs.kotobaminers.virtualryuugaku.myself.myself.ControllerMyself;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public abstract class Conversation {
	public String stage = "";
	public List<String> editor = new ArrayList<String>();
	public List<Talk> listTalk = new ArrayList<Talk>();
	public Map<String, DataComment> mapComment = new HashMap<String, DataComment>();
	public ConversationQuestion question = new ConversationQuestion();

	public Set<String> recommenders = new HashSet<String>();
	public enum CheckState {NOT_EXISTS, UNCHECKED, CHECKED, RECOMMENDED, }
	public CheckState getCheckState() {
		CheckState state = CheckState.UNCHECKED;
		if (!(0 < listTalk.size())) {
			state = CheckState.NOT_EXISTS;
			return state;
		}

		for (String teacher : ControllerMyself.getTeachers()) {
			if (recommenders.contains(teacher)) {
				state = CheckState.RECOMMENDED;
				return state;
			}
		}

		if (0 < getCorrectors().size() || 0 < recommenders.size()) {
			state = CheckState.CHECKED;
			return state;
		}

		return state;
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
//		new ScoreboardTalk().update(player, conversation, data);
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
		UtilitiesProgramming.printDebugMessage("", new Exception());
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

	public List<Integer> getOrder() {
		List<Integer> order = new ArrayList<Integer>();
		for(Talk talk : listTalk) {
			order.add(talk.id);
		}
		return order;
	}

	public String getDebugMessage() {
		String edit = "[" + UtilitiesGeneral.joinStrings(editor, ", ") + "]";
		String message = "[CONV] STAGE: " + stage + ", EDITOR: " + edit + "ID:" + getKeyTalk().toString();
		return message;
	}
}



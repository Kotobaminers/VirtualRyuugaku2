package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.ChatColor;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation.CheckState;
import com.github.orgs.kotobaminers.virtualryuugaku.game.game.GameGlobal.EventScore;
import com.github.orgs.kotobaminers.virtualryuugaku.myself.myself.StageMyself;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.NPCHandler;

public abstract class Stage {
	public String name = "";

	public static StageMyself createStageMyself(String name) throws Exception {
		if (ControllerConversation.existsMyselfStage(name)) {
			StageMyself stage = StageMyself.create();
			stage.name = name;
			stage.conversations = new HashSet<ConversationMyself>();
			List<Conversation> conversations2 = ControllerConversation.getConversations(name);
			for (Conversation conversation : conversations2) {
				if (conversation instanceof ConversationMyself) {
					stage.conversations.add((ConversationMyself) conversation);
				}
			}
			if (0 < stage.conversations.size()) {
				return stage;
			}
		}
		throw new Exception("Invalid Stage Name: " + name);
	}

	public static Stage createStage(String name) throws Exception {
		Stage stage = null;
		if (ControllerConversation.existsMyselfStage(name)) {
			stage = Stage.createStageMyself(name);
		} else {
			stage = Stage.createStageMulti(name);
		}
		return stage;
	}

	public static StageMulti createStageMulti(String name) throws Exception {
		StageMulti stage = StageMulti.create();
		stage.name = name;
		List<Conversation> conversations = ControllerConversation.getConversations(name);
		for (Conversation conversation : conversations) {
			if (conversation instanceof ConversationMulti) {
				stage.conversations.add((ConversationMulti) conversation);
			}
		}
		if (0 < stage.conversations.size()) {
			return stage;
		} else {
			throw new Exception("Invalid Stage Name: " + name);
		}
	}

	public Set<NPC> getNPCs(Set<Conversation> conversations) {
		Set<Integer> ids = new HashSet<Integer>();
		for (Conversation conversation : conversations) {
			ids.addAll(conversation.getIDSorted());
		}
		Set<NPC> npcs = new HashSet<NPC>();
		for (Integer id : ids) {
			try {
				npcs.add(NPCHandler.getNPC(id));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return npcs;
	}

	public Integer getQuestionMax() {
		Integer max = 0;
		Set<Conversation> conversations = getConversations();
		for (Conversation conversation : conversations) {
			if (conversation.hasValidQuestion()) {
				max++;
			}
		}
		return max;
	}
	public Integer getQuestionDone(DataPlayer data) {
		Integer done = 0;
		for (Conversation conversation : getConversations()) {
			if(data.questionDone.contains(conversation.getIDSorted())) {
				done++;
			}
		}
		return done;
	}
	public String getQuestionDoneByMax(DataPlayer data) {
		String doneByMax = getDoneByMax(getQuestionDone(data), getQuestionMax());
		return doneByMax;
	}

	public Integer getConversationMax() {
		Integer max = getConversations().size();
		return max;
	}

	public Integer getConversationDone(DataPlayer data) {
		Integer done = 0;
		for (Conversation conversation : getConversations()) {
			if(data.conversationDone.contains(conversation.getIDSorted())) {
				done++;
			}
		}
		return done;
	}

	public String getConversationDoneByMax(DataPlayer data) {
		String doneByMax = getDoneByMax(getConversationDone(data), getConversationMax());
		return doneByMax;
	}

	private String getDoneByMax(Integer done, Integer max) {
		String str = "";
		if (max <= done) {
			str = ChatColor.GREEN + "*Completed*" + ChatColor.RESET + "(" + max.toString() + ")";
		} else {
			str += "" + ChatColor.YELLOW + ChatColor.BOLD;
			for (int i = 0; i < done; i++) {
				str += "*";
			}
			str += ChatColor.RED;
			for (int i = 0; i < max - done; i++) {
				str += "-";
			}
		}
		return str;
	}

	public Integer getNPCsTotal() {
		Set<Integer> ids = new HashSet<Integer>();
		for (Conversation conversation : getConversations()) {
			ids.addAll(conversation.getIDSorted());
		}
		return ids.size();
	}
	public Integer getKeySentenceTotal() {
		Integer total = 0;
		for (Conversation conversation : getConversations()) {
			for (Talk talk : conversation.listTalk) {
				if(talk.key) {
					total++;
				}
			}
		}
		return total;
	}

	public Integer getScoreMax() {
		Integer key = getKeySentenceTotal();
		Integer positive = 0;
		for (EventScore event : EventScore.values()) {
			if (0 < event.getScore()) {
				positive += event.getScore();
			}
		}
		Integer max = key * positive;
		return max;
	}
	public Integer getScore(DataPlayer data) {
		return data.getScore(name);
	}
	public String getScoreDoneByMax(DataPlayer data) {
		String doneByMax = getDoneByMax(getScore(data), getScoreMax());
		return doneByMax;
	}


	public abstract Set<Conversation> getConversations(CheckState check);
	public abstract Set<Conversation> getConversations();
}




package com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class EventConversation {
	private NPC npc;
	private Conversation conversation;
	private Player player;
	public EventConversation(NPC npc, Conversation talker, Player player) {
		this.npc = npc;
		this.conversation = talker;
		this.player = player;
	}

	public void talk() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
		conversation.talkNext(player, data);
//		conversation.talkEffect(player, npc);//Talking NPCs' locantion can't get because it's not saved.
		conversation.talkSound(player);
		new ScoreboardTalk().update(player, conversation, data);
		addLine(data);
	}
	private void addLine(DataPlayer data) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(conversation.listTalk.size() <= data.line) {
			data.line = 0;
		} else {
			data.line++;
		}
	}

	public void printKeySentence() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
//		DataPlayer data = DataManagerPlayer.getDataPlayer(player);
//		if(!data.select.equa ls(npc.getId())) {
//			DataManagerPlayer.selectTalker(player, talker);
//		} else {
//			UtilitiesProgramming.printDebugMessage("", new Exception());
//			Description description = talker.getKeyDescription();
//			String en = description.express(Expression.EN);
//			List<String> list = new ArrayList<String>();
//			switch(data.language) {
//			case ROMAJI:
//				list.add(description.express(Expression.ROMAJI));
//				break;
//			case KANA:
//				list.add(description.express(Expression.KANA));
//				break;
//			case EN:
//			case KANJI:
//				list.add(description.express(Expression.KANJI));
//				break;
//			case NONE:
//			default:
//				break;
//			}
//			String jp = UtilitiesGeneral.joinStrings(list, ", ");
////			String[] opts = {talker.name};
//			String[] optsJp = {jp};
//			String[] optsEn = {en};
////			MessengerGeneral.print(player, Message.TALKER_KEY_SENTENCE_1, opts);
//			MessengerGeneral.print(player, Message.TALKER_KEY_SENTENCE_JP_1, optsJp);
//			MessengerGeneral.print(player, Message.TALKER_KEY_SENTENCE_EN_1, optsEn);
//		}
	}

	public void ownEmptyTalker() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
//		for(Conversation search : DataManagerConversation.getMapTalker().values()) {
//			if(search.stage.equalsIgnoreCase(talker.stage)) {
//				if(search.editor.contains(player.getName())) {
//					MessengerGeneral.print(player, Message.ALREADY_OWNED_0, null);
//					return;
//				}
//			}
//		}
//		npc.setBukkitEntityType(EntityType.PLAYER);
//		npc.setName(player.getName());//NPC's skin will change.
//		talker.name = player.getName();
//		talker.editor.add(player.getName());
//		MessengerGeneral.print(player, Message.OWN_TALKER_0, null);
	}

	public void registerEmptyTalker(String stage) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
//		talker.id = npc.getId();
//		talker.stage = stage;
//		talker.name = player.getName();
//		talker.editor = new ArrayList<String>();
//		UtilitiesProgramming.printDebugTalker(talker);
//		DataManagerConversation.registerTalker(talker);
	}
}
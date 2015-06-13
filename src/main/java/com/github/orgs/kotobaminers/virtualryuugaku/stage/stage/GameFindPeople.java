package com.github.orgs.kotobaminers.virtualryuugaku.stage.stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.FireworkEffect.Type;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.FireworkUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.FireworkUtility.FireworkColor;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Conversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.DataManagerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataManagerPlayer;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.DataPlayer.PlayerScore;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class GameFindPeople {
	private Player player;
	public String stage = "";
	public List<Talk> listTalk = new ArrayList<Talk>();
	public Integer count = 0;
	public Mode mode = Mode.RANDOM;
	enum Mode {JP, EN, RANDOM}

 	public static GameFindPeople createGame(Player player, String stage) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		GameFindPeople game = new GameFindPeople();
		game.player = player;
		game.stage = stage;
		List<Talk> listTalk = new ArrayList<Talk>();
		for(Conversation conversation : DataManagerConversation.getMapConversation().values()) {
			if (conversation.stage.equalsIgnoreCase(stage)) {
				listTalk.addAll(conversation.getKeySentence());
			}
		}
		if(0 < listTalk.size()) {
			Collections.shuffle(listTalk);
			game.listTalk = listTalk;
		} else {
			UtilitiesProgramming.printDebugMessage("Error", new Exception());
		}
		return game;
	}
	void initialize() {}

	public Talk getTalkCurrent() {
		Talk talk = new Talk();
		if (count < listTalk.size()) {
			talk = listTalk.get(count);
		}
		return talk;
	}

	public void tryNext() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		if(!(count < listTalk.size())) {
			UtilitiesProgramming.printDebugMessage("Error", new Exception());
			return;
		}
		Talk talk = getTalkCurrent();
		giveQuestion(player, talk);
	}
	private void giveQuestion(Player player, Talk talk){
		UtilitiesProgramming.printDebugMessage("DBG: ANS ID == " + talk.id, new Exception());
		Description description = talk.description;
		String question = "";
		Mode modeQuestion = mode;
		if (mode.equals(Mode.RANDOM)) {
			Random random = new Random();
			Integer i = random.nextInt(2);
			switch(i) {
			case 0:
				modeQuestion = Mode.EN;
				break;
			case 1:
			default:
				modeQuestion = Mode.JP;
				break;
			}
		}
		switch(modeQuestion) {
		case EN:
			question = UtilitiesGeneral.joinStrings(description.getEnglishList(), ", ");
			break;
		case JP:
		case RANDOM:
		default:
			question = UtilitiesGeneral.joinStrings(description.getJapaneseList(), ", ");
			break;
		}
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.FIND_PEOPLE_MISSION_0, null));
		String[] opts = {question};
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.FIND_PEOPLE_QUEST_1, opts));
		player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
	}

	void correct(Player player) {
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.CORRECT_0, null));
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
	}
	void wrong(Player player) {
		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message.WRONG_0, null));
		player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
		GameFindPeopleHandler.initializeGame(player);
	}

	public void validate(Integer id) {
		UtilitiesProgramming.printDebugMessage("" , new Exception());
		UtilitiesProgramming.printDebugMessage(id.toString() + " " + getTalkCurrent().id, new Exception());
		if (getTalkCurrent().id.equals(id)) {
			correct(player);
			count++;
			tryNext();
		} else {
			wrong(player);
		}
		if (isFinished()) {
			finish();
			return;
		}
		UtilitiesProgramming.printDebugMessage(id.toString() + " " + getTalkCurrent().id, new Exception());

	}
	private boolean isFinished() {
		if (listTalk.size() <= count) {
			return true;
		}
		return false;
	}

	private void finish() {
		String[] opts = {player.getName(), stage};
		MessengerGeneral.broadcast(MessengerGeneral.getMessage(Message.FIND_PEOPLE_FINISH_2, opts));
		GameFindPeopleHandler.initializeGame(player);
		FireworkUtility.shootFirework(player.getWorld(), player.getLocation(), Type.BALL_LARGE, FireworkColor.GREEN, FireworkColor.AQUA, 0);
		DataManagerPlayer.getDataPlayer(player).addScore(player, PlayerScore.FIND_PEOPLE);
	}

}
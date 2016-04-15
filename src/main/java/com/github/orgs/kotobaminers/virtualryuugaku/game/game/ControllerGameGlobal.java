package com.github.orgs.kotobaminers.virtualryuugaku.game.game;


public class ControllerGameGlobal /*extends Controller */{
}

//	public static GameGlobal game = new GameGlobal();
//
//	@Override
//	public void setStorage() {
//		game = new GameGlobal();
//	}
//
//	@Override
//	public Storage getStorage() {
//		return game;
//	}
//
//	public static void loadGame(String stage) {
//		game.load(stage);
//	}
//
//	public static void giveNextQuestion(CommandSender sender) {
//		UtilitiesProgramming.printDebugMessage("", new Exception());
//		if (game.hasNextTalk()) {
//			game.giveNextQuestion();
//		} else {
//			Message.GAME_TRY_FINISH_0.print(sender, null);
//		}
//	}
//
//	public static void finishGame() {
//		game.finishGame();
//	}
//
//	public static void validataAnswer(Player player, String answer) {
//		if(game.cantAnswer.contains(player.getName())) {
//			MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.GAME_NOT_ALLOWED_TO_ANSWER_0, null));
//			return;
//		}
//		if (game.validateAnswer(player, answer)) {
//			eventWriteCorrect(player);
//		} else {
//			eventWriteWrong(player);
//			game.printHints(player, answer);
//		}
//	}
//
//	private static void eventWriteWrong(Player player) {
//		player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
//		Message.COMMON_WRONG_0.print(player, null);
//		game.addScore(player, EventScore.ANSWER_WRONGLY);
//	}
//
//
//	private static void eventWriteCorrect(Player player) {
//		player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
//		String[] opts = {UtilitiesGeneral.joinStrings(game.getCurrentAnswers(), ", ")};
//		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.STAGE_CORRECT_1, opts));
//		game.addScore(player, EventScore.ANSWER_CORRECTLY);
//		game.cantAnswer.add(player.getName());
//	}
//
//	private static void eventFindWrong(Player player) {
//		player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
//		Message.COMMON_WRONG_0.print(player, null);
//		game.addScore(player, EventScore.LEFT_CLICK_NPC_WRONGLY);
//	}
//
//
//	private static void eventFindCorrect(Player player) {
//		player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
//		Message.COMMON_CORRECT_0.print(player, null);
//		game.addScore(player, EventScore.LEFT_CLICK_NPC_CORRECTLY);
//		game.cantFind.add(player.getName());
//	}
//
//	public static boolean isValidGame() {
//		if (0 < game.talks.size()) {
//			return true;
//		}
//		return false;
//	}
//
//	public static void updataScoreboard(Player player) {
//		new GameGlobalScoreboard().update(player, game.scores);
//	}
//
//	public static void checkFindPeople(Player player, NPC npc) {
//		UtilitiesProgramming.printDebugMessage("", new Exception());
//		if (!game.cantFind.contains(player.getName())) {
//			try {
//				Conversation conversation = ControllerConversation.getConversation(npc);
//				if(conversation.listTalk.contains(game.getCurrentKeyTalk())) {
//					eventFindCorrect(player);
//				} else {
//					eventFindWrong(player);
//				}
//				updataScoreboard(player);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public static void cheat(Player player) {
//		game.addScore(player, EventScore.CHEAT_A_CONVERSATION);
//		updataScoreboard(player);
//		String[] opts = {"" + EventScore.CHEAT_A_CONVERSATION.getScore()};
//		MessengerGeneral.print(player, MessengerGeneral.getMessage(Message0.GAME_CHEAT_1, opts));
//	}
//
//	public static void printRule(CommandSender sender) {
//		Message.GAME_RULE_TITLE_0.print(sender, null);
//		for (EventScore event : EventScore.values()) {
//			Message.GAME_RULE_2.print(sender, event.getMessageOpts());
//		}
//	}
//}

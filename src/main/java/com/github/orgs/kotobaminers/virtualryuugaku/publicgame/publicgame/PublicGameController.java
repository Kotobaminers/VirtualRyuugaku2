package com.github.orgs.kotobaminers.virtualryuugaku.publicgame.publicgame;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;

public class PublicGameController {
	private PublicGame game = new PublicGamePreperation();

	enum PublicGameMode {PREPERATION, FIND_PEOPLE, ANKI, END;
		public static PublicGameMode giveNext(PublicGameMode mode) {
			Integer position = 0;
			for (PublicGameMode search : PublicGameMode.values()) {
				if (search.equals(mode)) {
					if (position < PublicGameMode.values().length - 2) {
						return PublicGameMode.values()[position + 1];
					} else {
						return PublicGameMode.PREPERATION;
					}
				}
				position++;
			}
			return PublicGameMode.PREPERATION;
		}

	}
	private PublicGameMode mode = PublicGameMode.PREPERATION;
	private static PublicGameStage stage =  new PublicGameStage();


	public void loadStage(String stageName) {
		try {
			stage = new PublicGameStage();
			stage.conversations = ControllerConversation.getConversations(stageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadGame() {
		game = new PublicGamePreperation();
		switch(mode) {
		case PREPERATION:
			game = new PublicGamePreperation();
			break;
		case FIND_PEOPLE:
			break;
		case ANKI:
			break;
		case END:
			break;
		default:
			break;
		}
	}

	public void continueGame() {
		game.continueGame();
		if (game.isFinished()) {
			game.finishGame();
			mode = PublicGameMode.giveNext(mode);
		}
	}

	public void broadcastStart() {

	}

	public void finishGame() {

	}

}

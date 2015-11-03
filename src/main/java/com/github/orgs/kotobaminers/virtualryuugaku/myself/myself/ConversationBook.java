package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.MessengerGeneral.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ControllerConversation;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMyself;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Effects;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class ConversationBook {
	//Fields for the book itself
	public List<List<String>> pages = new ArrayList<List<String>>();
	public ConversationMyself conversation = new ConversationMyself();

	//Fields for handling
	private Player holder;
	public BookMeta book = null;
	public static final String separator = "\n";

	public ConversationBook() {}
	private ConversationBook(BookMeta meta) {
		book = meta;
	}

	public ConversationBook createConversatinBook(Player player) throws Exception {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		BookMeta meta = loadBook(player);
		ConversationBook conversationBook = new ConversationBook(meta);
		conversationBook.pages = conversationBook.loadPages(meta);

		if(conversationBook.isValid()) {
			if(conversationBook.book.hasPages()) {
				conversationBook.holder = player;
				conversationBook.conversation = conversationBook.loadConversations();
			}
		} else {
			Message.BOOK_INVALID_0.print(player, null);
			Effects.playSound(player, Scene.BAD);
			throw new Exception("Invalid book.");
		}
		conversationBook.printDebug();
		return conversationBook;
	}

	public static void giveConversationBookEmpty(Player player, String stage)  {
		if (player.getItemInHand().getType().equals(Material.AIR)) {
			ItemStack item = new ItemStack(Material.BOOK_AND_QUILL);
			BookMeta book = (BookMeta) item.getItemMeta();
			String page = player.getName() + "\n" + stage.toUpperCase();
			book.setPages(Arrays.asList(page));
			item.setItemMeta(book);
			player.setItemInHand(item);
			Message.BOOK_GET_0.print(player, null);;
			Effects.playSound(player, Scene.APPEAR);
		} else {
			Message.COMMON_NOT_AIR_IN_HAND_0.print(player, null);
		}
	}

	private static BookMeta loadBook(Player player) throws Exception {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		BookMeta book = null;
		ItemStack item = player.getItemInHand();
		if(item.getType().equals(Material.BOOK_AND_QUILL) || item.getType().equals(Material.WRITTEN_BOOK)) {
			BookMeta tmp = (BookMeta) item.getItemMeta();
			if(tmp.hasPages()) {
				book = tmp;
				return book;
			}
		} else {
			Message.BOOK_NOT_IN_HAND_0.print(player, null);
		}
		throw new Exception("Invalid Book");
	}

//	private String loadPage(Integer number) throws Exception{
//		UtilitiesProgramming.printDebugMessage("", new Exception());
//		String black = "";
//		if(number <= book.getPageCount()) {
//			String section = "§";
//			String page = book.getPage(number);
//
//			List<Integer> index = new ArrayList<Integer>();
//			for (int i = 0; i < page.length(); i++) {
//				String search = page.substring(i, i+1);
//				if (search.equalsIgnoreCase(section)) {
//					i++;
//				} else {
//					index.add(i);
//				}
//			}
//
//			for(int i : index) {
//				black += page.substring(i, i+1);
//			}
//			UtilitiesProgramming.printDebugMessage(black, new Exception());
//			System.out.println(index);
//		} else {
//			throw new Exception();
//		}
//		return black;
//	}

	private List<List<String>> loadPages(BookMeta meta) {
		List<List<String>> pages = new ArrayList<List<String>>();
		if (0 < meta.getPageCount()) {
			for (int i = 1; i <= book.getPageCount(); i++) {
				String black = "";
				String section = "§";
				String page = meta.getPage(i);

				List<Integer> index = new ArrayList<Integer>();
				for (int j = 0; j < page.length(); j++) {
					String search = page.substring(j, j+1);
					if (search.equalsIgnoreCase(section)) {
						j++;
					} else {
						index.add(j);
					}
				}

				for(int j : index) {
					black += page.substring(j, j+1);
				}
				List<String> lines = new ArrayList<String>();
				lines.addAll(Arrays.asList(black.split(separator)));
				pages.add(lines);
			}
		}
		return pages;
	}

	public boolean isMine() {
		if(holder.getName().equalsIgnoreCase(getOwner())) {
			return true;
		}
		return false;
	}

	private String getOwner() {
		String owner = "";
		if (0 < pages.size()) {
			if (0 < pages.get(0).size()) {
				owner = pages.get(0).get(0);
			}
		}
		return owner;
	}

	private String getStage() {
		String stage = "";
		if (0 < pages.size()) {
			if (1 < pages.get(0).size()) {
				stage = pages.get(0).get(1);
			}
		}
		return stage;
	}

	private ConversationMyself loadConversations() {
		ConversationMyself conversation = new ConversationMyself();
		if(1 < pages.size()) {
			List<List<String>> talks = new ArrayList<List<String>>();
			for(Integer index = 1; index < pages.size(); index++) {
				talks.add(pages.get(index));
			}
			conversation = createConversation(talks);
		}
		return conversation;
	}

	private ConversationMyself createConversation(List<List<String>> pages) {
		ConversationMyself conversation = new ConversationMyself();
		conversation.stageName = getStage();
		conversation.editor = Arrays.asList(getOwner());

		List<Talk> talks = new ArrayList<Talk>();
		for(List<String> page : pages) {
			String en = "";
			String kana = "";
			String kanji = "";
			if(2 < page.size()) {
				kanji = page.get(2);
			}
			if(1 < page.size()) {
				Talk talk = new Talk();
				en = page.get(0);
				kana = page.get(1);
				List<String> tips = new ArrayList<String>();
				talk.description = Description.create(kanji, kana, en, tips);
				talk.name = getOwner();
				talks.add(talk);
			} else {
				holder.sendMessage("ERROR in a page.");
			}
		}
		conversation.listTalk = talks;
		return conversation;
	}

	public boolean isValid() {
		UtilitiesProgramming.printDebugMessage("" + pages, new Exception());
		if (1 < pages.size()) {
			UtilitiesProgramming.printDebugMessage("", new Exception());
			List<String> info = pages.get(0);
			if (1 < info.size()) {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				for (int i = 1; i < pages.size(); i++) {
					UtilitiesProgramming.printDebugMessage("", new Exception());
					List<String> lines = pages.get(i);
					UtilitiesProgramming.printDebugMessage("" + lines.size(), new Exception());
					if (!(1 < lines.size())) {
						return false;
					}
				}
				String stage = info.get(1);
				if (ControllerConversation.existsMyselfStage(stage)) {
					return true;
				}
			}
		}
		return false;
	}


	private void printDebug() {
		String message = "OWNER: " + getOwner() + " STAGE: " + getStage();
		UtilitiesProgramming.printDebugMessage(message, new Exception());
		UtilitiesProgramming.printDebugConversation(conversation);
	}


}

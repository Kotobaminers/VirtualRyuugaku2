package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.Talk;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class ConversationBook {
	//Fields for the book itself
	public String owner = "";
	public String stage = "";
	public ConversationMyself conversation = new ConversationMyself();

	//Fields for handling
	private Player holder;
	public BookMeta book = null;
	public static final String separator = "\n";

	private ConversationBook() {};

	public static ConversationBook createConversatinBook(Player player) {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		ConversationBook conversationBook = new ConversationBook();
		conversationBook.holder = player;
		conversationBook.book = conversationBook.loadBook();

		if(conversationBook.book instanceof BookMeta) {
			if(conversationBook.book.hasPages()) {
				conversationBook.owner = conversationBook.loadOwner();
				conversationBook.stage = conversationBook.loadStage();
				conversationBook.conversation = conversationBook.loadConversations();
				UtilitiesProgramming.printDebugMessage("", new Exception());
			}
		}
		conversationBook.printDebug();;

		return conversationBook;
	}

	public void update() {
	}

	private BookMeta loadBook() {
		UtilitiesProgramming.printDebugMessage("", new Exception());
		BookMeta book = null;
		ItemStack item = holder.getItemInHand();
		if(item.getType().equals(Material.BOOK_AND_QUILL) || item.getType().equals(Material.WRITTEN_BOOK)) {
			BookMeta tmp = (BookMeta) item.getItemMeta();
			if(tmp.hasPages()) {
				book = tmp;
			}

		}
		return book;
	}

	private String loadPage(Integer number) throws Exception{
		UtilitiesProgramming.printDebugMessage("", new Exception());
		String black = "";
		if(number <= book.getPageCount()) {
			String section = "§";
			String page = book.getPage(number);

			List<Integer> index = new ArrayList<Integer>();
			for (int i = 0; i < page.length(); i++) {
				String search = page.substring(i, i+1);
				if (search.equalsIgnoreCase(section)) {
					i++;
				} else {
					index.add(i);
				}
			}

			for(int i : index) {
				black += page.substring(i, i+1);
			}
			UtilitiesProgramming.printDebugMessage(black, new Exception());
			System.out.println(index);
		} else {
			throw new Exception();
		}
		return black;
	}

	public boolean isMine() {
		UtilitiesProgramming.printCharCode(owner);
		UtilitiesProgramming.printCharCode(holder.getName());
		if(holder.getName().equalsIgnoreCase(owner)) {
			return true;
		}
		return false;
	}

	private String loadOwner() {
		String owner = "";
		try {
			String page = loadPage(1);
			String[] strings = page.split(separator);
			if(0 < strings.length) {
				owner = strings[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return owner;
	}

	private String loadStage() {
		String stage = "";
		try {
			String page = loadPage(1);
			String[] strings = page.split(separator);
			if(1 < strings.length) {
				stage = strings[1];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stage;
	}

	private ConversationMyself loadConversations() {
		ConversationMyself conversation = new ConversationMyself();
		if(1 < book.getPageCount()) {
			List<List<String>> pages = new ArrayList<List<String>>();
			List<String> lines = new ArrayList<String>();
			for(Integer index = 2; index <= book.getPageCount(); index++) {
				try {
					lines = Arrays.asList(loadPage(index).split(separator));
				} catch (Exception e) {
					e.printStackTrace();
				}
				pages.add(lines);
			}
			conversation = createConversation(pages);
		}
		return conversation;
	}

	private ConversationMyself createConversation(List<List<String>> pages) {
		ConversationMyself conversation = new ConversationMyself();
		conversation.stage = stage;
		conversation.editor = Arrays.asList(owner);

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
				talk.name = owner;
				en = page.get(0);
				kana = page.get(1);
				List<String> tips = new ArrayList<String>();
				talk.description = Description.create(kanji, kana, en, tips);
				talks.add(talk);
			} else {
				holder.sendMessage("ERROR in a page.");
			}
		}
		conversation.listTalk = talks;
		return conversation;
	}

	private void printDebug() {
		String message = "OWNER: " + owner + " STAGE: " + stage;
		UtilitiesProgramming.printDebugMessage(message, new Exception());
		UtilitiesProgramming.printDebugConversation(conversation);
	}


}

package com.github.orgs.kotobaminers.virtualryuugaku.myself.myself;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.orgs.kotobaminers.virtualryuugaku.conversation.conversation.ConversationMyself;

public class DataMyself {
	public String name = "";
	public UUID id = null;
	public Map<String, ConversationMyself> mapConversations = new HashMap<String, ConversationMyself>();
}
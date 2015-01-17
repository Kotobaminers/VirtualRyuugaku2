package com.github.orgs.kotobaminers.virtualryuugaku.talker.comment;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesProgramming;

public class DataComment {
	public String sender = "";
	public String expression = "";
	public CommentState state = CommentState.NEW;
	public enum CommentState {
		NEW, DONE;
		public static CommentState lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return CommentState.valueOf(name.toUpperCase());
			} catch (Exception e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return CommentState.NEW;
			}
		}
	}
	public Boolean isValid() {
		if(0 < sender.length() && 0 < expression.length()) {
			return true;
		}
		return false;
	}

	public Boolean isSame(DataComment data) {
		if(sender.equalsIgnoreCase(data.sender) && expression.equalsIgnoreCase(data.expression) && state.equals(data.state)) {
			return true;
		}
		return false;
	}

}
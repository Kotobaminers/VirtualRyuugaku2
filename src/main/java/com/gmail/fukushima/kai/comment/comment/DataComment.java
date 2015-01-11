package com.gmail.fukushima.kai.comment.comment;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class DataComment {
	public String sender = "";
	public String expression = "";
	public CommentState state = CommentState.NEW;
	public enum CommentState {
		NEW, OLD;
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
		if(0 < editor.length()) {
			return true;
		}
		return false;
	}
	public Boolean isSame(DataComment data) {
		if(editor.equalsIgnoreCase(data.editor) && id.equals(data.id) && sender.equalsIgnoreCase(data.sender) && expression.equalsIgnoreCase(data.expression) && state.equals(data.state)) {
			return true;
		}
		return false;
	}
	public void printInfo(Player player) {
		player.sendMessage("[Comment Info] " + "From: " + this.sender + "To: " + this.editor + " STATE: " + this.state);
		player.sendMessage(" COMMENT: " + this.expression);
	}

	public String editor = "";
	public Integer id = 0;
}
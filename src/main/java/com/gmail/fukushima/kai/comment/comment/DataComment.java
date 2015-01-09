package com.gmail.fukushima.kai.comment.comment;

import org.bukkit.entity.Player;

import com.gmail.fukushima.kai.utilities.utilities.UtilitiesProgramming;

public class DataComment {
	public String owner = "";
	public Integer id = 0;
	public String sender = "";
	public String comment = "";
	public CommentState state = CommentState.NEW;
	public enum CommentState {
		NEW, OLD;
		public static CommentState lookup(String name) {
			try {
				UtilitiesProgramming.printDebugMessage("", new Exception());
				return CommentState.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				UtilitiesProgramming.printDebugMessage(e.toString(), new Exception());
				return CommentState.NEW;
			}
		}
	}
	public Boolean isValid() {
		if(0 < owner.length()) {
			return true;
		}
		return false;
	}
	public Boolean isSame(DataComment data) {
		if(owner.equalsIgnoreCase(data.owner) && id.equals(data.id) && sender.equalsIgnoreCase(data.sender) && comment.equalsIgnoreCase(data.comment) && state.equals(data.state)) {
			return true;
		}
		return false;
	}
	public void printInfo(Player player) {
		player.sendMessage("[Comment Info] " + "From: " + this.sender + "To: " + this.owner + " STATE: " + this.state);
		player.sendMessage(" COMMENT: " + this.comment);
	}
}
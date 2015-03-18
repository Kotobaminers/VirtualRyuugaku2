package com.github.orgs.kotobaminers.virtualryuugaku.vrgnpc.vrgnpc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Description;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.Expression;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.UtilitiesGeneral;

public class VRGNPC {
	public String name = "";
	public String stage = "";
	public Integer id = 0;
	public List<String> editor = new ArrayList<String>();
	public List<Description> listDescription = new ArrayList<Description>();

	public void printInformation(Player player) {
		player.sendMessage("NAME: " + name + ", STAGE: " + stage + ", ID: " + id + "EDITOR: " + UtilitiesGeneral.joinStrings(editor, ", "));
		for(Description description: listDescription) {
			player.sendMessage(description.express(Expression.EN));
			player.sendMessage(description.express(Expression.ROMAJI));
		}
	}
}
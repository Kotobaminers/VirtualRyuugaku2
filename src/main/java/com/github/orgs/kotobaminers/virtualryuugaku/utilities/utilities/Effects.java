package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;

public class Effects {
	public static void playSound(Player player, Scene scene) {
		SoundMeta meta = new SoundMeta(scene);
		player.playSound(player.getLocation(), meta.sound, meta.volume, meta.pitch);
	}
}

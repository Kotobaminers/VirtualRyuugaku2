package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.FireworkUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.FireworkUtility.FireworkColor;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;

public class Effects {
	public static void playSound(Player player, Scene scene) {
		SoundMeta meta = new SoundMeta(scene);
		player.playSound(player.getLocation(), meta.sound, meta.volume, meta.pitch);
	}

	public static void shootFirework(Player player) {
		FireworkUtility.shootFirework(player.getWorld(), player.getLocation(), Type.BALL_LARGE, FireworkColor.GREEN, FireworkColor.AQUA, 0);
	}
}

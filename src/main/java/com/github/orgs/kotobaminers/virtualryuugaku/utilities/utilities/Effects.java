package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import org.bukkit.Effect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.FireworkUtility.FireworkColor;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;

public class Effects {
	public static void playSound(Player player, Scene scene) {
		SoundMeta meta = new SoundMeta(scene);
		player.playSound(player.getLocation(), meta.sound, meta.volume, meta.pitch);
	}
	@Deprecated
	public static void effectTalk(Player player, Integer id) {
		NPCHandler.findNPC(id).ifPresent(n -> {
			Location location = n.getStoredLocation();
			Utility.lookAt(player, location);
			player.getWorld().spigot().playEffect(location.add(0, 2, 0), Effect.NOTE, 25, 10, 0, 0, 0, 0, 1, 10);
			player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);}
		);
	}

	public static void shootFirework(Player player) {
		FireworkUtility.shootFirework(player.getWorld(), player.getLocation(), Type.BALL_LARGE, FireworkColor.GREEN, FireworkColor.AQUA, 0);
	}
}

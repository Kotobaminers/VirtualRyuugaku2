package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import org.bukkit.Effect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.FireworkUtility.FireworkColor;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.SoundMeta.Scene;

public class Effects {
	public static void playSound(Player player, Scene scene) {
		SoundMeta meta = new SoundMeta(scene);
		player.playSound(player.getLocation(), meta.sound, meta.volume, meta.pitch);
	}
	public static void effectTalk(Player player, Integer id) {
		try {
			player.getWorld().playEffect(NPCHandler.getNPC(id).getStoredLocation().add(0, 2, 0), Effect.SMOKE, 22);//data is 22(No direction value).
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void shootFirework(Player player) {
		FireworkUtility.shootFirework(player.getWorld(), player.getLocation(), Type.BALL_LARGE, FireworkColor.GREEN, FireworkColor.AQUA, 0);
	}
}

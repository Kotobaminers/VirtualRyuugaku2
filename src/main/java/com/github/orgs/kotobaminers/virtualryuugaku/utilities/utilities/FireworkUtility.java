package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;


public class FireworkUtility {
	public static void shootFirework(World world, Location location, Type type, FireworkColor fireworkColor1, FireworkColor fireworkColor2, int power) {
	Firework fw = (Firework) world.spawn(location, Firework.class);
	FireworkMeta fwm = fw.getFireworkMeta();
	Random random = new Random();
	Color color1 = FireworkColor.getColor(fireworkColor1);
	Color color2 = FireworkColor.getColor(fireworkColor2);
	FireworkEffect effect = FireworkEffect.builder().flicker(random.nextBoolean()).withColor(color1).withFade(color2).with(type).trail(random.nextBoolean()).build();
	fwm.addEffect(effect);
	fwm.setPower(power);
	fw.setFireworkMeta(fwm);
	}

	public enum FireworkColor {AQUA, BLACK, BLUE, FUCHSIA, GREY, GREEN, LIME, MAROON, NAVY, OLIVE, PURPLE, RED, SILBER, TEAL, WHITE, YELLOW;
		public static Color getColor(FireworkColor colors) {
			Color color = null;
			switch (colors) {
			case AQUA:
				color = Color.AQUA;
				break;
			case BLACK:
				color = Color.BLACK;
				break;
			case BLUE:
				color = Color.BLUE;
				break;
			case FUCHSIA:
				color = Color.FUCHSIA;
				break;
			case GREEN:
				color = Color.GREEN;
				break;
			case GREY:
				color = Color.GREEN;
				break;
			case LIME:
				color = Color.LIME;
				break;
			case MAROON:
				color = Color.MAROON;
				break;
			case NAVY:
				color = Color.NAVY;
				break;
			case OLIVE:
				color = Color.OLIVE;
				break;
			case PURPLE:
				color = Color.PURPLE;
				break;
			case RED:
				color = Color.RED;
				break;
			case SILBER:
				color = Color.SILVER;
				break;
			case TEAL:
				color = Color.TEAL;
				break;
			case WHITE:
				color = Color.WHITE;
				break;
			case YELLOW:
				color = Color.YELLOW;
				break;
			default:
				color = Color.WHITE;
				break;
			}
			return color;
		}
		public static Color getColor(String name) {
			FireworkColor colors = FireworkColor.valueOf(name);
			return getColor(colors);
		}
	}
}

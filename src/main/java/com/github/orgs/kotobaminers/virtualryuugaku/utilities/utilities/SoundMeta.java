package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import org.bukkit.Sound;

public class SoundMeta {
	Sound sound = Sound.LEVEL_UP;
	float volume = 1;
	float pitch = 1;
	public enum Scene {GOOD, BAD, NOTICE, APPEAR, }

	public SoundMeta(Scene scene) {
		switch(scene) {
		case BAD:
			sound = Sound.ITEM_BREAK;
			break;
		case GOOD:
			sound = Sound.LEVEL_UP;
			break;
		case NOTICE:
			sound = Sound.NOTE_PIANO;
			break;
		case APPEAR:
			sound = Sound.CHICKEN_EGG_POP;
			break;
		default:
			break;
		}
	}
}
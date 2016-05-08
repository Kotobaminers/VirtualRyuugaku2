package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

public abstract class TalkSentence extends HolographicSentence {
	private Japanese japanese;
	private English english;
	protected static final String EMPTY_KANJI = "Enter kanji";
	protected static final String EMPTY_KANA = "Enter kana";
	protected static final String EMPTY_ROMAJI = "Enter romaji";
	protected static final String EMPTY_EN = "Enter English";
	private boolean key = false;

	public TalkSentence(List<String> kanji, List<String> kana, List<String> en, Integer id) {
		this.japanese = new Japanese(kanji, kana);
		this.english = new English(en);
		this.id = id;
	}

	public static boolean isEmpty(TalkSentence sentence) {
		List<String> empties = Arrays.asList(EMPTY_EN, EMPTY_KANA, EMPTY_KANJI, EMPTY_ROMAJI);
		if(sentence.japanese.getLine().stream().allMatch(s -> empties.contains(s))
			&& sentence.english.getLine().stream().allMatch(s -> empties.contains(s))) {
			return true;
		}
		return false;
	}

	public Optional<List<String>> getLines(SpellType spell) {
		switch(spell) {
		case EN:
			return Optional.ofNullable(getEnglish().getLine());
		case KANA:
		case KANJI:
		case ROMAJI:
			return getJapanese().getLine(Arrays.asList(spell));
		default:
			return Optional.empty();
		}
	}

	@Override
	public Optional<List<ItemStack>> giveSentenceIcons() {
		List<ItemStack> icons = Arrays.asList(SpellType.EN, SpellType.KANJI, SpellType.KANA, SpellType.ROMAJI).stream()
			.map(spell -> {
				ItemStack item = spell.getIconItem();
				ItemMeta itemMeta = item.getItemMeta();
				this.getLines(spell).ifPresent(line -> itemMeta.setDisplayName(line.get(0)));
				item.setItemMeta(itemMeta);
				return item;})
			.collect(Collectors.toList());
		if (0 < icons.size()) {
			return Optional.of(icons);
		}
		return Optional.empty();
	}


	@Override
	public void update(String line, SpellType spell) {
		switch(spell) {
		case EN:
			english.update(line, spell);
			return;
		case KANA:
		case KANJI:
		case ROMAJI:
			japanese.update(line, spell);
			return;
		default:
			return;
		}
	}
	@Override
	public void playEffect(Player player, Location location) {
		Utility.lookAt(player, location);
//		player.getWorld().spigot().playEffect(location.add(0, 2, 0), Effect.NOTE, 25, 10, 0, 0, 0, 0, 1, 10);
		player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
	}
	public Japanese getJapanese() {
		return japanese;
	}
	public English getEnglish() {
		return english;
	}
	public boolean isKey() {
		return key;
	}
	public void setKey(boolean key) {
		this.key = key;
	}
}

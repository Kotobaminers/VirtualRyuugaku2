package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.SentenceHologram;

import net.citizensnpcs.api.npc.NPC;

public abstract class HolographicSentence {
	protected Integer id = 0;
	private String unit ="";

	public abstract void playEffect(Player player, Location location);
	public abstract void update(String line, SpellType spell);
	public abstract List<String> getHolographicLines(List<SpellType> spells);
	public abstract Optional<List<ItemStack>> giveIcons();
	public abstract Optional<List<ItemStack>> giveSentenceIcons();
	public abstract Optional<List<ItemStack>> giveEmptyIcons();

	public abstract void registerHologram(SentenceHologram hologram, NPC npc, List<HolographicSentence> sentences);
	public abstract Location getTalkerLocation(NPC npc);

	public String getUnitName() {
		return unit;
	}
	public void setUnitName(String unit) {
		this.unit = unit;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
package com.github.orgs.kotobaminers.virtualryuugaku.data.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCHandler;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.SentenceHologram;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage.PathStage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

import net.citizensnpcs.api.npc.NPC;

public class QuestionSentence extends HolographicSentence{
	private String question = "";
	private List<String> answers;

	public static Optional<QuestionSentence> create(ConfigurationSection section, List<Integer> index) {
		if(section.isString(PathStage.Q.toString()) && section.isList(PathStage.A.toString())) {
			QuestionSentence instance = new QuestionSentence();
			instance.question = section.getString(PathStage.Q.name());
			instance.answers = section.getStringList(PathStage.A.name());
			instance.id = index.get(index.size() - 1);
			return Optional.of(instance);
		}
		return Optional.empty();
	}

	public Function<String, Boolean> validate = (answer) ->
		answers.stream().anyMatch(a -> a.equalsIgnoreCase(answer));

	@Override
	public void playEffect(Player player, Location location) {
		Utility.lookAt(player, location);
		player.getWorld().spigot().playEffect(location.add(0, 2, 0), Effect.NOTE, 25, 10, 0, 0, 0, 0, 1, 10);
		player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1F, 1F);
	}

	public String getQuestion() {
		return question;
	}

	@Override
	public void update(String line, SpellType spell) {
		this.question = line;
	}

	@Override
	public List<String> getHolographicLines(List<SpellType> spells) {
		return Arrays.asList(getQuestion());
	}

	@Override
	public Optional<List<ItemStack>> giveIcons() {
		ItemStack item = new ItemStack(Material.WOOL, 1, (short) 5);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(getQuestion());
		meta.setLore(Arrays.asList("Question"));
		item.setItemMeta(meta);
		return Optional.ofNullable(Arrays.asList(item));
	}

	@Override
	public Optional<List<ItemStack>> giveSentenceIcons() {
		return Optional.empty();
	}

	@Override
	public Optional<List<ItemStack>> giveEmptyIcons() {
		return Optional.empty();
	}

	@Override
	public void registerHologram(SentenceHologram hologram, NPC npc, List<HolographicSentence> sentences) {
		HologramStorage.holograms.put(sentences.stream().map(s -> s.getId()).collect(Collectors.toList()), hologram);
	}

	@Override
	public Location getHologramLocation(NPC npc) {
		return NPCHandler.findNPC(id).get().getStoredLocation();
	}
}


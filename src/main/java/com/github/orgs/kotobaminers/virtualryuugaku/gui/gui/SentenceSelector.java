package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HelperSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.PlayerSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.QuestionSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceEditor;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceEditor.EditMode;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.Unit;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.UnitStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerData;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;

import net.citizensnpcs.api.npc.NPC;

public abstract class SentenceSelector extends VRGGUI {
	public abstract List<List<ItemStack>> getIcons();
	public abstract List<ItemStack> getOptionIcons();

	public static Optional<SentenceSelector> create(NPC npc) {
		Unit unit = UnitStorage.findDisplayedUnit(npc).orElse(null);
		if (unit == null) return Optional.empty();

		unit.addEmptyPlayerSentencesIfAbsent(npc);

		List<HolographicSentence> sentences = unit.findDisplayedLS(npc).orElse(new ArrayList<>());
		if (0 < sentences.size()) {
			if(sentences.stream().allMatch(sentence -> sentence instanceof PlayerSentence)) {
				return Optional.of(PlayerSentenceSelector.create(unit, sentences));
			}
			if(sentences.stream().allMatch(sentence -> sentence instanceof HelperSentence || sentence instanceof QuestionSentence)) {
				return Optional.of(new HelperSentenceSelector(sentences));
			}
		}
		return Optional.empty();
	}

	@Override
	public Inventory createInventory() {
		Inventory inventory = Bukkit.createInventory(null, MAX_SIZE, getTitle());
		Integer low = 0;
		for (List<ItemStack> list : getIcons()) {
			Integer column = 0;
			for (ItemStack item : list) {
				item.setAmount(low + 1);
				inventory.setItem(low + column++ * 9, item);
			}
			low++;
		}

		Integer position = new Integer(MAX_SIZE - 1);
		for (ItemStack item : getOptionIcons()) {
			inventory.setItem(position--, item);
		}
		return inventory;
	}

	public static Optional<SentenceSelector> create(InventoryClickEvent event) {
		switch(event.getInventory().getTitle()) {
		case PlayerSentenceSelector.TITLE:
			return Optional.of(PlayerSentenceSelector.create());
		case HelperSentenceSelector.TITLE:
			return Optional.of(new HelperSentenceSelector());
		}
		return Optional.empty();
	}

	public Optional<SentenceEditor> createEditor(InventoryClickEvent event, GUIIcon icon) {
		int id = PlayerDataStorage.getPlayerData((Player) event.getWhoClicked()).getSelectId();
		int index = event.getRawSlot() % 9;
		Optional<List<HolographicSentence>> sentences = NPCUtility.findNPC(id)
			.flatMap(npc -> UnitStorage.findDisplayedUnit(npc)
				.flatMap(unit -> unit.findDisplayedLS(npc)));
		if (!sentences.isPresent()) return Optional.empty();
		if (sentences.get().stream().allMatch(s -> s instanceof HelperSentence)) {
			sentences.get().add(QuestionSentence.createEmpty(sentences.get().get(0).getId()));
		}
		Optional<HolographicSentence> sentence = sentences
				.flatMap(list -> Optional.ofNullable(list.get(index)));
		if (!sentence.isPresent()) return Optional.empty();
		switch (icon) {
			case EN:
				return Optional.of(new SentenceEditor(sentences, sentence, EditMode.EN));
			case KANJI:
				return Optional.of(new SentenceEditor(sentences, sentence, EditMode.KANJI));
			case KANA:
				return Optional.of(new SentenceEditor(sentences, sentence, EditMode.KANA));
			case HELPER_QUESTION:
				return Optional.of(new SentenceEditor(sentences, sentence, EditMode.QUESTION));
			case PLAYER_SKULL:
				return Optional.of(new SentenceEditor(sentences, sentence, EditMode.NONE));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void executeClickedEvent(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			PlayerData data = PlayerDataStorage.getPlayerData(player);
			GUIIcon.create(event).ifPresent(icon -> {
				switch (icon) {
				case EN:
				case KANA:
				case KANJI:
				case HELPER_QUESTION:
					Optional<SentenceEditor> editor = create(event).flatMap(selector -> selector.createEditor(event, icon));
					if(editor.isPresent()) {
						if (editor.get().canEdit(player)) {
							PlayerDataStorage.openEditor(player, editor);
							player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
							return;
						}
					}
					Message.INVALID_1.print(Arrays.asList("You cannot edit this"), player);
					player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
					return;
				case ROMAJI:
				case PLAYER_QUESTION:
					Message.INVALID_1.print(Arrays.asList("You cannot edit this."), player);
					player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
					return;
				case PLAYER_SKULL:
					Optional<SentenceEditor> editor2 = SentenceSelector.create(event).flatMap(sel -> sel.createEditor(event, icon));
					if (editor2.isPresent()) {
						if (editor2.get().canEdit(player)) {
							PlayerDataStorage.openEditor(player, editor2);
							NPCUtility.findNPC(data.getSelectId()).ifPresent(npc -> SentenceOptionSelector.create(npc, event).ifPresent(selector -> player.openInventory(selector.createInventory())));
							player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
						} else {
							Message.INVALID_1.print(Arrays.asList("You cannot edit this."), player);
							player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
						}
					}
					return;
				case FREE_UP:
					NPCUtility.findNPC(data.getSelectId())
						.ifPresent(npc -> {
							NPCUtility.changeNPCAsEmpty(npc);
							player.getWorld().spigot().playEffect(npc.getStoredLocation().clone().add(0,1,0), Effect.EXPLOSION, 22, 22, (float) 0.5, (float) 0.5, (float) 0.5, (float) 0, 20, 10);
							player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1f, 1f);
						});
					return;
				case RESPAWN:
					NPCUtility.findNPC(data.getSelectId())
						.ifPresent(npc -> {
							Optional<String> skin = NPCUtility.findSkinName(npc);
							Optional<UUID> uuid = NPCUtility.findSkinUUID(npc);
							npc.despawn();
							if (skin.isPresent() && uuid.isPresent()) {
								NPCUtility.refreshSkinMeta(npc);
								npc.data().set("cached-skin-uuid-name", skin.get());
								npc.data().set("cached-skin-uuid", uuid.get().toString());
							}
							npc.spawn(npc.getStoredLocation());
						});
					return;
//				case SPAWN_NPC:
//					player.openInventory(new SpawnNPCSelector().createInventory());
//					player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
//					return;
				case ROLE_PLAY:
					player.openInventory(new RolePlaySelector().createInventory());
					return;
				default:
					break;
				}
			});
		}
	}
}


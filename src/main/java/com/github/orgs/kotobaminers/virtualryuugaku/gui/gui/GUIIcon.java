package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.Enums.SpellType;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.common.common.VRGMessenger.Message;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HelperSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.HolographicSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.QuestionSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceEditor;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceEditor.EditMode;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities.Utility;

public enum GUIIcon {
	SPEAKER(Material.SKULL_ITEM, 3, "Speaker", null),
	EN(Material.WOOL, 3, "Enter English", null),
	KANJI(Material.WOOL, 14, "Enter kanji", null),
	KANA(Material.WOOL, 1, "Enter kana", null),
	ROMAJI(Material.WOOL, 2, "Enter romaji", null),
	QUESTION(Material.WOOL, 5, "Question", null),

	PREPEND_SENTENCE(Material.WOOL, 8, "Prepend a new sentence", null),
	APPEND_SENTENCE(Material.WOOL, 7, "Append a new sentence", null),
	REMOVE_SENTENCE(Material.GLASS, 0, "Remove this sentence", null),
	CHANGE_SPEAKER(Material.SKULL_ITEM, 0, "Chage the speaker", null),
	BACK(Material.BARRIER, 0, "Back to the previous menu", null),

	LEARNER_QUESTION(Material.WOOL, 0, "Question", null),

	FREE_UP(Material.GLASS_BOTTLE, 0, "Free up this NPC", null),
	RESPAWN(Material.EMERALD, 0, "Reload this NPC", null),

	FREE(Material.FEATHER, 0, "Free", Arrays.asList("To Look Around")),
	TOUR(Material.COMPASS, 0, "Tour", Arrays.asList("To Join A Tour")),
	TRAINING(Material.IRON_SPADE, 0, "Training", Arrays.asList("To Start Training")),
	ANKI(Material.BOOK_AND_QUILL, 0, "Anki", Arrays.asList("To Memorize Sentences")),
	YOUR_NPC(Material.SKULL_ITEM, 4, "Your NPC", Arrays.asList("To Create Your NPC")),

	UNIT(Material.WOOD_DOOR, 0, "STAGE", null),
	OPTION(Material.RECORD_10, 0, "Option", null),

	DISPLAY_EN(Material.STAINED_GLASS, 3, "Display English", null),
	DISPLAY_KANJI(Material.STAINED_GLASS, 14, "Display kanji", null),
	DISPLAY_KANA(Material.STAINED_GLASS, 1, "Display kana", null),
	DISPLAY_ROMAJI(Material.STAINED_GLASS, 2, "Display romaji", null),

	;

	private Material material;
	private short data;
	private String displayName;
	private Optional<List<String>> lore;

	private GUIIcon(Material material, int data, String displayName, List<String> lore) {
		this.material = material;
		this.data = (short) data;
		this.displayName = displayName;
		this.lore = Optional.ofNullable(lore);
	}

	public static Optional<GUIIcon> create(InventoryClickEvent event) {
		return Stream.of(GUIIcon.values())
			.filter(i -> i.getMaterial().equals(event.getCurrentItem().getType()) && i.getData() == event.getCurrentItem().getDurability())
			.findFirst();
	}

	public ItemStack createItem() {
		ItemStack item = new ItemStack(this.material, 1, data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayName);
		lore.ifPresent(l -> meta.setLore(l));
		item.setItemMeta(meta);
		return item;
	}

	public Material getMaterial() {
		return material;
	}
	public short getData() {
		return data;
	}

	public void eventClicked(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		switch(this) {
		case EN:
		case KANA:
		case KANJI:
		case QUESTION:
			Optional<SentenceEditor> editor = SentenceSelector.create(event).flatMap(selector -> selector.createEditor(event, this));
			if(editor.isPresent()) {
				if (editor.get().canEdit(player)) {
					editor.get().updateEditMode(EditMode.EDIT, player);
					PlayerDataStorage.openEditor(player, editor);
					if (this.equals(QUESTION)) {
						Optional<HolographicSentence> sentence = editor.get().findSentence();
						if (!sentence.isPresent()) {
							List<HolographicSentence> sentences = editor.get().findSentences().get();
							if(sentences.stream().allMatch(s -> s instanceof HelperSentence)) {
								sentences.add(QuestionSentence.createEmpty(PlayerDataStorage.getPlayerData(player).getSelectId()));
							}
							sentence = editor.get().findSentence();
							if (!sentence.isPresent()) {
								Message.ERROR_1.print(Arrays.asList("Broken sentences"), player);
								return;
							}
						}
						if (sentence.get() instanceof QuestionSentence) {
							QuestionSentence question = (QuestionSentence) sentence.get();
							Message.EDITOR_1.print(Arrays.asList("To edit the question: [/vrg edit <QUESTION>]"), player);
							Message.EDITOR_1.print(Arrays.asList("To add an answer: [/vrg aa <ANSER>]"), player);
							Message.EDITOR_1.print(Arrays.asList("To remove an answer: [/vrg ra <ANSER>]"), player);
							Message.EDITOR_1.print(Arrays.asList("To delete the question: [/vrg dq]"), player);
							Message.EDITOR_1.print(Arrays.asList("Current asnwers: [" + String.join(", ", question.getAnswers()) + "]"), player);
						} else {
							Message.ERROR_1.print(Arrays.asList("Unconsidered sentence"), player);
						}
					} else {
						Message.EDITOR_1.print(Arrays.asList("To Edit This: [/vrg edit <SENTENCE>]"), player);
					}
					player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
					return;
				}
			}
			Message.INVALID_1.print(Arrays.asList("You cannot edit this"), player);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
			return;

		case ROMAJI:
			Message.INVALID_1.print(Arrays.asList("You cannot edit this."), player);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
			return;
		case SPEAKER:
			int id2 = PlayerDataStorage.getPlayerData(player).getSelectId();
			int index = event.getRawSlot() % 9;
			Optional<SentenceEditor> editor2 = Optional.of(SentenceEditor.create(id2, index, Optional.empty()));
			if (editor2.isPresent()) {
				if (editor2.get().canEdit(player)) {
					PlayerDataStorage.openEditor(player, editor2);
					player.openInventory(SentenceOptionSelector.create(index).createInventory());
					player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
				} else {
					Message.INVALID_1.print(Arrays.asList("You cannot edit this."), player);
					player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
				}
			}
			return;
		case APPEND_SENTENCE:
			PlayerDataStorage.getPlayerData(player).editor.ifPresent(e -> e.appendSentence(player));
			return;
		case PREPEND_SENTENCE:
			PlayerDataStorage.getPlayerData(player).editor.ifPresent(e -> e.prependSentence(player));
			return;
		case REMOVE_SENTENCE:
			PlayerDataStorage.getPlayerData(player).editor.ifPresent(e -> e.removeSentence(player));
			return;
		case CHANGE_SPEAKER:
			PlayerDataStorage.getPlayerData(player).editor.ifPresent(e -> e.updateEditMode(EditMode.CHANGE_ID, player));
			return;
		case BACK:
			String title = event.getInventory().getTitle();
			Optional<Inventory> inventory = Optional.empty();
			switch(title) {
				case SentenceOptionSelector.TITLE:
					inventory = NPCUtility.findNPC(PlayerDataStorage.getPlayerData(player).getSelectId())
						.flatMap(npc -> SentenceSelector.create(npc)
							.flatMap(selector -> Optional.ofNullable(selector.createInventory())));
					break;
				case OptionSelector.TITLE:
				case GameModeSelector.TITLE:
					inventory = Optional.ofNullable(new StageSelector().createInventory());
					break;
				default:
					break;
			}
			inventory.ifPresent(i -> {
				player.openInventory(i);
				player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
			});
			return;

		case LEARNER_QUESTION:
			return;
		case FREE_UP:
			NPCUtility.findNPC(PlayerDataStorage.getPlayerData(player).getSelectId())
				.ifPresent(npc -> {
					NPCUtility.changeNPCAsEmpty(npc);
					player.getWorld().spigot().playEffect(npc.getStoredLocation().clone().add(0,1,0), Effect.EXPLOSION, 22, 22, (float) 0.5, (float) 0.5, (float) 0.5, (float) 0, 20, 10);
					player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1f, 1f);
				});
			return;
		case RESPAWN:
			NPCUtility.findNPC(PlayerDataStorage.getPlayerData(player).getSelectId())
				.ifPresent(npc -> {
					Optional<String> skin = NPCUtility.findSkinName(npc);
					Optional<UUID> uuid = NPCUtility.findSkinUUID(npc);
					npc.despawn();
					if (skin.isPresent() && uuid.isPresent()) {
						NPCUtility.refreshSkinMeta(npc);
						System.out.println(skin.get() + " " + uuid.get().toString());
						npc.data().set("cached-skin-uuid-name", skin.get());
						npc.data().set("cached-skin-uuid", uuid.get().toString());
					}
					npc.spawn(npc.getStoredLocation());
				});
			return;

		case ANKI:
		case FREE:
		case TOUR:
		case TRAINING:
			SentenceStorage.findStage.apply(event.getCurrentItem().getItemMeta().getLore().get(0))
			.ifPresent(lls -> lls.stream()
				.findFirst().ifPresent(ls -> ls.stream()
					.findFirst().ifPresent(s ->
						NPCUtility.findNPC(s.getId())
							.ifPresent(npc -> {
								Utility.teleportToNPC((Player) event.getWhoClicked(), npc);
								Utility.sendTitle(
										(Player) event.getWhoClicked(),
										event.getCurrentItem().getItemMeta().getLore().get(0),
										this.lore.get().get(0));
							}))));
			return;
		case YOUR_NPC:
			if(SentenceStorage.playerIds.containsKey(event.getCurrentItem().getItemMeta().getLore().get(0))) {
				SentenceStorage.playerIds.get(event.getCurrentItem().getItemMeta().getLore().get(0)).stream()
					.findFirst().ifPresent(id -> NPCUtility.findNPC(id)
						.ifPresent(npc -> {
							Utility.teleportToNPC((Player) event.getWhoClicked(), npc);
							Utility.sendTitle(
									(Player) event.getWhoClicked(),
									event.getCurrentItem().getItemMeta().getLore().get(0),
									this.lore.get().get(0));
						}));
			}
			return;
		case UNIT:
			GameModeSelector.create(event.getCurrentItem().getItemMeta().getDisplayName())
				.ifPresent(s -> {
					player.openInventory(s.createInventory());
					player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
					return;
				});
			return;
		case DISPLAY_EN:
			PlayerDataStorage.getPlayerData(player).toggleSpellType(SpellType.EN);
			player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
			return;
		case DISPLAY_KANJI:
			PlayerDataStorage.getPlayerData(player).toggleSpellType(SpellType.KANJI);
			player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
			return;
		case DISPLAY_KANA:
			PlayerDataStorage.getPlayerData(player).toggleSpellType(SpellType.KANA);
			player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
			return;
		case DISPLAY_ROMAJI:
			PlayerDataStorage.getPlayerData(player).toggleSpellType(SpellType.ROMAJI);
			player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
			return;
		case OPTION:
			player.openInventory(	OptionSelector.create(player).createInventory());
			player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
			return;
		default:
			return;
		}
	}
}


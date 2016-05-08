package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.orgs.kotobaminers.virtualryuugaku.common.common.NPCUtility;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.SentenceEditor.EditMode;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.TalkSentence;
import com.github.orgs.kotobaminers.virtualryuugaku.data.data.UnitStorage;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerData;
import com.github.orgs.kotobaminers.virtualryuugaku.player.player.PlayerDataStorage;

import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class SentenceOptionSelector extends VRGGUI{
	public static final String TITLE = "Select Sentence Options!";

	private String key= OFF;
	private static final String ON = "" + ChatColor.GREEN + ChatColor.BOLD + "ON";
	private static final String OFF = "" + ChatColor.RED + ChatColor.BOLD + "OFF";

	SentenceOptionSelector() {};

	public static Optional<SentenceOptionSelector> create(NPC npc, InventoryClickEvent event) {
		SentenceOptionSelector selector = new SentenceOptionSelector();
		int index = event.getRawSlot()%9;
		return UnitStorage.findDisplayedUnit(npc).flatMap(unit -> unit.findHelperLS(npc.getId()))
			.map(ls -> ls.stream().filter(s -> s instanceof TalkSentence)
			.map(s -> (TalkSentence) s)
			.collect(Collectors.toList()))
			.map(s -> {
				if(index < s.size()) {
					if (s.get(index).isKey()) selector.key = ON;
					return selector;
				} else {
					return null;
				}
			});
	}

	@Override
	public String getTitle() {
		return TITLE;
	}
	@Override
	public Inventory createInventory() {
		Inventory inventory = Bukkit.createInventory(null, VRGGUI.MAX_SIZE, getTitle());
		inventory.addItem(createKeyIcon());
		getGUIIcons().stream().forEach(icon -> inventory.addItem(icon.createItem()));
		return inventory;
	}

	private ItemStack createKeyIcon() {
		ItemStack icon = GUIIcon.KEY.createItem();
		ItemMeta itemMeta = icon.getItemMeta();
		itemMeta.setLore(Arrays.asList(String.valueOf(key)));
		icon.setItemMeta(itemMeta);
		return icon;
	}

	public List<GUIIcon> getGUIIcons() {
		return Arrays.asList(
				GUIIcon.CHANGE_SPEAKER,
				GUIIcon.PREPEND_SENTENCE,
				GUIIcon.APPEND_SENTENCE,
				GUIIcon.REMOVE_SENTENCE,
				GUIIcon.BACK);
	}

	@Override
	public void executeClickedEvent(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			PlayerData data = PlayerDataStorage.getPlayerData(player);
			GUIIcon.create(event).ifPresent(icon -> {
				switch (icon) {
				case KEY:
					data.editor.ifPresent(e -> e.toggleKey(player));
					return;
				case APPEND_SENTENCE:
					data.editor.ifPresent(e -> e.appendSentence(player));
					return;
				case PREPEND_SENTENCE:
					data.editor.ifPresent(e -> e.prependSentence(player));
					return;
				case REMOVE_SENTENCE:
					data.editor.ifPresent(e -> e.removeSentence(player));
					return;
				case CHANGE_SPEAKER:
					data.editor.ifPresent(e -> e.updateEditMode(EditMode.CHANGE_ID, player));
					return;
				case FREE_UP:
					NPCUtility.findNPC(PlayerDataStorage.getPlayerData(player).getSelectId())
						.ifPresent(npc -> {
							NPCUtility.changeNPCAsEmpty(npc);
//							player.getWorld().spigot().playEffect(npc.getStoredLocation().clone().add(0,1,0), Effect.EXPLOSION, 22, 22, (float) 0.5, (float) 0.5, (float) 0.5, (float) 0, 20, 10);
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
				case BACK:
					NPCUtility.findNPC(data.getSelectId())
						.flatMap(npc -> SentenceSelector.create(npc)
						.map(sel -> sel.createInventory()))
						.ifPresent(i -> {
							player.openInventory(i);
							player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
						});
					return;
				default:
					break;
				}
			});
		}
	}
}

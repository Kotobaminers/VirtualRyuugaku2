package com.github.orgs.kotobaminers.virtualryuugaku.gui.gui;

public class SpawnNPCSelector /*extends VRGGUI*/ {
//	public static final String TITLE = "Spawn NPC Selector";
//
//	@Override
//	public String getTitle() {
//		return TITLE;
//	}
//
//	@Override
//	public Inventory createInventory() {
//		Inventory inventory = Bukkit.createInventory(null, MAX_SIZE, getTitle());
//		UnitStorage.units.keySet().stream().sorted()
//			.map(key -> UnitStorage.units.get(key))
//			.map(unit -> {
//				ItemStack item = GUIIcon.UNIT_DONE_MAX.createItem();
//				ItemMeta itemMeta = item.getItemMeta();
//				itemMeta.setDisplayName(unit.getName());
//				item.setItemMeta(itemMeta);
//				return item;})
//			.forEach(inventory::addItem);
//		int position = MAX_SIZE - 1;
//		for (ItemStack option : getOptions()) {
//			inventory.setItem(position--, option);
//		}
//		return inventory;
//	}
//
//	private List<ItemStack> getOptions() {
//		return Arrays.asList(GUIIcon.BACK).stream()
//			.map(icon -> icon.createItem())
//			.collect(Collectors.toList());
//	}
//
//	@Override
//	public void executeClickedEvent(InventoryClickEvent event) {
//		if(event.getWhoClicked() instanceof Player) {
//			Player player = (Player) event.getWhoClicked();
//			PlayerData data = PlayerDataStorage.getPlayerData(player);
//			GUIIcon.create(event).ifPresent(icon -> {
//				switch (icon) {
//				case UNIT_DONE_MAX:
//					String name = event.getCurrentItem().getItemMeta().getDisplayName();
//					OnlinePlayerNPCs.unitNames.put(player.getUniqueId(), event.getCurrentItem().getItemMeta().getDisplayName());
//					Optional.ofNullable(UnitStorage.units.getOrDefault(name, null))
//						.ifPresent(unit -> unit.addEmptyPlayerSentencesIfAbsent(player));
//					player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
//					return;
//				case BACK:
//					NPCUtility.findNPC(data.getSelectId())
//						.flatMap(npc -> SentenceSelector.create(npc)
//						.map(sel -> sel.createInventory()))
//						.ifPresent(i -> 	player.openInventory(i));
//					player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
//					return;
//				default:
//					return;
//				}
//			});
//		}
//	}
}

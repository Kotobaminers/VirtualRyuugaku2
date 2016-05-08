package com.github.orgs.kotobaminers.virtualryuugaku.utilities.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.citizensnpcs.api.npc.NPC;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class Utility {
	public static List<Integer> toListInteger(String string) {
		List<Integer> list = new ArrayList<Integer>();
		String tmp = "";
		if(string.startsWith("[") && string.endsWith("]")) {
			tmp = string.substring(1, string.length()-1);
			if (0 < tmp.length()) {
				String[] tmps = tmp.split(", ");
				for(String str : tmps) {
					list.add(Integer.parseInt(str));
				}
			}
		}
		return list;
	}

	public static void lookAt(Player player, Location lookat) {
		//Clone the loc to prevent applied changes to the input loc
		 Location loc = player.getLocation().clone();

		// Values of change in distance (make it relative)
		 double dx = lookat.getX() - loc.getX();
		 double dy = lookat.getY() - loc.getY();
		     double dz = lookat.getZ() - loc.getZ();

		 // Set yaw
		 if (dx != 0) {
			   // Set yaw start value based on dx
			   if (dx < 0) {
				 loc.setYaw((float) (1.5 * Math.PI));
				   } else {
					 loc.setYaw((float) (0.5 * Math.PI));
					   }
			   loc.setYaw((float) loc.getYaw() - (float) Math.atan(dz / dx));
			 } else if (dz < 0) {
				   loc.setYaw((float) Math.PI);
			}

		// Get the distance from dx/dz
		double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));
		// Set pitch
		loc.setPitch((float) -Math.atan(dy / dxz));
		// Set values, convert to degrees (invert the yaw since Bukkit uses a different yaw dimension format)
		loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
		loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);
		player.teleport(loc);
	}

	public static Entity[] getNearbyEntities(Location l, int radius) {
		int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
		HashSet <Entity> radiusEntities = new HashSet < Entity > ();
		for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
			for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
				int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
				for (Entity e: new Location(l.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk().getEntities()) {
					if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock())
						radiusEntities.add(e);
				}
			}
		}
		return radiusEntities.toArray(new Entity[radiusEntities.size()]);
	}

	public static final void teleportToNPC(Player player, NPC npc) {
		Location location = npc.getStoredLocation().clone();
		location.add(0, 1, -0.5);
		location.setPitch(50);
		location.setYaw(0);
		player.teleport(location);
//		player.getWorld().spigot().playEffect(player.getLocation(), Effect.ENDER_SIGNAL);
		player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
	}

	//TODO: This function requres external jar.
	public static final void sendTitle(Player player, String title, String subtitle){
		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(
				EnumTitleAction.TITLE,
				ChatSerializer.a("{\"text\":\"" + title + "\",\"color\":\"gold\",\"bold\":true,\"obfuscated\":false}"),
				20,40,30);
		PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(
				EnumTitleAction.SUBTITLE,
				ChatSerializer.a("{\"text\":\"" + subtitle + "\",\"color\":\"green\",\"italic\":true,\"underlined\":true,\"obfuscated\":false}"),
				20,40,30);
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		connection.sendPacket(titlePacket);
		connection.sendPacket(subTitlePacket);
	}

	public static final ItemStack setSkullOwner(ItemStack itemStack, String owner) {
		if (itemStack.getType().equals(Material.SKULL_ITEM)) {
			SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
			itemMeta.setDisplayName(owner);
			itemMeta.setOwner(owner);
			itemStack.setItemMeta(itemMeta);
		}
		return itemStack;
	}

	public static final ItemStack setItemDisplayName(ItemStack itemStack, String name) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}


}



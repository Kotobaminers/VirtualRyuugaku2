package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookOpen {
	private static String PackageName;
	private static boolean v1_8 = false;
	static {
		String version = Bukkit.getServer().getClass().getPackage().getName()
				.replace(".", ",").split(",")[3];
		PackageName = "net.minecraft.server." + version;
		if (version.startsWith("v1_8")) {
			v1_8 = true;
		}
	}

	@SuppressWarnings("deprecation")
	public static boolean openBook(Player player, String[] book) {
		if (!v1_8)
			return false;
		ItemStack item = player.getItemInHand().clone();
		try {
			player.setItemInHand(createWriteBook(book));
			player.updateInventory();
			Object CustomPayloadPacket = getCraftClass(
					"PacketPlayOutCustomPayload").newInstance();
			for (Field field : CustomPayloadPacket.getClass()
					.getDeclaredFields()) {
				field.setAccessible(true);
				if (field.getName().equals("a")) {
					field.set(CustomPayloadPacket, String.valueOf("MC|BOpen"));
				} else if (field.getName().equals("b")) {
					field.set(CustomPayloadPacket, getPacketDataSerializer());
				}
			}
			sendPacket(player, CustomPayloadPacket);
			return true;
		} catch (Throwable e) {
			return false;
		} finally {
			player.setItemInHand(item);
			player.updateInventory();
		}
	}

	private static ItemStack createWriteBook(String[] book) {
		ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta) item.getItemMeta();
		meta.addPage(book);
		item.setItemMeta(meta);
		return item;
	}

	private static Class<?> getCraftClass(String s) throws Exception {
		Class<?> craftclass = Class.forName(PackageName + "." + s);
		return craftclass;
	}

	private static Object getPacketDataSerializer() throws Exception {
		Class<?> a = getCraftClass("PacketDataSerializer");
		Constructor<?> b = a.getConstructor(new Class<?>[] { ByteBuf.class });
		Object PacketDataSerializer = b.newInstance(new Object[] { Unpooled
				.buffer() });
		return PacketDataSerializer;
	}

	private static void sendPacket(Player player, Object packet)
			throws Exception {
		Method PlayerHandle = player.getClass().getMethod("getHandle");
		Object EntityPlayer = PlayerHandle.invoke(player);
		Object PlayerConnection = null;
		for (Field field : EntityPlayer.getClass().getDeclaredFields()) {
			if (field.getName().equals("playerConnection")) {
				PlayerConnection = field.get(EntityPlayer);
				break;
			}
		}
		Method sendPacket = null;
		for (Method m : PlayerConnection.getClass().getDeclaredMethods()) {
			if (m.getName().equals("sendPacket")) {
				sendPacket = m;
				break;
			}
		}
		sendPacket.invoke(PlayerConnection, packet);
	}
}
package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitTask;

import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.VRGManager;

public class SentenceHologram {
	double height = 1.4;
	Location loc = null;
	List<String> lines = new ArrayList<String>();
	ArrayList<Entity> holos = new ArrayList<Entity>();
	public Integer count = 0;
	public BukkitTask task = null;

	public SentenceHologram(Location location, List<String> lines) {
		this.lines = lines;
		this.loc = location;
	}
	public SentenceHologram() {
	}

	public void spawnTemp(Integer duration){
		if(0 < lines.size()){
			cancelOldTask();
			this.loc.setY((this.loc.getY() + this.height) - 1.25);
//			int longest = lines.stream().reduce((line1, line2) -> line1.length() >= line2.length() ? line1 : line2).get().getBytes().length;
			for(int i = lines.size(); 0 < i; i--) {
//				StringBuilder s = new StringBuilder();
//				s.append(lines.get(i - 1));
//				for(int j = 0; j < longest - lines.get(i - 1).getBytes().length; j++) {
//					s.append(" ");
//				}
				final ArmorStand hologram =
						(ArmorStand) this.loc.getWorld().spawnEntity(this.loc, EntityType.ARMOR_STAND);
				holos.add(hologram);
				hologram.setCustomName(new String(lines.get(i-1)));
				hologram.setCustomNameVisible(true);
				hologram.setGravity(false);
				hologram.setVisible(false);
				this.loc.setY(this.loc.getY() + 0.25);
			}
			task = Bukkit.getScheduler().runTaskLater(VRGManager.plugin, new Runnable() {
				@Override
				public void run() {
					remove();
				}
			}, duration);
		}
	}

	public void remove() {
		this.holos.forEach(e -> e.remove());
		this.holos = new ArrayList<>();
	}
	public void setLines(List<String> lines){
        this.lines = lines;
	}
	public void setLocation(Location location){
        this.loc = location;
	}
	public void setHeight(double Height){
        this.height = Height;
	}
	public void cancelOldTask() {
		if (task != null) {
			task.cancel();
		}
	}
	public boolean isShown() {
		if (0 < holos.size()) {
			return true;
		}
		return false;
	}

}


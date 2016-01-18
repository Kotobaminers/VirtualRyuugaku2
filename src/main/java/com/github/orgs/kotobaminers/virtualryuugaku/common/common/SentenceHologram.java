package com.github.orgs.kotobaminers.virtualryuugaku.common.common;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.github.orgs.kotobaminers.virtualryuugaku.virtualryuugaku.VirtualRyuugakuManager;

public class SentenceHologram {
	double height = 1.4;
	Location loc = null;
	List<String> lines = new ArrayList<String>();
	ArrayList<Entity> holos = new ArrayList<Entity>();
	public Integer count = 0;

	public SentenceHologram(Location location, List<String> lines) {
		this.lines = lines;
		this.loc = location;
	}
	public SentenceHologram() {
	}

	public void spawnTemp(Integer duration){
		if(0 < lines.size()){
			this.loc.setY((this.loc.getY() + this.height) - 1.25);
			lines.stream().forEach(l -> System.out.println(l + " " + l.getBytes().length));
			int longest = lines.stream().reduce((line1, line2) -> line1.length() >= line2.length() ? line1 : line2).get().getBytes().length;
			for(int i = lines.size(); 0 < i; i--) {
				StringBuilder s = new StringBuilder();
				s.append(lines.get(i - 1));
				for(int j = 0; j < longest - lines.get(i - 1).getBytes().length; j++) {
					s.append(" ");
				}
				final ArmorStand hologram =
						(ArmorStand) this.loc.getWorld().spawnEntity(this.loc, EntityType.ARMOR_STAND);
				holos.add(hologram);
				hologram.setCustomName(new String(s));
				hologram.setCustomNameVisible(true);
				hologram.setGravity(false);
				hologram.setVisible(false);
				this.loc.setY(this.loc.getY() + 0.25);
				Bukkit.getScheduler().runTaskLater(VirtualRyuugakuManager.plugin, new Runnable() {
					@Override
					public void run() {
						remove();
					}
				}, duration);
			}
		}
	}

	public void remove() {
		this.holos.forEach(e -> e.remove());
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

}

//public void spawn(){
//if(lines.size() == 1){
//	spawn();
//	return;
//}
//if(lines.size() > 1){
//	this.loc.setY((this.loc.getY() + this.height)-1.25);
//	for(int i = lines.size();i>0;i--){
//		final ArmorStand Hologram = (ArmorStand)this.loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
//		holos.add(Hologram);
//		Hologram.setCustomName(lines.get(i-1));
//		Hologram.setCustomNameVisible(true);
//		Hologram.setGravity(false);
//		Hologram.setVisible(false);
//		this.loc.setY(this.loc.getY()+0.25);
//	}
//}
//}


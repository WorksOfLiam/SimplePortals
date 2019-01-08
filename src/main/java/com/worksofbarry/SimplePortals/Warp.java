package com.worksofbarry.SimplePortals;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

public class Warp {
	public static Map<String, Warp> List = new HashMap<String, Warp>();
	
	public static boolean Exists(String Name) {
		return List.containsKey(Name);
	}
	public static Warp GetWarp(String Name) {
		return List.get(Name);
	}
	
	private Location WarpLocation;
	public Warp(Location point) {
		WarpLocation = point;
	}
	
	public Location GetPoint() {
		return WarpLocation;
	}
}

package com.worksofbarry.SimplePortals;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

public class Portal {
	public static Map<String, Portal> List = new HashMap<String, Portal>();
	
	public static String LocationString(Location Location) {
		return Location.getWorld().getName() + "," + 
               String.valueOf(Location.getBlockX()) + "," +
               String.valueOf(Location.getBlockY()) + "," +
               String.valueOf(Location.getBlockZ());
	}
	
	private Location StandingPos;
	private String WarpPoint;
	public Portal(Location Position, String WarpName) {
		StandingPos = Position;
		WarpPoint = WarpName;
	}
	
	public boolean isStandingOn(Location Point) {
		return (Point.getWorld().getName() == StandingPos.getWorld().getName() && Point.getBlockX() == StandingPos.getBlockX() && Point.getBlockY() == StandingPos.getBlockY() && Point.getBlockZ() == StandingPos.getBlockZ());
	}
	
	public String GetWarpName() {
		return WarpPoint;
	}
	
	public Location GetPortalLocation() {
		return StandingPos;
	}
	
	public Location GetWarpLocation() {
		System.out.println("Finding warp: " + WarpPoint);
		if (Warp.Exists(WarpPoint)) {
			return Warp.GetWarp(WarpPoint).GetPoint();
		} else {
			return null;
		}
	}
}

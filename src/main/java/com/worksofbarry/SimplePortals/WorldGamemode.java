package com.worksofbarry.SimplePortals;

import java.util.HashMap;

import org.bukkit.GameMode;

public class WorldGamemode {
	public static HashMap<String, GameMode> WorldList = new HashMap<String, GameMode>();
	
	public String World;
	public GameMode Gamemode;
	
	public WorldGamemode(String world, GameMode gm) {
		this.World = world;
		this.Gamemode = gm;
	}
}

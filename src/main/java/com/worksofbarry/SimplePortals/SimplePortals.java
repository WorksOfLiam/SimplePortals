package com.worksofbarry.SimplePortals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.worksofbarry.SimplePortals.Commands.PortalCommand;
import com.worksofbarry.SimplePortals.Commands.SetGMCommand;
import com.worksofbarry.SimplePortals.Commands.SpawnCommand;
import com.worksofbarry.SimplePortals.Commands.WarpCommand;
import com.worksofbarry.SimplePortals.Commands.WorldsCommand;
import com.worksofbarry.SimplePortals.Listeners.PlayerListener;

public class SimplePortals extends JavaPlugin {
	private final PlayerListener playerListener = new PlayerListener();
	
	@Override
	public void onEnable() {
		//Add seperate listeners here
		PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(playerListener, this);
        
		//Register commands here AND in plugins.yml.
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("portal").setExecutor(new PortalCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("worlds").setExecutor(new WorldsCommand());
        getCommand("setgm").setExecutor(new SetGMCommand());
        
		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
		}
		
		Load();
	}

	@Override
	public void onDisable() {
		Save();
	}
	
	public void Save() {
		ConfigurationSection list;
		Warp warp;
		Portal portal;

    	List<String> WorldNames = new ArrayList<String>();
		for (World world : Bukkit.getWorlds()) {
			WorldNames.add(world.getName());
		}
		getConfig().set("worlds", WorldNames);
		
		list = getConfig().createSection("spawn");
		list.set("world", Spawn.SpawnPoint.getWorld().getName());
		list.set("x", Spawn.SpawnPoint.getX());
		list.set("y", Spawn.SpawnPoint.getY());
		list.set("z", Spawn.SpawnPoint.getZ());
		list.set("p", Spawn.SpawnPoint.getPitch());
		list.set("ya", Spawn.SpawnPoint.getYaw());
		
		list = getConfig().createSection("warps");
		for (String key : Warp.List.keySet()) {
			System.out.println("Saving warp: " + key);
			warp = Warp.GetWarp(key);
			list.set(key + ".world", warp.GetPoint().getWorld().getName());
			list.set(key + ".x", warp.GetPoint().getX());
			list.set(key + ".y", warp.GetPoint().getY());
			list.set(key + ".z", warp.GetPoint().getZ());
			list.set(key + ".p", warp.GetPoint().getPitch());
			list.set(key + ".ya", warp.GetPoint().getYaw());
		}

		list = getConfig().createSection("portals");
		for (String key : Portal.List.keySet()) {
			portal = Portal.List.get(key);
			list.set(key + ".pos.world", portal.GetPortalLocation().getWorld().getName());
			list.set(key + ".pos.x", portal.GetPortalLocation().getX());
			list.set(key + ".pos.y", portal.GetPortalLocation().getY());
			list.set(key + ".pos.z", portal.GetPortalLocation().getZ());
			list.set(key + ".warp", portal.GetWarpName());
		}
		

		list = getConfig().createSection("gamemodes");
		for (String worldName : WorldGamemode.WorldList.keySet()) {
			list.set(worldName, WorldGamemode.WorldList.get(worldName).toString());
		}
		
		saveConfig();
	}
	
	public void Load() {
		Location location;
		ConfigurationSection list;
		
		if (getConfig().contains("worlds")) {
			for (String WorldName : getConfig().getStringList("worlds")) {
				System.out.println(WorldName);
				new WorldCreator(WorldName).createWorld();
			}
		}

		if (getConfig().contains("spawn")) {
			list = getConfig().getConfigurationSection("spawn");
			location = new Location(
					Bukkit.getWorld(list.getString("world")), 
					Double.valueOf(list.getDouble("x")),
					Double.valueOf(list.getDouble("y")),
					Double.valueOf(list.getDouble("z")));
			location.setPitch(Float.valueOf(list.getString("p")));
			location.setYaw(Float.valueOf(list.getString("ya")));
			
			Spawn.SpawnPoint = location;
		} else {
			Spawn.SpawnPoint = Bukkit.getWorlds().get(0).getSpawnLocation();
		}
		
		if (getConfig().contains("warps")) {
			list = getConfig().getConfigurationSection("warps");
			
			for (String key : list.getKeys(false)) {
				location = new Location(
						Bukkit.getWorld(list.getString(key + ".world")), 
						Double.valueOf(list.getDouble(key + ".x")),
						Double.valueOf(list.getDouble(key + ".y")),
						Double.valueOf(list.getDouble(key + ".z")));
				location.setPitch(Float.valueOf(list.getString(key + ".p")));
				location.setYaw(Float.valueOf(list.getString(key + ".ya")));
				
				Warp.List.put(key, new Warp(location));
			}
		}
		
		if (getConfig().contains("portals")) {
			list = getConfig().getConfigurationSection("portals");
			
			for (String key : list.getKeys(false)) {
				location = new Location(
						Bukkit.getWorld(list.getString(key + ".pos.world")), 
						Double.valueOf(list.getDouble(key + ".pos.x")),
						Double.valueOf(list.getDouble(key + ".pos.y")),
						Double.valueOf(list.getDouble(key + ".pos.z")));
				
				Portal.List.put(key, new Portal(location, list.getString(key + ".warp")));
			}
		}
		
		if (getConfig().contains("gamemodes")) {
			list = getConfig().getConfigurationSection("gamemodes");
			for (String key : list.getKeys(false)) {
            	WorldGamemode.WorldList.put(key, GameMode.valueOf(list.getString(key)));
			}
		}
	}
}


package com.worksofbarry.SimplePortals.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.worksofbarry.SimplePortals.Portal;
import com.worksofbarry.SimplePortals.Spawn;
import com.worksofbarry.SimplePortals.WorldGamemode;

public class PlayerListener implements Listener {
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location loc;
    	
        for (Portal portal : Portal.List.values()) {
        	if (portal.isStandingOn(event.getTo())) {
        		loc = portal.GetWarpLocation();
        		if (loc != null) {
            		event.getPlayer().teleport(loc);
        		} else {
        			event.getPlayer().sendMessage(ChatColor.RED + "Warp " + portal.GetWarpName() + " is missing. Please report this to an admin.");
        		}
        		return;
        	}
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	Player player = event.getPlayer();
    	
    	if (!player.hasPlayedBefore()) {
    		player.teleport(Spawn.SpawnPoint);
    	}
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
    	event.setRespawnLocation(Spawn.SpawnPoint);
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
    	Player player = event.getPlayer();
    	String toWorld = event.getTo().getWorld().getName();
    	GameMode toGameMode = GameMode.SURVIVAL;
    	
    	if (event.getFrom().getWorld().getName() != toWorld) {
    		if (WorldGamemode.WorldList.containsKey(toWorld)) {
    			toGameMode = WorldGamemode.WorldList.get(toWorld);
    		}
    		
    		if (player.getGameMode().equals(GameMode.CREATIVE) && !toGameMode.equals(GameMode.CREATIVE)) {
    			player.sendMessage(ChatColor.RED + "Inventory clearned as teleported from creative world.");
    		}
    		
    		player.setGameMode(toGameMode);
    	}
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
    	Player player = event.getPlayer();
    	Block block = event.getClickedBlock();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
	    	if (!block.equals(null)) {
		    	if (block.getType().equals(Material.ENDER_CHEST)) {
		    		if (player.getGameMode().equals(GameMode.CREATIVE)) {
		    			player.sendMessage(ChatColor.RED + "Cannot access ender chest while in creative mode.");
		    			event.setCancelled(true);
		    		}
		    	}
	    	}
        }
    }
}


package com.worksofbarry.SimplePortals.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.worksofbarry.SimplePortals.Portal;
import com.worksofbarry.SimplePortals.Warp;

public class PortalCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
    	Player player;
        if (sender instanceof Player) {
        	player = (Player) sender;
        	
        	if (player.hasPermission("simpleportals.portal")) {
	            if (split.length == 0) {
	            } else {
	            	switch (split[0]) {
	            	case "create":
	            		if (split.length == 2) {
	            			if (Warp.List.containsKey(split[1])) {
	            				Portal.List.put(Portal.LocationString(player.getLocation()), new Portal(player.getLocation(), split[1]));
		                		player.sendMessage(ChatColor.WHITE + "Created portal.");
	            			} else {
		                		player.sendMessage(ChatColor.WHITE + "Warp does not exist.");
	            			}
	            		} else {
	                		player.sendMessage(ChatColor.RED + "Not enough parameters: /portal create <warp name>");
	            		}
	            		break;
	            		
	            	case "delete":
	                    String location = Portal.LocationString(player.getTargetBlock(null, 100).getLocation());
	                    
	                    if (Portal.List.containsKey(location)) {
	                    	Portal.List.remove(location);
	                		player.sendMessage(ChatColor.WHITE + "Portal removed.");
	                    } else {
	                		player.sendMessage(ChatColor.WHITE + "Portal is not here.");
	                    }
	            		break;
	            	}
	            }
        	} else {
        		player.sendMessage(ChatColor.RED + "You do not have access to /portal.");
        	}
            return true;
            
        } else {
            return false;
        }
    }
}


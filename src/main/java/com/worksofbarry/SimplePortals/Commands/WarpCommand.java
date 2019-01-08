package com.worksofbarry.SimplePortals.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.worksofbarry.SimplePortals.Warp;

public class WarpCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
    	Player player;
        if (sender instanceof Player) {
        	player = (Player) sender;
        	
        	if (player.hasPermission("simpleportals.warp")) {
	            if (split.length == 0) {
	            } else {
	            	switch (split[0]) {
	            	case "list":
	            		player.sendMessage("Warp list: " + String.join(", ", Warp.List.keySet()));
	            		break;
	            	case "create":
	            		if (split.length == 2) {
	            			if (!Warp.List.containsKey(split[1])) {
	            				Warp.List.put(split[1], new Warp(player.getLocation()));
		                		player.sendMessage(ChatColor.WHITE + "Created warp: " + split[1]);
	            			} else {
		                		player.sendMessage(ChatColor.WHITE + "Warp already exists.");
	            			}
	            		} else {
	                		player.sendMessage(ChatColor.RED + "Not enough parameters: /warp create <name>");
	            		}
	            		break;
	            		
	            	case "delete":
	            		if (split.length == 2) {
	            			if (Warp.List.containsKey(split[1])) {
	            				Warp.List.remove(split[1]);
		                		player.sendMessage(ChatColor.WHITE + "Deleted warp: " + split[1]);
	            			} else {
		                		player.sendMessage(ChatColor.WHITE + "Warp does not exist.");
	            			}
	            		} else {
	                		player.sendMessage(ChatColor.RED + "Not enough parameters: /warp create <name>");
	            		}
	            		break;
	            		
	            	default:
	            		if (split.length == 1) {
	            			if (Warp.List.containsKey(split[0])) {
	            				player.teleport(Warp.GetWarp(split[0]).GetPoint());
	            			} else {
		                		player.sendMessage(ChatColor.WHITE + "Warp does not exist.");
	            			}
	            		}
	            		break;
	            	}
	            }
        	} else {
        		player.sendMessage(ChatColor.RED + "You do not have access to /warp.");
        	}
            return true;
            
        } else {
            return false;
        }
    }
}


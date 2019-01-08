package com.worksofbarry.SimplePortals.Commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.worksofbarry.SimplePortals.WorldGamemode;

public class SetGMCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
		
    	Player player;
        if (sender instanceof Player) {
        	player = (Player) sender;
        	
        	if (player.hasPermission("simpleportals.worlds")) {
	            if (split.length == 2) {
	            	WorldGamemode.WorldList.put(split[0], GameMode.valueOf(split[1].toUpperCase()));
	        		player.sendMessage(ChatColor.RED + "Set " + split[0] + " to " + split[1] + ".");
	            } else {
	        		player.sendMessage(ChatColor.RED + "Not enough parameters: /setgm <world> <SURVIVAL|CREATIVE>");
	            }
            } else {
        		player.sendMessage(ChatColor.RED + "You do not have access to /setgm.");
            }
        }
		
		return true;
	}

}

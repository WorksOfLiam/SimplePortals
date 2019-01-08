package com.worksofbarry.SimplePortals.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.worksofbarry.SimplePortals.Spawn;

public class SpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
		Player player;
        if (sender instanceof Player) {
        	player = (Player) sender;
			if (player.hasPermission("simpleportals.spawn")) {
	            if (split.length == 0) {
	            	//SPAWN
	            	player.teleport(Spawn.SpawnPoint);
	            } else {
	            	//SPAWN SET
	            	if (split[0].equals("set")) {
		            	if (player.hasPermission("simpleportals.spawnset")) {
		            		Spawn.SpawnPoint = player.getLocation().clone();
		            		player.sendMessage("Spawn point changed.");
		            	} else {
		            		player.sendMessage("You do not have permission to change the spawn point.");
		            	}
	            	}
	            }
			}
        }
        return true;
	}

}

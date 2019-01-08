package com.worksofbarry.SimplePortals.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldsCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
		Player player;
		List<String> WorldNames;

		if (sender instanceof Player) {
			player = (Player) sender;

			if (player.hasPermission("simpleportals.worlds")) {
				if (split.length == 0) {
				} else {
					switch (split[0]) {
					case "list":
						WorldNames = new ArrayList<String>();
						for (World world : Bukkit.getWorlds()) {
							WorldNames.add(world.getName());
						}

						player.sendMessage(ChatColor.GOLD + "World list: ");
						for (String WorldName : WorldNames) {
							player.sendMessage(WorldName);
						}

						break;

					case "create": // worlds create name type
						WorldNames = new ArrayList<String>();
						for (World world : Bukkit.getWorlds()) {
							WorldNames.add(world.getName());
						}

						if (split.length == 3) {
							WorldCreator creator = new WorldCreator(split[1]).environment(Environment.NORMAL)
									.generateStructures(false).type(WorldType.valueOf(split[2].toUpperCase()));
							Bukkit.broadcastMessage(ChatColor.GOLD + "Creating new world " + split[1]);
							Bukkit.createWorld(creator);
							Bukkit.broadcastMessage(ChatColor.GOLD + "Created new world " + split[1]);
						} else {
							player.sendMessage(ChatColor.RED + "Not enough parameters: /worlds create <name> <type>");
							player.sendMessage(ChatColor.RED + "<type> can be: NORMAL, LARGE_BIOMES, FLAT, AMPLIFIED");
						}
						break;

					case "spawn": // worlds spawn name
						if (split.length == 2) {
							player.teleport(Bukkit.getWorld(split[1]).getSpawnLocation());
						} else {
							player.sendMessage(ChatColor.RED + "Not enough parameters: /worlds spawn <name>");
						}
						break;
					}
				}
			} else {
				player.sendMessage(ChatColor.RED + "You do not have access to /worlds.");
			}
		}

		return true;
	}

}

package com.lolpvp.commands.classes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Disposal implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{
		if(command.getName().equalsIgnoreCase("dispose"))
		{
			if(sender instanceof Player)
			{
				if(args.length >= 0)
				{
					Player player = ((Player)sender);
					player.openInventory(Bukkit.getServer().createInventory(null, 54, ChatColor.RED + "Disposal"));
					return true;	
				}
			}
		}
		return false;
	}

}
package com.lolpvp.virtualchest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.lolpvp.core.Core;
import com.lolpvp.utils.UUIDFetcher;

public class VirtualChest implements CommandExecutor
{
	
	private Core plugin;
	
	public VirtualChest(Core instance)
	{
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		// TODO Auto-generated method stub
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			if(commandLabel.equalsIgnoreCase("chest2"))
			{
				if(player.hasPermission("lolpvp.chest2"))
				{
					if(args.length == 0)
					{
						String s = player.getName().toLowerCase();
				        String name = ChatColor.RED + player.getName();
				        Inventory inv = Bukkit.getServer().createInventory(null, 54, name);
				        VirtualChestManager.getInstance().load(s, inv, player);
				        VirtualChestManager.getInstance().in.add(player.getName());	
					}
					else if(args.length == 1)
					{
						if(player.hasPermission("lolpvp.chest2.others"))
						{
							String s = args[0].toLowerCase();
							if (UUIDFetcher.getUUIDFromName(args[0]) != null)
							{
								FileConfiguration fc = this.plugin.playerFile(UUIDFetcher.getUUIDFromName(s));
								if (fc.getConfigurationSection("chest.") != null)
								{
									String name = ChatColor.RED + UUIDFetcher.getExactName(args[0]);
									Inventory inv = Bukkit.getServer().createInventory(null, 54, name);
									VirtualChestManager.getInstance().load(s, inv, player);
									VirtualChestManager.getInstance().in.add(player.getName());
								}
								else
								{
									player.sendMessage(ChatColor.AQUA + UUIDFetcher.getExactName(args[0]) + ChatColor.RED + " does not have a chest.");
								}
							}
							else
							{
								player.sendMessage(ChatColor.AQUA + args[0] + ChatColor.RED + " is not a player!");
							}	
						}
						else
						{
							player.sendMessage(ChatColor.RED + "You do not have permission for this command.");	
						}	
					}
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				}
			}
			else if(commandLabel.equalsIgnoreCase("clearchest2"))
			{
				if(player.hasPermission("lolpvp.clearchest2"))
				{
					if (args.length == 0) 
					{
						VirtualChestManager.getInstance().clearChest(player, player.getName());
					} 
					
					if(args.length == 1 && player.hasPermission("lolpvp.chearchest2.others")) 
					{	
						if(UUIDFetcher.getUUIDFromName(args[0]) != null)
						{
							VirtualChestManager.getInstance().clearChest(player, args[0]);	
						}
						else
						{
							player.sendMessage(ChatColor.AQUA + args[0] + ChatColor.RED + " is not a player!");
						}
					}
					else
					{
						player.sendMessage(ChatColor.RED + "You do not have permission for this command.");	
					}
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You do not have permission for this command.");	
				}
			}
		}
		return false;
	}
}

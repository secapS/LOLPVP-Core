package com.lolpvp.chests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.lolpvp.commands.SubCommand;
import com.lolpvp.core.Core;

public class Chest extends SubCommand
{
	public static final HashMap<String, Integer> in = new HashMap<>();
	public static final HashMap<String, String> name = new HashMap<>();
	
	private static Core plugin;
	
	public Chest()
	{
		super("sets/gives a PVP chest", "lolpvp.chest", "<set|give>", new String[]{"c", "chest"});
		// TODO Auto-generated constructor stub
	}
	
	public static void setup(Core core)
	{
		plugin = core;
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) 
	{
		if(args.length == 0)
		{
			sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.AQUA + "/lol c|chest set name id");
			sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.AQUA + "/lol c|chest give player chest");
			return;
		}
		
		if(args.length < 3)
			return;
		
		if(!(sender instanceof Player))
		{
			sender.sendMessage(args[0]);
			if(args[1].equalsIgnoreCase("give"))
			{
				Player playerRecieving = Bukkit.getPlayer(args[2]);
				if(playerRecieving != null)
				{
					boolean passed = false;
					String name = null;
					FileConfiguration fc = plugin.pvpFile();
					for (String ss : fc.getConfigurationSection("chests.").getKeys(false)) {
						if (ss.equalsIgnoreCase(args[3]))
						{
							name = ChatColor.RED + args[3] + " Chest";
							passed = true;
							break;
						}
					}
					if (!passed)
					{
						sender.sendMessage(ChatColor.RED + "Could not find chest " + args[3]);
						return;
					}
					ItemStack item = new ItemStack(Material.CHEST);
					ItemMeta sitem = item.getItemMeta();
					List<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "Right click to unwrap!");
					sitem.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
					sitem.setLore(lore);
					item.setItemMeta(sitem);
					playerRecieving.getInventory().addItem(new ItemStack[] { item });
					playerRecieving.sendMessage(ChatColor.RED + "You were given a " + name);	
				}
				else
				{
					sender.sendMessage(ChatColor.AQUA + args[2] + ChatColor.RED + " is not online!");
				}
			}
		}
		else
		{
			if(args[0].equalsIgnoreCase("set"))
			{
				Player player = (Player)sender;
				int i;
				try
				{
					i = Integer.parseInt(args[2]);
				}
				catch (NumberFormatException e)
				{
					player.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.AQUA + "/lol c|chest " + this.getUsage() + " name id");
					return;
				}
				name.put(player.getName(), args[1]);
				in.put(player.getName(), Integer.valueOf(i));
				Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.RED + "Set PVP Chest");
				player.openInventory(inventory);		
			}
			else if(args[0].equalsIgnoreCase("give"))
			{
				Player playerRecieving = Bukkit.getPlayer(args[1]);
				if(playerRecieving != null)
				{
					boolean passed = false;
					String name = null;
					FileConfiguration fc = plugin.pvpFile();
					for (String ss : fc.getConfigurationSection("chests.").getKeys(false)) {
						if (ss.equalsIgnoreCase(args[2]))
						{
							name = ChatColor.RED + args[2] + " Chest";
							passed = true;
							break;
						}
					}
					if (!passed)
					{
						sender.sendMessage(ChatColor.RED + "Could not find chest " + args[2]);
						return;
					}
					ItemStack item = new ItemStack(Material.CHEST);
					ItemMeta sitem = item.getItemMeta();
					List<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "Right click to unwrap!");
					sitem.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
					sitem.setLore(lore);
					item.setItemMeta(sitem);
					playerRecieving.getInventory().addItem(new ItemStack[] { item });
					playerRecieving.sendMessage(ChatColor.RED + "You were given a " + name);	
				}
				else
				{
					sender.sendMessage(ChatColor.AQUA + args[1] + ChatColor.RED + " is not online!");
				}
			}
		}
	}
}

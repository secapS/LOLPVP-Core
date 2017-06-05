package com.lolpvp.virtualchest;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.lolpvp.core.Core;
import com.lolpvp.utils.UUIDFetcher;

public class VirtualChestManager 
{
	private static Core plugin;
	private static VirtualChestManager instance;

	public final HashSet<String> in = new HashSet<String>();
	
	public static void setup(Core core)
	{
		plugin = core;
	}
	
	public void load(String s, Inventory inv, Player player)
	{
		FileConfiguration fc = plugin.playerFile(UUIDFetcher.getUUIDFromName(s));
		if (fc.getConfigurationSection("chest.") != null) {
			for (String ss : fc.getConfigurationSection("chest.").getKeys(false)) {
				inv.setItem(Integer.parseInt(ss), fc.getItemStack("chest." + ss));
			}
		}
		player.openInventory(inv);
	}

	public void save(Inventory inv)
	{
		String s = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', inv.getTitle())).toLowerCase();
		FileConfiguration fc = plugin.playerFile(UUIDFetcher.getUUIDFromName(s));
		int slot = 0;
		for (ItemStack stack : inv.getContents())
		{
			fc.set("chest." + slot, stack);
			slot++;
		}
		try
		{
			fc.save(plugin.playerData(UUIDFetcher.getUUIDFromName(s)));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void clearChest(Player player, String name)
	{
		if (UUIDFetcher.getUUIDFromName(name) != null)
		{
			FileConfiguration fc = plugin.playerFile(UUIDFetcher.getUUIDFromName(name));
			if (fc.getConfigurationSection("chest.") != null)
			{
				fc.set("chest", null);
				try
				{
					fc.save(plugin.playerData(UUIDFetcher.getUUIDFromName(name)));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				player.sendMessage(ChatColor.GRAY + "Cleared " + ChatColor.AQUA + UUIDFetcher.getExactName(name) + ChatColor.GRAY + "'s chest.");
			}
			else
			{
				player.sendMessage(ChatColor.AQUA + UUIDFetcher.getExactName(name) + ChatColor.RED + "does not have a chest.");
			}
		}
		else
		{
			player.sendMessage(ChatColor.AQUA + name + ChatColor.RED + " is not a player!");
		}
	}
	
	public static VirtualChestManager getInstance()
	{
		if(instance == null)
			instance = new VirtualChestManager();
		return instance;
	}
}

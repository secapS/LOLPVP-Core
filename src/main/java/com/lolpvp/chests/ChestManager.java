package com.lolpvp.chests;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.lolpvp.core.Core;

public class ChestManager 
{
	private static Core plugin;
	private static ChestManager instance;
	public void saveChest(Inventory inv, int id, String name)
	{
		FileConfiguration fc = plugin.pvpFile();
		int slot = 0;
		for (ItemStack stack : inv.getContents())
		{
			fc.set("chests." + name.toLowerCase() + "." + id + "." + slot, stack);
			slot++;
		}
		try
		{
			fc.save(plugin.pvpData());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setup(Core core)
	{
		plugin = core;
	}
	
	public static ChestManager getInstance()
	{
		if(instance == null)
			instance = new ChestManager();
		return instance;	
	}
}

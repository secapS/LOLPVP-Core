package com.lolpvp.weapons.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.lolpvp.weapons.BallerItem;

public class IronMan extends BallerItem
{

	public IronMan() 
	{
		super(ChatColor.AQUA + "Iron Man", Material.AIR, 1000000, null, null, new String[]{"ironman"});
	}
}

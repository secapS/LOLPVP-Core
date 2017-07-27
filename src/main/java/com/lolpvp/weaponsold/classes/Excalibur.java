package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import com.lolpvp.weaponsold.BallerItem;

public class Excalibur extends BallerItem
{

	public Excalibur() 
	{
		super(ChatColor.AQUA + "Excalibur", Material.DIAMOND_SWORD, 100000, lore(), enchantments(), "excal", "excalibur");
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "The essential PVP sword");
		}};
	} 
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DAMAGE_ALL, 5);
			this.put(Enchantment.DAMAGE_UNDEAD, 5);
			this.put(Enchantment.KNOCKBACK, 2);
			this.put(Enchantment.LOOT_BONUS_MOBS, 3);
			this.put(Enchantment.FIRE_ASPECT, 2);
		}};
	}
	
}

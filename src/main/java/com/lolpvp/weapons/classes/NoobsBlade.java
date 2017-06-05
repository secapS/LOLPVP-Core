package com.lolpvp.weapons.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import com.lolpvp.weapons.BallerItem;

public class NoobsBlade extends BallerItem
{
	public NoobsBlade() 
	{
		super(ChatColor.AQUA + "Noob's Blade", Material.DIAMOND_SWORD, 50000, lore(), enchantments(), "nb", "noobsblade");
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "Made for noobs - by Thad");
		}};
	} 
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DAMAGE_ALL, 1);
			this.put(Enchantment.KNOCKBACK, 1);
			this.put(Enchantment.LOOT_BONUS_MOBS, 1);
			this.put(Enchantment.FIRE_ASPECT, 1);
		}};
	}	
}

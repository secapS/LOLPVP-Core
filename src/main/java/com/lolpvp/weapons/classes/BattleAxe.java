package com.lolpvp.weapons.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import com.lolpvp.weapons.BallerItem;

public class BattleAxe extends BallerItem
{

	public BattleAxe() {
		super(ChatColor.AQUA + "Battle Axe", Material.DIAMOND_AXE, 3000000, lore(), enchantments(), "ba", "battleaxe");
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() 
	{
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DAMAGE_ALL, 10);
			this.put(Enchantment.DAMAGE_UNDEAD, 10);
			this.put(Enchantment.DAMAGE_ARTHROPODS, 5);
			this.put(Enchantment.LOOT_BONUS_MOBS, 5);
			this.put(Enchantment.FIRE_ASPECT, 10);
		}};
	}

	@SuppressWarnings("serial")
	private static List<String> lore() {
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "Shreds your enemy's armor faster than a sword!");
		}};
	}
}

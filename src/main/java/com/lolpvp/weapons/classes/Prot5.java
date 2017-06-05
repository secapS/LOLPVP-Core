package com.lolpvp.weapons.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.lolpvp.weapons.BallerItem;

public class Prot5 extends BallerItem
{

	public Prot5() 
	{
		super(ChatColor.AQUA + "Prot 5",
				new Material[]{Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS}, 
				750000, lore(), enchantments(), "protection5", "prot5", "p5");		
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "Authentic Protection 5 Armor");
		}};
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
		}};
	}
	
	public ItemStack[] getItemStacks(int amount)
	{
		ItemStack[] armor = {new ItemStack(this.getMaterials()[0], amount),
				  new ItemStack(this.getMaterials()[1], amount),
				  new ItemStack(this.getMaterials()[2], amount),
				  new ItemStack(this.getMaterials()[3], amount)};
		ItemMeta meta = armor[0].getItemMeta();
		meta.setDisplayName(this.getName());
		meta.setLore(this.getLore());
		armor[0].setItemMeta(meta);
		armor[1].setItemMeta(meta);
		armor[2].setItemMeta(meta);
		armor[3].setItemMeta(meta);
		for(ItemStack item : armor)
		{
			for(Enchantment ench : this.getEnchantments().keySet())
			{
				item.addUnsafeEnchantment(ench, this.getEnchantments().get(ench));
			}
		}
		return armor;
	}
	public ItemStack getItemStack(Material material)
	{
		ItemStack[] armor = {new ItemStack(this.getMaterials()[0], 1),
				  new ItemStack(this.getMaterials()[1], 1),
				  new ItemStack(this.getMaterials()[2],1),
				  new ItemStack(this.getMaterials()[3], 1)};
		for(ItemStack item : armor)
		{
			for(Enchantment ench : this.getEnchantments().keySet())
			{
				item.addUnsafeEnchantment(ench, this.getEnchantments().get(ench));
			}
		}
		ItemMeta meta;
	
		switch(material)
		{
			case DIAMOND_HELMET: 
				meta = armor[0].getItemMeta();
				meta.setDisplayName(this.getName());
				meta.setLore(this.getLore());
				armor[0].setItemMeta(meta);
				return armor[0];
			case DIAMOND_CHESTPLATE:
				meta = armor[1].getItemMeta();
				meta.setDisplayName(this.getName());
				meta.setLore(this.getLore());
				armor[1].setItemMeta(meta);
				return armor[1];
			case DIAMOND_LEGGINGS:
				meta = armor[2].getItemMeta();
				meta.setDisplayName(this.getName());
				meta.setLore(this.getLore());
				armor[2].setItemMeta(meta);
				return armor[2];
			case DIAMOND_BOOTS:
				meta = armor[3].getItemMeta();
				meta.setDisplayName(this.getName());
				meta.setLore(this.getLore());
				armor[3].setItemMeta(meta);
				return armor[3];
			default:
				return null;
		}
	}
}

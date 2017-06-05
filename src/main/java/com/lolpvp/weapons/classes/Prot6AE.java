package com.lolpvp.weapons.classes;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.lolpvp.weapons.BallerItem;

public class Prot6AE extends BallerItem
{

	public Prot6AE() 
	{
		super(ChatColor.AQUA + "Prot 6 AE",
				new Material[]{Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS}, 
				2500000, lore(), enchantments(), "protection6ae", "prot6ae", "p6ae");		
		// TODO Auto-generated constructor stub
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			if(isArmor(player.getEquipment().getArmorContents()))
			{
				event.setDamage(event.getDamage() - (event.getDamage() * 0.3));
			}
		}
	}
	
	public boolean isArmor(ItemStack[] stack)
	{
		return stack[0] != null
			   && stack[0].hasItemMeta()
			   && stack[1] != null
			   && stack[1].hasItemMeta()
			   && stack[2] != null
			   && stack[2].hasItemMeta()
			   && stack[3] != null
			   && stack[3].hasItemMeta()
			   && stack[0].getItemMeta().hasLore()
			   && stack[1].getItemMeta().hasLore()
			   && stack[2].getItemMeta().hasLore()
			   && stack[3].getItemMeta().hasLore()
			   && stack[0].getItemMeta().getLore().equals(lore())
			   && stack[1].getItemMeta().getLore().equals(lore())
			   && stack[2].getItemMeta().getLore().equals(lore())
			   && stack[3].getItemMeta().getLore().equals(lore());
	}

	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "Authentic Protection 6 AE Armor");
		}};
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.PROTECTION_ENVIRONMENTAL, 6);
			this.put(Enchantment.PROTECTION_EXPLOSIONS, 6);
			this.put(Enchantment.PROTECTION_FIRE, 6);
			this.put(Enchantment.PROTECTION_PROJECTILE, 6);
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
				if(item.getType().equals(Material.DIAMOND_HELMET))
				{
					item.addUnsafeEnchantment(Enchantment.WATER_WORKER, 6);
					item.addUnsafeEnchantment(Enchantment.OXYGEN, 6);
				}
				else if(item.getType().equals(Material.DIAMOND_BOOTS))
				{
					item.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 6);
				}
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
				if(item.getType().equals(Material.DIAMOND_HELMET))
				{
					item.addUnsafeEnchantment(Enchantment.WATER_WORKER, 6);
					item.addUnsafeEnchantment(Enchantment.OXYGEN, 6);
				}
				else if(item.getType().equals(Material.DIAMOND_BOOTS))
				{
					item.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 6);
				}
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
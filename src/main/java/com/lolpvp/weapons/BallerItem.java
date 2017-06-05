package com.lolpvp.weapons;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.lolpvp.core.Core;

public class BallerItem implements Listener
{
	private String name, owner;
	private Material material;
	private Material[] materials;
	private int price, meta;
	private String[] aliases;
	private List<String> lore;
	private HashMap<Enchantment, Integer> enchantments;
	private boolean multipleMaterials;
	private boolean leatherArmor;
	private boolean glow;
	private LeatherArmorMeta leatherArmorMeta;
	
	public BallerItem(String name, Material material, int price, List<String> lore, HashMap<Enchantment, Integer> enchantments, String... aliases)
	{
		this.name = name;
		this.material = material;
		this.price = Core.getInstance().getConfig().getInt("items." + ChatColor.stripColor(name).replaceAll(" ", "-").replaceAll("'", "").toLowerCase() + ".price");
		this.lore = lore;
		this.enchantments = enchantments;
		this.aliases = aliases;
		this.multipleMaterials = false;
		this.leatherArmor = false;
	}
	
	public BallerItem(String name, Material material, int price, boolean glow, List<String> lore, HashMap<Enchantment, Integer> enchantments, String... aliases)
	{
		this(name, material, price, lore, enchantments, aliases);
		this.glow = glow;
	}

	
	public BallerItem(String name, Material material, String owner, int meta, int price, List<String> lore, HashMap<Enchantment, Integer> enchantments, String... aliases)
	{
		this(name, material, meta, price, lore, enchantments, aliases);
		this.owner = owner;
		this.leatherArmor = false;
		this.multipleMaterials = false;
	}
	
	public BallerItem(String name, Material material, int meta, int price, List<String> lore, HashMap<Enchantment, Integer> enchantments, String... aliases)
	{
		this(name, material, price, lore, enchantments, aliases);
		this.meta = meta;
		this.leatherArmor = false;
		this.multipleMaterials = false;
	}
	
	public BallerItem(String name, Material[] materials, int price, List<String> lore, HashMap<Enchantment, Integer> enchantments, String... aliases)
	{
		this.name = name;
		this.materials = materials;
		this.price = price;
		this.lore = lore;
		this.enchantments = enchantments;
		this.aliases = aliases;
		this.multipleMaterials = true;
		this.leatherArmor = false;
	}
	
	public BallerItem(String name, Material material, int price, List<String> lore, HashMap<Enchantment, Integer> enchantments, LeatherArmorMeta itemMeta, String... aliases)
	{
		this(name, material, price, lore, enchantments, aliases);
		this.leatherArmorMeta = itemMeta;
		this.leatherArmor = true;
		this.multipleMaterials = false;
	}
	
	public boolean shouldGlow()
	{
		return this.glow;
	}
	
	public boolean isLeatherArmor()
	{
		return this.leatherArmor;
	}
	
	public LeatherArmorMeta getLeatherArmorMeta()
	{
		return this.leatherArmorMeta;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getOwner()
	{
		return this.owner;
	}
	
	public int getMeta()
	{
		return this.meta;
	}
	
	public boolean hasMeta()
	{
		return this.meta > 0;
	}
	
	public boolean hasOwner()
	{
		return this.owner != null;
	}
	
	public Material getMaterial()
	{
		return this.material;
	}
	
	public Material[] getMaterials()
	{
		return this.materials;
	}
	
	public int getPrice()
	{
		return this.price;
	}
	
	public List<String> getLore()
	{
		return this.lore;
	}
	
	public HashMap<Enchantment, Integer> getEnchantments()
	{
		return this.enchantments;
	}
	
	public String[] getAliases()
	{
		return this.aliases;
	}
	
	public boolean hasMultipleMaterials()
	{
		return this.multipleMaterials;
	}
}

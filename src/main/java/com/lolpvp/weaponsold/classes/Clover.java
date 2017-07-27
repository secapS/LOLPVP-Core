package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class Clover extends BallerItem 
{
	ArrayList<ItemStack> items = new ArrayList<>();
	Random rand = new Random();
	@SuppressWarnings("deprecation")
	public Clover() 
	{
		super(ChatColor.AQUA + "4 Leaf Clover", Material.getMaterial(175), 3, 1, lore(), enchantments(), "clover", "4leafclover");
		this.addToArray(this.getPVPChest(), 25);
		this.addToArray(this.getStPatChest(), 25);
		for(ItemStack i : this.getFlowers())
		{
			this.addToArray(i, 7);	
		}
		// TODO Auto-generated constructor stub
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		if(ItemManager.getInstance().isBallerItem(event.getPlayer().getItemInHand(), this))
		{
			event.setCancelled(true);
			if(event.getPlayer().getItemInHand().getAmount() > 1)
			{
				event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
				event.getPlayer().getInventory().addItem(this.items.get(rand.nextInt(this.items.size())));
			}
			else
			{
				event.getPlayer().setItemInHand(null);
				event.getPlayer().getInventory().addItem(this.items.get(rand.nextInt(this.items.size())));
			}
			event.getPlayer().updateInventory();
			final Firework fw = (Firework) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.FIREWORK);
			FireworkMeta data = fw.getFireworkMeta();
			data.addEffect(FireworkEffect.builder().with(Type.BURST).flicker(true).withColor(Color.GREEN).withFlicker().build());
			data.setPower(1);
			fw.setFireworkMeta(data);
		}
	}
	
	public ItemStack getPVPChest()
	{
		ItemStack item = new ItemStack(Material.CHEST);
		ItemMeta sitem = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Right click to unwrap!");
		sitem.setDisplayName(ChatColor.RED + "PVP Chest");
		sitem.setLore(lore);
		item.setItemMeta(sitem);
		return item;
	}
	
	public ItemStack getStPatChest()
	{
		ItemStack item = new ItemStack(Material.CHEST);
		ItemMeta sitem = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Right click to unwrap!");
		sitem.setDisplayName(ChatColor.RED + "StPatricks Chest");
		sitem.setLore(lore);
		item.setItemMeta(sitem);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack[] getFlowers()
	{
		ItemStack[] flowers = {new ItemStack(Material.getMaterial(38), 2304, (short)0), new ItemStack(Material.getMaterial(38), 2304, (short)1), 
		new ItemStack(Material.getMaterial(38), 2304, (short)2), new ItemStack(Material.getMaterial(38), 2304, (short)3), new ItemStack(Material.getMaterial(38), 2304, (short)4),
		new ItemStack(Material.getMaterial(38), 2304, (short)5), new ItemStack(Material.getMaterial(38), 2304, (short)6), new ItemStack(Material.getMaterial(38), 2304, (short)7),
		new ItemStack(Material.getMaterial(38), 2304, (short)8)};
		return flowers;
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "Right click to see what you get!");
			this.add(ChatColor.DARK_GRAY + "St Patricks Day 2015");
		}};
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.LOOT_BONUS_MOBS, 4);
		}};
	}

	public void addToArray(ItemStack item, int amount)
	{
		for(int i = 0; i < amount; i++)
		{
			items.add(item);
		}
	}
}

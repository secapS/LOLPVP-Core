package com.lolpvp.weapons.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import com.lolpvp.core.Core;
import com.lolpvp.weapons.BallerItem;
import com.lolpvp.weapons.BallerItemTask;

public class SnowArmor extends BallerItem
{
	public HashMap<String, BukkitTask> players = new HashMap<String, BukkitTask>();
	Core plugin;
	public SnowArmor(Core core) {
		super(ChatColor.AQUA + "Snow Armor", new Material[]{Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS},
				1500000, lore(), enchantments(), "snowarmor", "sarmor", "snow");
		this.plugin = core;
		// TODO Auto-generated constructor stub
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if(isSnowArmor(player.getInventory().getArmorContents()))
		{
			players.put(player.getName(), new BallerItemTask(player, this).runTaskTimer(this.plugin, 20L, 0L));	
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		if(players.containsKey(player.getName()))
		{
			players.get(player.getName()).cancel();
			players.remove(player.getName());
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event)
	{
		if(event.getPlayer() instanceof Player)
		{
			Player player = (Player)event.getPlayer();
			if(isSnowArmor(player.getEquipment().getArmorContents()) && !players.containsKey(player.getName()))
			{
				players.put(player.getName(), new BallerItemTask(player, this).runTaskTimer(this.plugin, 20L, 0L));	
			}
			else if(!isSnowArmor(player.getEquipment().getArmorContents()) && players.containsKey(player.getName()))
			{
				players.get(player.getName()).cancel();
				players.remove(player.getName());
			}
		}
	}
	
	public boolean isSnowArmor(ItemStack[] stack)
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
	
	public ItemStack[] getItemStacks(int amount)
	{
		ItemStack[] armor = {new ItemStack(this.getMaterials()[0], amount),
				  new ItemStack(this.getMaterials()[1], amount),
				  new ItemStack(this.getMaterials()[2], amount),
				  new ItemStack(this.getMaterials()[3], amount)};
		ItemMeta meta = armor[0].getItemMeta();
		meta.setLore(this.getLore());
		meta.setDisplayName(this.getName());
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

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() {
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
		}};
	}

	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Snowstorm I");
			this.add(ChatColor.DARK_GRAY + "Wear a full set of Snow Armor for a special effect");
		}};
	}
}

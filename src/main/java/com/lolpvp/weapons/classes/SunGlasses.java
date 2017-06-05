package com.lolpvp.weapons.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.lolpvp.weapons.BallerItem;
import com.lolpvp.weapons.ItemManager;

public class SunGlasses extends BallerItem
{

	public SunGlasses() {
		super(ChatColor.AQUA + "Sun Glasses", Material.DIAMOND_HELMET, 1, lore(), enchantments(), "sunglasses");
		// TODO Auto-generated constructor stub
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
	private static List<String> lore() {
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Tint I");
			this.add(ChatColor.DARK_GRAY + "LOLPVP limited edition sunglasses! (Original Edition)");
		}};
	}

	ArrayList<String> Shades = new ArrayList<String>();
	
	@EventHandler
	public void OnPlayerWearGlasses(InventoryCloseEvent e) 
	{
		if (e.getPlayer() instanceof Player) 
		{
			Player player = (Player) e.getPlayer();
			ItemStack helm = player.getInventory().getHelmet();

			if (Shades.contains(player.getName())) 
			{
				if(!ItemManager.getInstance().isBallerItem(helm, this))
				{
					player.removePotionEffect(PotionEffectType.NIGHT_VISION);
					player.setPlayerTime(player.getWorld().getTime(), true);
					Shades.remove(player.getName());
					return;	
				}
			}
			if(player.getEquipment().getHelmet() != null && ItemManager.getInstance().isBallerItem(helm, this))
			{
				if (player.getWorld().getTime() < 13000) 
				{
					player.setPlayerTime(13000, true);
				}
				Shades.add(player.getName());
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000, 5));	
			}
		}
	}
	@EventHandler
	public void OnPlayerDeath(PlayerDeathEvent e) 
	{
		if (Shades.contains(e.getEntity().getName())) 
		{
			e.getEntity().setPlayerTime(e.getEntity().getWorld().getTime(), true);
			Shades.remove(e.getEntity().getName());
		}
	}
}

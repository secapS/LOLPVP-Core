package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class ParkourBoots extends BallerItem
{
	public ParkourBoots()
	{
		super(ChatColor.AQUA + "Parkour Boots", Material.LEATHER_BOOTS, 1, lore(), enchantments(), leatherArmorMeta(), "parkourboots");
	}
	
	@EventHandler
	public void onPlayerDamaged(EntityDamageEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			if(event.getCause().equals(DamageCause.FALL))
			{
				if(player.getEquipment().getBoots() != null && ItemManager.getInstance().isBallerItem(player.getEquipment().getBoots(), this))
				{
					event.setCancelled(true);
				}
			}
		}
	}
	
	public static LeatherArmorMeta leatherArmorMeta()
	{
		ItemStack stack = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
		meta.setColor(Color.WHITE);
		return meta;
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "Free Runner boots.");
		}};
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.PROTECTION_FALL, 10);
		}};
	}
}

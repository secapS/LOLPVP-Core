package com.lolpvp.weapons.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.lolpvp.weapons.BallerItem;
import com.lolpvp.weapons.ItemManager;

public class WitchHat extends BallerItem
{
	public WitchHat() 
	{
		super("Witch's Hat", Material.DIAMOND_HELMET, 1, lore(), enchantments(), "wh", "witchhat", "witchshat");
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "A special Halloween item that prevents all fall damage!");
		}};
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.WATER_WORKER, 5);
			this.put(Enchantment.OXYGEN, 5);
			this.put(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
			this.put(Enchantment.PROTECTION_EXPLOSIONS, 5);
			this.put(Enchantment.PROTECTION_FIRE, 5);
			this.put(Enchantment.PROTECTION_PROJECTILE, 5);
		}};
	}
	
	@EventHandler
	public void onPlayerDamaged(EntityDamageEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			if(event.getCause().equals(DamageCause.FALL))
			{
				if(player.getEquipment().getHelmet() != null && ItemManager.getInstance().isBallerItem(player.getEquipment().getHelmet(), this))
				{
					event.setCancelled(true);
				}
			}
		}
	}

}

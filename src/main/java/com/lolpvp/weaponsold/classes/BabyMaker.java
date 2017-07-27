package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.lolpvp.core.Core;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class BabyMaker extends BallerItem
{

	public BabyMaker()
	{
		super(ChatColor.AQUA + "Baby Maker", Material.GOLD_SWORD, 3000000, lore(), enchantments(), "babymaker", "baby", "bm");
		// TODO Auto-generated constructor stub
	}
	
	@EventHandler
	public void onInteract(EntityDamageByEntityEvent event)
	{
		if(event.getDamager() instanceof Player)
		{
			Player player = (Player)event.getDamager();
			if(event.getEntity() instanceof Player)
			{
				if(Core.isPlayerInPVP(((Player)event.getEntity())) && !InvisRing.in.contains(player.getName()))
				{
					if(ItemManager.getInstance().isBallerItem(player.getItemInHand(), this))
					{
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5 * 20, 1));
					}
				}
			}
		}
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Resistance I");
			this.add(ChatColor.DARK_GRAY + "Hit to take less damage from enemies!");
		}};
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DAMAGE_ALL, 10);
			this.put(Enchantment.DAMAGE_UNDEAD, 10);
			this.put(Enchantment.DAMAGE_ARTHROPODS, 5);
			this.put(Enchantment.LOOT_BONUS_MOBS, 5);
			this.put(Enchantment.DURABILITY, 10);
		}};
	}
}

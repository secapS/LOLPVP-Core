package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class Chainsaw extends BallerItem
{

	public Chainsaw() 
	{
		super(ChatColor.AQUA + "Leatherface's Chainsaw", Material.DIAMOND_SWORD, 6000000, lore(), enchantments(), "leatherfaceschainsaw", "leatherfacechainsaw", "chainsaw", "leather", "chain");
		// TODO Auto-generated constructor stub
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event)
	{
		if(event.getDamager() instanceof Player)
		{
			Player player = (Player)event.getDamager();
			if(event.getEntity() instanceof LivingEntity)
			{
				LivingEntity hit = (LivingEntity)event.getEntity();
				
				if(ItemManager.getInstance().isBallerItem(player.getItemInHand(), this))
				{
					for(int i = 0; i < 2; i++)
					{
						player.getWorld().playEffect(hit.getLocation().add(0.0D, 1.0D, 0.0D), Effect.STEP_SOUND, 152);
					}
				}
			}
		}
	}
	
	
	@SuppressWarnings("serial")
	private static List<String> lore() 
	{
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "The chainsaw used by Leatherface during the");
			this.add(ChatColor.DARK_GRAY + "Texas Chain Saw Massacre. Now you can use it");
			this.add(ChatColor.DARK_GRAY + "to slay your enemies in PVP and spew");
			this.add(ChatColor.DARK_GRAY + "their blood everywhere!");
		}};	
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() 
	{
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.LOOT_BONUS_MOBS, 10);
			this.put(Enchantment.DAMAGE_UNDEAD, 10);
			this.put(Enchantment.DAMAGE_ALL, 10);
		}};
	}
}

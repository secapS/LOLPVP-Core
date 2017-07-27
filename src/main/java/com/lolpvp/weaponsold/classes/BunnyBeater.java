package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.util.Vector;

import com.lolpvp.core.Core;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class BunnyBeater extends BallerItem
{

	public BunnyBeater() 
	{
		super(ChatColor.AQUA + "Bunny Beater", Material.DIAMOND_SWORD, 1, lore(), enchantments(), "bunnybeater", "bb");
		// TODO Auto-generated constructor stub
	}
	
	@EventHandler
	public void onInteractItem(PlayerInteractEntityEvent event)
	{
		Player player = event.getPlayer();
		if(event.getRightClicked() instanceof LivingEntity)
		{
			LivingEntity entity = (LivingEntity) event.getRightClicked();
			if(Core.isPlayerInPVP(player) && !InvisRing.in.contains(player.getName()))
			{
				if(ItemManager.getInstance().isBallerItem(player.getItemInHand(), this))
				{
					entity.setVelocity(new Vector(0, .65, 0));
					entity.getWorld().playSound(entity.getLocation(), Sound.ENDERDRAGON_WINGS, 1F, 1F);
				}	
			}
		}
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>(){{
			this.add(ChatColor.GRAY + "Launcher 0.5");
			this.add(ChatColor.DARK_GRAY + "The Easter Bunny's sword! A special Easter item.");
		}};
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>(){{
			this.put(Enchantment.DAMAGE_ALL, 10);
			this.put(Enchantment.DAMAGE_UNDEAD, 10);
			this.put(Enchantment.DAMAGE_ARTHROPODS, 5);
			this.put(Enchantment.LOOT_BONUS_MOBS, 5);
		}};
	}

}

package com.lolpvp.weapons.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import com.lolpvp.core.Core;
import com.lolpvp.weapons.BallerItem;
import com.lolpvp.weapons.ItemManager;

public class ZeusBow extends BallerItem
{
	ArrayList<Entity> arrows = new ArrayList<Entity>();
	Core plugin;

	public ZeusBow(Core core) 
	{
		super(ChatColor.AQUA + "Zeus Bow", Material.BOW, 300000, true, lore(), enchantments(), "zeusbow", "lightningbow");
		this.plugin = core;
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onArrowHit(ProjectileHitEvent event)
	{
		this.check(arrows, event.getEntity());
		if(event.getEntity().getShooter() instanceof Player)
		{
			Player player = (Player)event.getEntity().getShooter();
			if(Core.isPlayerInPVP(player))
			{
				if(event.getEntity() instanceof Arrow)
				{
					Location location = event.getEntity().getLocation();
					if(this.arrows.contains(event.getEntity()))
					{
						player.getWorld().strikeLightning(location);
					}
				}	
			}
		}
	}
	
	@EventHandler
	public void onShootBow(EntityShootBowEvent event)
	{
		if(event.getProjectile() instanceof Arrow)
		{
			Arrow arrow = (Arrow)event.getProjectile();
			ItemStack bow = event.getBow();
			
			if(ItemManager.getInstance().isBallerItem(bow, this))
			{
				this.arrows.add(arrow);
			}
		}
	}

	public void check(final ArrayList<Entity> a, final Entity e)
	{
		if (a.contains(e)) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
			{
				public void run()
				{
					a.remove(e);
				}
			}, 3L);
		}
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
		}};
	}

	@SuppressWarnings("serial")
	private static List<String> lore() 
	{
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Lightning I");
			this.add(ChatColor.DARK_GRAY + "Shoot enemies to strike them with lightning!");
		}};
	}

}

package com.lolpvp.weapons.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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

public class EnderBow extends BallerItem
{
	ArrayList<Entity> arrows = new ArrayList<Entity>();
	Core plugin;
	public EnderBow(Core core) 
	{
		super(ChatColor.AQUA + "Ender Bow", Material.BOW, 300000, true, lore(), enchantments(), "enderbow", "teleportbow", "ebow");
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
			if(event.getEntity() instanceof Arrow)
			{
				if(this.arrows.contains(event.getEntity()))
				{
					Location location = new Location(event.getEntity().getWorld(), event.getEntity().getLocation().getX(), event.getEntity().getLocation().getY(), event.getEntity().getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
					player.teleport(location);
		            player.getWorld().playEffect(event.getEntity().getLocation(), Effect.ENDER_SIGNAL, 4);
		            player.getWorld().playSound(event.getEntity().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
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
	private static List<String> lore() 
	{
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Teleport I");
			this.add(ChatColor.DARK_GRAY + "Shoot to teleport!");
		}};
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
		}};
	}
}

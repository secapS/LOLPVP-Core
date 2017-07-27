package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import com.lolpvp.core.Core;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class RocketBoots extends BallerItem
{
	Core plugin;
	public RocketBoots(Core core)
	{
		super(ChatColor.AQUA + "Rocket Boots", Material.DIAMOND_BOOTS, 6000000, lore(), enchantments(), "rocketboots", "rb");
		this.plugin = core;
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() 
	{
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
			this.put(Enchantment.PROTECTION_EXPLOSIONS, 5);
			this.put(Enchantment.PROTECTION_FALL, 5);
			this.put(Enchantment.PROTECTION_FIRE, 5);
			this.put(Enchantment.PROTECTION_PROJECTILE, 5);
		}};
	}

	@SuppressWarnings("serial")
	private static List<String> lore() 
	{
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Hover Jump I");
			this.add(ChatColor.DARK_GRAY + "Double tap shift to hover jump!");
		}};
	}

	public final HashSet<String> inrocket = new HashSet<String>();
	public final HashMap<String, Integer> rr = new HashMap<String, Integer>();

	@EventHandler
	public void onDamage(EntityDamageEvent event)
	{
		if ((event.getEntity() instanceof Player))
		{
			Player player = (Player)event.getEntity();
			if ((this.inrocket.contains(player.getName())) && 
					(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)))
			{
				event.setCancelled(true);
				this.inrocket.remove(player.getName());
			}
		}
	}

	@EventHandler
	public void onRocket(PlayerToggleSneakEvent event)
	{
		final Player player = event.getPlayer();
		if ((player.getInventory().getBoots() != null) && 
				(!player.getInventory().getBoots().getType().equals(Material.AIR)) && 
				(player.getInventory().getBoots().getType().equals(Material.DIAMOND_BOOTS)) && 
				(ItemManager.getInstance().isBallerItem(player.getInventory().getBoots(), this)) && 
				(!IceBlade.froze.contains(player.getName())))
		{
			if (this.rr.containsKey(player.getName()))
			{
				if (((Integer)this.rr.get(player.getName())).intValue() >= 2)
				{
					this.rr.remove(player.getName());
					player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, 1);
					player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, 1);
					player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 4);
					player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 4);
					player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 1.0F);
					player.setVelocity(new Vector(player.getVelocity().getX(), 2.2, player.getVelocity().getZ()));
					this.inrocket.add(player.getName());
				}
				else
				{
					this.rr.put(player.getName(), Integer.valueOf(((Integer)this.rr.get(player.getName())).intValue() + 1));
				}
			}
			else
			{
				this.rr.put(player.getName(), Integer.valueOf(1));
			}
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
			{
				public void run()
				{
					RocketBoots.this.rr.remove(player.getName());
				}
			}, 10L);
		}
	}
}

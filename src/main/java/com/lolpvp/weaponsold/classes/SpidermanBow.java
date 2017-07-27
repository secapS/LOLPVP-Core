package com.lolpvp.weaponsold.classes;

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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.lolpvp.core.Core;
import com.lolpvp.utils.Cooldowns;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class SpidermanBow extends BallerItem 
{
	Core plugin;
	public SpidermanBow(Core instance) 
	{
		super(ChatColor.AQUA + "Spider-Man Bow", Material.BOW, 1, lore(), enchantments(), "spidermanbow");
		this.plugin = instance;
	}
	
	ArrayList<Entity> arrows = new ArrayList<Entity>();
	HashMap<Player,Location> loc = new HashMap<Player,Location>();
	HashMap<Player,Location> loc2 = new HashMap<Player,Location>();
	
	
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
	
	@EventHandler
	public void onArrowHit(ProjectileHitEvent event)
	{
		this.check(arrows, event.getEntity());
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
				arrows.add(arrow);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		if(event.getEntity() instanceof Player 
				&& event.getDamager() instanceof Arrow)
		{
			final Player player = (Player)event.getEntity();
			Arrow arrow = (Arrow)event.getDamager();
			if(!(arrow.getShooter() instanceof Player))
				return;
			if(Core.isPlayerInPVP(player))
			{
				if(ItemManager.getInstance().isBallerItem(((Player)arrow.getShooter()).getItemInHand(), this))
				{
					if(Cooldowns.tryCooldown(player, "spiderman", 5 * 1000))
					{
						if(arrows.contains(arrow))
						{
							if (player.getLocation().getY() != player.getLocation().getBlockY() || player.getLocation().add(0, 0.5, 0).getBlock().getType() != Material.AIR)
							{
								loc.put(player, player.getLocation().add(new Vector(0,1,0)));
								loc2.put(player, player.getLocation().add(new Vector(0,2,0)));
								player.getLocation().add(new Vector(0,1,0)).getBlock().setType(Material.WEB);
								player.getLocation().add(new Vector(0,2,0)).getBlock().setType(Material.WEB);
							} 
							else 
							{
								loc.put(player, player.getLocation());
								loc2.put(player, player.getLocation().add(new Vector(0,1,0)));
								player.getLocation().getBlock().setType(Material.WEB);
								player.getLocation().add(new Vector(0,1,0)).getBlock().setType(Material.WEB);
							}
							plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable()
						    {
						        @Override
						      	public void run()
						      	{
						        	loc.get(player).getBlock().setType(Material.AIR);
						        	loc2.get(player).getBlock().setType(Material.AIR);
						        	
						        	loc.remove(player);
						        	loc2.remove(player);
						      	}
						    }, 100L);
						}	
					}
					else
					{
						Long lol = Long.valueOf(Cooldowns.getCooldown(player, "spiderman"));
						int bbb = lol.intValue() / 1000;
						((Player)arrow.getShooter()).sendMessage(this.getName() + ChatColor.RED + " is on cooldown for " + Integer.toString(bbb) + " seconds.");
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
			this.add(ChatColor.GRAY + "Web Shooter I");
			this.add(ChatColor.DARK_GRAY + "Hit an enemy with an arrow to trap them in a web!");
		}};
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.ARROW_DAMAGE, 10);
		}};
	}
}

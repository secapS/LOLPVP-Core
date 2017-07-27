package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import com.lolpvp.utils.ParticleEffect;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

/**
 * Created by Luke on 6/16/2015.
 */
public class AngelWings extends BallerItem
{
    public AngelWings()
    {
        super(ChatColor.AQUA + "Angel Wings", Material.DIAMOND_CHESTPLATE, 1, lore(), enchantments(), "angelwings");
    }
    
    HashMap<String, Location> locs = new HashMap<String, Location>();
	ArrayList<String> hitPlayers = new ArrayList<String>();
	ArrayList<String> FlyingPlayers = new ArrayList<String>();
	
	public boolean checkHeight(Player p, Location loc) 
	{
		if (locs.get(p.getName()) != null) 
		{
			if (locs.get(p.getName()).getY() + 20 > loc.getY()) 
			{
				return true;
			}
		}
		return false;
	}
	
	@EventHandler
	public void OnPlayerSneak(PlayerToggleSneakEvent e) 
	{
		Player p = e.getPlayer();
		if (ItemManager.getInstance().isBallerItem(p.getInventory().getChestplate(), this)) 
		{
			if (p.getLocation().getBlock().getType() != Material.AIR && locs.containsKey(p.getName())) 
			{
				locs.remove(p.getName());
			}
			if (locs.get(p.getName()) == null) 
			{
				locs.put(p.getName(), p.getLocation());
			}
			if (checkHeight(p, p.getLocation()) && !hitPlayers.contains(p.getName())) 
			{
				p.setVelocity(p.getEyeLocation().getDirection().multiply(0.5).add(new Vector(0,0.5,0)));
    			p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1f, 1f);
    			ParticleEffect.FIREWORKS_SPARK.display(p.getLocation(), new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.25f, 30);
    			if (p.getAllowFlight() != true) 
    			{
    				p.setAllowFlight(true);
    			}
    			if (!FlyingPlayers.contains(p.getName())) 
    			{
    				FlyingPlayers.add(p.getName());
    			}
			}
		}
	}
	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent e) 
	{
		Player p = e.getPlayer();
		if (ItemManager.getInstance().isBallerItem(p.getInventory().getChestplate(), this) || FlyingPlayers.contains(p.getName())) 
		{
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onArrowHitPlayer(EntityDamageByEntityEvent e) 
	{
		if (e.getDamager() instanceof Arrow) 
		{
			if (e.getEntity() instanceof Player) 
			{
				Player p = (Player) e.getEntity();
				if (ItemManager.getInstance().isBallerItem(p.getInventory().getChestplate(), this)) 
				{
					if (p.getLocation().subtract(new Vector(0,1,0)).getBlock().getType() == Material.AIR) 
					{
						hitPlayers.add(p.getName());
					}
				}
			}
		}
	}
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) 
	{
		Player p = e.getPlayer();
		if (FlyingPlayers.contains(p.getName())) 
		{
			if (!ItemManager.getInstance().isBallerItem(p.getInventory().getChestplate(), this)) 
			{
				p.setAllowFlight(false);
			}
			if (p.getLocation().getBlock().getType() != Material.AIR) 
			{
				p.setAllowFlight(false);
				FlyingPlayers.remove(p.getName());
				if (hitPlayers.contains(p.getName())) 
				{
					hitPlayers.remove(p.getName());
				}
			}
		}
	}
    
    @SuppressWarnings("serial")
	private static List<String> lore()
    {
        return new ArrayList<String>()
        {{
                this.add(ChatColor.GRAY + "Flight I");
                this.add(ChatColor.DARK_GRAY + "Shift to fly!");
            }};
    }

    @SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
    {
        return new HashMap<Enchantment, Integer>()
        {{
                this.put(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        }};
    }
}

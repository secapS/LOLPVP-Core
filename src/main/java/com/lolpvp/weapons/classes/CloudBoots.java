package com.lolpvp.weapons.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.lolpvp.utils.ParticleEffect;
import com.lolpvp.weapons.BallerItem;
import com.lolpvp.weapons.ItemManager;


public class CloudBoots extends BallerItem
{
	public CloudBoots() 
	{
		super(ChatColor.AQUA + "Cloud Boots", Material.DIAMOND_BOOTS, 1, lore(), enchantments(), "cloudboots");
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("serial")
	public static HashMap<Enchantment, Integer> enchantments() {
		return new HashMap<Enchantment, Integer>()
				{{
					this.put(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
					this.put(Enchantment.PROTECTION_FIRE, 5);
					this.put(Enchantment.PROTECTION_EXPLOSIONS, 5);
					this.put(Enchantment.PROTECTION_PROJECTILE, 5);
					this.put(Enchantment.PROTECTION_FALL, 5);
				}};
	}
	@SuppressWarnings("serial")
	public static List<String> lore() {
		return new ArrayList<String>()
				{{
					this.add(ChatColor.GRAY + "Double Jump I");
					this.add(ChatColor.DARK_GRAY + "Special boots that allow you to double jump!");
				}};
	}
	
	Random rand = new Random();
	ArrayList<String> Cloud = new ArrayList<String>();

	@EventHandler
	public void OnPlayerJump(PlayerMoveEvent e) 
	{
		Player p = e.getPlayer();
		ItemStack boots = p.getInventory().getBoots();
		
		if (Cloud.contains(p.getName()) && !ItemManager.getInstance().isBallerItem(boots, this)) 
		{
			p.setAllowFlight(false);
			p.setFlying(false);
			Cloud.remove(p.getName());
			return;
		}
		if (ItemManager.getInstance().isBallerItem(boots, this)) 
		{
			if (p.getGameMode() != GameMode.CREATIVE) 
			{
				if (p.getLocation().subtract(new Vector(0,1,0)).getBlock().getType() != Material.AIR) 
				{
					p.setFlying(false);
					if (!p.isFlying()) 
					{
						if (p.getAllowFlight() == false) 
						{
							p.setAllowFlight(true);
						}
					}
				}
			}
		}
	}
	@EventHandler
    public void setFlyOnJump(PlayerToggleFlightEvent event) 
	{
        Player p = event.getPlayer();
        ItemStack boots = p.getInventory().getBoots();
        if(p.getGameMode() != GameMode.CREATIVE) 
        {
            if (event.isFlying() && ItemManager.getInstance().isBallerItem(boots, this))
			{
            	event.setCancelled(true);
            	p.setFlying(false);
            	p.setAllowFlight(false);
                p.setVelocity(p.getLocation().getDirection().zero().setY(0.8));
    			p.getWorld().playSound(p.getLocation(), Sound.DIG_SNOW, 3f, 1.3f);
				ParticleEffect.SNOW_SHOVEL.display(p.getLocation(), rand.nextFloat(), rand.nextFloat(), rand.nextInt(1), 0.25f, 30);
                if (!Cloud.contains(p.getName())) 
    			{
    				Cloud.add(p.getName());
    			}
                return;
			}
        }
    }
	@EventHandler
	public void OnPlayerDeath(PlayerDeathEvent e) 
	{
		if (Cloud.contains(e.getEntity().getName())) 
		{
			e.getEntity().setAllowFlight(false);
			e.getEntity().setFlying(false);
			Cloud.remove(e.getEntity().getName());
		}
	}
	@EventHandler
	public void OnPlayerJoin(PlayerJoinEvent e) 
	{
		if (Cloud.contains(e.getPlayer().getName())) 
		{
			e.getPlayer().setAllowFlight(false);
			e.getPlayer().setFlying(false);
			Cloud.remove(e.getPlayer().getName());
		}
	}
}
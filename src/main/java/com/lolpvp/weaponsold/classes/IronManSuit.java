package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.confuser.barapi.BarAPI;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.lolpvp.core.Core;
import com.lolpvp.weaponsold.BallerItem;

public class IronManSuit extends BallerItem 
{
	Core plugin;
	public IronManSuit(Core instance)
	{
		super(ChatColor.AQUA + "MK II", new Material[]{Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS}, 1, lore(), enchantments(), "ironmansuit", "ironmanarmor");
		this.plugin = instance;
	}
	
	ArrayList<String> Armor = new ArrayList<String>();
	ArrayList<String> Toggle = new ArrayList<String>();
	public ArrayList<UUID> cooldown = new ArrayList<UUID>();
	public ArrayList<UUID> cooldown2 = new ArrayList<UUID>();
	
	@EventHandler
	public void OnPlayerJump(PlayerMoveEvent e) 
	{
		Player p = e.getPlayer();
		
		if (Armor.contains(p.getName()) && !isWearingSuit(p.getInventory().getArmorContents())) 
		{
			p.setAllowFlight(false);
			p.setFlying(false);
			if (BarAPI.hasBar(p)) 
			{
				BarAPI.removeBar(p);
			}
			Armor.remove(p.getName());
			if (Toggle.contains(p.getName())) 
			{
				Toggle.remove(p.getName());
			}
			return;
		}
		if (isWearingSuit(p.getInventory().getArmorContents())) 
		{
			if (p.getGameMode() != GameMode.CREATIVE && p.getLocation().subtract(0,1,0).getBlock().getType() != Material.AIR && !p.isFlying()) 
			{
				
				if (!Armor.contains(p.getName())) 
				{
					Armor.add(p.getName());
					p.setAllowFlight(true);
				}
			}
			
			if(Toggle.contains(p.getName()))
			{
				for(int i = 0; i < 27; i++)
	            	p.getWorld().playEffect(p.getLocation(), Effect.TILE_BREAK, 51);
			}
		}
	}
	@EventHandler
    public void JetPackUsage(PlayerToggleFlightEvent event) 
	{
        final Player p = event.getPlayer();
        
        if (cooldown.contains(p.getUniqueId()) && !cooldown2.contains(p.getUniqueId())) 
        {
        	cooldown2.add(p.getUniqueId());
        	new BukkitRunnable()
    		{
    			Integer seconds = 5;
    			public void run()
    			{
    				if (seconds == 0) 
    				{
    					cooldown.remove(p.getUniqueId());
    					cooldown2.remove(p.getUniqueId());
    					cancel();
    					return;
    				} else {
    			    	seconds = Integer.valueOf(seconds.intValue() - 1);
    			    }
    			}
    		}.runTaskTimer(plugin, 0L, 20L);
        }
        
        if(cooldown.contains(p.getUniqueId())) 
        {
            p.sendMessage(ChatColor.RED + "You cannot use that ability yet!");
            event.setCancelled(true);
            return;
        } else {
        	if (event.isCancelled()) 
        	{
        		event.setCancelled(false);
        	}
        }

        if (Armor.contains(p.getName()) && !Toggle.contains(p.getName())) 
        {
        	if(p.getGameMode() != GameMode.CREATIVE) 
            {
            	new BukkitRunnable()
    			{
    				Integer seconds = 30;
    				Double num = 3.3333333333;
    				public void run()
    				{
    					if (seconds > 1) 
    					{
    						seconds = Integer.valueOf(seconds.intValue() - 1);
    						float bar = (float) (seconds.floatValue() * num);
    						if (p.getLocation().subtract(0,1,0).getBlock().getType().equals(Material.AIR)) 
    			            {
        			            BarAPI.setMessage(p, ChatColor.RED + "" + ChatColor.BOLD + "Reactor Power: " + seconds.toString(), bar);
        			    
        			            if (!Toggle.contains(p.getName())) 
        			            {
        			            	Toggle.add(p.getName());
        			            }
    			            } else {
    			            	
    			            	//FOR THE COOLDOWN VVVVVVVVVVVV
    			            	if (!cooldown.contains(p.getUniqueId()))
    			            	{
    			            		cooldown.add(p.getUniqueId());
    			            	}
        			            //FOR THE COOLDOWN ^^^^^^^^^
        			            
    			            	BarAPI.removeBar(p);
    			            	Armor.remove(p.getName());
    			            	Toggle.remove(p.getName());
    			            	p.setAllowFlight(false);
    			            	p.setFlying(false);
    							cancel();
    							return;
    			            }
    					} else {
    						
    						//FOR THE COOLDOWN VVVVVVVVVVVV
    						if (!cooldown.contains(p.getUniqueId()))
     			            {
     			            	cooldown.add(p.getUniqueId());
     			            }
    			            //FOR THE COOLDOWN ^^^^^^^^^
    						
    						BarAPI.removeBar(p);
    					    Armor.remove(p.getName());
    					    Toggle.remove(p.getName());
    					    p.setAllowFlight(false);
    					    p.setFlying(false);
    						cancel();
    						return;
    					}
    				}
    			}.runTaskTimer(plugin, 0L, 20L);
            }
        }
    }
	
	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) 
	{
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) 
		{
			Player p = (Player) e.getEntity();
			
			if (isWearingSuit(p.getInventory().getArmorContents())) 
			{
				e.setDamage(100.0);
			}
		}
	}
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) 
	{
		if (Armor.contains(e.getEntity().getName())) 
		{
			Armor.remove(e.getEntity().getName());
			e.getEntity().setAllowFlight(false);
			e.getEntity().setFlying(false);
		}
		if (Toggle.contains(e.getEntity().getName())) 
		{
			Toggle.remove(e.getEntity().getName());
			e.getEntity().setAllowFlight(false);
			e.getEntity().setFlying(false);
		}
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) 
	{
		if (Armor.contains(e.getPlayer().getName())) 
		{
			Armor.remove(e.getPlayer().getName());
			e.getPlayer().setAllowFlight(false);
			e.getPlayer().setFlying(false);
		}
		if (Toggle.contains(e.getPlayer().getName())) 
		{
			Toggle.remove(e.getPlayer().getName());
			e.getPlayer().setAllowFlight(false);
			e.getPlayer().setFlying(false);
		}
	}
	
	public boolean isWearingSuit(ItemStack[] stack)
	{
		return stack[0] != null
			   && stack[0].hasItemMeta()
			   && stack[1] != null
			   && stack[1].hasItemMeta()
			   && stack[2] != null
			   && stack[2].hasItemMeta()
			   && stack[3] != null
			   && stack[3].hasItemMeta()
			   && stack[0].getItemMeta().hasLore()
			   && stack[1].getItemMeta().hasLore()
			   && stack[2].getItemMeta().hasLore()
			   && stack[3].getItemMeta().hasLore()
			   && stack[0].getItemMeta().getLore().equals(lore())
			   && stack[1].getItemMeta().getLore().equals(lore())
			   && stack[2].getItemMeta().getLore().equals(lore())
			   && stack[3].getItemMeta().getLore().equals(lore());
	}
	
	public ItemStack[] getItemStacks(int amount)
	{
		ItemStack[] armor = {new ItemStack(this.getMaterials()[0], amount),
				  new ItemStack(this.getMaterials()[1], amount),
				  new ItemStack(this.getMaterials()[2], amount),
				  new ItemStack(this.getMaterials()[3], amount)};
		ItemMeta meta = armor[0].getItemMeta();
		meta.setLore(this.getLore());
		meta.setDisplayName(this.getName());
		armor[0].setItemMeta(meta);
		armor[1].setItemMeta(meta);
		armor[2].setItemMeta(meta);
		armor[3].setItemMeta(meta);
		for(ItemStack item : armor)
		{
			for(Enchantment ench : this.getEnchantments().keySet())
			{
				item.addUnsafeEnchantment(ench, this.getEnchantments().get(ench));
			}
		}
		return armor;
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() {
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DURABILITY, 1);
		}};
	}

	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Flight I");
			this.add(ChatColor.DARK_GRAY + "Avengers: Age Of Ultron Collectable Item.");
		}};
	}
}

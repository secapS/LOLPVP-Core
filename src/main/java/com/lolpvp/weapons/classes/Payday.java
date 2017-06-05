package com.lolpvp.weapons.classes;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.lolpvp.core.Core;
import com.lolpvp.utils.FancyMessage;
import com.lolpvp.weapons.BallerItem;
import com.lolpvp.weapons.ItemManager;

public class Payday extends BallerItem
{
	Core plugin;
	public Payday(Core instance) 
	{
		super(ChatColor.AQUA + "Pay Day", Material.DIAMOND_SWORD, 1, lore(), enchantments(), "payday");
		this.plugin = instance;
	}
	
	ArrayList<Item> money = new ArrayList<Item>();
	List<String> lore = new ArrayList<String>();
	
	@EventHandler
	public void onEntityDamageEvent(EntityDamageByEntityEvent event)
	{
		if(event.getDamager() instanceof Player && event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getDamager();
			Player hit = (Player)event.getDamager();
			if(ItemManager.getInstance().isBallerItem(player.getItemInHand(), this))
			{
				 final Firework fw = (Firework) player.getWorld().spawnEntity(hit.getLocation(), EntityType.FIREWORK);
		   	     FireworkMeta data = fw.getFireworkMeta();
		   	     data.addEffect(FireworkEffect.builder().with(Type.BURST).withColor(Color.GREEN).withFade(Color.YELLOW).build());
		   	     fw.setFireworkMeta(data);
		   	     plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable()
		   	     {
			        @Override
			      	public void run()
			      	{
			    	  	fw.detonate();
			      	}
		   	     }, 2L);
		   	    for (int i = 0; i < 10; i++)
			    {
	        		money.add(player.getWorld().dropItemNaturally(player.getLocation().add(0, new Random().nextDouble(), 0), new ItemStack(Material.PAPER)));
			    }
			    plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable()
			    {
			    	@Override
			    	public void run()
			    	{
			    		for(Item items : money)
			    		{
			    			items.remove();
			    		}
			    		money.clear();
			    	}
			    }, 20L);
			}
		}
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) 
	{
		if (money.contains(e.getItem())) 
		{
			e.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) 
	{
		Player p = e.getEntity();
		if(p.getKiller() == null || !(p.getKiller() instanceof Player))
			return;
		Player killer = p.getKiller();
		if (ItemManager.getInstance().isBallerItem(killer.getItemInHand(), this)) 
		{
			String amount = String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(Core.getEconomy().getBalance(p) * 0.1));
			for(Player players : Bukkit.getOnlinePlayers()) {
				new FancyMessage("It's payday bitch!").color(ChatColor.GREEN).tooltip(killer.getName() + " stole " + amount + " from " + p.getName()).send(players);	
			}
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
			ItemStack trophy = new ItemStack(Material.PAPER);
			ItemMeta meta = trophy.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE + "Kill: " + p.getName());
			lore.clear();
			lore.add(ChatColor.DARK_GRAY + "Trophy for killing " + p.getName() + " on " + format.format(now));
			meta.setLore(lore);
			trophy.setItemMeta(meta);
			killer.getInventory().addItem(trophy);
			killer.updateInventory();
			if ((Core.getEconomy().getBalance(p) * 0.1) > 5000000) 
			{
				Core.getEconomy().depositPlayer(killer, 5000000);
				Core.getEconomy().withdrawPlayer(p, 5000000);
			} else {
				Core.getEconomy().depositPlayer(killer, Core.getEconomy().getBalance(p) * 0.1);
				Core.getEconomy().withdrawPlayer(p, Core.getEconomy().getBalance(p) * 0.1);
			}
		}
	}
	
	@SuppressWarnings("serial")
	public static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
					this.put(Enchantment.DAMAGE_ALL, 10);
					this.put(Enchantment.DAMAGE_UNDEAD, 10);
					this.put(Enchantment.LOOT_BONUS_MOBS, 10);
		}};
	}
	@SuppressWarnings("serial")
	public static List<String> lore() 
	{
		return new ArrayList<String>()
		{{
					this.add(ChatColor.GRAY + "Pay Day I");
					this.add(ChatColor.DARK_GRAY + "It's pay day! Steal up to 10% of your enemy's money when you kill them!");
		}};
	}
}

package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.lolpvp.core.Core;
import com.lolpvp.utils.ParticleEffect;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class CupidBow extends BallerItem
{
	Core plugin;
	ArrayList<Entity> arrows = new ArrayList<Entity>();
	public CupidBow(Core core) 
	{
		super(ChatColor.AQUA + "Cupid's Bow", Material.BOW, 1000000, lore(), enchantments(), "cupidsbow", "cupidbow", "cupid");
		this.plugin = core;
		// TODO Auto-generated constructor stub
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		if(event.getEntity() instanceof Player 
				&& event.getDamager() instanceof Arrow)
		{
			Player player = (Player)event.getEntity();
			Arrow arrow = (Arrow)event.getDamager();
			if(Core.isPlayerInPVP(player))
			{
				if(this.arrows.contains(arrow))
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 30));
					ParticleEffect.HEART.display(player.getLocation(), 1.0F, 1.0F, 1.0F, 1.0F, 30);
				}	
			}
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
			this.put(Enchantment.ARROW_INFINITE, 1);
		}};
	}

	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{
			this.add("A rare Valentines Day bow!");
		}};
	}

}

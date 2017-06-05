package com.lolpvp.weapons.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.lolpvp.core.Core;
import com.lolpvp.utils.CustomEntityFirework;
import com.lolpvp.weapons.BallerItem;
import com.lolpvp.weapons.ItemManager;

public class EggsplodingEgg extends BallerItem
{	
	public EggsplodingEgg() 
	{
		super(ChatColor.AQUA + "Eggsploding Egg", Material.EGG, 1, lore(), enchantments(), "eggsplodingegg");
		// TODO Auto-generated constructor stub
	}
	
	ArrayList<Egg> eggs = new ArrayList<Egg>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLaunch(ProjectileLaunchEvent event)
	{
		if(event.getEntity().getShooter() instanceof Player)
		{
			if(event.getEntity() instanceof Egg)
			{
				Player player = (Player)event.getEntity().getShooter();
				if(eggShooters.remove(player.getName()))
				{
					this.eggs.add((Egg)event.getEntity());
				}
			}
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		eggShooters.remove(event.getPlayer().getName());
	}
	
	List<String> eggShooters = new ArrayList<String>();
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(ItemManager.getInstance().isBallerItem(event.getItem(), this)) {
				if(event.getItem().getAmount() == 1) {
					player.setItemInHand(null);
				}
				eggShooters.add(player.getName());
			}
		}
	}
	
	FireworkEffect.Builder builder = FireworkEffect.builder();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLand(ProjectileHitEvent event)
	{
		if(event.getEntity() instanceof Egg)
		{
			if(this.eggs.remove((Egg)event.getEntity()))
			{
				FireworkEffect effect = builder.flicker(false).trail(false).with(FireworkEffect.Type.BALL_LARGE).withColor(Color.FUCHSIA).withFade(Color.AQUA).build();
				CustomEntityFirework.spawn(event.getEntity().getLocation(), effect);
				Egg egg = (Egg)event.getEntity();
				Player thrower = (Player)egg.getShooter();
				if(Core.canBuildHere(thrower, egg.getLocation()))
				{
					event.getEntity().getWorld().playSound(egg.getLocation(), Sound.EXPLODE, 1F, 1F);
					event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.EXPLOSION_LARGE, 0);
					for(Entity e : event.getEntity().getNearbyEntities(5, 5, 5))
					{
						if(e instanceof LivingEntity)
						{
							if(Core.isEntityInPVP(egg) && Core.isEntityInPVP(e))
								((LivingEntity) e).damage(10.0);	
						}
					}
				}
				else if(!Core.isEntityInPVP(egg))
				{
					event.getEntity().getWorld().playSound(egg.getLocation(), Sound.EXPLODE, 1F, 1F);
					event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.EXPLOSION_LARGE, 0);
				}
				else
				{
					event.getEntity().getWorld().playSound(egg.getLocation(), Sound.EXPLODE, 1F, 1F);
					event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.EXPLOSION_LARGE, 0);
				}
			}	
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event)
	{
		if(event.getDamager() instanceof Egg)
		{
			if(this.eggs.remove((Egg)event.getDamager()))
			{
				FireworkEffect effect = builder.flicker(false).trail(false).with(FireworkEffect.Type.BALL).withColor(Color.FUCHSIA).withFade(Color.AQUA).build();
				CustomEntityFirework.spawn(event.getEntity().getLocation(), effect);
				Egg egg = (Egg)event.getDamager();
				Player thrower = (Player)egg.getShooter();
				
				if(Core.canBuildHere(thrower, egg.getLocation()))
				{
					event.getEntity().getWorld().playSound(egg.getLocation(), Sound.EXPLODE, 1F, 1F);
					event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.EXPLOSION_LARGE, 0);
					for(Entity e : event.getEntity().getNearbyEntities(5, 5, 5))
					{
						if(e instanceof LivingEntity)
						{
							if(Core.isEntityInPVP(egg) && Core.isEntityInPVP(e))
								((LivingEntity) e).damage(10.0);	
						}
					}
				}
				else if(!Core.isEntityInPVP(egg))
				{
					event.getEntity().getWorld().playSound(egg.getLocation(), Sound.EXPLODE, 1F, 1F);
					event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.EXPLOSION_HUGE, 0);
				}
				else
				{
					event.getEntity().getWorld().playSound(egg.getLocation(), Sound.EXPLODE, 1F, 1F);
					event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.EXPLOSION_LARGE, 0);
				}
			}
		} 
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() {
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			
		}};
	}

	@SuppressWarnings("serial")
	private static List<String> lore() {
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Right click to throw.");
		}};
	}
}

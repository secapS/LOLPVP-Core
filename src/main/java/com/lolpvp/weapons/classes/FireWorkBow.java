package com.lolpvp.weapons.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import com.lolpvp.core.Core;
import com.lolpvp.weapons.BallerItem;
import com.lolpvp.weapons.ItemManager;

public class FireWorkBow extends BallerItem
{
	ArrayList<Entity> arrows = new ArrayList<Entity>();
	Core plugin;
	final HashMap<Long, Long> fireworkTimer = new HashMap<Long, Long>();
	
	public FireWorkBow(Core core) {
		super(ChatColor.AQUA + "Firework Bow", Material.BOW, 300000, lore(), enchantments(), "fireworkbow", "fbow", "firework");
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
				Location location = event.getEntity().getLocation();
				if(this.arrows.contains(event.getEntity()))
				{
					for (int i = 1; i <= 3 / 2; i++) {
						shoot(player, location);
					}
					event.getEntity().remove();
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
	
	public void shoot(Player player, Location location)
	{
		Long hashMapID = Long.valueOf(new Random().nextLong());
		List<FireworkEffect.Type> type = new ArrayList<FireworkEffect.Type>();
		List<Color> colour = new ArrayList<Color>();
		Collections.addAll(type, new FireworkEffect.Type[] { FireworkEffect.Type.BALL, FireworkEffect.Type.BALL_LARGE, FireworkEffect.Type.BURST, FireworkEffect.Type.STAR, FireworkEffect.Type.CREEPER });
		Collections.addAll(colour, new Color[] { Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW });
		this.fireworkTimer.put(hashMapID, Long.valueOf(System.currentTimeMillis()));
		Firework firework = (Firework)player.getWorld().spawn(location, Firework.class);
		FireworkMeta data = firework.getFireworkMeta();
		data.addEffects(new FireworkEffect[] { FireworkEffect.builder().withColor((Color)colour.get(new Random().nextInt(17))).withColor((Color)colour.get(new Random().nextInt(17))).withColor((Color)colour.get(new Random().nextInt(17))).with((FireworkEffect.Type)type.get(new Random().nextInt(5))).trail(new Random().nextBoolean()).flicker(new Random().nextBoolean()).build() });
		data.setPower(new Random().nextInt(2) + 2);
		firework.setFireworkMeta(data);
		if (System.currentTimeMillis() - ((Long)this.fireworkTimer.get(hashMapID)).longValue() > 10000L) {
			this.fireworkTimer.remove(hashMapID);
		}
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() 
	{
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.ARROW_DAMAGE, 5);
		}};
	}

	@SuppressWarnings("serial")
	private static List<String> lore() 
	{
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Firework I");
			this.add(ChatColor.DARK_GRAY + "Shoot fireworks!");
		}};
	}
}

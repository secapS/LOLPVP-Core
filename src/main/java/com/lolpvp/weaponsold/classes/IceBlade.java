package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.lolpvp.core.Core;
import com.lolpvp.utils.Cooldowns;
import com.lolpvp.utils.ParticleEffect;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class IceBlade extends BallerItem 
{
    private HashSet<String> r;
	public static HashSet<String> froze;
	
	Core plugin;
	public IceBlade(Core instance)
	{
		super(ChatColor.AQUA + "Iceblade", Material.DIAMOND_SWORD, 10000000, lore(), enchantments(), "iceblade", "ice", "ib");
		// TODO Auto-generated constructor stub
		this.plugin = instance;
		r = new HashSet<String>();
		froze = new HashSet<String>();
	}
	
	@EventHandler
	public void onVelocityChange(PlayerVelocityEvent event)
	{
		if(froze.contains(event.getPlayer().getName()))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		if(BarAPI.hasBar(player) 
				&& BarAPI.getMessage(player).equals("Ender Dragon"))
		{
			BarAPI.removeBar(event.getPlayer());
		}
		if ((froze.contains(event.getPlayer().getName())) && 
				(event.getFrom().getY() < event.getTo().getY())) {
			event.setTo(new Location(player.getWorld(), event.getFrom().getX(), event.getFrom().getY(), event.getFrom().getZ(), event.getTo().getYaw(), event.getTo().getPitch()));
		}
	}
	
	@EventHandler
	public void onDmg(EntityDamageEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			if(froze.contains(player.getName()))
			{
				if(event.getCause().equals(DamageCause.FALL) || event.getCause().equals(DamageCause.CONTACT))
				{
					event.setDamage(0.0);
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event)
	{
		event.getPlayer().setWalkSpeed(0.2F);
	}
	
	@EventHandler
	public void onThis(PlayerToggleSneakEvent event)
	{
		final Player player = event.getPlayer();
		if ((ItemManager.getInstance().isBallerItem(player.getItemInHand(), this)) && 
				(!this.r.contains(player.getName()))) {
			new BukkitRunnable()
			{
				Integer lol = Integer.valueOf(0);

				public void run()
				{
					if (player.isSneaking())
					{
						if (this.lol.intValue() <= 10)
						{
							if ((Cooldowns.tryCooldown(player, "ice", 5 * 1000))  || (IceBlade.this.r.contains(player.getName())))
							{
								//add
								IceBlade.this.r.add(player.getName());
								float f = this.lol.floatValue() * 10.0F;
								if(BarAPI.hasBar(player))
									BarAPI.removeBar(player);
								BarAPI.setMessage(player, ChatColor.AQUA + "" + ChatColor.BOLD + "Freeze Radius: " + Float.toString(this.lol.floatValue()), f);	
								this.lol = Integer.valueOf(this.lol.intValue() + 1);
								player.sendMessage("Time: " + lol.intValue());
							}
							else
							{
								//remove
								IceBlade.this.r.remove(player.getName());
								Long lol = Long.valueOf(Cooldowns.getCooldown(player, "ice"));
								int bbb = lol.intValue() / 1000;
								player.sendMessage(IceBlade.this.getName() + ChatColor.RED + " is on cooldown for " + Integer.toString(bbb));
								cancel();	
							}
						}
						else
						{
							//remove
							player.sendMessage("charged");
							IceBlade.this.activate(player, this.lol.intValue());
							cancel();
						}
					}
					else
					{
						if (this.lol.intValue() > 0) 
						{
							//remove
							player.sendMessage("charged");
							IceBlade.this.activate(player, this.lol.intValue());
						}
						cancel();
					}
				}
			}.runTaskTimer(this.plugin, 0L, 20L);
		}
	}
	
	public void activate(final Player player, int radius)
	{
		boolean b = false;
		this.r.remove(player.getName());
		BarAPI.removeBar(player);
		int bb = 0;
		StringBuilder playersAffected = new StringBuilder();
		for (Entity p : player.getNearbyEntities(radius, radius, radius)) 
		{
			if ((p instanceof Player))
			{
				final Player oo = (Player)p;
				bb++;
				if(Core.isPlayerInPVP(player) && !InvisRing.in.contains(player.getName()))
				{
					if (!froze.contains(oo.getName()))
					{
						b = true;
						froze.add(oo.getName());
						player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 10.0F, 10.0F);
						ParticleEffect.CLOUD.display(player.getLocation(), 1.0F, 1.0F, 1.0F, 1.0F, 30);
						int ii = 3;
						oo.setWalkSpeed(1.0E-004F);
						BarAPI.setMessage(oo, ChatColor.AQUA + "" + ChatColor.BOLD + "You have been frozen by " + player.getName(), ii);
						oo.sendMessage(ChatColor.GRAY + "You have been frozen by " + ChatColor.AQUA + player.getName());
						playersAffected.append(ChatColor.AQUA + oo.getName() + ChatColor.GRAY + ", ");
						ParticleEffect.CLOUD.display(oo.getLocation(), 1.0F, 1.0F, 1.0F, 1.0F, 40);
						ParticleEffect.CLOUD.display(oo.getLocation(), 1.0F, 1.0F, 1.0F, 1.0F, 40);
						oo.getWorld().playEffect(oo.getLocation(), Effect.STEP_SOUND, 79);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
						{
							public void run()
							{
								froze.remove(oo.getName());
								BarAPI.removeBar(oo);
								oo.setWalkSpeed(0.2F);
							}
						}, ii * 20L);
					}
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You cannot use this weapon here.");
					this.r.remove(player.getName());
					break;
				}
			}
		}
		if(playersAffected.length() > 0)
		{
			player.sendMessage(ChatColor.GRAY + "You have frozen: " + playersAffected.toString());
		}
		if ((bb == 0) && (!b)) {
			player.sendMessage(ChatColor.RED + "You did not affect anyone.");
		}
	}

	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Freeze I");
			this.add(ChatColor.DARK_GRAY + "Hold shift to freeze enemies!");
		}};
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DAMAGE_ALL, 10);
			this.put(Enchantment.DAMAGE_UNDEAD, 10);
			this.put(Enchantment.DAMAGE_ARTHROPODS, 5);
			this.put(Enchantment.LOOT_BONUS_MOBS, 5);
		}};
	}
}
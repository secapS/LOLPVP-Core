package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.lolpvp.core.Core;
import com.lolpvp.utils.Cooldowns;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.BallerItemTask;
import com.lolpvp.weaponsold.ItemManager;

public class AbominableSnowman extends BallerItem
{
    private HashSet<String> r;
	private HashSet<String> froze;
	
	Core plugin;
	public AbominableSnowman(Core core)
	{
		super(ChatColor.AQUA + "Abominable Snowman", Material.DIAMOND_SWORD, 6000000, lore(), enchantments(), "snowman", "abominablesnowman");
		r = new HashSet<String>();
		froze = new HashSet<String>();
		this.plugin = core;
		// TODO Auto-generated constructor stub
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
							if ((Cooldowns.tryCooldown(player, "snow", 5 * 1000)) || (AbominableSnowman.this.r.contains(player.getName())))
							{
								AbominableSnowman.this.r.add(player.getName());
								float f = this.lol.floatValue() * 10.0F;
								if(BarAPI.hasBar(player))
									BarAPI.removeBar(player);
								BarAPI.setMessage(player, ChatColor.AQUA + "" + ChatColor.BOLD + "Blind Radius " + Float.toString(this.lol.floatValue()), f);
								this.lol = Integer.valueOf(this.lol.intValue() + 1);
							}
							else
							{
								AbominableSnowman.this.r.remove(player.getName());
								Long lol = Long.valueOf(Cooldowns.getCooldown(player, "snow"));
								int bbb = lol.intValue() / 1000;
								player.sendMessage(ChatColor.AQUA + "Abominable Snowman" + ChatColor.RED + " is on cooldown for " + Integer.toString(bbb));
								cancel();	
							}
						}
						else
						{
							AbominableSnowman.this.activate(player, this.lol.intValue());
							cancel();
						}
					}
					else
					{
						if (this.lol.intValue() > 0) 
						{
							AbominableSnowman.this.activate(player, this.lol.intValue());
						}
						cancel();
					}
				}
			}.runTaskTimer(plugin, 0L, 20L);
		}
	}
	
	HashMap<String, BukkitTask> blinded = new HashMap<>();
	
	public void activate(final Player player, int radius)
	{
		boolean b = false;
		int bb = 0;
		this.r.remove(player.getName());
		BarAPI.removeBar(player);
		StringBuilder playersAffected = new StringBuilder();
		List<Entity> entities = player.getNearbyEntities(radius, radius, radius);
		ArrayList<Player> players = new ArrayList<Player>();
		for (Entity p : entities) 
		{
			if ((p instanceof Player))
			{
				bb++;
				final Player oo = (Player)p;
				players.add(oo);
				if(Core.isPlayerInPVP(player) && !InvisRing.in.contains(player.getName()))
				{
					if (!this.froze.contains(oo.getName()))
					{
						blinded.put(oo.getName(), new BallerItemTask(players, this).runTaskTimer(plugin, 0L, 5L));
						b = true;
						this.froze.add(oo.getName());
						oo.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, radius * 20, 0));
						int ii = 3;
						BarAPI.setMessage(oo, ChatColor.AQUA + "" + ChatColor.BOLD + "You have been blinded by " + player.getName(), ii);
						
						if(oo != player)
						{
							playersAffected.append(ChatColor.AQUA + oo.getName());
							playersAffected.append(ChatColor.GRAY + ", ");
						}
						
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
						{
							public void run()
							{
								AbominableSnowman.this.froze.remove(oo.getName());
								blinded.get(oo.getName()).cancel();
								blinded.remove(oo.getName());
								BarAPI.removeBar(oo);
							}
						}, ii * 20L);
					}	
				}
			}
		}
		if(playersAffected.length() > 0)
		{
			player.sendMessage(ChatColor.GRAY + "You have blinded: " + playersAffected.toString());
		}
		if ((bb == 0) && (!b)) {
			player.sendMessage(ChatColor.RED + "You did not affect anyone.");
		}
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() {
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DAMAGE_ALL, 10);
			this.put(Enchantment.DAMAGE_UNDEAD, 10);
			this.put(Enchantment.LOOT_BONUS_MOBS, 5);
		}};
	}

	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Snowstorm I");
			this.add(ChatColor.DARK_GRAY + "This sword was found buried deep in a cavern in Antarctica. Shift to charge");
		}};
	}

}

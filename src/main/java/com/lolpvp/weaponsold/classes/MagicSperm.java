package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.lolpvp.core.Core;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class MagicSperm extends BallerItem
{

	Core plugin;
	public final HashMap<String, Integer> in = new HashMap<>();
	
	public MagicSperm(Core instance) 
	{
		super(ChatColor.AQUA + "Magic Sperm", Material.DIAMOND_SWORD, 10000000, lore(), enchantments(), "magicsperm", "ms");
		this.plugin = instance;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		if ((event.getEntity() instanceof Player))
		{
			Player player = (Player)event.getEntity();
			if ((event.getDamager() instanceof Player))
			{
				final Player target = (Player)event.getDamager();
				if (ItemManager.getInstance().isBallerItem(target.getItemInHand(), this))
				{
					int i = 2;
					if (!this.in.containsKey(target.getName()))
					{
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							if (p.getName().equals(player.getName())) {
								p.hidePlayer(target);
							}
						}
						BarAPI.setMessage(target, ChatColor.AQUA + "" + ChatColor.BOLD + "You are invisible.", i);
						this.in.put(target.getName(), Integer.valueOf(i + 1));
						new BukkitRunnable()
						{
							public void run()
							{
								if (MagicSperm.this.in.containsKey(target.getName()))
								{
									MagicSperm.this.in.put(target.getName(), Integer.valueOf(((Integer)MagicSperm.this.in.get(target.getName())).intValue() - 1));
									if (((Integer)MagicSperm.this.in.get(target.getName())).intValue() <= 0)
									{
										MagicSperm.this.in.remove(target.getName());
										for (Player p : Bukkit.getServer().getOnlinePlayers()) {
											p.showPlayer(target);
										}
										cancel();
									}
								}
								else
								{
									cancel();
								}
							}
						}.runTaskTimer(this.plugin, 0L, 20L);
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
			this.add(ChatColor.GRAY + "Invisibility I");
			this.add(ChatColor.DARK_GRAY + "Hit enemies to become invisible!");
		}};
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DAMAGE_ALL, 10);
			this.put(Enchantment.DAMAGE_UNDEAD, 10);
			this.put(Enchantment.DURABILITY, 10);
		}};
	}
	
}

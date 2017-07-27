package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.earth2me.essentials.Essentials;
import com.lolpvp.core.Core;
import com.lolpvp.utils.Cooldowns;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class ThorsHammer extends BallerItem
{
	Core plugin;
	
	public ThorsHammer(Core instance) 
	{
		super(ChatColor.AQUA + "Mjolnir", Material.STONE_AXE, 1, lore(), enchantments(), "thorshammer", "mjolnir");
		this.plugin = instance;
		// TODO Auto-generated constructor stub
	}
	
	Set<String> strikeTime = new HashSet<>();
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event)
	{
		Player player = event.getPlayer();
		if(event.getRightClicked() instanceof LivingEntity)
		{
			final LivingEntity rightClicked = (LivingEntity)event.getRightClicked();
			if(ItemManager.getInstance().isBallerItem(player.getItemInHand(), this))
			{
				if((Core.isPlayerInPVP(player) && Core.isEntityInPVP(rightClicked)) && !InvisRing.in.contains(player.getName()))
				{
					if(Cooldowns.tryCooldown(player, "thor", 7 * 1000))
					{
						if(rightClicked instanceof Player)
						{
							player.getWorld().strikeLightningEffect(rightClicked.getLocation());
							if(!(Essentials.getPlugin(Essentials.class).getUser((Player)rightClicked).isGodModeEnabled()))
							{
								if(!strikeTime.contains(((Player)rightClicked).getName()))
								{
									strikeTime.add(((Player)rightClicked).getName());
									new BukkitRunnable()
									{
										@Override
										public void run()
										{
											strikeTime.remove(((Player)rightClicked).getName());
										}
									}.runTaskLater(plugin, 7 * 20);
									Damageable entity2 = (Damageable)rightClicked;
									rightClicked.playEffect(EntityEffect.HURT);
									rightClicked.setHealth(entity2.getHealth() > 4 ? entity2.getHealth() - 4.0 : 0);		
								}	
							}
						}
						else
						{
							player.getWorld().strikeLightningEffect(rightClicked.getLocation());
							Damageable entity2 = (Damageable)rightClicked;
							rightClicked.setHealth(entity2.getHealth() > 4 ? entity2.getHealth() - 4.0 : 0);		
						}
					}
					else
					{
						Long lol = Long.valueOf(Cooldowns.getCooldown(event.getPlayer(), "thor"));
						int bbb = lol.intValue() / 1000;
						player.sendMessage(this.getName() + ChatColor.RED + " is on cooldown for " + Integer.toString(bbb) + " seconds.");
					}	
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You cannot use this ability in No-PVP areas.");
				}
			}
		}
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() 
	{
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DAMAGE_ALL, 10);
			this.put(Enchantment.DURABILITY, 10);
		}};
	}

	@SuppressWarnings("serial")
	private static List<String> lore() {
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Lightning I");
			this.add(ChatColor.DARK_GRAY + "Thor's  Hammer: Right click to strike!");
			this.add(ChatColor.DARK_GRAY + "Avengers: Age of Ultron Collectible Item.");
		}};
	}
}

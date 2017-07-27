package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.earth2me.essentials.Essentials;
import com.lolpvp.core.Core;
import com.lolpvp.utils.Cooldowns;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;
import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class CaptainAmerica extends BallerItem
{
	Core plugin;
	public CaptainAmerica(Core instance) 
	{
		super(ChatColor.AQUA + "Captain America's Vibranium Sword", Material.STONE_SWORD, 1, lore(), enchantments(), "vibraniumsword", "captainamerica");
		// TODO Auto-generated constructor stub
		this.plugin = instance;
	}
	
	List<String> hitTime = new ArrayList<>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			if(ItemManager.getInstance().isBallerItem(player.getItemInHand(), this))
			{
				if(!InvisRing.in.contains(player.getName()))
				{
					if(Cooldowns.tryCooldown(player, "captainamerica", 10 * 1000))
					{
						event.setCancelled(true);
						new ItemProjectile("vibraniumsword", player, ItemManager.getInstance().getItemStack(this).clone(), 0.6F);
						player.setItemInHand(null);
						player.updateInventory();
					}
					else
					{
						Long lol = Long.valueOf(Cooldowns.getCooldown(event.getPlayer(), "captainamerica"));
						int bbb = lol.intValue() / 1000;
						player.sendMessage(this.getName() + ChatColor.RED + " is on cooldown for " + Integer.toString(bbb) + " seconds.");
					}	
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You cannot use this while invis!");
				}
			}
		}
	}
	
	private Map<UUID, Integer> bounces = new HashMap<>();
	@EventHandler
	public void onHit(CustomProjectileHitEvent event)
	{
		if(event.getProjectile().getProjectileName().equalsIgnoreCase("vibraniumsword"))
		{
			Player player = (Player)event.getProjectile().getShooter();
			if(event.getHitType().equals(CustomProjectileHitEvent.HitType.ENTITY))
			{
				if(event.getProjectile().getShooter() != event.getHitEntity() && event.getHitEntity() != null)
				{
					Vector to = new Vector(event.getProjectile().getShooter().getLocation().getX(), event.getProjectile().getShooter().getLocation().getY(), event.getProjectile().getShooter().getLocation().getZ());
					Vector from = new Vector(event.getHitEntity().getLocation().getX(), event.getHitEntity().getLocation().getY(), event.getHitEntity().getLocation().getZ());
					event.setCancelled(true);
					event.getProjectile().getEntity().setVelocity(to.subtract(from).multiply(to.distance(from) > 1.0 ? to.distance(from) * 0.02 : 0.02).add(new Vector(0, 0.295, 0)).normalize());
					event.getHitEntity().getWorld().playSound(event.getHitEntity().getLocation(), Sound.ANVIL_LAND, 0.5F, -0.3F);
					if(event.getHitEntity() instanceof Player)
					{
						final Player hit = (Player)event.getHitEntity();
						if(Core.isPlayerInPVP(hit) && Core.isPlayerInPVP(player))
						{
							if(!Essentials.getPlugin(Essentials.class).getUser(hit).isGodModeEnabled())
							{
								Random r = new Random();
								double randomValue = .5 + (.75 - .5) * r.nextDouble();
								if(!hitTime.contains(hit.getName()) && !InvisRing.in.contains(hit.getName()))
								{
									hitTime.add(hit.getName());
									new BukkitRunnable()
									{
										@Override
										public void run()
										{
											hitTime.remove(hit.getName());
										}
									}.runTaskLater(plugin, 5 * 20);
									
									hit.sendMessage(ChatColor.AQUA + "You've been hit with a Vibranium sword! Vibranium breaks down armor extremely fast, so remember to /repair!");
									if(hit.getInventory().getHelmet() != null)
									{
										hit.getInventory().getHelmet().setDurability((short) (hit.getInventory().getHelmet().getDurability() + (hit.getInventory().getHelmet().getType().getMaxDurability() * randomValue)));

										if(hit.getInventory().getHelmet().getDurability() > hit.getInventory().getHelmet().getType().getMaxDurability())
										{
											hit.getInventory().setHelmet(null);
											hit.playSound(hit.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
										}
									}
									
									if(hit.getInventory().getChestplate() != null)
									{
										hit.getInventory().getChestplate().setDurability((short) (hit.getInventory().getChestplate().getDurability() + (hit.getInventory().getChestplate().getType().getMaxDurability() * randomValue)));

										if(hit.getInventory().getChestplate().getDurability() > hit.getInventory().getChestplate().getType().getMaxDurability())
										{
											hit.getInventory().setChestplate(null);
											hit.playSound(hit.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
										}
									}
									
									if(hit.getInventory().getLeggings() != null)
									{
										hit.getInventory().getLeggings().setDurability((short) (hit.getInventory().getLeggings().getDurability() + (hit.getInventory().getLeggings().getType().getMaxDurability() * randomValue)));

										if(hit.getInventory().getLeggings().getDurability() > hit.getInventory().getLeggings().getType().getMaxDurability())
										{
											hit.getInventory().setLeggings(null);
											hit.playSound(hit.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
										}
									}
									
									if(hit.getInventory().getBoots() != null)
									{
										hit.getInventory().getBoots().setDurability((short) (hit.getInventory().getBoots().getDurability() + (hit.getInventory().getBoots().getType().getMaxDurability() * randomValue)));
										if(hit.getInventory().getBoots().getDurability() > hit.getInventory().getBoots().getType().getMaxDurability())
										{
											hit.getInventory().setBoots(null);
											hit.playSound(hit.getLocation(), Sound.ITEM_BREAK, 1F, 1F);	
										}
									}	
									hit.damage(2.0);
								}	
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED + "You cannot use this ability in No-PVP areas.");
						}
					}
				}
				else
				{
					player.getWorld().playSound(player.getLocation(), Sound.ANVIL_LAND, 0.5F, -0.3F);
					ItemStack item = ItemManager.getInstance().getItemStack(this);
					player.getInventory().addItem(item);
				}
			}
			
			if(event.getHitType().equals(CustomProjectileHitEvent.HitType.BLOCK))
			{
				
				Vector to = new Vector(event.getProjectile().getShooter().getLocation().getX(), event.getProjectile().getShooter().getLocation().getY(), event.getProjectile().getShooter().getLocation().getZ());
				Vector from = new Vector(event.getHitBlock().getLocation().getX(), event.getHitBlock().getLocation().getY(), event.getHitBlock().getLocation().getZ());
				event.setCancelled(true);
				event.getHitBlock().getWorld().playSound(event.getHitBlock().getLocation(), Sound.ANVIL_LAND, 0.5F, -0.3F);
				event.getProjectile().getEntity().setVelocity(to.subtract(from).multiply(to.distance(from) > 1.0 ? to.distance(from) * 0.02 : 0.02).add(new Vector(0, 0.295, 0)).normalize());
				
				if(!bounces.containsKey(event.getProjectile().getEntity().getUniqueId()))
				{
					bounces.put(event.getProjectile().getEntity().getUniqueId(), 1);
				}
				else
				{
					bounces.put(event.getProjectile().getEntity().getUniqueId(), bounces.get(event.getProjectile().getEntity().getUniqueId()).intValue() + 1);
				}
				
				if(bounces.get(event.getProjectile().getEntity().getUniqueId()).equals(Integer.valueOf(5)))
				{
					bounces.remove(event.getProjectile().getEntity().getUniqueId());
					event.setCancelled(false);
					player.getInventory().addItem(ItemManager.getInstance().getItemStack(this));
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
			this.add(ChatColor.GRAY + "Boomerang I");
			this.add(ChatColor.DARK_GRAY + "Avengers: Age of Ultron Collectible Item.");
		}};
	}
}
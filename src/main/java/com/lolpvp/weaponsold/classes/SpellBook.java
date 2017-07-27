package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.earth2me.essentials.Essentials;
import com.lolpvp.core.Core;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;
import com.stirante.MoreProjectiles.Particles;
import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent.HitType;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class SpellBook extends BallerItem 
{
	Core plugin;
	public SpellBook(Core core)
	{
		super(ChatColor.AQUA + "Spell Book", Material.BOOK, 1, lore(), enchantments(), "spellbook");
		plugin = core;
		// TODO Auto-generated constructor stub
	}
	
	ArrayList<String> lore = new ArrayList<String>();

	HashMap<Player, Integer> hashmap = new HashMap<Player, Integer>();

	public void replenishMana(Player p, ItemStack i) 
	{
		if (ItemManager.getInstance().isBallerItemName(i, this)) 
		{
			final ItemMeta meta = i.getItemMeta();
			String total = meta.getLore().get(0).replace(meta.getLore().get(0), ChatColor.GRAY + "Evil Spells: 5");
			lore.clear();
			lore.add(total);
			lore.add(ChatColor.DARK_GRAY + "A magic spellbook!");
			meta.setLore(lore);
			i.setItemMeta(meta);
			p.updateInventory();
		}
	}
	
	public void useMana(Player p, ItemStack i) 
	{
		ItemMeta meta = i.getItemMeta();
		String number = meta.getLore().get(0).replace("Evil Spells: ", "");
		String total = meta.getLore().get(0).replace(meta.getLore().get(0), ChatColor.GRAY + "Evil Spells: " + String.valueOf(Integer.parseInt(ChatColor.stripColor(number)) - 1));
		lore.clear();
		lore.add(total);
		lore.add(ChatColor.DARK_GRAY + "A magic spellbook!");
		meta.setLore(lore);
		i.setItemMeta(meta);
		p.updateInventory();
	}
	
	public void throwSpellBook(Player p) 
	{
		final ItemProjectile item = new ItemProjectile("SpellBook", p, p.getItemInHand(), 1);
		new BukkitRunnable()
		{
			public void run()
			{
				if (!item.getEntity().isDead() && !item.getEntity().isOnGround()) 
				{
					Particles.CRIT.display(item.getEntity().getLocation(), 0.0F, 0.0F, 0.0F, 0.0F, 1);
				} else {
					cancel();
					return;
				}
			}
		}.runTaskTimer(plugin, 0L, 1L);
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) 
	{
		final Player p = e.getPlayer();
		if (ItemManager.getInstance().isBallerItemName(p.getItemInHand(), this)) 
		{
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) 
			{
				if (!hashmap.containsKey(p)) 
				{
					hashmap.put(p, 5);
				}
				if (hashmap.get(p) != 0) 
				{
					throwSpellBook(p);
					useMana(p, p.getItemInHand());
					hashmap.put(p, hashmap.get(p) - 1);
					if (hashmap.get(p) == 0) 
					{
						p.sendMessage(ChatColor.RED + "You are out of mana! Please keep this item in your inventory to regenerate.");
						new BukkitRunnable()
						{
							Integer seconds = 0;

							public void run()
							{
								if(seconds.intValue() < 5)
								{
									seconds = Integer.valueOf(seconds.intValue() + 1);
								} else {
									for (ItemStack it : p.getInventory().getContents()) 
									{
										replenishMana(p, it);
									}
									p.sendMessage(ChatColor.GREEN + "Your mana has been replenished!");
									hashmap.remove(p);
									cancel();
								}
							}
						}.runTaskTimer(plugin, 0L, 80L);
					}
				} else {
					p.sendMessage(ChatColor.RED + "You are out of mana! Please keep this item in your inventory to regenerate.");
					return;
				}
			}
		}
	}

	@EventHandler
	public void onProjectileHit(CustomProjectileHitEvent e) 
	{
		if (e.getProjectile().getProjectileName().equals("SpellBook")) 
		{
			if (e.getHitType().equals(HitType.ENTITY)) 
			{
				if (e.getHitEntity().equals(e.getProjectile().getShooter())) 
				{
					e.setCancelled(true);
					return;
				}
				Damageable entity = e.getHitEntity();
				
				if(e.getHitEntity() instanceof Player)
				{
					Player player = (Player)e.getHitEntity();
					
					if(Core.isPlayerInPVP(player))
					{
						if(!IceBlade.froze.contains(player.getName()))
						{
							if(!Essentials.getPlugin(Essentials.class).getUser(player).isGodModeEnabled())
							{
								entity.setHealth(entity.getHealth() > 2 ? entity.getHealth() - 2 : 0);
								entity.playEffect(EntityEffect.HURT);
								entity.getWorld().playSound(entity.getLocation(), Sound.HURT_FLESH, 1f, 1f);	
							}
						}
						else
						{
							((Player)e.getProjectile().getShooter()).sendMessage(ChatColor.RED + player.getName() + " is already under a Freeze Spell! You cannot use 2 spells on the same player!");
						}	
					}
				}
				else
				{
					entity.setHealth(entity.getHealth() > 2 ? entity.getHealth() - 2 : 0);
					entity.playEffect(EntityEffect.HURT);
					entity.getWorld().playSound(entity.getLocation(), Sound.HURT_FLESH, 1f, 1f);
				}
			}
		}
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore() 
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Evil Spells: 5");
			this.add(ChatColor.DARK_GRAY + "A magic spellbook!");
		}};
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.LOOT_BONUS_MOBS, 1);
		}};
	}
}

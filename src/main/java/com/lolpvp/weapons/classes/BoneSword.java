package com.lolpvp.weapons.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.lolpvp.core.Core;
import com.lolpvp.utils.Cooldowns;
import com.lolpvp.weapons.BallerItem;
import com.lolpvp.weapons.ItemManager;

public class BoneSword extends BallerItem
{
	Core plugin;
	public BoneSword(Core core) 
	{
		super(ChatColor.AQUA + "Bone Sword", Material.DIAMOND_SWORD, 1, lore(), enchantments(), "bonesword");
		// TODO Auto-generated constructor stub
		this.plugin = core;
	}
	
	ArrayList<UUID> horselist = new ArrayList<UUID>();
	ArrayList<Item> bones = new ArrayList<>();
	@EventHandler
	public void OnPlayerHit(EntityDamageByEntityEvent e) 
	{
		if (e.getDamager() instanceof Player) 
		{
			if (e.getEntity() instanceof LivingEntity) 
			{
				Player player = (Player)e.getDamager();
				LivingEntity entity = (LivingEntity) e.getEntity();
				if(ItemManager.getInstance().isBallerItem(player.getItemInHand(), this))
				{
					for(int i = 0; i < 10; i++)
					{
						bones.add(entity.getWorld().dropItemNaturally(entity.getLocation().add(0, new Random().nextDouble(), 0), getBone()));
					}
					this.plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable()
					{
						@Override
						public void run()
						{
							for(Item items : bones)
							{
								items.remove();
							}
							bones.clear();
						}
					}, 30L);
					entity.getWorld().playSound(entity.getLocation(), Sound.FALL_BIG, 2f, 1f);	
				}
			}
		}
	}
	
	public ItemStack getBone()
	{
		ItemStack bone = new ItemStack(Material.BONE);
		ItemMeta meta = bone.getItemMeta();
		meta.setDisplayName("Bones");
		bone.setItemMeta(meta);
		return bone;
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event)
	{
		if(bones.contains(event.getItem()))
		{
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void OnPlayerRightclick(PlayerInteractEvent e) 
	{
		final Player p = (Player) e.getPlayer();
		World world = p.getWorld();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) 
		{
			if(ItemManager.getInstance().isBallerItem(p.getItemInHand(), this))
			{
				if(Cooldowns.tryCooldown(p, "bone", 15 * 1000)) 
				{
					final Horse h = (Horse) world.spawnEntity(p.getLocation(), EntityType.HORSE);
					h.setAdult();
					h.setVariant(Variant.SKELETON_HORSE);
					h.setOwner(p);
					h.setTamed(true);
					h.getInventory().addItem(new ItemStack(Material.SADDLE));
					h.setCustomName(ChatColor.WHITE + p.getName());
					h.setCustomNameVisible(true);
					h.getInventory().setArmor(new ItemStack(Material.DIAMOND_BARDING));
					h.setMaxHealth(300.0);
					h.setHealth(300.0);
					h.setRemoveWhenFarAway(false);
					horselist.add(h.getUniqueId());
					this.plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable()
					{
						@Override
						public void run()
						{
							h.setHealth(0.0);
							h.remove();
							horselist.remove(h.getUniqueId());
							p.sendMessage(ChatColor.RED + "Your horse has despawned.");
						}
					}, 180 * 20L);
				}
				else 
				{
					Long lol = Long.valueOf(Cooldowns.getCooldown(p, "bone"));
					int bbb = lol.intValue() / 1000;
					p.sendMessage(this.getName() + ChatColor.RED + " is on cooldown for " + Integer.toString(bbb) + " seconds.");
				}	
			}
		}
	}
	@EventHandler
	public void onHorseDeath(EntityDeathEvent e) 
	{
		if (e.getEntityType().equals(EntityType.HORSE)) 
		{
			if(horselist.contains(e.getEntity().getUniqueId())) 
			{
				e.getDrops().clear();
				horselist.remove(e.getEntity().getUniqueId());
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
			this.put(Enchantment.DAMAGE_UNDEAD, 10);
			this.put(Enchantment.LOOT_BONUS_MOBS, 5);
			this.put(Enchantment.DAMAGE_ARTHROPODS, 5);
		}};
	}

	@SuppressWarnings("serial")
	private static List<String> lore() 
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Summon Horse I");
			this.add(ChatColor.DARK_GRAY + "The sword of the dead!");
		}};
	}

}

package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.confuser.barapi.BarAPI;
import net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.earth2me.essentials.Essentials;
import com.lolpvp.core.Core;
import com.lolpvp.utils.Cooldowns;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class PianoKey extends BallerItem
{
	Core plugin;
	public PianoKey(Core instance) 
	{
		super(ChatColor.AQUA + "Piano Key", Material.DIAMOND_SWORD, 1, lore(), enchantments(), "pianokey");
		this.plugin = instance;
		// TODO Auto-generated constructor stub
	}
	
	ArrayList<Inventory> inventory = new ArrayList<Inventory>();
	ArrayList<String> lore = new ArrayList<String>();
	
	public ArrayList<Location> getCircle(Location center, double radius, int amount)
	{
        World world = center.getWorld();
        double increment = (2*Math.PI)/amount;
        ArrayList<Location> locations = new ArrayList<Location>();
        for(int i = 0;i < amount; i++){
        double angle = i*increment;
        double x = center.getX() + (radius * Math.cos(angle));
        double z = center.getZ() + (radius * Math.sin(angle));
        locations.add(new Location(world, x, center.getY() + 1, z));
        }
        return locations;
    }
	
    public static void playRecord(Player p, Location loc, Integer record)
    {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldEvent(1005, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), record, false));
    }
   
    public static void stopRecord(Player p, Location loc)
    {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldEvent(1005, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 0, false));
    }
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) 
	{
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) 
		{
			Player p = (Player) e.getDamager();
			Player player = (Player) e.getEntity();
			
			if (ItemManager.getInstance().isBallerItemName(p.getItemInHand(), this)) 
			{
				player.getWorld().playEffect(player.getLocation().add(new Vector(0,1,0)), Effect.NOTE, 2);
				player.getWorld().playEffect(player.getLocation().add(new Vector(0,1,1)), Effect.NOTE, 2);
				player.getWorld().playEffect(player.getLocation().add(new Vector(1,1,0)), Effect.NOTE, 2);
			}
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDeath(PlayerDeathEvent e) 
	{
		Player p = e.getEntity();
		if (p.getKiller() instanceof Player) 
		{
			Player player = p.getKiller();
			if (ItemManager.getInstance().isBallerItemName(player.getItemInHand(), this)) 
			{
				if (player.getItemInHand().getItemMeta().getLore().get(3) != null) 
				{
					String id = player.getItemInHand().getItemMeta().getLore().get(3).replace(ChatColor.DARK_GRAY + "Selected Song: ", "");
					for (final Player other: Bukkit.getOnlinePlayers()) 
					{
						if (other != null && other != p) 
						{
							final Location loc = other.getLocation().clone();
							playRecord(other, loc, Integer.parseInt(ChatColor.stripColor(id)));
							plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() 
							{
								public void run() 
								{
									stopRecord(other, loc);
								}
							}, 100L);
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void OnRightClick(PlayerInteractEvent e) 
	{
		Player p = e.getPlayer();
		if (ItemManager.getInstance().isBallerItemName(p.getItemInHand(), this)) 
		{
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) 
			{
				if (p.isSneaking()) 
				{
					Inventory inv = Bukkit.getServer().createInventory(p, 9, ChatColor.RED + "Piano Key Song Customizer");
					inv.addItem(new ItemStack(Material.RECORD_3));
					inv.addItem(new ItemStack(Material.RECORD_4));
					inv.addItem(new ItemStack(Material.RECORD_5));
					inv.addItem(new ItemStack(Material.RECORD_6));
					inv.addItem(new ItemStack(Material.RECORD_7));
					inv.addItem(new ItemStack(Material.RECORD_8));
					inv.addItem(new ItemStack(Material.RECORD_9));
					inv.addItem(new ItemStack(Material.RECORD_10));
					inv.addItem(new ItemStack(Material.RECORD_11));
					inv.addItem(new ItemStack(Material.RECORD_12));
					if (!inventory.contains(inv)) 
					{
						inventory.add(inv);
					}
					p.openInventory(inv);
				}
			}
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void OnRecordClick(InventoryClickEvent e) 
	{
		if (inventory.contains(e.getInventory())) 
		{
			if (e.getWhoClicked() instanceof Player) 
			{
				e.setCancelled(true);
				Player p = (Player) e.getWhoClicked();
				if (e.getCurrentItem().getType().equals(Material.RECORD_3) || e.getCurrentItem().getType().equals(Material.RECORD_4)
						|| e.getCurrentItem().getType().equals(Material.RECORD_5) || e.getCurrentItem().getType().equals(Material.RECORD_6) 
						|| e.getCurrentItem().getType().equals(Material.RECORD_7) || e.getCurrentItem().getType().equals(Material.RECORD_8) 
						|| e.getCurrentItem().getType().equals(Material.RECORD_9) || e.getCurrentItem().getType().equals(Material.RECORD_10) 
						|| e.getCurrentItem().getType().equals(Material.RECORD_11) || e.getCurrentItem().getType().equals(Material.RECORD_12)) 
				{
					ItemMeta meta = p.getItemInHand().getItemMeta();
					lore.clear();
					lore.addAll(meta.getLore());
					String string = lore.get(3).replace(lore.get(3), ChatColor.DARK_GRAY + "Selected Song: " + e.getCurrentItem().getTypeId());
					lore.remove(3);
					lore.add(string);
					meta.setLore(lore);
					p.getItemInHand().setItemMeta(meta);
				}
				p.updateInventory();
				inventory.remove(e.getInventory());
				p.closeInventory();
			}
		}
	}
	@EventHandler
	public void onShift(PlayerToggleSneakEvent e)
	{
		final Player p = e.getPlayer();
		final ItemStack i = p.getItemInHand();

		if (ItemManager.getInstance().isBallerItemName(i, this)) 
		{
			if(Cooldowns.tryCooldown(p, "pianokey", 3 * 1000))
			{
				new BukkitRunnable()
				{
					Integer seconds = 0;

					public void run()
					{
						if(p.isSneaking())
						{
							if(seconds.intValue() <= 10)
							{
								float bar = seconds.floatValue() * 10.0F;
								BarAPI.setMessage(p, ChatColor.AQUA + "" + ChatColor.BOLD + "Music Radius: " + seconds + ".0", bar);
								seconds = Integer.valueOf(seconds.intValue() + 1);
							} else {
								activate(p, seconds);
								cancel();
							}
						}
						else
						{
							if(seconds.intValue() > 1)
							{
								activate(p, seconds.intValue());
							} else {
								BarAPI.removeBar(p);
							}
							cancel();
						}
					}
				}.runTaskTimer(plugin, 0L, 20L);	
			}
			else
			{
				Long lol = Long.valueOf(Cooldowns.getCooldown(p, "pianokey"));
				int bbb = lol.intValue() / 1000;
				p.sendMessage(this.getName() + ChatColor.RED + " is on cooldown for " + Integer.toString(bbb) + " seconds.");
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void activate(final Player p, final Integer seconds)
	{
		final World world = p.getWorld();
		BarAPI.removeBar(p);
		if (Core.isPlayerInPVP(p) && !InvisRing.in.contains(p.getName())) 
		{
			for (Location loc : getCircle(p.getLocation(), seconds.doubleValue(), 10)) 
    		{
				world.playEffect(loc, Effect.NOTE, 50);
    		}
			List<Entity> victims = p.getNearbyEntities(seconds.doubleValue(), seconds.doubleValue(), seconds.doubleValue());
			for (final Entity victimz: victims) 
			{
				if (victimz instanceof Player) 
				{
					Damageable victim = (Damageable) victimz;
					if (Core.isPlayerInPVP((Player)victim)) 
					{
						new BukkitRunnable()
						{
							int i = 0;
							@Override
							public void run()
							{
								if(i < 15)
								{
									victimz.getWorld().playSound(victimz.getLocation(), Sound.NOTE_PIANO, 1f, 0.2f * (i + seconds));
									for(int j = 0; j < 2; j++)
										victimz.getWorld().playEffect(victimz.getLocation().add(new Vector(new Random().nextInt(2),1.5,new Random().nextInt(2))), Effect.NOTE, 2);
								}
								else
								{
									cancel();
								}
								i++;
							}
						}.runTaskTimer(plugin, 0L, 2L);
						
						if(!Essentials.getPlugin(Essentials.class).getUser((Player)victim).isGodModeEnabled())
						{
							if (victim.getHealth() > 4) 
							{
								victim.setHealth(victim.getHealth() - 4);
								victim.playEffect(EntityEffect.HURT);
							} else {
								victim.playEffect(EntityEffect.HURT);
								victim.setHealth(0);
							}	
						}
					}
				}
			}
		} else {
			p.sendMessage(ChatColor.RED + "You can only use this in PVP enabled areas!");
		}
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore() 
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Music I");
			this.add(ChatColor.DARK_GRAY + "Hold shift to play music.");
			this.add(ChatColor.DARK_GRAY + "Hold shift and right click air to select a song.");
			this.add(ChatColor.DARK_GRAY + "Selected Song: ");
		}};
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DAMAGE_ALL, 10);
			this.put(Enchantment.DAMAGE_UNDEAD, 10);
		}};
	}
}

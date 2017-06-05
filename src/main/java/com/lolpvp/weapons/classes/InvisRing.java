package com.lolpvp.weapons.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.lolpvp.core.Core;
import com.lolpvp.weapons.BallerItem;
import com.lolpvp.weapons.ItemManager;

public class InvisRing extends BallerItem implements CommandExecutor
{
	public final HashMap<String, Integer> warm = new HashMap<String, Integer>();
	public static final HashSet<String> in = new HashSet<String>();
	
	Core plugin;
	
	@SuppressWarnings("deprecation")
	public InvisRing(Core core) 
	{
		super(ChatColor.AQUA + "Invisibility Ring", Material.getMaterial(175), 1000000, lore(), enchantments(), "invisibilityring", "invisring", "ring");
		this.plugin = core;
		// TODO Auto-generated constructor stub
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event)
	{
		InvisRing.in.remove(event.getPlayer().getName());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		for (String s : InvisRing.in)
		{
			Player p = Bukkit.getServer().getPlayer(s);
			if (p != null)
			{
				player.hidePlayer(p);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		if ((event.getFrom().getBlockX() != event.getTo().getBlockX()) || (event.getFrom().getBlockZ() != event.getTo().getBlockX()))
		{
			Player player = event.getPlayer();
			boolean hasIt = false;
			if (InvisRing.in.contains(player.getName()))
			{
				for (ItemStack i : player.getInventory().getContents())
				{
					if (ItemManager.getInstance().isBallerItem(i, this))
					{
						hasIt = true;
						break;
					}
				}
				if (!hasIt)
				{
					for (Player o : Bukkit.getServer().getOnlinePlayers())
					{
						o.showPlayer(player);
					}
					InvisRing.in.remove(player.getName());
					BarAPI.removeBar(player);
					player.sendMessage(ChatColor.RED + "You are no longer invisible.");
				}
			}
		}
	}

	@EventHandler
	public void onPick(PlayerPickupItemEvent event)
	{
		if (InvisRing.in.contains(event.getPlayer().getName()))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onHit(EntityDamageByEntityEvent event)
	{
		if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Player)))
		{
			Player hitter = (Player)event.getDamager();
			if (InvisRing.in.contains(hitter.getName())) {
				event.setCancelled(true);
			}
			Player player = (Player)event.getEntity();
			if (this.warm.containsKey(player.getName()))
			{
				Bukkit.getServer().getScheduler().cancelTask(this.warm.get(player.getName()));
				this.warm.remove(player.getName());
				player.sendMessage(ChatColor.RED + "You cannot vanish now. You were hit.");
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(final PlayerInteractEvent event)
	{
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			final Player player = event.getPlayer();
			if(ItemManager.getInstance().isBallerItem(player.getItemInHand(), this))
			{
				event.setCancelled(true);
				if(!this.plugin.getConfig().getBoolean("invisring-disabled"))
				{
					if (InvisRing.in.contains(player.getName()))
					{
						for (Player o : Bukkit.getServer().getOnlinePlayers()) 
						{
							o.showPlayer(player);
						}
						InvisRing.in.remove(player.getName());
						BarAPI.removeBar(player);
						player.sendMessage(ChatColor.RED + "You are no longer invisible.");
					}
					else if(!this.warm.containsKey(player.getName()))
					{
						int warmup = 5;
						player.sendMessage(ChatColor.GRAY + "Vanishing in: " + ChatColor.AQUA + warmup + ChatColor.GRAY + " seconds.");
						this.warm.put(player.getName(), Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
						{
							@Override
							public void run() 
							{
								// TODO Auto-generated method stub
								BarAPI.setMessage(player, ChatColor.RED + "" + ChatColor.BOLD + "You are now invisible!");
								player.sendMessage(ChatColor.GREEN + "You are now invisible.");
								for(Player p : Bukkit.getOnlinePlayers())
								{
									p.hidePlayer(player);
								}
								InvisRing.in.add(player.getName());
								InvisRing.this.warm.remove(player.getName());
								
								new BukkitRunnable()
								{
									@Override
									public void run()
									{
										// TODO Auto-generated method stub
										if(InvisRing.in.contains(player.getName()))
										{
											BarAPI.setMessage(player, ChatColor.RED + "" + ChatColor.BOLD + "You are now invisible!");
										}
										else
										{
											cancel();
										}
									}	
								}.runTaskTimer(plugin, 0L, 400L);
							}
						}, warmup * 20L));
					}	
				}
				else
				{
					player.sendMessage(ChatColor.RED + "InvisRings are disabled.");
				}
			}
		}
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() {
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DURABILITY, 1);
		}};
	}

	@SuppressWarnings("serial")
	private static List<String> lore() {
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Invisibility I");
			this.add(ChatColor.DARK_GRAY + "Right click to activate");
		}};
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(command.getName().equalsIgnoreCase("invisring"))
		{
			if(sender.hasPermission("lolpvp.invisring-command"))
			{
				if(args.length == 0)
				{
					sender.sendMessage(ChatColor.RED + "/invisring <enable/disable>");
					return true;
				}
				else if(args.length <= 2)
				{
					if(args[0].equalsIgnoreCase("disable"))
					{
						this.plugin.getConfig().set("invisring-disabled", true);
						this.plugin.saveConfig();
						sender.sendMessage(ChatColor.GREEN + "InvisRing disabled.");
						
						for(String playerName : in)
						{
							Player player = Bukkit.getPlayer(playerName);
							for(Player players : Bukkit.getOnlinePlayers())
							{
								players.showPlayer(player);
							}
						}
						in.clear();
						return true;
					}
					else if(args[0].equalsIgnoreCase("enable"))
					{
						this.plugin.getConfig().set("invisring-disabled", false);
						this.plugin.saveConfig();
						sender.sendMessage(ChatColor.GREEN + "InvisRing enabled.");
						return true;
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "/invisring <enable/disable>");
						return true;
					}
				}
				return true;	
			}
		}
		return false;
	}
}

package com.lolpvp.weapons.classes;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import com.lolpvp.core.Core;
import com.lolpvp.weapons.BallerItem;
import com.lolpvp.weapons.ItemManager;
import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent.HitType;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;

public class MoneyBag extends BallerItem implements CommandExecutor
{
	Core plugin;
	public MoneyBag(Core instance)
	{
		super(ChatColor.AQUA + "Money Bag", Material.CHEST, 1, lore(), enchantments(), "moneybag");
		this.plugin = instance;
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "Right click for a random prize!");
		}};
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.ARROW_DAMAGE, 10);
		}};
	}
	
	ArrayList<UUID> block = new ArrayList<UUID>();
	HashMap<Player, String> itemname = new HashMap<Player, String>();
	
	public ArrayList<Location> getCircle(Location center, double radius, int amount){
        World world = center.getWorld();
        double increment = (2*Math.PI)/amount;
        ArrayList<Location> locations = new ArrayList<Location>();
        for(int i = 0;i < amount; i++){
        double angle = i*increment;
        double x = center.getX() + (radius * Math.cos(angle));
        double z = center.getZ() + (radius * Math.sin(angle));
        locations.add(new Location(world, x, center.getY() + 2, z));
        }
        return locations;
    }
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onMoneyBagPlace(PlayerInteractEvent e)
	{
	    final Player p = e.getPlayer();
	     
	    if (ItemManager.getInstance().isBallerItem(p.getItemInHand(), this))
	    {
	    	ItemStack chest = p.getItemInHand().clone();
	    	if (p.hasPermission("lolpvp.green")) 
	    	{
	    		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) 
			    {
	    			e.setCancelled(true);
	    			if (plugin.getCustomConfig() != null) 
	    			{
	    				if (plugin.getCustomConfig().getConfigurationSection("Main.") != null) 
	    				{
	    					if (plugin.getCustomConfig().getConfigurationSection("Main.Items.") != null) 
		    				{
		    					if (plugin.getCustomConfig().getConfigurationSection("Main.Items.").getKeys(false) != null) 
					    		{
					    			for (String option : plugin.getCustomConfig().getConfigurationSection("Main.Items.").getKeys(false)) 
						    		{
					    				if(!this.isOnCooldown(p))
					    				{
					    					if (option != null) 
							    			{
							    				ConfigurationSection path = plugin.getCustomConfig().getConfigurationSection("Main.Items." + option);
							    				double percentage = Double.valueOf(path.getString("Percentage"));
							    				if (path != null && path.get(".Name") != null && path.get(".Commands") != null) 
							    				{
							    					String prize = (String) path.getString("Name");
										    		Random rand = new Random();
										    		double comparison = rand.nextDouble() * 100;
										    		if(percentage >= comparison)
										    		{
										    			if (prize != null)
										    			{
										    				if (path.isList("Commands")) 
										    				{
										    					List<String> command = (List<String>) path.getList("Commands");
										    					if (command != null) 
										    					{
										    						for (String com : command)
											    					{
											    						String com2 = com.replace("/", "").replace("{player}", p.getName());
												    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), com2);
											    					}
										    						final Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
									    					   	     FireworkMeta data = fw.getFireworkMeta();
									    					   	     data.addEffect(FireworkEffect.builder().with(Type.BURST).flicker(true).withColor(Color.GREEN).withFlicker().build());
									    					   	     data.setPower(1);
									    					   	     fw.setFireworkMeta(data);
										    						plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable()
										    					    {
										    					        @Override
										    					      	public void run()
										    					      	{
										    					        	for (Location loc : getCircle(p.getLocation(), 1, 10)) 
									    					        		{
									    					        			ItemProjectile projectile = new ItemProjectile("money", loc, new ItemStack(Material.PAPER), p, 0f);
											    					        	projectile.getEntity().setVelocity(new Vector(0,0,0));
											    					        	projectile.getItem().addUnsafeEnchantment(Enchantment.DURABILITY, 1);
									    					        		}
										    					        	Location location = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 2, p.getLocation().getZ());
										    					        	ItemProjectile projectile = new ItemProjectile("money", location, new ItemStack(Material.PAPER), p, 0f);
										    					        	projectile.getEntity().setVelocity(new Vector(0,0,0));
										    					        	projectile.getItem().addUnsafeEnchantment(Enchantment.DURABILITY, 1);
										    					    	  	fw.detonate();
										    					      	}
										    					    }, 2L);
											    					if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) 
											    				    {
											    				    	if (!block.contains(p.getUniqueId())) 
											    				    	{
											    				    		block.add(p.getUniqueId());
											    				    	}
											    				    }
											    					//PUT COOLDOWN CODE HERE. I AM TOO LAZY TO MAKE MY OWN.
												    				this.setCooldown(p);
											    					itemname.put(p, prize);
												    				if (itemname.get(p) != null) 
												    		    	{
												    					String message = plugin.getCustomConfig().getString("Main.Broadcast Message").replace("{player}", p.getName()).replace("{item}", itemname.get(p));
												    					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
												    				    p.setItemInHand(chest);
												    				    p.updateInventory();
												    				    itemname.remove(p);
												    		    	}
										    					}
										    				}
										    			}
										            }
							    				}
							    			}	
					    				}
					    				else
					    				{
					    					p.updateInventory();
					    				}
						    	    }
					    		}
		    				}
	    				}
	    			}
			    }
	    	} else {
	    		p.setItemInHand(chest);
	    		p.sendMessage(ChatColor.RED + "Money Bags can only be opened by top donators!");
	    		p.sendMessage(ChatColor.RED + "Visit buy.lolpvp.com to work your way up!");
	    		return;
	    	}
	    }
	}
	
	public void setCooldown(Player player)
	{
		SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss");
		String parts = "0:0:0:3:0:0";
		String finalone = format.format(add(parts.split(":")));
		FileConfiguration fc = this.plugin.playerFile(player);
		fc.set("moneybag-cooldown-set", finalone);
		try
		{
			fc.save(this.plugin.playerData(player));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isOnCooldown(Player player)
	{
		SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss");
		FileConfiguration fc = this.plugin.playerFile(player);
		Date d;
		try {
			if(fc.getString("moneybag-cooldown-set") != null)
			{
				d = format.parse(fc.getString("moneybag-cooldown-set"));
				Date date = new Date();	
				if(date.after(d) || player.hasPermission("lolpvp.moneybag-bypass"))
				{
					return false;
				}
				else
				{
					String[] parts = fc.getString("moneybag-cooldown-set").split(":");
					String seconds = parts[5];
					String minutes = parts[4];
					String hours, pm, days, months, years;
					if (Integer.parseInt(parts[3]) > 12)
					{
						hours = Integer.toString(Integer.parseInt(parts[3]) - 12);
						pm = "PM";
					}
					else
					{
						hours = parts[3];
						pm = "AM";
					}
					days = parts[0];
					months = getMonthForInt(Integer.parseInt(parts[1]) - 1);
					years = parts[2];
					player.sendMessage(ChatColor.RED + "You cannot open a Moneybag until: " + months + " " + days + ", " + years + "; " + hours + ":" + minutes + ":" + seconds + " " + pm);
					return true;
				}
			}
			else
			{
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public String getMonthForInt(int num)
	{
		String month = "null";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if ((num >= 0) && (num <= 11)) {
			month = months[num];
		}
		return month;
	}
	
	public Date add(String[] parts)
	{
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int seconds = Integer.parseInt(parts[0]);
		int minutes = Integer.parseInt(parts[1]);
		int hours = Integer.parseInt(parts[2]);
		int days = Integer.parseInt(parts[3]);
		int months = Integer.parseInt(parts[4]);
		int years = Integer.parseInt(parts[5]);
		calendar.add(13, seconds);
		calendar.add(12, minutes);
		calendar.add(11, hours);
		calendar.add(5, days);
		calendar.add(2, months);
		calendar.add(1, years);
		return calendar.getTime();
	}
	
	@EventHandler
	public void onPaperHit(CustomProjectileHitEvent e) 
	{
		if (e.getProjectile().getProjectileName().equals("money")) 
		{
			if (e.getHitType().equals(HitType.ENTITY)) 
			{
				e.setCancelled(true);
			} 
			else if (e.getHitType().equals(HitType.BLOCK))
			{
				e.getProjectile().getEntity().remove();
			}
		}
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) 
	{
		if (block.contains(e.getPlayer().getUniqueId())) 
		{
			e.getBlock().setType(Material.AIR);
			block.remove(e.getPlayer().getUniqueId());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (cmd.getName().equals("resetmoneybag"))
		{
			if (sender instanceof Player)
			{
				Player player = (Player) sender;
				if(player.hasPermission("lolpvp.reset-moneybag"))
				{
					if(args.length == 0)
					{
						FileConfiguration fc = this.plugin.playerFile(player);
						fc.set("moneybag-cooldown-set", null);
						try {
							fc.save(this.plugin.playerData(player));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(args.length == 1)
					{
						FileConfiguration fc = this.plugin.playerFile(Bukkit.getOfflinePlayer(args[0]));
						fc.set("moneybag-cooldown-set", null);
						try {
							fc.save(this.plugin.playerData(player));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}	
				}
			}
		}
		return false;
	}
}

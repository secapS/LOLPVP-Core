package com.lolpvp.commands.kits;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Kits implements CommandExecutor
{
	Set<String> invis = new HashSet<String>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			
			if(command.getName().equalsIgnoreCase("regen"))
			{
				if(player.hasPermission("lolpvp.regen"))
				{
					this.giveKit(player, 1);
					this.sendKitTaken(player, "regen");
					return true;	
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You do not have permission to take this kit.");
					return true;
				}
			}
			
			if(command.getName().equalsIgnoreCase("ironman"))
			{
				if(player.hasPermission("lolpvp.ironman"))
				{
					this.giveKit(player, 0);
					this.sendKitTaken(player, "ironman");
					return true;
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You do not have permission to take this kit.");
					return true;
				}
			}
			
			if(command.getName().equalsIgnoreCase("i2"))
			{
				if(player.hasPermission("lolpvp.ironman"))
				{
					this.giveKit(player, 2);
					this.sendKitTaken(player, "ironman");
					return true;
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You do not have permission to take this kit.");
					return true;
				}
			}
			
			if(command.getName().equalsIgnoreCase("nightvision"))
			{
				if(player.hasPermission("lolpvp.nightvision"))
				{
					this.giveKit(player, 3);
					this.sendKitTaken(player, "nightvision");
					return true;	
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You do not have permission to take this kit.");
					return true;
				}
			}
			
			if(command.getName().equalsIgnoreCase("fireresistance"))
			{
				if(player.hasPermission("lolpvp.fireresistance"))
				{
					this.giveKit(player, 4);
					this.sendKitTaken(player, "fireresistance");
					return true;	
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You do not have permission to take this kit.");
					return true;
				}
			}
			
			if(command.getName().equalsIgnoreCase("invis"))
			{
				if(player.hasPermission("lolpvp.invis"))
				{
					if(!this.invis.contains(player.getName())) {
						this.giveKit(player, 5);
						this.sendKitTaken(player, "invis");
						this.invis.add(player.getName());
						return true;	
					} else {
						player.removePotionEffect(PotionEffectType.INVISIBILITY);
						this.sendKitTaken(player, "invis");
						this.invis.remove(player.getName());
						return true;
					}
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You do not have permission to take this kit.");
					return true;
				}
			}
		}
		return false;
	}
	
	public void sendKitTaken(Player player, String kit)
	{
		switch(kit)
		{
		   case "ironman":
			   player.sendMessage(ChatColor.GRAY + "You have taken" + ChatColor.AQUA + " IRONMAN " +  ChatColor.GRAY + "kit.");
			   break;
		   case "regen":
			   player.sendMessage(ChatColor.GRAY + "You have taken" + ChatColor.AQUA + " REGEN " +  ChatColor.GRAY + "kit.");
			   break;
		   case "invis":
			   player.sendMessage(ChatColor.GRAY + "You have taken" + ChatColor.AQUA + " INVIS " +  ChatColor.GRAY + "kit.");
			   break;
		   case "nightvision":
			   player.sendMessage(ChatColor.GRAY + "You have taken" + ChatColor.AQUA + " NIGHT VISION " +  ChatColor.GRAY + "kit.");
			   break;
		   case "fireresistance":
			   player.sendMessage(ChatColor.GRAY + "You have taken" + ChatColor.AQUA + " FIRE RESISTANCE " +  ChatColor.GRAY + "kit.");
			   break;
		}
	}
	
	public void giveKit(Player player, int kit)
	{
		switch(kit)
		{
			default:
				break;
			case 0: 
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 180 * 20, 2));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 180 * 20, 2));
				break;
			case 1:
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 180 * 20, 1));
				break;
			case 2:
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 180 * 20, 2));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 180 * 20, 1));
				break;
			case 3:
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 180 * 20, 0));
				break;
			case 4:
				player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 180 * 20, 0));
				break;
			case 5:
				player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true));
				break;
		}
	}
}

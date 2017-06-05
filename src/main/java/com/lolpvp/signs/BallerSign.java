	package com.lolpvp.signs;

import java.text.NumberFormat;
import java.util.Locale;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.lolpvp.core.Core;
import com.lolpvp.utils.Cooldowns;
import com.lolpvp.weapons.BallerItem;
import com.lolpvp.weapons.ItemManager;

public class BallerSign implements Listener, CommandExecutor
{
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && (event.getClickedBlock().getType().equals(Material.SIGN_POST) || event.getClickedBlock().getType().equals(Material.WALL_SIGN)))
		{
			Sign sign = (Sign)event.getClickedBlock().getState();
			
			if(sign.getLine(0).equalsIgnoreCase(ChatColor.GOLD + "-Buy-"))
			{
				BallerItem item = ItemManager.getInstance().getItemByName(sign.getLine(1));
				String s = "pex user {name} add lolpvp.ironman";
				String price = sign.getLine(2).replace('$', ' ');
				int priceInt = Integer.valueOf(price.replaceAll(",", "").replaceAll(" ", ""));
				EconomyResponse r = Core.getEconomy().withdrawPlayer(player, priceInt);
				if (!player.hasPermission("lol.bypass.qf"))
				{	
					if (Cooldowns.tryCooldown(player, "sign", 1000))
					{
						if (r.transactionSuccess())
						{
							if(item.equals(ItemManager.getInstance().getItemByName("ironman")))
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s.replace("{name}", player.getName()));
							else
								ItemManager.getInstance().giveItem(player, item);
							player.updateInventory();
							player.sendMessage(ChatColor.GRAY  + bought(item) + item.getName() + ChatColor.GRAY + " for $" + ChatColor.AQUA + NumberFormat.getNumberInstance(Locale.US).format(priceInt).replaceAll(",", ChatColor.GRAY + "," + ChatColor.AQUA) + ChatColor.GRAY + ".");
						}
						else
						{
							player.sendMessage(ChatColor.RED + "Insufficient funds.");
						}
					}
				}
				else
				{
					if(r.transactionSuccess())
					{
						if(item.equals(ItemManager.getInstance().getItemByName("ironman")))
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s.replace("{name}", player.getName()));
						else
							ItemManager.getInstance().giveItem(player, item);
						player.updateInventory();
						player.sendMessage(ChatColor.GRAY + bought(item) + item.getName() + ChatColor.GRAY + " for $" + ChatColor.AQUA + NumberFormat.getNumberInstance(Locale.US).format(priceInt).replaceAll(",", ChatColor.GRAY + "," + ChatColor.AQUA) + ChatColor.GRAY + ".");
					}
					else
					{
						player.sendMessage(ChatColor.RED + "Insufficient funds.");
					}
				}
			}
		}
	}
	
	public String bought(BallerItem item)
	{
		String name = ChatColor.stripColor(item.getName());
		if(item.equals(ItemManager.getInstance().getItemByName("ironman")))
			return "You have bought ";
		if(name.startsWith("A") || name.startsWith("I") || name.startsWith("E") || name.startsWith("O") || name.startsWith("U"))
			return "You have bought an ";
		else
			return "You have bought a ";
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if ((commandLabel.equalsIgnoreCase("ballersign")) && 
				((sender instanceof Player)))
		{
		final	Player player = (Player)sender;
			if (!player.hasPermission("lolpvp.signs"))
			{
				player.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				return true;
			}
			if (args.length < 2)
			{
				player.sendMessage(ChatColor.RED + "Usage: /ballersign <weaponName> <weaponPrice>");
				return true;
			}
			if(!ItemManager.getInstance().isBallerItem(args[0]))
			{
				player.sendMessage(ChatColor.RED + "That is not a baller item!");
				return true;
			}
			if ((player.getTargetBlock(null, 6).getType().equals(Material.SIGN)) || (player.getTargetBlock(null, 6).getType().equals(Material.SIGN_POST)) || (player.getTargetBlock(null, 6).getType().equals(Material.WALL_SIGN))) {	
				BallerItem item = ItemManager.getInstance().getItemByName(args[0]);	
				int price = Integer.valueOf(args[1]);
				Sign sign = (Sign) player.getTargetBlock(null, 6).getState();
				sign.setLine(0, ChatColor.GOLD + "-Buy-");
				String name = "";
				if(ChatColor.stripColor(item.getName()).length() > 15)
				{
					for(String alias : item.getAliases())
					{
						if(alias.length() <= 15)
						{
							name = alias;
							break;
						}
					}
				}
				else
				{
					name = item.getName();
				}
				sign.setLine(1, ChatColor.stripColor(name).toUpperCase());
				sign.setLine(2, "$" + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(price)));
				sign.update();
				player.sendMessage(ChatColor.GREEN + "Created a baller item sign.");
			}
			else
			{
				player.sendMessage(ChatColor.RED + "You must look at a sign");
			}
		}
		return true;
	}
}

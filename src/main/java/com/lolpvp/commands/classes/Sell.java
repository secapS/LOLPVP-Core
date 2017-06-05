package com.lolpvp.commands.classes;

import java.text.NumberFormat;
import java.util.Locale;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lolpvp.commands.SubCommand;
import com.lolpvp.core.Core;
import com.lolpvp.weapons.ItemManager;

public class Sell extends SubCommand
{

	public Sell() 
	{
		super("Sells a baller item in your hand.", "", new String[]{"sell", "s"});
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			if(args.length == 0)
			{
				if(ItemManager.getInstance().getBallerItemName(player.getItemInHand()) != null)
				{
					String item = ItemManager.getInstance().getBallerItemName(player.getItemInHand());
					int price = ItemManager.getInstance().getItemByName(item).getPrice();
					Core.getEconomy().depositPlayer(player, (double)price);
					player.setItemInHand(null);
					player.sendMessage(ChatColor.GRAY + "You have sold a " + item + ChatColor.GRAY + " for $" + ChatColor.AQUA + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(price)).replaceAll(",", ChatColor.GRAY + "," + ChatColor.AQUA) + ChatColor.GRAY + ".");
				}
				else
				{
					player.sendMessage(ChatColor.RED + "That is not a baller item!");
				}
			}
		}
	}

}

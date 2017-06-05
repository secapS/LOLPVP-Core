package com.lolpvp.commands.classes;

import java.text.NumberFormat;
import java.util.Locale;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lolpvp.commands.SubCommand;
import com.lolpvp.weapons.ItemManager;

public class Price extends SubCommand
{
	
	public Price() {
		super("Checks the price of baller item in your hand.", "", new String[]{"price", "p"});
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
					String name = ItemManager.getInstance().getBallerItemName(player.getItemInHand());
					int price = ItemManager.getInstance().getItemByName(name).getPrice();
					player.sendMessage(ChatColor.GRAY + "The price for a " + name + ChatColor.GRAY + " is $" + ChatColor.AQUA + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(price)).replaceAll(",", ChatColor.GRAY + "," + ChatColor.AQUA) + ChatColor.GRAY + ".");
				}
				else
				{
					player.sendMessage(ChatColor.RED + "That is not a baller item!");
				}
			}
		}
	}
}

package com.lolpvp.redeemer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;

public class PerkBookCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			if(command.getName().equalsIgnoreCase("redeem"))
			{
				if(!player.getItemInHand().getType().equals(Material.AIR))
				{
					if(PerkBookManager.getInstance().isPerkBook(player.getItemInHand()))
					{
						String[] regex = ((BookMeta)player.getItemInHand().getItemMeta()).getPage(1).split(" ");
						for(String perk : regex)
						{
							for(PerkBook perkbook : PerkBookManager.getPerkBooks())
							{
								if(perk.equalsIgnoreCase(perkbook.getPerk()))
								{
									PerkBookManager.getInstance().redeemPerkBook(player, perkbook);
								}
							}
						}
					}
					else
					{
						player.sendMessage(ChatColor.RED + "That's not a perkbook!");
						return true;	
					}	
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You must be holding a Perk Book.");
					return true;
				}
			}
			
			if(command.getName().equalsIgnoreCase("pgive") && player.isOp())
			{
				for(PerkBook perkbook : PerkBookManager.getPerkBooks())
				{
					if(perkbook.getPerk().equalsIgnoreCase(args[0]))
					{
						PerkBookManager.getInstance().givePerkBook(player, PerkBookManager.getInstance().getPerkBookByName(args[0]));
						return true;
					}
				}
			}
		}
		return false;
	}

}

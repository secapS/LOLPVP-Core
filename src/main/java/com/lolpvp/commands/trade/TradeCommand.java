package com.lolpvp.commands.trade;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Iterables;
import com.lolpvp.commands.SubCommand;

public class TradeCommand extends SubCommand implements CommandExecutor
{
	public TradeCommand() {
		super("trade perks.", "lolpvp.trade", "<perk> <player>", new String[]{"trade", "t"});
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			StringBuilder trades = new StringBuilder();
			trades.append(ChatColor.GRAY + "Trades: ");

			if(args.length == 0)
			{
				for(Trade trade : TradeManager.getInstance().getTrades())
				{
					if(!trade.getName().equalsIgnoreCase((Iterables.getLast(TradeManager.getInstance().getTrades()).getName())))
					{
						trades.append(ChatColor.AQUA + trade.getName() + ChatColor.GRAY + ", ");	
					}
					else
					{
						trades.append(ChatColor.AQUA + trade.getName() + ChatColor.GRAY + ".");
					}
				}
				player.sendMessage(ChatColor.GRAY + "----------------" + ChatColor.AQUA + "TRADE OPTIONS" + ChatColor.GRAY + "----------------");
				player.sendMessage(ChatColor.DARK_RED + "Proper syntax: /lol trade <perk> <player>");
				player.sendMessage(trades.toString());
			}
			else if(args.length == 1)
			{
				boolean successful = false;
				for(Trade trade : TradeManager.getInstance().getTrades())
				{
					if(trade.getName().equalsIgnoreCase(args[0]))
					{
						successful = true;
					}
				}
				if(successful){
					player.sendMessage(ChatColor.RED + "You must specify a player!");
					player.sendMessage(ChatColor.RED + "Proper syntax: /lol trade <perk> <player>");	
				}
				else
				{
					player.sendMessage(ChatColor.RED + "That is not a real perk! Use /lol trade to see the list of perks!");
				}
			}
			else if(args.length == 2)
			{
				boolean successful = false;
				for(Trade trade : TradeManager.getInstance().getTrades())
				{
					if(args[0].equalsIgnoreCase(trade.getName()))
					{
						successful = true;
					}
				}
				if(successful){
					if(!args[1].equalsIgnoreCase(player.getName()))
					{
						Player playerRecieving = Bukkit.getPlayer(args[1]);
						if(playerRecieving != null)
						{
							TradeManager.getInstance().tradePerk(TradeManager.getInstance().getTradeByName(args[0]), player, playerRecieving);
						}
						else
						{
							player.sendMessage(ChatColor.RED + "That player is not online!");
						}
					}
					else
					{
						player.sendMessage(ChatColor.RED + "You cannot trade with yourself!");
					}	
				}
				else
				{
					player.sendMessage(ChatColor.RED + "That is not a real perk! Use /lol trade to see the list of perks!");
				}
			}	
		}
		else
		{
			sender.sendMessage("[LOLPVP Trade] You must be a player to use this command!");
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(commandLabel.equalsIgnoreCase("loltrade"))
		{
			if(sender instanceof Player)
			{
				Player player = (Player)sender;
				StringBuilder trades = new StringBuilder();
				trades.append(ChatColor.GRAY + "Trades: ");

				if(args.length == 0)
				{
					for(Trade trade : TradeManager.getInstance().getTrades())
					{
						if(!trade.getName().equalsIgnoreCase((Iterables.getLast(TradeManager.getInstance().getTrades()).getName())))
						{
							trades.append(ChatColor.AQUA + trade.getName() + ChatColor.GRAY + ", ");	
						}
						else
						{
							trades.append(ChatColor.AQUA + trade.getName() + ChatColor.GRAY + ".");
						}
					}
					player.sendMessage(ChatColor.GRAY + "----------------" + ChatColor.AQUA + "TRADE OPTIONS" + ChatColor.GRAY + "----------------");
					player.sendMessage(ChatColor.DARK_RED + "Proper syntax: /loltrade <perk> <player>");
					player.sendMessage(trades.toString());
				}
				else if(args.length == 1)
				{
					boolean successful = false;
					for(Trade trade : TradeManager.getInstance().getTrades())
					{
						if(trade.getName().equalsIgnoreCase(args[0]))
						{
							successful = true;
						}
					}
					if(successful){
						player.sendMessage(ChatColor.RED + "You must specify a player!");
						player.sendMessage(ChatColor.RED + "Proper syntax: /loltrade <perk> <player>");	
					}
					else
					{
						player.sendMessage(ChatColor.RED + "That is not a real perk! Use /loltrade to see the list of perks!");
					}
				}
				else if(args.length == 2)
				{
					boolean successful = false;
					for(Trade trade : TradeManager.getInstance().getTrades())
					{
						if(args[0].equalsIgnoreCase(trade.getName()))
						{
							successful = true;
						}
					}
					if(successful){
						if(!args[1].equalsIgnoreCase(player.getName()))
						{
							Player playerRecieving = Bukkit.getPlayer(args[1]);
							if(playerRecieving != null)
							{
								TradeManager.getInstance().tradePerk(TradeManager.getInstance().getTradeByName(args[0]), player, playerRecieving);
							}
							else
							{
								player.sendMessage(ChatColor.RED + "That player is not online!");
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED + "You cannot trade with yourself!");
						}	
					}
					else
					{
						player.sendMessage(ChatColor.RED + "That is not a real perk! Use /loltrade to see the list of perks!");
					}
				}	
			}
			else
			{
				sender.sendMessage("[LOLPVP Trade] You must be a player to use this command!");
			}
	
		}
		return false;
	}

}

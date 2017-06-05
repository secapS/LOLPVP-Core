package com.lolpvp.commands.trade;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.lolpvp.core.Core;
import com.lolpvp.redeemer.PerkBookManager;

public class TradeManager
{	
	private static TradeManager tradeManager = new TradeManager();
	private List<Trade> trades = new ArrayList<Trade>();
	Core plugin;
	public void setupTrades(Core instance)
	{
		this.plugin = instance;
		//Perks
		this.addTrade(new Trade("repair", "essentials.repair", "essentials.repair.armor", "essentials.repair.enchanted", "essentials.repair.all"));
//		this.addTrade(new Trade("heal", "essentials.heal"));
		this.addTrade(new Trade("regen", "lolpvp.regen"));
		this.addTrade(new Trade("arenavip", "essentials.warp", "essentials.warps.arenavip"));
		this.addTrade(new Trade("ironman", "lolpvp.ironman"));
//		this.addTrade(new Trade("hat", "essentials.hat"));
//		this.addTrade(new Trade("feed", "essentials.feed"));
//		this.addTrade(new Trade("ignore", "essentials.ignore"));
		this.addTrade(new Trade("spinningtag", "lolpvp.setmagic"));
//		this.addTrade(new Trade("ptime", "essentials.ptime"));
//		this.addTrade(new Trade("enderchest", "essentials.enderchest"));
		this.addTrade(new Trade("tptoggle", "essentials.tptoggle"));
		
		//Groups
		this.addTrade(new Trade("$$$$$", "Donator_Five", true));
		this.addTrade(new Trade("thad+", "Donator_Four", true));
		this.addTrade(new Trade("thad", "Donator_Three", true));
		this.addTrade(new Trade("vip+", "Donator_Two", true));
		this.addTrade(new Trade("vip", "Donator_Zero", true));
	}
	
	public void clearTrades()
	{
		this.trades.clear();
	}
	
	public List<Trade> getTrades()
	{
		return trades;
	}
	
	public void addTrade(Trade trade)
	{
		trades.add(trade);
	}
	
	public void removeTrade(Trade trade)
	{
		trades.remove(trade);
	}
	
	public static TradeManager getInstance()
	{
		return tradeManager;	
	}
	
	public Trade getTradeByName(String tradeName)
	{
		for(Trade trade : this.getTrades())
		{
			if(trade.getName().equalsIgnoreCase(tradeName))
			{
				return trade;
			}
		}
		return null;
	}

	public boolean fullInv(Player player, int i)
	{
		int l = 0;
		for (ItemStack item : player.getInventory().getContents()) {
			if (item == null) {
				l++;
			}
		}
		return l >= i;
	}
	
	@SuppressWarnings("deprecation")
	public void tradePerk(Trade trade, Player playerTrading, Player playerReceiving)
	{
		PermissionUser userTrading = PermissionsEx.getUser(playerTrading);
		PermissionUser userRecieving = PermissionsEx.getUser(playerReceiving);
		
		boolean successful = false;
		if(trade.isGroup())
		{	
			for(PermissionGroup group : userTrading.getGroups())
			{
				if(group.getName().equals(trade.getGroup()))
				{
					successful = true;
				}
			}
			
			if(successful)
			{
				if(!fullInv(playerReceiving, 1))
				{
					successful = false;
					playerTrading.sendMessage(ChatColor.GRAY + "Trade Failed! " + ChatColor.AQUA + playerReceiving.getName() + ChatColor.GRAY + "'s inventory is full!");
				}
				else
				{
					userTrading.removeGroup(trade.getGroup());
					userTrading.addGroup("Regular");
					plugin.getChatMethod().removeTag(playerTrading);
					PerkBookManager.getInstance().givePerkBook(playerReceiving, PerkBookManager.getInstance().getPerkBookByName(trade.getName()));
					playerTrading.sendMessage(ChatColor.GRAY + "Trade successful! You have given " + ChatColor.AQUA +  trade.getName() + ChatColor.GRAY + " to " + ChatColor.AQUA + playerReceiving.getName() + ChatColor.GRAY + "!");
					playerReceiving.sendMessage(ChatColor.GRAY + "Trade successful! You received " + ChatColor.AQUA +  trade.getName() + ChatColor.GRAY + " from " + ChatColor.AQUA + playerTrading.getName() + ChatColor.GRAY + "!");	
					successful = false;	
				}
				return;
			}
			else
			{
				playerTrading.sendMessage(ChatColor.RESET + "" + ChatColor.RED + "You do not have the right permissions to trade: " + ChatColor.AQUA + trade.getName() + ChatColor.RED + "!");
				playerTrading.sendMessage(ChatColor.DARK_RED  + "If this is an error, please contact LOLPVP Support with proof!");
				return;
			}
		}
		else if(trade.hasMultiplePermissions())
		{
			int i = 0;
			for(String permissions : trade.getPermissions())
			{
				if(userTrading.has(permissions))
				{
					i++;
					if(i == trade.getPermissions().length)
						successful = true;
				}
				else
				{
					successful = false;
				}
			}
			
			if(successful){
				for(String permissions : trade.getPermissions())
				{
					userTrading.removePermission(permissions);
					userRecieving.addPermission(permissions);	
				}
				
				playerTrading.sendMessage(ChatColor.GRAY + "Trade successful! You have given " + ChatColor.AQUA +  trade.getName() + ChatColor.GRAY + " to " + ChatColor.AQUA + playerReceiving.getName() + ChatColor.GRAY + "!");
				playerReceiving.sendMessage(ChatColor.GRAY + "Trade successful! You received " + ChatColor.AQUA +  trade.getName() + ChatColor.GRAY + " from " + ChatColor.AQUA + playerTrading.getName() + ChatColor.GRAY + "!");
				return;
			}
			else
			{
				playerTrading.sendMessage(ChatColor.RESET + "" + ChatColor.RED + "You do not have the right permissions to trade: " + ChatColor.AQUA + trade.getName() + ChatColor.RED + "!");
				playerTrading.sendMessage(ChatColor.DARK_RED  + "If this is an error, please contact LOLPVP Support with proof!");
				return;
			}
			/*
			if(trade.hasMultiplePermissions())
			{
				boolean successful = false;
				for(String permissions : trade.getPermissions())
				{
					if(userTrading.has(permissions))
					{
						userTrading.removePermission(permissions);
						userRecieving.addPermission(permissions);
						successful = true;
					}
					else
					{
						successful = false;
					}
				}
				
				if(successful){
					playerTrading.sendMessage(ChatColor.GRAY + "Trade successful! You have given " + ChatColor.AQUA +  trade.getName() + ChatColor.GRAY + " to " + ChatColor.AQUA + playerRecieving.getName() + ChatColor.GRAY + "!");
					playerRecieving.sendMessage(ChatColor.GRAY + "Trade successful! You received " + ChatColor.AQUA +  trade.getName() + ChatColor.GRAY + " from " + ChatColor.AQUA + playerTrading.getName() + ChatColor.GRAY + "!");
				}
				else
				{
					playerTrading.sendMessage(ChatColor.RESET + "" + ChatColor.RED + "You do not have the right permissions to trade: " + ChatColor.AQUA + trade.getName() + ChatColor.RED + "!");
					playerTrading.sendMessage(ChatColor.DARK_RED  + "If this is an error, please contact LOLPVP Support with proof!");
				}
			}
			else
			{
				if(userTrading.has(trade.getPermission()))
				{
					userTrading.removePermission(trade.getPermission());
					userRecieving.addPermission(trade.getPermission());
					playerTrading.sendMessage(ChatColor.GRAY + "Trade successful! You have given " + ChatColor.AQUA +  trade.getName() + ChatColor.GRAY + " to " + ChatColor.AQUA + playerRecieving.getName() + ChatColor.GRAY + "!");
					playerRecieving.sendMessage(ChatColor.GRAY + "Trade successful! You received " + ChatColor.AQUA +  trade.getName() + ChatColor.GRAY + " from " + ChatColor.AQUA + playerTrading.getName() + ChatColor.GRAY + "!");
					return;
				}
				else
				{
					playerTrading.sendMessage(ChatColor.RESET + "" + ChatColor.RED + "You do not have the right permissions to trade: " + ChatColor.AQUA + trade.getName() + ChatColor.RED + "!");
					playerTrading.sendMessage(ChatColor.DARK_RED  + "If this is an error, please contact LOLPVP Support with proof!");
					return;
				}
			}*/
		}
		else if(userTrading.has(trade.getPermission()))
		{
			userTrading.removePermission(trade.getPermission());
			userRecieving.addPermission(trade.getPermission());
			playerTrading.sendMessage(ChatColor.GRAY + "Trade successful! You have given " + ChatColor.AQUA +  trade.getName() + ChatColor.GRAY + " to " + ChatColor.AQUA + playerReceiving.getName() + ChatColor.GRAY + "!");
			playerReceiving.sendMessage(ChatColor.GRAY + "Trade successful! You received " + ChatColor.AQUA +  trade.getName() + ChatColor.GRAY + " from " + ChatColor.AQUA + playerTrading.getName() + ChatColor.GRAY + "!");
			return;
		}
		else
		{
			playerTrading.sendMessage(ChatColor.RESET + "" + ChatColor.RED + "You do not have the right permissions to trade: " + ChatColor.AQUA + trade.getName() + ChatColor.RED + "!");
			playerTrading.sendMessage(ChatColor.DARK_RED  + "If this is an error, please contact LOLPVP Support with proof!");
			return;
		
		}
	}
}
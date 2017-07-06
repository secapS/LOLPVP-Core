package com.lolpvp.chat;

import java.io.IOException;

import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.lolpvp.core.Core;

public class NewChat implements Listener, CommandExecutor
{
	Core plugin;
	
	public NewChat(Core instance)
	{
		this.plugin = instance;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
//		if(!(Essentials.getPlugin(Essentials.class).getUser(player).isMuted() || this.plugin.muteAll.isChatMuted()))
		{
			event.setCancelled(true);
			FancyMessage newMessage = new FancyMessage();
			
//			if(plugin.getChatMethod2().hasTag(player))
//			{
//				newMessage.text("[")
//				.color(ChatColor.DARK_GRAY)
//				.then(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', plugin.getChatMethod2().filterTag(player))))
//				.color(plugin.getChatMethod().getPrefixChatColor(player))
//				.then("]")
//				.color(ChatColor.DARK_GRAY)
//				.then(player.getDisplayName())
//				.color(ChatColor.GRAY)
//				.tooltip("Money: $" + Core.getEconomy().format(Core.getEconomy().getBalance(player)).replace(" Dollars", ""),
//					     "Team: " + Core.getTeams().getTeam(player))
//				.suggest("/m " + player.getDisplayName())
//				.then(">")
//				.color(ChatColor.GOLD)
//				.then(" " + event.getMessage())
//				.color(ChatColor.GRAY);
//			}
//			else
			{
				newMessage.text(player.getDisplayName())
				.color(ChatColor.GRAY)
				.tooltip("Money: $" + Core.getEconomy().format(Core.getEconomy().getBalance(player)).replace(" Dollars", ""))
//					     "Team: " + Core.getTeams().getTeam(player))
				.suggest("/msg " + player.getName())
				.then(">")
				.color(ChatColor.GOLD)
				.then(" " + event.getMessage())
				.color(ChatColor.GRAY);
			}
//			for(Player players : Bukkit.getOnlinePlayers())
//				newMessage.send(players);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		
		if(plugin.getChatMethod2().hasTag(player))
		{
//			player.setDisplayName(ChatColor.DARK_GRAY + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getChatMethod2().filterTag(player)) + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY + this.getNick(player) + ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + Core.getLevels().getPlayerLevel(player) + ChatColor.DARK_GRAY + "]");
		}
		else
		{
//			player.setDisplayName(ChatColor.GRAY + this.getNick(player) + ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + Core.getLevels().getPlayerLevel(player) + ChatColor.DARK_GRAY + "]");
		}
	}
	
	public boolean hasNick(Player player)
	{
		FileConfiguration fc = this.plugin.playerData(player);
		return fc.getString("nick") != null;
	}
	
	public String getNick(Player player)
	{
		FileConfiguration fc = this.plugin.playerData(player);
		return hasNick(player) ? fc.getString("nick") : player.getName();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) 
	{
		if(!sender.isOp())
		{
			sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
			return false;
		}
		
		if(command.getName().equalsIgnoreCase("setnick"))
		{
			if(args.length > 0)
			{
				if(args.length == 1)
				{
					if(Bukkit.getOfflinePlayer(args[0]) != null)
					{
						OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
						
						if(player.hasPlayedBefore())
						{
							FileConfiguration fc = this.plugin.playerData(player);
							
							if(fc.getString("nick") != null)
							{
								fc.set("nick", null);
//								try {
//									fc.save(this.plugin.playerFile(player));
//								} catch (IOException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
								sender.sendMessage(ChatColor.GREEN + "Removed nick from " + ChatColor.AQUA + player.getName());
							}
						}
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "Player is null");
					}
				}
				else if(args.length == 2)
				{
					if(Bukkit.getOfflinePlayer(args[0]) != null)
					{
						OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
						
						if(player.hasPlayedBefore())
						{
							FileConfiguration fc = this.plugin.playerData(player);
							fc.set("nick", args[1]);
//							try {
//								fc.save(this.plugin.playerFile(player));
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							sender.sendMessage(ChatColor.GREEN + "Set " + ChatColor.AQUA + player.getName() + ChatColor.GREEN + "'s nick to " + ChatColor.AQUA + args[1]);
						}
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "Player is null");
					}
				}
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "Syntax: /setnick <playerName> <nick>");
			}
		}
		return false;
	}
	
//	@Override
//	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
//	{
//		if(command.getName().equals("lolt"))
//		{
//			if(sender instanceof Player)
//			{
//				Player player = (Player)sender;
//				if(player.hasPermission("lolpvp.settag"))
//				{
//					if(args.length == 1)
//					{
//						this.setTag(player, args[0]);	
//					}
//					else
//					{
//						player.sendMessage(ChatColor.RED + "/lolt <tag>");
//					}
//				}
//				else
//				{
//					player.sendMessage("You do not have permission for this command.");
//				}
//			}
//		}
//		
//		if(command.getName().equals("lolto"))
//		{
//			if(sender.hasPermission("lolpvp.settag.others"))
//			{
//				if(args.length == 2)
//				{
//					if(Bukkit.getPlayer(args[0]) != null)
//					{
//						this.setTag(Bukkit.getPlayer(args[0]), args[1]);
//					}
//					else
//					{
//						sender.sendMessage(ChatColor.RED + "That player is not online.");
//					}
//				}
//			}
//		}
//		
//		if(command.getName().equals("lolm"))
//		{
//			if(sender instanceof Player)
//			{
//				Player player = (Player)sender;
//				if(player.hasPermission("lolpvp.magic"))
//				{
//					this.setMagicTag(player);
//				}
//				else
//				{
//					sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
//				}
//			}
//		}
//		return false;
//	}
//	
//	public void setTag(Player player, String tag)
//	{
//		if(checkTag(player, tag))
//		{
//			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " prefix " + "&8[" + getTagColor(player) + tag + "&8]");
//			player.sendMessage(ChatColor.GREEN + "Set your tag to: " + ChatColor.DARK_GRAY + "[" + ChatColor.translateAlternateColorCodes('&', getTagColor(player)) + tag + ChatColor.DARK_GRAY + "]");
//		}
//		else
//		{
//			player.sendMessage(ChatColor.RED + "You cannot use that tag");
//		}
//	}
//	
//	public void setMagicTag(Player player)
//	{
//		PermissionUser user = PermissionsEx.getUser(player);
//		if(!user.getPrefix().replace("[", "").replace("]", "").contains("&k"))
//		{
//			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " prefix " + "&8[" + getTagColor(player) + "&k" + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', user.getPrefix().replace("[", "").replace("]", ""))) + "&8]");
//		}
//		else
//		{
//			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " prefix " + "&8[" + getTagColor(player) + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', user.getPrefix().replace("[", "").replace("]", ""))) + "&8]");
//		}
//		player.sendMessage(ChatColor.GREEN + "Set magic color");
//	}
//	
//	public String getTagColor(Player player)
//	{
//		String color = player.hasPermission("lolpvp.gray") ? "&7" : null;
//		color = player.hasPermission("lolpvp.darkred") ? "&4" : color;
//		color = player.hasPermission("lolpvp.black") ? "&0" : color;
//		color = player.hasPermission("lolpvp.green") ? "&a" : color;
//		color = player.hasPermission("lolpvp.red") ? "&c" : color;
//		color = player.hasPermission("lolpvp.gold") ? "&6": color;
//		return color;
//	}
//	
//	/**
//	 * 
//	 * @param player 
//	 * @param tag
//	 * @return true if the player can use the tag. returns false if they can't.
//	 */
//	public boolean checkTag(Player player, String tag)
//	{
//		for(String bannedTag : this.getBannedTags())
//		{
//			if(tag.equalsIgnoreCase(bannedTag))
//				return false;
//		}
//		if(tag.length() > 5)
//		{
//			if(player.hasPermission("lolpvp.longtag"))
//			{
//				if(!player.hasPermission("lolpvp.owner"))
//				{
//					if(tag.length() < 25)
//					{
//						return true;
//					}
//					else
//					{
//						player.sendMessage(ChatColor.RED + "That tag is too long.");
//						return false;
//					}
//				}
//				else
//				{
//					return true;
//				}
//			}
//			else
//			{
//				player.sendMessage(ChatColor.RED + "That tag is too long.");
//				return false;
//			}
//		}
//		else if(tag.length() <= 5)
//		{
//			return true;
//		}
//		return false;
//	}
//	
//	public boolean canSetTag(Player player)
//	{
//		SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss");
//		FileConfiguration fc = this.plugin.playerData(player);
//		try {
//			Date d = format.parse(fc.getString("next-set"));
//			Date date = new Date();
//			return date.after(d);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}
//	
//	public String[] getBannedTags()
//	{
//		return new String[]
//		{
//			"Owner",
//			"Admin",
//			"Mod",
//			"Helper",
//			"Moderator",
//			"Adm1n",
//			"M0d",
//			"Helpr",
//			"Staff",
//			"Administrator"
//		};
//	}
}
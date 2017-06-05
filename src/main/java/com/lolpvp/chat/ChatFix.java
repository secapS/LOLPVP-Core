package com.lolpvp.chat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.lolpvp.core.Core;
import com.lolpvp.utils.UUIDLibrary;

public class ChatFix implements Listener, CommandExecutor
{
	private Core plugin;
	
	public ChatFix(Core plugin)
	{
		this.plugin = plugin;
	}
	
	public void set(Player player, String[] args)
	{
		FileConfiguration fc = this.plugin.playerFile(player);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= args.length - 1; i++) {
			sb.append(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', args[i]))).append(" ");
		}
		String k = sb.toString();
		if (fc.getString("next-set") != null)
		{
			this.plugin.getChatMethod().setTag(player, fc, k.substring(0, k.length() - 1));
			this.plugin.getChatMethod2().setPrefix(player);
		}
		else
		{
			this.plugin.getChatMethod().quitSet(player, fc, k.substring(0, k.length() - 1));
			this.plugin.getChatMethod2().setPrefix(player);
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if ((cmd.getName().equalsIgnoreCase("lolt")) && 
				((sender instanceof Player)))
		{
			Player player = (Player)sender;
			if ((!player.hasPermission("lolpvp.settag")) && (!player.hasPermission("lolpvp.*")) && (!player.isOp()))
			{
				player.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				return true;
			}
			switch (args.length)
			{
			case 0: 
				player.sendMessage(ChatColor.RED + "Usage: /lolt <tag>");
				return true;
			case 1: 
				set(player, args);
				return true;
			}
			set(player, args);
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("loltd"))
		{
			if ((!sender.hasPermission("lolpvp.settag.others")) && (!sender.hasPermission("lolpvp.*")) && (!sender.isOp()))
			{
				sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				return true;
			}
			switch (args.length)
			{
			case 1: 
				if (UUIDLibrary.getUUIDFromName(args[0]) != null)
				{
					String uuid = UUIDLibrary.getUUIDFromName(args[0]);
					FileConfiguration fc = this.plugin.playerFile(uuid);
					SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss");
					Date date = new Date();
					fc.set("next-set", format.format(date));
					sender.sendMessage(ChatColor.GREEN + "Reset " + UUIDLibrary.getExactName(args[0]) + " set-tag time to now.");
					try
					{
						fc.save(this.plugin.playerData(uuid));
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "Could not find player " + args[0]);
				}
				break;
			default: 
				sender.sendMessage(ChatColor.RED + "Usage: /loltd <player>");
			}
		}
		if (cmd.getName().equalsIgnoreCase("lolto"))
		{
			if ((!sender.hasPermission("lolpvp.settag.others")) && (!sender.hasPermission("lolpvp.*")) && (!sender.isOp()))
			{
				sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				return true;
			}
			switch (args.length)
			{
			case 0: 
			case 1: 
				sender.sendMessage(ChatColor.RED + "Usage: /lolto <player> <tag>");
				return true;
			}
			if (sender.hasPermission("lolpvp.longtag"))
			{
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i <= args.length - 1; i++) {
					sb.append(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', args[i]))).append(" ");
				}
				String k = sb.toString();
				this.plugin.getChatMethod().setTagOther(sender, args[0], k.substring(0, k.length() - 1));
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
			}
		}
		if (cmd.getName().equalsIgnoreCase("loltr"))
		{
			if ((!sender.hasPermission("lolpvp.settag.others")) && (!sender.hasPermission("lolpvp.*")) && (!sender.isOp()))
			{
				sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				return true;
			}
			if (args.length != 1)
			{
				sender.sendMessage(ChatColor.RED + "Usage: /loltr <player>");
				return true;
			}
			this.plugin.getChatMethod().removeTag(sender, args[0]);
		}
		if (cmd.getName().equalsIgnoreCase("lolm"))
		{
			if ((!sender.hasPermission("lolpvp.setmagic")) && (!sender.hasPermission("lolpvp.*")) && (!sender.isOp()))
			{
				sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				return true;
			}
			switch (args.length)
			{
			case 0: 
				if ((sender instanceof Player))
				{
					Player player = (Player)sender;
					FileConfiguration fc = this.plugin.playerFile(player);
					if (fc.getString("tag") == null)
					{
						sender.sendMessage(ChatColor.RED + "You do not have a tag set.");
						return true;
					}
					String ss = this.plugin.getChatMethod().getPrefix(player);
					String tag = fc.getString("tag");
					if ((fc.getString("magic") == null) || (!fc.getBoolean("magic")))
					{
						player.sendMessage(ChatColor.GREEN + "Your tag has been set to: " + ss + "&k" + tag);
						fc.set("magic", Boolean.valueOf(true));
						this.plugin.getChatMethod2().setPrefix(player);
					}
					else
					{
						player.sendMessage(ChatColor.GREEN + "Your tag has been set to: " + ss + tag);
						fc.set("magic", Boolean.valueOf(false));
						this.plugin.getChatMethod2().setPrefix(player);
					}
					try
					{
						fc.save(this.plugin.playerData(player));
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				break;
			case 1: 
				if (!sender.hasPermission("lolpvp.setmagic.others"))
				{
					sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
					return true;
				}
				if (UUIDLibrary.getUUIDFromName(args[0]) == null)
				{
					sender.sendMessage(ChatColor.RED + "Could not find player " + args[0]);
					return true;
				}
				String uuid = UUIDLibrary.getUUIDFromName(args[0]);
				Player player = Bukkit.getPlayer(UUID.fromString(UUIDLibrary.getUUIDFromName(args[0])));
				FileConfiguration fc = this.plugin.playerFile(uuid);
				if (fc.getString("tag") == null)
				{
					sender.sendMessage(ChatColor.RED + "You do not have a tag set.");
					return true;
				}
				String ss = this.plugin.getChatMethod().getOfflinePrefix(player, (World)Bukkit.getServer().getWorlds().get(0));
				String tag = fc.getString("tag");
				if ((fc.getString("magic") == null) || (!fc.getBoolean("magic")))
				{
					sender.sendMessage(ChatColor.GRAY + "Set " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " tag to: " + ss + "&k" + tag);
					fc.set("magic", Boolean.valueOf(true));
				}
				else
				{
					sender.sendMessage(ChatColor.GRAY + "Set " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " tag to: " + ss + tag);
					fc.set("magic", Boolean.valueOf(false));
				}
				try
				{
					fc.save(this.plugin.playerData(uuid));
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				break;
			default: 
				sender.sendMessage(ChatColor.RED + "Usage: /lolm");
			}
		}
//		else if(cmd.getName().equalsIgnoreCase("lolnt"))
//		{
//			if(!sender.isOp())
//				return true;
//			switch(args.length)
//			{
//			case 0:
//				if(sender instanceof Player)
//				{
//					this.plugin.getChatMethod().setNameTag((Player)sender);
//					sender.sendMessage(ChatColor.GRAY + "You have set your nametag.");
//					return true;
//				}
//				else
//				{
//					sender.sendMessage(ChatColor.RED + "You must be a player to use this command on yourself.");
//					return true;
//				}
//			case 1:
//				this.plugin.getChatMethod().setNameTag(Bukkit.getPlayer(args[0]));
//				sender.sendMessage(ChatColor.GRAY + "You have set " + ChatColor.AQUA + Bukkit.getPlayer(args[0]).getName() + ChatColor.GRAY + "'s name tag.");
//				return true;
//			default: 
//				sender.sendMessage(ChatColor.RED + "Correct usage: /loltnt <player>");
//				return true;
//			}
//		}
//		else if(cmd.getName().equalsIgnoreCase("lolrnt"))
//		{
//			if(!sender.isOp())
//				return true;
//			switch(args.length)
//			{
//			case 0:
//				if(sender instanceof Player)
//				{
//					this.plugin.getChatMethod().removeNameTag((Player)sender);
//					sender.sendMessage(ChatColor.GRAY + "You have removed your nametag.");
//					return true;
//				}
//				else
//				{
//					sender.sendMessage(ChatColor.RED + "You must be a player to use this command on yourself.");
//					return true;
//				}
//			case 1:
//				this.plugin.getChatMethod().removeNameTag(Bukkit.getPlayer(args[0]));
//				sender.sendMessage(ChatColor.GRAY + "You have removed " + ChatColor.AQUA + Bukkit.getPlayer(args[0]).getName() + ChatColor.GRAY + "'s name tag.");
//				return true;
//			default: 
//				sender.sendMessage(ChatColor.RED + "Correct usage: /lolrnt <player>");
//				return true;
//			}
//		}
		return true;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		Player o = event.getPlayer();
		FileConfiguration fc = this.plugin.playerFile(o);
		if ((!Core.permission.getPrimaryGroup(o).equalsIgnoreCase("regular")) && 
				(this.plugin.getChatMethod2().hasTag(o)) && 
				(o.hasPermission("lolpvp.settag")))
		{
			if ((this.plugin.getChatMethod2().filterTag(o).startsWith("null")) || (this.plugin.getChatMethod2().filterTag(o).contains("&8"))) {
				this.plugin.getChatMethod().setTag(o, this.plugin.playerFile(o), this.plugin.getChatMethod2().filterTag(o).replace("null", "").replace("&8", ""));
			}
			this.plugin.getChatMethod2().setPrefix(o);
		}
		if (o.getName().length() > 14) {
			o.setPlayerListName(ChatColor.GRAY + o.getName().substring(0, 14));
		} else {
			o.setPlayerListName(ChatColor.GRAY + o.getName());
		}
		if (fc.getString("magic") == null)
		{
			fc.set("magic", Boolean.valueOf(false));
			fc.set("uuid", o.getUniqueId().toString().replace("-", ""));
			try
			{
				fc.save(this.plugin.playerData(o));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public String filterMessage(Player player, String m)
	{
		if (player.hasPermission("lolpvp.chatcolor")) {
			return ChatColor.translateAlternateColorCodes('&', m.replace("{TAG}", ""));
		}
		return m.replace("{TAG}", "");
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		FileConfiguration fc = this.plugin.playerFile(player);
		String group = Core.permission.getPrimaryGroup(player);
		String m = event.getMessage();
		String has = this.plugin.getConfig().getString("groups." + group + ".has-tag");
		String no = this.plugin.getConfig().getString("groups." + group + ".no-tag");
		if (fc.getString("tag") != null)
		{
			String tag = this.plugin.getChatMethod2().filterTag(player);
			if (this.plugin.getChatMethod().getPrefix(player) != null)
			{
				event.setMessage(filterMessage(player, m));
				event.setFormat(ChatColor.translateAlternateColorCodes('&', has.replace("{PLAYER}", player.getName()).replace("{TAG}", tag)) + m.replace("%", "%%"));
			}
			else
			{
				event.setMessage(filterMessage(player, m));
				event.setFormat(ChatColor.translateAlternateColorCodes('&', has.replace("{PLAYER}", player.getName()).replace("{TAG}", tag)) + m.replace("%", "%%"));
			}
		}
		else
		{
			event.setMessage(filterMessage(player, m));
			event.setFormat(ChatColor.translateAlternateColorCodes('&', no.replace("{PLAYER}", player.getName())) + m.replace("%", "%%"));
		}
	}
}
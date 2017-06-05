package com.lolpvp.commands.classes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.lolpvp.core.Core;

public class MuteAll implements CommandExecutor, Listener
{
	private boolean muted;
	private Core plugin;

	public MuteAll(Core plugin)
	{
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if ((cmd.getName().equalsIgnoreCase("muteall")) && 
				((sender instanceof Player)))
		{
			Player player = (Player)sender;
			if (!player.hasPermission("lolpvp.muteall"))
			{
				player.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				return true;
			}
			if (args.length >= 0)
			{
				if (!this.muted)
				{
					this.muted = true;
					plugin.getServer().broadcastMessage(ChatColor.RED + "The public chat has been muted.");
					return true;
				}
				this.muted = false;
				plugin.getServer().broadcastMessage(ChatColor.GREEN + "The public chat has been unmuted.");
				return true;
			}
		}
		return true;
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		if (this.muted && !player.hasPermission("lolpvp.talkmute"))
		{
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "The public chat is currently muted.");
		}
	}
	
	public boolean isChatMuted()
	{
		return this.muted;
	}
}


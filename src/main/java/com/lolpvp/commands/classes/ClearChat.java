package com.lolpvp.commands.classes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lolpvp.core.Core;

public class ClearChat implements CommandExecutor
{
	private Core plugin;

	public ClearChat(Core plugin)
	{
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		Player player = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("clearchat"))
		{
			if (!player.hasPermission("lolpvp.clearchat"))
			{
				player.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				return true;
			}
			if (args.length >= 0) {
				for (int x = 0; x < 120; x++) {
					for (Player p : Bukkit.getOnlinePlayers())
					{
						p.sendMessage(" ");
						if (x == 119)
						{
							plugin.getServer().broadcastMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " cleared the chat.");
							break;
						}
					}
				}
			}
		}
		return true;
	}
}

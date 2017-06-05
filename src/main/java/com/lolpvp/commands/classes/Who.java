package com.lolpvp.commands.classes;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.lolpvp.core.Core;

public class Who implements CommandExecutor
{
	private Core plugin;

	public Who(Core plugin)
	{
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if ((cmd.getName().equalsIgnoreCase("who")) && 
				((sender instanceof Player)))
		{
			Player player = (Player)sender;
			StringBuilder sb;
			int i;
			int b;
			int k;
			int l;
			if (player.hasPermission("lolpvp.who"))
			{
				if (args.length >= 0)
				{
					sb = new StringBuilder();
					i = Bukkit.getServer().getOnlinePlayers().length;
					b = 0;
					k = 0;
					if (this.plugin.getConfig().getConfigurationSection("groups.") != null) {
						for (String l1 : this.plugin.getConfig().getConfigurationSection("groups.").getKeys(true)) {
							for (Player o : Bukkit.getServer().getOnlinePlayers()) {
								if ((player.canSee(o)) && 
										(Core.permission.getPrimaryGroup(o).equalsIgnoreCase(l1)))
								{
									FileConfiguration fc = this.plugin.playerData(o);
									if (this.plugin.getConfig().getString("groups." + l1 + ".fixed-tag") == null)
									{
										if (fc.getString("tag") != null)
										{
											if (this.plugin.getChatMethod().getPrefix(o) != null) {
												sb.append(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("list-prefix").replace("{TAG}", this.plugin.getChatMethod2().filterTag(o)) + o.getName())).append(ChatColor.WHITE + ", ");
											} else {
												sb.append(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("list-no-tag-prefix") + o.getName())).append(ChatColor.WHITE + ", ");
											}
										}
										else {
											sb.append(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("list-no-tag-prefix") + o.getName())).append(ChatColor.WHITE + ", ");
										}
									}
									else if (!this.plugin.getConfig().getBoolean("groups." + l1 + ".staff"))
									{
										if (fc.getString("tag") != null)
										{
											if (this.plugin.getChatMethod().getPrefix(o) != null) {
												sb.append(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("list-prefix").replace("{TAG}", this.plugin.getChatMethod2().filterTag(o)) + o.getName())).append(ChatColor.WHITE + ", ");
											} else {
												sb.append(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("list-no-tag-prefix") + o.getName())).append(ChatColor.WHITE + ", ");
											}
										}
										else {
											sb.append(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(new StringBuilder().append("groups.").append(l1).append(".fixed-tag").toString()) + o.getName())).append(ChatColor.WHITE + ", ");
										}
									}
									else {
										sb.append(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(new StringBuilder().append("groups.").append(l1).append(".fixed-tag").toString()) + o.getName())).append(ChatColor.WHITE + ", ");
									}
									if (this.plugin.getConfig().getBoolean("groups." + l1 + ".staff")) {
										b++;
									}
									if (this.plugin.getConfig().getBoolean("groups." + l1 + ".donator")) {
										k++;
									}
								}
							}
						}
					}
					l = Bukkit.getServer().getMaxPlayers();
					List<String> help = this.plugin.getConfig().getStringList("list");
					for (String s : help) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', s).replace("{ONLINE}", Integer.toString(i)).replace("{STAFF_ONLINE}", Integer.toString(b)).replace("{ALL_PLAYERS}", sb.toString().substring(0, sb.length() - 2)).replace("{MAX}", Integer.toString(l)).replace("{DONORS}", Integer.toString(k)));
					}
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "You do not have permission for this command.");
			}
		}
		return true;
	}
}
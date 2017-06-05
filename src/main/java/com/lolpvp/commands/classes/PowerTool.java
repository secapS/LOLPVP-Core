package com.lolpvp.commands.classes;

import java.io.IOException;
import java.util.Set;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.lolpvp.core.Core;
import com.lolpvp.utils.Cooldowns;

public class PowerTool implements CommandExecutor, Listener
{
	Core plugin;
	public PowerTool(Core core)
	{
		this.plugin = core;
	}
	
	@EventHandler
	public void onHit(PlayerInteractEvent event)
	{
		if ((event.getAction().equals(Action.LEFT_CLICK_BLOCK)) || (event.getAction().equals(Action.LEFT_CLICK_AIR))) {
			Player player = event.getPlayer();
			if (player.hasPermission("lol.pt"))
			{
				FileConfiguration fc;
				ItemStack it = player.getItemInHand();
				fc = this.plugin.playerFile(player);
				if (fc.getConfigurationSection("pt") != null)
				{
					if ((it != null) && (!it.getType().equals(Material.AIR))) {
						Set<String> ptItems = fc.getConfigurationSection("pt").getKeys(false);
						if(ptItems.contains(it.getType().name())) {
							int cooldown = this.plugin.getConfig().getInt("pt-cooldown");
							String command = fc.getString("pt." + it.getType().name());
							if(!player.hasPermission("lol.bypass.pt")) {
								if (Cooldowns.tryCooldown(player, "pt", cooldown * 1000)) {
									event.setCancelled(true);
									Bukkit.getServer().dispatchCommand(player, command);
									return;
								}
								else {
									long cooldownLeft = Cooldowns.getCooldown(player, "pt") / 1000L;
									player.sendMessage(ChatColor.RED + "You cannot use powertool for another " + Long.toString(cooldownLeft) + " seconds.");
								}
							}
							else {
								event.setCancelled(true);
								Bukkit.getServer().dispatchCommand(player, command);
								return;
							}

						}
					}
				}
			}	
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			if(commandLabel.equalsIgnoreCase("pt"))
			{
				if ((player.getItemInHand() == null) || (player.getItemInHand().getType().equals(Material.AIR)))
				{
					player.sendMessage(ChatColor.RED + "Hold an item.");
					return true;
				}
				FileConfiguration fc = this.plugin.playerFile(player);
				if (args.length == 0)
				{
					player.sendMessage(ChatColor.GRAY + "Removed powertool from the item in your hand.");
					fc.set("pt." + player.getItemInHand().getType().name(), null);
					try {
						fc.save(this.plugin.playerData(player));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return true;
				}
				StringBuilder sb = new StringBuilder();
				for (int k = 0; k <= args.length - 1; k++) 
				{
					sb.append(args[k]).append(" ");
				}
				fc.set("pt." + player.getItemInHand().getType().name(), sb.toString());
				String name = player.getItemInHand().getType().name();
				String ss = WordUtils.capitalizeFully(name.toLowerCase().replace("_", " "));
				player.sendMessage(ChatColor.GRAY + "Put powertool on " + ChatColor.AQUA + ss + ChatColor.GRAY + " with the command " + ChatColor.AQUA + sb.toString());
				try
				{
					fc.save(this.plugin.playerData(player));
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			else if(commandLabel.equalsIgnoreCase("clearpt"))
			{
				FileConfiguration fc = this.plugin.playerFile(player);
				
				if(args.length >= 0)
				{
					fc.set("pt", null);
					try {
						fc.save(this.plugin.playerData(player));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					player.sendMessage(ChatColor.GRAY + "Your PTs have been cleared.");
					return true;
				}	
			}
		}
		return false;
	}
}

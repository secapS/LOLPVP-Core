package com.lolpvp.signs;

import java.text.NumberFormat;
import java.util.Locale;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.lolpvp.core.Core;

public class BuySigns implements Listener, CommandExecutor
{
	Core plugin;
	public BuySigns(Core instance)
	{
		this.plugin = instance;
	}
	
	SignSettingsManager settings = SignSettingsManager.getInstance();
	
	@EventHandler
	public void onBreak(BlockBreakEvent event)
	{
		Player player;
		if ((event.getPlayer().hasPermission("lol.breaksign")) && (
				(event.getBlock().getType().equals(Material.SIGN_POST)) || (event.getBlock().getType().equals(Material.WALL_SIGN))))
		{
			player = event.getPlayer();
			Block b = event.getBlock();
			Sign sign = (Sign)b.getState();
			for (String i : this.settings.getData().getConfigurationSection("signs.").getKeys(false))
			{
				if (player.isSneaking() && sign.getLine(0).equals(ChatColor.AQUA + "-Buy-") && sign.getLine(1).equalsIgnoreCase(i))
				{
					this.settings.getData().set("signs." + i, null);
					this.settings.saveData();
					player.sendMessage(ChatColor.GREEN + "You have deleted a command sign");
				}
			}
		}
	}

	@EventHandler
	public void onBuy(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if ((event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && (
				(event.getClickedBlock().getType().equals(Material.SIGN_POST)) || (event.getClickedBlock().getType().equals(Material.WALL_SIGN))))
		{
			Sign sign = (Sign)event.getClickedBlock().getState();
			if (sign.getLine(0).equalsIgnoreCase(ChatColor.AQUA + "-Buy-"))
			{
				event.setCancelled(true);
				for (String i : this.settings.getData().getConfigurationSection("signs.").getKeys(false))
				{
					if (sign.getLine(1).equalsIgnoreCase(i))
					{
						String s = this.settings.getData().getString("signs." + i + ".command");
						if (!player.hasPermission("lol.bypass.qf"))
						{
							if (com.lolpvp.utils.Cooldowns.tryCooldown(player, "sign", 1000))
							{
								EconomyResponse r = Core.getEconomy().withdrawPlayer(player, this.settings.getData().getDouble("signs." + i + ".price"));
								if (r.transactionSuccess())
								{
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s.replace("/", "").replace("{name}", player.getName()));
									player.sendMessage(ChatColor.GRAY + "You have completed a transaction for $" + ChatColor.AQUA + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(this.settings.getData().getDouble("signs." + i + ".price"))).replaceAll(",", ChatColor.GRAY + "," + ChatColor.AQUA) + ChatColor.GRAY + ".");
									break;
								}
								player.sendMessage(ChatColor.RED + "Insufficient funds.");
								break;
							}
						}
						else
						{
							EconomyResponse r = Core.getEconomy().withdrawPlayer(player, this.settings.getData().getDouble("signs." + i + ".price"));
							if (r.transactionSuccess())
							{
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s.replace("/", "").replace("{name}", player.getName()));
								player.sendMessage(ChatColor.GRAY + "You have completed a transaction for $" + ChatColor.AQUA + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(this.settings.getData().getDouble("signs." + i + ".price"))).replaceAll(",", ChatColor.GRAY + "," + ChatColor.AQUA) + ChatColor.GRAY + ".");
								break;
							}
							player.sendMessage(ChatColor.RED + "Insufficient funds.");
							break;
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if ((commandLabel.equalsIgnoreCase("qf")) && 
				((sender instanceof Player)))
		{
		final	Player player = (Player)sender;
			if (!player.hasPermission("lolpvp.signs"))
			{
				player.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				return true;
			}
			if (args.length <= 1)
			{
				player.sendMessage(ChatColor.RED + "Usage: /qf <price> <sign name> <command>");
				return true;
			}
			if ((player.getTargetBlock(null, 6).getType().equals(Material.SIGN)) || (player.getTargetBlock(null, 6).getType().equals(Material.SIGN_POST)) || (player.getTargetBlock(null, 6).getType().equals(Material.WALL_SIGN))) {
				try
				{
					final int i = Integer.parseInt(args[0]);
					StringBuilder sb = new StringBuilder();
					for (int k = 2; k <= args.length - 1; k++) {
						sb.append(args[k]).append(" ");
					}
					final Sign sign = (Sign) player.getTargetBlock(null, 6).getState();
					final String string = args[1].toLowerCase();
					setSignStrings(sign, string, i);
					sign.update();
					this.settings.getData().set("signs." + string + ".price", Integer.valueOf(i));
					this.settings.getData().set("signs." + string + ".command", sb.toString());
					this.settings.saveData();
					player.sendMessage(ChatColor.GREEN + "Created a buy sign at a price of " + Integer.toString(i));
				}
				catch (Exception e)
				{
					e.printStackTrace();
					player.sendMessage(ChatColor.RED + "Usage: /qf <price> <sign name> <command>");
					return true;
				}
			}
			else
			{
				player.sendMessage(ChatColor.RED + "You must look at a sign");
			}
		}
		return true;
	}
	
	public void setSignStrings(Sign sign, String name, int price)
	{
		sign.setLine(0, ChatColor.AQUA + "-Buy-");
		sign.setLine(1, name.toUpperCase());
		sign.setLine(2, "$" + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(price)));
	}
}

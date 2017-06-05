package com.lolpvp.votifier;

import java.io.File;
import java.io.IOException;

import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.lolpvp.core.Core;
import com.lolpvp.utils.UUIDLibrary;

public class VotifierListener implements Listener, CommandExecutor
{
	private Core plugin;

	public VotifierListener(Core plugin)
	{
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if ((cmd.getName().equalsIgnoreCase("votes")) && 
				((sender instanceof Player)))
		{
			Player player = (Player)sender;
			if ((!player.hasPermission("lolpvp.votes")) && (!player.hasPermission("lolpvp.*")) && (!player.isOp()))
			{
				player.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				return true;
			}
			FileConfiguration fc = this.plugin.playerFile(player);
			switch (args.length)
			{
			case 0: 
//				10 votes - 10 diamonds
//				25 votes - $10,000 ingame money
//				50 votes - 100 diamonds
//				75 votes - $100,000 ingame money
//				100 votes - 50 op apples
//				125 votes - $25 donation voucher
				player.sendMessage(ChatColor.AQUA + "10" + ChatColor.GRAY + " votes: " + ChatColor.AQUA + "10 diamonds.");
				player.sendMessage(ChatColor.AQUA + "25" + ChatColor.GRAY + " votes: " + ChatColor.AQUA + "$10,000 ingame money.");
				player.sendMessage(ChatColor.AQUA + "50" + ChatColor.GRAY + " votes: " + ChatColor.AQUA + "100 diamonds.");
				player.sendMessage(ChatColor.AQUA + "75" + ChatColor.GRAY + " votes: " + ChatColor.AQUA + "$100,000 ingame money.");
				player.sendMessage(ChatColor.AQUA + "100" + ChatColor.GRAY + " votes: " + ChatColor.AQUA + "50 OP Apples.");
				player.sendMessage(ChatColor.AQUA + "125" + ChatColor.GRAY + " votes: " + ChatColor.AQUA + "$25 donation voucher.");
				int i = fc.getInt("votes");
				player.sendMessage(ChatColor.GRAY + "Total votes for this month: " + ChatColor.AQUA + Integer.toString(i));
				return true;
			case 1:
				if (args[0].equalsIgnoreCase("top"))
				{
					this.plugin.getVotesTop().onSort(player);
					player.sendMessage("test");
					return true;
				}
				if (player.hasPermission("lolpvp.votes.others"))
				{
					if (UUIDLibrary.getUUIDFromName(args[0]) != null)
					{
						FileConfiguration fc2 = this.plugin.playerFile(UUIDLibrary.getUUIDFromName(args[0]));
						int v = fc2.getInt("votes");
						player.sendMessage(ChatColor.AQUA + UUIDLibrary.getExactName(args[0]) + "'s " + ChatColor.GRAY + "total votes for this month: " + ChatColor.AQUA + Integer.toString(v));
					}
					else
					{
						player.sendMessage(ChatColor.RED + "Could not find player: " + args[0]);
					}
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				}
				break;
			default: 
				player.sendMessage(ChatColor.RED + "Usage: /votes or /votes <player>");
			}
		}
		if ((cmd.getName().equalsIgnoreCase("resetvotes")) && 
				((sender instanceof Player)))
		{
			Player player = (Player)sender;
			if (!player.isOp())
			{
				player.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				return true;
			}
			if (args.length >= 0)
			{
				player.sendMessage(ChatColor.GREEN + "Everyone's votes has been reset.");
				String path = this.plugin.getDataFolder().getAbsolutePath();
				String plugins = path.substring(0, path.lastIndexOf(File.separator));
				File users = new File(plugins + File.separator + "LOLPVP", "userdata2");
				if (users.exists()) {
					for (File file : users.listFiles())
					{
						YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
						try
						{
							config.set("votes", Integer.valueOf(0));
							config.set("pending-commands", null);
							config.save(file);
						}
						catch (IOException ex)
						{
							ex.printStackTrace();
						}
					}
				}
			}
		}
		return true;
	}

	public void add(Player player, ItemStack ii)
	{
		if (player.getInventory().firstEmpty() == -1)
		{
			int zip = 0;
			for (ItemStack o : player.getInventory().getContents())
			{
				int l = o.getAmount();
				zip++;
				if ((o.getType().equals(ii.getType())) && (o.getAmount() < ii.getMaxStackSize()))
				{
					player.getInventory().addItem(new ItemStack[] { ii });
					break;
				}
				if ((zip == 36) && (l != l + 1))
				{
					player.getWorld().dropItemNaturally(player.getLocation(), ii);
					break;
				}
			}
		}
		else
		{
			player.getInventory().addItem(new ItemStack[] { ii });
		}
	}

	@EventHandler
	public void onJoin(final PlayerJoinEvent event)
	{
		final Player player = event.getPlayer();
		final FileConfiguration fc = this.plugin.playerFile(event.getPlayer());
		if (fc.getString("votes") == null) {
			fc.set("votes", Integer.valueOf(0));
		}
		fc.set("uuid", player.getUniqueId().toString());
		if (fc.getStringList("pending-command") != null) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
                for (String s : fc.getStringList("pending-command"))
                {
                    String[] parts = s.split(":");
                    if (parts[0].equalsIgnoreCase("give"))
                    {
                        String[] p = parts[1].split(";");
                        int item = Integer.parseInt(p[1]);
                        int data = Integer.parseInt(parts[1]);
                        int amount = Integer.parseInt(parts[2]);
                        ItemStack ii = new ItemStack(Material.getMaterial(item), amount, (short)data);
                        VotifierListener.this.add(player, ii);
                    }
                    else if (parts[0].equalsIgnoreCase("send"))
                    {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', parts[1]));
                    }
                    else if (parts[0].equalsIgnoreCase("money"))
                    {
                        Core.getEconomy().depositPlayer(player, Double.parseDouble(parts[1]));
                    }
                }
                fc.set("pending-command", null);
                try
                {
                    fc.save(VotifierListener.this.plugin.playerData(event.getPlayer()));
                }
                catch (IOException exception)
                {
                    exception.printStackTrace();
                }
            }, 40L);
		}
		try
		{
			fc.save(this.plugin.playerData(event.getPlayer()));
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}

	public void load(String name)
	{
		FileConfiguration fc = this.plugin.playerFile(UUIDLibrary.getUUIDFromName(name));
		if (fc.get("votes") == null) {
			fc.set("votes", Integer.valueOf(1));
		}
		else {
			this.plugin.getVotesTop().votes.put(name, Integer.valueOf(fc.getInt("votes") + 1));
			fc.set("votes", Integer.valueOf(fc.getInt("votes") + 1));
		}
		if (this.plugin.getConfig().getStringList("votes." + fc.getInt("votes") + "-votes") != null) {
			Player player = Bukkit.getServer().getPlayer(name);
			giveReward(player);
		}
		try
		{
			fc.save(this.plugin.playerData(UUIDLibrary.getUUIDFromName(name)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void giveReward(Player player) {
        FileConfiguration fc = this.plugin.playerFile(UUIDLibrary.getUUIDFromName(player.getName()));
        if (player != null) {
            for (String rewardCommands : this.plugin.getConfig().getStringList("votes." + fc.getInt("votes") + "-votes")) {
                String command = rewardCommands.replace("{PLAYER}", player.getName());
                this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), command);
            }
        } else {
            fc.set("pending-command", this.plugin.getConfig().getStringList("votes." + fc.getInt("votes") + "-votes"));
        }
    }

	@EventHandler(priority=EventPriority.NORMAL)
	public void onVote(VotifierEvent event)
	{
		load(event.getVote().getUsername());
	}
}
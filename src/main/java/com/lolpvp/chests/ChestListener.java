package com.lolpvp.chests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.lolpvp.core.Core;

public class ChestListener implements Listener
{
	Core plugin;
	public ChestListener(Core instance)
	{
		this.plugin = instance;
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event)
	{
		if(event.getPlayer() instanceof Player)
		{
			Player player = (Player)event.getPlayer();
			String name = event.getInventory().getTitle();
			if(Chest.in.containsKey(player.getName())
					&& name.equals(ChatColor.RED + "Set PVP Chest"))
			{
				ChestManager.getInstance().saveChest(event.getInventory(), ((Integer)Chest.in.get(player.getName())).intValue(), (String)Chest.name.get(player.getName()));
				player.sendMessage(ChatColor.GRAY + "Successfully saved " + ChatColor.AQUA + ChatColor.AQUA + (String)Chest.name.get(player.getName()) + ChatColor.GRAY + " ID: " + ChatColor.AQUA + Integer.toString(((Integer)Chest.in.get(player.getName())).intValue()));
			}
		}
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Action a = event.getAction();
		String nn;
		if ((a.equals(Action.RIGHT_CLICK_BLOCK)) || (a.equals(Action.RIGHT_CLICK_AIR)))
		{
			ItemStack i = player.getItemInHand();
			if ((i != null) && 
					(!i.getType().equals(Material.AIR)) && 
					(i.hasItemMeta()) && 
					(i.getItemMeta().hasLore()) && 
					(i.getItemMeta().hasDisplayName()) && 
					(i.getType().equals(Material.CHEST)))
			{
				event.setCancelled(true);
				String[] parts = i.getItemMeta().getDisplayName().split(" ");
				nn = ChatColor.stripColor(parts[0]);
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GRAY + "Right click to unwrap!");
				FileConfiguration fc = this.plugin.pvpFile();
				boolean passed = false;
				if (fc.getConfigurationSection("chests.") != null)
				{
					for (String s : fc.getConfigurationSection("chests.").getKeys(false)) {
						if (s.equalsIgnoreCase(nn)) {
							passed = true;
						}
					}
					if (passed && 
							i.getItemMeta().getLore().equals(lore))
					{
						runThis(player, nn);
						Bukkit.getServer().broadcastMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + " just opened a " + nn + " Chest!");
						Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "Get YOURS now at " + ChatColor.AQUA + "http://buy.LOLPVP.com" + ChatColor.YELLOW + "!");
					}
				}
			}
		}
	}

	public void runThis(Player player, String string)
	{
		ItemStack i = new ItemStack(Material.getMaterial(player.getItemInHand().getType().name()), 1);
		ItemMeta si = i.getItemMeta();
		si.setDisplayName(player.getItemInHand().getItemMeta().getDisplayName());
		si.setLore(player.getItemInHand().getItemMeta().getLore());
		i.setItemMeta(si);
		player.getInventory().removeItem(new ItemStack[] { i });
		FileConfiguration fc = this.plugin.pvpFile();
		ArrayList<Integer> in = new ArrayList<Integer>();
		for (String s : fc.getConfigurationSection("chests." + string.toLowerCase()).getKeys(false)) {
			in.add(Integer.valueOf(Integer.parseInt(s)));
		}
		Random random = new Random();
		int f = random.nextInt(in.size());
		for (String ss : fc.getConfigurationSection("chests." + string.toLowerCase() + "." + Integer.toString(((Integer)in.get(f)).intValue())).getKeys(false))
		{
			ItemStack item = fc.getItemStack("chests." + string.toLowerCase() + "." + in.get(f) + "." + ss);
			if (player.getInventory().firstEmpty() == -1)
			{
				int zip = 0;
				for (ItemStack o : player.getInventory().getContents())
				{
					int l = o.getAmount();
					zip++;
					if ((o.getType().equals(item.getType())) && (o.getAmount() < item.getMaxStackSize()))
					{
						player.getInventory().addItem(new ItemStack[] { fc.getItemStack("chests." + string.toLowerCase() + "." + in.get(f) + "." + ss) });
						break;
					}
					if ((zip == 36) && (l != l + 1))
					{
						player.getWorld().dropItemNaturally(player.getLocation(), item);
						break;
					}
				}
			}
			else
			{
				player.getInventory().addItem(new ItemStack[] { fc.getItemStack("chests." + string.toLowerCase() + "." + in.get(f) + "." + ss) });
			}
		}
		player.updateInventory();
	}
}
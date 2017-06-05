package com.lolpvp.virtualchest;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class VirtualChestListener implements Listener
{
	@EventHandler
	public void onClose(final InventoryCloseEvent event)
	{
		if(event.getPlayer() instanceof Player)
		{
			if(VirtualChestManager.getInstance().in.contains(event.getPlayer().getName()))
			{
				VirtualChestManager.getInstance().save(event.getInventory());
				VirtualChestManager.getInstance().in.remove(event.getPlayer().getName());
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player)event.getWhoClicked();
		if(event.getInventory().getName().equals(ChatColor.RED + player.getName())) {
			if(event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT || event.getClick() == ClickType.NUMBER_KEY) {
				if(!(event.getCurrentItem().getType() == Material.POTION)) {
					event.setCancelled(true);		
				}
			}	
		}
	}
}

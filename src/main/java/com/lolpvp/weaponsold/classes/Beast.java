package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.lolpvp.core.Core;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class Beast extends BallerItem
{

	public Beast() 
	{
		super(ChatColor.AQUA + "The Beast", Material.DIAMOND_AXE, 1, lore(), enchantments(), "thebeast", "beast");
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockClick(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if(ItemManager.getInstance().isBallerItem(player.getItemInHand(), this)
				&& event.getAction().equals(Action.LEFT_CLICK_BLOCK)
				&& !event.getClickedBlock().getType().equals(Material.BEDROCK)
				&& !event.isCancelled()
				&& !event.getClickedBlock().getType().equals(Material.CHEST)
				&& !event.getClickedBlock().getType().equals(Material.TRAPPED_CHEST)
				&& Core.canBuildHere(player, event.getClickedBlock()))
		{
			 player.getWorld().playEffect(event.getClickedBlock().getLocation(), Effect.STEP_SOUND, event.getClickedBlock().getType().getId());
		      event.getClickedBlock().breakNaturally();
		}
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Instant Break I");
			this.add(ChatColor.DARK_GRAY + "An all-in-one tool that breaks any block instantly!");
		}};
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DAMAGE_ALL, 5);
			this.put(Enchantment.DAMAGE_UNDEAD, 5);
			this.put(Enchantment.LOOT_BONUS_MOBS, 5);
		}};
	}	
}
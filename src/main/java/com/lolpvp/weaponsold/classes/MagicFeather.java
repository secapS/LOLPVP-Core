package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import com.lolpvp.core.Core;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class MagicFeather extends BallerItem
{
	public MagicFeather() 
	{
		super(ChatColor.AQUA + "Magic Feather", Material.FEATHER, 1, lore(), enchantments(), "magicfeather");
		// TODO Auto-generated constructor stub
	}
	
	ArrayList<UUID> players = new ArrayList<UUID>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) 
	{
		Player p = e.getPlayer();
		if (ItemManager.getInstance().isBallerItem(p.getItemInHand(), this) && !Core.isPlayerInPVP(p)) 
		{
			if (!players.contains(p.getUniqueId())) 
			{
				players.add(p.getUniqueId());
			}
			p.setAllowFlight(true);
			p.setFlying(true);
			p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.WOOL.getId());
		}
		if (players.contains(p.getUniqueId())) 
		{
			if (Core.isPlayerInPVP(p) || !ItemManager.getInstance().isBallerItem(p.getItemInHand(), this)) 
			{
				p.setFlying(false);
				p.setAllowFlight(false);
				players.remove(p.getUniqueId());
			}
		}
	}
	
	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent e) 
	{
		Player p = e.getPlayer();
		if (players.contains(p.getUniqueId()) || ItemManager.getInstance().isBallerItem(p.getItemInHand(), this)) 
		{
			e.setCancelled(true);
		}
	}

	@SuppressWarnings("serial")
	private static List<String> lore() 
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Flight I");
			this.add(ChatColor.DARK_GRAY + "Hold to fly. Only works in safezones.");
		}};
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.LOOT_BONUS_MOBS, 1);
		}};
	}
	
}
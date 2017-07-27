package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffectType;

import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class HealingShears extends BallerItem
{

	public HealingShears() {
		super(ChatColor.AQUA + "Healing Shears", Material.SHEARS, 1, lore(), enchantments(), "healingshears", "heal", "hs", "shears");
		// TODO Auto-generated constructor stub
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event)
	{
		if(event.getRightClicked() instanceof Player)
		{
			if(ItemManager.getInstance().isBallerItem(event.getPlayer().getItemInHand(), this))
			{
				Player target = (Player)event.getRightClicked();
				if(target.hasPotionEffect(PotionEffectType.INVISIBILITY))
				{
					target.removePotionEffect(PotionEffectType.INVISIBILITY);
					event.getPlayer().sendMessage(ChatColor.GRAY + "Removed invisibility potion effect from " + ChatColor.AQUA + target.getName());
				}	
			}
		}
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "Remove invisibility potion effect from a player.");
		}};
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
		}};
	}
}

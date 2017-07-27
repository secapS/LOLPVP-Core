package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.lolpvp.core.Core;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class BlindingRod extends BallerItem
{

	public BlindingRod() {
		super(ChatColor.AQUA + "Blinding Rod", Material.SHEARS, 300000, true, lore(), enchantments(), "blind", "rod", "br", "blindingrod");
		// TODO Auto-generated constructor stub
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event)
	{
		if(event.getDamager() instanceof Player)
		{
			if(event.getEntity() instanceof Player)
			{
				Player player = (Player)event.getDamager();
				Player otherPlayer = (Player)event.getEntity();
				if(Core.isPlayerInPVP(otherPlayer))
				{
					if(ItemManager.getInstance().isBallerItem(player.getItemInHand(), this) && !InvisRing.in.contains(player.getName()))
					{
						otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 4 * 20, 0));	
					}	
				}
			}
		}
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			
		}};
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Blindness I");
			this.add(ChatColor.DARK_GRAY + "Hit enemies to blind them");
		}};
	}
}

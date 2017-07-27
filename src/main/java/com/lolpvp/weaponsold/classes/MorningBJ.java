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

import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class MorningBJ extends BallerItem
{
	public MorningBJ() 
	{
		super(ChatColor.AQUA + "Morning BJ", Material.WOOD_SWORD, 700000, lore(), enchantments(), "morningbj", "mbj");
		// TODO Auto-generated constructor stub
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			if(event.getDamager() instanceof Player)
			{
				Player player = (Player)event.getDamager();
				if(ItemManager.getInstance().isBallerItem(player.getItemInHand(), this))
				{
					event.setDamage(event.getDamage() * 1.1);
				}	
			}
		}
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() {
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DAMAGE_ALL, 10);
			this.put(Enchantment.FIRE_ASPECT, 10);
		}};
	}

	@SuppressWarnings("serial")
	private static List<String> lore() {
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "The second wood tier weapon!");
		}};
	}
}

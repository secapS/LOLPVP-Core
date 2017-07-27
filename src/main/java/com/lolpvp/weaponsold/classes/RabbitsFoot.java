package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import com.lolpvp.core.Core;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.BallerItemTask;
import com.lolpvp.weaponsold.ItemManager;

public class RabbitsFoot extends BallerItem
{
	Core plugin;
	public RabbitsFoot(Core core)
	{
		super(ChatColor.AQUA + "Rabbits Foot", Material.DIAMOND_BOOTS, 1000000, lore(), enchantments(), "rabbitsfoot", "foot");
		this.plugin = core;
	}
	
	HashMap<String, BukkitTask> players = new HashMap<String, BukkitTask>();
	
	@EventHandler
	public void onClose(InventoryCloseEvent event)
	{
		if(event.getPlayer() instanceof Player)
		{
			Player player = (Player)event.getPlayer();
			
			if(player.getEquipment().getBoots() != null && ItemManager.getInstance().isBallerItem(player.getEquipment().getBoots(), this))
			{
				players.put(player.getName(), new BallerItemTask(player, this).runTaskTimer(this.plugin, 0L, 20L));	
			}
			else if(players.containsKey(player.getName()))
			{
				player.removePotionEffect(PotionEffectType.JUMP);
				players.get(player.getName()).cancel();
				players.remove(player.getName());
			}
		}
	}

	@SuppressWarnings("serial")
	private static List<String> lore() {
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Jump Boost III");
			this.add(ChatColor.DARK_GRAY + "A special Easter item!");
		}};
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() {
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
			this.put(Enchantment.PROTECTION_EXPLOSIONS, 5);
			this.put(Enchantment.PROTECTION_FALL, 5);
			this.put(Enchantment.PROTECTION_FIRE, 5);
			this.put(Enchantment.PROTECTION_PROJECTILE, 5);
		}};
	}
}

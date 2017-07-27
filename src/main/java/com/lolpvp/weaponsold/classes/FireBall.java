package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.confuser.barapi.BarAPI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class FireBall extends BallerItem
{

	public FireBall() 
	{
		super(ChatColor.AQUA + "Fire Ball", Material.FIREBALL, 1, lore(), enchantments(), "fireball");
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		
		if(ItemManager.getInstance().isBallerItem(player.getItemInHand(), this))
		{
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			{
				event.setCancelled(true);
				IceBlade.froze.remove(player.getName());
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, 0));
				player.setFireTicks(5 * 20);
				player.setWalkSpeed(0.2F);
				BarAPI.removeBar(player);
				if(player.getItemInHand().getAmount() > 1)
				{
					player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
				}
				else
				{
					player.setItemInHand(null);
				}
			}	
		}
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() 
	{
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.ARROW_FIRE, 1);
		}};
	}

	@SuppressWarnings("serial")
	private static List<String> lore() {
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "Right Click to heat yourself up after being frozen by an ice weapon!");
		}};
	}
}

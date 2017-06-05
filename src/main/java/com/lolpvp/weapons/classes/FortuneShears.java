package com.lolpvp.weapons.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.lolpvp.core.Core;
import com.lolpvp.weapons.BallerItem;
import com.lolpvp.weapons.ItemManager;

public class FortuneShears extends BallerItem 
{

	public FortuneShears()
	{
		super(ChatColor.AQUA + "Fortune Shears", Material.SHEARS, 1000000, lore(), enchantments(), "fortuneshears", "fortune");
		// TODO Auto-generated constructor stub
	}
	
	  @SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.NORMAL)
	  public void onBlock(BlockBreakEvent event)
	  {
		  Player player = event.getPlayer();
		  if (Core.canBuildHere(player, event.getBlock()) && 
				  ((event.getBlock().getType().equals(Material.LEAVES)) || (event.getBlock().getType().equals(Material.LEAVES_2))) && 
				  (ItemManager.getInstance().isBallerItem(player.getItemInHand(), this)))
		  {
			  Random random = new Random();
			  int b = random.nextInt(5);
			  if (b == 1)
			  {
				  event.setCancelled(true);
				  event.getBlock().setType(Material.AIR);
				  event.getBlock().getWorld().playEffect(event.getBlock().getLocation(), Effect.STEP_SOUND, 1, event.getBlock().getTypeId());
				  player.getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.APPLE, 1));
				  event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
			  }
		  }
	  }

	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		// TODO Auto-generated method stub
		return new ArrayList<String>()
		{{		
			this.add(ChatColor.DARK_GRAY + "A higher chance of getting apples from trees!");
		}};
	}

	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() 
	{
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DURABILITY, 1);
		}};
	}

}

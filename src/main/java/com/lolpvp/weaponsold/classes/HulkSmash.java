package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.lolpvp.core.Core;
import com.lolpvp.utils.Cooldowns;
import com.lolpvp.utils.ParticleEffect;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class HulkSmash extends BallerItem{

	public HulkSmash() 
	{
		super(ChatColor.AQUA + "Hulk Smash", Material.SLIME_BALL, 1, lore(), enchantments(), "hulksmash");
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() {
		// TODO Auto-generated method stub
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DURABILITY, 1);
		}};
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) 
	{
		Player p = e.getPlayer();
		
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			if(ItemManager.getInstance().isBallerItem(p.getItemInHand(), this))
			{
				if(Core.isPlayerInPVP(p))
				{
					if(Cooldowns.tryCooldown(p, "hulk", 5 * 1000))
					{
						p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 2f, 1f);
						List<Entity> victims = p.getNearbyEntities(10, 10, 10);
						for (final Entity victim: victims) 
						{
							if (victim instanceof LivingEntity) 
							{
								LivingEntity player = (LivingEntity) victim;
								player.setVelocity(new Vector(0,1.2,0));
							}
						}	
						
						int radius = 10;
						final Block block = p.getLocation().getBlock(); //placed block	
						
						for (int x = -(radius); x <= radius; x += 2)
						{
							for (int z = -(radius); z <= radius; z += 2)
							{
								if(!block.getRelative(x,-1,z).getType().equals(Material.AIR))
								{
									ParticleEffect.displayBlockCrack(block.getRelative(x,0,z).getLocation(), block.getRelative(x,-1,z).getTypeId(), block.getRelative(x,-1,z).getData(), 0f, 0f, 0f, 0f, 14);	
								}
							}
						}
					}
					else
					{
						Long lol = Long.valueOf(Cooldowns.getCooldown(p, "hulk"));
						int bbb = lol.intValue() / 1000;
						p.sendMessage(this.getName() + ChatColor.RED + " is on cooldown for " + Integer.toString(bbb) + " seconds.");
					}
				}
			}	
		}
	}

	
	
	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "Right click to smash!");
			this.add(ChatColor.DARK_GRAY + "Avengers: Age Of Ultron Collectable Item.");
		}};
	}
}

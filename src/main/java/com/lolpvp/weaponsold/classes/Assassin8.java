package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.lolpvp.utils.Cooldowns;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class Assassin8 extends BallerItem
{

	public Assassin8() 
	{
		super(ChatColor.AQUA + "Assassin8", Material.DIAMOND_SWORD, 10000000, lore(), enchantments(), "assassin8", "assassin");
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		if(ItemManager.getInstance().isBallerItem(event.getPlayer().getItemInHand(), this))
		{
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			{
				Block block = null;
				BlockIterator iterator = null;
				try
				{
					iterator = new BlockIterator(event.getPlayer(), 4);
				}
				catch(IllegalStateException e)
				{}
				while(iterator != null && iterator.hasNext())
				{
					block = iterator.next();
				}
				if(iterator != null && !iterator.hasNext() && block != null && !block.getType().equals(Material.AIR))
				{
					if(Cooldowns.tryCooldown(event.getPlayer(), "teleport", 1 * 1000))
					{
						List<Entity> near = event.getPlayer().getNearbyEntities(5, 5, 5);
						Player target = null;
						for(Entity e : near)
						{
							if(e instanceof Player)
							{	
								target = (Player)e;
								break;
							}
						}
						Location teleportLocation = block.getLocation();
						teleportLocation.setDirection(event.getPlayer().getLocation().getDirection());
						if(target != null)
							teleportLocation.setYaw(-Math.abs(target.getLocation().getYaw()));
						event.getPlayer().teleport(teleportLocation);
					}
					else
					{
						Long lol = Long.valueOf(Cooldowns.getCooldown(event.getPlayer(), "teleport"));
						int bbb = lol.intValue() / 1000;
						event.getPlayer().sendMessage(this.getName() + ChatColor.RED + " is on cooldown for " + Integer.toString(bbb));
					}
				}	
			}
		}
	}
	
	public void lookAt(Location tp, Location location) {
        tp.setYaw(getLocalAngle(new Vector(location.getBlockX(), 0, location.getBlockZ()), location.toVector()));
    }
	
	private final float getLocalAngle(Vector point1, Vector point2) 
	{
		double dx = point2.getX() - point1.getX();
		double dz = point2.getZ() - point1.getZ();
		float angle = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;
		if(angle < 0) {
			angle += 360.0F;
		}
		return angle;
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.GRAY + "Teleport VIII");
			this.add(ChatColor.DARK_GRAY + "The teleport sword!");
		}};
	}
	
	@SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments()
	{
		return new HashMap<Enchantment, Integer>()
		{{
			this.put(Enchantment.DAMAGE_ALL, 8);
			this.put(Enchantment.DAMAGE_ARTHROPODS, 8);
			this.put(Enchantment.DAMAGE_UNDEAD, 8);
			this.put(Enchantment.FIRE_ASPECT, 8);
			this.put(Enchantment.LOOT_BONUS_MOBS, 8);
			this.put(Enchantment.DURABILITY, 8);
		}};
	}
}
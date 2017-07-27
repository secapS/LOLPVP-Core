package com.lolpvp.weaponsold.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.confuser.barapi.BarAPI;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.lolpvp.core.Core;
import com.lolpvp.utils.Cooldowns;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;

public class ElectroMagnet extends BallerItem
{

	Core plugin;
	public ElectroMagnet(Core core) 
	{
		super(ChatColor.AQUA + "Electromagnet", Material.FLINT, 1, lore(), enchantments(), "electromagnet", "magnet");
		this.plugin = core;
	}
	
	List<String> chargeUp = new ArrayList<String>();
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		final Player player = event.getPlayer();
		if(ItemManager.getInstance().isBallerItem(player.getItemInHand(), this))
		{
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) 
					|| event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			{
				event.setCancelled(true);

				if(Cooldowns.tryCooldown(player, "magnet", 5 * 1000)) {
					chargeUp.add(player.getName());
					BarAPI.setMessage(player, ChatColor.RED + "Charging Magnet...");
					
					for(int i = 0; i <= 10; i++) {
						com.lolpvp.utils.ParticleEffect.ENCHANTMENT_TABLE.display(player.getLocation(), 0, 0, 0, 10f, 30);
					}
					
					new BukkitRunnable() {
						@Override
						public void run() {
							BarAPI.removeBar(player);
							chargeUp.remove(player.getName());
							player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_LARGE, 0);
							for(Entity e : player.getNearbyEntities(15, 15, 15)) {
								if(e instanceof LivingEntity) {
									ElectroMagnet.this.pullEntityToLocation(e, player.getLocation());
								}
							}
						}
					}.runTaskLater(this.plugin, 1 * 20L);
				}
			}	
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		this.chargeUp.remove(event.getPlayer().getName());
	}
	private void pullEntityToLocation(final Entity e, Location loc)
	{
		Location entityLoc = e.getLocation();

		entityLoc.setY(entityLoc.getY()+1.5);
		e.teleport(entityLoc);

		double g = -0.02;
		double d = loc.distance(entityLoc);
		double t = d;
		double v_x = (1.0+0.1*t) * (loc.getX()-entityLoc.getX())/t;
		double v_y = (1.0+0.03*t) * (loc.getY()-entityLoc.getY())/t -0.5*g*t;
		double v_z = (1.0+0.1*t) * (loc.getZ()-entityLoc.getZ())/t;

		Vector v = e.getVelocity();
		v.setX(v_x);
		v.setY(v_y);
		v.setZ(v_z);
		e.setVelocity(v);
	}
	
	@SuppressWarnings("serial")
	private static List<String> lore()
	{
		return new ArrayList<String>()
		{{
			this.add(ChatColor.DARK_GRAY + "Star Lord's Electromagnet.");
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

package com.stirante.MoreProjectiles;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent;
import com.stirante.MoreProjectiles.event.CustomProjectileHitEvent.HitType;
import com.stirante.MoreProjectiles.projectile.BlockProjectile;
import com.stirante.MoreProjectiles.projectile.CustomProjectile;
import com.stirante.MoreProjectiles.projectile.ItemProjectile;
import com.stirante.MoreProjectiles.projectile.OrbProjectile;
import com.stirante.MoreProjectiles.projectile.ProjectileScheduler;

/**
 * Simple test class.
 */
public class MainTest extends JavaPlugin implements Listener {
    
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.hasItem() && e.getItem().getType() == Material.GOLD_AXE) {
            CustomProjectile p = new ItemProjectile("randomNameItem", e.getPlayer(), new ItemStack(Material.values()[(int) (Material.values().length * Math.random())]), 1F);//creates item projectile from random item stack
            p.addTypedRunnable(new TypedRunnable<ItemProjectile>() {
                @Override
                public void run(ItemProjectile o) {
                    Particles.HAPPY_VILLAGER.display(o.getEntity().getLocation(), 0, 0, 0, 0, 1);
                }
            });
            e.setCancelled(true);
        }
        if (e.hasItem() && e.getItem().getType() == Material.GOLD_HOE) {
            new BlockProjectile("randomNameBlock", e.getPlayer(), 1, 0, 1F);//creates block projectile from stone block
            e.setCancelled(true);
        }
        if (e.hasItem() && e.getItem().getType() == Material.GOLD_SPADE) {
            CustomProjectile p = new OrbProjectile("randomNameOrb", e.getPlayer(), 1.0F);//creates exp orb projectile
            p.addTypedRunnable(new TypedRunnable<OrbProjectile>() {
                @Override
                public void run(OrbProjectile o) {
                    Particles.HAPPY_VILLAGER.display(o.getEntity().getLocation(), 0, 0, 0, 0, 1);
                }
            });
            e.setCancelled(true);
        }
        if (e.hasItem() && e.getItem().getType() == Material.GOLD_PICKAXE) {
            new ProjectileScheduler("randomName", e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.LEASH_HITCH), e.getPlayer(), 1.0F, this);
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onHit(CustomProjectileHitEvent e) {
        if (e.getHitType() == HitType.ENTITY) {
            e.getHitEntity().damage(3D, e.getProjectile().getShooter());//if projectile hit entity, it damages to this entity
        }
        Particles.CRIT.display(e.getProjectile().getEntity().getLocation(), 0, 0, 0, 0.2F, 20);//plays crit particle at projectile location
    }
    
}

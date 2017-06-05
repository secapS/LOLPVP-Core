package com.lolpvp.utils;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class LocationUtils
{
	public static void runHelix(Location loc) {
		   
        double radius = 5;
   
        for (double y = 5; y >= 0; y -= 0.007) {
            radius = y / 3;
            double x = radius * Math.cos(3 * y);
            double z = radius * Math.sin(3 * y);
       
            double y2 = 5 - y;
       
            Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
            ParticleEffect.RED_DUST.display(loc2, 0, 0, 0, 0, 1);
        }
   
        for (double y = 5; y >= 0; y -= 0.007) {
            radius = y / 3;
            double x = -(radius * Math.cos(3 * y));
            double z = -(radius * Math.sin(3 * y));
       
            double y2 = 5 - y;
       
            Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
            ParticleEffect.RED_DUST.display(loc2, 0, 0, 0, 0, 1);
        }
    }
	
	public static ArrayList<Block> sphere(final Location center, final int radius) {
	    ArrayList<Block> sphere = new ArrayList<Block>();
	    for (int Y = -radius; Y < radius; Y++)
	      for (int X = -radius; X < radius; X++)
	         for (int Z = -radius; Z < radius; Z++)
	            if (Math.sqrt((X * X) + (Y * Y) + (Z * Z)) <= radius) {
	               final Block block = center.getWorld().getBlockAt(X + center.getBlockX(), Y + center.getBlockY(), Z + center.getBlockZ());
	               sphere.add(block);
	            }
	return sphere;
	}
}

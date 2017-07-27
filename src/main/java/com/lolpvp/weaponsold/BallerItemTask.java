package com.lolpvp.weaponsold;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.lolpvp.utils.ParticleEffect;
import com.lolpvp.weaponsold.classes.IronManSuit;
import com.lolpvp.weaponsold.classes.SnowArmor;

public class BallerItemTask extends BukkitRunnable
{
	private Player player;
	private BallerItem item;
	private List<Player> players;
	public BallerItemTask(Player player, BallerItem item)
	{
		this.player = player;
		this.item = item;
	}
	
	public BallerItemTask(ArrayList<Player> players, BallerItem item)
	{
		this.players = players;
		this.item = item;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub 
		if(item.equals(ItemManager.getInstance().getItemByName("rabbitsfoot")))
		{
			if(player.getEquipment().getBoots() != null && ItemManager.getInstance().isBallerItem(player.getEquipment().getBoots(), ItemManager.getInstance().getItemByName("rabbitsfoot")))
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2));
			}
			else
			{
				this.cancel();
			}
		}
		else if(item.equals(ItemManager.getInstance().getItemByName("snowarmor")))
		{
			SnowArmor snowarmor = (SnowArmor)item;
			if(snowarmor.isSnowArmor(player.getEquipment().getArmorContents()))
			{
				ParticleEffect.SNOW_SHOVEL.display(player.getLocation().add(new Vector(0, 1, 0)), 0.0f, 1.0f, 0.0f, 1, 10);
			}
			else
			{
				this.cancel();
			}
		}
		else if(item.equals(ItemManager.getInstance().getItemByName("ironmansuit")))
		{
			IronManSuit ironManSuit = (IronManSuit)item;
			if(ironManSuit.isWearingSuit(player.getEquipment().getArmorContents()))
			{
				ParticleEffect.displayBlockCrack(player.getLocation(), 51, (byte) 0, 0f, 0f, 0f, 0f, 10);
			}
			else
			{
				this.cancel();
			}
		}
		else if(item.equals(ItemManager.getInstance().getItemByName("bunnyears")))
		{
			if(player.getEquipment().getHelmet() != null && ItemManager.getInstance().isBallerItem(player.getEquipment().getHelmet(), ItemManager.getInstance().getItemByName("bunnyears")))
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2));
			}
			else
			{
				this.cancel();
			}
		}
		else if(item.equals(ItemManager.getInstance().getItemByName("snowman")))
		{
			for(Player p : players)
			{
				for(int i = 0; i < 20; i++)
				{
					p.getWorld().playEffect(p.getLocation().add(0.0, 1.0, 0.0), Effect.STEP_SOUND, 80);
					p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, 80);
				}
			}
		}
	}

}

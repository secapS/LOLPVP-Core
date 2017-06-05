package com.lolpvp.weapons.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.lolpvp.core.Core;
import com.lolpvp.utils.Cooldowns;
import com.lolpvp.weapons.BallerItem;
import com.lolpvp.weapons.ItemManager;

/**
 * Created by Luke on 6/16/2015.
 */
public class LOLSword extends BallerItem implements CommandExecutor
{
    Core plugin;
    public LOLSword(Core instance)
    {
        super(ChatColor.AQUA + "lolitssword", Material.DIAMOND_SWORD, 1, lore(), enchantments(), "lolsword");
        this.plugin = instance;
    }
    
    ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (cmd.getName().equals("lolsword"))
        {
            if (sender instanceof Player)
            {
                Player player = (Player) sender;
          
                if(args.length == 0)
                {
                	 if (player.getName().startsWith("lolits"))
                     {
                         if(!this.plugin.playerFile(player).getBoolean("lolsword-taken"))
                         {
                             ItemManager.getInstance().giveItem(player, this);
                             player.sendMessage(ChatColor.AQUA + "Here is your LOL Sword!");
                             FileConfiguration fc = this.plugin.playerFile(player);
                             fc.set("lolsword-taken", Boolean.valueOf(true));
                             try {
     							fc.save(this.plugin.playerData(player));
     						} catch (IOException e) {
     							// TODO Auto-generated catch block
     							e.printStackTrace();
     						}
                             return true;
                         }
                         else
                         {
                             player.sendMessage(ChatColor.RED + "You already took a LOL Sword!");
                             return true;
                         }
                     } else {
                         player.sendMessage(ChatColor.RED + "You are not allowed to take the LOL Sword.");
                     }	
                }
                else if(args[0].equalsIgnoreCase("reset"))
                {
                	if(player.hasPermission("lolpvp.reset-lolsword"))
                	{
                		FileConfiguration fc = this.plugin.playerFile(player);
                		fc.set("lolsword-taken", null);
                		try {
                			fc.save(this.plugin.playerData(player));
                		} catch (IOException e) {
                			// TODO Auto-generated catch block
                			e.printStackTrace();
                		}
                		player.sendMessage(ChatColor.RED + "Reset.");
                	}
                }
            }
        }
        return false;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        //Add a check for if they have already taken the LOL Sword with a cooldown so they don't keep getting messaged.
        if (e.getPlayer().getName().startsWith("lolits") && !this.plugin.playerFile(e.getPlayer()).getBoolean("lolsword-taken"))
        {
        	e.getPlayer().sendMessage(ChatColor.GRAY + "-----------------------");
            e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Type /lolsword for a free sword! This is available exclusively for players whose names start with \"lolits\"!");
            FileConfiguration fc = this.plugin.playerFile(e.getPlayer());
            fc.set("lolsword-taken", Boolean.valueOf(false));
            try {
				fc.save(this.plugin.playerData(e.getPlayer()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }
    
    @EventHandler
	public void onPlayerInteractPlayer(PlayerInteractEntityEvent e) 
	{
		if (e.getRightClicked() instanceof Player) 
		{
			Player p = e.getPlayer();
			Player clicked = (Player) e.getRightClicked();
			
			if (ItemManager.getInstance().isBallerItem(p.getItemInHand(), this)) 
			{
				if(e.getPlayer().getName().startsWith("lolits"))
				{
					if(Cooldowns.tryCooldown(p, "lolsword", 5 * 1000))
					{
						effects.add(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
						effects.add(new PotionEffect(PotionEffectType.CONFUSION, 100, 0));
						effects.add(new PotionEffect(PotionEffectType.POISON, 100, 0));
						effects.add(new PotionEffect(PotionEffectType.SLOW, 100, 0));
						effects.add(new PotionEffect(PotionEffectType.WITHER, 100, 0));
						effects.add(new PotionEffect(PotionEffectType.WEAKNESS, 100, 0));
						Random r = new Random();
						PotionEffect poteffect = effects.get(r.nextInt(effects.size()));
						clicked.addPotionEffect(poteffect);
					}
					else
					{
						Long lol = Long.valueOf(Cooldowns.getCooldown(p, "lolsword"));
						int bbb = lol.intValue() / 1000;
						p.sendMessage(this.getName() + ChatColor.RED + " is on cooldown for " + Integer.toString(bbb) + " seconds.");
					}	
				}
				else
				{
					p.sendMessage(ChatColor.RED + "You cannot use this sword! You need to have lolits in your name!");
				}
			}
		}
	}

    @SuppressWarnings("serial")
	private static List<String> lore() {
        return new ArrayList<String>()
        {{
        	this.add(ChatColor.GRAY + "Random Effect I");
            this.add(ChatColor.DARK_GRAY + "A special sword given only to those who bear the lolits name.");
        }};
    }

    @SuppressWarnings("serial")
	private static HashMap<Enchantment, Integer> enchantments() {
        return new HashMap<Enchantment, Integer>()
        {{
            this.put(Enchantment.DAMAGE_ALL, 8);
        }};
    }
}

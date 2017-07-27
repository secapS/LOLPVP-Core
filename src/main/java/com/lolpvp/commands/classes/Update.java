package com.lolpvp.commands.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.lolpvp.commands.SubCommand;
import com.lolpvp.weaponsold.ItemManager;
import com.lolpvp.weaponsold.classes.Prot4;
import com.lolpvp.weaponsold.classes.Prot4AE;
import com.lolpvp.weaponsold.classes.Prot5;
import com.lolpvp.weaponsold.classes.Prot5AE;
import com.lolpvp.weaponsold.classes.Prot6;
import com.lolpvp.weaponsold.classes.Prot6AE;

public class Update extends SubCommand {

	public Update() {
		super("update", "Hold baller item in hand to update.", new String[]{"u", "up"});
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			if(player.getItemInHand() == null)
			{
				player.sendMessage(ChatColor.RED + "There is no Baller Item in your hand.");
			}
			else
			{
				ItemStack hand = player.getItemInHand();
				if(player.getItemInHand().getType().equals(Material.DIAMOND_SWORD))
				{
					
					if(player.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 5
							&& player.getItemInHand().containsEnchantment(Enchantment.DAMAGE_UNDEAD)
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD) == 5
							&& player.getItemInHand().containsEnchantment(Enchantment.KNOCKBACK)
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK) == 2
							&& player.getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS)
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) == 3
							&& player.getItemInHand().containsEnchantment(Enchantment.FIRE_ASPECT)
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.FIRE_ASPECT) == 2)
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("excal")));
					
					if(player.getItemInHand().containsEnchantment(Enchantment.KNOCKBACK)
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.KNOCKBACK) == 10)
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("daterapist")));
					
					if(loreContainsText(player.getItemInHand(), ChatColor.GRAY + "Poison VIII"))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("ejac")));
					
					else if(loreContainsText(player.getItemInHand(), ChatColor.GRAY + "Launcher I"))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("wb")));
					
					else if(loreContainsText(player.getItemInHand(), ChatColor.GRAY + "Invisibility I"))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("ms")));	
					
					else if(loreContainsText(player.getItemInHand(), ChatColor.GRAY + "Snowstorm I"))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("snowman")));
					
					if(player.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ARTHROPODS) 
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS) == 8)
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("ejac")));
					
					if(this.itemHasEnchantAndLevel(player.getItemInHand(), Enchantment.ARROW_INFINITE, 1))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("ms")));
					
					if(this.itemHasEnchantAndLevel(player.getItemInHand(), Enchantment.PROTECTION_FALL, 2))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("wb")));
				}
				
				if(player.getItemInHand().getType().equals(Material.GOLD_SWORD))
				{
					if(loreContainsText(player.getItemInHand(), ChatColor.GRAY + "Resistance I"))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("babymaker")));
					
					if(player.getItemInHand().containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL) 
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 1)
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("babymaker")));
				}
				
				if(player.getItemInHand().getType().equals(Material.WOOD_SWORD))
				{
					if(loreContainsText(player.getItemInHand(), ChatColor.GRAY + "Launcher I"))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("wildturkey")));
					
					if(player.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL) 
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 10
							&& player.getItemInHand().containsEnchantment(Enchantment.DURABILITY)
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY) == 10
							&& !loreContainsText(player.getItemInHand(), "Launcher I"))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("morningwood")));
					
					if(player.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL) 
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 10
							&& player.getItemInHand().containsEnchantment(Enchantment.FIRE_ASPECT)
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.FIRE_ASPECT) == 10)
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("morningbj")));
				}
				
				if(player.getItemInHand().getType().equals(Material.DIAMOND_AXE)
						&& this.itemHasEnchantAndLevel(player.getItemInHand(), Enchantment.DAMAGE_ALL, 10))
					player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("battleaxe")));					
					
				if(player.getItemInHand().getType().equals(Material.WOOD_AXE))
					if(player.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 10
							&& player.getItemInHand().containsEnchantment(Enchantment.FIRE_ASPECT)
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.FIRE_ASPECT) == 5
							&& player.getItemInHand().containsEnchantment(Enchantment.DURABILITY)
							&& player.getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY) == 10)
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("sparringaxe")));
				
				if(player.getItemInHand().getType().equals(Material.BOW))
				{
					if(loreContainsText(player.getItemInHand(), ChatColor.GRAY + "Poison I")
							&& loreContainsText(player.getItemInHand(), ChatColor.GRAY + "Lightning I"))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("aebow")));
					
					else if(loreContainsText(player.getItemInHand(), ChatColor.GRAY + "Poison I"))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("poisonbow")));
					
					else if(loreContainsText(player.getItemInHand(), ChatColor.GRAY + "Lightning I"))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("lightningbow")));
					
					else if(player.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ARTHROPODS))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("40foot")));
					
					else if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_FALL, 1))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("enderbow")));
				}
				
				if(player.getItemInHand().getType().equals(Material.MILK_BUCKET))
				{
					ItemStack itemstack = player.getItemInHand();
					ItemMeta meta = itemstack.getItemMeta();
					if(itemstack.hasItemMeta() && meta.hasDisplayName() && meta.getDisplayName().equalsIgnoreCase("EMP"))
					{
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("emp")));
					}
				}	
				
				if(hand.getType().equals(Material.SHEARS))
				{
					if(this.itemHasEnchantAndLevel(hand, Enchantment.LOOT_BONUS_BLOCKS, 1))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("fortuneshears")));
					
					else if(this.itemHasEnchantAndLevel(hand, Enchantment.DAMAGE_UNDEAD, 5))
						player.setItemInHand(ItemManager.getInstance().getItemStack(ItemManager.getInstance().getItemByName("blindingrod")));

				}
				
				if(player.getItemInHand().getType().equals(Material.DIAMOND_HELMET))
				{
					Material material = Material.DIAMOND_HELMET;
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 4))
					{
						player.setItemInHand(((Prot4)ItemManager.getInstance().getItemByName("prot4")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 4)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_EXPLOSIONS, 4)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_FIRE, 4)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_PROJECTILE, 4))
					{
						player.setItemInHand(((Prot4AE)ItemManager.getInstance().getItemByName("prot4ae")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 5))
					{
						player.setItemInHand(((Prot5)ItemManager.getInstance().getItemByName("prot5")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 5)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_EXPLOSIONS, 5)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_FIRE, 5)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_PROJECTILE, 5))
					{
						player.setItemInHand(((Prot5AE)ItemManager.getInstance().getItemByName("prot5AE")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 6))
					{
						player.setItemInHand(((Prot6)ItemManager.getInstance().getItemByName("prot6")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 6)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_EXPLOSIONS, 6)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_FIRE, 6)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_PROJECTILE, 6))
					{
						player.setItemInHand(((Prot6AE)ItemManager.getInstance().getItemByName("prot6AE")).getItemStack(material));
					}
				}
				
				if(player.getItemInHand().getType().equals(Material.DIAMOND_CHESTPLATE))
				{
					Material material = Material.DIAMOND_CHESTPLATE;
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 4))
					{
						player.setItemInHand(((Prot4)ItemManager.getInstance().getItemByName("prot4")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 4)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_EXPLOSIONS, 4)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_FIRE, 4)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_PROJECTILE, 4))
					{
						player.setItemInHand(((Prot4AE)ItemManager.getInstance().getItemByName("prot4ae")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 5))
					{
						player.setItemInHand(((Prot5)ItemManager.getInstance().getItemByName("prot5")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 5)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_EXPLOSIONS, 5)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_FIRE, 5)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_PROJECTILE, 5))
					{
						player.setItemInHand(((Prot5AE)ItemManager.getInstance().getItemByName("prot5AE")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 6))
					{
						player.setItemInHand(((Prot6)ItemManager.getInstance().getItemByName("prot6")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 6)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_EXPLOSIONS, 6)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_FIRE, 6)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_PROJECTILE, 6))
					{
						player.setItemInHand(((Prot6AE)ItemManager.getInstance().getItemByName("prot6AE")).getItemStack(material));
					}
				}
				
				if(player.getItemInHand().getType().equals(Material.DIAMOND_LEGGINGS))
				{
					Material material = Material.DIAMOND_LEGGINGS;
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 4))
					{
						player.setItemInHand(((Prot4)ItemManager.getInstance().getItemByName("prot4")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 4)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_EXPLOSIONS, 4)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_FIRE, 4)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_PROJECTILE, 4))
					{
						player.setItemInHand(((Prot4AE)ItemManager.getInstance().getItemByName("prot4ae")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 5))
					{
						player.setItemInHand(((Prot5)ItemManager.getInstance().getItemByName("prot5")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 5)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_EXPLOSIONS, 5)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_FIRE, 5)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_PROJECTILE, 5))
					{
						player.setItemInHand(((Prot5AE)ItemManager.getInstance().getItemByName("prot5AE")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 6))
					{
						player.setItemInHand(((Prot6)ItemManager.getInstance().getItemByName("prot6")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 6)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_EXPLOSIONS, 6)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_FIRE, 6)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_PROJECTILE, 6))
					{
						player.setItemInHand(((Prot6AE)ItemManager.getInstance().getItemByName("prot6AE")).getItemStack(material));
					}
				}
				
				if(player.getItemInHand().getType().equals(Material.DIAMOND_BOOTS))
				{
					Material material = Material.DIAMOND_BOOTS;
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 4))
					{
						player.setItemInHand(((Prot4)ItemManager.getInstance().getItemByName("prot4")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 4)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_EXPLOSIONS, 4)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_FIRE, 4)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_PROJECTILE, 4))
					{
						player.setItemInHand(((Prot4AE)ItemManager.getInstance().getItemByName("prot4ae")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 5))
					{
						player.setItemInHand(((Prot5)ItemManager.getInstance().getItemByName("prot5")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 5)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_EXPLOSIONS, 5)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_FIRE, 5)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_PROJECTILE, 5))
					{
						player.setItemInHand(((Prot5AE)ItemManager.getInstance().getItemByName("prot5AE")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 6))
					{
						player.setItemInHand(((Prot6)ItemManager.getInstance().getItemByName("prot6")).getItemStack(material));
					}
					
					if(this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_ENVIRONMENTAL, 6)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_EXPLOSIONS, 6)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_FIRE, 6)
							&& this.itemHasEnchantAndLevel(hand, Enchantment.PROTECTION_PROJECTILE, 6))
					{
						player.setItemInHand(((Prot6AE)ItemManager.getInstance().getItemByName("prot6AE")).getItemStack(material));
					}
				}
			}
		}
	}
	
	public boolean itemHasEnchantAndLevel(ItemStack item, Enchantment enchantment, int level)
	{
		return item.containsEnchantment(enchantment) && item.getEnchantmentLevel(enchantment) == level;
	}
	
	public boolean loreContainsText(ItemStack itemstack, String text)
	{
		return itemstack != null
			   && itemstack.hasItemMeta()
			   && itemstack.getItemMeta().hasLore()
			   && itemstack.getItemMeta().getLore().contains(text);
	}
}

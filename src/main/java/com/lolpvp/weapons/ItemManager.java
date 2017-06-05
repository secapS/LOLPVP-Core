package com.lolpvp.weapons;

import java.util.ArrayList;

import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.lolpvp.core.Core;
import com.lolpvp.weapons.classes.AEBow;
import com.lolpvp.weapons.classes.AbominableSnowman;
import com.lolpvp.weapons.classes.AngelWings;
import com.lolpvp.weapons.classes.Assassin8;
import com.lolpvp.weapons.classes.BabyMaker;
import com.lolpvp.weapons.classes.BattleAxe;
import com.lolpvp.weapons.classes.Beast;
import com.lolpvp.weapons.classes.BlindingRod;
import com.lolpvp.weapons.classes.BoneSword;
import com.lolpvp.weapons.classes.BunnyBeater;
import com.lolpvp.weapons.classes.BunnyEars;
import com.lolpvp.weapons.classes.CaptainAmerica;
import com.lolpvp.weapons.classes.Chainsaw;
import com.lolpvp.weapons.classes.CloudBoots;
import com.lolpvp.weapons.classes.Clover;
import com.lolpvp.weapons.classes.CupidBow;
import com.lolpvp.weapons.classes.DateRapist;
import com.lolpvp.weapons.classes.EMP;
import com.lolpvp.weapons.classes.EggsplodingEgg;
import com.lolpvp.weapons.classes.Ejacul8;
import com.lolpvp.weapons.classes.ElectroMagnet;
import com.lolpvp.weapons.classes.EnderBow;
import com.lolpvp.weapons.classes.Excalibur;
import com.lolpvp.weapons.classes.FireBall;
import com.lolpvp.weapons.classes.FireWorkBow;
import com.lolpvp.weapons.classes.FortuneShears;
import com.lolpvp.weapons.classes.FortyFoot;
import com.lolpvp.weapons.classes.GoldTurkey;
import com.lolpvp.weapons.classes.HealingShears;
import com.lolpvp.weapons.classes.HulkSmash;
import com.lolpvp.weapons.classes.IceBlade;
import com.lolpvp.weapons.classes.InvisRing;
import com.lolpvp.weapons.classes.IronMan;
import com.lolpvp.weapons.classes.IronManSuit;
import com.lolpvp.weapons.classes.LOLSword;
import com.lolpvp.weapons.classes.MagicFeather;
import com.lolpvp.weapons.classes.MagicSperm;
import com.lolpvp.weapons.classes.MoneyBag;
import com.lolpvp.weapons.classes.MorningBJ;
import com.lolpvp.weapons.classes.MorningWood;
import com.lolpvp.weapons.classes.NoobsBlade;
import com.lolpvp.weapons.classes.ParkourBoots;
import com.lolpvp.weapons.classes.Payday;
import com.lolpvp.weapons.classes.PianoKey;
import com.lolpvp.weapons.classes.PoisonBow;
import com.lolpvp.weapons.classes.Prot4;
import com.lolpvp.weapons.classes.Prot4AE;
import com.lolpvp.weapons.classes.Prot5;
import com.lolpvp.weapons.classes.Prot5AE;
import com.lolpvp.weapons.classes.Prot6;
import com.lolpvp.weapons.classes.Prot6AE;
import com.lolpvp.weapons.classes.RabbitsFoot;
import com.lolpvp.weapons.classes.RocketBoots;
import com.lolpvp.weapons.classes.SnowArmor;
import com.lolpvp.weapons.classes.SparringAxe;
import com.lolpvp.weapons.classes.SpellBook;
import com.lolpvp.weapons.classes.SpidermanBow;
import com.lolpvp.weapons.classes.SunGlasses;
import com.lolpvp.weapons.classes.ThorsHammer;
import com.lolpvp.weapons.classes.WifeBeater;
import com.lolpvp.weapons.classes.WildTurkey;
import com.lolpvp.weapons.classes.WitchHat;
import com.lolpvp.weapons.classes.WitchsMagic;
import com.lolpvp.weapons.classes.ZeusBow;

public class ItemManager
{
	private static ArrayList<BallerItem> items;
	private static ItemManager instance;
	
	public static void setup(Core core)
	{
		items = new ArrayList<>();
		items.add(new ElectroMagnet(core));
		items.add(new IronMan());
		items.add(new Assassin8());
		items.add(new Ejacul8());
		items.add(new WifeBeater());
		items.add(new MagicSperm(core));
		items.add(new BabyMaker());
		items.add(new WildTurkey());
		items.add(new Chainsaw());
		items.add(new EMP());
		items.add(new BlindingRod());
		items.add(new GoldTurkey());
		items.add(new IceBlade(core));
		items.add(new Excalibur());
		items.add(new NoobsBlade());
		items.add(new Prot4());
		items.add(new Prot4AE());
		items.add(new Prot5());
		items.add(new Prot5AE());
		items.add(new Prot6());
		items.add(new Prot6AE());
		items.add(new WitchHat());
		items.add(new WitchsMagic());
		items.add(new HealingShears());
		items.add(new RabbitsFoot(core));
		items.add(new SnowArmor(core));
		items.add(new AbominableSnowman(core));
		items.add(new BunnyEars(core));
		items.add(new MorningBJ());
		items.add(new MorningWood());
		items.add(new SparringAxe());
		items.add(new InvisRing(core));
		items.add(new Beast());
		items.add(new DateRapist());
		items.add(new RocketBoots(core));
		items.add(new FortuneShears());
		items.add(new AEBow(core));
		items.add(new FortyFoot(core));
		items.add(new EnderBow(core));
		items.add(new ZeusBow(core));
		items.add(new PoisonBow(core));
		items.add(new CupidBow(core));
		items.add(new FireWorkBow(core));
		items.add(new BattleAxe());
		items.add(new ParkourBoots());
		items.add(new Clover());
		items.add(new CloudBoots());
		items.add(new BoneSword(core));
		items.add(new SunGlasses());
		items.add(new EggsplodingEgg());
		items.add(new BunnyBeater());
		items.add(new ThorsHammer(core));
		items.add(new CaptainAmerica(core));
		items.add(new IronManSuit(core));
		items.add(new HulkSmash());
		items.add(new Payday(core));
		items.add(new MagicFeather());
		items.add(new SpellBook(core));
		items.add(new PianoKey(core));
		items.add(new SpidermanBow(core));
		items.add(new MoneyBag(core));
		items.add(new LOLSword(core));
        items.add(new AngelWings());
        items.add(new FireBall());
	}
	
	public boolean isBallerItem(ItemStack itemstack, BallerItem item)
	{
		if(itemstack != null && itemstack.hasItemMeta() && itemstack.getItemMeta().hasLore() && item.getLore() != null)
		{
			for(int i = 0; i <= item.getLore().size() - 1; i++)
			{
				if(!itemstack.getItemMeta().getLore().get(i).equals(item.getLore().get(i)))
					return false;
			}
			return true;
		}
		return false;
		//return itemstack != null && itemstack.hasItemMeta() && itemstack.getItemMeta().hasLore() && item.getLore().containsAll(itemstack.getItemMeta().getLore());
	}
	
	public boolean isBallerItemName(ItemStack itemstack, BallerItem item)
	{
		return itemstack != null && itemstack.hasItemMeta() && itemstack.getItemMeta().hasDisplayName() && itemstack.getItemMeta().getDisplayName().equalsIgnoreCase(item.getName());
	}
	
	public String getBallerItemName(ItemStack item)
	{
		for(BallerItem bitem : this.getItems())
		{
			if(this.isBallerItem(item, bitem))
			{
				return bitem.getName();
			}
		}
		return null;
	}
	
	public ItemStack getItemStack(BallerItem item)
	{
		return this.getItemStack(item, 1);	
	}
	
	public ItemStack addGlow(ItemStack item)
	{ 
		  net.minecraft.server.v1_7_R4.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		  NBTTagCompound tag = null;
		  if (!nmsStack.hasTag()) 
		  {
		      tag = new NBTTagCompound();
		      nmsStack.setTag(tag);
		  }
		  if (tag == null) tag = nmsStack.getTag();
		  NBTTagList ench = new NBTTagList();
		  tag.set("ench", ench);
		  nmsStack.setTag(tag);
		  return CraftItemStack.asCraftMirror(nmsStack);
	}

	public ItemStack getItemStack(BallerItem item, int amount)
	{
		ItemStack[] itemstack = {new ItemStack(item.getMaterial(), amount)};
		ItemMeta meta = itemstack[0].getItemMeta();
		for(Enchantment ench : item.getEnchantments().keySet())
			meta.addEnchant(ench, item.getEnchantments().get(ench), true);	
		meta.setDisplayName(item.getName());
		meta.setLore(item.getLore());
		itemstack[0].setItemMeta(meta);
		if(item.shouldGlow())
			return this.addGlow(itemstack[0]);
		else
			return itemstack[0];
	}
	
	public ItemStack getItemStackWithMeta(BallerItem item, int amount)
	{
		if(item.hasOwner())
		{
			ItemStack[] itemstack = {new ItemStack(item.getMaterial(), amount, (short)item.getMeta())};
			SkullMeta skullmeta = (SkullMeta)itemstack[0].getItemMeta();
			skullmeta.setOwner(item.getOwner());
			for(Enchantment ench : item.getEnchantments().keySet())
				skullmeta.addEnchant(ench, item.getEnchantments().get(ench), true);	
			skullmeta.setDisplayName(item.getName());
			skullmeta.setLore(item.getLore());
			itemstack[0].setItemMeta(skullmeta);
			return itemstack[0];		
		}
		
		if(item.hasMeta() && !item.hasOwner())
		{
			ItemStack[] itemstack = {new ItemStack(item.getMaterial(), amount, (short)item.getMeta())};
			ItemMeta meta = itemstack[0].getItemMeta();
			for(Enchantment ench : item.getEnchantments().keySet())
				meta.addEnchant(ench, item.getEnchantments().get(ench), true);	
			meta.setDisplayName(item.getName());
			meta.setLore(item.getLore());
			itemstack[0].setItemMeta(meta);
			return itemstack[0];
		}
		
		if(item.isLeatherArmor())
		{
			ItemStack[] itemstack = {new ItemStack(item.getMaterial(), amount)};
			LeatherArmorMeta meta = (LeatherArmorMeta) itemstack[0].getItemMeta();
			for(Enchantment ench : item.getEnchantments().keySet())
				meta.addEnchant(ench, item.getEnchantments().get(ench), true);	
			meta.setDisplayName(item.getName());
			meta.setLore(item.getLore());
			meta.setColor(Color.WHITE);
			itemstack[0].setItemMeta(meta);
			return itemstack[0];
		}
		return null;
	}
	
	public ItemStack[] getItemStacks(BallerItem item, int amount)
	{
		ItemStack[] stacks = new ItemStack[item.getMaterials().length];
		for(int i = 0; i < item.getMaterials().length; i++)
		{
			ItemStack itemstack = new ItemStack(item.getMaterials()[i], amount);
			ItemMeta meta = itemstack.getItemMeta();
			for(Enchantment ench : item.getEnchantments().keySet())
				meta.addEnchant(ench, item.getEnchantments().get(ench), true);	
			meta.setDisplayName(item.getName());
			meta.setLore(item.getLore());
			itemstack.setItemMeta(meta);
			stacks[i] = itemstack;
		}
		return stacks;
	}
	
	public void giveItem(Player player, BallerItem item, int amount)
	{
		if(item.hasOwner())
			player.getInventory().addItem(this.getItemStackWithMeta(item, amount));
		else if(item.hasMeta() && !item.hasOwner())
			player.getInventory().addItem(this.getItemStackWithMeta(item, amount));
		else if(item.hasMultipleMaterials())
			player.getInventory().addItem(this.getItemStacks(item, amount));
		else if(item.isLeatherArmor())
			player.getInventory().addItem(this.getItemStackWithMeta(item, amount));
		else
			player.getInventory().addItem(this.getItemStack(item, amount));
		player.updateInventory();
	}
	
	public void giveItem(Player player, BallerItem item)
	{
		giveItem(player, item, 1);
	}
	
	public BallerItem getItemByName(String name)
	{
		for(BallerItem item : items)
		{
			if(item.getName().equalsIgnoreCase(name))
				return item;
			else if(ChatColor.stripColor(item.getName()).equalsIgnoreCase(name))
				return item;
			else
			{
				for(String alias : item.getAliases())
				{
					if(alias.equalsIgnoreCase(name))
						return item;
				}	
			}
		}
		return null;
	}
	
	public boolean isBallerItem(String s)
	{
		for(BallerItem item : items)
		{
			if(item.getName().equalsIgnoreCase(s))
				return true;
			
			for(String alias : item.getAliases())
			{
				if(alias.equalsIgnoreCase(s))
					return true;
			}
		}
		return false;
	}
	
	public ItemStack getItemStack(BallerItem item, Material material)
	{
		if(item.hasMultipleMaterials())
		{
			if(item.equals(new Prot4()))
			{
				return ((Prot4)item).getItemStack(material);
			}
			else if(item.equals(new Prot4AE()))
			{
				return ((Prot4AE)item).getItemStack(material);
			}
			else if(item.equals(new Prot5()))
			{
				return ((Prot5)item).getItemStack(material);
			}
			else if(item.equals(new Prot5AE()))
			{
				return ((Prot5AE)item).getItemStack(material);
			}
			else if(item.equals(new Prot6()))
			{
				return ((Prot6)item).getItemStack(material);
			}
			else if(item.equals(new Prot6AE()))
			{
				return ((Prot6AE)item).getItemStack(material);
			}
		}
		return null;
	}
	
	public ArrayList<BallerItem> getItems()
	{
		return items;
	}
	
	public void registerItems(Core core)
	{
		for(BallerItem item : this.getItems())
		{
			core.getServer().getPluginManager().registerEvents(item, core);
		}
	}
	
	public static ItemManager getInstance()
	{
		if(instance == null)
			instance = new ItemManager();
		return instance;	
	}
}

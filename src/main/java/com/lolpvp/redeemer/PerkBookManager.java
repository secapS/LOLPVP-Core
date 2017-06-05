package com.lolpvp.redeemer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PerkBookManager 
{
	//heal, ironman, feed, regen, repair, ranks, spinning tag, forums vip, rename book.
	private static List<PerkBook> perkBooks;
	
	private static PerkBookManager instance;
	
	public static void setup()
	{
		perkBooks = new ArrayList<>();
		perkBooks.add(new PerkBook("ironman", PerkBookType.PERMISSION, "lolpvp.ironman"));
		perkBooks.add(new PerkBook("regen", PerkBookType.PERMISSION, "lolpvp.regen"));
		perkBooks.add(new PerkBook("nightvision", PerkBookType.PERMISSION, "lolpvp.nightvision"));
		perkBooks.add(new PerkBook("fireresistance", PerkBookType.PERMISSION, "lolpvp.fireresistance"));
		perkBooks.add(new PerkBook("vip", PerkBookType.GROUP, "Donator_Zero"));
		perkBooks.add(new PerkBook("vip+", PerkBookType.GROUP, "Donator_Two"));
		perkBooks.add(new PerkBook("thad", PerkBookType.GROUP, "Donator_Three"));
		perkBooks.add(new PerkBook("thad+", PerkBookType.GROUP, "Donator_Four"));
		perkBooks.add(new PerkBook("$$$$$", PerkBookType.GROUP, "Donator_Five"));
		perkBooks.add(new PerkBook("hat", PerkBookType.PERMISSION, "essentials.hat"));
		perkBooks.add(new PerkBook("ptime", PerkBookType.PERMISSION, "essentials.ptime"));
		perkBooks.add(new PerkBook("tptoggle", PerkBookType.PERMISSION, "essentials.tptoggle"));
		perkBooks.add(new PerkBook("heal", PerkBookType.PERMISSION, "essentials.heal"));
		perkBooks.add(new PerkBook("ignore", PerkBookType.PERMISSION, "essentials.ignore"));
		perkBooks.add(new PerkBook("repair", PerkBookType.MULTIPLE_PERMISSIONS, "essentials.repair", "essentials.repair.armor", "essentials.repair.enchanted", "essentials.repair.all"));
		perkBooks.add(new PerkBook("spinning", PerkBookType.PERMISSION, "lolpvp.setmagic"));
//		perkBooks.add(new PerkBook("arenavip", PerkBookType.MULTIPLE_PERMISSIONS, "essentials.warps.vip", "essentials.warp"));
	}
	
	public PerkBook getPerkBookByName(String name)
	{
		for(PerkBook perkbook : perkBooks)
		{
			if(perkbook.getPerk().equalsIgnoreCase(name))
				return perkbook;
		}
		return null;
	}
	String page = "You can redeem this book for one PERK_NAME PERK_TYPE \n \n Hold the book and type /redeem if you want to redeem it! \n \n Visit www.LOLPVP.com to unlock more perks!";
	public void givePerkBook(Player player, PerkBook perkbook)
	{
		ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta)item.getItemMeta();
		meta.setTitle("Perk Book");
		meta.setAuthor("lolitsthad");
		meta.addPage(getPage(perkbook));
//		if(!perkbook.getType().equals(PerkBookType.GROUP))
//			meta.addPage("You can redeem this book for one " + perkbook.getPerk().toUpperCase() + " perk! \n \n Hold the book and type /redeem if you want to redeem it! \n \n Visit www.LOLPVP.com to unlock more perks!");
//		else
//			meta.addPage("You can redeem this book for one " + perkbook.getPerk().toUpperCase() + " pack! (only perks) \n \n Hold the book and type /redeem if you want to redeem it! \n \n Visit www.LOLPVP.com to unlock more perks!");
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
	}
	
	public String getPage(PerkBook perk)
	{
		return "You can redeem this book for one " + perk.getPerk() +  " " + (perk.getType().equals(PerkBookType.GROUP) ? "rank!" : "perk!") +  "\n \n Hold the book and type /redeem if you want to redeem it! \n \n Visit www.LOLPVP.com to unlock more perks!";
	}
	
	public void redeemPerkBook(Player player, PerkBook perkbook)
	{
		try
		{
			if(isPerkBook(player.getItemInHand(), perkbook))
			{
				switch(perkbook.getType())
				{
				case PERMISSION:
					if(!PermissionsEx.getUser(player).has(perkbook.getPermission()))
					{
						PermissionsEx.getUser(player).addPermission(perkbook.getPermission());
						player.sendMessage(ChatColor.GREEN + "You have redeemed a " + perkbook.getPerk().toUpperCase() + " book!");
						if(player.getItemInHand().getAmount() > 1)
						{
							player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
							break;
						}
						else
						{
							player.setItemInHand(null);
							break;
						}
					}
					else
					{
						player.sendMessage(ChatColor.RED + "You already have that perk!");
						break;
					}
				case MULTIPLE_PERMISSIONS:
					if(!PermissionsEx.getUser(player).has(perkbook.getPermissions()[0]))
					{
						for(String permission : perkbook.getPermissions())
						{
							PermissionsEx.getUser(player).addPermission(permission);
						}
						player.sendMessage(ChatColor.GREEN + "You have redeemed a " + perkbook.getPerk().toUpperCase() + " book!");
						if(player.getItemInHand().getAmount() > 1)
						{
							player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
							break;
						}
						else
						{
							player.setItemInHand(null);
							break;
						}	
					}
					else
					{
						player.sendMessage(ChatColor.RED + "You already have that perk!");
						break;
					}
				case GROUP:
					for(PerkBook pbook : getPerkBooks())
					{
						if(pbook.getType().equals(PerkBookType.GROUP))
						{
							if(!PermissionsEx.getUser(player).inGroup(pbook.getGroup()))
							{
								PermissionsEx.getUser(player).addGroup(perkbook.getGroup());
								player.sendMessage(ChatColor.GREEN + "You have redeemed a " + perkbook.getPerk().toUpperCase() + " book!");
								if(player.getItemInHand().getAmount() > 1)
								{
									player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
									break;
								}
								else
								{
									player.setItemInHand(null);
									break;
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "You are already in a group!");
								break;
							}
						}
					}
				}
			}
			else
			{
				player.sendMessage(ChatColor.RED + "That is not a perkbook!");
			}	
		}
		catch(IllegalArgumentException e)
		{
			player.sendMessage(ChatColor.RED + "That is not a perkbook!");
		}
	}
	
	public boolean isPerkBook(ItemStack item, PerkBook perkbook)
	{
		return item != null && item.getType().equals(Material.WRITTEN_BOOK) && item.hasItemMeta() 
				&& item.getItemMeta() instanceof BookMeta
				&& ((BookMeta)item.getItemMeta()).hasTitle() && ((BookMeta)item.getItemMeta()).getTitle().equals("Perk Book")
				&& ((BookMeta)item.getItemMeta()).hasAuthor() && ((BookMeta)item.getItemMeta()).getAuthor().equals(perkbook.getAuthor())
				&& ((BookMeta)item.getItemMeta()).hasPages() && ((BookMeta)item.getItemMeta()).getPage(1).contains(getPage(perkbook));
	}
	
	public boolean isPerkBook(ItemStack item)
	{
		return item != null && item.getType().equals(Material.WRITTEN_BOOK) && item.hasItemMeta() 
				&& item.getItemMeta() instanceof BookMeta
				&& ((BookMeta)item.getItemMeta()).hasTitle() && ((BookMeta)item.getItemMeta()).getTitle().equals("Perk Book")
				&& ((BookMeta)item.getItemMeta()).hasAuthor() && ((BookMeta)item.getItemMeta()).getAuthor().equals("lolitsthad")
				&& ((BookMeta)item.getItemMeta()).hasPages();
	}
	
	public static List<PerkBook> getPerkBooks()
	{
		return perkBooks;
	}
	
	public static PerkBookManager getInstance()
	{
		if(instance == null)
			instance = new PerkBookManager();
		return instance;
	}
}

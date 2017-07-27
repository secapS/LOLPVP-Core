package com.lolpvp.commands.classes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lolpvp.commands.SubCommand;
import com.lolpvp.weaponsold.BallerItem;
import com.lolpvp.weaponsold.ItemManager;
import com.lolpvp.weaponsold.classes.IronManSuit;
import com.lolpvp.weaponsold.classes.Prot4;
import com.lolpvp.weaponsold.classes.Prot4AE;
import com.lolpvp.weaponsold.classes.Prot5;
import com.lolpvp.weaponsold.classes.Prot5AE;
import com.lolpvp.weaponsold.classes.Prot6;
import com.lolpvp.weaponsold.classes.Prot6AE;
import com.lolpvp.weaponsold.classes.SnowArmor;

public class GetItems extends SubCommand
{
	public GetItems() 
	{
		super("gives a baller item", "lolpvp.balleritem", "<item> <amount> <player>", new String[]{"i", "item"});
	}

	@Override
	public void onCommand(CommandSender sender, String[] args)
	{
		if(args.length == 0)
		{
			sender.sendMessage(ChatColor.GRAY + "--------" + ChatColor.AQUA + "LOLPVP ITEMS" + ChatColor.GRAY + "--------");
			StringBuilder items = new StringBuilder();
			for(BallerItem item : ItemManager.getInstance().getItems())
			{
				if(item != ItemManager.getInstance().getItemByName("ironman"))
				{
					items.append(ChatColor.AQUA + ChatColor.stripColor(item.getName()));
					if(item.equals(ItemManager.getInstance().getItems().get(ItemManager.getInstance().getItems().size() - 1)))
					{
						items.append(ChatColor.GRAY + ".");
					}
					else
					{
						items.append(ChatColor.GRAY + ", ");
					}	
				}
			}
			sender.sendMessage(items.toString());
		}

		if(sender instanceof Player)
		{
			if(args.length == 1)
			{
				if(ItemManager.getInstance().getItemByName(args[0]) != null)
				{	
					if(args[0].equalsIgnoreCase("ironman"))
						return;
					if(ItemManager.getInstance().getItemByName(args[0]).hasMultipleMaterials())
					{
						if(ItemManager.getInstance().isBallerItem(args[0]))
						{
							if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection4")))
							{
								Prot4 prot4 = (Prot4) ItemManager.getInstance().getItemByName(args[0]);
								((Player)sender).getInventory().addItem(prot4.getItemStacks(1));
								((Player)sender).updateInventory();
								sender.sendMessage(ChatColor.GRAY + "Here is your " + prot4.getName() + ChatColor.GRAY + ".");	
							}

							if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection5")))
							{
								Prot5 prot5 = (Prot5) ItemManager.getInstance().getItemByName(args[0]);
								((Player)sender).getInventory().addItem(prot5.getItemStacks(1));
								((Player)sender).updateInventory();
								sender.sendMessage(ChatColor.GRAY + "Here is your " + prot5.getName() + ChatColor.GRAY + ".");	
							}

							if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection4ae")))
							{
								Prot4AE prot4ae = (Prot4AE) ItemManager.getInstance().getItemByName(args[0]);
								((Player)sender).getInventory().addItem(prot4ae.getItemStacks(1));
								((Player)sender).updateInventory();
								sender.sendMessage(ChatColor.GRAY + "Here is your " + prot4ae.getName() + ChatColor.GRAY + ".");	
							}

							if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection5ae")))
							{
								Prot5AE prot5ae = (Prot5AE) ItemManager.getInstance().getItemByName(args[0]);
								((Player)sender).getInventory().addItem(prot5ae.getItemStacks(1));
								((Player)sender).updateInventory();
								sender.sendMessage(ChatColor.GRAY + "Here is your " + prot5ae.getName() + ChatColor.GRAY + ".");	
							}

							if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection6")))
							{
								Prot6 prot6 = (Prot6) ItemManager.getInstance().getItemByName(args[0]);
								((Player)sender).getInventory().addItem(prot6.getItemStacks(1));
								((Player)sender).updateInventory();
								sender.sendMessage(ChatColor.GRAY + "Here is your " + prot6.getName() + ChatColor.GRAY + ".");	
							}

							if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection6ae")))
							{
								Prot6AE prot6ae = (Prot6AE) ItemManager.getInstance().getItemByName(args[0]);
								((Player)sender).getInventory().addItem(prot6ae.getItemStacks(1));
								((Player)sender).updateInventory();
								sender.sendMessage(ChatColor.GRAY + "Here is your " + prot6ae.getName() + ChatColor.GRAY + ".");	
							}
						}

						if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("snowarmor")))
						{
							SnowArmor snowarmor = (SnowArmor)ItemManager.getInstance().getItemByName(args[0]);
							((Player)sender).getInventory().addItem(snowarmor.getItemStacks(1));
							((Player)sender).updateInventory();
							sender.sendMessage(ChatColor.GRAY + "Here is your " + snowarmor.getName() + ChatColor.GRAY + ".");
						}
						
						if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("ironmansuit")))
						{
							IronManSuit ironmansuit = (IronManSuit)ItemManager.getInstance().getItemByName(args[0]);
							((Player)sender).getInventory().addItem(ironmansuit.getItemStacks(1));
							((Player)sender).updateInventory();
							sender.sendMessage(ChatColor.GRAY + "Here is your " + ironmansuit.getName() + ChatColor.GRAY + ".");
						}
					}
					else
					{
						ItemManager.getInstance().giveItem((Player)sender, ItemManager.getInstance().getItemByName(args[0]));
						((Player)sender).updateInventory();
						sender.sendMessage(ChatColor.GRAY + "Here is your " + ChatColor.AQUA + ItemManager.getInstance().getItemByName(args[0]).getName() + ChatColor.GRAY + ".");
					}
				}
				else
				{
					sender.sendMessage(ChatColor.AQUA + args[0] + ChatColor.RED + " is not a real item!");
				}
			}

			if(args.length == 2)
			{
				if(ItemManager.getInstance().getItemByName(args[0]) != null)
				{
					if(args[0].equalsIgnoreCase("ironman"))
						return;
					if(ItemManager.getInstance().getItemByName(args[0]).hasMultipleMaterials())
					{
						if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection4")))
						{
							Prot4 prot4 = (Prot4) ItemManager.getInstance().getItemByName(args[0]);
							((Player)sender).getInventory().addItem(prot4.getItemStacks(Integer.valueOf(args[1])));
							((Player)sender).updateInventory();
							sender.sendMessage(ChatColor.GRAY + "Here is your " + prot4.getName() + ChatColor.GRAY + ".");
						}

						if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("snowarmor")))
						{
							SnowArmor snowarmor = (SnowArmor) ItemManager.getInstance().getItemByName(args[0]);
							((Player)sender).getInventory().addItem(snowarmor.getItemStacks(Integer.valueOf(args[1])));
							((Player)sender).updateInventory();
							sender.sendMessage(ChatColor.GRAY + "Here is your " + snowarmor.getName() + ChatColor.GRAY + ".");
						}
						
						if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("ironmansuit")))
						{
							IronManSuit ironmansuit = (IronManSuit)ItemManager.getInstance().getItemByName(args[0]);
							((Player)sender).getInventory().addItem(ironmansuit.getItemStacks(Integer.valueOf(args[1])));
							((Player)sender).updateInventory();
							sender.sendMessage(ChatColor.GRAY + "Here is your " + ironmansuit.getName() + ChatColor.GRAY + ".");
						}

						if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection5")))
						{
							Prot5 prot5 = (Prot5) ItemManager.getInstance().getItemByName(args[0]);
							((Player)sender).getInventory().addItem(prot5.getItemStacks(Integer.valueOf(args[1])));
							((Player)sender).updateInventory();
							sender.sendMessage(ChatColor.GRAY + "Here is your " + prot5.getName() + ChatColor.GRAY + ".");	
						}

						if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection4ae")))
						{
							Prot4AE prot4ae = (Prot4AE) ItemManager.getInstance().getItemByName(args[0]);
							((Player)sender).getInventory().addItem(prot4ae.getItemStacks(Integer.valueOf(args[1])));
							((Player)sender).updateInventory();
							sender.sendMessage(ChatColor.GRAY + "Here is your " + prot4ae.getName() + ChatColor.GRAY + ".");	
						}

						if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection5ae")))
						{
							Prot5AE prot5ae = (Prot5AE) ItemManager.getInstance().getItemByName(args[0]);
							((Player)sender).getInventory().addItem(prot5ae.getItemStacks(Integer.valueOf(args[1])));
							((Player)sender).updateInventory();
							sender.sendMessage(ChatColor.GRAY + "Here is your " + prot5ae.getName() + ChatColor.GRAY + ".");	
						}

						if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection6")))
						{
							Prot6 prot6 = (Prot6) ItemManager.getInstance().getItemByName(args[0]);
							((Player)sender).getInventory().addItem(prot6.getItemStacks(Integer.valueOf(args[1])));
							((Player)sender).updateInventory();
							sender.sendMessage(ChatColor.GRAY + "Here is your " + prot6.getName() + ChatColor.GRAY + ".");	
						}

						if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection6ae")))
						{
							Prot6AE prot6ae = (Prot6AE) ItemManager.getInstance().getItemByName(args[0]);
							((Player)sender).getInventory().addItem(prot6ae.getItemStacks(Integer.valueOf(args[1])));
							((Player)sender).updateInventory();
							sender.sendMessage(ChatColor.GRAY + "Here is your " + prot6ae.getName() + ChatColor.GRAY + ".");	
						}
					}
					else
					{
						sender.sendMessage(ChatColor.GRAY + "Here is your " + ItemManager.getInstance().getItemByName(args[0]).getName() + ChatColor.GRAY + ".");
						ItemManager.getInstance().giveItem((Player)sender, ItemManager.getInstance().getItemByName(args[0]), Integer.valueOf(args[1]));			
						((Player)sender).updateInventory();
					}
				}
				else
				{
					sender.sendMessage(ChatColor.AQUA + args[0] + ChatColor.RED + " is not a real item!");
				}
			}
			
			if(args.length == 3)
			{
				if(ItemManager.getInstance().getItemByName(args[0]) != null)
				{
					if(args[0].equalsIgnoreCase("ironman"))
						return;
					Player player = Bukkit.getPlayer(args[2]);
					if(player != null)
					{
						if(ItemManager.getInstance().getItemByName(args[0]).hasMultipleMaterials())
						{
							if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection4")))
							{
								Prot4 prot4 = (Prot4) ItemManager.getInstance().getItemByName(args[0]);
								player.getInventory().addItem(prot4.getItemStacks(Integer.valueOf(args[1])));
								player.sendMessage(ChatColor.GRAY + "Here is your " + prot4.getName() + ChatColor.GRAY + ".");
								player.updateInventory();
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + prot4.getName() + ChatColor.GRAY + ".");
							}

							if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("snowarmor")))
							{
								SnowArmor snowarmor = (SnowArmor) ItemManager.getInstance().getItemByName(args[0]);
								player.getInventory().addItem(snowarmor.getItemStacks(Integer.valueOf(args[1])));
								player.updateInventory();
								player.sendMessage(ChatColor.GRAY + "Here is your " + snowarmor.getName() + ChatColor.GRAY + ".");
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + snowarmor.getName() + ChatColor.GRAY + ".");
							}
							
							if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("ironmansuit")))
							{
								IronManSuit ironmansuit = (IronManSuit)ItemManager.getInstance().getItemByName(args[0]);
								((Player)sender).getInventory().addItem(ironmansuit.getItemStacks(Integer.valueOf(args[1])));
								((Player)sender).updateInventory();
								player.sendMessage(ChatColor.GRAY + "Here is your " + ironmansuit.getName() + ChatColor.GRAY + ".");
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + ironmansuit.getName() + ChatColor.GRAY + ".");
							}

							if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection5")))
							{
								Prot5 prot5 = (Prot5) ItemManager.getInstance().getItemByName(args[0]);
								player.getInventory().addItem(prot5.getItemStacks(Integer.valueOf(args[1])));
								player.updateInventory();
								player.sendMessage(ChatColor.GRAY + "Here is your " + prot5.getName() + ChatColor.GRAY + ".");
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + prot5.getName() + ChatColor.GRAY + ".");
							}

							if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection4ae")))
							{
								Prot4AE prot4ae = (Prot4AE) ItemManager.getInstance().getItemByName(args[0]);
								player.getInventory().addItem(prot4ae.getItemStacks(Integer.valueOf(args[1])));
								player.updateInventory();
								player.sendMessage(ChatColor.GRAY + "Here is your " + prot4ae.getName() + ChatColor.GRAY + ".");
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + prot4ae.getName() + ChatColor.GRAY + ".");
							}

							if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection5ae")))
							{
								Prot5AE prot5ae = (Prot5AE) ItemManager.getInstance().getItemByName(args[0]);
								player.getInventory().addItem(prot5ae.getItemStacks(Integer.valueOf(args[1])));
								player.updateInventory();
								player.sendMessage(ChatColor.GRAY + "Here is your " + prot5ae.getName() + ChatColor.GRAY + ".");
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + prot5ae.getName() + ChatColor.GRAY + ".");
							}

							if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection6")))
							{
								Prot6 prot6 = (Prot6) ItemManager.getInstance().getItemByName(args[0]);
								player.getInventory().addItem(prot6.getItemStacks(Integer.valueOf(args[1])));
								player.updateInventory();
								player.sendMessage(ChatColor.GRAY + "Here is your " + prot6.getName() + ChatColor.GRAY + ".");
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + prot6.getName() + ChatColor.GRAY + ".");
							}

							if(ItemManager.getInstance().getItemByName(args[0]).equals(ItemManager.getInstance().getItemByName("protection6ae")))
							{
								Prot6AE prot6ae = (Prot6AE) ItemManager.getInstance().getItemByName(args[0]);
								player.getInventory().addItem(prot6ae.getItemStacks(Integer.valueOf(args[1])));
								player.updateInventory();
								player.sendMessage(ChatColor.GRAY + "Here is your " + prot6ae.getName() + ChatColor.GRAY + ".");
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + prot6ae.getName() + ChatColor.GRAY + ".");
							}
						}
						else
						{
							sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + ItemManager.getInstance().getItemByName(args[0]).getName() + ChatColor.GRAY + ".");
							player.sendMessage(ChatColor.GRAY + "Here is your " + ItemManager.getInstance().getItemByName(args[0]).getName() + ChatColor.GRAY + ".");
							ItemManager.getInstance().giveItem(player, ItemManager.getInstance().getItemByName(args[0]), Integer.valueOf(args[1]));			
							player.updateInventory();
						}		
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "That player is not online!");
					}
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "That is not a real item!");
				}
			}
		}
		else
		{
			if(args.length < 4)
			{
				sender.sendMessage(ChatColor.AQUA + getUsage());
				return;
			}
			else
			{
				if(ItemManager.getInstance().getItemByName(args[1]) != null)
				{
					if(args[1].equalsIgnoreCase("ironman"))
						return;
					Player player = Bukkit.getPlayer(args[3]);
					if(player != null)
					{
						if(ItemManager.getInstance().getItemByName(args[1]).hasMultipleMaterials())
						{
							if(ItemManager.getInstance().getItemByName(args[1]).equals(ItemManager.getInstance().getItemByName("protection4")))
							{
								Prot4 prot4 = (Prot4) ItemManager.getInstance().getItemByName(args[1]);
								player.getInventory().addItem(prot4.getItemStacks(Integer.valueOf(args[2])));
								player.sendMessage(ChatColor.GRAY + "Here is your " + prot4.getName() + ChatColor.GRAY + ".");
								player.updateInventory();
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + prot4.getName() + ChatColor.GRAY + ".");
							}

							if(ItemManager.getInstance().getItemByName(args[1]).equals(ItemManager.getInstance().getItemByName("snowarmor")))
							{
								SnowArmor snowarmor = (SnowArmor) ItemManager.getInstance().getItemByName(args[1]);
								player.getInventory().addItem(snowarmor.getItemStacks(Integer.valueOf(args[2])));
								player.updateInventory();
								player.sendMessage(ChatColor.GRAY + "Here is your " + snowarmor.getName() + ChatColor.GRAY + ".");
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + snowarmor.getName() + ChatColor.GRAY + ".");
							}
							
							if(ItemManager.getInstance().getItemByName(args[1]).equals(ItemManager.getInstance().getItemByName("ironmansuit")))
							{
								IronManSuit ironmansuit = (IronManSuit)ItemManager.getInstance().getItemByName(args[1]);
								player.getInventory().addItem(ironmansuit.getItemStacks(Integer.valueOf(args[2])));
								player.updateInventory();
								player.sendMessage(ChatColor.GRAY + "Here is your " + ironmansuit.getName() + ChatColor.GRAY + ".");
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + ironmansuit.getName() + ChatColor.GRAY + ".");
							}

							if(ItemManager.getInstance().getItemByName(args[1]).equals(ItemManager.getInstance().getItemByName("protection5")))
							{
								Prot5 prot5 = (Prot5) ItemManager.getInstance().getItemByName(args[1]);
								player.getInventory().addItem(prot5.getItemStacks(Integer.valueOf(args[2])));
								player.updateInventory();
								player.sendMessage(ChatColor.GRAY + "Here is your " + prot5.getName() + ChatColor.GRAY + ".");
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + prot5.getName() + ChatColor.GRAY + ".");
							}

							if(ItemManager.getInstance().getItemByName(args[1]).equals(ItemManager.getInstance().getItemByName("protection4ae")))
							{
								Prot4AE prot4ae = (Prot4AE) ItemManager.getInstance().getItemByName(args[1]);
								player.getInventory().addItem(prot4ae.getItemStacks(Integer.valueOf(args[2])));
								player.updateInventory();
								player.sendMessage(ChatColor.GRAY + "Here is your " + prot4ae.getName() + ChatColor.GRAY + ".");
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + prot4ae.getName() + ChatColor.GRAY + ".");
							}

							if(ItemManager.getInstance().getItemByName(args[1]).equals(ItemManager.getInstance().getItemByName("protection5ae")))
							{
								Prot5AE prot5ae = (Prot5AE) ItemManager.getInstance().getItemByName(args[1]);
								player.getInventory().addItem(prot5ae.getItemStacks(Integer.valueOf(args[2])));
								player.updateInventory();
								player.sendMessage(ChatColor.GRAY + "Here is your " + prot5ae.getName() + ChatColor.GRAY + ".");
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + prot5ae.getName() + ChatColor.GRAY + ".");
							}

							if(ItemManager.getInstance().getItemByName(args[1]).equals(ItemManager.getInstance().getItemByName("protection6")))
							{
								Prot6 prot6 = (Prot6) ItemManager.getInstance().getItemByName(args[1]);
								player.getInventory().addItem(prot6.getItemStacks(Integer.valueOf(args[2])));
								player.updateInventory();
								player.sendMessage(ChatColor.GRAY + "Here is your " + prot6.getName() + ChatColor.GRAY + ".");
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + prot6.getName() + ChatColor.GRAY + ".");
							}

							if(ItemManager.getInstance().getItemByName(args[1]).equals(ItemManager.getInstance().getItemByName("protection6ae")))
							{
								Prot6AE prot6ae = (Prot6AE) ItemManager.getInstance().getItemByName(args[1]);
								player.getInventory().addItem(prot6ae.getItemStacks(Integer.valueOf(args[2])));
								player.updateInventory();
								player.sendMessage(ChatColor.GRAY + "Here is your " + prot6ae.getName() + ChatColor.GRAY + ".");
								sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + prot6ae.getName() + ChatColor.GRAY + ".");
							}
						}
						else
						{
							sender.sendMessage(ChatColor.GRAY + "Gave " + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " a " + ItemManager.getInstance().getItemByName(args[1]).getName() + ChatColor.GRAY + ".");
							player.sendMessage(ChatColor.GRAY + "Here is your " + ItemManager.getInstance().getItemByName(args[1]).getName() + ChatColor.GRAY + ".");
							ItemManager.getInstance().giveItem(player, ItemManager.getInstance().getItemByName(args[1]), Integer.valueOf(args[2]));			
							player.updateInventory();
						}		
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "That player is not online!");
					}
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "That is not a real item!");
				}
			}	
		}
	}
}

package com.lolpvp.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lolpvp.chests.Chest;
import com.lolpvp.commands.classes.GetItems;
import com.lolpvp.commands.classes.Price;
import com.lolpvp.commands.classes.Sell;
import com.lolpvp.commands.classes.Update;
import com.lolpvp.commands.trade.TradeCommand;

public class LOLPVPCommand implements CommandExecutor 
{
	private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();
	
	public LOLPVPCommand()
	{
		this.commands.add(new GetItems());
		this.commands.add(new Update());
		this.commands.add(new TradeCommand());
		this.commands.add(new Sell());
		this.commands.add(new Price());
		this.commands.add(new Chest());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		if (sender instanceof Player) 
		{
			if (commandLabel.equalsIgnoreCase("lol")) 
			{
				if (args.length == 0) 
				{
					sender.sendMessage(ChatColor.GRAY + "--------" + ChatColor.AQUA + "LOLPVP COMMANDS" + ChatColor.GRAY + "--------");
				
					for (SubCommand c : commands)
					{
						if(c.hasPermission() && sender.hasPermission(c.getPermission()))
						{
							sender.sendMessage(sendHelpMessage(ChatColor.AQUA + "/lol " + aliases(c) + " " + c.getUsage(), ChatColor.GRAY + ": " + c.getMessage() + "."));
						}
					}
					
					sender.sendMessage(sendHelpMessage("/lol t|trade <perk> <player>", ": Trades perks with other players."));
					sender.sendMessage(sendHelpMessage("/lol u|update", ": Updates a baller item in your hand."));
					sender.sendMessage(sendHelpMessage("/lol p|price", ": Checks the price of baller item in your hand."));
					sender.sendMessage(sendHelpMessage("/lol s|sell", ": Sells baller item in your hand."));
					sender.sendMessage(sendHelpMessage("/dispose", ": Creates a disposal inventory."));
					sender.sendMessage(sendHelpMessage("/redeem", ": Redeems a perk book you are holding."));
					if(sender.hasPermission("lolpvp.settag"))
						sender.sendMessage(sendHelpMessage("/lolt", ": Set your tag."));
					
					if(sender.hasPermission("lolpvp.magic"))
						sender.sendMessage(sendHelpMessage("/lolm", ": Set your tag to magic."));
					
					if(sender.hasPermission("lolpvp.regen"))
						sender.sendMessage(sendHelpMessage("/regen | re", ": Gives regeneration II for 3 minutes."));
					else if(sender.hasPermission("lolpvp.ironman"))
					{
						sender.sendMessage(sendHelpMessage("/ironman | im", ": Gives strength III and speed III for 3 minutes."));
						sender.sendMessage(sendHelpMessage("/i2", ": Gives strength III and speed II 3 minutes."));	
					}
					else if(sender.hasPermission("lolpvp.nightvision"))
						sender.sendMessage(sendHelpMessage("/nightvision | nv", ": Gives nightvision I for 3 minutes."));
					else if(sender.hasPermission("lolpvp.fireresistance"))
						sender.sendMessage(sendHelpMessage("/fireresistance | fr", ": Gives fire resistance I for 3 minutes."));
					else if(sender.hasPermission("lolpvp.invis"))
						sender.sendMessage(sendHelpMessage("/invis | in", ": Gives invisibility I for 3 minutes."));
					if(sender.hasPermission("lolpvp.chest2"))
					{
						sender.sendMessage(sendHelpMessage("/chest2", ": Opens virtual chest."));
						if(sender.hasPermission("lolpvp.chest2.others"))
						{
							sender.sendMessage(sendHelpMessage("/chest2 <player>", ": Opens players virtual chest."));
						}
						if(sender.hasPermission("lolpvp.clearchest2"))
						{
							sender.sendMessage(sendHelpMessage("/clearchest2", ": Clears virtual chest."));
							if(sender.hasPermission("lolpvp.clearchest2.others"))
							{
								sender.sendMessage(sendHelpMessage("/clearchest2 <player>", ": Clears players virtual chest."));
							}
						}
					}
					if(sender.isOp())
						sender.sendMessage(this.sendHelpMessage("/qf <price> <name> <commmand>", ": Quick fix command"));
					return true;
				}

				SubCommand command = getCommand(args[0]);

				if (command == null) 
				{
					sender.sendMessage(ChatColor.RED + "That command doesn't exist!");
					return true;
				}

				Vector<String> a = new Vector<String>(Arrays.asList(args));
				a.remove(0);
				args = a.toArray(new String[a.size()]);

				if(command.hasPermission() && sender.hasPermission(command.getPermission()))
				{
					command.onCommand(sender, args);	
				}
				else if(command.hasPermission() && !sender.hasPermission(command.getPermission()))
				{
					sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				}
				else
				{
					command.onCommand(sender, args);
				}

				return true;
			}
		}
		else
		{
			if (args.length == 0) 
			{
				sender.sendMessage(sendHelpMessage("/lol i|item <item> <amount> <player>", ": Give items to players"));	
				sender.sendMessage(sendHelpMessage("/lol c|chest give <player> <chest>", ": Give chest to players"));	
			}
			else
			{
				SubCommand command = getCommand(args[0]);
				command.onCommand(sender, args);
			}
		}
		return true;
	}
	
	public String sendHelpMessage(String cmd, String desc)
	{
		return ChatColor.AQUA + cmd + ChatColor.GRAY + desc; 
	}
	
	private String aliases(SubCommand cmd) 
	{
		String fin = "";
		
		for (String a : cmd.getAliases()) 
		{
			fin += a + " | ";
		}
		
		return fin.substring(0, fin.lastIndexOf(" | "));
	}
	
	private SubCommand getCommand(String name) {
		for (SubCommand cmd : commands) {
			if (cmd.getClass().getSimpleName().equalsIgnoreCase(name)) 
				return cmd;
			for (String alias : cmd.getAliases()) 
				if (name.equalsIgnoreCase(alias)) 
					return cmd;
		}
		return null;
	}
}
package com.lolpvp.commands.classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.earth2me.essentials.Essentials;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.lolpvp.core.Core;

public class ClearChest implements CommandExecutor
{
	private File[] easyChestFiles;
	
	Multimap<String, String> ips;
	
	private Core plugin; 
	public ClearChest(Core core)
	{
//		playerFiles = new File(plugin.getDataFolder() + File.separator + "userdata2").listFiles();
		easyChestFiles = new File(Core.getEasyChests().getDataFolder() + File.separator + "userdata").listFiles();
		ips = ArrayListMultimap.create();
		this.plugin = core;
	}
	
//	public static void main(String[] args)
//	{
//		String uuid = "05c30200c2474e63b4ab8848d08d1e05";
//		//05c30200-c247-4e63-b4ab-8848d08d1e05
//		System.out.println("05c30200-c247-4e63-b4ab-8848d08d1e05");
//		System.out.println(uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20));
//	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{
		if(command.getName().equalsIgnoreCase("clearchests"))
		{
			if(!sender.isOp())
			{
				sender.sendMessage(ChatColor.RED + "You do not have permission for this command");
				return false;		
			}
			
			if(args.length >= 0)
			{
				
				Bukkit.broadcastMessage(ChatColor.GREEN + "------ Clearing Chests ------");
				for(File easyChestFile : easyChestFiles)
				{
					String uuid = easyChestFile.getName().replace(".yml", "");
					UUID fixedUUID = UUID.fromString(uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20));
					FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(easyChestFile);
									
					OfflinePlayer player = Bukkit.getOfflinePlayer(fixedUUID);
					
					Bukkit.broadcastMessage(fixedUUID.toString());
					
					if(player.hasPlayedBefore()) {
						PermissionUser user = PermissionsEx.getUser(player.getName());
						
						if(!(user.inGroup("Donator_Zero") || user.inGroup("Donator_One") || user.inGroup("Donator_Two") || user.inGroup("Donator_Three")
								|| user.inGroup("Donator_Four") || user.inGroup("Donator_Five") || user.inGroup("Youtube") || user.inGroup("OP")
								|| user.inGroup("OPCREATIVE") || user.inGroup("OPHEAD") || user.inGroup("OPMOD")
								|| user.inGroup("Admin") || user.inGroup("HeadAdmin") || user.inGroup("HeadAdminHidden")))
						{
							if(fileConfig.get("items") != null)
							{
								fileConfig.set("items", null);
								try {
									fileConfig.save(easyChestFile);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}		
					}
				}
				Bukkit.broadcastMessage(ChatColor.GREEN + "------ Cleared Chests ------");
				
//				Bukkit.broadcastMessage(ChatColor.GREEN + "------ Clearing Chest2s ------");
//				
//				for(File playerFile : playerFiles)
//				{
//					String uuid = playerFile.getName().replace(".yml", "");
//					FileConfiguration fileConfig = plugin.playerFile(uuid);
//					UUID fixedUUID = UUID.fromString(uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20));
//					OfflinePlayer player = Bukkit.getOfflinePlayer(fixedUUID);
//
//					if(player.hasPlayedBefore())
//					{
//						Bukkit.broadcastMessage(fixedUUID.toString());
//
//						PermissionUser user = PermissionsEx.getUser(player.getName());
//						if(!(user.inGroup("Donator_Five") || user.inGroup("Youtube") || user.inGroup("OP")
//								|| user.inGroup("OPCREATIVE") || user.inGroup("OPHEAD")
//								|| user.inGroup("Admin") || user.inGroup("HeadAdmin") || user.inGroup("HeadAdminHidden")))
//						{
//							if(fileConfig.get("chest") != null)
//							{
//								fileConfig.set("chest", null);
//								try {
//									fileConfig.save(playerFile);
//								} catch (IOException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							}
//						}	
//					}
//				}
//				Bukkit.broadcastMessage(ChatColor.GREEN + "------ Cleared Chest2s ------");
			}
		}
		else if(command.getName().equalsIgnoreCase("checkchests")) {

			if(!sender.isOp()) {
				sender.sendMessage(ChatColor.RED + "You do not have permission for this command");
				return false;		
			}
			
			for(File easyChestFile : easyChestFiles)
			{
				String uuid = easyChestFile.getName().replace(".yml", "");
				UUID fixedUUID = UUID.fromString(uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20));
								
				OfflinePlayer player = Bukkit.getOfflinePlayer(fixedUUID);
				
				if(player.hasPlayedBefore()) {
					PermissionUser user = PermissionsEx.getUser(player.getName());
					if((user.inGroup("Donator_Zero") || user.inGroup("Donator_One") || user.inGroup("Donator_Two") || user.inGroup("Donator_Three")
							|| user.inGroup("Donator_Four") || user.inGroup("Donator_Five") || user.inGroup("Youtube")))
					{
						Essentials essentials = Core.getPlugin(Essentials.class);
						if(essentials.getOfflineUser(player.getName()) != null) {
							String ip = essentials.getOfflineUser(player.getName()).getLastLoginAddress();
							ips.put(ip, player.getName());	
						}
					}		
				}
			}
			
			BufferedWriter output = null;
			try {
				File file = new File(this.plugin.getDataFolder() + File.separator + "Same IPs.txt");
				output = new BufferedWriter(new FileWriter(file));
				for(String ip : ips.keySet()) {
					if(ips.get(ip).size() > 1) {
						output.write(ip + " : " + ips.get(ip));
					}
				}
			} catch ( IOException e ) {
				e.printStackTrace();
			} finally {
				if ( output != null )
					try {
						output.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
			ips.clear();
		}
		return false;
	}
}

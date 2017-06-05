package com.lolpvp.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import com.lolpvp.core.Core;

public class UUIDLibrary
{
	private static Core plugin;

	public UUIDLibrary(Core instance)
	{
		plugin = instance;
	}

	public static String getNameFromUUID(String uuid)
	{
		FileConfiguration fc = plugin.playerFile(uuid);
		if ((fc.getString("uuid") != null) && 
				(fc.getString("uuid").contains("-"))) {
			Bukkit.broadcastMessage(fc.getString("uuid"));
			return Bukkit.getServer().getOfflinePlayer(UUID.fromString(fc.getString("uuid"))).getName();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static String getExactName(String name)
	{
		return Bukkit.getServer().getOfflinePlayer(name).getName();
	}

	@SuppressWarnings("deprecation")
	public static String getUUIDFromName(String name)
	{
		if (Bukkit.getServer().getOfflinePlayer(name).hasPlayedBefore()) {
			return Bukkit.getServer().getOfflinePlayer(name).getUniqueId().toString().replace("-", "");
		}
		return null;
	}
}

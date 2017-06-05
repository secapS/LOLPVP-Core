package com.lolpvp.chat;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.lolpvp.core.Core;

public class ChatMethod2
{
	private Core plugin;

	public ChatMethod2(Core plugin)
	{
		this.plugin = plugin;
	}

	public String filterTag(Player player)
	{
		FileConfiguration fc = this.plugin.playerFile(player);
		String tag = fc.getString("tag");
		if ((!player.hasPermission("lolpvp.owner")) && 
				(tag.length() > this.plugin.getConfig().getInt("max-normaltag-length"))) {
			if (!player.hasPermission("lolpvp.longtag")) {
				tag = tag.substring(0, this.plugin.getConfig().getInt("max-normaltag-length"));
			} else if (tag.length() > this.plugin.getConfig().getInt("max-longtag-length")) {
				tag = tag.substring(0, this.plugin.getConfig().getInt("max-longtag-length"));
			}
		}
		if (fc.getBoolean("magic"))
		{
			if (player.hasPermission("lolpvp.setmagic")) {
				tag = this.plugin.getChatMethod().getPrefix(player) + "&k" + tag;
			} else {
				tag = this.plugin.getChatMethod().getPrefix(player) + tag;
			}
		}
		else {
			tag = this.plugin.getChatMethod().getPrefix(player) + tag;
		}
		return tag;
	}

	public boolean hasTag(Player player)
	{
		FileConfiguration fc = this.plugin.playerFile(player);
		return fc.getString("tag") != null;
	}

	public void setStaff(Player player, String staff)
	{
		String path = this.plugin.getDataFolder().getAbsolutePath();
		String plugins = path.substring(0, path.lastIndexOf(File.separator));
		File folder = new File(plugins + File.separator + "PermissionsEx" + File.separator + "permissions.yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(folder);
		if (!Core.chat.getPlayerPrefix(player).equals(staff)) {
			if (fc.getString("users." + player.getUniqueId().toString()) != null) {
				fc.set("users." + player.getUniqueId().toString() + ".options.prefix", staff);
			} 
		}
		try
		{
			fc.save(folder);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void setPrefix(Player o)
	{
		String none = this.plugin.getConfig().getString("list-no-tag-prefix");
		String staff = null;
		if (this.plugin.getConfig().getString("groups." + Core.permission.getPrimaryGroup(o) + ".fixed-tag") != null) {
			staff = this.plugin.getConfig().getString("groups." + Core.permission.getPrimaryGroup(o) + ".fixed-tag");
		}
		if (!this.plugin.getConfig().getBoolean("groups." + Core.permission.getPrimaryGroup(o) + ".staff"))
		{
			if (hasTag(o))
			{
				String tag = this.plugin.getConfig().getString("list-prefix").replace("{TAG}", filterTag(o));
				setStaff(o, tag);
			}
			else
			{
				setStaff(o, none);
			}
		}
		else {
			setStaff(o, staff);
		}
	}
}

package com.lolpvp.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.lolpvp.core.Core;

public class AntiSpamBot implements Listener
{
	final Map<String, String> joined = new HashMap<>();

	Core plugin;
	
	public AntiSpamBot(Core core)
	{
		this.plugin = core;
	}

	@EventHandler
	public void onJoin(final PlayerJoinEvent event)
	{
		joined.put(event.getPlayer().getName(), RandomStringUtils.randomAlphanumeric(4));
		event.getPlayer().sendMessage(ChatColor.RED + "Please move or type in the captcha: " + joined.get(event.getPlayer().getName()));
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		if(joined.containsKey(event.getPlayer().getName()))
		{
			joined.remove(event.getPlayer().getName());
			event.getPlayer().sendMessage(ChatColor.GREEN+ "Thank you for verifying that you're not a bot.");
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event)
	{
		if(joined.containsKey(event.getPlayer().getName()))
			joined.remove(event.getPlayer().getName());
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event)
	{
		if(joined.containsKey(event.getPlayer().getName()))
		{
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "Please move or type in the captcha: " + joined.get(event.getPlayer().getName()));
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event)
	{
		if(joined.containsKey(event.getPlayer().getName()))
		{
			event.setCancelled(true);
			if(event.getMessage().equals(joined.get(event.getPlayer().getName())))
			{
				joined.remove(event.getPlayer().getName());
				event.getPlayer().sendMessage(ChatColor.GREEN+ "Thank you for verifying that you're not a bot.");
			}
			else
			{
				event.getPlayer().sendMessage(ChatColor.RED + "Please move or type in the captcha: " + joined.get(event.getPlayer().getName()));
			}
		}
	}
}

package com.lolpvp.commands;

import org.bukkit.command.CommandSender;

public abstract class SubCommand 
{
	public abstract void onCommand(CommandSender sender, String[] args);
	
	private String message, usage, permission;
	private String[] aliases;
	private boolean hasPermission;
	
	public SubCommand(String message, String usage, String... aliases) 
	{
		this.message = message;
		this.usage = usage;
		this.aliases = aliases;
		this.hasPermission = false;
	}
	
	public SubCommand(String message, String permission, String usage, String... aliases) 
	{
		this.message = message;
		this.usage = usage;
		this.aliases = aliases;
		this.hasPermission = true;
		this.permission = permission;
	}
	
	public final String getMessage() 
	{
		return message;
	}
	
	public final String getUsage() 
	{
		return usage;
	}
	
	public final String[] getAliases() 
	{
		return aliases;
	}
	
	public final String getPermission()
	{
		return permission;
	}
	
	public final boolean hasPermission()
	{
		return hasPermission;
	}
}
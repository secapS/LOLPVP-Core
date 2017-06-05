package com.lolpvp.commands.trade;

public class Trade 
{
	private String name;
	private String permission;
	private String group;
	private String[] permissions;
	private boolean isGroup;
	private boolean hasMultiplePermissions;
	
	public Trade(String name, String permission)
	{
		this.name = name;
		this.permission = permission;
		this.hasMultiplePermissions = false;
		this.isGroup = false;
	}
	
	public Trade(String name, String... permissions){
		this.name = name;
		this.permissions = permissions;
		this.hasMultiplePermissions = true;
		this.isGroup = false;
	}
	
	public Trade(String name, String group, boolean isGroup)
	{
		this.name = name;
		this.group = group;
		this.hasMultiplePermissions = false;
		this.isGroup = isGroup;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getGroup()
	{
		return this.group;
	}
	
	public String[] getPermissions()
	{
		return this.permissions;
	}
	
	public String getPermission()
	{
		return this.permission;
	}
	
	public boolean isGroup()
	{
		return isGroup;
	}
	
	public boolean hasMultiplePermissions()
	{
		return hasMultiplePermissions;
	}
	
	public void setName(String newName)
	{
		this.name = newName;
	}
	
	public void setGroup(String newGroup)
	{
		this.group = newGroup;
	}
	
	public void setPermission(String newPermission)
	{
		this.permission = newPermission;
	}
}
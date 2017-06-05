package com.lolpvp.core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.lolpvp.chat.ChatFix;
import com.lolpvp.chat.ChatMethod;
import com.lolpvp.chat.ChatMethod2;
import com.lolpvp.chests.Chest;
import com.lolpvp.chests.ChestListener;
import com.lolpvp.chests.ChestManager;
import com.lolpvp.commands.LOLPVPCommand;
import com.lolpvp.commands.classes.ClearChat;
import com.lolpvp.commands.classes.ClearChest;
import com.lolpvp.commands.classes.Disposal;
import com.lolpvp.commands.classes.MuteAll;
import com.lolpvp.commands.classes.PowerTool;
import com.lolpvp.commands.classes.Who;
import com.lolpvp.commands.kits.Kits;
import com.lolpvp.commands.trade.TradeCommand;
import com.lolpvp.commands.trade.TradeManager;
import com.lolpvp.redeemer.PerkBookCommand;
import com.lolpvp.redeemer.PerkBookManager;
import com.lolpvp.signs.BallerSign;
import com.lolpvp.signs.BuySigns;
import com.lolpvp.signs.SignSettingsManager;
import com.lolpvp.utils.AntiSpamBot;
import com.lolpvp.utils.UUIDLibrary;
import com.lolpvp.virtualchest.VirtualChest;
import com.lolpvp.virtualchest.VirtualChestListener;
import com.lolpvp.virtualchest.VirtualChestManager;
import com.lolpvp.votifier.VotesTop;
import com.lolpvp.votifier.VotifierListener;
import com.lolpvp.weapons.ItemManager;
import com.lolpvp.weapons.classes.InvisRing;
import com.lolpvp.weapons.classes.LOLSword;
import com.lolpvp.weapons.classes.MoneyBag;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class Core extends JavaPlugin implements Listener
{
	private static Economy econ = null;
	private ChatMethod chatmethod;
	private ChatMethod2 chatmethod2;
	private ChatFix chatFix;	
	public static Chat chat = null;
	private VotesTop votestop = null;
	private VotifierListener votifierListener = null;
	public static Permission permission = null;
	public MuteAll muteAll = null;
	
	private ClearChest clearChests = new ClearChest(this);
	
	private static Core instance;
	
	
//	private NewChat newChat = null;
	
	@Override
	public void onEnable()
	{
		instance = this;
		PerkBookManager.setup();
		ItemManager.setup(this);
		this.setupChat();
		new UUIDLibrary(this);
		muteAll = new MuteAll(this);
		votestop = new VotesTop(this);
		votifierListener = new VotifierListener(this);
		chatFix = new ChatFix(this);
		chatmethod = new ChatMethod(this);
		chatmethod2 = new ChatMethod2(this);
		
//		newChat = new NewChat(this);
		this.setupPermissions();
		TradeManager.getInstance().setupTrades(this);
		ItemManager.getInstance().registerItems(this);
		ChestManager.setup(this);
		Chest.setup(this);
		VirtualChestManager.setup(this);
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		reloadConfig();
		SignSettingsManager.getInstance().setup(this);
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getPluginManager().registerEvents(new ChestListener(this), this);
		this.getServer().getPluginManager().registerEvents(new VirtualChestListener(), this);
		this.getServer().getPluginManager().registerEvents(new PowerTool(this), this);
		this.getServer().getPluginManager().registerEvents(new BuySigns(this), this);
		this.getServer().getPluginManager().registerEvents(this.chatFix, this);
		
//		this.getServer().getPluginManager().registerEvents(this.newChat, this);
		this.getServer().getPluginManager().registerEvents(this.votifierListener, this);
		this.getServer().getPluginManager().registerEvents(new BallerSign(), this);
		this.getServer().getPluginManager().registerEvents(new AntiSpamBot(this), this);
		this.getServer().getPluginManager().registerEvents(muteAll, this);
		this.getCommand("lol").setExecutor(new LOLPVPCommand());
		this.getCommand("regen").setExecutor(new Kits());
		this.getCommand("ironman").setExecutor(new Kits());
		this.getCommand("i2").setExecutor(new Kits());
		this.getCommand("nightvision").setExecutor(new Kits());
		this.getCommand("fireresistance").setExecutor(new Kits());
		this.getCommand("invis").setExecutor(new Kits());
		this.getCommand("chest2").setExecutor(new VirtualChest(this));
		this.getCommand("clearchest2").setExecutor(new VirtualChest(this));
		this.getCommand("pt").setExecutor(new PowerTool(this));
		this.getCommand("clearpt").setExecutor(new PowerTool(this));
		this.getCommand("qf").setExecutor(new BuySigns(this));
//		this.getCommand("lolt").setExecutor(newChat);
		
		this.getCommand("lolt").setExecutor(this.chatFix);
		this.getCommand("loltd").setExecutor(this.chatFix);
		this.getCommand("loltr").setExecutor(this.chatFix);
		
//		this.getCommand("lolto").setExecutor(newChat);
		
		this.getCommand("lolto").setExecutor(this.chatFix);
		
//		this.getCommand("lolnt").setExecutor(this.chatFix);
//		this.getCommand("lolrnt").setExecutor(this.chatFix);
//		this.getCommand("lolm").setExecutor(newChat);
		
		this.getCommand("lolm").setExecutor(this.chatFix);
		
		this.getCommand("who").setExecutor(new Who(this));
		this.getCommand("votes").setExecutor(this.votifierListener);
		this.getCommand("resetvotes").setExecutor(this.votifierListener);
		this.getCommand("ballersign").setExecutor(new BallerSign());
		this.getCommand("clearchat").setExecutor(new ClearChat(this));
		this.getCommand("muteall").setExecutor(muteAll);
		this.getCommand("redeem").setExecutor(new PerkBookCommand());
		this.getCommand("pgive").setExecutor(new PerkBookCommand());
		this.getCommand("dispose").setExecutor(new Disposal());
		this.getCommand("loltrade").setExecutor(new TradeCommand());
		this.getCommand("lolsword").setExecutor(new LOLSword(this));
		this.getCommand("resetmoneybag").setExecutor(new MoneyBag(this));
		this.getCommand("invisring").setExecutor(new InvisRing(this));
		this.getCommand("clearchests").setExecutor(this.clearChests);
		this.getCommand("checkchests").setExecutor(this.clearChests);
//		this.getCommand("setnick").setExecutor(this.newChat);
		if (!setupEconomy())
		{
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		this.getServer().getPluginManager().registerEvents(this, this);
		
	}
	
	public static Core getInstance() {
		return instance;
	}
	
//	@Override
//	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
//	{
//		return false;
//	}

	@Override
	public void onDisable()
	{
		ItemManager.getInstance().getItems().clear();
	}

	private static WorldGuardPlugin getWorldGuard() {
		final Plugin plugin = Bukkit.getServer().getPluginManager()
				.getPlugin("WorldGuard");
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null;
		}

		return (WorldGuardPlugin) plugin;
	}

	public static boolean isPlayerInPVP(Player player){
		final RegionManager regionManager = getWorldGuard().getRegionManager(player.getLocation().getWorld());
		final ApplicableRegionSet set = regionManager.getApplicableRegions(player.getLocation());
		final LocalPlayer localPlayer = getWorldGuard().wrapPlayer(player);
		return set.testState(localPlayer, DefaultFlag.PVP);
	}
	
	public static boolean isEntityInPVP(Entity entity){
		final RegionManager regionManager = getWorldGuard().getRegionManager(entity.getLocation().getWorld());
		final ApplicableRegionSet set = regionManager.getApplicableRegions(entity.getLocation());
		return set.testState(null, DefaultFlag.PVP);
	}

	public static boolean canBuildHere(Player player, Location location) {
		if (location == null) {
			return true;
		}

		final WorldGuardPlugin wg = getWorldGuard();
		return wg == null || wg.canBuild(player, location);
	}
	
	public static me.whatatopic.lolpvplevels.Main getLevels()
	{
		return (me.whatatopic.lolpvplevels.Main)getPlugin(me.whatatopic.lolpvplevels.Main.class);
	}
	
	public static me.whatatopic.lolpvpteams.Teams getTeams()
	{
		return (me.whatatopic.lolpvpteams.Teams)getPlugin(me.whatatopic.lolpvpteams.Teams.class);
	}
	
	public static me.MirrorRealm.chest.Main getEasyChests()
	{
		return ( me.MirrorRealm.chest.Main)getPlugin( me.MirrorRealm.chest.Main.class);
	}
	
//	public void removePVPRegions(final Player player)
//	{
//		final HashMap<ProtectedRegion, Map<Flag<?>, Object>> oldFlags = new HashMap<>();
//		
//		HashMap<Flag<?>, Object> flags = new HashMap<Flag<?>, Object>();
//		final RegionManager regionManager = getWorldGuard().getRegionManager(this.getServer().getWorld("world"));
//		for(ProtectedRegion region : regionManager.getRegions().values())
//		{
//			oldFlags.put(region, region.getFlags());
//			flags.put(DefaultFlag.PVP, StateFlag.State.ALLOW);
//			if(region.getFlags().containsKey(DefaultFlag.BUILD) && region.getFlag(DefaultFlag.BUILD).equals(StateFlag.State.DENY))
//				flags.put(DefaultFlag.BUILD, StateFlag.State.DENY);
//			region.setFlags(flags);
//			flags.clear();
//		}
//		
//		player.sendMessage("Removed all PVP flags");
//		
//		this.getServer().getScheduler().runTaskLater(this, new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				for(ProtectedRegion region : regionManager.getRegions().values())
//				{
//					region.setFlags(oldFlags.get(region));
//				}
//				player.sendMessage("All region flags restored");
//				oldFlags.clear();
//			}
//		}, 20L);
//	}

	public static boolean canBuildHere(Player player, Block block) {
		if (block == null) {
			return true;
		}

		final WorldGuardPlugin wg = getWorldGuard();
		return wg == null || wg.canBuild(player, block);
	}

	public static boolean safeSetBlock(Player player, Block block, Material type) {
		if (!canBuildHere(player, block)) {
			return false;
		}

		block.setType(type);

		return true;
	}

	public VotesTop getVotesTop()
	{
		return this.votestop;
	}

	public ChatMethod getChatMethod()
	{
		return this.chatmethod;
	}

	public ChatMethod2 getChatMethod2()
	{
		return this.chatmethod2;
	}

	private boolean setupChat()
	{
		RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
		chat = (Chat)rsp.getProvider();
		return chat != null;
	}

	public FileConfiguration pvpFile()
	{
		File playerDir = new File(getDataFolder() + File.separator + "pvpchest.yml");
		return YamlConfiguration.loadConfiguration(playerDir);
	}

	public File pvpData()
	{
		return new File(getDataFolder() + File.separator + "pvpchest.yml");
	}

	public FileConfiguration playerFile(String s)
	{
		File playerDir = new File(getDataFolder() + File.separator + "userdata2" + File.separator + s + ".yml");
		return YamlConfiguration.loadConfiguration(playerDir);
	}

	public FileConfiguration playerFile(Player player)
	{
		return playerFile(player.getUniqueId().toString().replace("-", ""));
	}

	public FileConfiguration playerFile(OfflinePlayer player)
	{
		return playerFile(player.getUniqueId().toString().replace("-", ""));
	}

	public File playerData(String s)
	{
		return new File(getDataFolder() + File.separator + "userdata2" + File.separator + s + ".yml");
	}

	public File playerData(Player player)
	{
		return playerData(player.getUniqueId().toString().replace("-", ""));
	}

	public File playerData(OfflinePlayer player)
	{
		return playerData(player.getUniqueId().toString().replace("-", ""));
	}

	private boolean setupEconomy()
	{
		if (getServer().getPluginManager().getPlugin("Vault") == null)
		{
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null)
		{
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	private boolean setupPermissions()
	{
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		permission = (Permission)rsp.getProvider();
		return permission != null;
	}

	public static Economy getEconomy()
	{
		return econ;
	}
	
	private String mainDirectory;
	private File customConfigFile = null;
	private FileConfiguration customConfig = null;

	public void reloadCustomConfig() {
		mainDirectory = this.getDataFolder().getPath();
		customConfigFile = new File(String.valueOf(mainDirectory) + "/MoneyBagConfig.yml");
		new File(mainDirectory).mkdir();
		if (!customConfigFile.exists()) 
		{
			try 
			{
				customConfigFile.createNewFile();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
			customConfig.addDefault("Main.Items.example.Percentage", "100");
			customConfig.addDefault("Main.Items.example.Name", "Example Name");
			customConfig.createSection("Main.Items.example.Commands");
			List<String> commands = getCustomConfig().getStringList("Main.Items.example.Commands");
			commands.add("/EXAMPLECOMMAND {player}");
			commands.add("/EXAMPLECOMMAND {player}");
			customConfig.set("Main.Items.example.Commands", commands);
			customConfig.addDefault("Main.Broadcast Message", "&a{player} just looked in their Money Bag and received a random prize. A {item}! Money Bags are available for top donators, if you think you have what it takes visit buy.lolpvp.com and work your way up to the top spot now!");
			customConfig.options().copyDefaults(true);
			try {
				customConfig.save(customConfigFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
	}
	public FileConfiguration getCustomConfig() {
		if (customConfig == null) 
		{
			reloadCustomConfig();
		}
		return customConfig;
	}
	public void saveCustomConfig() {
		if (customConfig == null || customConfigFile == null) {
			return;
		}
		try 
		{
			getCustomConfig().save(customConfigFile);
		} 
		catch (IOException ex) 
		{
			getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
		}
	}
}
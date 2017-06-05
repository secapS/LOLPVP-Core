package com.lolpvp.votifier;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.lolpvp.core.Core;
import com.lolpvp.utils.UUIDLibrary;

public class VotesTop
{
	private Core plugin;

	public VotesTop(Core plugin)
	{
		this.plugin = plugin;
	}

	public final Map<String, Integer> votes = new HashMap<>();

	public void putAll()
	{
		String path = this.plugin.getDataFolder().getAbsolutePath();
		String plugins = path.substring(0, path.lastIndexOf(File.separator));
		File users = new File(plugins + File.separator + "LOLPVP", "userdata2");
		if (users.exists()) {
			try
			{
				for (File file : users.listFiles())
				{
					YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
					int i = config.getInt("votes");
					if (config.getString("uuid") != null) {
						this.votes.put(UUIDLibrary.getNameFromUUID(config.getString("uuid")), Integer.valueOf(i));
					}
				}
			}
			catch (NullPointerException e)
			{
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map sortByComparator(Map unsortMap)
	{
		List list = new LinkedList(unsortMap.entrySet());
		Collections.sort(list, new Comparator()
		{
			public int compare(Object o1, Object o2)
			{
				return ((Comparable)((Map.Entry)o1).getValue()).compareTo(((Map.Entry)o2).getValue());
			}
		});
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();)
		{
			Map.Entry entry = (Map.Entry)it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	@SuppressWarnings("unchecked")
	public void onSort(Player player)
	{
		Map<String, Integer> sortedMap = sortByComparator(this.votes);
		String msg = this.plugin.getConfig().getString("votestop");
		List<String> strings = new ArrayList<>();
		int count = 0;
		for (Map.Entry<String, Integer> s : sortedMap.entrySet())
		{
			count++;
			strings.add(msg.replace("{NAME}", UUIDLibrary.getExactName((String)s.getKey())).replace("{VOTES}", Integer.toString(((Integer)s.getValue()).intValue())));
			if (count == 10) {
				break;
			}
		}
		player.sendMessage("test1");
		count = 0;
		for (String fin : strings)
		{
			count++;
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', fin.replace("{RANK}", Integer.toString(count))));
		}
	}
}
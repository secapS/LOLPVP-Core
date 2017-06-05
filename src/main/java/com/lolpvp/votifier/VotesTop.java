package com.lolpvp.votifier;

import com.lolpvp.core.Core;
import com.lolpvp.utils.UUIDLibrary;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class VotesTop
{
	private Core plugin;

	public VotesTop(Core plugin)
	{
		this.plugin = plugin;
	}

	public final Map<String, Integer> votes = new HashMap<>();

	private Map sortByComparator(Map unsortMap)
	{
		List list = new LinkedList(unsortMap.entrySet());
		Collections.sort(list, (o1, o2) -> ((Comparable)((Map.Entry)o1).getValue()).compareTo(((Map.Entry)o2).getValue()));
		Map sortedMap = new LinkedHashMap();
		for (Object aList : list) {
			Map.Entry entry = (Map.Entry) aList;
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

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
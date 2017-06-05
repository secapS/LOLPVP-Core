package com.lolpvp.votifier;

import com.lolpvp.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class VotesManager {

    private Core plugin;

    public VotesManager(Core instance) {
        this.plugin = instance;
        loadVotes();
    }

    public void checkVote(UUID uuid) {
        FileConfiguration fc = this.plugin.playerData(uuid);
        if (fc.get("votes") == null) {
            fc.set("votes", Integer.valueOf(1));
        }
        else {
            fc.set("votes", Integer.valueOf(fc.getInt("votes") + 1));
        }
        if (this.plugin.getConfig().getStringList("votes." + fc.getInt("votes") + "-votes") != null) {
            Player player = Bukkit.getServer().getPlayer(uuid);
            giveReward(player);
        }
        try {
            fc.save(this.plugin.playerFile(uuid));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void giveReward(Player player) {
        FileConfiguration fc = this.plugin.playerData(player.getUniqueId());
        if (player != null) {
            for (String rewardCommands : this.plugin.getConfig().getStringList("votes." + fc.getInt("votes") + "-votes")) {
                String command = rewardCommands.replace("{PLAYER}", player.getName());
                this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), command);
            }
        } else {
            fc.set("pending-command", this.plugin.getConfig().getStringList("votes." + fc.getInt("votes") + "-votes"));
        }
    }

    public final Map<UUID, Integer> votes = new HashMap<>();

    public SortedMap<UUID, Integer> sortVotes() {
        SortedMap<UUID, Integer> sortedVotes = new TreeMap<UUID, Integer>(new ValueComparator(votes));
        sortedVotes.putAll(votes);
        return sortedVotes;
    }

    public void loadVotes() {
        File users = new File(this.plugin.getDataFolder(), "userdata");
        if (users.exists()) {
            Arrays.stream(users.listFiles()).forEach(file -> {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                votes.put(UUID.fromString(users.getName()), Integer.valueOf(config.getInt("votes")));
            });
        }
    }
}

class ValueComparator implements Comparator<UUID> {
    Map<UUID, Integer> base;

    public ValueComparator(Map<UUID, Integer> base) {
        this.base = base;
    }

    @Override
    public int compare(UUID a, UUID b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}

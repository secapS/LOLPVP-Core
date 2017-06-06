package com.lolpvp.votifier;

import com.lolpvp.core.Core;
import com.lolpvp.utils.UUIDFetcher;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class VotifierListener implements Listener {
	private Core plugin;

	public VotifierListener(Core plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		final FileConfiguration fc = this.plugin.playerData(event.getPlayer());
		if (fc.getString("votes") == null) {
			fc.set("votes", Integer.valueOf(0));
		}
		fc.set("uuid", player.getUniqueId().toString());
		if (fc.getStringList("pending-command") != null) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
                for (String s : fc.getStringList("pending-command")) {
                    this.plugin.getVotesManager().giveReward(player);
                }
                fc.set("pending-command", null);
                try {
                    fc.save(VotifierListener.this.plugin.playerFile(event.getPlayer()));
                }
                catch (IOException exception) {
                    exception.printStackTrace();
                    this.plugin.getLogger().log(Level.WARNING, "Couldn't save " + event.getPlayer().getName() + "'s player data.");
                }
            }, 40L);
		}
		try {
			fc.save(this.plugin.playerFile(event.getPlayer()));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

    public Map<UUID, Integer> getAllVotes() {
	    Map<UUID, Integer> allVotes = new HashMap<>();
        File users = new File(this.plugin.getDataFolder(), "userdata");
        if (users.exists()) {
            Arrays.stream(users.listFiles()).forEach(file -> {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                allVotes.put(UUID.fromString(users.getName()), Integer.valueOf(config.getInt("votes")));
            });
        }
        return allVotes;
    }

	@EventHandler(priority=EventPriority.NORMAL)
	public void onVote(VotifierEvent event) {
        try {
            this.plugin.getVotesManager().checkVote(UUIDFetcher.getUUIDOf(event.getVote().getUsername()));
        } catch (Exception e) {
            this.plugin.getLogger().log(Level.WARNING, "Couldn't find UUID of the Username: " + event.getVote().getUsername());
        }
    }
}
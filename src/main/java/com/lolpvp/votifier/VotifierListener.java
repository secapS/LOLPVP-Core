package com.lolpvp.votifier;

import com.lolpvp.core.Core;
import com.lolpvp.utils.UUIDFetcher;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
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
                    giveReward(player);
                }
                fc.set("pending-command", null);
                try {
                    fc.save(VotifierListener.this.plugin.playerFile(event.getPlayer()));
                }
                catch (IOException exception) {
                    exception.printStackTrace();
                }
            }, 40L);
		}
		try {
			fc.save(this.plugin.playerFile(event.getPlayer()));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	private void checkVote(UUID uuid) {
		FileConfiguration fc = this.plugin.playerData(uuid);
		if (fc.get("votes") == null) {
			fc.set("votes", Integer.valueOf(1));
		}
		else {
			this.plugin.getVotesTop().votes.put(uuid, Integer.valueOf(fc.getInt("votes") + 1));
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

	private void giveReward(Player player) {
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

	@EventHandler(priority=EventPriority.NORMAL)
	public void onVote(VotifierEvent event) {
        try {
            checkVote(UUIDFetcher.getUUIDOf(event.getVote().getUsername()));
        } catch (Exception e) {
            this.plugin.getLogger().log(Level.WARNING, "Couldn't find UUID of the Username: " + event.getVote().getUsername());
        }
    }
}
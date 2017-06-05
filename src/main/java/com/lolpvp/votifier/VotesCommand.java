package com.lolpvp.votifier;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.lolpvp.core.Core;
import com.lolpvp.utils.UUIDFetcher;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

@CommandAlias("votes")
public class VotesCommand extends BaseCommand {

    private Core plugin;

    public VotesCommand(Core instance) {
        this.plugin = instance;
    }


    @Default
    @CommandPermission("lolpvp.votes")
    public void onDefault(Player player, @Optional String otherPlayer) {
        FileConfiguration playerData;
        OfflinePlayer offlinePlayer = null;
        try {
            offlinePlayer = this.plugin.getServer().getOfflinePlayer(UUIDFetcher.getUUIDOf(otherPlayer));
        } catch (Exception e) {
            e.printStackTrace();
            this.plugin.getLogger().log(Level.WARNING, "Couldn't find UUID of the Username: " + otherPlayer);
        }
        if(otherPlayer != null && player.hasPermission("lolpvp.votes.others") && offlinePlayer != null) {
            playerData = this.plugin.playerData(offlinePlayer);
        } else {
            playerData = this.plugin.playerData(player);
            player.sendMessage(ChatColor.AQUA + "10" + ChatColor.GRAY + " votes: " + ChatColor.AQUA + "10 diamonds.");
            player.sendMessage(ChatColor.AQUA + "25" + ChatColor.GRAY + " votes: " + ChatColor.AQUA + "$10,000 ingame money.");
            player.sendMessage(ChatColor.AQUA + "50" + ChatColor.GRAY + " votes: " + ChatColor.AQUA + "100 diamonds.");
            player.sendMessage(ChatColor.AQUA + "75" + ChatColor.GRAY + " votes: " + ChatColor.AQUA + "$100,000 ingame money.");
            player.sendMessage(ChatColor.AQUA + "100" + ChatColor.GRAY + " votes: " + ChatColor.AQUA + "50 OP Apples.");
            player.sendMessage(ChatColor.AQUA + "125" + ChatColor.GRAY + " votes: " + ChatColor.AQUA + "$25 donation voucher.");
            int votes = playerData.getInt("votes");
            player.sendMessage(ChatColor.GRAY + "Total votes for this month: " + ChatColor.AQUA + votes);
        }
    }

    @Subcommand("top|t")
    @CommandPermission("lolpvp.votes.top")
    public void onVotesTop(Player player) {
        this.plugin.getVotesTop().onSort(player);
    }

    @Subcommand("reset")
    @CommandPermission("")
    public void onResetVotes(Player player) {
        player.sendMessage(ChatColor.GREEN + "Everyone's votes has been reset.");
        File users = new File(this.plugin.getDataFolder(), "userdata");
        if (users.exists()) {
            for (File file : users.listFiles()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                try {
                    config.set("votes", Integer.valueOf(0));
                    config.set("pending-commands", null);
                    config.save(file);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

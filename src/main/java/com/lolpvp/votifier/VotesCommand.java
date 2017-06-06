package com.lolpvp.votifier;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.lolpvp.core.Core;
import com.lolpvp.core.Permissions;
import com.lolpvp.utils.UUIDFetcher;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.UUID;
import java.util.logging.Level;

@CommandAlias("votes")
public class VotesCommand extends BaseCommand {

    private Core plugin;

    public VotesCommand(Core instance) {
        this.plugin = instance;
    }


    @Default
    @CommandPermission(Permissions.VOTES)
    public void onDefault(Player player, @Optional String otherPlayer) {
        FileConfiguration playerData;
        OfflinePlayer offlinePlayer = null;
        try {
            offlinePlayer = this.plugin.getServer().getOfflinePlayer(UUIDFetcher.getUUIDOf(otherPlayer));
        } catch (Exception e) {
            e.printStackTrace();
            this.plugin.getLogger().log(Level.WARNING, "Couldn't find UUID of the Username: " + otherPlayer);
        }
        if(otherPlayer != null && player.hasPermission(Permissions.VOTES_OTHERS) && offlinePlayer != null) {
            playerData = this.plugin.playerData(offlinePlayer);
        } else {
            playerData = this.plugin.playerData(player);
            for(String voteAmount : this.plugin.getConfig().getConfigurationSection("votes").getKeys(false)) {
                String amount = voteAmount.split("-")[0];

            }
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
    @CommandPermission(Permissions.VOTES_TOP)
    public void onVotesTop(Player player) {
        SortedMap<UUID, Integer> sortedMap = this.plugin.getVotesManager().sortVotes();
        int rank = 1;
        for(UUID votedPlayers : sortedMap.keySet()) {
            String message = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("votestop")
                    .replace("{RANK}", Integer.valueOf(rank).toString())
                    .replace("{PLAYER}", this.plugin.getServer().getOfflinePlayer(votedPlayers).getName())
                    .replace("{VOTES}", sortedMap.get(votedPlayers).toString()));
            player.sendMessage(message);
            rank++;
        }
    }

    @Subcommand("reset")
    @CommandPermission(Permissions.VOTES_RESET)
    public void onResetVotes(Player player) {
        player.sendMessage(ChatColor.GREEN + "Everyone's votes has been reset.");
        File users = new File(this.plugin.getDataFolder(), "userdata");
        if (users.exists()) {
            Arrays.stream(users.listFiles()).forEach( file -> {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                config.set("votes", Integer.valueOf(0));
                config.set("pending-commands", null);
                try {
                    config.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    this.plugin.getLogger().log(Level.WARNING, "Couldn't save " + player.getName() + "'s data file.");
                }
            });
        }
    }
}

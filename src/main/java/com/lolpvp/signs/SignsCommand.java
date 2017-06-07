package com.lolpvp.signs;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Single;
import co.aikar.commands.annotation.Subcommand;
import com.lolpvp.core.Core;
import com.lolpvp.core.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

@CommandAlias("commandsign")
public class SignsCommand extends BaseCommand {

    private Core plugin;

    public SignsCommand(Core instance) {
        this.plugin = instance;
    }

    @Subcommand("create|c")
    @CommandPermission(Permissions.SIGNS_CREATE)
    public void onCreate(Player player, int price, @Single String command) {
        if((player.getTargetBlock(null, 6).getType().equals(Material.SIGN)) || (player.getTargetBlock(null, 6).getType().equals(Material.SIGN_POST)) || (player.getTargetBlock(null, 6).getType().equals(Material.WALL_SIGN))) {
            Sign sign = (Sign)(player.getTargetBlock(null, 6)).getState();
            this.plugin.getSignsManager().createCommandSign(sign, command.replace("/", ""), price);
            this.plugin.getSignsManager().reloadSignData();
            player.sendMessage(ChatColor.GREEN + "Created command sign successfully.");
        }
    }

    @Subcommand("addcommand|ac")
    @CommandPermission(Permissions.SIGNS_ADD_COMMAND)
    public void onAddCommand(Player player, @Single String command) {
        if((player.getTargetBlock(null, 6).getType().equals(Material.SIGN)) || (player.getTargetBlock(null, 6).getType().equals(Material.SIGN_POST)) || (player.getTargetBlock(null, 6).getType().equals(Material.WALL_SIGN))) {
            Sign sign = (Sign)(player.getTargetBlock(null, 6)).getState();
            if(this.plugin.getSignsManager().isCommandSign(sign)) {
                this.plugin.getSignsManager().addCommandToSign(sign, command.replace("/", ""));
                player.sendMessage(ChatColor.GREEN + "Added command to sign successfully.");
                this.plugin.getSignsManager().reloadSignData();
            }
        }
    }

}

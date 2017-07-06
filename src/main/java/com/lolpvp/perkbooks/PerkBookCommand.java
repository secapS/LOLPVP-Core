package com.lolpvp.perkbooks;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.lolpvp.core.Core;
import com.lolpvp.core.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Arrays;

@CommandAlias("perkbook")
public class PerkBookCommand extends BaseCommand {

    private Core plugin;

    public PerkBookCommand(Core instance) {
        this.plugin = instance;
    }

    @Subcommand("give")
    @CommandPermission(Permissions.PREFIX + "perkbook.give")
    public void givePerkBook(Player player, String perkbook) {
        this.plugin.getPerkBookManager().givePerkBook(player, perkbook);
    }

    @Subcommand("create")
    @CommandPermission(Permissions.PREFIX + "perkbook.create")
    public void createPerkBook(Player player, String type, @Split(" ") String[] permissions) {
        if(player.getItemInHand() == null || player.getItemInHand().getType() != Material.WRITTEN_BOOK) {
            player.sendMessage(ChatColor.RED + "You must hold a written book in your hand.");
            return;
        }

        this.plugin.getPerkBookManager().createPerkBook(((BookMeta)player.getItemInHand().getItemMeta()), PerkBook.Type.valueOf(type), Arrays.asList(permissions));
    }
}

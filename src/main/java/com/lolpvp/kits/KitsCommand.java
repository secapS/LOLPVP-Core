package com.lolpvp.kits;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.lolpvp.core.Core;
import com.lolpvp.core.Permissions;
import org.bukkit.entity.Player;

@CommandAlias("kit")
public class KitsCommand extends BaseCommand {

    private Core plugin;

    public KitsCommand(Core instance) {
        this.plugin = instance;
    }

    @Default
    public void onDefault(Player player, String kit) {
        this.plugin.getKitsManager().giveKit(kit, player);
    }

    @Subcommand("create")
    @CommandPermission(Permissions.PREFIX + "kits.create")
    public void onCreateKit(Player player, String kit, @Split String[] potionEffects) {
        this.plugin.getKitsManager().saveKit(this.plugin.getKitsManager().createKit(kit, player, potionEffects));
        this.plugin.getKitsManager().reloadKitData();
    }

    @Subcommand("delete")
    @CommandPermission(Permissions.PREFIX + "kits.delete")
    public void onDeleteKit(Player player, String kit) {
        this.plugin.getKitsManager().removeKit(kit);
        this.plugin.getKitsManager().reloadKitData();
    }
}

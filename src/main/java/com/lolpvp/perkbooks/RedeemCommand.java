package com.lolpvp.perkbooks;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.lolpvp.core.Core;
import org.bukkit.entity.Player;

@CommandAlias("redeem")
public class RedeemCommand extends BaseCommand {
    private Core plugin;

    public RedeemCommand(Core instance) {
        this.plugin = instance;
    }

    @Default
    public void redeemPerkBook(Player player) {
        this.plugin.getPerkBookManager().redeemPerkBook(player);
    }
}
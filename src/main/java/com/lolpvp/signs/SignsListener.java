package com.lolpvp.signs;

import com.lolpvp.core.Core;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignsListener implements Listener {

    private Core plugin;

    public SignsListener(Core instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign)event.getClickedBlock().getState();
                if(this.plugin.getSignsManager().isCommandSign(sign)) {
                    this.plugin.getSignsManager().executeSignCommands(sign, event.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(event.getBlock().getState() instanceof Sign) {
            Sign sign = (Sign)event.getBlock().getState();
            if(this.plugin.getSignsManager().isCommandSign(sign) && event.getPlayer().hasPermission("")) {
                this.plugin.getSignsManager().removeCommandSign(sign);
            }
        }
    }
}

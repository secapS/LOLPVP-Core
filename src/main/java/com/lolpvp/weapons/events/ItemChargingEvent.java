package com.lolpvp.weapons.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ItemChargingEvent extends Event implements Cancellable {
    private boolean cancelled = false;

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean value) {
        this.cancelled = value;
    }
}

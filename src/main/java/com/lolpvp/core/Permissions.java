package com.lolpvp.core;

import org.bukkit.entity.Player;

public enum Permissions {


    VOTES("votes"),
    VOTES_OTHERS(VOTES.toString() + ".others"),
    VOTES_TOP(VOTES.toString() + ".top"),
    VOTES_RESET(VOTES.toString() + ".reset"),
    SIGNS("signs"),
    SIGNS_CREATE(SIGNS.toString() + ".create"),
    SIGNS_DESTROY(SIGNS.toString() + ".destroy"),
    SIGNS_ADD_COMMAND(SIGNS.toString() + ".add-command");

    private final String PREFIX = "lolpvp.";

    private String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public boolean has(Player player) {
        return player.hasPermission(this.toString());
    }

    @Override
    public String toString() {
        return PREFIX + this.permission;
    }
}

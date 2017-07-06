package com.lolpvp.core;

import org.bukkit.entity.Player;

public enum Permissions {


    VOTES("votes"),
    VOTES_OTHERS(VOTES.toString() + ".others"),
    VOTES_TOP(VOTES.toString() + ".top"),
    VOTES_RESET(VOTES.toString() + ".reset"),
    SIGNS_CREATE("signs.create"),
    SIGNS_DESTROY("signs.destroy"),
    SIGNS_ADD_COMMAND("signs.add-command"),
    KITS_CREATE("kits.create"),
    KITS_DELETE("kits.delete"),
    KITS_GIVE("kits.give"),
    PERKBOOK_REDEEM("perkbook.redeem"),
    PERKBOOK_GIVE("perkbook.give"),
    PERKBOOK_CREATE("perkbook.create"),
    PERKBOOK_DELETE("perkbook.delete");

    public static final String PREFIX = "lolpvp.";

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

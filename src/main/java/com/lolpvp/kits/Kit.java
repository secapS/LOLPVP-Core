package com.lolpvp.kits;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class Kit {
    @Getter
    private String name;
    @Getter
    @Setter
    private ItemStack[] inventory;
    @Getter
    @Setter
    private ItemStack[] armor;
    @Getter
    @Setter
    private PotionEffect[] potionEffects;

    public Kit(String name) {
        this.name = name;
    }
}

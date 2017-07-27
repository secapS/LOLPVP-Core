package com.lolpvp.weapons;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CustomItem {

    private Material material;

    public CustomItem(Material material) {
        this.material = material;
    }

    public List<String> getLore() {
        return Collections.EMPTY_LIST;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return Collections.EMPTY_MAP;
    }
}

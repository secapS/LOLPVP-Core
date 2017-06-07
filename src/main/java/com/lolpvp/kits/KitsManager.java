package com.lolpvp.kits;

import com.lolpvp.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class KitsManager {

    private Core plugin;
    private FileConfiguration kitsData;
    private File kitsFile;

    public KitsManager(Core instance) {
        this.plugin = instance;
        kitsFile = new File(instance.getDataFolder(), "kits.yml");
        if(!kitsFile.exists()) {
            try {
                kitsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                instance.getLogger().log(Level.WARNING, "Couldn't create kits.yml");
            }
        }
        kitsData = YamlConfiguration.loadConfiguration(kitsFile);
    }

    public Kit createKit(String name, Player player, String[] potions) {
        List<String> potionsList = Arrays.asList(potions);
        Kit kit = new Kit(name);
        kit.setArmor(player.getInventory().getArmorContents());
        kit.setInventory(player.getInventory().getContents());
        PotionEffect[] potionEffects = new PotionEffect[potions.length];
        potionsList.forEach(potion -> {
            int amplifier = Integer.parseInt(potion.split(":")[1]);
            int duration = Integer.parseInt(potion.split(":")[2]);
            PotionEffect potionEffect = PotionEffectType.getByName(potion).createEffect(duration, amplifier);
            potionEffects[potionsList.indexOf(potion)] = potionEffect;
        });
        kit.setPotionEffects(potionEffects);
        return kit;
    }

    public void giveKit(String kit, Player player) {
        if(isKit(kit)) {
            ItemStack[] inventory = (ItemStack[]) this.kitsData.getList(kit + ".inventory").toArray();
            ItemStack[] armor = (ItemStack[]) this.kitsData.getList(kit + ".armor").toArray();
            PotionEffect[] potionEffects = (PotionEffect[]) this.kitsData.getList(kit + ".potioneffects").toArray();
            player.getInventory().setContents(inventory);
            player.getInventory().setArmorContents(armor);
            player.getActivePotionEffects().clear();
            Arrays.stream(potionEffects).forEach(potionEffect -> potionEffect.apply(player));

        } else {
            player.sendMessage(ChatColor.RED + "That is not a kit.");
        }
    }

    public void saveKit(Kit kit) {
        List<ItemStack> inventory = Arrays.asList(kit.getInventory());
        List<ItemStack> armor = Arrays.asList(kit.getArmor());
        List<PotionEffect> potionEffects = Arrays.asList(kit.getPotionEffects());

        this.kitsData.set(kit.getName() + ".inventory", inventory);
        this.kitsData.set(kit.getName() + ".armor", armor);
        this.kitsData.set(kit.getName() + ".potionEffects", potionEffects);
    }

    public void removeKit(String kit) {
        if(isKit(kit)) {
            this.kitsData.set(kit, null);
        }
    }

    public void saveKitData() {
        try {
            this.kitsData.save(this.kitsFile);
        } catch (IOException e) {
            e.printStackTrace();
            this.plugin.getLogger().log(Level.WARNING, "Couldn't save kits.yml");
        }
    }

    public void reloadKitData() {
        this.saveKitData();
        try {
            this.kitsData.load(this.kitsFile);
        } catch (IOException e) {
            e.printStackTrace();
            this.plugin.getLogger().log(Level.WARNING, "Couldn't load kits.yml");
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            this.plugin.getLogger().log(Level.WARNING, "Couldn't load kits.yml");
        }
    }

    public boolean isKit(String kit) {
        return this.kitsData.getConfigurationSection(kit) != null;
    }
}

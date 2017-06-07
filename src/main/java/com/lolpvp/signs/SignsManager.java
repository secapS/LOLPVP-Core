package com.lolpvp.signs;

import com.lolpvp.core.Core;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class SignsManager {

    private FileConfiguration signsData;
    private File signsFile;
    private Core plugin;

    public SignsManager(Core instance) {
        this.plugin = instance;
        signsFile = new File(instance.getDataFolder(), "signs.yml");
        if(!signsFile.exists()) {
            try {
                signsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                instance.getLogger().log(Level.WARNING, "Couldn't create signs.yml");
            }
        }
        signsData = YamlConfiguration.loadConfiguration(signsFile);
    }

    public void createCommandSign(Sign sign, String command, int price) {
        String id = UUID.randomUUID().toString();
        sign.setMetadata("commandsign-id", new FixedMetadataValue(this.plugin, id));
        if(price > 0) {
            sign.setMetadata("commandsign-price", new FixedMetadataValue(this.plugin, price));
            this.signsData.set(id + ".price", price);
        }
        sign.setMetadata("commandsign-commands", new FixedMetadataValue(this.plugin, command));
        this.signsData.set(id + ".commands", command);

        this.signsData.set(id + ".world", sign.getWorld().getName());
        this.signsData.set(id + ".x", sign.getLocation().getX());
        this.signsData.set(id + ".y", sign.getLocation().getY());
        this.signsData.set(id + ".z", sign.getLocation().getZ());
    }

    public void removeCommandSign(Sign sign) {
        String id = sign.getMetadata("commandsign-id").get(0).asString();
        this.signsData.set(id, null);
        sign.removeMetadata("commandsign-id", this.plugin);
        sign.removeMetadata("commandsign-commands", this.plugin);
        sign.getBlock().breakNaturally();
    }

    public void executeSignCommands(Sign sign, Player player) {
        if(sign.hasMetadata("commandsign-price")) {
            EconomyResponse response = Core.getEconomy().withdrawPlayer(player, sign.getMetadata("commandsign-price").get(0).asInt());
            if(!response.transactionSuccess()) {
                player.sendMessage(ChatColor.RED + "Insufficient funds.");
                return;
            }
        }
        sign.getMetadata("commandsign-commands").forEach(command -> this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), command.asString()));
    }

    public void addCommandToSign(Sign sign, String command) {
        if(isCommandSign(sign)) {
            sign.setMetadata("commandsign-commands", new FixedMetadataValue(this.plugin, command));
            this.signsData.getStringList(sign.getMetadata("commandsign-id").get(0).asString() + ".commands").add(command);
        }
    }

    public void loadSigns() {
        for(World worlds : this.plugin.getServer().getWorlds()) {
            for(String signs : this.signsData.getKeys(false)) {
                if(worlds.getName().equals(this.signsData.getString(signs + ".world"))) {
                    int x = (int)this.signsData.getDouble(signs + ".x");
                    int y = (int)this.signsData.getDouble(signs + ".y");
                    int z = (int)this.signsData.getDouble(signs + ".z");
                    Block block = worlds.getBlockAt(x, y, z);
                    if(block.getState() instanceof Sign && isCommandSign((Sign)block.getState())) {
                        loadSign(UUID.fromString(signs), (Sign)block.getState());
                    }
                }
            }
        }
    }

    private void loadSign(UUID uuid, Sign sign) {
        sign.setMetadata("commandsign-id", new FixedMetadataValue(this.plugin, uuid.toString()));
        if(this.signsData.get(uuid.toString() + ".price") != null) {
            sign.setMetadata("commandsign-price", new FixedMetadataValue(this.plugin, this.signsData.getInt(uuid.toString() + ".price")));
        }
        List<String> commands = this.signsData.getStringList(uuid.toString() + ".commands");
        StringBuilder commandsString = new StringBuilder();
        commands.forEach(command -> {
            if(command == commands.get(commands.size() - 1)) {
                commandsString.append(command);
            } else {
                commandsString.append(command + "|");
            }
        });
        sign.setMetadata("commandsign-commands", new FixedMetadataValue(this.plugin, commandsString.toString()));
    }

    public boolean isCommandSign(Sign sign) {
        if(sign.hasMetadata("commandsign-id")) return true;
        for(String signs : signsData.getKeys(false)) {
            double x = this.signsData.getDouble(signs + ".x");
            double y = this.signsData.getDouble(signs + ".y");
            double z = this.signsData.getDouble(signs + ".z");
            return x == sign.getX() && y == sign.getY() && z == sign.getZ();
        }
        return false;
    }

    public void saveSignData() {
        try {
            this.signsData.save(this.signsFile);
        } catch (IOException e) {
            e.printStackTrace();
            this.plugin.getLogger().log(Level.WARNING, "Couldn't save signs.yml");
        }
    }

    public void reloadSignData() {
        this.saveSignData();
        try {
            this.signsData.load(this.signsFile);
        } catch (IOException e) {
            e.printStackTrace();
            this.plugin.getLogger().log(Level.WARNING, "Couldn't load signs.yml");
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            this.plugin.getLogger().log(Level.WARNING, "Couldn't load signs.yml");
        }
    }

}

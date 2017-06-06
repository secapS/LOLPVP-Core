package com.lolpvp.signs;

import com.lolpvp.core.Core;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SignManager {

    private FileConfiguration signsData;
    private Core plugin;

    public SignManager(Core instance) {
        this.plugin = instance;
    }

    public void createCommandSign(Sign sign, List<String> commands) {
    }

    public void createCommandSign(Sign sign, List<String> commands, int price) {
        sign.setMetadata("commandsign-id", new FixedMetadataValue(this.plugin, UUID.randomUUID().toString()));
        if(price > 0) sign.setMetadata("commandsign-price", new FixedMetadataValue(this.plugin, price));
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

    public void executeSignCommands(Sign sign) {
        String[] commands = sign.getMetadata("commandsign-price").get(0).asString().split("|");
        Arrays.stream(commands).forEach(command -> {
            this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), command);
        });
    }

    public void removeCommandSign(Sign sign) {
        String id = sign.getMetadata("commandsign-id").get(0).asString();
        this.signsData.set(id, null);
        sign.removeMetadata("commandsign-id", this.plugin);
        sign.removeMetadata("commandsign-commands", this.plugin);
        sign.getBlock().breakNaturally();
    }

    public boolean isCommandSign(Sign sign) {
        for(String signs : signsData.getKeys(false)) {
            double x = this.signsData.getDouble(signs + ".x");
            double y = this.signsData.getDouble(signs + ".y");
            double z = this.signsData.getDouble(signs + ".z");
            return x == sign.getX() && y == sign.getY() && z == sign.getZ();
        }
        return false;
    }

}

package com.lolpvp.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtils {
    public static void giveItems(Player player, ItemStack... items) {
        for(ItemStack givenItem : items) {
            if (player.getInventory().firstEmpty() == -1) {
                int zip = 0;
                for (ItemStack inventory : player.getInventory().getContents()) {
                    int inventoryItemAmount = inventory.getAmount();
                    zip++;
                    if ((inventory.getType().equals(givenItem.getType())) && (inventory.getAmount() < givenItem.getMaxStackSize()))
                    {
                        player.getInventory().addItem(new ItemStack[] { givenItem });
                        break;
                    }
                    if ((zip == 36) && (inventoryItemAmount != inventoryItemAmount + 1))
                    {
                        player.getWorld().dropItemNaturally(player.getLocation(), givenItem);
                        break;
                    }
                }
            }
            else
            {
                player.getInventory().addItem(new ItemStack[] { givenItem });
            }
        }
    }
}

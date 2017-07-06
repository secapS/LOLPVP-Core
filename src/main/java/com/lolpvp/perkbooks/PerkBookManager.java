package com.lolpvp.perkbooks;

import com.lolpvp.core.Core;
import com.lolpvp.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class PerkBookManager {
    private FileConfiguration perkBooksData;
    private File perkBooksFile;

    private Core plugin;

    public PerkBookManager(Core instance) {
        this.plugin = instance;
        perkBooksFile = new File(instance.getDataFolder(), "perkbooks.yml");
        if(!perkBooksFile.exists()) {
            try {
                perkBooksFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                instance.getLogger().log(Level.WARNING, "Couldn't create perkbooks.yml");
            }
        }
        perkBooksData = YamlConfiguration.loadConfiguration(perkBooksFile);
    }

    public boolean isPerkBook(ItemStack itemStack) {
        if (itemStack.getType() == Material.WRITTEN_BOOK) {
            BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
            for (String perkbook : this.perkBooksData.getKeys(false)) {
                return (bookMeta.hasAuthor() && bookMeta.getAuthor().equals(this.perkBooksData.getString(perkbook + ".author")))
                        && (bookMeta.hasTitle() && bookMeta.getTitle().equals(this.perkBooksData.getString(perkbook)))
                        && (bookMeta.hasPages() && bookMeta.getPages().containsAll(this.perkBooksData.getStringList(perkbook + ".pages")));
            }
        }
        return false;
    }

    public void givePerkBook(Player player, String perkbook) {
        PerkBook perkBook = getPerkBook(perkbook);
        if(perkBook == null) {
            this.plugin.getLogger().log(Level.WARNING, "That perkbook does not exist!");
            return;
        }
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta)book.getItemMeta();
        bookMeta.setTitle(perkBook.getTitle());
        bookMeta.setAuthor(perkBook.getAuthor());
        bookMeta.setPages(perkBook.getPages());
        book.setItemMeta(bookMeta);
        PlayerUtils.giveItems(player, book);
    }

    public void redeemPerkBook(Player player) {
        if(isPerkBook(player.getItemInHand())) {
            PerkBook perkBook = getPerkBook(((BookMeta)player.getItemInHand().getItemMeta()).getTitle());
            if(perkBook.getType() == PerkBook.Type.PERMISSION) {
                perkBook.getPermissions().forEach(permission -> this.plugin.permission.playerAdd(player, permission));
            } else if(perkBook.getType() == PerkBook.Type.GROUP) {
                this.plugin.permission.playerAddGroup(player, perkBook.getPermissions().get(0));
            }
            player.setItemInHand(null);
        }
    }

    public PerkBook getPerkBook(String perkbook) {
        perkbook = perkbook.toLowerCase();
        PerkBook perkBook = null;
        if(this.perkBooksData.getConfigurationSection(perkbook) != null) {
            perkBook = new PerkBook(perkbook, this.perkBooksData.getString(perkbook + ".author"));
            perkBook.setPages(this.perkBooksData.getStringList(perkbook + ".pages"));
            perkBook.setType(PerkBook.Type.valueOf(this.perkBooksData.getString(perkbook + ".type")));
        }
        return perkBook;
    }

    public void createPerkBook(BookMeta book, PerkBook.Type type, List<String> permissions) {
        this.perkBooksData.set(book.getTitle().toLowerCase() + ".author", book.getAuthor());
        this.perkBooksData.set(book.getTitle().toLowerCase() + ".pages", book.getPages());
        this.perkBooksData.set(book.getTitle().toLowerCase() + ".type", type.toString());
        this.perkBooksData.set(book.getTitle().toLowerCase() + ".permissions", permissions);
        this.reloadSignData();
    }

    public void deletePerkBook(String title) {
        if(this.perkBooksData.getConfigurationSection(title) == null) {
            this.plugin.getLogger().log(Level.WARNING, "That perkbook does not exist!");
            return;
        }
        this.perkBooksData.set(title, null);
        this.reloadSignData();
    }

    public void savePerkBookData() {
        try {
            this.perkBooksData.save(this.perkBooksFile);
        } catch (IOException e) {
            e.printStackTrace();
            this.plugin.getLogger().log(Level.WARNING, "Couldn't save perkbooks.yml");
        }
    }

    public void reloadSignData() {
        this.savePerkBookData();
        try {
            this.perkBooksData.load(this.perkBooksFile);
        } catch (IOException e) {
            e.printStackTrace();
            this.plugin.getLogger().log(Level.WARNING, "Couldn't load perkbooks.yml");
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            this.plugin.getLogger().log(Level.WARNING, "Couldn't load perkbooks.yml");
        }
    }

}

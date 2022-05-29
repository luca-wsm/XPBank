package de.luca.xpbank.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GuiUtil {

    private static Inventory stateInventory;

    public static boolean isInLevelSpan(int level, int min, int max) {
        return level >= min && level < max;
    }

    public static void createItemForSpan(int startIndex, int endIndex, boolean useRedGlas) {
      int _index = startIndex;
      int _level = 25;
        while(_index != endIndex + 1) {
            if(useRedGlas) {
                stateInventory.setItem(_index, ItemUtil.createItem(Material.RED_STAINED_GLASS_PANE, 1, 0, "§aXP-Stand: " + _level + " Level"));
            } else {
                stateInventory.setItem(_index, ItemUtil.createItem(Material.GREEN_STAINED_GLASS_PANE, 1, 0, "§aXP-Stand: " + _level + " Level"));
            }
            _index++;
            _level += 25;
        }
    }


    public static void openOverview(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "XP-Bank");
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, ItemUtil.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 0, ""));
        }
        inv.setItem(11, ItemUtil.createItem(Material.EXPERIENCE_BOTTLE, 1, 0, "§aInpay"));
        inv.setItem(13, ItemUtil.createItem(Material.GREEN_STAINED_GLASS_PANE, 1, 0, "§aXP-Stand"));
        inv.setItem(15, ItemUtil.createItem(Material.GLASS_BOTTLE, 1, 0, "§4Outpay"));
        inv.setItem(26, ItemUtil.createItem(Material.RED_TERRACOTTA, 1, 0, "§4Schließen"));
        p.openInventory(inv);
    }

    public static void openInpay(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "Wähle nun den Inpay-Betrag");
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, ItemUtil.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 0, ""));
        }

        int level = 5;
        for(int i = 11; i <= 15; i++) {
            inv.setItem(i, ItemUtil.createItem(Material.GREEN_STAINED_GLASS_PANE, 1, 0, "§a" + level + " Level"));
            level += 5;
        }

        inv.setItem(26, ItemUtil.createItem(Material.RED_TERRACOTTA, 1, 0, "§4Schließen"));
        p.openInventory(inv);
    }

    public static void openOutpay(Player p) {

        Inventory inv = Bukkit.createInventory(null, 27, "Wähle nun den Outpay-Betrag");
        for (int i = 0; i < 27; i++) {
            inv.setItem(i, ItemUtil.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 0, ""));
        }

        int level = 5;
        for(int i = 10; i <= 14; i++) {
            inv.setItem(i, ItemUtil.createItem(Material.RED_STAINED_GLASS_PANE, 1, 0, "§4" + level + " Level"));
            level += 5;
        }

        inv.setItem(16, ItemUtil.createItem(Material.REDSTONE_BLOCK, 1, 0, "§4Alle Level"));
        inv.setItem(26, ItemUtil.createItem(Material.RED_TERRACOTTA, 1, 0, "§4Schließen"));
        p.openInventory(inv);
    }

    public static void openState(Player p) {

        stateInventory = Bukkit.createInventory(null, 27, "XP-Stand");
        for (int i = 0; i < 27; i++) {
            stateInventory.setItem(i, ItemUtil.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 0, ""));
        }

        int level = XpDatabaseUtil.getLevel(p);

        createItemForSpan(10, 16, true);
        stateInventory.setItem(22, ItemUtil.createItem(Material.ENCHANTED_BOOK, 1, 0, "§aKontostand: §6" + level + " §aLevel"));
        stateInventory.setItem(26, ItemUtil.createItem(Material.RED_TERRACOTTA, 1, 0, "§4Schließen"));

        if (isInLevelSpan(level, 25, 50)) {
            stateInventory.setItem(10, ItemUtil.createItem(Material.GREEN_STAINED_GLASS_PANE, 1, 0, "§aXP-Stand: 25 Level"));
        } else if (isInLevelSpan(level, 50, 75)) {
            createItemForSpan(10, 12, false);
        } else if (isInLevelSpan(level, 75, 100)) {
            createItemForSpan(10, 12, false);
        } else if (isInLevelSpan(level, 100, 125)) {
            createItemForSpan(10, 13, false);
        } else if (isInLevelSpan(level, 125, 150)) {
            createItemForSpan(10, 14, false);
        } else if (isInLevelSpan(level, 150, 175)) {
            createItemForSpan(10, 15, false);
        } else if (level >= 175) {
            createItemForSpan(10, 16, false);
        }

        p.openInventory(stateInventory);
    }
}

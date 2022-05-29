package de.luca.xpbank.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

    public static ItemStack createItem(Material material, int count, int subid, String displayName) {
        short neuesubid = (short) subid;
        ItemStack itemStack = new ItemStack(material, count, neuesubid);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

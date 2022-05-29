package de.luca.xpbank.events;

import de.luca.xpbank.commands.XpBankCommand;
import de.luca.xpbank.utils.GuiUtil;
import de.luca.xpbank.utils.PlayerUtil;
import de.luca.xpbank.utils.XpDatabaseUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiEvents implements Listener {

    // May fix DRT in the future

    //OverView Click
    @EventHandler
    public void onClickOverview(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());

        if(e.getView().getTitle().equalsIgnoreCase("XP-Bank")) {
            switch(clickedItem.getItemMeta().getDisplayName()) {
                case "§aInpay":
                    p.closeInventory();
                    GuiUtil.openInpay(p);
                    break;
                case "§aXP-Stand":
                    p.closeInventory();
                    GuiUtil.openState(p);
                    break;
                case "§4Outpay":
                    p.closeInventory();
                    GuiUtil.openOutpay(p);
                    break;
                case "§4Schließen":
                    p.closeInventory();
                    new PlayerUtil().sendMessage(p, "Die XP-Bank wurde erfolgreich geschlossen!");
            }
            e.setCancelled(true);
        }
    }

    //Inpay Click
    @EventHandler
    public void onClickInPay(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());

        if (e.getView().getTitle().equalsIgnoreCase("Wähle nun den Inpay-Betrag")) {
            switch (clickedItem.getItemMeta().getDisplayName()) {
                case "§a5 Level":
                    p.closeInventory();
                    XpBankCommand.payIn(p, 5);
                    break;
                case "§a10 Level":
                    p.closeInventory();
                    XpBankCommand.payIn(p, 10);
                    break;
                case "§a15 Level":
                    p.closeInventory();
                    XpBankCommand.payIn(p, 15);
                    break;
                case "§a20 Level":
                    p.closeInventory();
                    XpBankCommand.payIn(p, 20);
                    break;
                case "§a25 Level":
                    p.closeInventory();
                    XpBankCommand.payIn(p, 25);
                    break;
                case "§4Schließen":
                    p.closeInventory();
                    new PlayerUtil().sendMessage(p, "Die XP-Bank wurde erfolgreich geschlossen!");
            }
            e.setCancelled(true);
        }
    }

    //Outpay Click
    @EventHandler
    public void onClickOutpay(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());

        if (e.getView().getTitle().equalsIgnoreCase("Wähle nun den Outpay-Betrag")) {

            switch (clickedItem.getItemMeta().getDisplayName()) {
                case "§45 Level":
                    p.closeInventory();
                    XpBankCommand.payOut(p, 5);
                    break;
                case "§410 Level":
                    p.closeInventory();
                    XpBankCommand.payOut(p, 10);
                    break;
                case "§415 Level":
                    p.closeInventory();
                    XpBankCommand.payOut(p, 15);
                    break;
                case "§420 Level":
                    p.closeInventory();
                    XpBankCommand.payOut(p, 20);
                    break;
                case "§425 Level":
                    p.closeInventory();
                    XpBankCommand.payOut(p, 25);
                    break;
                case "§4Alle Level":
                    p.closeInventory();
                    XpBankCommand.payOut(p, XpDatabaseUtil.getLevel(p));
                case "§4Schließen":
                    p.closeInventory();
                    new PlayerUtil().sendMessage(p, "Die XP-Bank wurde erfolgreich geschlossen!");
            }
            e.setCancelled(true);
        }
    }

    //Outpay Click
    @EventHandler
    public void onClickState(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getInventory().getItem(e.getSlot());

        if (e.getView().getTitle().equalsIgnoreCase("XP-Stand")) {

            switch (clickedItem.getItemMeta().getDisplayName()) {
                case "§4Schließen":
                    p.closeInventory();
                    new PlayerUtil().sendMessage(p, "Die XP-Bank wurde erfolgreich geschlossen!");
            }
            e.setCancelled(true);
        }
    }
}
